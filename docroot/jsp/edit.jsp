<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="com.mpwc.model.Worker" %>
<%@ page import="com.mpwc.service.WorkerLocalServiceUtil" %>

<portlet:defineObjects />

<p><b>Edit worker</b></p>

<%
	long workerId = Long.valueOf( renderRequest.getParameter("workerId") );

	Worker w = WorkerLocalServiceUtil.getWorker(workerId);

%>

<portlet:actionURL var="editWorkerURL" name="editWorker">
    <portlet:param name="mvcPath" value="/jsp/view.jsp" />
    <portlet:param name="workerId" value="<%= String.valueOf( w.getWorkerId() ) %>" />
</portlet:actionURL>

<aui:form action="<%= editWorkerURL %>" method="post">
	
	<aui:input type="hidden" name="redirectURL" value="<%= renderResponse.createRenderURL().toString() %>"/>

	<aui:fieldset>
		<aui:input label="name" name="name" type="text" value="<%= w.getName() %>" />
	    <aui:input label="surname" name="surname" type="text" value="<%= w.getSurname() %>" />
	</aui:fieldset>
   <aui:fieldset>
	    <aui:input label="nif" name="nif" type="text" value="<%= w.getNif() %>" />
	    <aui:input label="email" name="email" type="text" value="<%= w.getEmail() %>" />    
   </aui:fieldset>
   <aui:fieldset>
		<aui:input label="phone" name="phone" type="text" value="<%= w.getPhone() %>" />
		<aui:select name="status">
			<aui:option label="Active" value="1"></aui:option>
			<aui:option label="Inactive" value="2"></aui:option>
			<aui:option label="Bloqued" value="3"></aui:option>
		</aui:select>
   </aui:fieldset>
   <aui:fieldset>
   		<aui:input type="textarea" name="comments" value="<%= w.getComments() %>" />
   </aui:fieldset>
   <aui:button type="submit" />
</aui:form>


<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/jsp/view.jsp" />
</portlet:renderURL>

<p><a href="<%= viewURL %>">&larr; Back</a></p>