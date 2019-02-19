package com.cn.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.cn.api.service.TestService;

@RestController
public class TestServiceImpl implements TestService {

	@Override
	public Map<String, Object> test(Integer id, String name) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("name", name);
		return map;
	}

}
