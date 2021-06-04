<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$id = $_POST['id'];
$idf = $_POST['idfoto'];
$cuenta = $_POST['cuenta'];
$empleado = $_POST['empleado'];
$tipo = $_POST['tipor'];
$descrip = $_POST['descrip'];
$foto = $_POST['fotoreporte'];
$estado = "En espera";
include_once("date.php");
$inserta = "INSERT INTO $bd.reporte VALUES ('$id','$empleado','$d','$tipo','$estado','$descrip','1');";
mysqli_query($con, $inserta) or die (mysqli_error());
$path = "soportes/$idf.jpg";
$url = "http://192.168.0.13:80/appausamovil/$path";
file_put_contents($path, base64_decode($foto));
include_once("date.php");
$inserta = "INSERT INTO $bd.documento VALUES ('$idf','$url','$cuenta','$f','1');";
mysqli_query($con, $inserta) or die (mysqli_error());
$inserta = "INSERT INTO $bd.reporte_documento VALUES ('$id','$idf');";
mysqli_query($con, $inserta) or die (mysqli_error());
include_once("date.php");
$inserta = "INSERT INTO $bd.historial_reporte VALUES ('$id','$d','$cuenta','$estado','$estado');";
mysqli_query($con, $inserta) or die (mysqli_error());
mysqli_close($con);
?>