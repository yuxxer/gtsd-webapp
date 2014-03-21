/**
 * Project:wizlong-sgmels-webapp
 * File:EventDispatcher.java
 * Copyright 2004-2013 Homolo Co., Ltd. All rights reserved.
 */
package com.yuxxer.gtsd.aop;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.homolo.framework.aop.AopMethodEventDispatcher;
import com.homolo.framework.events.EventTarget;
import com.yuxxer.gtsd.ProjectConfig;

/**
 * @author zhanzy
 * @version $Id$
 */
@Aspect
@Component
public class EventDispatcher extends AopMethodEventDispatcher {

	@Resource(name=ProjectConfig.PREFIX+"eventTarget")
	private EventTarget eventTarget;

	public EventTarget getEventTarget() {
		return eventTarget;
	}

	@Around("execution(* com.yuxxer.gtsd.manager.*.*(..))")
	public Object around0(ProceedingJoinPoint pjp) throws Throwable {
		return super.proceePointcut(pjp);
	}

}
