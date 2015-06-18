<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>比书网</title>
	<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
	<!--[if lte IE 6]><link rel="stylesheet" href="css/ie6.css" type="text/css" media="all" /><![endif]-->
	
	<meta name="keywwords" content="BookCity" />
	<meta name="description" content="BookCity" />
	
	<!-- JS -->
	<script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>	
	<script src="js/jquery.jcarousel.pack.js" type="text/javascript"></script>	
	<script src="js/jquery-func.js" type="text/javascript"></script>	
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  	<script src="js/jquery.js"></script>
 	<script src="js/jquery-ui.js"></script>
  	<link rel="stylesheet" href="js/jquery-ui.css">
	<!-- End JS -->
	<style type="text/css">
table.hovertable {
    font-family: verdana,arial,sans-serif;
    font-size:11px;
    color:#333333;
    border-width: 1px;
    border-color: #999999;
    border-collapse: collapse;
}
table.hovertable th {
    background-color:#c3dde0;
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #a9c6c9;
}
table.hovertable tr {
    background-color:#d4e3e5;
}
table.hovertable td {
    border-width: 1px;
    padding: 8px;
    border-style: solid;
    border-color: #a9c6c9;
}
</style>
	
	
	<script type="text/javascript">
	function loadBookDetail(){
	$.ajax({
			type: "POST",
			url: "GetBookDetailInfoServlet",
			dateType: "json",
			data: {},
			success: function(xml){
				var json = $.parseJSON(xml);
				
				var ddflag = 0, jdflag = 0;
				
				for(i in json){
					var category = json[i].category;
					var bookName = json[i].bookName;
					var url = json[i].URL;
					var ISBN = json[i].ISBN;
					var collections = json[i].collections;
					var comment = json[i].comment;
					var edition = json[i].edition;
					var opens = json[i].opens;
					var orignalPrice = json[i].orignalPrice;
					var packages = json[i].packages;
					var pageAttr = json[i].pageAttr;
					var pages = json[i].pages;
					var press = json[i].press;
					var printCount =  json[i].printCount;
					var printTime = json[i].printTime;
					var publishingTime = json[i].publishingTime;
		            var author = json[i].author;
		            var picUrl = json[i].picURL;
		            var price = json[i].price;
		            var website = json[i].website;
		            var words = json[i].words;
		            
					if("当当网" == website){
						$("<table width='695' height='240' border='1' class='hovertable' id='ddTable"+ i +"'>" + 
						"<tr><td width='53' height='42'>书名：</td><td colspan='5'  id='ddBookName" + i + "'>" + bookName +"</td>" +
					    "<td>作者：</td><td id='ddAuthor" + i + "'>"+ author +"</td></tr><tr><td height='44'>现价:</td><td width='52' id='ddPrice"+ i +"'>" + price + "</td>" +
					    "<td width='77'>原价：</td><td width='55' id='ddOrignalPrice" + i + "'>" + orignalPrice + "</td><td width='72'>出版社：</td>" +
					    "<td width='78' id='ddPress" + i + "'>" + press + "</td><td width='135'>出版日期：</td><td width='121' id='ddPublishingTime" + i +"'>" + publishingTime + "</td>" +
					  	"</tr><tr><td height='48'>版本：</td><td id='ddEdition" + i + "'>" + edition + "</td><td>收藏人气：</td><td id='ddCollections" + i + "'>" + collections + "</td>" +
					    "<td>来源：</td><td id='ddWebsite"+ i +"'>" + website + "</td><td>评论数：</td><td id='ddComment" + i + "'>"+ comment +"</td></tr>" +
					    "<tr><td height='45'>页数：</td><td id='ddPages" + i + "'>" + pages + "</td><td>字数：</td><td id='ddWords" + words + "'>" + words + "</td>" +
					    "<td>包装类型：</td><td id='ddPackages"+ i +"'>"+ packages +"</td><td>纸张类型：</td><td id='ddPageAttr"+ i +"'>"+ pageAttr +"</td></tr>" +
					  	"<tr><td height='47'>ISBN：</td><td id='ddISBN'" + i + ">"+ ISBN +"</td><td height='47'>网址：</td><td height='47' colspan='5' id='ddURL" + i + "'><a href = '"+ url +"'>"+ url +"</a></td></tr>" +
						"</table>").appendTo($("#ddBookInfoShow"));
						
						ddflag = 1;
			            
					}else if("京东网" == website){
						
						$("<table width='695' height='240' border='1' class='hovertable' id='ddTable"+ i +"'>" + 
						"<tr><td width='53' height='42'>书名：</td><td colspan='5'  id='ddBookName" + i + "'>" + bookName +"</td>" +
					    "<td>作者：</td><td id='ddAuthor" + i + "'>"+ author +"</td></tr><tr><td height='44'>现价:</td><td width='52' id='ddPrice"+ i +"'>" + price + "</td>" +
					    "<td width='77'>原价：</td><td width='55' id='ddOrignalPrice" + i + "'>" + orignalPrice + "</td><td width='72'>出版社：</td>" +
					    "<td width='78' id='ddPress" + i + "'>" + press + "</td><td width='135'>出版日期：</td><td width='121' id='ddPublishingTime" + i +"'>" + publishingTime + "</td>" +
					  	"</tr><tr><td height='48'>版本：</td><td id='ddEdition" + i + "'>" + edition + "</td><td>收藏人气：</td><td id='ddCollections" + i + "'>" + collections + "</td>" +
					    "<td>来源：</td><td id='ddWebsite"+ i +"'>" + website + "</td><td>评论数：</td><td id='ddComment" + i + "'>"+ comment +"</td></tr>" +
					    "<tr><td height='45'>页数：</td><td id='ddPages" + i + "'>" + pages + "</td><td>字数：</td><td id='ddWords" + words + "'>" + words + "</td>" +
					    "<td>包装类型：</td><td id='ddPackages"+ i +"'>"+ packages +"</td><td>纸张类型：</td><td id='ddPageAttr"+ i +"'>"+ pageAttr +"</td></tr>" +
					  	"<tr><td height='47'>ISBN：</td><td id='ddISBN'" + i + ">"+ ISBN +"</td><td height='47'>网址：</td><td height='47' colspan='5' id='ddURL" + i + "'><a href = '"+ url +"'>"+ url +"</a></td></tr>" +
						"</table>").appendTo($("#jdBookInfoShow"));
						
						jdflag = 1;
					}
				}
				
				if(0 == ddflag){
					$("<h2>抱歉数据库中未找到此网站相关书籍</h2>").appendTo($("#ddBookInfoShow"));
					$("#accordion").accordion({active:1});
					
				}else if(0 == jdflag){
					$("<h2>抱歉数据库中未找到此网站相关书籍</h2>").appendTo($("#jdBookInfoShow"));
					$("#accordion").accordion({active:0});
				}
				
			}
		});
	} 
	
	function validate(){
		var searchContent = $.trim($("#search").val());
		
		if(searchContent == ""){
			alert("请输入搜索内容");
		}else{
			$("#searchForm").submit();	
		}
		
	}
	
	$(function(){
		loadBookDetail();
		$("#accordion").accordion({
            heightStyle: "content",
            
            
            beforeActivate: function(event, ui) {
                // The accordion believes a panel is being opened
               if (ui.newHeader[0]) {
                   var currHeader  = ui.newHeader;
                   var currContent = currHeader.next('.ui-accordion-content');
                // The accordion believes a panel is being closed
               } else {
                   var currHeader  = ui.oldHeader;
                   var currContent = currHeader.next('.ui-accordion-content');
               }
                // Since we've changed the default behavior, this detects the actual status
               var isPanelSelected = currHeader.attr('aria-selected') == 'true';

                // Toggle the panel's header
               currHeader.toggleClass('ui-corner-all',isPanelSelected).toggleClass('accordion-header-active ui-state-active ui-corner-top',!isPanelSelected).attr('aria-selected',((!isPanelSelected).toString()));

               // Toggle the panel's icon
               currHeader.children('.ui-icon').toggleClass('ui-icon-triangle-1-e',isPanelSelected).toggleClass('ui-icon-triangle-1-s',!isPanelSelected);

                // Toggle the panel's content
               currContent.toggleClass('accordion-content-active',!isPanelSelected);    
               if (isPanelSelected) { currContent.slideUp(); }  else { currContent.slideDown(); }

               return false; // Cancels the default action
           }
        });
		
	});
	
	</script>
	
	
	
