//---------------------player start
var mp3list;//播放列表
var player;//播放器
var currentIndex;//当前播放
//var datas = [{"id":"1","url":"http://www.baidu.com/1.mp3","title":"wanna you"}];
function setMp3List(datas){
	mp3list = eval(  datas );
	console.log(mp3list);  
}
function startPlayer(i){
	if(mp3list.length>0){
		var index = i % mp3list.length;
		$("#mp3_title").html(mp3list[index].title);
		if(player!=undefined){
			player.stop();	
			player = null;
		}
		var song_url = window._D.url_file+mp3list[index].song_file;
		//console.log(window._D.url_file+mp3list[index].song_file);
		
		//如果播放歌曲列表中有本地地址（download_filename），则播放本地地址
		var isLocal = false;
		var download_filename = mp3list[index].download_filename;
		console.log("download_filename="+download_filename);
		if(download_filename!=null && download_filename!=undefined){
			song_url = download_filename;
			isLocal = true;
		}else{
			//无本地地址，则到已经下载的列表中查找
			var ds1 = getSkateDownloadedData();
			console.log("ds1:"+JSON.stringify(ds1));
			console.log(window._D.url_file+mp3list[index].song_file);
			var i,j;
			for(i=0,j=ds1.length;i<j;i++){
				if(ds1[i].url==song_url){
					song_url = ds1[i].download_filename;
					isLocal = true;
					break;
				}
			}			
		}
		console.log("play:="+isLocal+"/"+song_url);
		if(isLocal==true){
			console.log("TODO-播放本地音乐");
//			plus.io.requestFileSystem( plus.io.PUBLIC_DOWNLOADS, function(fs){				
//				alert(fs.fullPath);
//				alert(song_url);
//			} );			
			player = plus.audio.createPlayer(song_url);
			player.play( function () {
				//alert( "Audio play success!" ); 
				nextPlayer();		
			}, function ( e ) { 
				alert( "Audio play error: " + e.message ); 
			} );
			$('.ic_play').hide();
			$('.ic_pause').show();		
		}else{	
			player = plus.audio.createPlayer(song_url);
			player.play( function () {
				//alert( "Audio play success!" ); 
				nextPlayer();		
			}, function ( e ) { 
				alert( "Audio play error: " + e.message ); 
			} );
			$('.ic_play').hide();
			$('.ic_pause').show();				
		}
		
	
			
		currentIndex = index;
	}
}
function pausePlayer(){
	if(player!=undefined){
		player.pause();
		$('.ic_play').show()
		$('.ic_pause').hide();
	}
}
function resumePlayer(){
	if(player!=undefined){
		player.resume();
		$('.ic_play').hide();
		$('.ic_pause').show();		
	}	
}
function nextPlayer(){
	startPlayer(currentIndex+1);
}
function previewPlayer(){
	startPlayer(currentIndex);
}
//--------------------player end





//====================download start
//var datas = [{"id":"1","url":"http://www.baidu.com/1.mp3","title":"wanna you","is_download_finish":"0"}];
var is_downloading = false;
//已经下载完的
function getSkateDownloadedData(){
	var datas = plus.storage.getItem(window._C.DOWNLOADED_FILE) || [];
	return eval(datas);
}
function addSkateDownloadedData(data){
	var datas = getSkateDownloadedData();
	datas.push(data);
	plus.storage.setItem(window._C.DOWNLOADED_FILE,JSON.stringify(datas));
} 
function setSkateDownloadedData(datas){
	plus.storage.setItem(window._C.DOWNLOADED_FILE,JSON.stringify(datas));
}
function clearSkateDownloadedData(){
	//删除文件夹 downfile  
	plus.io.requestFileSystem( plus.io.PUBLIC_DOWNLOADS, function(fs){
		var directoryReader = fs.root.createReader();
		directoryReader.readEntries( function( entries ){
			var i;
			for( i=0; i < entries.length; i++ ) {
				entries[i].remove();
			}
		}, function ( e ) {
			alert( "Read entries failed: " + e.message );
		} );
	} );	
	plus.storage.removeItem(window._C.DOWNLOADED_FILE);
}
 
//正在下载中的
function getSkateDownloadingData(){
	var datas = plus.storage.getItem(window._C.DOWNLOADING_FILE) || [];
	return eval(datas);
}
function setSkateDownloadingData(datas){
	plus.storage.setItem(window._C.DOWNLOADING_FILE,JSON.stringify(datas));
}
function clearSkateDownloadingData(){
	plus.downloader.clear();
	plus.storage.removeItem(window._C.DOWNLOADING_FILE);
}

