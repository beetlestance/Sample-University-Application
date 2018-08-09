<?php
#prep the bundle
define( 'API_ACCESS_KEY', 'App_id' );

class Firebase{
	
	#API access key from Google API's Console
    
	 public function send($to ,$message){
		 $fields = array(
			 'to' => $to,
			 'notification' =>$message,
		 );
		 return $this->sendNotification($fields);
	 }
     /*$msg = array
          (
		'body' 	=> 'Hello',
		'title'	=> 'GJU'
          );
	$fields = array
			(
				'to'		=> $registrationIds,
				'notification'	=> $msg
			);
	*/
	
	private function sendNotification($fields){
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		curl_close( $ch );
#Echo Result Of FireBase Server
return $result;
	}
}
?>
