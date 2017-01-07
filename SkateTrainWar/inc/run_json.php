<?php
require_once APP_ROOT."/core/Utils.php";	
require_once APP_ROOT."/core/Dao.php";	
require_once APP_ROOT."/core/Json.php";	
require_once APP_ROOT."/core/Assert.php";	

//log start
ini_set('date.timezone','Asia/Shanghai');
$log = date('h:i:s',time())."\r\n";
$headers = apache_request_headers();
foreach ($headers as $key => $value) {
	$log .= "[header]{$key}={$value}\r\n";
}
$log .= "[URL]".$_SERVER['REQUEST_URI']."\r\n";
//log end

try {
	$dao = new Dao();
	$dao->init('mysql:host=127.0.0.1;dbname=skatetrainapp', "root", "");
	$t = array(
		"dao"=>$dao,
		"request"=>$_REQUEST
	);
	
	
	$json = run($t);
 	echo $json;
	$log .= '[return]'.$json;
	
 	$dao->close(true);
}catch(Exception $e){
	echo  'error:' . $e->getMessage();
	$dao->endTransaction(false);	
}	

 




//log start
$path = dirname(__FILE__);
$myfile = fopen($path.'/../log/runlog.'.date("Ymd").'.txt', 'a') or die("Unable to open file!");
fwrite($myfile,  $log."\r\n\r\n");
fclose($myfile);
//log end