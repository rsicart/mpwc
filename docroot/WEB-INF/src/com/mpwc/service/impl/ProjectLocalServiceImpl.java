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
import com.mpwc.model.Project;
import com.mpwc.model.Worker;
import com.mpwc.service.base.ProjectLocalServiceBaseImpl;
import com.mpwc.service.persistence.ProjectFinderUtil;

/**
 * The implementation of the project local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.mpwc.service.ProjectLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author rsicart
 * @see com.mpwc.service.base.ProjectLocalServiceBaseImpl
 * @see com.mpwc.service.ProjectLocalServiceUtil
 */
public class ProjectLocalServiceImpl extends ProjectLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.mpwc.service.ProjectLocalServiceUtil} to access the project local service.
	 */
	public List<Project> getProjectsByName(String name) throws PortalException,
	SystemException {

		// return bookPersistence.findByTitle(title);
		return ProjectFinderUtil.getProjectsByName("%" + name + "%");
	}
	
	public List<Project> getProjectsByStatusDesc(String desc) throws PortalException,
	SystemException {

		// return bookPersistence.findByTitle(title);
		return ProjectFinderUtil.getProjectsByStatusDesc("%" + desc + "%");
	}
	
	public List<Project> getProjectsByFilters(String desc, String name, String type, String descShort,
			Date startDate, Date endDate, Double costEstimatedeuros, long timeEstimatedHours, 
			boolean canSetWorkerHours, String comments) throws SystemException {
		return ProjectFinderUtil.getProjectsByFilters(desc, name, type, descShort,
				startDate, endDate, costEstimatedeuros, timeEstimatedHours, 
				canSetWorkerHours, comments);
	}
	
}