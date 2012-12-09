
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
import com.mpwc.model.Contacto;
import com.mpwc.model.impl.ContactoImpl;


public class ContactoFinderImpl extends BasePersistenceImpl implements ContactoFinder {

	// the name of the query
	public static String FIND_CONTACTOS_BY_STATUS_DESC = ContactoFinderImpl.class.getName()+ ".getContactosByStatusDesc";
	public static String FIND_CONTACTOS_BY_FILTERS = ContactoFinderImpl.class.getName()+ ".getContactosByFilters";
	
	public List<Contacto> getContactosByStatusDesc(String desc) throws SystemException {
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_CONTACTOS_BY_STATUS_DESC);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Contacto", ContactoImpl.class);
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
	
	public List<Contacto> getContactosByFilters(String desc, String nif, String firmname, String email, String phone, String city, String country, String address, String zipcode) throws SystemException {
		 Session session = null;
		 try {
			 session = openSession();
			 String sql = CustomSQLUtil.get(FIND_CONTACTOS_BY_FILTERS);
			 //replace logical operators
			 sql = CustomSQLUtil.replaceAndOperator(sql, true);
			 SQLQuery query = session.createSQLQuery(sql);
			 query.addEntity("Contacto", ContactoImpl.class);
			 //add parameters
			 QueryPos qPos = QueryPos.getInstance(query);
			 qPos.add("%"+desc+"%");
			 qPos.add("%"+desc+"%");
			 qPos.add("%"+desc+"%");
			 qPos.add(desc);
			 qPos.add("%"+nif+"%");
			 qPos.add(nif);
			 qPos.add("%"+firmname+"%");
			 qPos.add(firmname);
			 qPos.add("%"+email+"%");
			 qPos.add(email);
			 qPos.add("%"+phone+"%");
			 qPos.add(phone);
			 qPos.add("%"+city+"%");
			 qPos.add(city);
			 qPos.add("%"+country+"%");
			 qPos.add(country);
			 qPos.add("%"+address+"%");
			 qPos.add(address);
			 qPos.add("%"+zipcode+"%");
			 qPos.add(zipcode);
			 System.out.println("ContactoFinderImpl getContactosByFilters sql"+ sql.toString() +" *** qPos ->"+ qPos.toString() );
			 return (List)query.list();
		 } catch (Exception e) {
				throw new SystemException(e);
		 } finally {
			 closeSession(session);
		 }
	}
	
	
}