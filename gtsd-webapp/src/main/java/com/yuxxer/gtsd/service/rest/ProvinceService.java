package com.yuxxer.gtsd.service.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.homolo.framework.dao.util.PaginationSupport;
import com.homolo.framework.dao.util.Range;
import com.homolo.framework.dao.util.Sorter;
import com.homolo.framework.rest.ActionMethod;
import com.homolo.framework.rest.BaseDomainObjectServiceSupport;
import com.homolo.framework.rest.RequestParameters;
import com.homolo.framework.rest.RestService;
import com.yuxxer.gtsd.condition.ProvinceCondition;
import com.yuxxer.gtsd.domain.Province;
import com.yuxxer.gtsd.manager.ProvinceManager;

@RestService
public class ProvinceService extends BaseDomainObjectServiceSupport<Province> {


	@Autowired
	private ProvinceManager provinceManager;

	@ActionMethod(response = "json")
	public Object query(RequestParameters reqParams, Range range, Sorter sorter) {
		if (sorter == null) {
			sorter = new Sorter().asc("name");
		}
		String partWord = (String) reqParams.getParameter("partWord");
		ProvinceCondition condition = new ProvinceCondition();
		condition.setName(partWord);
		PaginationSupport<Province> ps = provinceManager.searchProvinces(condition, range, sorter);
		return ps;
	}
}
