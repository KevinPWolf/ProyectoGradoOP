<?php

	$username = $_REQUEST["username"];
	
	$con = new mysqli("fdb19.awardspace.net", "3296648_viewerrealm", "123456789an", "3296648_viewerrealm");
	
	$sql = "UPDATE `Persona` SET `islogging` = '0' WHERE `correo` = '".$username."'";
	$result = mysqli_query($con, $sql);

	
?>