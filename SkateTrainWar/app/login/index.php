<?php	
define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";
 

function run($t){
	$dao = $t['dao'];
	$dao->connect(false);
	$req = $t['request'];
	$loginNo = $req['loginNo'];		
	
	$user = $dao->getRowBySql("SELECT DISTINCT t1.id,t1.login_no,t1.user_name,t1.avator
FROM td_user t1,td_user_from t2
WHERE t1.id=t2.user_id
AND t1.is_deleted='0' 
AND t2.is_deleted='0'
AND t2.from_source='fosunlink'
AND t2.login_no=:login_no",array("login_no"=>$loginNo),null);
	session_start();
	$_SESSION['user'] = $user;
	return Json::encode($user);
}
?>