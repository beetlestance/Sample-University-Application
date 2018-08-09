<?php
 
$event = array();
 
// include db connect class
require_once 'include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
$conn = $db->connect();

if (isset($_POST['uid'])){
	
	$uid = $_POST['uid'];
 
// get all products from products table
$result = mysqli_query($conn,"SELECT *FROM events WHERE uid = $uid");
 
$result = mysqli_fetch_array($result);
    // looping through all results
    // products node
	    $event["title"] = $result["title"];
        $event["genre"] = $result["genre"];
        $event["venue"] = $result["venue"];
		$event["description"] = $result["description"];
		$event["count"] = $result["count"];
		$event["image"] = $result["image"];
		$event["likes"] = $result["likes"];
		$event["startingdate"] = $result["startingdate"];
		$event["endingdate"] = $result["endingdate"];
		$event["startingtime"] = $result["startingtime"];
		$event["endingtime"] = $result["endingtime"];
		$event["circleimage"] = $result["circleimage"];
		$event["organisername"] = $result["organiserName"];
 
        // push single product into final response array
    // echoing JSON response
    echo json_encode($event);
}else{
	$event["message"] = "Required parameter is missing";
}
?>