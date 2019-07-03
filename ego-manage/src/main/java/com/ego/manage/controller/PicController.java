package com.ego.manage.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.PicService;
import com.ego.pojo.TbContent;

@Controller
public class PicController {
	@Resource
	private PicService picServiceImpl;
	
	/**
	 * 图片上传
	 * @param file
	 * @return
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map<String, Object> upload(MultipartFile uploadFile){ //形参值与前台请求体的文件表单项名称相同
		Map<String, Object> map = new HashMap<>();
		try {
			map = picServiceImpl.upload(uploadFile);
		} catch (IOException e) {
			map.put("error", 1);
			map.put("message", "上传图片时服务器异常");
			e.printStackTrace();
		}
		return map;
	}
}
