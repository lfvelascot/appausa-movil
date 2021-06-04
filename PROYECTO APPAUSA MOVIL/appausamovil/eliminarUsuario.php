<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$id = $_POST['cc'];
$inserta = "UPDATE $bd.usuario SET e = '0' WHERE cc = '$id';";
mysqli_query($con, $inserta) or die (mysqli_error());
$inserta = "UPDATE $bd.cuenta SET e = '0' WHERE usuario = '$id';";
mysqli_query($con, $inserta) or die (mysqli_error());
$inserta = "UPDATE $bd.log SET e = '0' WHERE cuenta = (SELECT username FROM cuenta WHERE usuario = '$id');";
mysqli_query($con, $inserta) or die (mysqli_error());
mysqli_close($con);
?>