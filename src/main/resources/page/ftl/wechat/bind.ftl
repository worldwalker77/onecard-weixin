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
    <title>绑定身份证号码</title>
    <link href="/resources/css/wechat/css_style.css" rel="stylesheet" />
    <script src="/resources/js/jquery/jquery.min.js"></script>
</head>
<body>
	<input id="redirectUrl" type="hidden" value="${redirectUrl!''}" />
    <div class="form_header">请绑定个人身份证号码及手机号码，方便一卡通补贴数据及时推送和查询</div>
    <div class="form_box">
        <table cellpadding="0" cellspacing="0" class="form_table">
            <tr>
                <th>身份证号码</th>
                <td><input id="idNum" type="text" class="input_txt" /></td>
            </tr>
            <tr>
                <th>手机号码</th>
                <td><input id="mobilePhone" type="text" class="input_txt" maxlength="11"/></td>
            </tr>
        </table>
        <div class="op_box">
            <input id="save" type="submit" value="确定" class="submit_btn" />
            <input id="cancel" type="reset" value="取消" class="cancel_btn" />
        </div>
    </div>
    <script type="text/javascript" src="/resources/js/wechat/bind.js?version=${version}"></script>
</body>
</html>
