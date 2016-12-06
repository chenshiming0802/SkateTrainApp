<?php	
define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";

function run($t){
	$user = Assert::logined();
	$dao = $t['dao'];
	$dao->connect(false);
	$req = $t['request'];
	
	$course_id = $req['course_id'];	
	$user_id = $user['id'];
	
	$model = $dao->getRowById("td_course", $course_id);
	$model['bigimg_file'] = $dao->getRowValBySql("select file_path from td_file t where t.id=:id", array("id"=>$model['bigimg_file_id']), 'file_path');
	$model['user_count'] = $dao->getRowValBySql("SELECT COUNT(*) user_count FROM td_user_course  t WHERE t.is_deleted='0' AND t.course_id=:course_id", array('course_id'=>$course_id),"user_count");
	$model['has_join'] = $dao->getRowValBySql("select count(*) has_join from td_user_course t 
			where t.is_deleted='0' and  t.user_id=:user_id and t.course_id=:course_id", array('user_id'=>$user_id,'course_id'=>$course_id), 'has_join'); 
 
	
	$list = $dao->queryBySql("select t.* from td_course_daily t where t.is_deleted='0' and t.course_id=:course_id", 
		array('course_id'=>$course_id), null);
	
	
	foreach($list as $k=>$v){
		$list[$k]['events'] = $dao->queryBySql("select t.* 
			,(SELECT a.file_path FROM td_file a WHERE t.smallpic_file_id=a.id AND a.is_deleted='0') smallpic_file
			from td_course_daily_event t where t.is_deleted='0' and t.course_daily_id=:course_daily_id",
			array("course_daily_id"=>$v['id']),null);
	}
	
	return Json::encode(array(
		'course'=>$model,
		'course_daily'=>$list,
	));
}
?>