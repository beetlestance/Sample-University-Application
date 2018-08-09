<?php
 
$target_dir = "image/";
$temp = explode(".", $_FILES["file"]["name"]);
$target_file_name = $target_dir . $roll . "." . end($temp);

$response = array();
$roll = $_GET["roll"];

require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// Check if image file is a actual image or fake image
if (isset($_FILES["file"])) 
{
 if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name)) 
 {
  $db->storeImageUrl($roll,$target_file_name);
  $success = true;
  $message = "Successfully Uploaded";
 }
 else 
 {
  $success = false;
  $message = "Error while uploading";
 }
}
else 
{
 $success = false;
 $message = "Required Field Missing";
}

$response["success"] = $success;
$response["message"] = $message;
echo json_encode($response);

?>
