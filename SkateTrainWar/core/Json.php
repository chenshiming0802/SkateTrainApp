<?php
class Json{
	static function encode($list){
		return json_encode($list);
	}
}

/* 
 * @usage

$list = array(
	array("a1"=>"aa1","b1"=>"bb1"),array("a1"=>"aa2","b1"=>"bb2")
);
echo (Json::encode($list));
 */	