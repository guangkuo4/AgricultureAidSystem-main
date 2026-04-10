package com.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.interceptor.AuthorizationInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport{
	
	@Bean
    public AuthorizationInterceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorizationInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
        super.addInterceptors(registry);
	}
	
	/**
	 * springboot 2.0配置WebMvcConfigurationSupport之后，会导致默认配置被覆盖，要访问静态资源需要重写addResourceHandlers方法
	 */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 必须先注册 /upload/**，否则会被下面的 /** 抢走且 classpath 下没有 upload，导致轮播图等上传图片 404
		Path uploadDir = Paths.get(System.getProperty("user.dir"), "upload");
		String uploadLocation = uploadDir.toUri().toString();
		if (!uploadLocation.endsWith("/")) {
			uploadLocation += "/";
		}
		// 先 user.dir/upload（实际上传优先生效），再 classpath:/static/upload/ 作为演示缺省，避免仓库未带运行目录时数据库路径 404
		registry.addResourceHandler("/upload/**")
				.addResourceLocations(uploadLocation)
				.addResourceLocations("classpath:/static/upload/");

		registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/resources/")
        .addResourceLocations("classpath:/static/")
        .addResourceLocations("classpath:/admin/")
        .addResourceLocations("classpath:/front/")
        .addResourceLocations("classpath:/public/");
		super.addResourceHandlers(registry);
    }
}
