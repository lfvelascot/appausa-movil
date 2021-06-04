<?php
//1. Invoca los datos de conexión
include_once("conexion.php");
date_default_timezone_set("America/Bogota");
//2. Crear conexión a la Base de Datos
$con=mysqli_connect($host, $usuario, $clave, $bd) or die ('Falló la conexión');
mysqli_set_charset($con,"utf8");
//3. Tomar los campos provenientes del Formulario
$id = $_POST['cc'];
$pn = $_POST['pnom'];
$sn = $_POST['snom'];
$pa = $_POST['pape'];
$sa = $_POST['sape'];
$correom = $_POST['correo'];
$telm = $_POST['tel'];
$fechanam = $_POST['fechanam'];
//4. Insertar campos en la Base de Datos
list($ano,$mes,$dia) = explode("-",$fechanam);
$ano_diferencia  = date("Y") - $ano;
$mes_diferencia = date("m") - $mes;
$dia_diferencia   = date("d") - $dia;
if ($dia_diferencia < 0 || $mes_diferencia < 0)
	$ano_diferencia--;
// will output 2 days
if (empty($sn)){
	$inserta = "UPDATE $bd.usuario SET pnombre = '$pn',snombre = null,papellido = '$pa', sapellido = '$sa',fecha_nam = '$fechanam',edad= '$ano_diferencia', correo_electronico = '$correom', telefono = '$telm' WHERE cc = '$id';";
} else {
	$inserta = "UPDATE $bd.usuario SET pnombre = '$pn',snombre = '$sn',papellido = '$pa', sapellido = '$sa',fecha_nam = '$fechanam',edad= '$ano_diferencia', correo_electronico = '$correom', telefono = '$telm' WHERE cc = '$id';";
}
mysqli_query($con, $inserta) or die (mysqli_error());
mysqli_close($con);
?>