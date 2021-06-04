<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$id = $_POST['cid'];
$idm = $_POST['cidm'];
$nombrem = $_POST['cnomm'];
$telefonom = $_POST['ctelm'];
$emailm = $_POST['cemailm'];
//4. Insertar campos en la Base de Datos
$inserta = "UPDATE $bd.tabla SET id = '$idm',nombre = '$nombrem',telefono = '$telefonom',email = '$emailm' WHERE id = '$id';";
mysqli_query($con, $inserta) or die (mysqli_error());
mysqli_close($con);
?>