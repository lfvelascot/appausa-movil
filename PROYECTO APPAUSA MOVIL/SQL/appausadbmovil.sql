-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-06-2021 a las 20:18:43
-- Versión del servidor: 5.7.34-log
-- Versión de PHP: 7.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE DATABASE appausadbmovil;
USE appausadbmovil;
create user 'appausa_user'@'%' identified by 'appausa2020';
grant all privileges on appausadbmovil.* to 'appausa_user'@'%';
flush privileges;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `appausadbmovil`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `accion`
--

CREATE TABLE `accion` (
  `nombre` varchar(45) NOT NULL,
  `descrip` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `accion`
--

INSERT INTO `accion` (`nombre`, `descrip`) VALUES
('Cambio de Contraseña Exitoso', 'Cambio de Contraseña Exitoso'),
('Cambio de Contraseña Fallido', 'Cambio de Contraseña Fallido'),
('Comenatario Buscado', 'Comentario Buscado'),
('Comentario eliminado Exitoso', 'Comentario eliminado Exitoso'),
('Comentario eliminado Fallido', 'Comentario eliminado Fallido'),
('Comentario Enviado', 'Comentario Enviado'),
('Comentario modificado Exitoso', 'Comentario modificado Exitoso'),
('Comentario modificado Fallido', 'Comentario modificado Fallido'),
('Comentario No Enviado', 'Comentario NO Enviado'),
('Comentarios Vistos', 'Comentarios Vistos'),
('Contratos Vistos', 'Contratos Vistos'),
('Cuenta Añadido Exitoso', 'Cuenta Añadido Exitoso'),
('Cuenta Añadido Fallido', 'Cuenta Añadido Fallido'),
('Cuenta Buscado', 'Cuenta Buscado'),
('Cuenta Eliminado Exitoso', 'Cuenta Eliminado Exitoso'),
('Cuenta Eliminado Fallido', 'Cuenta Eliminado Fallido'),
('Cuenta Modificado Exitoso', 'Cuenta Modificado Exitoso'),
('Cuenta Modificado Fallido', 'Cuenta Modificado Fallido'),
('Cuentas Vistas', 'Cuentas Vistas'),
('Empleado Buscado', 'Empleado Buscado'),
('Empleado Eliminado Exitoso', 'Empleado Eliminado Exitoso'),
('Empleado Eliminado Fallido', 'Empleado Eliminado Fallido'),
('Empleado Modificado Exitoso', 'Empleado Modificado Exitoso'),
('Empleado Modificado Fallido', 'Empleado Modificado Fallido'),
('Empleados Vistos', 'Empleados Vistos'),
('Estado Cuenta Cambiado', 'Estado Cuenta Cambiado'),
('Estado Cuenta NO Cambiado', 'Estado Cuenta NO Cambiado'),
('Fin Sesión', 'Fin Sesión'),
('Ingreso a la Aplicación', 'Ingreso a la Aplicación'),
('Ingreso Fallido', 'Ingreso Fallido'),
('Registro de Actividad Visto', 'Log del usuario visto'),
('Reporte Buscado', 'Reporte Buscado'),
('Reporte eliminado exitoso', 'Reporte eliminado exitoso'),
('Reporte eliminado fallido', 'Reporte eliminado exitosamente'),
('Reporte enviado exitoso', 'Reporte enviado exitoso'),
('Reporte enviado fallido', 'Reporte enviado exitosamente'),
('Reporte modificado exitoso', 'Reporte modificado exitoso'),
('Reporte modificado fallido', 'Reporte modificado exitosamente'),
('Reportes enviados vistos', 'Reportes enviados vistos'),
('Reportes recibidos vistos', 'Reportes recibidos vistos'),
('Reportes Vistos', 'Reportes Vistas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentario`
--

CREATE TABLE `comentario` (
  `id` varchar(6) NOT NULL,
  `fecha` datetime NOT NULL,
  `cuenta` varchar(150) NOT NULL,
  `contenido` text NOT NULL,
  `e` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `comentario`
--

INSERT INTO `comentario` (`id`, `fecha`, `cuenta`, `contenido`, `e`) VALUES
('101', '2020-11-06 23:49:37', 'usuario1', 'comentario modificado', 1),
('102', '2020-11-10 12:53:37', 'usuario1', 'xghjklokjhgfdefghjklkjhgfdsdfghjkll.kjhgfdsdjklkjhgfdsñlgvfdtyi474f65dfs454df65gds654g65 65df6dg4654d65g4d5ds45g465dg485dg564d54g5d45g46fds', 1),
('103', '2020-11-10 12:54:11', 'usuario2', 'kfdokdfskodfsosdfkdsofosfoisofosojdsh jndjodjf ofpodgk   jfgoikgodfopfop dfjod ifds opdfod', 1),
('NG6MWY', '2021-05-19 17:53:21', 'usuario1', 'Esto es una prueba', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contrasena_restablecida`
--

CREATE TABLE `contrasena_restablecida` (
  `fecha` date NOT NULL,
  `codigo` varchar(150) NOT NULL,
  `cuenta` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contrato`
--

CREATE TABLE `contrato` (
  `num_contrato` bigint(20) NOT NULL,
  `empleado` bigint(10) NOT NULL,
  `empresa` bigint(17) NOT NULL,
  `cargo` varchar(45) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date DEFAULT NULL,
  `sueldo` bigint(10) NOT NULL,
  `funciones` text NOT NULL,
  `arl` bigint(17) DEFAULT NULL,
  `caja_compensación` bigint(17) DEFAULT NULL,
  `fondo_pensiones` bigint(17) DEFAULT NULL,
  `eps` bigint(17) DEFAULT NULL,
  `tipo_contrato` varchar(45) DEFAULT NULL,
  `e` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `contrato`
--

INSERT INTO `contrato` (`num_contrato`, `empleado`, `empresa`, `cargo`, `fecha_inicio`, `fecha_fin`, `sueldo`, `funciones`, `arl`, `caja_compensación`, `fondo_pensiones`, `eps`, `tipo_contrato`, `e`) VALUES
(1, 1000373689, 124578964, 'Profesor TC', '2020-05-13', '2021-03-18', 1200000, 'tgfrfggggfgdfdf', 1010254335, 1010297314, 1010254399, 1010254314, 'Definido', 1),
(2, 365145, 1, 'TH', '2020-10-10', '2020-10-31', 1200000, 'xrtfgdgffgf', 1010254399, 1010254314, 1010254314, 1010297314, 'Definido', 1),
(3, 54, 1, 'Asistente', '2020-10-11', '2021-10-11', 132000, 'yhhfgfggfgggf', 1010254335, 1010297314, 1010254399, 1010254314, 'Definido', 1),
(5, 36, 1, 'Talento Humano', '2020-10-12', '2021-10-12', 1200000, 'xxxxxxxxxxxxxyyyyyyyyyyyyyyyyyyyyyyyyy', 1010254335, 1010297314, 1010254399, 1010254314, 'Definido', 1),
(125478, 30247896, 145211004256, 'Talento Humano', '2020-11-16', '2021-11-16', 950120, 'Aqui van las funciones del empleado de talento humano', 1010254335, 1010297314, 1010254399, 1010254314, 'Definido', 1),
(145278, 30254690, 145211004256, 'Talento Humano', '2020-11-19', '2021-11-19', 985470, 'aqui van las funciones del empleado en la empresa para poder conocerlas', 1010254335, 1010297314, 1010254399, 25478110455, 'Definido', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE `cuenta` (
  `username` varchar(150) NOT NULL,
  `contrasena` varchar(150) NOT NULL,
  `estado` varchar(45) NOT NULL,
  `perfil` varchar(45) NOT NULL,
  `usuario` bigint(10) NOT NULL,
  `e` int(1) NOT NULL,
  `intentos_fallidos` int(1) NOT NULL DEFAULT '0',
  `ultimologin` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`username`, `contrasena`, `estado`, `perfil`, `usuario`, `e`, `intentos_fallidos`, `ultimologin`) VALUES
('empleado', '123456789', 'ACTIVA', 'EMPLEADO', 36985214, 1, 0, '2021-06-04 00:38:25'),
('usuario1', '123456', 'ACTIVA', 'ADMINISTRADOR', 1000689373, 1, 0, '2021-06-03 23:17:06'),
('usuario2', '123456', 'ACTIVA', 'TALENTO HUMANO GENERAL', 365145, 1, 0, '2021-06-04 00:24:36'),
('usuario4', '123456', 'ACTIVA', 'TALENTO HUMANO GENERAL', 30254690, 1, 0, NULL),
('usuariox', '123456789', 'BLOQUEADA', 'ADMINISTRADOR', 18, 1, 0, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `documento`
--

CREATE TABLE `documento` (
  `id` varchar(15) NOT NULL,
  `urlimagen` varchar(150) NOT NULL,
  `cuenta` varchar(150) NOT NULL,
  `fecha` datetime NOT NULL,
  `e` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `documento`
--

INSERT INTO `documento` (`id`, `urlimagen`, `cuenta`, `fecha`, `e`) VALUES
('1', 'rrrrrrrrrrr', 'usuario2', '2021-05-03 11:34:00', 1),
('FS-DC-311-943', 'http://192.168.0.13:80/appausamovil/soportes/FS-DC-311-943.jpg', 'empleado', '2021-06-02 21:57:30', 1),
('FS-DC-343-421', 'http://192.168.0.13:80/appausamovil/soportes/FS-DC-343-421.jpg', 'usuario2', '2021-06-01 22:57:18', 1),
('FS-IM-986-680', 'http://192.168.0.13:80/appausamovil/soportes/FS-IM-986-680.jpg', 'empleado', '2021-06-04 00:12:11', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `nit` bigint(17) NOT NULL,
  `nombre_empresa` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `email` varchar(150) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `descripción_empresa` text,
  `num_empleados` int(4) NOT NULL,
  `tipo_empresa` varchar(45) DEFAULT NULL,
  `inicio_actividad` int(2) NOT NULL,
  `fin_actividad` int(2) NOT NULL,
  `e` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`nit`, `nombre_empresa`, `direccion`, `email`, `telefono`, `descripción_empresa`, `num_empleados`, `tipo_empresa`, `inicio_actividad`, `fin_actividad`, `e`) VALUES
(1, 'PRUEBA SA', 'prueba', '9Pb7B+fNgorbXoXKGgO043UWJvIhWYBLas+1Og4FaHM=', '3144432469', 'empresa1@gmail.com', 25, 'Educación', 8, 16, 1),
(124578964, 'Universidad San Buenaventura', 'Calle 172b#75-89', 'empresa2@gmail.com', '7478523', 'Institución de educación superior ubicada en la ciudad de Bogotá', 5, 'Educación', 7, 17, 1),
(145211004256, 'Empresa 3', 'Calle 100 - 30', 'empresa3@gmail.com', '0030210012', 'Aquí va a la descripción de la empresa', 2, 'Construcción', 8, 17, 1),
(1154646321251, 'prueba 2', 'fddg', 'empresa4@gmail.com', '5030241025', 'fdfjkdjhdkjfjhskfjkdksjjlkdfkdfgd', 0, 'Educación', 8, 16, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entidad`
--

CREATE TABLE `entidad` (
  `nit` bigint(17) NOT NULL,
  `nombre_entidad` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `email` varchar(150) NOT NULL,
  `e` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `entidad`
--

INSERT INTO `entidad` (`nit`, `nombre_entidad`, `tipo`, `telefono`, `direccion`, `email`, `e`) VALUES
(1010254314, 'Compensar', 'EPS', '3173652100', 'Cra 45 # 17B- 47', '4gjrmqJIzUNdzF8nX/yaFuZQzHp6YS1WZnNCQE3nK9M=', 1),
(1010254335, 'Positiva', 'ARL', '3652147', 'Cll 72 $ 35 - 41', 'Rg6oC4h80q7M1oSKCWG6rL8godHT3fQwfmZPcOqq49E=', 1),
(1010254399, 'Colpensiones', 'FONDO DE PENSIONES', '7894561', 'Cra 35C $ 78 - 54', 'azGusZ+4Fb6mq1uAtLSLcjuyoEzZ/LuvP+HLd09bf38=', 1),
(1010297314, 'Compensar', 'CAJA DE COMPENSACIÓN', '7984125', 'Cll 100 A # 35 - 77', 'qs7DqlY0aZiJPQ4MkPfrLBYTaeKTulkk22YSQLASPqg=', 1),
(14511454111, 'Entidad Prueba C', 'FONDO DE PENSIONES', '8777485', 'Calle 100 - 30', 'dLx9Eo7H1KaQPiyAUAoT0QbRIFUcaeLAD+UrQI0n6iw=', 0),
(25478110455, 'Prueba 3', 'EPS', '5360510240', 'ckfjsklajdkldjska', '9Pb7B+fNgorbXoXKGgO043UWJvIhWYBLas+1Og4FaHM=', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_reporte`
--

CREATE TABLE `estado_reporte` (
  `nombre` varchar(45) NOT NULL,
  `descrip` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `estado_reporte`
--

INSERT INTO `estado_reporte` (`nombre`, `descrip`) VALUES
('Aceptado', 'Aceptado'),
('En espera', 'En espera'),
('Negado', 'Negado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_reporte`
--

CREATE TABLE `historial_reporte` (
  `id_reporte` varchar(20) NOT NULL,
  `fecha` date NOT NULL,
  `cuenta` varchar(150) NOT NULL,
  `estado_previo` varchar(45) NOT NULL,
  `estado_actual` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `historial_reporte`
--

INSERT INTO `historial_reporte` (`id_reporte`, `fecha`, `cuenta`, `estado_previo`, `estado_actual`) VALUES
('101', '2021-06-02', 'usuario1', 'En espera', 'Aceptado'),
('DC-311-943', '2021-06-02', 'empleado', 'En espera', 'En espera'),
('DC-343', '2021-06-01', 'usuario2', 'En espera', 'En espera'),
('IM-986-680', '2021-06-04', 'empleado', 'En espera', 'En espera');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `log`
--

CREATE TABLE `log` (
  `cuenta` varchar(150) NOT NULL,
  `fecha` datetime NOT NULL,
  `accion` varchar(45) NOT NULL,
  `descrip` text NOT NULL,
  `e` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `log`
--

INSERT INTO `log` (`cuenta`, `fecha`, `accion`, `descrip`, `e`) VALUES
('empleado', '2021-06-02 21:57:06', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('empleado', '2021-06-02 21:57:33', 'Reporte enviado exitoso', 'Se envio el reporteDC-311-943', 1),
('empleado', '2021-06-02 21:58:56', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('empleado', '2021-06-04 00:11:42', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('empleado', '2021-06-04 00:12:16', 'Reporte enviado exitoso', 'Se envio el reporteIM-986-680', 1),
('empleado', '2021-06-04 00:12:39', 'Fin Sesión', 'Finalizo sesion normalmente', 1),
('empleado', '2021-06-04 00:38:17', 'Cambio de Contraseña Exitoso', 'Se restablecio la contraseña', 1),
('empleado', '2021-06-04 00:38:25', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-01 22:51:30', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 15:29:11', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 15:40:47', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 16:33:34', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 16:53:19', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 17:16:31', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 17:16:44', 'Comentarios Vistos', 'Se listaron todos los comentarios de la APP', 1),
('usuario1', '2021-06-02 17:25:07', 'Ingreso Fallido', 'Contraseña ingresada es erronea', 1),
('usuario1', '2021-06-02 17:25:13', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 17:25:20', 'Comentarios Vistos', 'Se listaron todos los comentarios de la APP', 1),
('usuario1', '2021-06-02 17:41:01', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 17:41:15', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 17:50:47', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 17:54:03', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 17:55:48', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:19:03', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:24:51', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:25:00', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:31:15', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:31:20', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:31:46', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:33:57', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:35:12', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:36:03', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:37:50', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:38:55', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:40:02', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:41:05', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:43:22', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:44:33', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:49:40', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:54:31', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:55:26', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 19:59:11', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:00:12', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:01:40', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:01:56', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:04:10', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:05:03', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:06:16', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:07:20', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:09:55', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:11:54', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:12:28', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:18:54', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:21:25', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:29:14', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:31:00', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:33:38', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:36:06', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:37:11', 'Fin Sesión', 'Finalizo sesion normalmente', 1),
('usuario1', '2021-06-02 20:40:37', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:42:51', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 20:59:11', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 21:01:59', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 21:02:07', 'Fin Sesión', 'Finalizo sesion normalmente', 1),
('usuario1', '2021-06-02 21:28:48', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:02:25', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:06:33', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:16:50', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:17:37', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:19:47', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:20:45', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:21:01', 'Fin Sesión', 'Finalizo sesion normalmente', 1),
('usuario1', '2021-06-02 22:21:27', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:27:31', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:32:12', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:33:08', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:33:41', 'Reporte modificado exitoso', 'Se modifoco el reporte101', 1),
('usuario1', '2021-06-02 22:42:02', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:46:51', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:49:54', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:50:50', 'Reporte modificado exitoso', 'Se modifoco el reporte101', 1),
('usuario1', '2021-06-02 22:54:29', 'Reporte modificado exitoso', 'Se modifoco el reporte101', 1),
('usuario1', '2021-06-02 22:59:02', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-02 22:59:44', 'Cuenta Buscado', 'Se buscaron los datos de la cuenta empleado', 1),
('usuario1', '2021-06-02 23:00:07', 'Cuenta Modificado Exitoso', 'Se modificaron los datos de la cuenta empleadox', 1),
('usuario1', '2021-06-03 15:09:52', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 16:01:44', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 16:05:50', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 16:07:53', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 16:13:30', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 16:33:10', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 21:04:26', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 21:15:52', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 21:20:02', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 21:20:58', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 21:23:51', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 21:39:46', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 21:46:48', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 23:13:53', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 23:15:24', 'Fin Sesión', 'Finalizo sesion normalmente', 1),
('usuario1', '2021-06-03 23:17:06', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario1', '2021-06-03 23:31:37', 'Fin Sesión', 'Finalizo sesion normalmente', 1),
('usuario2', '2021-06-01 22:53:25', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-01 22:56:26', 'Reporte enviado exitoso', 'Se envio el reporteDC-415-101', 1),
('usuario2', '2021-06-01 22:57:20', 'Reporte enviado exitoso', 'Se envio el reporteDC-343-421', 1),
('usuario2', '2021-06-01 22:58:31', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-01 23:05:15', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-01 23:13:45', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-01 23:17:43', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-02 22:01:16', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-03 15:10:03', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-04 00:09:43', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-04 00:10:57', 'Fin Sesión', 'Finalizo sesion normalmente', 1),
('usuario2', '2021-06-04 00:12:45', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-04 00:17:12', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-04 00:19:20', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-04 00:21:50', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-04 00:24:36', 'Ingreso a la Aplicación', 'Ingreso sin problemas a la aplicacion', 1),
('usuario2', '2021-06-04 00:37:58', 'Fin Sesión', 'Finalizo sesion normalmente', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE `perfil` (
  `nombre` varchar(45) NOT NULL,
  `descrpción` varchar(45) NOT NULL,
  `rol_asociado` varchar(45) NOT NULL,
  `e` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`nombre`, `descrpción`, `rol_asociado`, `e`) VALUES
('ADMINISTRADOR', 'Rol para los Admon de la aplicación web', 'ADMINISTRADOR', 1),
('EMPLEADO', 'EMPLEADO', 'EMPLEADO', 1),
('SUPER', 'Rol para Super de la aplicación web', 'SUPER', 1),
('TALENTO HUMANO GENERAL', 'Rol para los TH de las empresas', 'TALENTO HUMANO', 1),
('TH CONTRATOS', 'Encargado de los contratos', 'TALENTO HUMANO', 1),
('TH EMPLEADO', 'Encargado de los Empleado', 'TALENTO HUMANO', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte`
--

CREATE TABLE `reporte` (
  `id` varchar(20) NOT NULL,
  `empleado` bigint(10) NOT NULL,
  `fecha` date NOT NULL,
  `tipo_reporte` varchar(45) NOT NULL,
  `estado_reporte` varchar(45) NOT NULL,
  `descrip` text NOT NULL,
  `e` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `reporte`
--

INSERT INTO `reporte` (`id`, `empleado`, `fecha`, `tipo_reporte`, `estado_reporte`, `descrip`, `e`) VALUES
('101', 365145, '2021-05-04', 'Incapacidad medica', 'Aceptado', 'blablablabla', 1),
('DC-311-943', 36985214, '2021-06-02', 'Diagnostico positivo COVID-19', 'En espera', 'Diagnostico Covid', 1),
('DC-343', 365145, '2021-06-01', 'Diagnostico positivo COVID-19', 'En espera', 'Prueba Covid', 1),
('IM-986-680', 36985214, '2021-06-04', 'Incapacidad medica', 'En espera', 'Incapacidad por X cosa', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte_documento`
--

CREATE TABLE `reporte_documento` (
  `id_reporte` varchar(20) NOT NULL,
  `id_documento` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `reporte_documento`
--

INSERT INTO `reporte_documento` (`id_reporte`, `id_documento`) VALUES
('101', '1'),
('DC-311-943', 'FS-DC-311-943'),
('DC-343', 'FS-DC-343-421'),
('IM-986-680', 'FS-IM-986-680');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `nombre` varchar(45) NOT NULL,
  `descrpción` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`nombre`, `descrpción`) VALUES
('ADMINISTRADOR', 'Rol para los Admon de la aplicación web'),
('EMPLEADO', 'Rol designado a los empleados de las empresas'),
('SUPER', 'Rol para Super de la aplicación web'),
('TALENTO HUMANO', 'Rol para los TH de las empresas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiporeporte`
--

CREATE TABLE `tiporeporte` (
  `nombre` varchar(45) NOT NULL,
  `descrip` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tiporeporte`
--

INSERT INTO `tiporeporte` (`nombre`, `descrip`) VALUES
('Diagnostico positivo COVID-19', 'Diagnostico positivo COVID-19'),
('Incapacidad medica', 'Incapacidad medica'),
('Problema familiar', 'Problema familiar');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_contrato`
--

CREATE TABLE `tipo_contrato` (
  `nombre` varchar(45) NOT NULL,
  `descrip` varchar(45) NOT NULL,
  `duracion` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipo_contrato`
--

INSERT INTO `tipo_contrato` (`nombre`, `descrip`, `duracion`) VALUES
('Definido', 'Contrato por cierto Tiempo', 'Definido por la empresa'),
('Indefinido', 'Sin tiempo definido', 'A decisión de la Empresa'),
('Obra Labor', 'Contrato a corto tiempo', 'Definido por la empresa');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_empresa`
--

CREATE TABLE `tipo_empresa` (
  `nombre_tipo` varchar(45) NOT NULL,
  `actividad` varchar(45) NOT NULL,
  `descripción` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipo_empresa`
--

INSERT INTO `tipo_empresa` (`nombre_tipo`, `actividad`, `descripción`) VALUES
('Aseo', 'Asear', 'Empresas de Aseo'),
('Construcción', 'Contruir', 'Empresa de Construcción'),
('Educación', 'Educar', 'Instituciones de educación');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `cc` bigint(10) NOT NULL,
  `tipo_doc` varchar(45) NOT NULL,
  `pnombre` varchar(45) NOT NULL,
  `snombre` varchar(45) DEFAULT NULL,
  `papellido` varchar(45) NOT NULL,
  `sapellido` varchar(45) NOT NULL,
  `fecha_nam` date NOT NULL,
  `edad` int(2) NOT NULL,
  `correo_electronico` varchar(150) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `rol` varchar(45) DEFAULT NULL,
  `e` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`cc`, `tipo_doc`, `pnombre`, `snombre`, `papellido`, `sapellido`, `fecha_nam`, `edad`, `correo_electronico`, `telefono`, `rol`, `e`) VALUES
(18, 'Cedula Ciudadania', 'pnombre', NULL, 'pape', 'sape', '2000-05-27', 20, 'correo@gmail.com', '123456', 'ADMINISTRADOR', 1),
(35, 'Cedula Ciudadania', 'MARIA', 'ANDREA', 'PEREZ', 'GONZALEZ', '2018-11-01', 14, 'correo2@gmail.com', '3125555', 'EMPLEADO', 1),
(36, 'Cedula Ciudadania', 'Karol', 'Andrea', 'Marquez', 'Paez', '2000-06-18', 20, 'correo3@gmail.com', '3147894512', 'EMPLEADO', 1),
(54, 'Cedula Extranjeria', 'ANDREA', NULL, 'PEREZ', 'RODRIGUEZ', '1981-07-16', 49, 'correo4@gmail.com', '3214569', 'EMPLEADO', 1),
(99, 'Cedula Ciudadania', 'Felipe', 'Felipe   ', 'Torres', 'Torres', '2000-12-16', 19, 'correo5@gmail.com', '3140005423', 'ADMINISTRADOR', 1),
(24571, 'Cedula Ciudadania', 'MARIA', 'JOSE', 'PEREZ', 'TORRES', '2000-02-01', 20, 'correo6@gmail.com', '3540014', 'EMPLEADO', 1),
(365145, 'Cedula Ciudadania', 'JOSE', NULL, 'CASTRO', 'GONZALEZ', '2012-06-01', 8, 'usuario2@gmail.com', '7415200', 'TALENTO HUMANO', 1),
(30247896, 'Cedula Ciudadania', 'Carmen', 'Lorena', 'Lopez', 'Gonzales', '2000-11-28', 19, 'correo7@gmail.com', '1031200046', 'EMPLEADO', 1),
(30254690, 'Cedula Ciudadania', 'Jorge', 'Esteban', 'Perez', 'Pedraza', '1999-12-28', 20, 'correo8@gmail.com', '0431004052', 'TALENTO HUMANO', 1),
(36985214, 'Cedula Ciudadania', 'MARIA', 'DEL CARMEN', 'TAO', 'ROJAS', '1981-07-16', 49, 'correo9@gmail.com', '3052418930', 'EMPLEADO', 1),
(1000373689, 'Cedula Ciudadania', 'LUIS', 'ANDRES', 'PEREZ', 'GONZALEZ', '2020-10-02', 33, 'correo10@gmail.com', '3140005429', 'EMPLEADO', 1),
(1000689373, 'Cedula Ciudadania', 'LUIS', 'FELIPE', 'VELASCO', 'TAO', '2000-12-28', 19, 'usuario1@gmail.com', '3144432469', 'ADMINISTRADOR', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `accion`
--
ALTER TABLE `accion`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD PRIMARY KEY (`fecha`,`cuenta`),
  ADD KEY `fk_comentario_cuenta1` (`cuenta`) USING BTREE;

--
-- Indices de la tabla `contrasena_restablecida`
--
ALTER TABLE `contrasena_restablecida`
  ADD PRIMARY KEY (`fecha`,`cuenta`),
  ADD UNIQUE KEY `codigo` (`codigo`),
  ADD KEY `fk_contrasena_restablecida_cuenta2_idx` (`cuenta`);

--
-- Indices de la tabla `contrato`
--
ALTER TABLE `contrato`
  ADD PRIMARY KEY (`num_contrato`,`empresa`),
  ADD KEY `fk_contrato_empleado1_idx` (`empleado`),
  ADD KEY `fk_contrato_empresa1_idx` (`empresa`),
  ADD KEY `fk_contrato_tipo_contrato1_idx` (`tipo_contrato`);

--
-- Indices de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `usuario` (`usuario`),
  ADD KEY `fk_cuentaapp_usuario_idx` (`usuario`),
  ADD KEY `fk_cuenta_perfil` (`perfil`);

--
-- Indices de la tabla `documento`
--
ALTER TABLE `documento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_documento_cuenta` (`cuenta`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`nit`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `telefono` (`telefono`),
  ADD UNIQUE KEY `nombre_empresa` (`nombre_empresa`),
  ADD KEY `fk_empresa_tipo_idx` (`tipo_empresa`);

--
-- Indices de la tabla `estado_reporte`
--
ALTER TABLE `estado_reporte`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `historial_reporte`
--
ALTER TABLE `historial_reporte`
  ADD PRIMARY KEY (`id_reporte`,`fecha`),
  ADD KEY `fk_historial_reporte_cuenta` (`cuenta`),
  ADD KEY `fk_historial_reporte_estado_previo` (`estado_previo`),
  ADD KEY `fk_historial_reporte_estado_actual` (`estado_actual`);

--
-- Indices de la tabla `log`
--
ALTER TABLE `log`
  ADD PRIMARY KEY (`cuenta`,`fecha`),
  ADD KEY `fk_log_accion_idx` (`accion`),
  ADD KEY `fk_log_cuenta_idx` (`cuenta`);

--
-- Indices de la tabla `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`nombre`),
  ADD KEY `fk_perfil_rol` (`rol_asociado`);

--
-- Indices de la tabla `reporte`
--
ALTER TABLE `reporte`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_reporte_usuario2` (`empleado`),
  ADD KEY `fk_reporte_tipo_reporte` (`tipo_reporte`),
  ADD KEY `fk_reporte_estado_reporte2` (`estado_reporte`);

--
-- Indices de la tabla `reporte_documento`
--
ALTER TABLE `reporte_documento`
  ADD PRIMARY KEY (`id_reporte`,`id_documento`),
  ADD KEY `fk_reporte_documento_documento` (`id_documento`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `tiporeporte`
--
ALTER TABLE `tiporeporte`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `tipo_contrato`
--
ALTER TABLE `tipo_contrato`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `tipo_empresa`
--
ALTER TABLE `tipo_empresa`
  ADD PRIMARY KEY (`nombre_tipo`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`cc`),
  ADD UNIQUE KEY `correo_electronico` (`correo_electronico`),
  ADD UNIQUE KEY `telefono` (`telefono`),
  ADD KEY `fk_usuario_rol_idx` (`rol`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comentario`
--
ALTER TABLE `comentario`
  ADD CONSTRAINT `fk_comentario_cuenta` FOREIGN KEY (`cuenta`) REFERENCES `cuenta` (`username`);

--
-- Filtros para la tabla `contrasena_restablecida`
--
ALTER TABLE `contrasena_restablecida`
  ADD CONSTRAINT `fk_contrasena_restablecida_cuenta1` FOREIGN KEY (`cuenta`) REFERENCES `cuenta` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_contrasena_restablecida_cuenta2` FOREIGN KEY (`cuenta`) REFERENCES `cuenta` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `contrato`
--
ALTER TABLE `contrato`
  ADD CONSTRAINT `fk_contrato_empleado` FOREIGN KEY (`empleado`) REFERENCES `usuario` (`cc`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_contrato_empresa1` FOREIGN KEY (`empresa`) REFERENCES `empresa` (`nit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_contrato_tipo_contrato1` FOREIGN KEY (`tipo_contrato`) REFERENCES `tipo_contrato` (`nombre`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `fk_cuenta_perfil` FOREIGN KEY (`perfil`) REFERENCES `perfil` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_cuenta_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`cc`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `documento`
--
ALTER TABLE `documento`
  ADD CONSTRAINT `fk_documento_cuenta` FOREIGN KEY (`cuenta`) REFERENCES `cuenta` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD CONSTRAINT `fk_empresa_tipo_empresa` FOREIGN KEY (`tipo_empresa`) REFERENCES `tipo_empresa` (`nombre_tipo`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Filtros para la tabla `historial_reporte`
--
ALTER TABLE `historial_reporte`
  ADD CONSTRAINT `fk_historial_reporte_cuenta` FOREIGN KEY (`cuenta`) REFERENCES `cuenta` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_historial_reporte_estado_actual` FOREIGN KEY (`estado_actual`) REFERENCES `estado_reporte` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_historial_reporte_estado_previo` FOREIGN KEY (`estado_previo`) REFERENCES `estado_reporte` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_historial_reporte_reporte` FOREIGN KEY (`id_reporte`) REFERENCES `reporte` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `log`
--
ALTER TABLE `log`
  ADD CONSTRAINT `fk_log_accion1` FOREIGN KEY (`accion`) REFERENCES `accion` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_log_cuenta1` FOREIGN KEY (`cuenta`) REFERENCES `cuenta` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `perfil`
--
ALTER TABLE `perfil`
  ADD CONSTRAINT `fk_perfil_rol` FOREIGN KEY (`rol_asociado`) REFERENCES `rol` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `reporte`
--
ALTER TABLE `reporte`
  ADD CONSTRAINT `fk_reporte_estado_reporte2` FOREIGN KEY (`estado_reporte`) REFERENCES `estado_reporte` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reporte_tipo_reporte` FOREIGN KEY (`tipo_reporte`) REFERENCES `tiporeporte` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reporte_usuario1` FOREIGN KEY (`empleado`) REFERENCES `usuario` (`cc`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reporte_usuario2` FOREIGN KEY (`empleado`) REFERENCES `usuario` (`cc`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `reporte_documento`
--
ALTER TABLE `reporte_documento`
  ADD CONSTRAINT `fk_reporte_documento_documento` FOREIGN KEY (`id_documento`) REFERENCES `documento` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_reporte_documento_reporte` FOREIGN KEY (`id_reporte`) REFERENCES `reporte` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`rol`) REFERENCES `rol` (`nombre`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
