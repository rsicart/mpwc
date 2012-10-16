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

package com.mpwc;

import java.io.IOException;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.mpwc.model.Worker;
import com.mpwc.service.WorkerLocalServiceUtil;

public class MpwcPortlet extends MVCPortlet {

    public void addWorker(ActionRequest actionRequest, ActionResponse actionResponse)
  	       throws IOException, PortletException{
    	
    	String name = actionRequest.getParameter("name");
    	String surname = actionRequest.getParameter("surname");
    	String nif = actionRequest.getParameter("nif");
    	String email = actionRequest.getParameter("email");
    	String phone = actionRequest.getParameter("phone");
    	int status = Integer.parseInt(actionRequest.getParameter("status"));

    	String comments = actionRequest.getParameter("comments");
    	Date now = new Date();
    	
    	if(name != null && surname != null && email != null && nif != null){
    		
	    	Worker w;
			try {
				long workerId = CounterLocalServiceUtil.increment(Worker.class.getName());
				w = WorkerLocalServiceUtil.createWorker(workerId);
				w.setName(name);
		    	w.setSurname(surname);
		    	w.setNif(nif);
		    	w.setEmail(email);
		    	if( phone != null ){ w.setPhone(phone); }
		    	if( status > 0 ){ w.setStatus(status); }
		    	if( comments != null ){ w.setComments(comments); }
		    	w.setCreateDate(now);
		    	WorkerLocalServiceUtil.addWorker(w);
			} catch (SystemException e) {
				System.out.println("addWorker exception:" + e.getMessage());
			}

    	}

    	// gracefully redirecting to the default portlet view
    	String redirectURL = actionRequest.getParameter("redirectURL");
    	actionResponse.sendRedirect(redirectURL);
     	
     }

    
    public void editWorker(ActionRequest actionRequest, ActionResponse actionResponse)
    	       throws IOException, PortletException {
    	
    	long workerId = Long.valueOf( actionRequest.getParameter("workerId") );
    	String name = actionRequest.getParameter("name");
    	String surname = actionRequest.getParameter("surname");
    	String nif = actionRequest.getParameter("nif");
    	String email = actionRequest.getParameter("email");
    	String phone = actionRequest.getParameter("phone");
    	int status = Integer.parseInt(actionRequest.getParameter("status"));
    	String comments = actionRequest.getParameter("comments");
    	Date now = new Date();
    	
    	if( workerId > 0 ){
    		
	    	Worker w;
			try {			
				w = WorkerLocalServiceUtil.getWorker(workerId);
				w.setName(name);
		    	w.setSurname(surname);
		    	w.setNif(nif);
		    	w.setEmail(email);
		    	if( phone != null ){ w.setPhone(phone); }
		    	if( status > 0 ){ w.setStatus(status); }
		    	if( comments != null ){ w.setComments(comments); }
		    	w.setModifiedDate(now);
		    	WorkerLocalServiceUtil.updateWorker(w);
			} catch (SystemException e) {
				System.out.println("editWorker exception:" + e.getMessage());
			} catch (PortalException e) {
				System.out.println("editWorker exception:" + e.getMessage());
			}

    	}

    	// gracefully redirecting to the default portlet view
    	String redirectURL = actionRequest.getParameter("redirectURL");
    	actionResponse.sendRedirect(redirectURL);

    }
    
    public void deleteWorker(ActionRequest actionRequest, ActionResponse actionResponse)
 	       throws IOException, PortletException {
 	
	    //Do not delete, mark as deleted
    	
	 	long workerId = Long.valueOf( actionRequest.getParameter("workerId") );
	
	 	int status = 100; //deleted status
	 	String comments = "Deleted worker.";
	 	Date now = new Date();
	 	
	 	if( workerId > 0 ){
	 		
		    	Worker w;
				try {			
					w = WorkerLocalServiceUtil.getWorker(workerId);
			    	w.setStatus(status);
			    	w.setComments(comments);
			    	w.setModifiedDate(now);
			    	WorkerLocalServiceUtil.updateWorker(w);
				} catch (SystemException e) {
					System.out.println("deleteWorker exception:" + e.getMessage());
				} catch (PortalException e) {
					System.out.println("deleteWorker exception:" + e.getMessage());
				}
	
	 	}
	
	 	// gracefully redirecting to the default portlet view
	 	String redirectURL = actionRequest.getParameter("redirectURL");
	 	actionResponse.sendRedirect(redirectURL);

    } 


}
