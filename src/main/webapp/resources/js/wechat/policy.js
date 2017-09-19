
var isEnd = false;
var pageNum = 0;
var pageSize = 3; 
var result = '';
$(function(){
	newsUtil.bindScroll();
	newsUtil.queryNews();
	newsUtil.btnQuery();
    
});
 var newsUtil = {
		 
		 btnQuery:function(){
			 $("#news_query").click(function(){
				 $("#content").html('<ul class="news_list"></ul>');
				 pageNum = 0; 
				 newsUtil.queryNews();
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
			        	newsUtil.queryNews();
			         }
			   });
		 },
		 queryNews:function(){
			 $.ajax({
	                type: 'GET',
	                url: "/onecard/queryPolicyList?" + "pageSize=" + pageSize + '&pageNum=' + pageNum + '&title=' + $("#title").val(),
	                dataType: 'json',
	                success: function(res){
	                	pageNum++;
	                    var list = res.data;
	                    var len = list.length;
	                    var items = '';
	                    for(var i =0;i<len;i++){
	                    	var liItem = "<li><p><a href='#'>关于土地承包责任制分红的若干意见</a></p><span>2017/08/08</span></li>";
	                    	items += liItem;
	                    }
	                    $(".news_list").append(items);
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


