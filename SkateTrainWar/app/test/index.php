<?php	
define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";

function run($t){
	$dao = $t['dao'];
	$dao->connect(false);
	$req = $t['request'];
	$id = $req['id'];	
	
	$list = $dao->queryBySql("select t.* from test t where t.a like :a",array("a"=>"a%"),null );
	return Json::encode($list);
}
?>