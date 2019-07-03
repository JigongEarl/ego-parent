package com.ego.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.ego.pojo.TbItem;

public interface TbItemService {
	/**
	 * 初始化solr数据
	 * @throws SolrServerException
	 * @throws IOException
	 */
	void init() throws SolrServerException, IOException;
	
	/**
	 * 搜索商品，分页显示
	 * @param query
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	Map<String, Object> selByQuery(String query,int page, int rows) throws SolrServerException, IOException;
	
	/**
	 * 新增solr数据
	 * @param tbItem
	 * @return
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	int add(Map<String, Object> map) throws SolrServerException, IOException;
}
