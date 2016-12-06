<?php	
define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";

function run($t){
	$user = Assert::logined();
	$dao = $t['dao'];
	$dao->connect(false);
 
	
	$sql = "SELECT t1.id course_id, t1.title,a1.file_path,
  		(SELECT COUNT(*) FROM td_user_course a2 WHERE a2.course_id=t1.id AND a2.is_deleted='0') count_user
 		FROM td_course t1 LEFT JOIN td_file a1 ON a1.is_deleted='0' AND t1.img_file_id=a1.id 
 		WHERE t1.is_deleted='0'
 		and not exists
 		( select 1 from td_user_course a where a.is_deleted='0' AND a.course_id=t1.id and a.user_id=:user_id )
 		ORDER BY t1.no asc";
	
	$list = $dao->queryBySql($sql,array('user_id'=>$user['id']),null );
	return Json::encode($list);
}
?>