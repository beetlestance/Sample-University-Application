<?php
 
 
class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
	public function getEvents(){
		$stmt = $this->conn->prepare("SELECT * FROM events");
		if($stmt->execute()){
			$event = $stmt->get_result()->fetch_assoc();
			return $event;
		}
		else{
			return NULL;
		}
	
	}
	
	public function storeImageUrl($roll,$filename){
		$stmt = $this->conn->prepare("UPDATE studentdetails SET profileimage=? WHERE RollNo = ?");
        $stmt->bind_param("ss",$filename,$roll);
        $result = $stmt->execute();
        $stmt->close();
	}
 
	public function updateInformation($roll,$email,$phone,$newpass){
		
		if($newpass!=NULL){
			$hash = $this->hashSSHA($newpass);
			$encrypted_password = $hash["encrypted"];
			$salt = $hash["salt"];
			$stmt = $this->conn->prepare("UPDATE studentdetails SET email = ?, encrypted_pass = ?, salt=?, phone=? WHERE RollNo = ?");
			$stmt->bind_param("sssss",$email, $encrypted_password, $salt,$phone, $roll);
			$result = $stmt->execute();
			$stmt->close();
		}else{
			$stmt = $this->conn->prepare("UPDATE studentdetails SET email=?,phone=? WHERE RollNo = ?");
			$stmt->bind_param("sss",$email,$phone,$roll);
			$result = $stmt->execute();
			$stmt->close();
		}
	}	
	 /**
     * Storing new user
     * returns user details
     */
    public function storeUser($roll, $email, $password,$phone) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
 
        $stmt = $this->conn->prepare("UPDATE studentdetails SET email = ?, encrypted_pass = ?, salt=?, phone=? WHERE RollNo = ?");
        $stmt->bind_param("sssss",$email, $encrypted_password, $salt,$phone, $roll);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM studentdetails WHERE RollNo = ?");
            $stmt->bind_param("s", $roll);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($roll, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM studentdetails WHERE RollNo = ?");
 
        $stmt->bind_param("s", $roll);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            // verifying user password
            $salt = $user['salt'];
            $encrypted_password = $user['encrypted_pass'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }
	
	public function getRegisterationDetailFromRoll($roll,$father){
		$stmt = $this->conn->prepare("SELECT * FROM studentdetails WHERE RollNo = ?");
		$stmt->bind_param("s",$roll);
		$verify = array("father"=>FALSE);
		
		
		if($stmt->execute()){
			$rolldetails = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			
			if(strtolower($father) == strtolower($rolldetails['FatherName'])){
				$verify["father"]=TRUE;
			}
		}
		
		return $verify;
	}
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($roll) {
        $stmt = $this->conn->prepare("SELECT encrypted_pass from studentdetails WHERE RollNo = ?");
 
        $stmt->bind_param("s", $roll);
 
        if($stmt->execute()){
			$user = $stmt->get_result()->fetch_assoc();
			$stmt->close();
			
			if($user['encrypted_pass']!=NULL){
				return true;
			}else{
				return false;
			}
		}
 
        /*$stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }*/
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
 
}
 
?>