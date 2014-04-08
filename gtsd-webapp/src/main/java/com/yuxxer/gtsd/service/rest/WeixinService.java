package com.yuxxer.gtsd.service.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.belerweb.social.weixin.bean.Article;
import com.belerweb.social.weixin.bean.Message;
import com.belerweb.social.weixin.bean.MsgType;
import com.homolo.framework.rest.ActionMethod;
import com.homolo.framework.rest.RestService;
import com.homolo.framework.rest.bind.annotation.ParameterVariable;
import com.yuxxer.gtsd.domain.Faq;
import com.yuxxer.gtsd.manager.FaqManager;
import com.yuxxer.gtsd.util.WeixinUtil;

@RestService
public class WeixinService {

	@Autowired
	private FaqManager faqManager;

	@ActionMethod(response = "json")
	public Object clickFaq(@ParameterVariable("openId") String openId,@ParameterVariable("originId") String originId) {
		List<Faq> faqs=faqManager.getAllFaqs();
		Message message=new Message(MsgType.NEWS);
		message.setFromUser(originId);
		message.setToUser(openId);
		message.setCreateTime(new Date());
		
		List<Article> articles=new ArrayList<Article>();
		for(Faq faq:faqs){
			Article article=new Article();
			article.setTitle(faq.getQuestion());
			article.setDescription(faq.getAnswer());
			//article.setUrl("");
			articles.add(article);
		}
		message.setArticles(articles);
		if(articles.size()>0){
			return WeixinUtil.sendFaqNews(message);
		}
		return null;
	}
}
