package com.rhb.dcode.wxmini.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhb.dcode.common.HttpClientUtil;
import com.rhb.dcode.common.JsonUtil;
import com.rhb.dcode.common.ResponseContent;
import com.rhb.dcode.common.ResponseEnum;

@RestController
public class WXLoginController {
	@PostMapping("/wxLogin")
	public ResponseContent<WXSessionModel> wxLogin(String code) {
		
		System.out.println("wxlogin - code: " + code);
		
//		https://api.weixin.qq.com/sns/jscode2session?
//				appid=APPID&
//				secret=SECRET&
//				js_code=JSCODE&
//				grant_type=authorization_code
		
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> param = new HashMap<>();
		param.put("appid", "wx6b5736ad8513a43f");
		param.put("secret", "eb9cc2c6a93f926b8f3affcc06e4ea64");
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");
		
		String wxResult = HttpClientUtil.doGet(url, param);
		System.out.println(wxResult);
		
		WXSessionModel model = JsonUtil.jsonToPojo(wxResult, WXSessionModel.class);
		
/*		// 存入session到redis
		redis.set("user-redis-session:" + model.getOpenid(), 
							model.getSession_key(), 
							1000 * 60 * 30);*/
		
		return new ResponseContent<WXSessionModel>(ResponseEnum.SUCCESS,model);
	}

}
