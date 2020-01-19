-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Servidor: fdb19.awardspace.net
-- Tiempo de generación: 19-01-2020 a las 21:59:05
-- Versión del servidor: 5.7.20-log
-- Versión de PHP: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `3296648_viewerrealm`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentarios`
--

CREATE TABLE `comentarios` (
  `ID` int(11) NOT NULL,
  `contexto` varchar(500) NOT NULL,
  `fecha` date NOT NULL,
  `emisor` varchar(30) NOT NULL,
  `ID_inmueble` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotos`
--

CREATE TABLE `fotos` (
  `ID` int(11) NOT NULL,
  `foto` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotoxinmueble`
--

CREATE TABLE `fotoxinmueble` (
  `ID` int(11) NOT NULL,
  `ID_INMUEBLE` int(11) NOT NULL,
  `ID_FOTO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Inmueble`
--

CREATE TABLE `Inmueble` (
  `ID` int(11) NOT NULL,
  `triggers` varchar(4000) NOT NULL,
  `suelos` varchar(4000) NOT NULL,
  `paredes` varchar(4000) NOT NULL,
  `ancho` int(4) NOT NULL,
  `largo` int(4) NOT NULL,
  `pisos` int(2) NOT NULL,
  `estado_inmueble` varchar(4000) NOT NULL,
  `texturas` varchar(4000) NOT NULL,
  `muebles` varchar(10000) NOT NULL,
  `precio` int(20) NOT NULL,
  `cliente` varchar(30) NOT NULL,
  `estado` varchar(15) NOT NULL COMMENT 'venta o disponible o arriendo',
  `barrio` varchar(25) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `nombre_Inmueble` varchar(100) NOT NULL,
  `informacion_extra` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inmueblexpersona`
--

CREATE TABLE `inmueblexpersona` (
  `ID` int(11) NOT NULL,
  `ID_INMUEBLE` int(11) NOT NULL,
  `ID_PERSONA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificaciones`
--

CREATE TABLE `notificaciones` (
  `ID` int(4) NOT NULL,
  `asunto` varchar(200) NOT NULL,
  `mensaje` varchar(2000) NOT NULL,
  `destinatario` varchar(30) NOT NULL,
  `origen` varchar(30) NOT NULL,
  `ID_persona` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Persona`
--

CREATE TABLE `Persona` (
  `ID` int(4) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contrasena` varchar(50) DEFAULT NULL,
  `penalizacion` int(3) DEFAULT NULL,
  `confiabilidad` int(4) DEFAULT NULL,
  `islogging` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Persona`
--

INSERT INTO `Persona` (`ID`, `nombre`, `correo`, `contrasena`, `penalizacion`, `confiabilidad`, `islogging`) VALUES
(1, 'otaku', 'kwolfp12@gmail.com', 'a814546dc77c65a2426107fd2e481861', NULL, NULL, 1),
(6, 'Dwan', 'CubioMan@yahoo.com', '56a454ff9cde3e76749a337443c153d1', NULL, NULL, 1),
(7, 'wendy', 'Huston25@yahoo.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefonos`
--

CREATE TABLE `telefonos` (
  `ID` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  `ID_PERSONA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `telefonos`
--

INSERT INTO `telefonos` (`ID`, `numero`, `ID_PERSONA`) VALUES
(1, 1223478635, 1),
(2, 6589321, 6);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_inmueble` (`ID_inmueble`),
  ADD KEY `ID_inmueble_2` (`ID_inmueble`),
  ADD KEY `ID_inmueble_3` (`ID_inmueble`),
  ADD KEY `ID_inmueble_4` (`ID_inmueble`);

--
-- Indices de la tabla `fotos`
--
ALTER TABLE `fotos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `ID_2` (`ID`);

--
-- Indices de la tabla `fotoxinmueble`
--
ALTER TABLE `fotoxinmueble`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_INMUEBLE` (`ID_INMUEBLE`,`ID_FOTO`),
  ADD KEY `ID_FOTO` (`ID_FOTO`);

--
-- Indices de la tabla `Inmueble`
--
ALTER TABLE `Inmueble`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `inmueblexpersona`
--
ALTER TABLE `inmueblexpersona`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_INMUEBLE` (`ID_INMUEBLE`,`ID_PERSONA`),
  ADD KEY `ID_PERSONA` (`ID_PERSONA`);

--
-- Indices de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_persona` (`ID_persona`);

--
-- Indices de la tabla `Persona`
--
ALTER TABLE `Persona`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- Indices de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_PERSONA` (`ID_PERSONA`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comentarios`
--
ALTER TABLE `comentarios`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `fotos`
--
ALTER TABLE `fotos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `fotoxinmueble`
--
ALTER TABLE `fotoxinmueble`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `Inmueble`
--
ALTER TABLE `Inmueble`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `inmueblexpersona`
--
ALTER TABLE `inmueblexpersona`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  MODIFY `ID` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `Persona`
--
ALTER TABLE `Persona`
  MODIFY `ID` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
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
-- Filtros para la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD CONSTRAINT `telefonos_ibfk_1` FOREIGN KEY (`ID_PERSONA`) REFERENCES `Persona` (`ID`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
