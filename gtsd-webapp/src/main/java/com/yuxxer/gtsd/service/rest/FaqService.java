package com.yuxxer.gtsd.service.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.homolo.framework.dao.util.PaginationSupport;
import com.homolo.framework.dao.util.Range;
import com.homolo.framework.dao.util.Sorter;
import com.homolo.framework.rest.ActionMethod;
import com.homolo.framework.rest.BaseDomainObjectServiceSupport;
import com.homolo.framework.rest.RequestParameters;
import com.homolo.framework.rest.RestService;
import com.yuxxer.gtsd.condition.FaqCondition;
import com.yuxxer.gtsd.domain.Faq;
import com.yuxxer.gtsd.manager.FaqManager;

@RestService
public class FaqService extends BaseDomainObjectServiceSupport<Faq> {


	@Autowired
	private FaqManager faqManager;

	@ActionMethod(response = "json")
	public Object query(RequestParameters reqParams, Range range, Sorter sorter) {
		if (sorter == null) {
			sorter = new Sorter().asc("index");
		}
		String partWord = (String) reqParams.getParameter("partWord");
		FaqCondition condition = new FaqCondition();
		condition.setPartWord(partWord);
		PaginationSupport<Faq> ps = faqManager.searchFaqs(condition, range, sorter);
		return ps;
	}
}
