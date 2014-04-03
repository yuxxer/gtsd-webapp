package com.yuxxer.gtsd.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.homolo.framework.dao.util.PaginationSupport;
import com.homolo.framework.dao.util.Range;
import com.homolo.framework.dao.util.Sorter;
import com.homolo.framework.rest.ActionMethod;
import com.homolo.framework.rest.BaseDomainObjectServiceSupport;
import com.homolo.framework.rest.RequestParameters;
import com.homolo.framework.rest.RestService;
import com.yuxxer.gtsd.condition.CityCondition;
import com.yuxxer.gtsd.domain.City;
import com.yuxxer.gtsd.manager.CityManager;

import eu.medsea.util.StringUtil;

@RestService
public class CityService extends BaseDomainObjectServiceSupport<City> {


	@Autowired
	private CityManager cityManager;

	@ActionMethod(response = "json")
	public Object query(RequestParameters reqParams, Range range, Sorter sorter) {
		if (sorter == null) {
			sorter = new Sorter().asc("name");
		}
		String partWord = (String) reqParams.getParameter("partWord");
		CityCondition condition = new CityCondition();
		condition.setName(partWord);
		PaginationSupport<City> ps = cityManager.searchCitys(condition, range, sorter);
		return ps;
	}
	
	@ActionMethod(response = "json")
	public Object listbyProvince(RequestParameters params) {
		String provinceId=params.getParameter("provinceId", String.class);
		List<City> list=new ArrayList<>();
		if(StringUtils.isNotBlank(provinceId)){
			CityCondition condition=new CityCondition();
			condition.setProvinceId(provinceId);
			list=cityManager.list(condition);
		}
		return list;
	}
}
