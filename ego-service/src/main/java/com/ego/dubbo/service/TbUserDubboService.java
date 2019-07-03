package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

public interface TbUserDubboService {
	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 */
	TbUser selByUser(TbUser user);
}
