var myOrder = {
		
		/**
		 * 查询我的订单（根据联系人和交易员）
		 */
		getMyOrder : function(){
			$(".otherOrder").html("<img src=\"http://static-online.zhaogangmit.com/project/ued/dldirqq/local/Resources/images/loading.gif");
			var param = {
				"traderId" : $("#operator_code").val(),
	        	"accountId" : $("#contact_id").val(),
	        	"pageSize" : $("#pageSize").val(),
	        	"pageNumber" : $("#pageNumber").val()
			}
			$.ajax({
				type: "POST",  
		        contentType : "application/json",
		        data : JSON.stringify(param),
		        url : contextPath + "/salesOrder/queryMyOrder",
		        success : function(result){
		        	$(".otherOrder").html("");
		        	if(result.code == "0"){
		        		var orderList = result.data.salesOrderDtoList;
		        		var total = result.data.total;
		        		if(orderList.length == total){
		        			$(".wrap").css("padding-bottom","46px");
		        			$(".otherOrder").css("top","395px");
		        			$(".otherOrder").css("background","#f1f4f9");
		        			$(".otherOrder").html("已加载完,为您找到"+total+"条相关结果");
		        		}else {
		        			$(".otherOrder").css("background","");
		        		}
		        		$("#orderTotal").val(total);
		        		var myOrderList = "";
		        		if(orderList.length > 0){
		        			for(var i=0;i<orderList.length;i++){
		        				var myOrder = "<div class=\"infor-order-section\"><div class=\"section-top\"><span>";
		        				myOrder += dateUtils.dateFormatter(new Date(orderList[i].orderDate),"yyyy.MM.dd");
		        				myOrder += "</span><a href=\"javascript:;\" class=\"blue checkItem\" onclick=\"myOrder.getMyOrderItem("+orderList[i].salesOrderId+",this)\">";
		        				myOrder += orderList[i].status;
		        				myOrder += "</a><input type=\"hidden\" id=\"salesOrderId\" value="+orderList[i].salesOrderId+"/>";
		        				myOrder += "</div><div class=\"section-bottom none\"><i class=\"icon-section-arrow1\"></i><i class=\"icon-section-arrow2\"></i><div class=\"kind kind-border item1\"></div><div class=\"kind item2\"></div><div class=\"view-order\"></div></div></div>";
		        				myOrderList += myOrder;
		        			}
		        			$(".myOrder").html(myOrderList);
		        		}
		        	}
		        }
			})
		},

		/**
		 * 查询订单商品明细
		 * @param orderDom
		 */
		getMyOrderItem : function(salesOrderId,orderDom){
			$(".section-bottom").fadeOut(50);
			if($(orderDom).hasClass("onlyItem")){
				$(orderDom).removeClass("onlyItem");
				return;
			}else{
				$(".checkItem").removeClass("onlyItem");
			}
			$.ajax({
				type: "POST",  
		        contentType : "application/json",
		        data : JSON.stringify(salesOrderId),
		        url : contextPath + "/salesOrder/queryOrderItem",
		        success : function(result){
		        	if(result.code == "0"){
		        		$(orderDom).addClass("onlyItem");
		        		var items = result.data.topTwoOfSalesOrderItem;
		        		var remainNumber = result.data.remainNumber;
		        		if(items.length >= 2){
		        			var firstItem = "<div class=\"kind-top\">"+
		        			items[0].categoryName+"<span class='line'>|</span>"+items[0].materialName+"<span class='line'>|</span>"+items[0].specificationName+
		        			"</div><div class=\"kind-bottom\">"+items[0].factoryName+
		        			"<span class='line'>|</span>"+items[0].warehouseName+"</div>";
		        			var secondItem = "<div class=\"kind-top\">"+
		        			items[1].categoryName+"<span class='line'>|</span>"+items[1].materialName+"<span class='line'>|</span>"+items[1].specificationName+
		        			"</div><div class=\"kind-bottom\">"+items[1].factoryName+
		        			"<span class='line'>|</span>"+items[1].warehouseName+"</div>";
		        			$(orderDom).parent().parent().children(".section-bottom").children(".item1").html(firstItem);
		        			$(orderDom).parent().parent().children(".section-bottom").children(".item2").html(firstItem);
		        			$(orderDom).parent().parent().children(".section-bottom").fadeIn(50);
		        			//$(".item1").html(firstItem);
		        			//$(".item2").html(secondItem);
		        		}else if(items.length = 1){
		        			var firstItem = "<div class=\"kind-top\">"+
		                    items[0].categoryName+"<span class='line'>|</span>"+items[0].materialName+"<span class='line'>|</span>"+items[0].specificationName+
		                    "</div><div class=\"kind-bottom\">"+items[0].factoryName+
		                    "<span class='line'>|</span>"+items[0].warehouseName+"</div>";
		        			$(orderDom).parent().parent().children(".section-bottom").children(".item1").html(firstItem);
		        			$(orderDom).parent().parent().children(".section-bottom").children(".item2").addClass("none");
		        			$(orderDom).parent().parent().children(".section-bottom").fadeIn(50);
		        			//$(".item1").html(firstItem);
		        			//$(".item2").addClass("none");
		        		}
		        		if(remainNumber > 0){
		        			var remain = remainNumber+"个商品未显示<a style='cursor:pointer' onclick='window.open(\"http://oms.zhaogangrentest.com/index?salesOrderId="+items[0].salesOrderId+"&zg_sso_token="+$("#sso_token").val()+"&zg_sso_token_temp="+$("#temp_sso_token").val()+"\")'>查看订单</a>";
		        			$(orderDom).parent().parent().children(".section-bottom").children(".view-order").html(remain);
		        			//$(".view-order").html(remain);
		        		}else{
		        			var remain ="<a style='cursor:pointer' onclick='window.open(\"http://oms.zhaogangrentest.com/index?salesOrderId="+items[0].salesOrderId+"&zg_sso_token="+$("#sso_token").val()+"&zg_sso_token_temp="+$("#temp_sso_token").val()+"\")'>查看订单</a>";
		        			$(orderDom).parent().parent().children(".section-bottom").children(".view-order").html(remain);
		        			//$(orderDom).parent().parent().children(".section-bottom").children(".view-order").addClass("none");
		        			//$(".view-order").addClass("none");
		        		}
		        	}
		        }
			})
		},
		
		/**
		 * 滚轮事件刷新订单
		 */
		refresh : function(){
			var $this =$(this),  
	        viewH = $(".wrap")[0].clientHeight,//可见高度  clientHeight 窗口高度
	        contentH = $(".wrap")[0].scrollHeight,//内容高度  offsetHeight 文档高度
	        scrollTop = $(".wrap")[0].scrollTop;//滚动条高度
			if(scrollTop == (contentH - viewH) && parseInt($("#orderTotal").val()) > parseInt($("#pageSize").val())){ //到达底部,加载新内容  
				// 这里加载数据.. 
				$("#pageSize").val(parseInt($("#pageSize").val())+8);
				myOrder.getMyOrder();
			}  
		}
}
$(function(){
	//初始化订单列表（8条）
	myOrder.getMyOrder();
	//滚轮监听事件
	if($(".wrap")[0].addEventListener) {
		$(".wrap")[0].addEventListener("scroll", myOrder.refresh, false);
	};
	//刷新按钮
	$(".refreshMyOrder").on("click",function(){
		$("#pageSize").val(8);
		$(".myOrder").html("");
		myOrder.getMyOrder();
	})
});