<?php
require_once 'include/Config.php';
$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);
$response = array("success" => false);
if (isset($_POST['userprofile']) && isset($_POST['username']) && isset($_POST['date']) && isset($_POST['status']) && isset($_POST['category']) && isset($_POST['suggestion'])){
	$userprofile=$_POST['userprofile'];
	$username=$_POST['username'];
	$date=$_POST['date'];
	$status=$_POST['status'];
	$category=$_POST['category'];
	$suggestion=$_POST['suggestion'];
	$sql="INSERT INTO `feedbacks`(`userprofile`, `username`, `date`, `status`, `category`, `suggestion`) VALUES (\"$userprofile\",\"$username\",\"$date\",\"$status\",\"$category\",\"$suggestion\")"; 
	$res = mysqli_query($con,$sql);
	if($res === FALSE) { 
			die(mysqli_error($con)); // TODO: better error handling
	}
	$response = array("success"=>true);
if (isset($_POST['comments']) && isset($_POST['id'])){
	$comments=$_POST['comments'];
	$id = $_POST['id'];
	$sql ="UPDATE `feedbacks` SET `comments`= \"$comments\" WHERE `id`= \"$id\"";
	$res = mysqli_query($con,$sql);
	
	if($res === FALSE) { 
			die(mysqli_error($con)); // TODO: better error handling
	}
	$response = array("success"=>true);
	}
}
echo json_encode($response);
?> 