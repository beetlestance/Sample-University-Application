<?php
 
$response = array();
 
// include db connect class
require_once 'include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
$conn = $db->connect();

// get all products from products table
$result = mysqli_query($conn,"SELECT *FROM events");
 
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["event"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $event = array();
        $event["uid"] = $row["uid"];
        $event["title"] = $row["title"];
        $event["genre"] = $row["genre"];
        $event["venue"] = $row["venue"];
	$event["description"] = $row["description"];
	$event["count"] = $row["count"];
	$event["likes"] = $row["likes"];
	$event["image"] = $row["image"];
	$event["circleimage"] = $row["circleimage"];
	$event["startingdate"] = $row["startingdate"];
	$event["startingtime"] = $row["startingtime"];
	$event["endingtime"] = $row["endingtime"];
	$event["endingdate"] = $row["endingdate"];

        // push single product into final response array
        array_push($response["event"], $event);
    }
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>