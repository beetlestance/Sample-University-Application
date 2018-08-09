<?php
$response = array();

// include db connect class
require_once 'include/DB_connect.php';

// connecting to db
$db = new DB_Connect();
$conn = $db->connect();

// get all products from products table
$result = mysqli_query($conn,"SELECT *FROM announcements");

// check for empty result
if(mysqli_num_rows($result) > 0){
	// looping through all results
    	// products node
	$response["announcements"] = array();

	while($row = mysqli_fetch_array($result)){
		// temp user array
		$announcements = array();
		$announcements["uid"] = $row["uid"];
		$announcements["heading"] = $row["heading"];
		$announcements["pdfUrl"] = $row["pdfUrl"];

		array_push($response["announcements"] , $announcements);

	}


	echo json_encode($response);
}

else{

	$response["message"] = "No announcement found";
	echo json_encode($response);
}
?>