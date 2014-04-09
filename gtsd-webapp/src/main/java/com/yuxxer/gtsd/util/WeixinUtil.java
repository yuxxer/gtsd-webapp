package com.yuxxer.gtsd.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.belerweb.social.weixin.bean.Article;
import com.belerweb.social.weixin.bean.Message;

/*
 * 订单查询
 */
public abstract class WeixinUtil {
	/**
	 * 通过订单号来查询快递单跟踪记录
	 * 
	 * @param orderId
	 * @param openId
	 * @param originalId
	 * @return
	 * @throws JSONException 
	 */
	public static String clickOrder(String orderId,String openId,String originalId) throws JSONException {
		JSONArray array=getRecordByOrderId(orderId);
		StringBuffer buffer=new StringBuffer();
		buffer.append("<xml>");
		buffer.append("<ToUserName>");
		buffer.append("<![CDATA["+openId+"]]>");
		buffer.append("</ToUserName>");
		buffer.append("<FromUserName>");
		buffer.append("<![CDATA["+originalId+"]]>");
		buffer.append("</FromUserName>");
		buffer.append("<CreateTime>"+new Date().getTime()+"</CreateTime>");
		buffer.append("<MsgType><![CDATA[text]]></MsgType>");
		buffer.append("<Content>");
		String result="";
		if(array.length()>0){
			for(int i=0;i<array.length();i++){
				JSONObject obj=(JSONObject)array.get(i);
				if(i>0){
					result=result+"\n";
				}
				result=result+obj.getString("time")+"\n";
				result=result+"\n"+obj.getString("location");
			}
		}else{
			result="暂无跟踪记录！！！";
		}
		buffer.append("<![CDATA["+result+"]]>");
		buffer.append("</Content>");
		buffer.append("</xml>");
		return buffer.toString();
	}
	/**
	 */
	public static String sendFaqNews(Message message){
		StringBuffer buffer=new StringBuffer();
		buffer.append("<xml>");
		buffer.append("<ToUserName>");
		buffer.append("<![CDATA["+message.getToUser()+"]]>");
		buffer.append("</ToUserName>");
		buffer.append("<FromUserName>");
		buffer.append("<![CDATA["+message.getFromUser()+"]]>");
		buffer.append("</FromUserName>");
		buffer.append("<CreateTime>"+message.getCreateTime()+"</CreateTime>");
		buffer.append("<MsgType><![CDATA[news]]></MsgType>");
		
		buffer.append("<ArticleCount>"+(message.getArticles().size()<=9?message.getArticles().size()+1:10)+"</ArticleCount>");
		buffer.append("<Articles>");

		buffer.append("<item>");
		buffer.append("<Title><![CDATA[高铁速递常见问题]]></Title>");
		buffer.append("<Description><![CDATA[高铁速递常见问题]]></Description>");
		buffer.append("<PicUrl><![CDATA[http://115.29.224.212/gtsd/images/faq.png]]></PicUrl>");
		buffer.append("</item>");
		int count=0;
		for(Article a:message.getArticles()){
			if(count<10){
				buffer.append("<item>");
				buffer.append("<Title><![CDATA["+a.getTitle()+"]]></Title>");
				buffer.append("<Description><![CDATA["+a.getDescription()+"]]></Description>");
				//buffer.append("<Url><![CDATA["+a.getUrl()+"]]></Url>");
				buffer.append("</item>");
				count ++;
			}
		}
		buffer.append("</Articles>");
		buffer.append("</xml>");
		return buffer.toString();
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	public static JSONArray getRecordByOrderId(String orderId) throws JSONException {
		JSONArray jsonArray=new JSONArray();
		
		HttpClient httpclient = new org.apache.http.impl.client.DefaultHttpClient();

		HttpPost request = new HttpPost("http://117.25.131.6/track.asp");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("ID", orderId));
		HttpEntity httpEntity = null;
		try {
			httpEntity = new UrlEncodedFormEntity(nvps, "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setEntity(httpEntity);
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String html = null;
			try {
				html = EntityUtils.toString(httpResponse.getEntity(), "gb2312");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			html = html.replaceAll("\\r?\\n", "");// 剔除换行符号
			try {
				// 解析返回页面的table标签，获得第二个table标签中的html内容
				Parser parser = Parser.createParser(html, "UTF-8");
				NodeFilter tableFilter = new TagNameFilter("TABLE");
				NodeList tableNodes = parser
						.extractAllNodesThatMatch(tableFilter);
				if (tableNodes != null && tableNodes.size() > 1) {
					Node tableNode = (Node) tableNodes.elementAt(1);
					Parser trParser = Parser.createParser(tableNode
							.getChildren().toHtml(), "UTF-8");
					// 获得table下的tr标签中的html内容
					NodeFilter trFilter = new TagNameFilter("TR");
					NodeList trNodes = trParser
							.extractAllNodesThatMatch(trFilter);
					if (trNodes != null && trNodes.size() > 1) {
						for (int i = 1; i < trNodes.size(); i++) {
							JSONObject object=new JSONObject();
							
							Node trNode = (Node) trNodes.elementAt(i);
							Parser tdParser = Parser.createParser(trNode
									.getChildren().toHtml(), "UTF-8");
							// 获得tr下的td标签中的html内容
							NodeFilter tdFilter = new TagNameFilter("TD");
							NodeList tdNodes = tdParser.extractAllNodesThatMatch(tdFilter);
							if(tdNodes!=null && tdNodes.size()==2){
								Node timeNode = tdNodes.elementAt(0);
								Node locationNode = tdNodes.elementAt(1);
								object.put("time", timeNode.toPlainTextString());
								String location =locationNode.toPlainTextString();
								location=location.replaceAll("\t", "");
								location=location.replaceAll(" ", "");
								object.put("location", location);
							}
							jsonArray.put(object);
						}
					}
				}
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
		return jsonArray;
	}
}
