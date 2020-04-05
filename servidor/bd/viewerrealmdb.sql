-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: viewerrealm.cst0lr1fnijg.us-east-2.rds.amazonaws.com:3306
-- Tiempo de generación: 05-04-2020 a las 20:13:51
-- Versión del servidor: 5.7.22-log
-- Versión de PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `viewerrealmdb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentarios`
--

DROP TABLE IF EXISTS `comentarios`;
CREATE TABLE IF NOT EXISTS `comentarios` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `contexto` varchar(500) NOT NULL,
  `fecha` date NOT NULL,
  `emisor` varchar(30) NOT NULL,
  `ID_inmueble` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_inmueble` (`ID_inmueble`),
  KEY `ID_inmueble_2` (`ID_inmueble`),
  KEY `ID_inmueble_3` (`ID_inmueble`),
  KEY `ID_inmueble_4` (`ID_inmueble`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotos`
--

DROP TABLE IF EXISTS `fotos`;
CREATE TABLE IF NOT EXISTS `fotos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `foto` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID` (`ID`),
  KEY `ID_2` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotoxinmueble`
--

DROP TABLE IF EXISTS `fotoxinmueble`;
CREATE TABLE IF NOT EXISTS `fotoxinmueble` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_INMUEBLE` int(11) NOT NULL,
  `ID_FOTO` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_INMUEBLE` (`ID_INMUEBLE`,`ID_FOTO`),
  KEY `ID_FOTO` (`ID_FOTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Inmueble`
--

DROP TABLE IF EXISTS `Inmueble`;
CREATE TABLE IF NOT EXISTS `Inmueble` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ancho` int(4) NOT NULL,
  `largo` int(4) NOT NULL,
  `estado_inmueble` varchar(4000) NOT NULL,
  `precio` int(20) NOT NULL,
  `estado` varchar(15) NOT NULL COMMENT 'venta o disponible o arriendo',
  `barrio` varchar(25) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `nombre_Inmueble` varchar(100) NOT NULL,
  `informacion_extra` varchar(500) NOT NULL,
  `Tipo` varchar(30) NOT NULL,
  `ID_VENDEDOR` int(4) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_VENDEDOR` (`ID_VENDEDOR`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Inmueble`
--

INSERT INTO `Inmueble` (`ID`, `ancho`, `largo`, `estado_inmueble`, `precio`, `estado`, `barrio`, `direccion`, `nombre_Inmueble`, `informacion_extra`, `Tipo`, `ID_VENDEDOR`) VALUES
(4, 6, 10, 'nuevo', 50000000, 'venta', 'sodosopa', 'cra 6B #3', 'conjuntos randy', 'ubicado en la mejor parte de la ciudad', 'apartamento', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inmueblexpersona`
--

DROP TABLE IF EXISTS `inmueblexpersona`;
CREATE TABLE IF NOT EXISTS `inmueblexpersona` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_INMUEBLE` int(11) NOT NULL,
  `ID_PERSONA` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_INMUEBLE` (`ID_INMUEBLE`,`ID_PERSONA`),
  KEY `ID_PERSONA` (`ID_PERSONA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificaciones`
--

DROP TABLE IF EXISTS `notificaciones`;
CREATE TABLE IF NOT EXISTS `notificaciones` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `asunto` varchar(200) NOT NULL,
  `mensaje` varchar(2000) NOT NULL,
  `destinatario` varchar(30) NOT NULL,
  `origen` varchar(30) NOT NULL,
  `ID_persona` int(4) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_persona` (`ID_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Persona`
--

DROP TABLE IF EXISTS `Persona`;
CREATE TABLE IF NOT EXISTS `Persona` (
  `ID` int(4) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contrasena` varchar(50) DEFAULT NULL,
  `penalizacion` int(3) DEFAULT NULL,
  `confiabilidad` int(4) DEFAULT NULL,
  `islogging` tinyint(1) NOT NULL,
  `registro` datetime NOT NULL,
  `foto` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `correo` (`correo`),
  UNIQUE KEY `correo_2` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Persona`
--

INSERT INTO `Persona` (`ID`, `nombre`, `correo`, `contrasena`, `penalizacion`, `confiabilidad`, `islogging`, `registro`, `foto`) VALUES
(1, 'Primero', 'Primero@gmail.com', '98f581a3ec305b6316ec0dc507e13e54', 0, 0, 0, '2020-04-05 15:45:24', NULL),
(2, 'Randy Marsh', 'Marshmelow@gmail.com', 'eaeb623384e5350e2184db84f66d2797', 0, 0, 0, '2020-04-05 15:58:18', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Pisos`
--

DROP TABLE IF EXISTS `Pisos`;
CREATE TABLE IF NOT EXISTS `Pisos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `paredes` varchar(500) NOT NULL,
  `habitaciones` varchar(500) NOT NULL,
  `muebles` varchar(500) NOT NULL,
  `texturas` varchar(500) DEFAULT NULL,
  `posiciones_muebles` varchar(500) DEFAULT NULL,
  `ID_inmueble` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_inmueble` (`ID_inmueble`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Pisos`
--

INSERT INTO `Pisos` (`ID`, `paredes`, `habitaciones`, `muebles`, `texturas`, `posiciones_muebles`, `ID_inmueble`) VALUES
(2, '[0,6,3],[5,6,7]', '[0,6,3],[5,6,7]', '[0,6,3],[5,6,7]', '', '', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefonos`
--

DROP TABLE IF EXISTS `telefonos`;
CREATE TABLE IF NOT EXISTS `telefonos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `numero` varchar(13) NOT NULL,
  `ID_PERSONA` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_PERSONA` (`ID_PERSONA`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `telefonos`
--

INSERT INTO `telefonos` (`ID`, `numero`, `ID_PERSONA`) VALUES
(1, '123425456', 1),
(2, '345222345', 2);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD CONSTRAINT `comentarios_ibfk_1` FOREIGN KEY (`ID_inmueble`) REFERENCES `Inmueble` (`ID`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `fotoxinmueble`
--
ALTER TABLE `fotoxinmueble`
  ADD CONSTRAINT `fotoxinmueble_ibfk_1` FOREIGN KEY (`ID_INMUEBLE`) REFERENCES `Inmueble` (`ID`),
  ADD CONSTRAINT `fotoxinmueble_ibfk_2` FOREIGN KEY (`ID_FOTO`) REFERENCES `fotos` (`ID`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `Inmueble`
--
ALTER TABLE `Inmueble`
  ADD CONSTRAINT `Inmueble_ibfk_1` FOREIGN KEY (`ID_VENDEDOR`) REFERENCES `Persona` (`ID`);

--
-- Filtros para la tabla `inmueblexpersona`
--
ALTER TABLE `inmueblexpersona`
  ADD CONSTRAINT `inmueblexpersona_ibfk_1` FOREIGN KEY (`ID_INMUEBLE`) REFERENCES `Inmueble` (`ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `inmueblexpersona_ibfk_2` FOREIGN KEY (`ID_PERSONA`) REFERENCES `Persona` (`ID`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD CONSTRAINT `notificaciones_ibfk_1` FOREIGN KEY (`ID_persona`) REFERENCES `Persona` (`ID`);

--
-- Filtros para la tabla `Pisos`
--
ALTER TABLE `Pisos`
  ADD CONSTRAINT `Pisos_ibfk_1` FOREIGN KEY (`ID_inmueble`) REFERENCES `Inmueble` (`ID`);

--
-- Filtros para la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD CONSTRAINT `telefonos_ibfk_1` FOREIGN KEY (`ID_PERSONA`) REFERENCES `Persona` (`ID`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
