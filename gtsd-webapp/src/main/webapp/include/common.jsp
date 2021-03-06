<%@ page language="java" pageEncoding="utf-8"%><%@ include file="page.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath() %>/css/main.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='<%=request.getContextPath()%>/js/plugins/jquery/jquery.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/plugins/jquery/jquery-ui.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/plugins/jquery/jquery-ui-timepicker-addon.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/plugins/jquery/jquery.ui.datepicker-zh-CN.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/js/plugins/jquery/jquery.mousewheel.min.js'></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/spinner/ui.spinner.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/spinner/jquery.mousewheel.js"></script>


<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/charts/excanvas.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/charts/jquery.flot.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/charts/jquery.flot.orderBars.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/charts/jquery.flot.pie.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/charts/jquery.flot.resize.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/charts/jquery.sparkline.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/uniform.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/jquery.cleditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/jquery.validationEngine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/jquery.tagsinput.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/autogrowtextarea.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/jquery.dualListBox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/jquery.inputlimiter.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/forms/chosen.jquery.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/wizard/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/wizard/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/wizard/jquery.form.wizard.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/uploader/plupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/uploader/plupload.html5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/uploader/plupload.html4.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/uploader/jquery.plupload.queue.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/tables/datatable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/tables/tablesort.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/tables/resizable.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.tipsy.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.collapsible.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.prettyPhoto.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.progress.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.timeentry.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.colorpicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.jgrowl.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.breadcrumbs.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/ui/jquery.sourcerer.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/calendar.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/plugins/elfinder.min.js"></script>

<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/charts/chart.js"></script> --%>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/custom.js"></script>
<script type="text/javascript">
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
</script>