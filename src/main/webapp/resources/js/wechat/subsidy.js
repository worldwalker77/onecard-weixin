﻿
var isEnd = false;
var pageNum = 0;
var pageSize = 3; 
$(function(){
	subsibyUtil.dateQueryInit();
	subsibyUtil.bindScroll();
	subsibyUtil.querySubsidys();
	subsibyUtil.btnQuery();
    
});
 var subsibyUtil = {
		 
		 btnQuery:function(){
			 $("#subsidy-query-btn").click(function(){
				 $(".subsidy_result").html('<ul class="subsidy_list"></ul>');
				 pageNum = 0; 
				 isEnd = false;
				 $("#scroll-end").css('display','none');
				 subsibyUtil.querySubsidys();
			 });
			 
		 },
		 bindScroll:function(){
			 $(window).scroll(function () {
			    	if (isEnd) {
						return;
					}
			        //已经滚动到上面的页面高度
			       var scrollTop = $(this).scrollTop();
			        //页面高度
			       var scrollHeight = $(document).height();
			         //浏览器窗口高度
			       var windowHeight = $(this).height();
			        //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
			        if (scrollTop + windowHeight == scrollHeight) {
			        	subsibyUtil.querySubsidys();
			         }
			   });
		 },
		 querySubsidys:function(){
			 $.ajax({
	                type: 'GET',
	                url: "/onecard/querySubsidyList?pageSize=" + pageSize + '&pageNum=' + pageNum + '&startTime=' + $("#start_time").val() + '&endTime=' + $("#end_time").val(),
	                dataType: 'json',
	                success: function(res){
	                	pageNum++;
	                    var list = res.data;
	                    if (list == null) {
	                    	isEnd = true;
	                    	$("#scroll-end").css('display','block'); 
	                    	return;
						}
	                    var len = list.length;
	                    var items = '';
	                    for(var i =0;i<len;i++){
	                    	var bankStatusShow = "<span class='f_green'>已成功</span>";
	                    	if (list[i].bankStatus == 1) {
	                    		bankStatusShow = "<span class='f_red'>已失败</span>";
							}
	                    	var liItem = "<li>" +
			                    			"<div class='subsidy_header'>" +
				                    			"<p>姓名：" +list[i].fName + "</p>" +
				                    			"<p>身份证号码：" +list[i].idNum + "</p>" +
				                    			"<p>村组：" +list[i].grName + "</p>" +
				                    			"<p>发放总金额：<span class='f_red'>" + list[i].total + "</span></p>" +
			                    			"</div>" +
			                    			"<div class='subsidy_txt'>" +
				                    			"<p>项目名称：" +list[i].proName + "</p>" +
				                    			"<p>发放时间：" +list[i].grantDate + "</p>" +
				                    			"<p>发送状态：" + bankStatusShow + "</p>" +
				                    			"<p>发放金额：<span class='f_red'>" + list[i].total + "</span></p>" +
				                    			"<p>发放明细：" + list[i].detail + "</p>" +
			                    			"</div>" +
		                    			"</li>";
	                    	items += liItem;
	                    }
	                    $(".subsidy_list").append(items);
	                    if (len < pageSize) {
	                    	isEnd = true;
	                    	$("#scroll-end").css('display','block'); 
	                    	return;
						}
	                    
	                },
	                error: function(xhr, type){
	                    alert('Ajax error!');
	                }
	            });
		 },
		 
		 dateQueryInit:function(){
			 var now = new Date(),
		        max = new Date(now.getFullYear() + 100, now.getMonth(), now.getDate());
		        $('#start_time').mobiscroll().date({
		            theme: 'ios7',
		            display: 'bottom',
		            lang: "zh",
		            max: max,
		            defaultValue: new Date(),
		            dateFormat: 'yy-mm-dd', //返回结果格式化为年月格式  
		            headerText: function (valueText) { //自定义弹出框头部格式  
		                array = valueText.split('-');
		                return array[0] + "年" + array[1] + "月" + array[2] + "日";
		            }
		        });
		        $('#end_time').mobiscroll().date({
		            theme: 'ios7',
		            display: 'bottom',
		            lang: "zh",
		            max: max,
		            defaultValue: new Date(),
		            dateFormat: 'yy-mm-dd', //返回结果格式化为年月格式  
		            headerText: function (valueText) { //自定义弹出框头部格式  
		                array = valueText.split('-');
		                return array[0] + "年" + array[1] + "月" + array[2] + "日";
		            }
		        });
		 }
		 
 }


