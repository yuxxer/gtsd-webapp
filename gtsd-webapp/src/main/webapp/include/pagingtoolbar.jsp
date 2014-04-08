<%@ page language="java"
	import="java.util.*,com.homolo.framework.util.*,com.homolo.framework.dao.util.PaginationSupport"
	pageEncoding="UTF-8"
%><%@ include file="page.jspf" %><%!

%><%

	String __page__ = helper.getRequestStringParameter("__page__", "__default_paginationsupport__");

	// Retrieve current PaginationSupport.

	PaginationSupport<?> pageObj = (PaginationSupport<?>) request.getAttribute(__page__);

	String preURL = helper.getRequestStringParameter("preURL", "");
	String cls = helper.getRequestStringParameter("cls", "pagination-mini");

	// Check Bean
	if (null == pageObj) {
		//out.print("Error: Current PaginationSupport Not Found.");
		//return;
	}
	String queryString = request.getQueryString();
	if (StringUtils.isEmpty(queryString)) {
		queryString = "";
	}

	int pageCount = pageObj.getTotalPage();
	int pageNo = pageObj.getCurrentPageNo();
	
	
	boolean prevable = pageNo > 1;
	boolean nextable = pageCount > pageNo;
	//boolean lastable = pageCount > pageNo;
	

	if (preURL.equals("")) {
		preURL = queryString.replaceAll("(\\&)*page=([^\\&=])*", "").replaceAll("^(\\&)*", "");
	
		if (!preURL.equals("")) {
			preURL = "?" + preURL + "&page=";
		} else {
			preURL = "?page=";
		}
	}


	Map<String, String> list = new LinkedHashMap<String, String>();
	list.put("1", null);
	if (pageCount > 1) {
		list.put("2", null);
	}
	boolean fDot = pageNo > 5;
	for (int i = -2; i < 3; i++) {
		int p = pageNo + i;
		if (p > 0 && p < pageCount) {
			list.put(String.valueOf(p), "");
		}
	}
	boolean lDot = pageNo < pageCount - 4;
	if (pageCount > 2) {
		list.put(String.valueOf(pageCount - 1), "");
	}
	if (pageCount > 1) {
		list.put(String.valueOf(pageCount), "");
	}
	Iterator<String> it = list.keySet().iterator();
	String ss = "";
	int c = 0, t = list.size();
	while(it.hasNext()) {
		String p = (String) it.next();
		if (p.equals(String.valueOf(pageNo))) {
			ss += "<li class=\"disabled\" ><a href=\"" +  preURL + p + "\">" + p + "</a><li>";
		} else {
			ss += "<li><a href=\"" +  preURL + p + "\">" + p + "</a><li>"; 
		}
		c++;
		if ((fDot && p.equals("2")) || (lDot && c == t - 2)) {
			ss += "<li><a href=\"#\">...</a><li>";
		}
	}
	
	//如果只有一页，或没有内容，则不显示分页字符
	if(pageCount<=1)ss="";

%><div class="pagination">
	<ul class="pages">
<%if(prevable){%><li class="prev"><a href="<%=preURL + (pageNo - 1)%>"><%=MessageUtils.getLocaleMessage("jsp.pagingtoolbar.label", "Previous")%></a><li><%}%>
<%=ss%>
<%if(nextable){%><li class="next"><a href="<%=preURL + (pageNo + 1)%>"><%=MessageUtils.getLocaleMessage("jsp.pagingtoolbar.label", "Next")%></a><li><%}%>
	</ul>
</div>