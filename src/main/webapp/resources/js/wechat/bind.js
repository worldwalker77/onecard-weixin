$(function() {
	bindUtil.bindClickEvent();
});

var bindUtil = {
		bindClickEvent:function(){
			$('#save').click(function() {
				var idNum = $("#idNum").val();
				if (bindUtil.validateIdCard(idNum) == false) {
		            return false;
		        }
				var mobilePhone = $("#mobilePhone").val();
		        if (bindUtil.verfyphonenumber(mobilePhone) == false) {
		            return false;
		        }
				$.ajax({
//			        type: "post",
			        url: '/bind/doBind?' + "idNum=" + idNum + '&mobilePhone=' + mobilePhone,
			        dataType: "json",
//			        data:data,
			        beforeSend: function () {
			        	
			        },
			        success: function (data) {
			        	if (data.code != 0) {
			        		alert(data.desc);
						}else{
							window.location.replace($('#redirectUrl').val());
						}
			        },
			        complete: function () {
			            
			        },
			        error: function (data) {
			        	alert("异常");
			        }
			    });
			});
			
		},
		validateIdCard:function(idCard){
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
		},
		verfyphonenumber:function(mb){
			if (mb.length == 0) {
	            alert("请输入手机号码!");
	            return false;
	        }
	        if (mb.length != 11) {
	            alert("手机号码有误！");
	            return false;
	        }
	        var myreg = /^(1[3|4|5|6|8|7|9])(\d){9}$/;
	        if (!myreg.test(mb)) {
	            alert("手机号码有误！");
	            return false;
	        }
	        return true;
		}
}

