<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$id = $_POST['cusername'];
$contra = $_POST['ccontrasena'];
$perfil = $_POST['cperfil'];
$cc = $_POST['cusuario'];
//4. Insertar campos en la Base de Datos
include_once("date.php");
$inserta = "INSERT INTO $bd.cuenta (username, contrasena, estado, perfil,usuario,e,intentos_fallidos,ultimologin)VALUES ('$id','$contra','ACTIVA','$perfil','$cc','1',0,NULL);";
mysqli_query($con, $inserta) or die (mysqli_error());
mysqli_close($con);
?>