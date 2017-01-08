<?php

define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";
function run($t){
	echo '
		 {
			"version": "'.SYS_VERSION.'",
			"title": "版本更新",
			"note": "'.SYS_VERSION.'\r\n'.SYS_VERSION_NOTE.'",
			"url": "'.SERVER_URL.'SkateTrainWar/release/'.SYS_APPNAME.'.'.SYS_VERSION.'.apk"
		}
	 ';
}	 
?>