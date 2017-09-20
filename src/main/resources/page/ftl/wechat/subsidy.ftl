<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <!-- Set render engine for 360 browser -->
    <meta name="renderer" content="webkit" />
    <!-- No Baidu Siteapp-->
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <meta name="format-detection" content="telephone = no" />
    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes" />
    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <title>补贴查询</title>
    <link href="/resources/css/wechat/mobiscroll-2.13.2.full.min.css" rel="stylesheet" />
    <link href="/resources/css/wechat/css_style.css" rel="stylesheet" />
    <script src="/resources/js/jquery/jquery.min.js"></script>
    <script src="/resources/js/utils/mobiscroll-2.13.2.full.min.js"></script>
</head>
<body>
	<div id="subsidy-container">
	    <div class="Inquire_box">
	        <table cellpadding="0" cellspacing="0" class="Inquire_table">
	            <tr>
	                <th>选择开始时间</th>
	                <td><input id="start_time" type="text" class="input_max" /></td>
	            </tr>
	            <tr>
	                <th>选择结束时间</th>
	                <td><input id="end_time" type="text" class="input_max" /></td>
	            </tr>
	        </table>
	        <div class="Inquire_op"><input id="subsidy-query-btn" type="submit" value="确定" class="submit_btn" /></div>
	    </div>
	    <div class="subsidy_result">
	        <ul class="subsidy_list">
	        </ul>
	    </div>
	    <div id="scroll-end" style="display:none;text-align:center">没有更多啦</div>
    </div>
    <script type="text/javascript" src="/resources/js/wechat/subsidy.js?version=${version}"></script>
</body>
</html>
