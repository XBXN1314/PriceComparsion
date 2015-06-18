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
					location.href = "bookDetailBasedOnSearch.jsp";
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
			url: "GetDetailInfoServlet",
			dateType: "json",
			data: {},
			success: function(xml){
				
			var json = $.parseJSON(xml);
				
			
if(xml != "[]"){
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
	            	$("<li><a onclick = detailInfo('" + picUrl + "')><img src='" + picUrl + "' id='picUrl" + 
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
		}else{
			
			$("<h2>...我表示啥都没找到...</h2>").appendTo($("#product-show"));
			
		}
			}
		});
	}
	
	
	$(function(){
		loadBook();
		
		$("#search").focus();
		
		$("em").children().click(function(){
			var layer3=$(this).text();
			var layer2=$(this).parent().parent().parent().children("dt").text();
			var layer1=$(this).parent().parent().parent().parent().parent().parent().children("h3").text();
			
			var category = layer1 + "#" + layer2 + "#" + layer3;
			$.ajax({
			type: "POST",
			url: "DetailInfoServlet",
			dateType: "json",
			data: {'category' : category},
			success: function(data){
				if(data != "" || data != null){
					location.href = "bookDetail.jsp";
				}
			}
		});
			
			});	
			
			$("dt").children().click(function(){
			var layer2=$(this).text();
			var layer1=$(this).parent().parent().parent().parent().parent().children("h3").text();
			});
			
			$(".item").children("h3").children("a").click(function(){
				var layer1=$(this).text();
			});
			
		$('.all-sort-list > .item').hover(function(){
			var eq = $('.all-sort-list > .item').index(this),				//获取当前滑过是第几个元素
				h = $('.all-sort-list').offset().top,						//获取当前下拉菜单距离窗口多少像素
				s = $(window).scrollTop(),									//获取游览器滚动了多少高度
				i = $(this).offset().top,									//当前元素滑过距离窗口多少像素
				item = $(this).children('.item-list').height(),				//下拉菜单子类内容容器的高度
				sort = $('.all-sort-list').height();						//父类分类列表容器的高度
			
			if ( item < sort ){												//如果子类的高度小于父类的高度
				if ( eq == 0 ){
					$(this).children('.item-list').css('top', (i-h));
				} else {
					$(this).children('.item-list').css('top', (i-h)+1);
				}
			} else {
				if ( s > h ) {												//判断子类的显示位置，如果滚动的高度大于所有分类列表容器的高度
					if ( i-s > 0 ){											//则 继续判断当前滑过容器的位置 是否有一半超出窗口一半在窗口内显示的Bug,
						$(this).children('.item-list').css('top', (s-h)+2 );
					} else {
						$(this).children('.item-list').css('top', (s-h)-(-(i-s))+2 );
					}
				} else {
					$(this).children('.item-list').css('top', 3 );
				}
			}	

			$(this).addClass('hover');
			$(this).children('.item-list').css('display','block');
		},function(){
			$(this).removeClass('hover');
			$(this).children('.item-list').css('display','none');
		});

		$('.item > .item-list > .close').click(function(){
			$(this).parent().parent().removeClass('hover');
			$(this).parent().hide();
		});
		
	});
	
	</script>
	
	
	
</head>
<body>
	
