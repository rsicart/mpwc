<!--  
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
-->

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

 <portlet:resourceURL var="jqGridResourceURL"></portlet:resourceURL>
 <portlet:resourceURL var="jqGridFormResourceURL">
 	<portlet:param name="jspPage" value="/jsp/list.jsp"></portlet:param>
 </portlet:resourceURL>
 
 <portlet:defineObjects />
 
 <%
 Locale locale = request.getLocale();
 String language = locale.getLanguage();
 String country = locale.getCountry();

 ResourceBundle res = ResourceBundle.getBundle("content.Language-ext", new Locale(language, country));
 %>
 
 <link rel="stylesheet" type="text/css" media="screen" href="<%=renderRequest.getContextPath()%>/css/ui.jqgrid.css" />
 <link rel="stylesheet" type="text/css" media="screen" href="<%=renderRequest.getContextPath()%>/css/ui.multiselect.css" />
 <script src="<%=renderRequest.getContextPath()%>/js/datatype.js" type="text/javascript"> </script>
 <script src="<%=renderRequest.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
 <script src="<%=renderRequest.getContextPath()%>/js/jquery-ui-custom.min.js" type="text/javascript"></script>
 <script src="<%=renderRequest.getContextPath()%>/js/jquery.layout.js" type="text/javascript"></script>
 <script src="<%=renderRequest.getContextPath()%>/js/i18n/grid.locale-en.js" type="text/javascript"></script>
 
 <script type="text/javascript">
 jQuery.jgrid.no_legacy_api = true;
 jQuery.jgrid.useJSON = true;
 </script>
 <script src="<%=renderRequest.getContextPath()%>/js/ui.multiselect.js" type="text/javascript"></script>
 <script src="<%=renderRequest.getContextPath()%>/js/jquery.jqGrid.js" type="text/javascript"></script>
 <script src="<%=renderRequest.getContextPath()%>/js/jquery.tablednd.js" type="text/javascript"></script>
 <script src="<%=renderRequest.getContextPath()%>/js/jquery.contextmenu.js" type="text/javascript"></script>

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
			var myGrid = $('#list3'),
		    selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
		    celValue = myGrid.jqGrid ('getCell', selRowId, 'id');
			$('#workerId').val(celValue);
			
			var fullid = "<%= renderResponse.getNamespace() %>"+"frm_list_workers";
			document.getElementById(fullid).action="<%= editWorkerCheckbox %>";
			
		}
		function onDelete(){
			var myGrid = $('#list3'),
		    selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
		    celValue = myGrid.jqGrid ('getCell', selRowId, 'id');
			$('#workerId').val(celValue);
			
			var fullid = "<%= renderResponse.getNamespace() %>"+"frm_list_workers";
			document.getElementById(fullid).action="<%= deleteWorkerCheckbox %>";
		}
	</script>
	
	<aui:form name="frm_list_workers" action="" method="post">
	
		<input type="hidden" id="workerId" name="workerId" value="" />
	
	<aui:layout>
	
	<aui:column columnWidth="90" first="true">
		
		<!-- Grid begin -->
		<table id="list3"></table>
		<div id="pager3"></div>
		<table id="navgrid"></table>
		<div id="pagernav"></div>
		<!-- Grid end -->
 	
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


 <script>
 jQuery("#list3").jqGrid({
     url:'<%=jqGridResourceURL.toString()%>',
 	 datatype: "json",
     colNames:['WorkerId', '<%= res.getString("formlabel.name") %>', '<%= res.getString("formlabel.surname") %>','<%= res.getString("formlabel.email") %>','<%= res.getString("formlabel.nif") %>','<%= res.getString("formlabel.status") %>'],
     colModel:[
      {name:'id',index:'id', width:60, sorttype:"int"},
      {name:'name',index:'name', width:100},
      {name:'surname',index:'surname', width:100, align:"right"},
      {name:'email',index:'email', width:150, align:"right"},  
      {name:'nif',index:'nif', width:60, align:"right"},  
      {name:'status',index:'status', width:40}  
     ],
     multiselect: true,
     rowNum:20,
     rowList:[10,20,30],
     pager: '#pager3',
     sortname: 'id',
     viewrecords: true,
     sortorder: "desc",
     onSelectRow: function(id){
 			jQuery("#workerId").value = id;
 	 },
     loadonce: true,
     caption: "<%= res.getString("jspview.maintitle") %>"
 });
 jQuery("#list3").jqGrid('navGrid','#pager3',
  {edit:false,add:false,del:false},
  {},
  {},
  {},
  {multipleSearch:true, multipleGroup:true}
  );


 </script>