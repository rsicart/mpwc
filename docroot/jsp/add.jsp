<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<portlet:defineObjects />

<% 
Locale locale = request.getLocale();
String language = locale.getLanguage();
String country = locale.getCountry();

ResourceBundle res = ResourceBundle.getBundle("content.Language-ext", new Locale(language, country));
%>

<p><b><%= res.getString("jspadd.maintitle") %></b></p>

<portlet:actionURL var="addWorkerURL" name="addWorker">
    <portlet:param name="mvcPath" value="/jsp/view.jsp" />
</portlet:actionURL>

<aui:form name="frm_add_worker" action="<%= addWorkerURL %>" method="post">
	
	<aui:input type="hidden" name="redirectURL" value="<%= renderResponse.createRenderURL().toString() %>"/>

	<aui:fieldset>
		<aui:input label='<%= res.getString("formlabel.name") %>' name="name" type="text" value="">
			<aui:validator name="required" />
			<!-- Only allow alphabetical characters -->
     		<aui:validator name="alpha" />
		</aui:input>
	    <aui:input label='<%= res.getString("formlabel.surname") %>' name="surname" type="text" value="">
	    	<aui:validator name="required" />
			<!-- Only allow alphabetical characters -->
     		<aui:validator name="alpha" />
	    </aui:input>
	</aui:fieldset>
   <aui:fieldset>
	    <aui:input label='<%= res.getString("formlabel.nif") %>' name="nif" type="text" value="" >
			<aui:validator name="required" />
			<!-- Only allow alphabetical characters -->
     		<aui:validator name="alphanum" />
     		
		</aui:input>
	    <aui:input label='<%= res.getString("formlabel.email") %>' name="email" type="text" value="" >
			<aui:validator name="required" />
			<!-- Only allow email format -->
     		<aui:validator name="email" />
		</aui:input>
	    
   </aui:fieldset>
   <aui:fieldset>
		<aui:input label='<%= res.getString("formlabel.phone") %>' name="phone" type="text" value="" >
			<!-- Only allow numeric format -->
     		<aui:validator name="digits" />
		</aui:input>
		<aui:select label='<%= res.getString("formlabel.status") %>' name="status">
			<aui:option label='<%= res.getString("formlabel.option.active") %>' value="1"></aui:option>
			<aui:option label='<%= res.getString("formlabel.option.inactive") %>' value="2"></aui:option>
			<aui:option label='<%= res.getString("formlabel.option.bloqued") %>' value="3"></aui:option>
		</aui:select>
   </aui:fieldset>
   <aui:fieldset>
   		<aui:input type="textarea" name="comments" value="" >
			<!-- Only allow alphanumeric format -->
     		<aui:validator name="alphanum" />
		</aui:input>
   </aui:fieldset>
   <aui:button type="submit" />
</aui:form>


<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/jsp/view.jsp" />
</portlet:renderURL>

<p><a href="<%= viewURL %>">&larr; Back</a></p>