
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


import java.util.List;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.mpwc.model.Project;
import com.mpwc.model.Worker;
import com.mpwc.model.impl.ProjectImpl;
import com.mpwc.model.impl.WorkerImpl;

public class WorkerFinderImpl extends BasePersistenceImpl implements WorkerFinder {

	// the name of the query
	public static String FIND_WORKERS_BY_NAME = WorkerFinderImpl.class.getName()+ ".getWorkersByName";
	public static String FIND_WORKERS_BY_STATUS_DESC = WorkerFinderImpl.class.getName()+ ".getWorkersByStatusDesc";
	public static String FIND_WORKERS_BY_FILTERS = WorkerFinderImpl.class.getName()+ ".getWorkersByFilters";
	public static String FIND_PROJECTS_BY_WORKER = WorkerFinderImpl.class.getName()+ ".getProjects";
	
	public List<Worker> getWorkersByName(String name) throws SystemException {
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_WORKERS_BY_NAME);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Worker", WorkerImpl.class);
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add(name);
			 return (List)query.list();
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
	public List<Worker> getWorkersByStatusDesc(String desc) throws SystemException {
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_WORKERS_BY_STATUS_DESC);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Worker", WorkerImpl.class);
			 //query.addEntity("Status", StatusImpl.class);
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
	
	public List<Worker> getWorkersByFilters(String desc, String nif, String name, String surname, String email, String phone) throws SystemException {
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_WORKERS_BY_FILTERS);
			 //replace logical operators
			 sql = CustomSQLUtil.replaceAndOperator(sql, true);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Worker", WorkerImpl.class);
			 //add parameters
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add("%"+desc+"%");
			 qPos.add("%"+desc+"%");
			 qPos.add("%"+desc+"%");
			 qPos.add(desc);
			 qPos.add("%"+nif+"%");
			 qPos.add(nif);
			 qPos.add("%"+name+"%");
			 qPos.add(name);
			 qPos.add("%"+surname+"%");
			 qPos.add(surname);
			 qPos.add("%"+email+"%");
			 qPos.add(email);
			 qPos.add("%"+phone+"%");
			 qPos.add(phone);
			 System.out.println("WorkerFinderImpl getWorkersByFilters sql"+ sql.toString() +" *** qPos ->"+ qPos.toString() );
			 return (List)query.list();
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
	public List<Project> getProjects(long workerId) throws SystemException {
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_PROJECTS_BY_WORKER);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Project", ProjectImpl.class);
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add(workerId);
			 return (List)query.list();
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
}