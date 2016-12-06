//Handlebars.registerHelper('breaklines', function(text) {
//    text = Handlebars.Utils.escapeExpression(text);
//    text = text.toString();
//    text = text.replace(/(\\r\\n|\\n|\\r)/gm, '<br>');
//    return new Handlebars.SafeString(text);
//});

window._T = {
	str_click_event :"tap",
	//str_click_event :"tap",
	ready:function(func){
		"use strict";
		
		$(document).ready(function(){
			func();
		});
		
		document.write ('<div id="div_la" style="position: absolute;right: 0px;"><script language="javascript" type="text/javascript" src="http://js.users.51.la/19019486.js"></script></a></div>');
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
				console.log(e);
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
		if(json.swipeBack==undefined){
			json.swipeBack = false;
		}
		if(json.statusBarBackground==undefined){
			json.statusBarBackground = '#f7f7f7';
		}
		var top = "45px";
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
				bottom: 0,
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
   bindBack:function(jqobjstr){
   		this.bindtap(jqobjstr,function(){
   			mui.back();
   		});
   },
   toast:function(str){
   		//mui.toast(str);
		mui.alert(str, '系统提示', function() {
		});   			
   },
};
