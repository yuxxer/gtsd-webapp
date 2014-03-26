package com.yuxxer.gtsd.service.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.homolo.framework.dao.util.PaginationSupport;
import com.homolo.framework.dao.util.Range;
import com.homolo.framework.dao.util.Sorter;
import com.homolo.framework.rest.ActionMethod;
import com.homolo.framework.rest.BaseDomainObjectServiceSupport;
import com.homolo.framework.rest.RequestParameters;
import com.homolo.framework.rest.RestService;
import com.yuxxer.gtsd.condition.ZoomCondition;
import com.yuxxer.gtsd.domain.Zoom;
import com.yuxxer.gtsd.manager.ZoomManager;

@RestService
public class ZoomService extends BaseDomainObjectServiceSupport<Zoom> {


	@Autowired
	private ZoomManager zoomManager;

	@ActionMethod(response = "json")
	public Object query(RequestParameters reqParams, Range range, Sorter sorter) {
		if (sorter == null) {
			sorter = new Sorter().asc("name");
		}
		String partWord = (String) reqParams.getParameter("partWord");
		ZoomCondition condition = new ZoomCondition();
		condition.setName(partWord);
		PaginationSupport<Zoom> ps = zoomManager.searchZooms(condition, range, sorter);
		return ps;
	}
}
