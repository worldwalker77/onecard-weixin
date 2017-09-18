$(function(){
	var pageNum = 0;
    // 每页展示6个
    var pageSize = 2; 
    
    $(window).scroll(function () {
        //已经滚动到上面的页面高度
       var scrollTop = $(this).scrollTop();
        //页面高度
       var scrollHeight = $(document).height();
         //浏览器窗口高度
       var windowHeight = $(this).height();
        //此处是滚动条到底部时候触发的事件，在这里写要加载的数据，或者是拉动滚动条的操作
        if (scrollTop + windowHeight == scrollHeight) {
        	$.ajax({
                type: 'GET',
                url: "/onecard/queryNews?" + "pageSize=" + pageSize + '&pageNum=' + pageNum + '&title=' + $("#title").val(),
                dataType: 'json',
                success: function(res){
                	var result = '';
                    var list = res.data;
                    var len = list.length;
                    for(var i =0;i<len;i++){
                    	result += list[i].title;
                    }
                    $(".news_list").append(result);
                },
                error: function(xhr, type){
                    alert('Ajax error!');
                }
            });
         }
   });
});



