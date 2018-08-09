<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['roll']) && isset($_POST['password'])) {
 
    // receiving the post params
    $roll = $_POST['roll'];
    $password = $_POST['password'];
 
    // get the user by email and password
    $user = $db->getUserByEmailAndPassword($roll, $password);
 
    if ($user != false) {
        // user is found
        $response["error"] = FALSE;
        $response["roll"] = $user["RollNo"];
        $response["user"]["name"] = $user["fullname"];
        $response["user"]["email"] = $user["email"];
		$response["user"]["course"] = $user["course"];
		$response["user"]["dob"] = $user["dob_dt"];
		$response["user"]["phone"] = $user["phone"];
		$response["user"]["profileimage"] = $user["profileimage"];
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Login credentials are wrong. Please try again!";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters email or password is missing!";
    echo json_encode($response);
}
?>