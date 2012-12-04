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

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.mpwc.NoSuchWorkerException;
import com.mpwc.model.Project;
import com.mpwc.model.TimeBox;
import com.mpwc.model.Worker;
import com.mpwc.service.WorkerLocalServiceUtil;
import com.mpwc.service.base.WorkerLocalServiceBaseImpl;
import com.mpwc.service.persistence.WorkerFinderUtil;
import com.mpwc.service.persistence.WorkerUtil;

/**
 * The implementation of the worker local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.mpwc.service.WorkerLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author rsicart
 * @see com.mpwc.service.base.WorkerLocalServiceBaseImpl
 * @see com.mpwc.service.WorkerLocalServiceUtil
 */
public class WorkerLocalServiceImpl extends WorkerLocalServiceBaseImpl {
	
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.mpwc.service.WorkerLocalServiceUtil} to access the worker local service.
	 */
	
	@Override
	public List<Worker> findByStatus(long status) throws PortalException,
			SystemException {

		return WorkerLocalServiceUtil.findByStatus(status);
	}
	
	
	public List<Worker> getWorkersByName(String name) throws PortalException,
	SystemException {

		// return bookPersistence.findByTitle(title);
		return WorkerFinderUtil.getWorkersByName("%" + name + "%");
	}
	
	public List<Worker> getWorkersByStatusDesc(String desc) throws PortalException,
	SystemException {

		// return bookPersistence.findByTitle(title);
		return WorkerFinderUtil.getWorkersByStatusDesc("%" + desc + "%");
	}
	
	public List<Worker> getWorkersByFilters(String desc, String nif, String name, String surname, String email, String phone) throws PortalException, SystemException {
		return WorkerFinderUtil.getWorkersByFilters(desc, nif, name, surname, email, phone);
	}

	public Worker addWorker(Worker worker, long userId) throws PortalException, SystemException {
		//create new worker
		Worker w = workerPersistence.create(counterLocalService.increment(Worker.class.getName()));
		
		//add resources
		resourceLocalService.addResources(w.getCompanyId(), w.getGroupId(), Worker.class.getName(), false);
		
		//set worker properties
		w.setNif(worker.getNif());
		w.setEmail(worker.getEmail());
		w.setName(worker.getName());
		w.setSurname(worker.getSurname());
		w.setPhone(worker.getPhone());
		w.setComments(worker.getComments());
		w.setStatusId(worker.getStatusId());
		w.setCreateDate(worker.getCreateDate());
		
		//set worker properties linked to liferay user properties
		w.setUserName(worker.getUserName());
		w.setCompanyId(worker.getCompanyId());
		w.setGroupId(worker.getGroupId());
		w.setUserId(userId);
		
		//add role MpwcUser
		Role mpwcUserRole = RoleLocalServiceUtil.getDefaultGroupRole(w.getGroupId());
		mpwcUserRole = RoleLocalServiceUtil.getRole(w.getCompanyId(), "MpwcUser");
		long roleId = mpwcUserRole.getRoleId();
		long[] userIds = new long[1];
		userIds[0] = userId;
		UserLocalServiceUtil.addRoleUsers(roleId, userIds);
		
		//add user to organization
		Group group = GroupLocalServiceUtil.getGroup(w.getGroupId());
		if(group.isOrganization()){
			UserLocalServiceUtil.addOrganizationUsers(group.getOrganizationId(), userIds);
			System.out.println("WorkerLocalServiceImpl addWorker: addin user to organization"+group.getOrganizationId());
		}
		
		return workerPersistence.update(w, false);
	}
	
	public Worker deleteWorker(long workerId) throws NoSuchWorkerException, PortalException, SystemException {
		Worker w = workerPersistence.findByPrimaryKey(workerId);	
		return deleteWorker(w);
	}
	
	public Worker deleteWorker(Worker worker) throws SystemException {		
		try {
			resourceLocalService.deleteResource(worker.getCompanyId(), Worker.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, worker.getPrimaryKey());
		} catch (PortalException e) {
			e.printStackTrace();
		}		
		return workerPersistence.remove(worker);
	}	
	
	public List<Worker> findByG_U(long groupId, long userId) throws SystemException{
		return WorkerUtil.findByG_U(groupId, userId);
	}
	
	public Worker findByG_U_First(long groupId, long userId,
			OrderByComparator orderByComparator)
			throws NoSuchWorkerException, SystemException {
		return workerPersistence.findByG_U_First(groupId, userId, orderByComparator);
	}
	
	public List<Project> getProjects(long workerId) throws SystemException{
		//return workerPersistence.getProjects(workerId);
		return WorkerFinderUtil.getProjects(workerId);
	}
	
	public List<TimeBox> getTimeBoxs(long projectId) throws SystemException {
		return projectPersistence.getTimeBoxs(projectId);
	}
}