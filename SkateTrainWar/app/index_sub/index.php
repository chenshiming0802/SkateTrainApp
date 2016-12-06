<?php	
define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";
 

function run($t){
	$user = Assert::logined();
	$dao = $t['dao'];
	$dao->connect(false);
	$list = $dao->queryBySql("SELECT
		  t1.id           course_id,
		  t1.title,
		  a1.file_path,
		  (SELECT COUNT(*) FROM td_user_course a2 WHERE a2.course_id=t1.id AND a2.is_deleted='0') count_user
		FROM td_course t1
		  LEFT JOIN td_file a1
		    ON a1.is_deleted = '0'
		      AND t1.img_file_id = a1.id,
		  td_user_course t2
		WHERE t1.is_deleted = '0'
		    AND t2.is_deleted = '0'
		    AND t1.id = t2.course_id
		    AND t2.user_id=:user_id
				ORDER BY t1.no asc",array("user_id"=>$user['id']),null );
	return Json::encode($list);
}
?>