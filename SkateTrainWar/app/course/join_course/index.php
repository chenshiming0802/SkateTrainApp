<?php	
define("APP_ROOT",dirname(dirname(dirname(dirname(__FILE__)))));
require APP_ROOT."/inc/run_json.php";
//http://192.168.31.100/DailyTrainWar/app/course/join_course/?course_id=2&join_type=unjoin
function run($t){
	$user = Assert::logined();
	$dao = $t['dao'];
	$dao->connect(true);
	$req = $t['request'];
	
	$course_id = $req['course_id'];	
	$join_type = $req['join_type'];
	$user_id = $user['id'];
 
	$model = $dao->getRowBySql("select * from td_user_course t 
		where t.is_deleted='0' and  t.user_id=:user_id and t.course_id=:course_id", array('user_id'=>$user_id,'course_id'=>$course_id) ); 
	
	if($join_type=='join'){
		if($model==null){
			$model = array();
			$model['user_id'] = $user_id;
			$model['course_id'] = $course_id;
			$dao->insert2('td_user_course', $model);
		}
	}else if($join_type=='unjoin'){
		if($model!=null){
			$dao->delete2('td_user_course',$model['id']);
		}
	}
 
	
	return Json::encode(array());
}
?>