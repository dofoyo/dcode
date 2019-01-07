package com.rhb.dcode.wxmini.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhb.dcode.common.HttpClientUtil;
import com.rhb.dcode.common.JsonUtil;
import com.rhb.dcode.common.ResponseContent;
import com.rhb.dcode.common.ResponseEnum;

@RestController
public class WXLoginController {
	protected static final Logger logger = LoggerFactory.getLogger(WXLoginController.class);

	@Value("${appid}")
	private String appid;

	@Value("${secret}")
	private String secret;
	
	List<Record> records = new ArrayList<Record>();
	
	@PostMapping("/wxLogin")
	public ResponseContent<WXSessionModel> wxLogin(String code) {
		
		//System.out.println("wxlogin - code: " + code);
		logger.info("wxlogin - code: " + code);
		
//		https://api.weixin.qq.com/sns/jscode2session?
//				appid=APPID&
//				secret=SECRET&
//				js_code=JSCODE&
//				grant_type=authorization_code
		
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String, String> param = new HashMap<>();
		param.put("appid", appid);
		param.put("secret", secret);
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");
		
		String wxResult = HttpClientUtil.doGet(url, param);
		//System.out.println(wxResult);
		logger.info("wxResult: " + wxResult);

		
		WXSessionModel model = JsonUtil.jsonToPojo(wxResult, WXSessionModel.class);
		
/*		// 存入session到redis
		redis.set("user-redis-session:" + model.getOpenid(), 
							model.getSession_key(), 
							1000 * 60 * 30);*/
		
		return new ResponseContent<WXSessionModel>(ResponseEnum.SUCCESS,model);
	}
	
	@PostMapping("/updateRecord")
	public ResponseContent<String> updateRecord(String openid, String second) {
		//System.out.println("openid:" + openid + ",second:" + second);
		
		Integer order = this.getOrder(openid, second);
		return new ResponseContent<String>(ResponseEnum.SUCCESS,order.toString());
	}
	
	private Integer getOrder(String openid, String second) {
		Record record = new Record(openid,second);
		
		logger.info(record.toString());
		
		records.add(record);
		Collections.sort(records,new Comparator<Record>() {
			@Override
			public int compare(Record o1, Record o2) {
				// TODO Auto-generated method stub
				return o1.getSecond().compareTo(o2.getSecond());
			}
		});
		int i=0;
		for(Record r : records) {
			i++;
			if(r.equals(record)) {
				break;
			}
		}
		
		return i;
	}
	
	@GetMapping("/records")
	public ResponseContent<List<Record>> getRecords() {
		return new ResponseContent<List<Record>>(ResponseEnum.SUCCESS,this.records);
	}

}
