package com.yuxxer.gtsd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

public class weixinBtn {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception, IOException {
		HttpClient httpclient = new org.apache.http.impl.client.DefaultHttpClient();

		String appId="";
		String secretId="4b0d625b9123f6f00957fe815b41d7cc";
		// 获取access_token
		String access_token = null;
		HttpGet httpgets = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secretId);
		HttpResponse response = httpclient.execute(httpgets);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instreams = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instreams));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					instreams.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String str = sb.toString();
			JSONObject accessObj = new JSONObject(str);
			if (accessObj.has("access_token")) {
				access_token = accessObj.getString("access_token");
			}
		}
		System.out.println(access_token);

		// 创建菜单
		JSONObject params = new JSONObject();
		JSONArray buttons = new JSONArray();
		params.put("button", buttons);

		JSONObject btn0 = new JSONObject();
		btn0.put("type", "click");
		btn0.put("name", "开庭提醒");
		btn0.put("key", "weixin-btns-anhao");
		JSONObject btn1 = new JSONObject();
		btn1.put("type", "click");
		btn1.put("name", "法律资讯");
		btn1.put("key", "weixin-btns-news");
		JSONObject btn2 = new JSONObject();
		JSONArray subs = new JSONArray();
		btn2.put("name", "关于");
		btn2.put("sub_button", subs);

		JSONObject subbtn = new JSONObject();
		subbtn.put("type", "click");
		subbtn.put("name", "咨询服务");
		subbtn.put("key", "weixin-btns-zixun");
		subs.put(subbtn);

		buttons.put(btn0);
		buttons.put(btn1);
		buttons.put(btn2);

		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
		HttpPost httppost = new HttpPost(url);
		StringEntity s = new StringEntity(params.toString(),"UTF-8");  
        s.setContentEncoding("UTF-8");  
        s.setContentType("application/json");  
        //s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httppost.setEntity(s);
        HttpResponse res = httpclient.execute(httppost); 
		System.out.println(res);
	}

}
