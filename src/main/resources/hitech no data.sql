-- --------------------------------------------------------
-- Servidor:                     hitech-health.c3dyjrwatq8v.eu-west-1.rds.amazonaws.com
-- Versão do servidor:           10.4.8-MariaDB - Source distribution
-- OS do Servidor:               Linux
-- HeidiSQL Versão:              10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Copiando estrutura do banco de dados para hitechhealth
CREATE DATABASE IF NOT EXISTS `hitechhealth` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hitechhealth`;

-- Copiando estrutura para tabela hitechhealth.configapp
CREATE TABLE IF NOT EXISTS `configapp` (
  `sessionTime` int(11) NOT NULL,
  `urlClient` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela hitechhealth.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `profession` varchar(100) NOT NULL,
  `city` varchar(50) NOT NULL,
  `branch` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Exportação de dados foi desmarcado.

-- Copiando estrutura para tabela hitechhealth.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProfile` int(11) NOT NULL DEFAULT 0,
  `activeAccount` char(1) NOT NULL DEFAULT 'N' COMMENT 'N - NO | \r\nY - YES',
  `keyValidation` varchar(50) DEFAULT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(20) NOT NULL,
  `token` varchar(50) DEFAULT NULL,
  `lastAcess` timestamp NULL DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

INSERT INTO `configapp` (`sessionTime`, `urlClient`) VALUES
	(20, 'http://elasticbeanstalk-eu-west-1-100101151868.s3-website-eu-west-1.amazonaws.com/');
	
INSERT INTO `user` (`id`, `idProfile`, `activeAccount`, `keyValidation`, `email`, `password`, `token`, `lastAcess`, `name`) VALUES
	(1, 1, 'Y', NULL, 'admin@hitech-health.com', 'admin', '2a893320-cbc9-4004-865f-25c2f3a50efe', '2020-08-27 14:42:52', 'admin');

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
