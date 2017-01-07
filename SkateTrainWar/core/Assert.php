<?php
class Assert{
	static function logined(){
//		session_start();
//		if(!isset($_SESSION['user'])){
//			@header('HTTP/1.0 401.4 - Authorization failed by filter.');
//			exit(0);
//		} 
//		return $_SESSION['user'];
		return array("id"=>"1");
	}	
}
