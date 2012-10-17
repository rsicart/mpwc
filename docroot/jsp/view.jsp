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

<%@page import="com.mpwc.service.persistence.WorkerUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
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
 	ls = WorkerLocalServiceUtil.findByStatus(1);
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
 		<portlet:renderURL var="addNewWorkerCheckbox">
		    <portlet:param name="mvcPath" value="/jsp/add.jsp" />
		</portlet:renderURL>	
	 	<portlet:renderURL var="editWorkerCheckbox">
			    <portlet:param name="mvcPath" value="/jsp/edit.jsp" />
		</portlet:renderURL>
		<portlet:renderURL var="deleteWorkerCheckbox">
			    <portlet:param name="mvcPath" value="/jsp/delete.jsp" />
		</portlet:renderURL>
	 	
	 	<script type="text/javascript">
	 		function onAdd(){
				var fullid = "<%= renderResponse.getNamespace() %>"+"frm_list_workers";
	 			document.getElementById(fullid).action="<%= addNewWorkerCheckbox %>";
	 		} 	
	 		function onEdit(){
				var fullid = "<%= renderResponse.getNamespace() %>"+"frm_list_workers";
	 			document.getElementById(fullid).action="<%= editWorkerCheckbox %>";
	 		}
	 		function onDelete(){
				var fullid = "<%= renderResponse.getNamespace() %>"+"frm_list_workers";
	 			document.getElementById(fullid).action="<%= deleteWorkerCheckbox %>";
	 		}
	 	</script>
 	
 		<aui:form name="frm_list_workers" action="" method="post">
 		
 		<aui:layout>
 		
 		<aui:column columnWidth="90" first="true">
 		
	 	<table border="1" width="80%">
	 	<tr class="portlet-section-header">
	 		<td><b></b></td>
	 		<td><b> <%= res.getString("formlabel.name") %> </b></td>
	 		<td><b> <%= res.getString("formlabel.surname") %></b></td>
	 		<td><b> <%= res.getString("formlabel.nif") %></b></td>
	 		<td><b> <%= res.getString("formlabel.email") %> </b></td>
	 		<td><b> <%= res.getString("formlabel.phone") %></b></td>
	 		<td><b> <%= res.getString("formlabel.status") %></b></td>
	 	</tr>
	 	<%
	 	for(Worker w: ls){
	 		%>

 		<tr class="portlet-section-body">
 			<td><input type="checkbox" name="workerId" value="<%= String.valueOf( w.getWorkerId() ) %>" /></td>
 			<td> <%= w.getName() %></td> 
 			<td> <%= w.getSurname() %></td> 
 			<td> <%= w.getNif() %></td>
 			<td> <%= w.getEmail() %></td>
 			<td> <%= w.getPhone() %></td>
 			<td> <%= w.getStatus() %></td>
 		</tr>
	 		<%
	 	}
	 	%>
	 	</table>
	 	
	 	</aui:column>
	 	
	 	<aui:column columnWidth="10" last="true">
	 	
		 	<aui:fieldset>
		 	
		 	<aui:button type="submit" value='<%= res.getString("formlabel.actionadd") %>' onClick='onAdd();' />
		 	<aui:button type="submit" value='<%= res.getString("formlabel.actionedit") %>' onClick='onEdit();' />	 	
		 	<aui:button type="submit" value='<%= res.getString("formlabel.actiondelete") %>' onClick='onDelete();' />
		 	
		 	</aui:fieldset>
	 	
	 	</aui:column>
	 	
	 	</aui:layout>
	 	
	 	</aui:form>
	 	
 	<%
 	}
 } catch (Exception e) {
	error = e.getMessage();
	System.out.println("Error view.jsp: "+error);
 }
%>