//添加下载任务
function addDownloadSongTask(datas){
	var ds = getSkateDownloadingData();
	var ds1 = getSkateDownloadedData();
	var i,j;
	
	//如果正在下载中列表和下载完成的列表中多没，则新增下载列表中
	for(i=0,j=datas.length;i<j;i++){
		var d = datas[i];
		if(_.findIndex(ds, { url: d.url})>=0){
			break;
		}
		if(_.findIndex(ds1, { url: d.url})>=0){
			break;
		}
		//没有发现列表，
		d.url = window._D.url_file+d.url;
		ds.push(d);	
	}		
	

	setSkateDownloadingData(ds);
	console.log("downloadSong"+ds.length+"/"+ds1.length+"="+JSON.stringify(ds));
}
//下载歌曲
function downloadSong(){
	console.log("downloadSong="+is_downloading);
	//如果当前任务在执行中，则不重复触发下载任务
	if(is_downloading==true){
		return ; 
	}
	is_downloading = true;
 
	var ds = getSkateDownloadingData();
	//alert("to download filesize::"+ds.length);
	if(ds.length>0){
		var index = 0;
		console.log("Begin download File:"+JSON.stringify(ds));
		var url = ds[index].url.replaceAll("\\\\","/");
		var dtask = plus.downloader.createDownload( url, {method:"GET"}, function ( d, status ) {
			is_downloading = false;//标识当前没有任务下载中
			if ( status == 200 ) { 
				ds[index].download_filename = d.filename;
				//下载成功后，下载文件从待下载列表删除,加入到已下载列表中,
				addSkateDownloadedData(ds[index]);				
				setSkateDownloadingData(removeFromArray(ds,index));
				console.log(JSON.stringify(getSkateDownloadingData()));		
				console.log(JSON.stringify(getSkateDownloadedData()));	
				displayDownloadList();
				downloadSong();
			} else {
				ds[index].is_download_finish = "-1";
				setSkateDownloadingData(ds); 
			}  	
		});
	    dtask.addEventListener( "statechanged", function(task,status){	    	
	    	if(!dtask){return;}
	    	switch(task.state) {
	    		case 1: // 开始
	    			console.log( "开始下载..." );
	    		break;
	    		case 2: // 已连接到服务器
	    			console.log( "链接到服务器..." );
	    		break;
	    		case 3:	// 已接收到数据
	    			//console.log( "下载数据更新:" );
	    			//console.log( task.downloadedSize+"/"+task.totalSize );
	    			$("#"+mp3urlToId(task.url)).html(getSizeInfo(task.downloadedSize)+"/"+getSizeInfo(task.totalSize)+" M");
	    		break;
	    		case 4:	// 下载完成
	    			console.log( "下载完成！" );
	    			console.log( task.totalSize );
	    		break;
	    	}
	    } );
		dtask.start(); 
		//alert("dtask.start");
	}else{
		is_downloading = false;
	}
}
function getSizeInfo(i){
	if(i>2000){
		return (i/1000).toFixed(0);
	}else{
		return (i/1000).toFixed(1);;
	}
}
function removeFromArray(arr,index){  
    return arr.slice(0,index).concat(arr.slice(index+1,arr.length))  
}  
//mp3 url转id
function mp3urlToId(url){
	str = url.replaceAll("\\\\","/");
	str = str.substring(str.lastIndexOf("/")+1,str.lastIndexOf("."));
	str = escape(str); 
	str = str.replaceAll("%","");
	return str;
}
function displayDownloadList(){
 
	var p = "";
 
		p += "<h5 style='background-color:#efeff4;margin-left: 12px;'><img src='img/player.png' style='width: 0.8rem'> 下载中的音乐</h5><ul class='mui-table-view'>";
		var ds = getSkateDownloadingData();
		//alert(ds.length);
		var i,j;
		for(i=0,j=ds.length;i<j;i++){
			var d = ds[i];
			p += "<li class='mui-table-view-cell' '>"+d.title+"<span class='mui-badge mui-badge-primary' id='"+mp3urlToId(d.url)+"'>等待下载2</span></li>";
		}
		p += "</ul>";
		
		if(ds.length>0){
			p += "<div class='title' id='clearDownloadList' onClick='clear_downloadingline()'>清空下载队列</div>	";
		}else{
			p +="没有文件";
		}
 
	$("#ddpage").html(p);
}
//当前是否显示下载页面
var isDisplayDownloadPage = false;
function downloadPage(){
	console.log("isDisplayDownloadPage="+isDisplayDownloadPage);
	var vv = plus.webview.getWebviewById(activeTab);
	if(isDisplayDownloadPage==false){
		isDisplayDownloadPage = true;
		displayDownloadList();
		
		vv.hide();
	}else{
		isDisplayDownloadPage = false;
		displayDownloadList();
		
		vv.show();
	}
}

function clear_downloadingline(){
	var btnArray = ['取消', '确定'];
	mui.confirm('请确认是否清空下载队列','操作提示：', btnArray, function(e) {
		if (e.index == 1) {
			clearSkateDownloadingData();  
			displayDownloadList();
		} 
	});		
}
//====================download end