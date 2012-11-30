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
	
	public Project add(Project project) throws SystemException, PortalException {
		
		//create new project
		Project p = projectPersistence.create(counterLocalService.increment(Project.class.getName()));
		
		//add resources
		resourceLocalService.addResources(p.getCompanyId(), p.getGroupId(), Project.class.getName(), false);
		
		//set project properties
		p.setName(project.getName());
		p.setType(project.getType());
		p.setDescShort(project.getDescShort());		
		p.setDescFull(project.getDescFull());
		p.setStartDate(project.getStartDate());
		p.setEndDate(project.getEndDate());
		p.setCostEstimatedEuros(project.getCostEstimatedEuros());
		p.setTimeEstimatedHours(project.getTimeEstimatedHours());
		p.setCanSetWorkerHours(project.getCanSetWorkerHours());
		p.setComments(project.getComments());
		p.setProjectStatusId(project.getProjectStatusId());
		p.setCreateDate(project.getCreateDate());
		
		//other properties
		p.setCompanyId(project.getCompanyId());
		p.setGroupId(project.getGroupId());
		
		return projectPersistence.update(p, false);
	}
	
	/* Worker related operations 
	 * (see ProjectPersistenceImpl.java to find more methods to add) 
	 */
	public List<Worker> getWorkers(long projectId) throws SystemException {
		return projectPersistence.getWorkers(projectId);
	}
	
	public void addWorker(long projectId, long workerId) throws SystemException {
		System.out.println("ProjectLocalServiceImpl addWorker "+workerId+" to project "+projectId);
		projectPersistence.addWorker(projectId, workerId);
	}
	
	public void addWorker(long projectId, Worker worker) throws SystemException {
		projectPersistence.addWorker(projectId, worker);
	}
	
	public void setWorkers(long projectId, List<Worker> workerList) throws SystemException {
		projectPersistence.setWorkers(projectId, workerList);
	}
	
	public void removeWorker(long projectId, long workerId) throws SystemException {
		projectPersistence.removeWorker(projectId, workerId);
	}
	public void removeWorker(long projectId, Worker worker) throws SystemException {
		projectPersistence.removeWorker(projectId, worker);
	}
	
	public int addProjectWorker(long projectId, long workerId) throws SystemException {
		//Bug LPS-29668 in liferay portal, dont uncomment until 6.2 (or 6.1.1 GA2 patched)
		//projectPersistence.addWorker(projectId, workerId);
		
		//Temporal alternative solution, instead line above, use finder method below
		System.out.println("ProjectLocalServiceImpl addProjectWorker "+workerId+" to project "+projectId);
		int res = ProjectFinderUtil.addProjectWorker(projectId, workerId);
		//refresh cache
		projectPersistence.clearCache();
		return res;
	}	
	
	public int delProjectWorker(long projectId, long workerId) throws SystemException {
		//Bug LPS-29668 in liferay portal, dont uncomment until 6.2 (or 6.1.1 GA2 patched)
		//projectPersistence.removeWorker(projectId, workerId);
		
		//Temporal alternative solution, instead line above, use finder method below		
		System.out.println("ProjectLocalServiceImpl delProjectWorker "+workerId+" from project "+projectId);
		int res = ProjectFinderUtil.delProjectWorker(projectId, workerId);
		//refresh cache
		projectPersistence.clearCache();
		return res;
	}
	
	public boolean containsWorker(long projectId, long workerId) throws SystemException {
		return projectPersistence.containsWorker(projectId, workerId);
	}
	
	public List<Worker> getProjectWorkers(long projectId) throws SystemException {
		return ProjectFinderUtil.getProjectWorkers(projectId);
	}
}