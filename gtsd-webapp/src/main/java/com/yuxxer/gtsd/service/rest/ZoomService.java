package com.yuxxer.gtsd.service.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
	
	
	@ActionMethod(response = "json")
	public Object datatableselect(RequestParameters params) {
		Sorter sorter = new Sorter().asc("title");
		String sEcho = params.getParameter("sEcho", String.class);
		Integer start = params.getParameter("iDisplayStart", Integer.class);
		Integer size = params.getParameter("iDisplayLength", Integer.class);
		String partWord = params.getParameter("sSearch", String.class);
		if (start == null || start < 0) {
			start = 0;
		}
		if (size == null || size < 10) {
			size = 10;
		}
		Range range = new Range(start, size);
		ZoomCondition condition = new ZoomCondition();
		condition.setName(partWord);
		PaginationSupport<Zoom> ps = zoomManager.searchZooms(condition, range, sorter);
		JSONObject json = null;
		try {
			json = new JSONObject();
			json.put("sEcho", sEcho);
			json.put("iTotalRecords", zoomManager.count(new ZoomCondition()));
			json.put("iTotalDisplayRecords", ps.getTotalCount());
			JSONArray jsonArray = new JSONArray();
			for (Zoom zoom : ps.getItems()) {
				JSONArray dataArray = new JSONArray();
				dataArray.put("<input type=\"checkbox\" name=\"checkbox\" value=\"" + zoom.getId() + "\"/><input type=\"hidden\" id=\"name_" + zoom.getId() + "\" value=\"" + zoom.getName() + "\"/>");
				dataArray.put(zoom.getName());
				dataArray.put(zoom.getDescription());
				jsonArray.put(dataArray);
			}
			json.put("aaData", jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
