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
    <title>投诉建议</title>
    <link href="/resources/css/wechat/css_style.css" rel="stylesheet" />
    <script src="/resources/js/jquery/jquery.min.js"></script>
</head>
<body>
    <div class="form_header"></div>
    <div class="form_box">
        <table cellpadding="0" cellspacing="0" class="form_table">
            <tr>
                <th>标题</th>
                <td><input id="title" type="text" class="input_txt" required="required" /></td>
            </tr>
            <tr>
                <th valign="top">内容</th>
                <td>
                    <textarea id="content" rows="2" cols="20" class="input_txt" required="required"></textarea>
                </td>
            </tr>
        </table>
        <div class="op_box">
            <input id="save" type="submit" value="确定" class="submit_btn" onclick="save();" />
            <input id="cancel" type="reset" value="取消" class="cancel_btn" />
        </div>
    </div>
</body>
</html>
<script type="text/javascript">
    var falg = true;
    function save() {
        $(".input_txt[required='required']").each(function (i, n) {
            if ($(this).val().length == 0) {
                alert($(this).parent().prev().text() + "不能为空！");
                falg = false;
                return false;
            }else {
                falg = true;
            }
        });
        if (falg) {
        		var param = {
    					title:$("#title").val(),
    					content:$("#content").val()
        		};
        		$.ajax({
	                type: 'POST',
	                url: "/onecard/addFeedBack",
	                data: JSON.stringify(param),
	                contentType: "application/json",
	                success: function(res){
							if(res.code == 0){
								window.location.replace('/onecard/feedbackList');
							}else{
								alert(res.desc);
							}                
	                },
	                error: function(xhr, type){
	                    alert('Ajax error!');
	                }
	            });
        }
    }
    //验证手机号码
    function verfyphonenumber(mb) {
        if (mb.val().length == 0) {
            alert("请输入手机号码!");
            return false;
        }
        if (mb.val().length != 11) {
            alert("手机号码有误！");
            return false;
        }
        var myreg = /^(1[3|4|5|6|8|7|9])(\d){9}$/;
        if (!myreg.test(mb.val())) {
            alert("手机号码有误！");
            return false;
        }
        return true;
    }
</script>