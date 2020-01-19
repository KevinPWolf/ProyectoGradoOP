<?php

	$username = $_REQUEST["username"];
	$password = $_REQUEST["password"];
	
	$con = new mysqli("fdb19.awardspace.net", "3296648_viewerrealm", "123456789an", "3296648_viewerrealm");
	$sql = "SELECT * FROM Persona WHERE `correo` = '".$username."'";
	$result = mysqli_query($con, $sql);
	if(mysqli_num_rows($result) > 0)
	{
        
		$password = md5($password);
		while($row = mysqli_fetch_assoc($result))
		{
                        if($row['islogging'] == 0)
                        {
                                if($password == $row['contrasena'])
                                {
                                
                                        $sql = "UPDATE `Persona` SET `islogging` = '1' WHERE `correo` = '".$username."'";
                                        $result = mysqli_query($con, $sql);
                                        die("Login-Success");
                                } 
                                else
                                        die("usuario o contraseña  Incorrecto");
                        }
                        else
                                die("usuario actualmente ingresado");
		}
	}
	die("usuario o contraseña  incorrecto");
	
?>