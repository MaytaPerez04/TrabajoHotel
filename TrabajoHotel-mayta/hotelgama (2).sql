-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-10-2025 a las 06:55:05
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `hotelgama`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consumo`
--

CREATE TABLE `consumo` (
  `idConsumo` int(11) NOT NULL,
  `fk_idProducto` int(11) NOT NULL,
  `fk_idReserva` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `consumo`
--

INSERT INTO `consumo` (`idConsumo`, `fk_idProducto`, `fk_idReserva`, `precio`) VALUES
(1, 1, 1, 1.50),
(2, 2, 1, 2.20),
(3, 4, 2, 7.00),
(4, 3, 2, 4.80),
(5, 3, 4, 4.80);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `delegacion`
--

CREATE TABLE `delegacion` (
  `idDelegacion` int(11) NOT NULL,
  `nombre` varchar(120) NOT NULL,
  `pais` varchar(80) NOT NULL,
  `deporte` varchar(80) NOT NULL,
  `cantidad_miembros` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `delegacion`
--

INSERT INTO `delegacion` (`idDelegacion`, `nombre`, `pais`, `deporte`, `cantidad_miembros`) VALUES
(1, 'Leones Atléticos', 'Argentina', 'Atletismo', 12),
(2, 'Tigres del Norte', 'México', 'Fútbol', 20),
(3, 'Águilas Andinas', 'Chile', 'Vóley', 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `idFactura` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `total` decimal(12,2) NOT NULL,
  `fk_idUsuario` int(11) NOT NULL,
  `fk_idReserva` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`idFactura`, `fecha`, `total`, `fk_idUsuario`, `fk_idReserva`) VALUES
(1, '2025-09-12', 3.70, 1, 1),
(2, '2025-09-15', 11.80, 2, 2),
(3, '2025-09-14', 4.80, 1, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitacion`
--

CREATE TABLE `habitacion` (
  `idHabitacion` int(11) NOT NULL,
  `tipo` enum('Simple','Doble','Suite') NOT NULL,
  `estado` enum('libre','ocupada') NOT NULL DEFAULT 'libre',
  `estadoLimpieza` enum('limpia','limpiando','limpiar') NOT NULL DEFAULT 'limpiar'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habitacion`
--

INSERT INTO `habitacion` (`idHabitacion`, `tipo`, `estado`, `estadoLimpieza`) VALUES
(1, 'Simple', 'libre', 'limpia'),
(2, 'Doble', 'libre', 'limpia'),
(3, 'Suite', 'ocupada', 'limpia'),
(4, 'Doble', 'ocupada', 'limpia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `huesped`
--

CREATE TABLE `huesped` (
  `idHuesped` int(11) NOT NULL,
  `nombre` varchar(120) NOT NULL,
  `tit_res` varchar(120) DEFAULT NULL,
  `documento` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `huesped`
--

INSERT INTO `huesped` (`idHuesped`, `nombre`, `tit_res`, `documento`) VALUES
(1, 'María González', 'Titular', 'DNI 32111222'),
(2, 'Pedro Sánchez', NULL, 'DNI 30123456'),
(3, 'Lucía Fernández', NULL, 'PAS 9A3321'),
(4, 'Bruno Castro', 'Titular', 'DNI 29111222');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL,
  `nombre` varchar(120) NOT NULL,
  `precio` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `nombre`, `precio`) VALUES
(1, 'Agua mineral 500ml', 1.50),
(2, 'Snack salado', 2.20),
(3, 'Cerveza artesanal', 4.80),
(4, 'Lavandería', 7.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `idReserva` int(11) NOT NULL,
  `fecha_ini_res` date NOT NULL,
  `fecha_fin_res` date NOT NULL,
  `tipo` varchar(40) NOT NULL,
  `fk_idHuesped` int(11) NOT NULL,
  `fk_idDelegacion` int(11) DEFAULT NULL,
  `fk_idHabitacion` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`idReserva`, `fecha_ini_res`, `fecha_fin_res`, `tipo`, `fk_idHuesped`, `fk_idDelegacion`, `fk_idHabitacion`) VALUES
(1, '2025-09-10', '2025-09-12', 'Individual', 1, NULL, 1),
(2, '2025-09-10', '2025-09-15', 'Grupo', 2, 1, 2),
(3, '2025-09-11', '2025-09-13', 'Individual', 3, NULL, NULL),
(4, '2025-09-12', '2025-09-14', 'Grupo', 4, 2, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `usuario` varchar(60) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `rol` enum('Recepcion','Administrador','Limpieza') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `usuario`, `contrasena`, `rol`) VALUES
(1, 'Ana López', 'alopez', 'hash$demo', 'Recepcion'),
(2, 'Juan Pérez', 'jperez', 'hash$demo', 'Administrador'),
(3, 'Carla Romero', 'cromero', 'hash$demo', 'Limpieza');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `consumo`
--
ALTER TABLE `consumo`
  ADD PRIMARY KEY (`idConsumo`),
  ADD KEY `fk_idProducto` (`fk_idProducto`),
  ADD KEY `fk_idReserva` (`fk_idReserva`);

--
-- Indices de la tabla `delegacion`
--
ALTER TABLE `delegacion`
  ADD PRIMARY KEY (`idDelegacion`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`idFactura`),
  ADD KEY `fk_idUsuario` (`fk_idUsuario`),
  ADD KEY `fk_idReserva` (`fk_idReserva`);

--
-- Indices de la tabla `habitacion`
--
ALTER TABLE `habitacion`
  ADD PRIMARY KEY (`idHabitacion`);

--
-- Indices de la tabla `huesped`
--
ALTER TABLE `huesped`
  ADD PRIMARY KEY (`idHuesped`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`idReserva`),
  ADD KEY `fk_idHuesped` (`fk_idHuesped`),
  ADD KEY `fk_idDelegacion` (`fk_idDelegacion`),
  ADD KEY `fk_idHabitacion` (`fk_idHabitacion`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `consumo`
--
ALTER TABLE `consumo`
  MODIFY `idConsumo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `delegacion`
--
ALTER TABLE `delegacion`
  MODIFY `idDelegacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `idFactura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `habitacion`
--
ALTER TABLE `habitacion`
  MODIFY `idHabitacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `huesped`
--
ALTER TABLE `huesped`
  MODIFY `idHuesped` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `idReserva` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `consumo`
--
ALTER TABLE `consumo`
  ADD CONSTRAINT `consumo_ibfk_1` FOREIGN KEY (`fk_idProducto`) REFERENCES `producto` (`idProducto`),
  ADD CONSTRAINT `consumo_ibfk_2` FOREIGN KEY (`fk_idReserva`) REFERENCES `reserva` (`idReserva`);

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`fk_idUsuario`) REFERENCES `usuario` (`idUsuario`),
  ADD CONSTRAINT `factura_ibfk_2` FOREIGN KEY (`fk_idReserva`) REFERENCES `reserva` (`idReserva`);

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`fk_idHuesped`) REFERENCES `huesped` (`idHuesped`),
  ADD CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`fk_idDelegacion`) REFERENCES `delegacion` (`idDelegacion`),
  ADD CONSTRAINT `reserva_ibfk_3` FOREIGN KEY (`fk_idHabitacion`) REFERENCES `habitacion` (`idHabitacion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
