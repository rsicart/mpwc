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
<%@ page import="com.mpwc.model.Worker" %>
<%@ page import="com.mpwc.service.WorkerLocalServiceUtil" %>
<%@page import="com.mpwc.service.persistence.WorkerUtil"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<portlet:defineObjects />

<%
 Locale locale = request.getLocale();
 String language = locale.getLanguage();
 String country = locale.getCountry();

 ResourceBundle res = ResourceBundle.getBundle("content.Language-ext", new Locale(language, country));
%>

<p>
<%= res.getString("javax.portlet.title") %>
</p>

<p>
<%= res.getString("javax.portlet.welcome") %>
</p>

<p>
<%= res.getString("javax.portlet.welcome-desc") %>
</p>


<ul>
<li><a href="<%= res.getString("javax.portlet.welcome-link1") %>"> Mpwc portlet </a></li>
<li><a href="<%= res.getString("javax.portlet.welcome-link2") %>"> Mpwc-worker portlet </a></li>
<li><a href="<%= res.getString("javax.portlet.welcome-link3") %>"> Mpwc-cooler theme</a></li>
</ul>

<p>
R.Sicart
</p> 	
