<?php

	$username = $_REQUEST["username"];
	$password = $_REQUEST["password"];
        $name = $_REQUEST["name"];
        $phone = $_REQUEST["phone"];

	
	$con = new mysqli("fdb19.awardspace.net", "3296648_viewerrealm", "123456789an", "3296648_viewerrealm");
	$sql = "SELECT correo FROM Persona WHERE `correo` = '".$username."'";
	$result = mysqli_query($con, $sql);
	if(mysqli_num_rows($result) > 0)
	{
                die("Usuario ya existe");
	}
	else
	{
                $password = md5($password);
		$sql = "INSERT INTO Persona(nombre,correo, contrasena, islogging) VALUE('$name','$username', '$password','0')";
		$result = mysqli_query($con, $sql);
                die("Usuario registrado");
	}

?>