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
    <title>修改绑定身份证号码</title>
    <link href="/resources/css/wechat/css_style.css" rel="stylesheet" />
    <script src="/resources/js/jquery/jquery.min.js"></script>
</head>
<body>
    <div class="form_header">修改个人身份证号码及手机号码，一卡通补贴数据按照新的号码及时推送和查询</div>
    <div class="form_box">
        <table cellpadding="0" cellspacing="0" class="form_table">
            <tr>
                <th>原身份证号码</th>
                <td><input id="id_number" type="text" class="input_txt" /></td>
            </tr>
            <tr>
                <th>原手机号码</th>
                <td><input id="phone" type="text" class="input_txt" maxlength="11"/></td>
            </tr>
            <tr>
                <th>新身份证号码</th>
                <td><input id="new_id_number" type="text" class="input_txt" /></td>
            </tr>
            <tr>
                <th>新手机号码</th>
                <td><input id="new_phone" type="text" class="input_txt" maxlength="11" /></td>
            </tr>
        </table>
        <div class="op_box">
            <input id="save" type="submit" value="确定" class="submit_btn" onclick="save();" />
            <input id="cancel" type="reset" value="取消" class="cancel_btn" onclick="cancel();" />
        </div>
    </div>
</body>
</html>
<script type="text/javascript">

	function cancel(){
    	window.location.reload();
    }
    function save() {
        if (validateIdCard($("#id_number").val()) == false) {
            return false;
        }
        if (verfyphonenumber($("#phone")) == false) {
            return false;
        }
        if (validateIdCard($("#new_id_number").val()) == false) {
            return false;
        }
        if (verfyphonenumber($("#new_phone")) == false) {
            return false;
        }
        
		var param = {
				oldIdNum:$("#id_number").val(),
				oldTel:$("#phone").val(),
				newIdNum:$("#new_id_number").val(),
				newTel:$("#new_phone").val()
		};
		$.ajax({
            type: 'POST',
            url: "/onecard/doModifyIdNum",
            data: JSON.stringify(param),
            contentType: "application/json",
            success: function(res){
					if(res.code == 0){
						window.location.replace('/onecard/modifySuccess');
					}else{
						alert(res.desc);
					}                
            },
            error: function(xhr, type){
                alert('Ajax error!');
            }
        });
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
    //验证15位和18位身份证号码
    function validateIdCard(idCard) {
        //15位和18位身份证号码的正则表达式
        var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

        //如果通过该验证，说明身份证格式正确，但准确性还需计算
        if (regIdCard.test(idCard)) {
            if (idCard.length == 18) {
                var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                for (var i = 0; i < 17; i++) {
                    idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
                }

                var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                var idCardLast = idCard.substring(17);//得到最后一位身份证号码

                //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                if (idCardMod == 2) {
                    if (idCardLast == "X" || idCardLast == "x") {
                        return true;
                    } else {
                        alert("身份证号码错误！");
                        return false;
                    }
                } else {
                    //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                    if (idCardLast == idCardY[idCardMod]) {
                        return true;
                    } else {
                        alert("身份证号码错误！");
                        return false;
                    }
                }
            }
        } else {
            alert("身份证格式不正确!");
            return false;
        }
    }
</script>