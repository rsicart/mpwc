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
	 	<table>
	 	<tr>
	 		<td><b>Name</b></td><td><b>Surame</b></td><td><b>Email</b></td>
	 	</tr>
	 	<%
	 	for(Worker w: ls){
	 		%>
	 		<tr>
	 			<td><%= w.getName() %></td> <td><%= w.getSurname() %></td> <td><%= w.getEmail() %></td>
	 		</tr>
	 		<%
	 	}
	 	%>
	 	</table>
 	<%
 	}
 } catch (Exception e) {
	error = e.getMessage(); 
 }
%>
