<?php
include_once("conexion.php");
//1. Crear conexión a la Base de Datos
$con=mysqli_connect($host,$usuario,$clave,$bd) or die('Fallo la conexion');
mysqli_set_charset($con,"utf8");
//2. Tomar los campos provenientes de la tabla
$id = $_GET['id'];
$consulta="SELECT * FROM $bd.log WHERE cuenta = '$id' LIMIT 20;";
$resultado = mysqli_query($con, $consulta);
while($fila = $resultado -> fetch_array()){
	$r[] = array_map ("utf8_encode",$fila);
}
echo json_encode($r);
$resultado -> close();
mysqli_close($con);
?>
