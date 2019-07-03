package com.ego.manage.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;
/**
 * 图片上传
 * @author 86156
 *
 */
@Service
public class PicServiceImpl implements PicService{

//	ftpClient.host=192.168.241.133
//	ftpClient.port=21
//	ftpClient.username=ftpuser
//	ftpClient.password=ftpuser
//	ftpClient.basepath=/home/ftpuser
//	ftpClient.filepath=/
	@Value("${ftpClient.host}")
	private String host;
	
	@Value("${ftpClient.port}")
	private int port;
	
	@Value("${ftpClient.username}")
	private String username;
	
	@Value("${ftpClient.password}")
	private String password;
	
	@Value("${ftpClient.basepath}")
	private String basePath;
	
	@Value("${ftpClient.filepath}")
	private String filePath;
	
	/**
	 * 图片上传到Linux服务器，基于vsftpd实现
	 */
	public Map<String, Object> upload(MultipartFile file) throws IOException {
		//重命名上传的文件
		String filename = IDUtils.genItemId() + file.getOriginalFilename()
							.substring(file.getOriginalFilename().lastIndexOf("."));
		//上传，FtpUtil,在FtpClient之上封装了类，实现一步上传下载
		boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, filename, 
							file.getInputStream());
		/*
		 * kindEditor要求的返回格式
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		if(result){
			map.put("error", 0);
			map.put("url", "http://192.168.241.133/"+filename);
		}else {
			map.put("error", 1);
		    map.put("message", "图片上传失败");
		}
		return map;
	}
	
}
