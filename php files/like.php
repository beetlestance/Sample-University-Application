<?php
 
$event = array();
 
// include db connect class
require_once 'include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
$conn = $db->connect();

if(isset($_POST['uid'])&&$_POST['likes']){
	$uid = $_POST['uid'];
	$count = $_POST['likes'];
	if($count=="1"){
	$result = mysqli_query($conn,"update events set likes=likes+1 where uid=$uid");
	if($result){
		$count=mysqli_query($conn,"SELECT likes FROM events WHERE uid=$uid");
		$count= mysqli_fetch_assoc($count);
		$event["likes"] = $count["likes"];
		echo json_encode($event);
	}
	}else{
	$result = mysqli_query($conn,"update events set likes=likes-1 where uid=$uid");
	if($result){
		$count=mysqli_query($conn,"SELECT likes FROM events WHERE uid=$uid");
		$count= mysqli_fetch_assoc($count);
		$event["likes"] = $count["likes"];
		echo json_encode($event);
	}
	}
}else{
	echo "response success";
}
?>