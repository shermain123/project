package com.cn.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ResponseBase {
	
	//响应代码
	private Integer rtnCode;
	
	//响应信息
	private String msg;
	
	//返回数据
	private Object data;

	public ResponseBase(){}
	
	public ResponseBase(Integer rtnCode, String msg, Object data) {
		super();
		this.rtnCode = rtnCode;
		this.msg = msg;
		this.data = data;
	}
	
	
}
