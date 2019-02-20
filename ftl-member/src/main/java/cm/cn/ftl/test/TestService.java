package cm.cn.ftl.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestService {

	@RequestMapping("/home")
	public String home(){
		return "这是一个测试！";
	}
	
}
