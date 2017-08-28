-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 27-Ago-2017 às 13:49
-- Versão do servidor: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bra_ght`
--
CREATE DATABASE IF NOT EXISTS `bra_ght` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `bra_ght`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `codigo` int(100) NOT NULL AUTO_INCREMENT,
  `creacion` date NOT NULL,
  `modificacion` date DEFAULT NULL,
  `nombre` varchar(200) NOT NULL,
  `tipo` char(1) NOT NULL,
  `valorHora` double NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `categoria`
--

INSERT INTO `categoria` (`codigo`, `creacion`, `modificacion`, `nombre`, `tipo`, `valorHora`) VALUES
(1, '2017-07-07', NULL, 'ADMINISTRADOR', 'A', 0),
(2, '2017-07-07', NULL, 'GESTOR', 'G', 0),
(3, '2017-08-27', NULL, 'USUARIO', 'U', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `controlmensual`
--

CREATE TABLE IF NOT EXISTS `controlmensual` (
  `codigo` int(100) NOT NULL AUTO_INCREMENT,
  `creacion` date NOT NULL,
  `mes` int(12) DEFAULT NULL,
  `ano` int(200) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `estado` char(1) DEFAULT NULL,
  `codUsuario` int(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codUsuario` (`codUsuario`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `horarios`
--

CREATE TABLE IF NOT EXISTS `horarios` (
  `codigo` int(100) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `entrada` time NOT NULL,
  `salida` time NOT NULL,
  `descanso` time NOT NULL,
  `total` time NOT NULL,
  `codUsuario` int(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codUsuario` (`codUsuario`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `codigo` int(100) NOT NULL AUTO_INCREMENT,
  `creacion` date NOT NULL,
  `modificacion` date DEFAULT NULL,
  `nombre` varchar(200) NOT NULL,
  `login` varchar(100) NOT NULL,
  `passw` varchar(64) NOT NULL,
  `valorHora` double NOT NULL,
  `codCategoria` int(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `codCategoria` (`codCategoria`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`codigo`, `creacion`, `modificacion`, `nombre`, `login`, `passw`, `valorHora`, `codCategoria`) VALUES
(1, '2017-08-27', NULL, 'Administrador', 'adm', 'adm', 0, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
