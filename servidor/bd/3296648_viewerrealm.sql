-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: fdb19.awardspace.net
-- Generation Time: Feb 09, 2020 at 07:09 PM
-- Server version: 5.7.20-log
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `3296648_viewerrealm`
--

-- --------------------------------------------------------

--
-- Table structure for table `comentarios`
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
-- Table structure for table `fotos`
--

CREATE TABLE `fotos` (
  `ID` int(11) NOT NULL,
  `foto` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `fotoxinmueble`
--

CREATE TABLE `fotoxinmueble` (
  `ID` int(11) NOT NULL,
  `ID_INMUEBLE` int(11) NOT NULL,
  `ID_FOTO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Inmueble`
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
  `estado` varchar(15) NOT NULL COMMENT 'venta o disponible o arriendo',
  `barrio` varchar(25) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `nombre_Inmueble` varchar(100) NOT NULL,
  `informacion_extra` varchar(500) NOT NULL,
  `Tipo` varchar(30) NOT NULL,
  `ID_VENDEDOR` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Inmueble`
--

INSERT INTO `Inmueble` (`ID`, `triggers`, `suelos`, `paredes`, `ancho`, `largo`, `pisos`, `estado_inmueble`, `texturas`, `muebles`, `precio`, `estado`, `barrio`, `direccion`, `nombre_Inmueble`, `informacion_extra`, `Tipo`, `ID_VENDEDOR`) VALUES
(1, '', '', '', 6, 12, 2, 'Usado', '', '', 250000000, 'venta', 'las cruces', 'cra 5 # 3-25 sur', 'casa', 'es una casa usada en el mejor barrio del mundo trae dinero y no policias "tienes pelotas de señorita" si no la compras', 'casa', 36),
(2, '', '', '', 3, 10, 1, 'Nuevo', '', '', 150000000, 'venta', 'cubiolandia', 'cra cubio # 3-25 sur', 'casa', 'una casa donde hay cubios amarillos y redondos', 'casa', 6);

-- --------------------------------------------------------

--
-- Table structure for table `inmueblexpersona`
--

CREATE TABLE `inmueblexpersona` (
  `ID` int(11) NOT NULL,
  `ID_INMUEBLE` int(11) NOT NULL,
  `ID_PERSONA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notificaciones`
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
-- Table structure for table `Persona`
--

CREATE TABLE `Persona` (
  `ID` int(4) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contrasena` varchar(50) DEFAULT NULL,
  `penalizacion` int(3) DEFAULT NULL,
  `confiabilidad` int(4) DEFAULT NULL,
  `islogging` tinyint(1) NOT NULL,
  `registro` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Persona`
--

INSERT INTO `Persona` (`ID`, `nombre`, `correo`, `contrasena`, `penalizacion`, `confiabilidad`, `islogging`, `registro`) VALUES
(1, 'otaku', 'kwolfp12@gmail.com', 'a814546dc77c65a2426107fd2e481861', 0, 0, 0, '2020-01-19 06:17:18'),
(6, 'Dwan', 'CubioMan@yahoo.com', '56a454ff9cde3e76749a337443c153d1', 0, 0, 0, '2020-01-21 10:17:15'),
(36, 'Cartman', 'elcoon@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', 0, 0, 1, '2020-01-29 20:35:36'),
(37, 'Usuario', 'user@ejemplo.com', '912ec803b2ce49e4a541068d495ab570', 0, 0, 1, '2020-02-04 19:40:50'),
(38, 'Prueba', 'prueba@prueba.com', 'c893bad68927b457dbed39460e6afd62', 0, 0, 1, '2020-02-04 19:48:47'),
(39, 'Sett', 'Sett@gmail.com', '912ec803b2ce49e4a541068d495ab570', 0, 0, 1, '2020-02-05 21:14:37'),
(40, 'Test', 'test@test.com', '912ec803b2ce49e4a541068d495ab570', 0, 0, 1, '2020-02-06 12:08:47'),
(41, 'Hola', 'mundo@algo.com', '912ec803b2ce49e4a541068d495ab570', 0, 0, 1, '2020-02-07 12:15:16'),
(42, 'Alibaba', 'Ali@baba.com', '912ec803b2ce49e4a541068d495ab570', 0, 0, 0, '2020-02-07 20:39:41');

-- --------------------------------------------------------

--
-- Table structure for table `PRUEBASOP`
--

CREATE TABLE `PRUEBASOP` (
  `RECORD_ID` varchar(50) DEFAULT NULL,
  `FLTCARRIER` varchar(50) DEFAULT NULL,
  `FLTNUMBER` varchar(50) DEFAULT NULL,
  `ESTACIONSALIDA` varchar(50) DEFAULT NULL,
  `ESTACIONLLEGADA` varchar(50) DEFAULT NULL,
  `ACSUBTYPE` varchar(50) DEFAULT NULL,
  `FLTSTATE` varchar(50) DEFAULT NULL,
  `DEPACTDATETIMEUTC` varchar(50) DEFAULT NULL,
  `ARRACTDATETIMEUTC` varchar(50) DEFAULT NULL,
  `DEPACTDATETIMELCL` varchar(50) DEFAULT NULL,
  `ARRACTDATETIMELCL` varchar(50) DEFAULT NULL,
  `OPERATINGCARRIER` varchar(50) DEFAULT NULL,
  `DEPSKDDATETIMELCL` varchar(50) DEFAULT NULL,
  `ARRSKDDATETIMELCL` varchar(50) DEFAULT NULL,
  `ANNIO` varchar(50) DEFAULT NULL,
  `MES` varchar(50) DEFAULT NULL,
  `DIA` varchar(50) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PRUEBASOP`
--

INSERT INTO `PRUEBASOP` (`RECORD_ID`, `FLTCARRIER`, `FLTNUMBER`, `ESTACIONSALIDA`, `ESTACIONLLEGADA`, `ACSUBTYPE`, `FLTSTATE`, `DEPACTDATETIMEUTC`, `ARRACTDATETIMEUTC`, `DEPACTDATETIMELCL`, `ARRACTDATETIMELCL`, `OPERATINGCARRIER`, `DEPSKDDATETIMELCL`, `ARRSKDDATETIMELCL`, `ANNIO`, `MES`, `DIA`) VALUES
('36835173', 'AV', '064', 'SAL', 'LHR', 'AT7', 'ARR', '06/10/19', '06/10/19', '6/10/2019  2:20:00 p. m.', '06/10/2019 15:20', 'GU', '06/10/2019 14:30', '06/10/2019 15:40', '2019', '10', '06'),
('36835173', 'AV', '064', 'SAL', 'LHR', 'AT7', 'ARR', '06/10/19', '06/10/19', '6/10/2019  2:20:00 p. m.', '06/10/2019 15:20', 'GU', '06/10/2019 14:30', '06/10/2019 15:40', '2019', '10', '06');

-- --------------------------------------------------------

--
-- Table structure for table `telefonos`
--

CREATE TABLE `telefonos` (
  `ID` int(11) NOT NULL,
  `numero` varchar(13) NOT NULL,
  `ID_PERSONA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `telefonos`
--

INSERT INTO `telefonos` (`ID`, `numero`, `ID_PERSONA`) VALUES
(1, '1223478635', 1),
(2, '6589321', 6),
(3, '223446307', 1),
(21, '4564534567', 36),
(22, '456342', 37),
(23, '3456234', 38),
(24, '45634564', 39),
(25, '345623', 40),
(26, '45634', 41),
(27, '35463', 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comentarios`
--
ALTER TABLE `comentarios`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_inmueble` (`ID_inmueble`),
  ADD KEY `ID_inmueble_2` (`ID_inmueble`),
  ADD KEY `ID_inmueble_3` (`ID_inmueble`),
  ADD KEY `ID_inmueble_4` (`ID_inmueble`);

--
-- Indexes for table `fotos`
--
ALTER TABLE `fotos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID` (`ID`),
  ADD KEY `ID_2` (`ID`);

--
-- Indexes for table `fotoxinmueble`
--
ALTER TABLE `fotoxinmueble`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_INMUEBLE` (`ID_INMUEBLE`,`ID_FOTO`),
  ADD KEY `ID_FOTO` (`ID_FOTO`);

--
-- Indexes for table `Inmueble`
--
ALTER TABLE `Inmueble`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_VENDEDOR` (`ID_VENDEDOR`);

--
-- Indexes for table `inmueblexpersona`
--
ALTER TABLE `inmueblexpersona`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_INMUEBLE` (`ID_INMUEBLE`,`ID_PERSONA`),
  ADD KEY `ID_PERSONA` (`ID_PERSONA`);

--
-- Indexes for table `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_persona` (`ID_persona`);

--
-- Indexes for table `Persona`
--
ALTER TABLE `Persona`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `correo` (`correo`),
  ADD UNIQUE KEY `correo_2` (`correo`);

--
-- Indexes for table `telefonos`
--
ALTER TABLE `telefonos`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_PERSONA` (`ID_PERSONA`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comentarios`
--
ALTER TABLE `comentarios`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `fotos`
--
ALTER TABLE `fotos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `fotoxinmueble`
--
ALTER TABLE `fotoxinmueble`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Inmueble`
--
ALTER TABLE `Inmueble`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `inmueblexpersona`
--
ALTER TABLE `inmueblexpersona`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `notificaciones`
--
ALTER TABLE `notificaciones`
  MODIFY `ID` int(4) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Persona`
--
ALTER TABLE `Persona`
  MODIFY `ID` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;
--
-- AUTO_INCREMENT for table `telefonos`
--
ALTER TABLE `telefonos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `comentarios`
--
ALTER TABLE `comentarios`
  ADD CONSTRAINT `comentarios_ibfk_1` FOREIGN KEY (`ID_inmueble`) REFERENCES `Inmueble` (`ID`) ON UPDATE CASCADE;

--
-- Constraints for table `fotoxinmueble`
--
ALTER TABLE `fotoxinmueble`
  ADD CONSTRAINT `fotoxinmueble_ibfk_1` FOREIGN KEY (`ID_INMUEBLE`) REFERENCES `Inmueble` (`ID`),
  ADD CONSTRAINT `fotoxinmueble_ibfk_2` FOREIGN KEY (`ID_FOTO`) REFERENCES `fotos` (`ID`) ON UPDATE CASCADE;

--
-- Constraints for table `Inmueble`
--
ALTER TABLE `Inmueble`
  ADD CONSTRAINT `Inmueble_ibfk_1` FOREIGN KEY (`ID_VENDEDOR`) REFERENCES `Persona` (`ID`);

--
-- Constraints for table `inmueblexpersona`
--
ALTER TABLE `inmueblexpersona`
  ADD CONSTRAINT `inmueblexpersona_ibfk_1` FOREIGN KEY (`ID_INMUEBLE`) REFERENCES `Inmueble` (`ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `inmueblexpersona_ibfk_2` FOREIGN KEY (`ID_PERSONA`) REFERENCES `Persona` (`ID`) ON UPDATE CASCADE;

--
-- Constraints for table `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD CONSTRAINT `notificaciones_ibfk_1` FOREIGN KEY (`ID_persona`) REFERENCES `Persona` (`ID`);

--
-- Constraints for table `telefonos`
--
ALTER TABLE `telefonos`
  ADD CONSTRAINT `telefonos_ibfk_1` FOREIGN KEY (`ID_PERSONA`) REFERENCES `Persona` (`ID`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
