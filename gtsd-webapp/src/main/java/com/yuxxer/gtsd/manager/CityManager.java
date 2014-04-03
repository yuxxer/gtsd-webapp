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
import com.yuxxer.gtsd.condition.CityCondition;
import com.yuxxer.gtsd.domain.City;

@DomainEngine(types = City.class)
@Transactional(readOnly = true)
public class CityManager {

	@Resource(name = ProjectConfig.NAME+".cityDao")
	private DomainObjectDao<City> cityDao;

	@EventMethod
	@DomainEngine.C(type = City.class)
	@Transactional
	public String createCity(City object) {
		if (object.getId() == null) {
			object.setId(UUID.generateUUID());
		}
		cityDao.createObject(object);
		return object.getId();
	}

	@EventMethod
	@DomainEngine.U(type = City.class)
	@Transactional
	public void updateCity(City object) {
		cityDao.updateObject(object);
	}

	@EventMethod
	@DomainEngine.D(type = City.class)
	@Transactional
	public void deleteCity(City object) {
		if (object != null) {
			cityDao.deleteObject(object);
		}
	}

	@EventMethod("deleteCity")
	@Transactional
	public void deleteCity(String id) {
		deleteCity(getCity(id));
	}

	@DomainEngine.R(type = City.class)
	public City getCity(String id) {
		return cityDao.loadObject(id);
	}

	public List<City> getAllCitys() {
		return cityDao.findAll();
	}

	@EventMethod("deleteallCity")
	@Transactional
	public void deleteAllCitys() {
		cityDao.deleteAll();
	}
	
	public int count(CityCondition condition) {
		return cityDao.countByCondition(condition);
	}
	
	public PaginationSupport<City> searchCitys(CityCondition condtion,
			Range range, Sorter sorter) {
		return cityDao.findByCondition(condtion, range, sorter);
	}
	
	public List<City> list(CityCondition condition) {
		return cityDao.findAllByCondition(condition);
	}
}