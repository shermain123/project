package com.cn.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member")
public interface TestService {

	@RequestMapping("/test")
	public Map<String,Object> test(Integer id,String name);
	
}
