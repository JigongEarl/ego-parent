package com.ego.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.service.TbItemService;
@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Resource
	private CloudSolrClient solrClient;
	/**
	 * 初始化solr数据
	 */
	public void init() throws SolrServerException, IOException {
		/*
		 * 初始化前先删除所有数据
		 */
		solrClient.deleteByQuery("*:*");
		solrClient.commit();
		
		List<TbItem> itemList = tbItemDubboServiceImpl.selAllByStatus((byte)1);
		for(TbItem tbItem : itemList) {
			TbItemDesc tbItemDesc = tbItemDescDubboServiceImpl.selByItemId(tbItem.getId());
			TbItemCat tbItemCat = tbItemCatDubboServiceImpl.selById(tbItem.getCid());
			/*
			 * 该内容已通过在spring配置文件中配置bean替代完成
			 * //连接对象，连接solr云(集群)
			 *  CloudSolrClient solrClient = new CloudSolrClient(
			 * "192.168.241.135:2181,192.168.241.135:2182,192.168.241.135:2183");
			 *  //连接默认索引库
			 * solrClient.setDefaultCollection("collection1");
			 */
			//slor的Document对象，表示需要录入的一组数据对象
			SolrInputDocument doc = new SolrInputDocument();
			
			/*
			 * 对照solr的schema.xml中的配置，封装数据到指定的索引字段
			 * 
			 * <field name="item_title" type="text_ik" indexed="true" stored="true"/> 
			 * <field name="item_sell_point" type="text_ik" indexed="true" stored="true"/> 
			 * <field name="item_price" type="long" indexed="true" stored="true"/> 
			 * <field name="item_image" type="string" indexed="false" stored="true"/> 
			 * <field name="item_category_name" type="string" indexed="true" stored="true"/> 
			 * <field name="item_desc" type="text_ik" indexed="true" stored="false"/> 
			 * <field name="item_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/> 
			 * <copyField source="item_title" dest="item_keywords"/>
			 * <copyField source="item_sell_point" dest="item_keywords"/> 
			 * <copyField source="item_category_name" dest="item_keywords"/> 
			 * <copyField source="item_desc" dest="item_keywords"/>
			 */
			doc.setField("id", tbItem.getId());
			doc.setField("item_title", tbItem.getTitle());
			doc.setField("item_sell_point", tbItem.getSellPoint());
			doc.setField("item_price", tbItem.getPrice());
			doc.setField("item_image", tbItem.getImage());
			doc.setField("item_category_name", tbItemCat.getName());
			doc.setField("item_desc", tbItemDesc.getItemDesc());
			doc.setField("item_updated", tbItem.getUpdated());
			solrClient.add(doc); //向solr中添加数据，需要提交才能生效
			solrClient.commit(); //提交操作
		}
	}
	
	/**
	 * 分页搜索
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public Map<String, Object> selByQuery(String query, int page, int rows) throws SolrServerException, IOException {
		//实例化查询条件，以下开始封装条件，相当于solr可视化界面中的Query整个条件输入栏
		SolrQuery params =new SolrQuery();
		/*
		 * 设置分页条件
		 */
		params.setStart(rows*(page-1));
		params.setRows(rows);
		//添加排序条件--降序
		params.setSort("item_updated", ORDER.desc);
		//设置搜索字段条件
		params.setQuery("item_keywords:"+query);
		/*
		 * 设置高亮
		 * 1、打开高亮  2、设置高亮索引字段  3、设置高亮样式(html/css样式，会加在字段前后)
		 */
		params.setHighlight(true);
		params.addHighlightField("item_title");
		params.setHighlightSimplePre("<span style='color:red'>");
		params.setHighlightSimplePost("</span>");
		//获取返回的查询结果
		QueryResponse response = solrClient.query(params);
		//未高亮内容
		SolrDocumentList solrList = response.getResults();
		//高亮内容
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		/*
		 * 封装待返回的对象
		 */
		List<TbItemChild> listChild = new ArrayList<>();
		for (SolrDocument doc : solrList) {
			TbItemChild  child = new TbItemChild();
			
			child.setId(Long.parseLong(doc.getFieldValue("id").toString()));
			List<String> list = highlighting.get(doc.getFieldValue("id")).get("item_title");
			if(list != null && list.size() > 0){//如果设置了高亮就从高亮部分取值，否则就从非高亮部分取
				child.setTitle(list.get(0));
			}else{
				child.setTitle(doc.getFieldValue("item_title").toString());
			}
			child.setPrice((Long)doc.getFieldValue("item_price"));
			Object image = doc.getFieldValue("item_image");
			child.setImages(image==null||image.equals("")?new String[1]:image.toString().split(","));
			child.setSellPoint(doc.getFieldValue("item_sell_point").toString());
			
			listChild.add(child);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("itemList", listChild);
		result.put("totalPages", solrList.getNumFound() % rows == 0 
							? solrList.getNumFound() / rows : solrList.getNumFound() / rows + 1);
		return result;
	}
	
	/**
	 * 新增数据
	 */
	public int add(Map<String, Object> _map) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		/*_map格式，需要取出里面的内容
		 * {item={id=155734072686208, title=这是一个神奇的彩票, sellPoint=aa, price=99900, num=9999, 
		 * barcode=88888, image=http://192.168.241.133/155734030771958.jpg, cid=1149, status=1, 
		 * created=1557340726862, updated=1557340726862}, desc=啊啊啊}
		 */
		Map<String, Object> map = (Map<String, Object>) _map.get("item");
		
		doc.setField("id", map.get("id"));
		doc.setField("item_title", map.get("title"));
		doc.setField("item_sell_point", map.get("sellPoint"));
		doc.setField("item_price", map.get("price"));
		doc.setField("item_image", map.get("image"));
		doc.setField("item_category_name", 
				tbItemCatDubboServiceImpl.selById((Integer)map.get("cid")).getName());
		doc.setField("item_desc", _map.get("desc"));
		doc.setField("item_updated", new Date((long)map.get("updated")));
		UpdateResponse response = solrClient.add(doc);
		solrClient.commit();
		if(response.getStatus() == 0) {
			return 1;
		}
		return 0;
	}

}
