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
    <aui:input label="nom" name="nom" type="text" value="" />
    <aui:input label="cognoms" name="cognoms" type="text" value="" />
    <aui:input label="email" name="email" type="text" value="" />
    <aui:button type="submit" />
</aui:form>


<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/jsp/view.jsp" />
</portlet:renderURL>

<p><a href="<%= viewURL %>">&larr; Back</a></p>