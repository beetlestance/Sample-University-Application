<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
$verify = array();
 
if (isset($_POST['roll']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
    $email = $_POST['email'];
    $password = $_POST['password'];
	$father = $_POST['father'];
	$roll = $_POST['roll'];
	$phone = $_POST['phone'];
	
    // check if user is already existed with the same roll
    if ($db->isUserExisted($roll)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "Roll No. is already registered or not found!";
        echo json_encode($response);
    } else {
		
		$verify = $db->getRegisterationDetailFromRoll($roll,$father);
		if(!$verify["father"]){
			$response["error"] = TRUE;
			$response["error_msg"] = "Father name is incorrect";
			echo json_encode($response);
		}
		else{
        // create a new user
        $user = $db->storeUser($roll, $email, $password,$phone);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["roll"] = $user["RollNo"];
			$response["user"]["name"] = $user["fullname"];
            $response["user"]["email"] = $user["email"];
			$response["user"]["dob"] = $user["dob_dt"];
			$response["user"]["course"] = $user["course"];
			$response["user"]["phone"] = $user["phone"];
			$response["user"]["profileimage"] = $user["profileimage"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
			}
		}
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>