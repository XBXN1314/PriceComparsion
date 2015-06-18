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
	
	<meta name="keywwords" content="Shop Around - Great free html template for on-line shop. Use it as a start point for your on line business. The template can be easily implemented in many open source E-commerce platforms" />
	<meta name="description" content="Shop Around - Great free html template for on-line shop. Use it as a start point for your on line business. The template can be easily implemented in many open source E-commerce platforms" />
	
	<!-- JS -->
	<script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>	
	<script src="js/jquery.jcarousel.pack.js" type="text/javascript"></script>	
	<script src="js/jquery-func.js" type="text/javascript"></script>	
	<link rel="stylesheet" type="text/css" href="js/jquery.autocomplete.css">
    <script type="text/javascript" src="js/jquery.autocomplete.js"></script>
	<!-- End JS -->
	
	<script type="text/javascript">
	function detailInfo(picUrl){
		$.ajax({
			type: "POST",
			url: "DetailInfoServlet",
			dateType: "json",
			data: {'picUrl' : picUrl},
			success: function(data){
				if(data != "" || data != null){
					location.href = "bookDetail.jsp";
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
	
	function loadBook(){
		$.ajax({
			type: "POST",
			url: "LoadIndexBookServlet",
			dateType: "json",
			data: {},
			success: function(str){
				var json = $.parseJSON(str);
				
				for(i in json){
					var bookName = json[i].bookName;
	                var author = json[i].author;
	                var picUrl = json[i].picURL;
	                var price = json[i].price;
	                var wesite = json[i].website;

	                if(bookName.length > 18){
	                	bookName = bookName.substring(0,18) + "...";
	                }
	                if(author.length > 10){
	                	author = author.substring(0,10) + "...";
	                }
	                
	                if((i+1)%3 != 0){
	                	$("" + "<form></form>" + 
	                	"<li><a onclick = detailInfo('" + picUrl + "')><img src='" + picUrl + "' id='picUrl" + 
						i +  "'/></a> <div class='product-info'> <h3 id='bookName" + 
						i + "'>"+ bookName + "</h3><div class='product-desc'><p id='author" + 
						i + "'>" + author + "</p><strong id='price1' class='price'>" + 
						price + "</strong></div><p>" + wesite + "</p></div></li>").appendTo($("#product-show"));
	                }else{
	                	$("<li  class='last'><a onclick = detailInfo('" + picUrl + "')><img src='" + picUrl + "' id='picUrl" + 
						i +  "'/></a> <div class='product-info'> <h3 id='bookName" + 
						i + "'>"+ bookName + "</h3><div class='product-desc'><p id='author" + 
						i + "'>" + author + "</p><strong id='price1' class='price'>" + 
						price + "</strong></div><p>" + wesite + "</p></div></li>").appendTo($("#product-show"));
	                }
				}
				
			}
			
		});
		
		
	}
	
	$(function(){
		loadBook();
		
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
			
			<!-- Content Slider -->
			<div id="slider" class="box">
				<div id="slider-holder">
					<ul>
					    <li><a href="#"><img src="css/images/slide.jpg" alt="" /></a></li>
					    <li><a href="#"><img src="css/images/22.jpg" alt="" /></a></li>
					    <li><a href="#"><img src="css/images/33.jpg" alt="" /></a></li>
					    <li><a href="#"><img src="css/images/44.jpg" alt="" /></a></li>
					</ul>
				</div>
				<div id="slider-nav">
					<a href="#" class="active">1</a>
					<a href="#">2</a>
					<a href="#">3</a>
					<a href="#">4</a>
				</div>
			</div>
			<!-- End Content Slider -->
			
			
			<!-- Products -->
			<div class="products">
				<div class="cl">&nbsp;</div>
				<ul id="product-show">
			    <!-- 此部分显示商品信息 -->
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
					<form action="GetBookInfoServlet" method="post"  id="searchForm">
						
						<label>关键字</label>
						<input type="text" class="field" id="search" 
						name="search" placeholder="点击输入搜索内容" 
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
							<a href="#" class="bul">高级搜索</a><br/>
						</p>
	
					</form>
				</div>
			</div>
			<!-- End Search -->
			
			<!-- Categories -->
			<div class="box categories">
				<h2>分类 <span></span></h2>
				<div class="box-content">
					<ul>
					    <li><a href="#">分类 1</a></li>
					    <li><a href="#">分类 2</a></li>
					    <li><a href="#">分类 3</a></li>
					    <li><a href="#">分类 4</a></li>
					    <li><a href="#">分类 5</a></li>
					    <li><a href="#">分类 6</a></li>
					    <li><a href="#">分类 7</a></li>
					    <li><a href="#">分类 8</a></li>
					    <li><a href="#">分类 9</a></li>
					    <li><a href="#">分类 10</a></li>
					    <li><a href="#">分类 11</a></li>
					    <li><a href="#">分类 12</a></li>
					    <li class="last"><a href="#">分类 13</a></li>
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
