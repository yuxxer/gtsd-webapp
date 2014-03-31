<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="com.homolo.framework.dao.util.PaginationSupport"
    import="com.homolo.usersystem.domain.User"
    import="org.apache.commons.lang3.StringUtils"
    import="org.apache.commons.lang3.RandomStringUtils"
%><%@ include
	file="/include/page.jspf"%><%
String tableId = request.getParameter("tableId");
if (StringUtils.isBlank(tableId)) {
	tableId = RandomStringUtils.randomAlphabetic(4);
}
int i = 0;
%>
                        <div class="block-fluid table-sorting clearfix">
                            <table style="border-collapse: collapse; border-spacing: 0; width: 100%" class="display dTable" id="<%=tableId%>">
                                <thead>
                                    <tr>
                                        <th>&nbsp;</th>
                                        <th width="39%">地名</th>
                                        <th width="60%">描述</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
<script type="text/javascript">
<!--
$('#<%=tableId%>').dataTable( {
    "bProcessing": true,
    "bServerSide": true,
    "sAjaxSource": "<%=$restRoot%>/gtsd.Zoom/collection/datatableselect",
    "aoColumns": [ { "bSortable": false }, { "bSortable": false }, { "bSortable": false }]
} );
//-->
</script>
