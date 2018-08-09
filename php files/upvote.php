<?php
 
$event = array();
 
require_once 'include/DB_Connect.php';
 
$db = new DB_Connect();
$conn = $db->connect();

if(isset($_GET['uid'])&&$_GET['count']){
	$uid = $_GET['uid'];
	$count = $_GET['count'];
	if($count=="1"){
	$result = mysqli_query($conn,"update feedbacks set comments=comments+1 where id=$uid");
	if($result){
		$count=mysqli_query($conn,"SELECT comments FROM feedbacks WHERE id=$uid");
		$count= mysqli_fetch_assoc($count);
		$event["comments"] = $count["comments"];
		echo json_encode($event);
	}
	}else{
	$result = mysqli_query($conn,"update feedbacks set comments=comments-1 where id=$uid");
	if($result){
		$count=mysqli_query($conn,"SELECT comments FROM feedbacks WHERE id=$uid");
		$count= mysqli_fetch_assoc($count);
		$event["comments"] = $count["comments"];
		echo json_encode($event);
	}
	}
}
?>