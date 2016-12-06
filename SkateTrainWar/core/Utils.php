<?php
function getHeaders(){
//	foreach ($_SERVER as $name => $value)   {
//		$headers = [];
////		if (substr($name, 0, 5) == 'HTTP_'){
//			$headers[str_replace(' ', '-', ucwords(strtolower(str_replace('_', ' ', substr($name, 5)))))] = $value;  
////		} 
//		return $headers;
//	}  
	return $_SERVER;
}