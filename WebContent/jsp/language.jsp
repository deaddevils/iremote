<%if("zh".equalsIgnoreCase(request.getLocale().getLanguage()) ){ %>
	<script type="text/javascript" src="/iremote/js/jslabel_zh.js"></script>
<%}else if("vi".equalsIgnoreCase(request.getLocale().getLanguage()) ){ %>
	<script type="text/javascript" src="/iremote/js/jslabel_vi.js"></script>
<%}else if("ch".equalsIgnoreCase(request.getLocale().getLanguage()) ){ %>
	<script type="text/javascript" src="/iremote/js/jslabel_ch.js"></script>
<%}else if("fr".equalsIgnoreCase(request.getLocale().getLanguage()) ){ %>
	<script type="text/javascript" src="/iremote/js/jslabel_fr.js"></script>
<%}else { %>
	<script type="text/javascript" src="/iremote/js/jslabel_en.js"></script>
<%} %>

<script type="text/javascript">
    function getErrorMsg(error) {
        var msg = error_str;
        for (var i = 0; i < error_msg.length; i++) {
            if(error == error_msg[i]['code']){
                return error_msg[i]['msg']
            }
        }
        msg += error;
        return msg;
    }
</script>