<!-- Shell -->	
<div class="shell">
	
	<!-- Header -->	
	<div id="header">
		<h1 id="logo"><a href="index.jsp">比价书城</a></h1>	
		
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
					<form action="GetBookInfoServlet" method="post" id="searchForm">
						
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
							<option value="文艺">文艺</option>
							<option value="童书">童书</option>
							<option value="励志/成功">励志/成功</option>
							<option value="生活">生活</option>
							<option value="计算机">计算机</option>
							<option value="人文社科">人文社科</option>
							<option value="经管">经管</option>
							<option value="科技">科技</option>
							<option value="教育">教育</option>
							<option value="工具书">工具书</option>
						</select>
						 
						 
						<input type="button" class="search-submit" value="搜索" onclick="validate();"/>
						
						<!-- 
						<p>
							<a href="#" class="bul">高级搜索</a><br />
						</p>
						 -->
						 
					</form>
				</div>
			</div>
			<!-- End Search -->
			
			<!-- Categories -->
			<div class="wrap">
		<div class="all-sort-list">
			<div class="item bo">
				<h3><a href="" class="STYLE5">文艺</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">小说</a></dt>
							<dd><em><a href="#">中国当代小说</a></em><em><a href="#">中国近现代小说</a></em><em><a href="#">中国古典小说</a></em><em><a href="#">四大名著</a></em><em><a href="#">外国小说</a></em><em><a href="#">侦探/悬疑/推理</a></em><em><a href="#">惊悚/恐怖</a></em><em><a href="#">魔幻/武侠/玄幻</a></em><em><a href="#">军事</a></em><em><a href="#">社会</a></em><em><a href="#">职场</a></em><em><a href="#">官场</a></em><em><a href="#">历史</a></em><em><a href="#">作品集</a></em><em><a href="#">情感</a></em><em><a href="#">港台小说</a></em><em><a href="#">都市</a></em><em><a href="#">影视小说</a></em><em><a href="#">世界名著</a></em>							</dd>
						</dl>
						<dl class="fore2">
							<dt><a href="#">文学</a></dt>
							<dd><em><a href="#">散文/随笔/书信</a></em><em><a href="#">纪实文学</a></em><em><a href="#">中国文学</a></em><em><a href="#">外国文学</a></em><em><a href="#">作品集</a></em><em><a href="#">文学评论与研究</a></em><em><a href="#">诗歌诗曲</a></em><em><a href="#">戏曲曲艺</a></em><em><a href="#">影视文学</a></em><em><a href="#">文学史</a></em><em><a href="#">文学类考试</a></em><em><a href="#">英文原版书</a></em><em><a href="#">名家作品</a></em>							</dd>
						</dl>
						<dl class="fore3">
							<dt><a href="#">传纪</a></dt>
							<dd><em><a href="#">政治人物</a></em><em><a href="#">历代帝王</a></em><em><a href="#">领袖首脑</a></em><em><a href="#">学者</a></em><em><a href="#">艺术家</a></em><em><a href="#">财经人物</a></em><em><a href="#">军事人物</a></em><em><a href="#">自传</a></em><em><a href="#">建筑师/设计师</a></em><em><a href="#">文娱明星</a></em><em><a href="#">历史人物</a></em><em><a href="#">女星人物</a></em><em><a href="#">法律人物</a></em><em><a href="#">宗教人物</a></em><em><a href="#">哲学家</a></em><em><a href="#">人文/社会学</a></em><em><a href="#">教育家</a></em><em><a href="#">语言文学家</a></em><em><a href="#">文学家</a></em><em><a href="#">国学大师</a></em><em><a href="#">人物合集</a></em><em><a href="#">传记的研究与发现</a></em><em><a href="#">家族研究/谱</a></em><em><a href="#">年谱</a></em><em><a href="#">英文原版书</a></em>							</dd>
						</dl>
						<dl class="fore4">
							<dt><a href="#">青春文学</a></dt>
							<dd><em><a href="#">校园</a></em><em><a href="#">爱情/情感</a></em><em><a href="#">叛逆/成长</a></em><em><a href="#">悬疑/惊悚</a></em><em><a href="#">娱乐/偶像</a></em><em><a href="#">爆笑/无厘头</a></em><em><a href="#">玄幻/科幻/新武侠</a></em><em><a href="#">大陆原创</a></em><em><a href="#">港台青春文学</a></em><em><a href="#">韩国青春文学</a></em><em><a href="#">其他国外青春文学</a></em><em><a href="#">穿越/重生/架空</a></em><em><a href="#">影视写真</a></em><em><a href="#">古代言情</a></em>							</dd>
						</dl>
						<dl class="fore5">
							<dt><a href="#">动漫/幽默</a></dt>
							<dd><em><a href="#">大陆动漫</a></em><em><a href="#">港台动漫</a></em><em><a href="#">日韩动漫</a></em><em><a href="#">欧美动漫</a></em><em><a href="#">动漫学堂</a></em><em><a href="#">轻小说</a></em><em><a href="#">绘本</a></em><em><a href="#">幽默笑话集</a></em><em><a href="#">连环画</a></em><em><a href="#">世界经典漫画</a></em><em><a href="#">小说/名著漫画</a></em><em><a href="#">游戏同人作品</a></em><em><a href="#">画集</a></em><em><a href="#">动漫同人作品</a></em>							</dd>
						</dl>
						<dl class="fore6">
							<dt><a href="#">艺术</a></dt>
							<dd><em><a href="#">绘画</a></em><em><a href="#">音乐</a></em><em><a href="#">舞蹈</a></em><em><a href="#">雕塑</a></em><em><a href="#">设计</a></em><em><a href="#">工艺美术</a></em><em><a href="#">民间艺术</a></em><em><a href="#">建筑艺术</a></em><em><a href="#">艺术类考试</a></em><em><a href="#">世界各国艺术</a></em><em><a href="#">书法/篆刻</a></em><em><a href="#">影视/媒体艺术</a></em><em><a href="#">收藏/鉴赏</a></em><em><a href="#">小人书/连环画</a></em><em><a href="#">英文原版书</a></em><em><a href="#">动画</a></em><em><a href="#">艺术史</a></em><em><a href="#">工具书</a></em>							</dd>
						</dl>
						<dl class="fore7">
							<dt><a href="#">摄影</a></dt>
							<dd><em><a href="#">技法/教程</a></em><em><a href="#">摄影器材</a></em><em><a href="#">后期处理</a></em><em><a href="#">分类摄影</a></em><em><a href="#">作品集/作品</a></em><em><a href="#">摄影入门</a></em><em><a href="#">摄影进阶</a></em><em><a href="#">英文原版书</a></em><em><a href="#">中国国家地理</a></em><em><a href="#">美国国家地理</a></em><em><a href="#">蜂鸟</a></em>							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="item">
				<h3><a href="" class="STYLE2">童书</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">童书</a></dt>
							<dd><em><a href="#">幼儿启蒙</a></em><em><a href="#">益智游戏</a></em><em><a href="#">玩具书</a></em><em><a href="#">动漫/卡通</a></em><em><a href="#">儿童文学</a></em><em><a href="#">科普/百科</a></em><em><a href="#">0-2岁</a></em><em><a href="#">3-6岁</a></em><em><a href="#">7-10岁</a></em><em><a href="#">11-14岁</a></em><em><a href="#">精装图书</a></em><em><a href="#">平装图书</a></em><em><a href="#">婴儿读物</a></em><em><a href="#">励志/成长</a></em><em><a href="#">进口儿童书</a></em><em><a href="#">少儿期刊</a></em><em><a href="#">阅读工具书</a></em><em><a href="#">少儿英语</a></em><em><a href="#">绘本</a></em><em><a href="#">艺术培养</a></em>							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="item">
				<h3><a href="" class="STYLE2">励志/成功</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">励志/成功</a></dt>
							<dd><em><a href="#">人在职场</a></em><em><a href="#">青少年励志</a></em><em><a href="#">名人励志</a></em><em><a href="#">成功/激励</a></em><em><a href="#">心灵与修养</a></em><em><a href="#">智商/智谋</a></em><em><a href="#">情商/情绪管理</a></em><em><a href="#">人际交往</a></em><em><a href="#">处世学</a></em><em><a href="#">礼仪</a></em><em><a href="#">口才演讲</a></em><em><a href="#">名言/格言</a></em><em><a href="#">励志经典著作</a></em><em><a href="#">人生哲学</a></em><em><a href="#">性格与习惯</a></em><em><a href="#">财商/财富智慧</a></em><em><a href="#">励志小品</a></em><em><a href="#">少儿英语</a></em><em><a href="#">男性励志</a></em><em><a href="#">女性励志</a></em><em><a href="#">英文原版书</a></em><em><a href="#">时间管理</a></em><em><a href="#">自我完善</a></em><em><a href="#">个人形象</a></em><em><a href="#">创业必修</a></em><em><a href="#">白富美</a></em><em><a href="#">高富帅</a></em>							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="item">
				<h3><a href="" class="STYLE2">生活</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">休闲/爱好</a></dt>
							<dd><em><a href="#">休闲</a></em><em><a href="#">爱好</a></em>							</dd>
						</dl>
						<dl class="fore2">
							<dt><a href="#">孕产/育儿</a></dt>
							<dd><em><a href="#">孕前准备</a></em><em><a href="#">孕期</a></em><em><a href="#">胎教</a></em><em><a href="#">孕产妇健康</a></em><em><a href="#">孕产妇饮食指导</a></em><em><a href="#">产后管理</a></em><em><a href="#">婴幼儿饮食营养</a></em><em><a href="#">婴儿抚触</a></em><em><a href="#">婴幼儿护理健康</a></em><em><a href="#">育儿百科</a></em><em><a href="#">早教/亲子互动</a></em><em><a href="#">崔玉涛</a></em>							</dd>
						</dl>
						<dl class="fore3">
							<dt><a href="#">烹饪/美食</a></dt>
							<dd><em><a href="#">家常菜谱</a></em><em><a href="#">饮食文化</a></em><em><a href="#">烘焙</a></em><em><a href="#">药膳食疗</a></em><em><a href="#">西餐料理</a></em><em><a href="#">茶酒饮料</a></em><em><a href="#">地方美食</a></em><em><a href="#">面包</a></em><em><a href="#">蛋糕</a></em><em><a href="#">汤</a></em><em><a href="#">早餐</a></em><em><a href="#">日韩料理</a></em>							</dd>
						</dl>
						<dl class="fore4">
							<dt><a href="#">时尚/美妆</a></dt>
							<dd><em><a href="#">时尚</a></em><em><a href="#">健身美体</a></em><em><a href="#">瑜伽</a></em><em><a href="#">奢侈品</a></em>							</dd>
						</dl>
						<dl class="fore5">
							<dt><a href="#">旅游/地图</a></dt>
							<dd><em><a href="#">旅游随笔</a></em><em><a href="#">自助游</a></em><em><a href="#">户外探险</a></em><em><a href="#">旅游攻略</a></em><em><a href="#">孤独星球</a></em><em><a href="#">地图</a></em>							</dd>
						</dl>
						<dl class="fore6">
							<dt><a href="#">家居</a></dt>
							<dd><em><a href="#">家装家饰</a></em><em><a href="#">家装方法指导</a></em><em><a href="#">家饰窍门</a></em><em><a href="#">英文原版书</a></em><em><a href="#">家庭园艺</a></em><em><a href="#">多肉</a></em><em><a href="#">宠物</a></em><em><a href="#">生活指南</a></em><em><a href="#">收纳</a></em><em><a href="#">爱车一族</a></em>							</dd>
						</dl>
						<dl class="fore7">
							<dt><a href="#">家教</a></dt>
							<dd><em><a href="#">家教理论</a></em><em><a href="#">家教方案</a></em><em><a href="#">素质教育</a></em><em><a href="#">心理疏导</a></em><em><a href="#">培育男孩</a></em><em><a href="#">培育女孩</a></em><em><a href="#">成功案例</a></em><em><a href="#">品格养成</a></em><em><a href="#">亲子关系</a></em><em><a href="#">0-6岁</a></em><em><a href="#">7-12岁</a></em><em><a href="#">13-18岁</a></em>							</dd>
						</dl>
						<dl class="fore8">
							<dt><a href="#">婚恋两性</a></dt>
							<dd><em><a href="#">两性关系</a></em><em><a href="#">恋爱</a></em><em><a href="#">婚姻</a></em><em><a href="#">性</a></em>							</dd>
						</dl>
						<dl class="fore9">
							<dt><a href="#">健身/保健</a></dt>
							<dd><em><a href="#">心理健康</a></em><em><a href="#">中医养生</a></em><em><a href="#">药膳食疗</a></em><em><a href="#">运动健康</a></em><em><a href="#">中老年养生</a></em><em><a href="#">性保健</a></em><em><a href="#">健康百科</a></em><em><a href="#">饮食健康</a></em><em><a href="#">男性养生</a></em><em><a href="#">女性/儿童</a></em><em><a href="#">针灸艾灸</a></em><em><a href="#">养生</a></em><em><a href="#">瑜伽/美体</a></em><em><a href="#">上班族</a></em>							</dd>
						</dl>
						<dl class="fore10">
							<dt><a href="#">体育/运动</a></dt>
							<dd><em><a href="#">田径/体操</a></em><em><a href="#">球类</a></em><em><a href="#">奥林匹克</a></em><em><a href="#">太极/武术/气功</a></em><em><a href="#">围棋</a></em><em><a href="#">中国象棋</a></em><em><a href="#">国际象棋</a></em>							</dd>
						</dl>
						<dl class="fore11">
							<dt><a href="#">手工DIY</a></dt>
							<dd><em><a href="#">刺绣/十字绣</a></em><em><a href="#">纸艺</a></em><em><a href="#">布艺/不织布</a></em><em><a href="#">手工材料</a></em><em><a href="#">毛线编织</a></em><em><a href="#">服装DIY</a></em><em><a href="#">串珠</a></em><em><a href="#">推理游戏</a></em><em><a href="#">男性养生</a></em><em><a href="#">数独</a></em><em><a href="#">魔术</a></em>							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="item">
				<h3><a href="" class="STYLE2">人文社科</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">历史</a></dt>
							<dd><em><a href="#">中国史</a></em><em><a href="#">世界史</a></em><em><a href="#">遗闻野史</a></em><em><a href="#">地方史志</a></em><em><a href="#">史家名著</a></em><em><a href="#">口述史</a></em><em><a href="#">历史普及读物</a></em><em><a href="#">文物考古</a></em><em><a href="#">史学理论</a></em><em><a href="#">历史随笔</a></em><em><a href="#">史料典籍</a></em><em><a href="#">名族史</a></em><em><a href="#">文物考古</a></em><em><a href="#">热点事件</a></em><em><a href="#">风俗习惯</a></em>							</dd>
						</dl>
						<dl class="fore2">
							<dt><a href="#">文化</a></dt>
							<dd><em><a href="#">文化评述</a></em><em><a href="#">地域文化</a></em><em><a href="#">中国文化</a></em><em><a href="#">各国文化</a></em><em><a href="#">文化随笔</a></em><em><a href="#">神秘文化</a></em><em><a href="#">文化史</a></em><em><a href="#">文化研究</a></em><em><a href="#">文化专题</a></em>							</dd>
						</dl>
						<dl class="fore3">
							<dt><a href="#">古籍</a></dt>
							<dd><em><a href="#">经部</a></em><em><a href="#">子部</a></em><em><a href="#">集部</a></em><em><a href="#">古籍整理</a></em><em><a href="#">古籍工具书</a></em><em><a href="#">善本影印本</a></em><em><a href="#">国家名家</a></em><em><a href="#">普及读物</a></em><em><a href="#">线装书</a></em>							</dd>
						</dl>
						<dl class="fore4">
							<dt><a href="#">心理学</a></dt>
							<dd><em><a href="#">社会心理学</a></em><em><a href="#">人格心理学</a></em><em><a href="#">心理学著作</a></em><em><a href="#">心理学入门</a></em><em><a href="#">心理质询治疗</a></em><em><a href="#">心理学理论</a></em><em><a href="#">色彩心理学</a></em><em><a href="#">积极心理学</a></em><em><a href="#">儿童心理学</a></em><em><a href="#">心灵疗愈</a></em><em><a href="#">人类心理学</a></em><em><a href="#">大众心理学</a></em><em><a href="#">应用心理学</a></em><em><a href="#">青少年心理学</a></em><em><a href="#">心理健康</a></em>							</dd>
						</dl>
						<dl class="fore5">
							<dt><a href="#">哲学/宗教</a></dt>
							<dd><em><a href="#">基督教</a></em><em><a href="#">道教</a></em><em><a href="#">哲学经典著作</a></em><em><a href="#">世界哲学</a></em><em><a href="#">哲学与人生</a></em><em><a href="#">佛教</a></em><em><a href="#">周易</a></em><em><a href="#">伦理学</a></em><em><a href="#">美学</a></em><em><a href="#">哲学史</a></em><em><a href="#">宗教知识读物</a></em><em><a href="#">术数/命理</a></em><em><a href="#">哲学理论与流派</a></em><em><a href="#">逻辑学</a></em>							</dd>
						</dl>
						<dl class="fore6">
							<dt><a href="#">政治/军事</a></dt>
							<dd><em><a href="#">中国政治</a></em><em><a href="#">党政读物</a></em><em><a href="#">世界政治</a></em><em><a href="#">中国共产党</a></em><em><a href="#">军事史</a></em><em><a href="#">世界各国军事</a></em><em><a href="#">政治理论</a></em><em><a href="#">公共管理</a></em><em><a href="#">领袖著作</a></em><em><a href="#">时事政治</a></em><em><a href="#">外交/国家关系</a></em><em><a href="#">领袖首脑</a></em><em><a href="#">军事理论</a></em><em><a href="#">中外战争纪实</a></em><em><a href="#">军事技术</a></em><em><a href="#">政治经典著作</a></em><em><a href="#">入党</a></em><em><a href="#">党史</a></em><em><a href="#">党章</a></em><em><a href="#">党建</a></em><em><a href="#">战略战术战役</a></em>							</dd>
						</dl>
						<dl class="fore7">
							<dt><a href="#">社会科学</a></dt>
							<dd><em><a href="#">社会学</a></em><em><a href="#">语言文学</a></em><em><a href="#">社会科学总论</a></em><em><a href="#">人类学</a></em><em><a href="#">新闻传播</a></em><em><a href="#">名家作品集</a></em><em><a href="#">经典著作</a></em><em><a href="#">教育</a></em>							</dd>
						</dl>
						<dl class="fore8">
							<dt><a href="#">法律</a></dt>
							<dd><em><a href="#">民法</a></em><em><a href="#">法律法规</a></em><em><a href="#">刑法</a></em><em><a href="#">法律实务</a></em><em><a href="#">经济法</a></em><em><a href="#">理论法学</a></em><em><a href="#">法律随笔</a></em><em><a href="#">经典著作</a></em><em><a href="#">法律史</a></em><em><a href="#">司法案例与司法解释</a></em><em><a href="#">普及读物</a></em><em><a href="#">司法考试</a></em>							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="item">
				<h3><a href="" class="STYLE2">经管</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">经管</a></dt>
							<dd><em><a href="#">战略管理</a></em><em><a href="#">MBA</a></em><em><a href="#">商务沟通</a></em><em><a href="#">管理学</a></em><em><a href="#">市场/营销</a></em><em><a href="#">生产与运作</a></em><em><a href="#">信息管理</a></em><em><a href="#">会计</a></em><em><a href="#">创业与企业</a></em><em><a href="#">商业史传</a></em><em><a href="#">电子商务</a></em><em><a href="#">工具书</a></em><em><a href="#">管理类考试</a></em><em><a href="#">外文原版/影印版</a></em><em><a href="#">经管音像</a></em><em><a href="#">英文原版书</a></em>							</dd>
						</dl>
						<dl class="fore2">
							<dt><a href="#">投资理财</a></dt>
							<dd><em><a href="#">基金</a></em><em><a href="#">期货</a></em><em><a href="#">外汇</a></em><em><a href="#">保险</a></em><em><a href="#">女性理财</a></em><em><a href="#">证券/股票</a></em><em><a href="#">投资指南</a></em><em><a href="#">黄金贵金属</a></em><em><a href="#">理财技巧</a></em><em><a href="#">购房置业</a></em><em><a href="#">纳税</a></em><em><a href="#">英文原版书</a></em><em><a href="#">小额借贷</a></em><em><a href="#">P2P</a></em>							</dd>
						</dl>
						<dl class="fore3">
							<dt><a href="#">经济</a></dt>
							<dd><em><a href="#">中国经济</a></em><em><a href="#">财政税收</a></em><em><a href="#">经济史</a></em><em><a href="#">世界经济</a></em><em><a href="#">统计/审计</a></em><em><a href="#">国际贸易</a></em><em><a href="#">经济法</a></em><em><a href="#">经济通俗读物</a></em><em><a href="#">经济学理论</a></em><em><a href="#">通货膨胀</a></em><em><a href="#">各部门经济</a></em><em><a href="#">保险</a></em><em><a href="#">工具书</a></em><em><a href="#">财税外贸保险</a></em><em><a href="#">英文原版书</a></em><em><a href="#">经济著作</a></em><em><a href="#">货币银行</a></em><em><a href="#">国际金融</a></em><em><a href="#">职业资格</a></em><em><a href="#">行业经济</a></em>							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="item">
				<h3><a href="" class="STYLE2">科技</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">科普读物</a></dt>
							<dd><em><a href="#">人类股市</a></em><em><a href="#">生物世界</a></em><em><a href="#">百科知识</a></em><em><a href="#">宇宙知识</a></em><em><a href="#">科学世界</a></em><em><a href="#">生态环境</a></em><em><a href="#">英文原版书</a></em><em><a href="#">数理化</a></em><em><a href="#">儿童科普</a></em>							</dd>
						</dl>
						<dl class="fore2">
							<dt><a href="#">建筑</a></dt>
							<dd><em><a href="#">标准/规范</a></em><em><a href="#">建筑史与建筑</a></em><em><a href="#">建筑科学</a></em><em><a href="#">建筑设计</a></em><em><a href="#">室内设计/装修</a></em><em><a href="#">建筑施工与鉴赏</a></em><em><a href="#">城乡规划</a></em><em><a href="#">园林景观</a></em><em><a href="#">建筑教材</a></em><em><a href="#">职业资格考试</a></em><em><a href="#">英文原版书</a></em><em><a href="#">建筑结构</a></em><em><a href="#">建筑制图</a></em><em><a href="#">建筑工程经济与管理</a></em><em><a href="#">一建</a></em><em><a href="#">二建</a></em>							</dd>
						</dl>
						<dl class="fore3">
							<dt><a href="#">药学</a></dt>
							<dd><em><a href="#">中医</a></em><em><a href="#">护理学</a></em><em><a href="#">药学</a></em><em><a href="#">临床医学</a></em><em><a href="#">内科</a></em><em><a href="#">外科</a></em><em><a href="#">儿科</a></em><em><a href="#">妇产科</a></em><em><a href="#">医学/药学考试</a></em><em><a href="#">预防医学</a></em><em><a href="#">其他临床医学</a></em><em><a href="#">药学图书</a></em><em><a href="#">医院管理</a></em><em><a href="#">医疗器材及使用</a></em><em><a href="#">英文原版书</a></em><em><a href="#">中医古典</a></em><em><a href="#">基础医学</a></em><em><a href="#">执业医师</a></em><em><a href="#">执业药师</a></em>							</dd>
						</dl>
						<dl class="fore4">
							<dt><a href="#">计算机/网络</a></dt>
							<dd><em><a href="#">数据库</a></em><em><a href="#">操作系统</a></em><em><a href="#">网络与通信</a></em><em><a href="#">图形图像多媒体</a></em><em><a href="#">家庭与办公软件</a></em><em><a href="#">计算机理论</a></em><em><a href="#">人工智能</a></em><em><a href="#">数码全攻略</a></em><em><a href="#">程序设计</a></em><em><a href="#">地理信息管理</a></em><em><a href="#">行业软件及应用</a></em><em><a href="#">企业软件开发</a></em><em><a href="#">信息安全</a></em><em><a href="#">计算机教材</a></em><em><a href="#">电脑杂志</a></em><em><a href="#">英文原版书</a></em>							</dd>
						</dl>
						<dl class="fore5">
							<dt><a href="#">农林/农业</a></dt>
							<dd><em><a href="#">农业基础科学</a></em><em><a href="#">园艺</a></em><em><a href="#">林业</a></em><em><a href="#">畜牧/狩猎/蚕</a></em><em><a href="#">水产/渔业</a></em><em><a href="#">农业工程</a></em><em><a href="#">植物保护</a></em><em><a href="#">农作物</a></em><em><a href="#">动物保护</a></em><em><a href="#">农林影像</a></em>							</dd>
						</dl>
						<dl class="fore6">
							<dt><a href="#">自然科学</a></dt>
							<dd><em><a href="#">总论</a></em><em><a href="#">数学</a></em><em><a href="#">物理学</a></em><em><a href="#">力学</a></em><em><a href="#">化学</a></em><em><a href="#">生物学</a></em><em><a href="#">地球科学</a></em><em><a href="#">环境科学</a></em><em><a href="#">天文学</a></em><em><a href="#">科技史</a></em><em><a href="#">自然科学类考试</a></em><em><a href="#">英文原版书</a></em><em><a href="#">地理学</a></em>							</dd>
						</dl>
						<dl class="fore7">
							<dt><a href="#">工业技术</a></dt>
							<dd><em><a href="#">一般工业技术</a></em><em><a href="#">化学工业</a></em><em><a href="#">机械/仪表工</a></em><em><a href="#">电器电工</a></em><em><a href="#">金属学与金属</a></em><em><a href="#">轻工业/手工</a></em><em><a href="#">汽车与交通运输</a></em><em><a href="#">冶金工业</a></em><em><a href="#">矿业工程</a></em><em><a href="#">石油/天然气</a></em><em><a href="#">武器工业</a></em><em><a href="#">能源与动力工程</a></em><em><a href="#">原子能技术</a></em><em><a href="#">水利工程</a></em><em><a href="#">航空/航天</a></em><em><a href="#">环境科学</a></em><em><a href="#">安全科学</a></em><em><a href="#">工具书/标准</a></em><em><a href="#">原版书</a></em><em><a href="#">英文原版书</a></em><em><a href="#">自动化技术</a></em>							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="item">
				<h3><a href="" class="STYLE2">教育</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">教材</a></dt>
							<dd><em><a href="#">高职高专教材</a></em><em><a href="#">成人教育教材</a></em><em><a href="#">研究生/本科</a></em><em><a href="#">中职教材</a></em><em><a href="#">职业技术培训</a></em><em><a href="#">公共课</a></em><em><a href="#">经济管理</a></em><em><a href="#">工学类</a></em><em><a href="#">文法类</a></em><em><a href="#">医学类</a></em><em><a href="#">理学类</a></em><em><a href="#">农学类</a></em><em><a href="#">自考教材</a></em>							</dd>
						</dl>
						<dl class="fore2">
							<dt><a href="#">外语</a></dt>
							<dd><em><a href="#">英语读物</a></em><em><a href="#">日语</a></em><em><a href="#">法语</a></em><em><a href="#">韩语</a></em><em><a href="#">小语种</a></em><em><a href="#">口语</a></em><em><a href="#">IELTS</a></em><em><a href="#">托福TOEFL</a></em><em><a href="#">托业TOEIC</a></em><em><a href="#">GRE/GMAT</a></em><em><a href="#">剑桥商务英语考试BEC</a></em><em><a href="#">职业/行业英语</a></em><em><a href="#">专四/专八</a></em><em><a href="#">大学英语四/六级</a></em><em><a href="#">英语综合教程</a></em><em><a href="#">英语专项训练</a></em><em><a href="#">英语考试</a></em><em><a href="#">英语工具书</a></em><em><a href="#">新概念</a></em><em><a href="#">听力</a></em><em><a href="#">英文版读物</a></em><em><a href="#">STA</a></em><em><a href="#">翻译资格</a></em><em><a href="#">对外汉语</a></em><em><a href="#">大众英语</a></em><em><a href="#">英语词汇</a></em><em><a href="#">品牌英语</a></em><em><a href="#">留学指南</a></em>							</dd>
						</dl>
						<dl class="fore3">
							<dt><a href="#">考试</a></dt>
							<dd><em><a href="#">学历考试</a></em><em><a href="#">计算机</a></em><em><a href="#">艺术/体育</a></em><em><a href="#">考研</a></em><em><a href="#">MBA/MPA/MPACC</a></em><em><a href="#">会计类考试</a></em><em><a href="#">建筑类考试</a></em><em><a href="#">医师资格</a></em><em><a href="#">公职</a></em><em><a href="#">财税外贸保险</a></em><em><a href="#">建筑工程</a></em><em><a href="#">医药卫生</a></em><em><a href="#">公务员</a></em><em><a href="#">事业单位</a></em><em><a href="#">卫生职称</a></em><em><a href="#">人力资源</a></em><em><a href="#">其他资格/职称考试</a></em><em><a href="#">银行类考试</a></em><em><a href="#">注册税务</a></em><em><a href="#">教师类考试</a></em>							</dd>
						</dl>
						<dl class="fore4">
							<dt><a href="#">中小学辅导</a></dt>
							<dd><em><a href="#">一年级</a></em><em><a href="#">二年级</a></em><em><a href="#">三年级</a></em><em><a href="#">四年级</a></em><em><a href="#">五年级</a></em><em><a href="#">六年级</a></em><em><a href="#">小学通用</a></em><em><a href="#">小学升初中</a></em><em><a href="#">七年级</a></em><em><a href="#">八年级</a></em><em><a href="#">九年级</a></em><em><a href="#">初中通用</a></em><em><a href="#">中考</a></em><em><a href="#">高一</a></em><em><a href="#">高二</a></em><em><a href="#">高三</a></em><em><a href="#">高中通用</a></em><em><a href="#">高考</a></em><em><a href="#">中小学阅读</a></em><em><a href="#">英语专项</a></em><em><a href="#">语文作业</a></em><em><a href="#">工具书</a></em><em><a href="#">写字/字帖</a></em><em><a href="#">教育理论</a></em><em><a href="#">学习方法/报考指南</a></em>							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="item">
				<h3><a href="" class="STYLE2">工具书</a></h3>
				<div class="item-list clearfix">
					<div class="subitem">
						<dl class="fore1">
							<dt><a href="#">工具书</a></dt>
							<dd><em><a href="#">汉语工具书</a></em><em><a href="#">英语工具书</a></em><em><a href="#">其他语种工具书</a></em><em><a href="#">百科全书/年鉴</a></em><em><a href="#">文学鉴赏辞典</a></em><em><a href="#">牛津系列</a></em><em><a href="#">朗文系列</a></em><em><a href="#">民族语工具书</a></em><em><a href="#">英文原版书-工具书</a></em>							</dd>
						</dl>	
					</div>
				</div>
			</div>
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
