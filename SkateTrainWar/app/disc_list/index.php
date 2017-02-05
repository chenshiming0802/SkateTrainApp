<?php	

define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";
 

function run($t){

	$user = Assert::logined();
	$dao = $t['dao'];
	$dao->connect(false);
	$req = $t['request'];
	@$key_id = $req['key_id'];	
 

	$list_key = $dao->queryBySql("
		SELECT * FROM td_disc_key t1 WHERE t1.is_deleted='0' AND t1.is_display_header='1'
	",array(),null );	
	
	$w = "";
	if($key_id!=null&&$key_id!=""){
		$w = "and exists (select 1 from td_disc_article_key b1 where b1.key_id='".$key_id."' and b1.article_id=t1.id)";
	}
	$sql = "
		SELECT
		  t1.id,
		  t1.title,
		  t1.content_type,
		  t1.publish_time,
		  a1.file_path       small_file_path
		FROM td_disc_article t1
		  LEFT JOIN td_file a1
		    ON a1.is_deleted = '0'
		      AND t1.small_img_id= a1.id
		WHERE t1.is_deleted = '0'
		".$w."
		ORDER BY t1.id desc	
		limit 0,30
	";
//	var_dump($sql);
	$list = $dao->queryBySql($sql,array(),null );
	
	foreach($list as $k=>$v){
		$ll = $dao->queryBySql("
			SELECT t2.name
			FROM td_disc_article_key t1,
			  td_disc_key t2
			WHERE t1.key_id = t2.id
			    AND t1.is_deleted = '0'
			    AND t2.is_deleted = '0'
			    AND t1.article_id=:article_id
		",array("article_id"=>$v['id']),null );		
		
		$keys = '';
		foreach($ll as $kk=>$vv){
			$keys = $keys.' '.$vv['name'];
		}
		
		$list[$k]['keys'] = $keys;
	}
	return Json::encode(array("list_key"=>$list_key,"list"=>$list));
}
?>