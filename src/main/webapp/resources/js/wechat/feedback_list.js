
var isEnd = false;
var pageNum = 0;
var pageSize = 3; 
$(function(){
	feedBackListUtil.bindScroll();
	feedBackListUtil.queryFeedbacks();
    
});
 var feedBackListUtil = {
		 
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
			        	feedBackListUtil.queryFeedbacks();
			         }
			   });
		 },
		 queryFeedbacks:function(){
			 $.ajax({
	                type: 'GET',
	                url: "/onecard/queryFeedbackList",
	                dataType: 'json',
	                success: function(res){
	                	pageNum++;
	                    var list = res.data;
	                    var len = list.length;
	                    var items = '';
	                    for(var i =0;i<len;i++){
	                    	var liItem = "<li><a href='feed.html'>" +
			                    			"<h2>建议内容：分红什么时间发放<i class='right_icon'></i></h2>" +
			                    			"<p>提交时间：2017-08-08</p>" +
			                    			"<p>状态：<span class='f_green'>已处理</span></p>" +
			                    			"<p>回复时间：2017-08-09</p>" +
		                    			 "</a></li>";
	                    	items += liItem;
	                    }
	                    $(".feed_list").append(items);
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
		 }
		 
 }


