<?php
require_once 'include/Config.php';
 
$con = mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_DATABASE);

$sql="SELECT `EventId`, `Title`, `StartDate`, `StartTime`, `EndDate`, `EndTime`, `IsFullDay`, `IsHoliday`, `DateTime` FROM `calendar` WHERE 1"; 
 
$res = mysqli_query($con,$sql);
if($res === FALSE) { 
    die(mysqli_error($con)); // TODO: better error handling
}
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('EventId'=>$row[0],
'Title'=>$row[1],
'StartDate'=>$row[2],
'StartTime'=>$row[3],
'EndDate'=>$row[4],
'EndTime'=>$row[5],
'IsFullDay'=>$row[6],
'IsHoliday'=>$row[7],
'DateTime'=>$row[8]
));
}
 
echo json_encode(array($result));
?> 