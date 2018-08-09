<?php
require_once 'include/Config.php';
$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);
if (isset($_POST['uid']))
{
	$id = $_POST['uid'];
	$sql="SELECT `id`, `userprofile`, `username`, `date`, `comments`, `status`, `category`, `suggestion` FROM `feedbacks` WHERE `id`= \"$id\"";
	$res = mysqli_query($con,$sql);
	if($res === FALSE) { 
		die(mysqli_error($con)); // TODO: better error handling
	}
	$result = array();
	while($row = mysqli_fetch_array($res)){
	array_push($result,
	array('id'=>$row[0],
	'userprofile'=>$row[1],
	'username'=>$row[2],
	'date'=>$row[3],
	'comments'=>$row[4],
	'status'=>$row[5],
	'category'=>$row[6],
	'suggestion'=>$row[7]
	));
	}
	echo json_encode(array("feedbackarray"=>$result));
}
else
{
	$sql="SELECT `id`, `userprofile`, `username`, `date`, `comments`, `status`, `category`, `suggestion` FROM `feedbacks` ORDER BY id DESC"; 
	$res = mysqli_query($con,$sql);
	if($res === FALSE) { 
		die(mysqli_error($con)); // TODO: better error handling
	}
	$result = array();
	while($row = mysqli_fetch_array($res)){
	array_push($result,
	array('id'=>$row[0],
	'userprofile'=>$row[1],
	'username'=>$row[2],
	'date'=>$row[3],
	'comments'=>$row[4],
	'status'=>$row[5],
	'category'=>$row[6],
	'suggestion'=>$row[7]
	));
	}
	echo json_encode(array("feedbackarray"=>$result));
}
?> 