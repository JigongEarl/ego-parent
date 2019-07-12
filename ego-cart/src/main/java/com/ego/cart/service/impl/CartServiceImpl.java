package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
@Service
public class CartServiceImpl implements CartService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${cart.key}")
	private String cartKey;
	
	/**
	 * 添加购物车
	 */
	public void addCart(long id, int num, HttpServletRequest request) {
		//查询待添加购物车的商品信息
		TbItem item = tbItemDubboServiceImpl.selById(id);
		//获取保存在redis中的用户信息的key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//从redis中获取用户信息
		TbUser userJson = JsonUtils.jsonToPojo(jedisDaoImpl.get(token), TbUser.class);
		//获取用户名
		String username = userJson.getUsername();
		//设置redis中保存购物车信息的key为：cartKey+用户名
		String key = cartKey + username;
		//购物车对象
		List<TbItemChild> list = new ArrayList<TbItemChild>();
		/*
		 * 判断redis中是否已保存了该购物车
		 * 1、有，追加商品   2、无，新建
		 */
		if(jedisDaoImpl.exists(key)) {
			String cartJson = jedisDaoImpl.get(key);
			if(cartJson != null && !cartJson.equals("")) { //有购物车
				list = JsonUtils.jsonToList(cartJson, TbItemChild.class);//获取购物车信息
				/*
				 * 判断商品在购物车中是否已经存在?  存在-->追加数量   不存在-->新建商品信息
				 * 注意，此时TbItemChild中的num表示购物车中该商品的数量，不再表示库存
				 */
				for(TbItemChild child :list) {
					if(child.getId() == id) {  //存在
						child.setNum(child.getNum() + num);
						jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
						return;
					}
				}
			}
		}
		/*
		 * redis中无购物车或者购物车无该商品，新建商品购物信息
		 */
		TbItemChild newChild = new TbItemChild(); 
		newChild.setId(item.getId());
		newChild.setNum(num);
		newChild.setTitle(item.getTitle());
		newChild.setPrice(item.getPrice());
		newChild.setImages(item.getImage().equals("")?new String[1]:item.getImage().split(","));
		list.add(newChild);
		System.out.println("addCart:cartJson:"+list);
		jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
	}

	/**
	 * 显示购物车商品信息
	 */
	public List<TbItemChild> showCart(HttpServletRequest request) {
		/*
		 * redis中用户信息的key-value
		 */
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		TbUser user = JsonUtils.jsonToPojo(jedisDaoImpl.get(token), TbUser.class);
		
		/*
		 * redis中购物车信息key-value
		 */
		String cartJson = jedisDaoImpl.get(cartKey + user.getUsername());
		return JsonUtils.jsonToList(cartJson, TbItemChild.class);
	}
	
	/**
	 * 根据id修改数量
	 */
	public EgoResult update(long id, int num,HttpServletRequest request) {
		/*
		 * redis中用户信息的key-value
		 */
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		TbUser user = JsonUtils.jsonToPojo(jedisDaoImpl.get(token), TbUser.class);
		/*
		 * redis中购物车信息key-value
		 */
		String key = cartKey + user.getUsername();
		String cartJson = jedisDaoImpl.get(key);
		//购物车信息
		List<TbItemChild> list = JsonUtils.jsonToList(cartJson, TbItemChild.class);
		for (TbItemChild child : list) {
			if((long)child.getId()==id){
				child.setNum(num);
			}
		}
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		EgoResult egoResult = new EgoResult();
		System.out.println("ok:"+ok);
		if(ok.equals("OK")){
			egoResult.setStatus(200);
		}
		return egoResult;
	}

	/**
	 * 删除购物车商品
	 */
	public EgoResult delete(long id, HttpServletRequest request) {
		/*
		 * redis中用户信息的key-value
		 */
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		TbUser user = JsonUtils.jsonToPojo(jedisDaoImpl.get(token), TbUser.class);
		
		/*
		 * redis中购物车信息key-value
		 */
		String key = cartKey + user.getUsername();
		String cartJson = jedisDaoImpl.get(key);
		//购物车信息
		List<TbItemChild> list = JsonUtils.jsonToList(cartJson, TbItemChild.class);
		TbItemChild tbItemChild = null;
		for (TbItemChild child : list) {
			if((long)child.getId()==id){
				tbItemChild = child;
			}
		}
		list.remove(tbItemChild);
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		System.out.println("ok:"+ok);
		EgoResult egoResult = new EgoResult();
		if(ok.equals("OK")){
			egoResult.setStatus(200);
		}
		return egoResult;
	}

}