</head>
<body>
	
<!-- Shell -->	
<div class="shell">
	
	<!-- Header -->	
	<div id="header">
		<h1 id="logo"><a href="#">比价书城</a></h1>	
		
		<!-- Navigation -->
		<div id="navigation">
			<ul>
			    <li><a href="index.jsp" class="active">首页</a></li>
			    <li><a href="#">商品展示</a></li>
			    <li><a href="#">关于</a></li>
			</ul>
		</div>
		<!-- End Navigation -->
	</div>
	<!-- End Header -->
	
	<!-- Main -->
	<div id="main">
		<div class="cl">&nbsp;</div>
		
		<!-- Content -->
		<div id="content">
			
			<!-- Products -->
			<div class="products">
				<div class="cl">&nbsp;</div>
				<ul id="product-show">
				    
<div id="accordion">
  <h3 id="">当当网</h3>
  <div id="ddBookInfoShow" style="overflow-x: hidden">
    
  </div>
  
  
  <h3>京东网</h3>
  <div id="jdBookInfoShow"  style="overflow-x: hidden">
    
  </div>
</div>
			    	
				</ul>
				<div class="cl">&nbsp;</div>
			</div>
			<!-- End Products -->
			
		</div>
		<!-- End Content -->
		
		<!-- Sidebar -->
		<div id="sidebar">
			
			<!-- Search -->
			<div class="box search">
				<h2>搜索<span></span></h2>
				<div class="box-content">
					<form action="GetBookInfoServlet" method="post" id="searchForm" >
						
						<label>关键字</label>
						<input type="text" class="field" id="search" name="search"
						 placeholder="点击输入搜索内容" 
						 onkeydown="javascript:if(event.keyCode==13) return validate();"   
                        onkeyup="javascript:if(event.keyCode==13) return false;"
                        onkeypress="javascript:if(event.keyCode==13) return false;"/>
						
						<!-- 实现动态展示搜索功能 -->
						<div id="searchresult" style="display: none;">  </div>
						
						<label>分类</label>
						<select class="field">
							<option value="">-- 选择分类 --</option>
						</select>
						
						<input type="button" class="search-submit" value="搜索" onclick="validate();"/>
						
						<p>
							<a href="#"  class="bul">高级搜索</a><br />
						</p>
	
					</form>
				</div>
			</div>
			<!-- End Search -->
			
			<!-- Categories -->
			<div class="box categories">
				<h2>Categories <span></span></h2>
				<div class="box-content">
					<ul>
					    <li><a href="#">Category 1</a></li>
					    <li><a href="#">Category 2</a></li>
					    <li><a href="#">Category 3</a></li>
					    <li><a href="#">Category 4</a></li>
					    <li><a href="#">Category 5</a></li>
					    <li><a href="#">Category 6</a></li>
					    <li><a href="#">Category 7</a></li>
					    <li><a href="#">Category 8</a></li>
					    <li><a href="#">Category 9</a></li>
					    <li><a href="#">Category 10</a></li>
					    <li><a href="#">Category 11</a></li>
					    <li><a href="#">Category 12</a></li>
					    <li class="last"><a href="#">Category 13</a></li>
					</ul>
				</div>
			</div>
			<!-- End Categories -->
			
		</div>
		<!-- End Sidebar -->
		
		<div class="cl">&nbsp;</div>
	</div>
	<!-- End Main -->
	
	<!-- Side Full -->
	<div class="side-full">
		
		
	</div>
	<!-- End Side Full -->
	
	<!-- Footer -->
	<div id="footer">
		<p class="left">
			<a href="#">首页</a>
			<span>|</span>
			<a href="#">商品展示</a>
			<span>|</span>
			<a href="#">关于</a>
			
		</p>
		<p class="right">
			&copy; 2015 ECUST Lab301.
			Collect form Lab301
		</p>
	</div>
	<!-- End Footer -->
	
</div>	
<!-- End Shell -->
	
	
</body>
</html>
