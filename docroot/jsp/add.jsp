<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="javax.portlet.PortletPreferences" %>

<portlet:defineObjects />

<p><b>Add worker</b></p>

<portlet:actionURL var="addWorkerURL" name="addWorker">
    <portlet:param name="mvcPath" value="/jsp/view.jsp" />
</portlet:actionURL>

<aui:form action="<%= addWorkerURL %>" method="post">
	
	<aui:input type="hidden" name="redirectURL" value="<%= renderResponse.createRenderURL().toString() %>"/>

	<aui:fieldset>
		<aui:input label="name" name="name" type="text" value="" />
	    <aui:input label="surname" name="surname" type="text" value="" />
	</aui:fieldset>
   <aui:fieldset>
	    <aui:input label="nif" name="nif" type="text" value="" />
	    <aui:input label="email" name="email" type="text" value="" />
	    
   </aui:fieldset>
   <aui:fieldset>
		<aui:input label="phone" name="phone" type="text" value="" />
		<aui:select name="status">
			<aui:option label="Active" value="1"></aui:option>
			<aui:option label="Inactive" value="2"></aui:option>
			<aui:option label="Bloqued" value="3"></aui:option>
		</aui:select>
   </aui:fieldset>
   <aui:fieldset>
   		<aui:input type="textarea" name="comments" value="" />
   </aui:fieldset>
   <aui:button type="submit" />
</aui:form>


<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/jsp/view.jsp" />
</portlet:renderURL>

<p><a href="<%= viewURL %>">&larr; Back</a></p>