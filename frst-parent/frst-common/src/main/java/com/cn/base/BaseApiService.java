package com.cn.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.constants.Constants;


@Component
public class BaseApiService {

	@Autowired
	protected BaseApiService baseApiService;
	
	public ResponseBase setResultError(Integer code, String msg){
		
		return setResult(code,msg,null);
	}
	
	public ResponseBase setResultError(String msg) {
		return setResult(Constants.HTTP_RES_CODE_500, msg, null);
	}

	// 返回成功，可以传data值
	public ResponseBase setResultSuccess(Object data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
	}

	// 返回成功，沒有data值
	public ResponseBase setResultSuccess() {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
	}

	// 返回成功，沒有data值
	public ResponseBase setResultSuccess(String msg) {
		return setResult(Constants.HTTP_RES_CODE_200, msg, null);
	}
	
	//通用方法
	public ResponseBase setResult(Integer code, String msg, Object data){
		return new ResponseBase(code,msg,data);
	}
}
