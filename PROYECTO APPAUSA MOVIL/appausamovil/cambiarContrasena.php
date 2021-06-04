<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$id = $_POST['cusername'];
$idm = $_POST['ccontrasena'];
//4. Insertar campos en la Base de Datos
$inserta = "UPDATE $bd.cuenta SET contrasena = '$idm' WHERE username = '$id';";
mysqli_query($con, $inserta) or die (mysqli_error());
mysqli_close($con);
?>