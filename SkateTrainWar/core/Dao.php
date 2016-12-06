<?php
class Dao{
	private $hosturl;
	private $user;
	private $psd;
	
	private $pdo;
	private $isAutoCommit;
	function init($hosturl,$user,$psd){
		$this->hosturl = $hosturl;
		$this->user = $user;
		$this->psd = $psd;
	} 
 
	function connect($transaction){
		$this->pdo = new PDO($this->hosturl, $this->user, $this->psd);
		$this->pdo->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);		
		$this->pdo->query('set names utf8;'); 
		$this->isAutoCommit = $transaction;
		$this->pdo->beginTransaction();		
	}
	function close($isCommit){
		//if($this->isAutoCommit){
		//	$this->conn->close();
		//}
		if($this->isAutoCommit){
			if($isCommit){
				$this->pdo->commit();
			}else{
				$this->pdo->rollback();
			}
		}
	}
	function getRowById($tableName,$id){
		$sql = "select * from {$tableName} t where t.id=:id";
		return $this->getRowBySql($sql, array('id'=>$id));
	}
	function getRowValById($tableName,$id,$key){
		$m = $this->getRowById($tableName, $id);
		return $m[$key];
	}
	function getRowBySql($sql,$param){
		$list = $this->queryBySql($sql,$param,null);
		foreach($list as $row){
			return $row;
		}
		return null;
	}
	function getRowValBySql($sql,$param,$key){
		$m = $this->getRowBySql($sql, $param);
		return $m[$key];
	}
	function queryBySql($sql,$params,$key){
		//var_dump($sql);
		$sth = $this->pdo->prepare($sql);
		foreach($params as $k=>$v){
			$sth->bindValue(':'.$k, $v);	
			//var_dump($k.'-'.$v);
		}
		$sth->execute();

		$result = $sth->fetchAll(PDO::FETCH_ASSOC);
		$ret = array();
		foreach($result as $row){
			$ret_item = array();	
			foreach($row as $k=>$v){
				$ret_item[$k]=$v;
			}
			if($key!=null){
				$ret[$row[$key]] = $ret_item;
			}else{
				$ret[] = $ret_item;
			}
			
		}
		//var_dump($ret);
		return $ret;
	}
	function insert($tableName,$values){
		$keystr = "";
		$valstr = "";
		$sep = "";
		foreach($values as $k=>$v){
			$keystr .= $sep.''.$k;
			$valstr .= $sep.':'.$k;
			$sep = ",";
		}
		$sql = "insert into {$tableName} ({$keystr}) values({$valstr})";
		$sth = $this->pdo->prepare($sql);
		foreach($values as $k=>$v){
			$sth->bindValue(':'.$k, $v);	
		}
		$sth->execute();
	
		return $this->pdo->lastInsertId();
	}
	function insert2($tableName,$values){
		$values['rec_create_time'] = date('Y-m-d H:i:s');
		$values['is_deleted'] = '0';
		$id = $this->insert($tableName, $values);
		return $this->getRowById($tableName,$id);
	}
	function update2($tableName,$values,$id){
		$values['rec_update_time'] = date('Y-m-d H:i:s');
		 
		$sql = "update {$tableName} set ";
		$sep = "";
		foreach($values as $k=>$v){
			$sql .=   " {$sep}{$k}=:{$k} ";
			$sep = ',';
		}
		$sql .= " where id=:id ";
		 
		$sth = $this->pdo->prepare($sql);
		foreach($values as $k=>$v){
			$sth->bindValue(':'.$k, $v);	
		}
		$sth->bindValue(':id', $id);
		$sth->execute();
		return $this->getRowById($tableName,$id);
	}
	function delete2($tableName,$id){
		$model = $this->getRowById($tableName, $id);
		
		if($model!=null){
			$model['is_deleted'] = '1';
			
			$this->update2($tableName,$model,$id);
		}
	}
 
}

/*
 * @usage
try {
	$dao = new Dao();
	$dao->init('mysql:host=127.0.0.1;dbname=traindaily', "root", "");
	$dao->connect(true);
	
 
	$model = array(
		"a"=>"a-".date('Y-m-d H:i:s'),
		'b'=>'199'
	);
	$model = $dao->insert2("test",$model);
	var_dump($model);
	
	$list = $dao->queryBySql("select t.* from test t where t.a like :a",array("a"=>"a%"),"a" );
	//var_dump($list);
	
	$row = $dao->getRowBySql("select t.* from test t where t.a like :a",array("a"=>"a%"));
	var_dump($row);
	
	
	$dao->close(true);
}catch(Exception $e){
	echo $sql . 'error:' . $e->getMessage();
	$dao->close(false);	
}
*/

