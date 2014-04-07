package com.yuxxer.gtsd.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.homolo.framework.annotation.DomainEngine;
import com.homolo.framework.dao.DomainObjectDao;
import com.homolo.framework.dao.util.PaginationSupport;
import com.homolo.framework.dao.util.Range;
import com.homolo.framework.dao.util.Sorter;
import com.homolo.framework.events.EventMethod;
import com.homolo.framework.util.UUID;
import com.yuxxer.gtsd.ProjectConfig;
import com.yuxxer.gtsd.condition.ZoomCondition;
import com.yuxxer.gtsd.domain.Zoom;

@DomainEngine(types = Zoom.class)
@Transactional(readOnly = true)
public class ZoomManager {

	@Resource(name = ProjectConfig.NAME+".zoomDao")
	private DomainObjectDao<Zoom> zoomDao;

	@EventMethod
	@DomainEngine.C(type = Zoom.class)
	@Transactional
	public String createZoom(Zoom object) {
		if (object.getId() == null) {
			object.setId(UUID.generateUUID());
		}
		zoomDao.createObject(object);
		return object.getId();
	}

	@EventMethod
	@DomainEngine.U(type = Zoom.class)
	@Transactional
	public void updateZoom(Zoom object) {
		zoomDao.updateObject(object);
	}

	@EventMethod
	@DomainEngine.D(type = Zoom.class)
	@Transactional
	public void deleteZoom(Zoom object) {
		if (object != null) {
			zoomDao.deleteObject(object);
		}
	}

	@EventMethod("deleteZoom")
	@Transactional
	public void deleteZoom(String id) {
		deleteZoom(getZoom(id));
	}

	@DomainEngine.R(type = Zoom.class)
	public Zoom getZoom(String id) {
		return zoomDao.loadObject(id);
	}

	public List<Zoom> getAllZooms() {
		return zoomDao.findAll();
	}

	@EventMethod("deleteallZoom")
	@Transactional
	public void deleteAllZooms() {
		zoomDao.deleteAll();
	}
	
	public int count(ZoomCondition condition) {
		return zoomDao.countByCondition(condition);
	}
	
	public PaginationSupport<Zoom> searchZooms(ZoomCondition condtion,
			Range range, Sorter sorter) {
		return zoomDao.findByCondition(condtion, range, sorter);
	}
	public List<Zoom> list(ZoomCondition condition) {
		return zoomDao.findAllByCondition(condition);
	}
}