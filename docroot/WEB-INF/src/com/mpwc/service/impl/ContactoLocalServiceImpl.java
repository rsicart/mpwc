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

import java.util.List;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourceConstants;
import com.mpwc.NoSuchWorkerException;
import com.mpwc.model.Contacto;
import com.mpwc.model.Project;
import com.mpwc.service.base.ContactoLocalServiceBaseImpl;
import com.mpwc.service.persistence.ContactoFinderUtil;


/**
 * The implementation of the contacto local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.mpwc.service.ContactoLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author rsicart
 * @see com.mpwc.service.base.ContactoLocalServiceBaseImpl
 * @see com.mpwc.service.ContactoLocalServiceUtil
 */
public class ContactoLocalServiceImpl extends ContactoLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.mpwc.service.ContactoLocalServiceUtil} to access the contacto local service.
	 */
	public Contacto addContact(Contacto contacto) throws PortalException, SystemException {
		//create new contacto
		Contacto c = contactoPersistence.create(counterLocalService.increment(Contacto.class.getName()));
		
		//add resources
		resourceLocalService.addResources(c.getCompanyId(), c.getGroupId(), Contacto.class.getName(), false);
		
		//set contacto properties
		c.setNif(contacto.getNif());
		c.setEmail(contacto.getEmail());
		c.setFirmname(contacto.getFirmname());
		c.setPhone(contacto.getPhone());
		c.setComments(contacto.getComments());
		c.setContactoStatusId(contacto.getContactoStatusId());
		c.setCity(contacto.getCity());
		c.setCountry(contacto.getCountry());
		c.setAddress(contacto.getAddress());
		c.setZipcode(contacto.getZipcode());
		c.setCreateDate(contacto.getCreateDate());
		
		c.setUserName(contacto.getUserName());
		c.setCompanyId(contacto.getCompanyId());
		c.setGroupId(contacto.getGroupId());
		
		return contactoPersistence.update(c, false);
	}
	
	public Contacto deleteContact(long contactoId) throws NoSuchWorkerException, PortalException, SystemException {
		Contacto c = contactoPersistence.findByPrimaryKey(contactoId);	
		return deleteContact(c);
	}
	
	public Contacto deleteContact(Contacto contacto) throws SystemException {		
		try {
			resourceLocalService.deleteResource(contacto.getCompanyId(), Contacto.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, contacto.getPrimaryKey());
		} catch (PortalException e) {
			e.printStackTrace();
		}		
		return contactoPersistence.remove(contacto);
	}
	
	public List<Contacto> getContactosByFilters(String desc, String nif, String firmname, String email, String phone, String city, String country, String address, String zipcode, String ctype) throws PortalException, SystemException {
		return ContactoFinderUtil.getContactosByFilters(desc, nif, firmname, email, phone, city, country, address, zipcode, ctype);
	}
	
	public List<Project> getProjects(long contactoId) throws SystemException {
			return contactoPersistence.getProjects(contactoId);
	}
}