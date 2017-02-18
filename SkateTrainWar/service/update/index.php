<?php

define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";
function run($t){
	$release_baseurl = "http://114.55.110.94/SkateTrainFile/release/";
	echo '
		 {
			"version": "'.SYS_VERSION.'",
			"title": "版本更新",
			"note": "'.SYS_VERSION.'\r\n'.SYS_VERSION_NOTE.'",
			"url": "'.$release_baseurl.SYS_APPNAME.'.'.SYS_VERSION.'_signed.apk",
			"wgt": "'.$release_baseurl.SYS_APPNAME.'.wgt"
		}
	 ';
}	 
?>