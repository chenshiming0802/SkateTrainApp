<?php
class Dao{
	private  $conn; 
	function test(){
		$this->conn = 'abc';
	}	
	function print2(){
		echo '->'.$this->conn.'';
	}
}


$dao = new Dao();
$dao->test();
$dao->print2();
?>