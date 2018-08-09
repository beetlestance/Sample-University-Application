<?php
require_once 'include/Config.php';
 
$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);
if (isset($_POST['department'])){
	$department_name=$_POST['department'];
}

$sql="SELECT `Name`, `Profile Pic Url`, `Designation`, `Contact`, `E-mail` FROM `faculty members` WHERE `Department`= \"$department_name\""; 
 
$res = mysqli_query($con,$sql);
if($res === FALSE) { 
    die(mysqli_error($con)); // TODO: better error handling
}
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('name'=>$row[0],
'profile_pic'=>$row[1],
'designation'=>$row[2],
'phone'=>$row[3],
'email'=>$row[4]
));
}
 
echo json_encode(array("contacts"=>$result));
?> 