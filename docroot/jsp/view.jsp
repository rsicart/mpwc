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

<portlet:defineObjects />

<%
 String error = "";
 List<Worker> ls;
 try{
	Integer n = WorkerLocalServiceUtil.getWorkersCount();
 	ls = WorkerLocalServiceUtil.getWorkers(0, n);
 	
 	%>
 	<p><b>Workers list</b></p>
 	<% 
 	if (n == 0){
 	%>
		<portlet:renderURL var="addWorkerURL">
		    <portlet:param name="mvcPath" value="/jsp/add.jsp" />
		</portlet:renderURL>	
		<p>No workers yet. <a href="<%= addWorkerURL %>">Add</a>a new one.</p>
 	<%
 	}
 	else{
 	%>
	 	<table border="1" width="80%">
	 	<tr>
	 		<td><b> Name</b></td>
	 		<td><b> Surame</b></td>
	 		<td><b> Nif</b></td>
	 		<td><b> Email</b></td>
	 		<td><b> Phone</b></td>
	 		<td><b></b></td>
	 	</tr>
	 	<%
	 	for(Worker w: ls){
	 		%>
	 		<portlet:renderURL var="editWorkerURL">
		    	<portlet:param name="mvcPath" value="/jsp/edit.jsp" />
		    	<portlet:param name="workerId" value="<%= String.valueOf( w.getWorkerId() ) %>" />
			</portlet:renderURL>
	 		<tr>
	 			<td> <%= w.getName() %></td> 
	 			<td> <%= w.getSurname() %></td> 
	 			<td> <%= w.getNif() %></td>
	 			<td> <%= w.getEmail() %></td>
	 			<td> <%= w.getPhone() %></td>
	 			<td> &rarr; <a href="<%= editWorkerURL %>"> Edit</a></td>
	 		</tr>
	 		<%
	 	}
	 	%>
	 	</table>
	 	
	 	<br/>
	 	<portlet:renderURL var="addNewWorkerURL">
		    <portlet:param name="mvcPath" value="/jsp/add.jsp" />
		</portlet:renderURL>	
		<p>Or you can <a href="<%= addNewWorkerURL %>">add</a> a new worker.</p>
 	<%
 	}
 } catch (Exception e) {
	error = e.getMessage(); 
 }
%>
