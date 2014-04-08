<%@ page language="java" pageEncoding="UTF-8"%><%@page 
import="java.io.*"
import="org.apache.commons.lang3.StringUtils"
import="java.util.ArrayList"
import="java.util.List"
import="org.dom4j.DocumentHelper"
import="org.dom4j.Element"
import="org.dom4j.Document"
import="java.util.Date"
import="com.homolo.framework.web.JspHelper"
import="com.yuxxer.gtsd.util.WeixinUtil"
import="com.yuxxer.gtsd.service.rest.WeixinService"
import="com.yuxxer.gtsd.Constants"
%><%
String echostr = request.getParameter("echostr");
if (echostr != null) {
	System.out.print(request.getParameter("echostr"));
	out.print(request.getParameter("echostr"));
	return;
}
String openId=null;//关注者的openId
String originalId=null;//公共号的originalId
JspHelper helper = new JspHelper(pageContext, "utf-8");
WeixinService weixinService=helper.getBean(WeixinService.class);
if (request.getMethod().equalsIgnoreCase("post")) {
	InputStream instreams = request.getInputStream();
	BufferedReader reader = new BufferedReader(new InputStreamReader(instreams, "utf-8"));
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
	String source = sb.toString();
	System.out.println(source);
	Document document = DocumentHelper.parseText(source);   
	Element toele=(Element)document.selectNodes("//ToUserName").get(0);//公众微信号
	Element fromele=(Element)document.selectNodes("//FromUserName").get(0);//普通微新用户openId
	openId=fromele.getText();
	originalId=toele.getText();
	Element msgtype=(Element)document.selectNodes("//MsgType").get(0);
	System.out.println(msgtype.getText());
	if(StringUtils.equals(msgtype.getText(),"text")){//接收文本消息
		Element contentele=(Element)document.selectNodes("//Content").get(0);
		String content=contentele.getText();
		String[] array=content.split(":");
		if(array!=null && array.length==2 && "kdcx".equals(array[0].toLowerCase())){
			String result=WeixinUtil.clickOrder(array[1], openId, originalId);
			out.print(result);
		}
		return;
	}
	else if(StringUtils.equals(msgtype.getText(),"image")){//接收图片消息
		
	}
	else if(StringUtils.equals(msgtype.getText(),"voice")){//接收语音消息
		
	}
	else if(StringUtils.equals(msgtype.getText(),"video")){//接收视频消息
		
	}
	else if(StringUtils.equals(msgtype.getText(),"location")){//接收地理位置消息
		
	}
	else if(StringUtils.equals(msgtype.getText(),"link")){//接收链接消息
		
	}
	else if(StringUtils.equals(msgtype.getText(),"event")){//接收事件消息
		Element event=(Element)document.selectNodes("//Event").get(0);
		if(StringUtils.equals(event.getText(),"subscribe")){//关注服务号事件
			StringBuffer buffer=new StringBuffer();
			buffer.append("<xml>");
			buffer.append("<ToUserName>");
			buffer.append("<![CDATA["+fromele.getText()+"]]>");
			buffer.append("</ToUserName>");
			buffer.append("<FromUserName>");
			buffer.append("<![CDATA["+toele.getText()+"]]>");
			buffer.append("</FromUserName>");
			buffer.append("<CreateTime>"+new Date().getTime()+"</CreateTime>");
			buffer.append("<MsgType><![CDATA[text]]></MsgType>");
			buffer.append("<Content>");
			buffer.append("<![CDATA[欢迎您的关注！]]>");
			buffer.append("</Content>");
			buffer.append("</xml>");
			out.print(buffer);
			return;
		}
		if(StringUtils.equals(event.getText(),"unsubscribe")){//取消关注公众号
			return;
		}
		if(StringUtils.equals(event.getText(),"CLICK")){//单击菜单事件
			Element btnkey=(Element)document.selectNodes("//EventKey").get(0);
			if(StringUtils.equals(btnkey.getText(), "weixin-btns-anhao")){
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
				buffer.append("<![CDATA[1.请照此格式输入【KDCX:运单号】\n2.<a href='"+Constants.SERVICE_HOST+"/weixin/search/index.jsp'>点击此处</a>]]>");
				buffer.append("</Content>");
				buffer.append("</xml>");
				out.print(buffer);
			}
			else if(StringUtils.equals(btnkey.getText(), "weixin-btns-news")){
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
				buffer.append("<![CDATA[欢迎使用网点查询!\n<a href='"+Constants.SERVICE_HOST+"/weixin/web/province.jsp'>点击此处</a>]]>");
				buffer.append("</Content>");
				buffer.append("</xml>");
				out.print(buffer);
			}
			else if(StringUtils.equals(btnkey.getText(), "weixin-btns-faq")){
				Object obj=weixinService.clickFaq(openId, originalId);
				if(obj!=null){
					out.print(obj.toString());
				}
			}
			return;
		}
	}
	
}
%>