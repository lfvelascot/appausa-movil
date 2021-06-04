<?php
include_once("conexion.php");
//1. Crear conexión a la Base de Datos
$con=mysqli_connect($host,$usuario,$clave,$bd) or die('Fallo la conexion');
mysqli_set_charset($con,"utf8");
$id = $_GET['id'];
$consulta="SELECT reporte.*, reporte_documento.id_documento FROM reporte, reporte_documento WHERE reporte.empleado = '$id' AND reporte.e = 1 AND reporte.id = reporte_documento.id_reporte;";
$resultado = mysqli_query($con, $consulta);
while($fila = $resultado -> fetch_array()){
	$r[] = array_map ("utf8_encode",$fila);
}
echo json_encode($r);
$resultado -> close();
mysqli_close($con);
?>