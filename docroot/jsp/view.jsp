<%--
/**
* Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ page import="com.mpwc.model.Worker" %>
<%@ page import="com.mpwc.service.WorkerLocalServiceUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<portlet:defineObjects />

<%
 Locale locale = request.getLocale();
 String language = locale.getLanguage();
 String country = locale.getCountry();

 ResourceBundle res = ResourceBundle.getBundle("content.Language-ext", new Locale(language, country));

 String error = "";
 List<Worker> ls;
 try{
	Integer n = WorkerLocalServiceUtil.getWorkersCount();
 	ls = WorkerLocalServiceUtil.getWorkers(0, n);
 	//TODO: get only non deleted workers 
 	
 	%>
 	<p><b><%= res.getString("jspview.maintitle") %></b></p>
 	<% 
 	if (n == 0){
 	%>
		<portlet:renderURL var="addWorkerURL">
		    <portlet:param name="mvcPath" value="/jsp/add.jsp" />
		</portlet:renderURL>	
		<p><%= res.getString("jspview.message.noworkers") %> <a href="<%= addWorkerURL %>"> <%= res.getString("jspview.message.addoneworker") %> </a></p>
 	<%
 	}
 	else{
 	%>
	 	<table border="1" width="80%">
	 	<tr>
	 		<td><b> <%= res.getString("formlabel.name") %> </b></td>
	 		<td><b> <%= res.getString("formlabel.surname") %></b></td>
	 		<td><b> <%= res.getString("formlabel.nif") %></b></td>
	 		<td><b> <%= res.getString("formlabel.email") %> </b></td>
	 		<td><b> <%= res.getString("formlabel.phone") %></b></td>
	 		<td><b> <%= res.getString("formlabel.status") %></b></td>
	 		<td><b></b></td>
	 		<td><b></b></td>
	 	</tr>
	 	<%
	 	for(Worker w: ls){
	 		%>
	 		<portlet:renderURL var="editWorkerURL">
		    	<portlet:param name="mvcPath" value="/jsp/edit.jsp" />
		    	<portlet:param name="workerId" value="<%= String.valueOf( w.getWorkerId() ) %>" />
			</portlet:renderURL>
			<portlet:renderURL var="deleteWorkerURL">
		    	<portlet:param name="mvcPath" value="/jsp/delete.jsp" />
		    	<portlet:param name="workerId" value="<%= String.valueOf( w.getWorkerId() ) %>" />
			</portlet:renderURL>
	 		<tr>
	 			<td> <%= w.getName() %></td> 
	 			<td> <%= w.getSurname() %></td> 
	 			<td> <%= w.getNif() %></td>
	 			<td> <%= w.getEmail() %></td>
	 			<td> <%= w.getPhone() %></td>
	 			<td> <%= w.getStatus() %></td>
	 			<td> &rarr; <a href="<%= editWorkerURL %>"> <%= res.getString("formlabel.actionedit") %></a></td>
	 			<td> &rarr; <a href="<%= deleteWorkerURL %>"> <%= res.getString("formlabel.actiondelete") %></a></td>
	 		</tr>
	 		<%
	 	}
	 	%>
	 	</table>
	 	
	 	<br/>
	 	<portlet:renderURL var="addNewWorkerURL">
		    <portlet:param name="mvcPath" value="/jsp/add.jsp" />
		</portlet:renderURL>	
		<p><a href="<%= addNewWorkerURL %>"> <%= res.getString("jspview.message.addoneworker") %> </a></p>
 	<%
 	}
 } catch (Exception e) {
	error = e.getMessage(); 
 }
%>
