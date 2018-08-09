<?php
 
$response = array();
 
// include db connect class
require_once 'include/DB_Connect.php';
 
// connecting to db
$db = new DB_Connect();
$conn = $db->connect();

// get all products from products table
$result = mysqli_query($conn,"SELECT * FROM news ORDER BY UploadingDate DESC");
 
// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["news"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $news = array();
        $news["id"] = $row["id"];
        $news["uploadingdate"] = $row["UploadingDate"];
        $news["enddate"] = $row["EndDate"];
        $news["office"] = $row["Office"];
	$news["category"] = $row["Category"];
	$news["contents"] = $row["Contents"];
	$news["pdfurl"] = $row["pdfUrl"];
        // push single product into final response array
        array_push($response["news"], $news);
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