window._D = window._D||{};
//window._D.app_version = "1.1.0";
window._D.app_name = "轮滑教学视频";
window._D.image_under_construction = "/res/webdocs/images/under_construction.jpg";

window._C = window._C||{};
window._C.DOWNLOADED_FILE = "DOWNLOADED_FILE";
window._C.DOWNLOADING_FILE = "DOWNLOADING_FILE";

window._D.image_file = window._D.url_file + "_images"; 

window._T = {
	str_click_event :"click",
	//str_click_event :"tap", 
	ready:function(func){
		"use strict";
		var that = this;
		$(document).ready(function(){
			func();
		});
//		document.write ('<div id="div_qinquan"><a href="mailto:sprcore_server@163.com">本网站所有视频来自网络</a></div>');
//		document.write ('<div id="div_qinquan_email"><a href="mailto:chenshiming0802@163.com">给我邮件</a>&nbsp('+window._D.app_version+')</div>');
		if(window._D.debug==false){
			document.write ('<div id="div_la" style="text-align:center;font-size:11px;display:none"><script async="true" language="javascript" type="text/javascript" src="http://js.users.51.la/19019486.js"></script></a></div>');		
		}
	},
	_addUrlTs:function(url){
		var timestamp = (new Date()).valueOf();
		if(url.indexOf("?")>=0){
			url += "&";
		}else{
			url += "?";
		}
		return url+"_ts="+timestamp;  
	},
	ajax:function(url,json){
		url = _D.base_url +"app/"+url;
		url = this._addUrlTs(url);
		console.log(url);
		if(json.timeout==undefined){
			json.timeout = 10000;
		}
		if(json.error==undefined){
			json.error = function(xhr,type,errorThrown){	
				console.log(type);
			};
		}		
		console.log("ajax#url="+url);
		console.log("ajax#json="+JSON.stringify(json));
		mui.ajax(url,json);
	},
	ajax_json:function(url,sFunc){
		var that = this;

		this.ajax(url,{
			dataType:'json',
			type:'get',
			//headers:{"ssoToken":"abcdeg"},
			success:function(data){	
				console.log(JSON.stringify(data));
				sFunc(data);
			},
			error:function(e){
				switch(e.status){
					case 401:
						that.redirect("login.html?from="+escape(window.location.href));
						break;
					default:
				}
			 	console.log(JSON.stringify(e));
			}
		});			
	},
	getRongQi:function(){
		if(navigator.userAgent.indexOf("DingTalk")>=0){
			return "DingTalk";
		}else{
			return "";
		}
	},
	mui_init:function(json){
		if(json==undefined){
			json = {};	
		}
		var bottom = 0;
		if(json.subpages!=undefined && json.subpages[0].bottom!=undefined){
			bottom = json.subpages[0].bottom;
		}		
		if(json.swipeBack==undefined){
			json.swipeBack = false;
		}
		if(json.statusBarBackground==undefined){
			json.statusBarBackground = '#f7f7f7';
		}
		var top = "45px";
	
		if(json.subpages!=undefined && json.subpages[0].top!=undefined){
			top = json.subpages[0].top;	
		}		
		switch(this.getRongQi()){
			case "DingTalk":		
				top = "0px";
				$("header").css("height","0px");
				$("header").css("display","none");
				$(".mui-bar-nav~.mui-content").css("padding-top","0px");
				break;
		}
		if(json.subpages!=undefined && json.subpages[0].styles==undefined){
			json.subpages[0].styles = {
				top: top,
				bottom: bottom,
				bounce: 'vertical'
			};
		}
		mui.init(json);
	},
	bindtap:function(jqobjstr,func){
		$(jqobjstr).on(this.str_click_event,function(){
			func($(this));
		});			
	},
	//var html = _T.template("#item-template",{results:data});
//	template:function(jqobjstr,data){
//		var source   = $(jqobjstr).html();
//		var handleHelper = Handlebars.registerHelper("addOne",function(index){
//			return index+1;
//		});		
//		var template = Handlebars.compile(source);	
//		var context = data;
//		var html    = template(context);	
//		return html;
//	},
	open:function(json){
		mui.openWindow(json);	
	},
	redirect:function(url){
		//alert(url);
		window.location = url;
	},
	get:function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有 目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
   },
   back:function(){
   		mui.back();
   },
