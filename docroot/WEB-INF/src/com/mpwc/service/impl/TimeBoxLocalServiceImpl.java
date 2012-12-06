/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.mpwc.service.impl;

import java.util.Date;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceConstants;
import com.mpwc.model.Project;
import com.mpwc.model.TimeBox;
import com.mpwc.service.base.TimeBoxLocalServiceBaseImpl;

/**
 * The implementation of the time box local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.mpwc.service.TimeBoxLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author rsicart
 * @see com.mpwc.service.base.TimeBoxLocalServiceBaseImpl
 * @see com.mpwc.service.TimeBoxLocalServiceUtil
 */
public class TimeBoxLocalServiceImpl extends TimeBoxLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.mpwc.service.TimeBoxLocalServiceUtil} to access the time box local service.
	 */
	
	public TimeBox add(TimeBox timeBox) throws SystemException, PortalException {
		
		//create new project
		TimeBox tb = timeBoxPersistence.create(counterLocalService.increment(TimeBox.class.getName()));
		
		//add resources
		resourceLocalService.addResources(tb.getCompanyId(), tb.getGroupId(), TimeBox.class.getName(), false);
		
		//set project properties
		tb.setProjectId(timeBox.getProjectId());
		tb.setWorkerId(timeBox.getWorkerId());
		tb.setMinutes(timeBox.getMinutes());
		tb.setComments(timeBox.getComments());
		tb.setDedicationDate(timeBox.getDedicationDate());
		tb.setCreateDate(timeBox.getCreateDate());
		tb.setModifiedDate(timeBox.getModifiedDate());
		
		//other properties
		tb.setCompanyId(timeBox.getCompanyId());
		tb.setGroupId(timeBox.getGroupId());
		
		return timeBoxPersistence.update(tb, false);
	}
	
	
	public long delete(long timeBoxId) throws SystemException, PortalException {
		TimeBox tb = timeBoxPersistence.findByPrimaryKey(timeBoxId);
	    return delete(tb);
	}
	
	public long delete(TimeBox timeBox) throws SystemException, PortalException {
        resourceLocalService.deleteResource(timeBox.getCompanyId(), TimeBox.class.getName(),ResourceConstants.SCOPE_INDIVIDUAL, timeBox.getPrimaryKey());
        timeBoxPersistence.remove(timeBox);
	    return timeBox.getTimeboxId();
	}
	
	public List<TimeBox> findByWorker(long workerId) throws SystemException, PortalException {
		return timeBoxPersistence.findByW(workerId);
	}
	
	public List<TimeBox> findByProjectWorker(long projectId, long workerId) throws SystemException, PortalException {
		return timeBoxPersistence.findByP_W(projectId, workerId);
	}
	
	public List<TimeBox> findByProject(long projectId) throws SystemException, PortalException {
		return timeBoxPersistence.findByP(projectId);
	}
	
	public List<TimeBox> findByProjectWorkerDDate(long projectId, long workerId, Date dedicationDate) throws SystemException, PortalException {
		return timeBoxPersistence.findByP_W_DD(projectId, workerId, dedicationDate);
	}
	
	public long totalizeTimeboxByProjectWorker(long projectId, long workerId) throws SystemException, PortalException{
		long total = 0;
		List<TimeBox> ltb = findByProjectWorker(projectId, workerId);
		for(TimeBox tb : ltb){
			total += tb.getMinutes();
		}
		return total;
	}
	
}