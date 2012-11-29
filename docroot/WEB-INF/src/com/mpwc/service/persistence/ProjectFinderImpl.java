
/*

Copyright (c) 2012 Roger Sicart. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    (1) Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer. 

    (2) Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in
    the documentation and/or other materials provided with the
    distribution.  
    
    (3)The name of the author may not be used to
    endorse or promote products derived from this software without
    specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 
 */

/*
 * @author R.Sicart
 */

package com.mpwc.service.persistence;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.mpwc.model.Project;
import com.mpwc.model.impl.ProjectImpl;

public class ProjectFinderImpl extends BasePersistenceImpl implements ProjectFinder {

	// the name of the query
	public static String FIND_PROJECTS_BY_NAME = ProjectFinderImpl.class.getName()+ ".getProjectsByName";
	public static String FIND_PROJECTS_BY_STATUS_DESC = ProjectFinderImpl.class.getName()+ ".getProjectsByStatusDesc";
	public static String FIND_PROJECTS_BY_FILTERS = ProjectFinderImpl.class.getName()+ ".getProjectsByFilters";
	public static String ADD_PROJECT_WORKER = ProjectFinderImpl.class.getName()+ ".addProjectWorker";
	public static String DEL_PROJECT_WORKER = ProjectFinderImpl.class.getName()+ ".delProjectWorker";
	
	public List<Project> getProjectsByName(String name) throws SystemException {
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_PROJECTS_BY_NAME);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Project", ProjectImpl.class);
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add(name);
			 return (List)query.list();
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
	public List<Project> getProjectsByStatusDesc(String desc) throws SystemException {
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_PROJECTS_BY_STATUS_DESC);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Project", ProjectImpl.class);
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add(desc);
			 qPos.add(desc);
			 qPos.add(desc);
			 return (List)query.list();
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
	public List<Project> getProjectsByFilters(String desc, String name, String type, String descShort, 
			Date startDate, Date endDate, Double costEstimatedEuros, long timeEstimatedHours, 
			boolean canSetWorkerHours, String comments) throws SystemException {
		 
		Session session = null;
		 
		 Timestamp startDateTs = CalendarUtil.getTimestamp(startDate);
		 Timestamp endDateTs = CalendarUtil.getTimestamp(endDate);
		 
		 System.out.println("ProjectFinder getProjectsByFilters start TS:"+startDateTs+" - end TS: "+endDateTs);
		 
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_PROJECTS_BY_FILTERS);
			 //replace logical operators
			 sql = CustomSQLUtil.replaceAndOperator(sql, true);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Project", ProjectImpl.class);
			 //add parameters
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add("%"+desc+"%");
			 qPos.add("%"+desc+"%");
			 qPos.add("%"+desc+"%");
			 qPos.add(desc);
			 qPos.add("%"+name+"%");
			 qPos.add(name);
			 qPos.add("%"+type+"%");
			 qPos.add(type);
			 qPos.add("%"+descShort+"%");
			 qPos.add(descShort);
			 /*
			 qPos.add(startDate);
			 qPos.add(startDate);
			 qPos.add(endDate);
			 qPos.add(endDate);
			 */
			 qPos.add(startDateTs);
			 qPos.add(startDateTs);
			 qPos.add(endDateTs);
			 qPos.add(endDateTs);
			 qPos.add(costEstimatedEuros);
			 qPos.add(costEstimatedEuros);
			 qPos.add(timeEstimatedHours);
			 qPos.add(timeEstimatedHours);
			 qPos.add(canSetWorkerHours);
			 qPos.add(canSetWorkerHours);
			 qPos.add("%"+comments+"%");
			 qPos.add(comments);
			 System.out.println("ProjectFinderImpl getProjectsByFilters sql"+ sql.toString() +" *** query ->");
			 return (List)query.list();
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
	public int addProjectWorker(long projectId, long workerId) throws SystemException{
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(ADD_PROJECT_WORKER);
			 SQLQuery query = session.createSQLQuery(sql);
			 //query.addEntity("Project", ProjectImpl.class);
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add(workerId);
			 qPos.add(projectId);
			 int res = query.executeUpdate();
			 System.out.println("ProjectFinderImpl addProjectWorker sql"+ sql.toString() +" *** result ->"+res);
			 return res;
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
	public int delProjectWorker(long projectId, long workerId) throws SystemException{
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(DEL_PROJECT_WORKER);
			 SQLQuery query = session.createSQLQuery(sql);
			 //query.addEntity("Project", ProjectImpl.class);
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add(workerId);
			 qPos.add(projectId);
			 int res = query.executeUpdate();
			 System.out.println("ProjectFinderImpl delProjectWorker sql"+ sql.toString() +" *** result ->"+res);
			 return res;
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
}