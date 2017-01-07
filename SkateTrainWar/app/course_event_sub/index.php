<?php	
define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";

function run($t){
	Assert::logined();
	$dao = $t['dao'];
	$dao->connect(false);
	$req = $t['request'];
	$course_event_id = $req['course_event_id'];	
	
	$model = $dao->getRowById('td_course_daily_event', $course_event_id);
	$model['film'] = $dao->getRowById('td_file', $model['film_file_id']);
	$model['film_file'] = $dao->getRowValById('td_file', $model['film_file_id'], 'file_path');
	$model['smallpic_file_file'] = $dao->getRowValById('td_file', $model['smallpic_file_id'], 'file_path');
	
	$list = $dao->queryBySql("SELECT t.*,(SELECT a.file_path FROM td_file a WHERE t.img_file_id=a.id AND a.is_deleted='0') img_file
 		FROM td_event_point t WHERE t.is_deleted='0'
  		and t.event_id=:course_event_id",array("course_event_id"=>$course_event_id),null );
	
	return Json::encode(array(
		'model'=>$model,
		'list'=>$list
	));
}
?>