// bindBack:function(jqobjstr){
// 		this.bindtap(jqobjstr,function(){
// 			mui.back();
// 		});
// },
	/*绑定父窗口的返回*/
	bindTopViewBack:function(jqobjstr){
		var top = plus.webview.getTopWebview();
		var self = plus.webview.currentWebview();
		top.evalJS("_T._bindBackBySelfId(\""+jqobjstr+"\",\""+self.id+"\");");
	},
	/*用于子窗口调用父窗口的返回，给bindTopViewBack方法使用*/
   	_bindBackBySelfId:function(jqobjstr,selfid){
   		//alert("_bindBackBySelfId="+jqobjstr+"/"+selfid);
   		this.bindtap(jqobjstr,function(){
   			var wv = plus.webview.getWebviewById( selfid );
   			//alert(wv.id);
   			wv.evalJS("history.back(-1);");
   		});
   	},	
   	bindBack:function(jqobjstr,backurl){
   		if(backurl!=null && backurl!=undefined){
     		this.bindtap(jqobjstr,function(){
   				window.location = backurl;
   			});  			
   		}else{
   			this.bindtap(jqobjstr,function(){
   				mui.back();
   			});
   		}
   },
	bindBackFunc:function(jqobjstr,func){
     	this.bindtap(jqobjstr,function(){
   			func();
   		});  			
   },
   toast:function(str){
   		//mui.toast(str);
		mui.alert(str, '系统提示', function() {
		});   			
   },
   //如果中版本为全量更新，小版本为增量更新
	app_update:function() {
		var that = this;
		var baseinfo = "http://114.55.110.94:8080/SkateTrainWar/";
		var url = baseinfo+"service/update/"; 
		console.log("SYS_VERSION_CHECK="+url);
		
	    plus.runtime.getProperty(plus.runtime.appid,function(inf){
	        var currentVersion=inf.version;//window._D.app_version
	        console.log("当前应用版本："+currentVersion); 
	        
			mui.getJSON(url, {
				"appid": plus.runtime.appid,
				"version": plus.runtime.version,
				"imei": plus.device.imei
			}, function(data) {
				console.log("remote version:"+data.version+",local version:"+currentVersion);
				if (data!=null && data.version!=null && that.compareVersion(data.version,currentVersion)) {
					if(that.compareMiddleVersion(data.version,currentVersion)){
						console.log("全量更新," + currentVersion +"->" + data.version);
						//全量更新
						plus.nativeUI.confirm(data.note, function(event) {
							if (0 == event.index) {
								plus.runtime.openURL(data.url);
							}
						}, data.title+"("+plus.runtime.version+")", ["立即更新", "取　　消"]);							
					}else{
						console.log("增量更新," + currentVersion +"->" + data.version);
						//增量更新
						that.partInstall(data.wgt);
					}
				}
			});	        
	    });		
		

	},
	//比较版本号
	compareVersion:function (curV,reqV){  
	   if(curV && reqV){  
	      //将两个版本号拆成数字  
	      var arr1 = curV.split('.'),  
	          arr2 = reqV.split('.');  
	      var minLength=Math.min(arr1.length,arr2.length),  
	          position=0,  
	          diff=0;  
	      //依次比较版本号每一位大小，当对比得出结果后跳出循环（后文有简单介绍）  
	      while(position<minLength && ((diff=parseInt(arr1[position])-parseInt(arr2[position]))==0)){  
	          position++;  
	      }  
	      diff=(diff!=0)?diff:(arr1.length-arr2.length);  
	      //若curV大于reqV，则返回true  
	      return diff>0;  
	   }else{  
	      //输入为空  
	      console.log("版本号不能为空");  
	      return false;  
	   }  
	},
	//比较中版本号
	compareMiddleVersion:function(curV,reqV){
	      var curVb = curV.substr(0,curV.lastIndexOf("."));	
	      var reqVb = reqV.substr(0,reqV.lastIndexOf("."));
		  //alert(curVb+"/"+reqVb);
		  return this.compareVersion(curVb,reqVb);

	},
	
	//增量更新
	partInstall:function(wgtUrl){
		console.log("增量更新包："+wgtUrl);
		plus.downloader.createDownload( wgtUrl, {filename:"_doc/update/"}, function(d,status){
	        if ( status == 200 ) { 
	            console.log("下载wgt成功："+d.filename);
	            var path = d.filename;
			    plus.runtime.install(path,{},function(){
			        console.log("安装wgt文件成功！");
			    },function(e){
			        console.log("安装wgt文件失败["+e.code+"]："+e.message);
			    });	            
	       } else {
	            console.log("下载wgt失败！");
	        }
	    }).start();		
	},
	parseHtml:function(str){
		if(str=="" || str==null || str==undefined){
			return "";
		}
		var str = str.replaceAll("\\[\\[img:([0-9a-zA-Z_\\.\\\\]*)\\]\\]","<img class='html_img' src='"+window._D.image_file+"$1'/>");
		var text_blank = "&nbsp;&nbsp;&nbsp;&nbsp;";
		str = str.replaceAll("\n","<BR>"+text_blank);
		return str;
	},
};


//消息推送点击后触发
document.addEventListener( "plusready", function(){
	console.log("index message");
	plus.push.addEventListener( "click", function( msg ) {
		//msg.payload , msg.content , msg.title
		console.log("index click message:"+msg);
		var pp = eval('(' + msg.payload + ')');
		
		console.log("_T.open:"+pp.to);
		_T.open({
		    url:pp.to,
		    id:"is_course"
		});	
	}, false );
}, false );

String.prototype.replaceAll = function(s1,s2){ 
	return this.replace(new RegExp(s1,"gm"),s2); 
}