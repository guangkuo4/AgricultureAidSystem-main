package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.R;

/**
 * 上传文件映射表
 */
@RestController
@RequestMapping("file")
@SuppressWarnings({"unchecked","rawtypes"})
public class FileController{
	@Autowired
    private ConfigService configService;

	/**
	 * 获取 upload 目录的绝对路径
	 */
	private String getUploadDir() {
		return System.getProperty("user.dir") + File.separator + "upload" + File.separator;
	}

	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	@IgnoreAuth
	public R upload(@RequestParam("file") MultipartFile file, String type) throws Exception {
		if (file.isEmpty()) {
			throw new EIException("上传文件不能为空");
		}
		String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		String uploadPath = getUploadDir();
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String fileName = new Date().getTime() + "." + fileExt;
        if (StringUtils.isNotBlank(type) && type.contains("_template")) {
            fileName = type + "." + fileExt;
            new File(uploadPath + fileName).deleteOnExit();
        }
		File dest = new File(uploadPath + fileName);
		file.transferTo(dest);
		if (StringUtils.isNotBlank(type) && type.equals("1")) {
			ConfigEntity configEntity = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", "faceFile"));
			if (configEntity == null) {
				configEntity = new ConfigEntity();
				configEntity.setName("faceFile");
				configEntity.setValue(fileName);
			} else {
				configEntity.setValue(fileName);
			}
			configService.insertOrUpdate(configEntity);
		}
		return R.ok().put("file", fileName);
	}

	/**
	 * 访问上传的文件（静态资源入口）
	 * 访问路径：/file/uploads/{filename}
	 * 前端拼接方式：baseUrl + "file/uploads/" + filename
	 */
	@RequestMapping("/uploads")
	@IgnoreAuth
	public void getUploadFile(@RequestParam String fileName, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException {
		if (StringUtils.isBlank(fileName)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "文件名不能为空");
			return;
		}
		String uploadPath = getUploadDir();
		File file = new File(uploadPath + fileName);
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "文件不存在");
			return;
		}
		String contentType = request.getServletContext().getMimeType(fileName);
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		response.setContentType(contentType);
		response.setContentLengthLong(file.length());
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + encodedFileName + "\"");
		try (InputStream in = Files.newInputStream(file.toPath());
		     OutputStream out = response.getOutputStream()) {
			byte[] buffer = new byte[8192];
			int len;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		}
	}

	/**
	 * 下载文件
	 */
	@RequestMapping("/download")
	@IgnoreAuth
	public ResponseEntity<byte[]> download(@RequestParam String fileName) {
		try {
			String uploadPath = getUploadDir();
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			File file = new File(uploadPath + fileName);
			if (file.exists()) {
				HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			    headers.setContentDispositionFormData("attachment", fileName);
			    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
