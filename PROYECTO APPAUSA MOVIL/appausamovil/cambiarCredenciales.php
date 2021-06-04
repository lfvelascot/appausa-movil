<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$id = $_POST['usuario'];
$idm = $_POST['usuariom'];
$cc = $_POST['contrasena'];

echo "$id  - $idm - $cc";
//4. Insertar campos en la Base de Datos
$sql = "UPDATE $bd.cuenta SET username = '$idm' WHERE username = '$id';";
mysqli_query($con, $sql) or die ("Problemas al actualizar".mysqli_error($con));
$inserta = "UPDATE $bd.cuenta SET contrasena = '$cc' WHERE username = '$idm';";
mysqli_query($con, $inserta) or die ("Problemas al actualizar".mysqli_error($con));
mysqli_close($con);
?>