<?php	
define("APP_ROOT",dirname(dirname(dirname(__FILE__))));
require APP_ROOT."/inc/run_json.php";

function run($t){
	$user = Assert::logined();
	$dao = $t['dao'];
	$dao->connect(false);
	$req = $t['request'];
	
	$id = $req['id'];	

	
	$model = $dao->getRowBySql("
SELECT
  t1.*,
  a1.file_path filmimg_file_path,
  a1.file_type filmimg_file_type
FROM td_disc_article t1
  LEFT JOIN td_file a1
    ON a1.id = t1.filmimg_file_id
      AND a1.is_deleted = '0'
WHERE t1.is_deleted = '0'
    AND t1.id = :id		
	", array('id'=>$id)); 

	return Json::encode(array(
		'model'=>$model
	));
}
?>