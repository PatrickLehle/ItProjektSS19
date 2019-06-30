-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 30. Jun 2019 um 12:59
-- Server-Version: 5.7.24-log
-- PHP-Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `itprojektss19`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `article`
--

CREATE TABLE `article` (
  `id` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `quantity` int(100) NOT NULL,
  `unit` varchar(100) NOT NULL,
  `retailerId` int(100) NOT NULL,
  `ownerId` int(100) NOT NULL,
  `groupId` int(11) NOT NULL,
  `creationDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `boolean` tinyint(1) NOT NULL,
  `fav` tinyint(1) NOT NULL,
  `delDat` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `article`
--

INSERT INTO `article` (`id`, `name`, `quantity`, `unit`, `retailerId`, `ownerId`, `groupId`, `creationDat`, `modDat`, `boolean`, `fav`, `delDat`) VALUES
(1, 'banane', 5, 'stück', 1, 2, 1, '2019-06-25 20:01:31', '2019-06-25 20:01:31', 0, 1, NULL),
(2, 'Apfel', 2, 'Stück', 2, 2, 1, '2019-06-26 16:13:29', '2019-06-26 16:13:29', 0, 1, NULL),
(3, 'Birne', 80, 'KG', 3, 2, 1, '2019-06-26 16:14:06', '2019-06-26 16:14:06', 0, 0, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `grocerylist`
--

CREATE TABLE `grocerylist` (
  `id` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `creationDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ownerId` int(100) NOT NULL,
  `groupId` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `grocerylist`
--

INSERT INTO `grocerylist` (`id`, `name`, `creationDat`, `modDat`, `ownerId`, `groupId`) VALUES
(1, 'list1', '2019-06-25 20:02:38', '2019-06-25 20:02:38', 2, 1),
(2, 'list2', '2019-06-26 07:56:10', '2019-06-26 07:56:10', 2, 10),
(3, 'rrrrrrrrrr', '2019-06-26 11:09:57', '2019-06-26 11:09:57', 2, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `grocerylistarticle`
--

CREATE TABLE `grocerylistarticle` (
  `grocerylistId` int(11) NOT NULL,
  `articleId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `grocerylistarticle`
--

INSERT INTO `grocerylistarticle` (`grocerylistId`, `articleId`) VALUES
(1, 1),
(1, 2),
(1, 3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groups`
--

CREATE TABLE `groups` (
  `id` int(100) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `groups`
--

INSERT INTO `groups` (`id`, `name`) VALUES
(1, 'benis'),
(2, 'test'),
(3, 'Meine erste Gruppe'),
(4, 'Meine erste Gruppe'),
(5, 'Meine erste Gruppe'),
(6, 'test'),
(7, 'test'),
(8, 'Meine erste Gruppe'),
(9, 'test'),
(10, 'test'),
(11, 'test2'),
(12, 'e'),
(13, 'woopwoop'),
(14, 'test'),
(15, 'neue gruppe'),
(16, 'Neu'),
(17, 'neu'),
(18, 'neu'),
(19, 'neu'),
(20, 'Meine erste Gruppe'),
(21, 'test'),
(22, 'Meine erste Gruppe'),
(23, 'retert'),
(24, 'Meine erste Gruppe'),
(25, 'asdwwwwww'),
(26, 'WWWWWWWWWW');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groupuser`
--

CREATE TABLE `groupuser` (
  `groupId` int(100) NOT NULL,
  `userId` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `groupuser`
--

INSERT INTO `groupuser` (`groupId`, `userId`) VALUES
(2, 1),
(6, 1),
(7, 1),
(1, 2),
(10, 2),
(11, 2),
(12, 2),
(13, 2),
(14, 2),
(15, 2),
(16, 2),
(17, 2),
(18, 2),
(19, 2),
(4, 8),
(5, 9),
(8, 10),
(9, 10),
(20, 11),
(21, 11),
(22, 12),
(23, 12),
(24, 13),
(25, 13),
(26, 13);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `retailer`
--

CREATE TABLE `retailer` (
  `id` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `groupId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `retailer`
--

INSERT INTO `retailer` (`id`, `name`, `groupId`) VALUES
(1, 'aldi', 1),
(2, 'Lidl', 1),
(3, 'Edeka', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `id` int(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`id`, `email`, `name`) VALUES
(1, '', 'test'),
(2, 'test@example.com', 'test'),
(3, 'test@test.de', 'test'),
(4, 'test@bla.com', 'test'),
(5, 'test@penis.com', 'test'),
(6, 'test@neuer.com', 'test'),
(7, 'test@eple.com', 'woop'),
(8, 'test@fe.com', 'ter'),
(9, 'test@e.com', 'zz'),
(10, 'test@dd.com', 'tsss'),
(11, 'test@eae.com', 'test'),
(12, 'test@efffxample.com', 'eee'),
(13, 'test@eaxample.com', 'ttttt'),
(14, 'test@example.com', 'test'),
(15, 'test@example.com', 'test'),
(16, 'test@example.com', 'ttt');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`),
  ADD KEY `retailerArticle` (`retailerId`),
  ADD KEY `ownerId` (`ownerId`),
  ADD KEY `groupId` (`groupId`);

--
-- Indizes für die Tabelle `grocerylist`
--
ALTER TABLE `grocerylist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `groupGrocerylist` (`groupId`),
  ADD KEY `owner` (`ownerId`);

--
-- Indizes für die Tabelle `grocerylistarticle`
--
ALTER TABLE `grocerylistarticle`
  ADD PRIMARY KEY (`articleId`,`grocerylistId`) USING BTREE,
  ADD KEY `grocerylistId` (`grocerylistId`);

--
-- Indizes für die Tabelle `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `groupuser`
--
ALTER TABLE `groupuser`
  ADD PRIMARY KEY (`groupId`,`userId`),
  ADD KEY `userId` (`userId`);

--
-- Indizes für die Tabelle `retailer`
--
ALTER TABLE `retailer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `groupId` (`groupId`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `article`
--
ALTER TABLE `article`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `grocerylist`
--
ALTER TABLE `grocerylist`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT für Tabelle `groups`
--
ALTER TABLE `groups`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT für Tabelle `retailer`
--
ALTER TABLE `retailer`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `article_ibfk_1` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `article_ibfk_2` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `groupId` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `retailerArticle` FOREIGN KEY (`retailerId`) REFERENCES `retailer` (`id`);

--
-- Constraints der Tabelle `grocerylist`
--
ALTER TABLE `grocerylist`
  ADD CONSTRAINT `grocerylist_ibfk_1` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `groupGrocerylist` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `owner` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`);

--
-- Constraints der Tabelle `grocerylistarticle`
--
ALTER TABLE `grocerylistarticle`
  ADD CONSTRAINT `grocerylistarticle_ibfk_1` FOREIGN KEY (`articleId`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `grocerylistarticle_ibfk_2` FOREIGN KEY (`grocerylistId`) REFERENCES `grocerylist` (`id`);

--
-- Constraints der Tabelle `groupuser`
--
ALTER TABLE `groupuser`
  ADD CONSTRAINT `groupuser_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `groupuser_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);

--
-- Constraints der Tabelle `retailer`
--
ALTER TABLE `retailer`
  ADD CONSTRAINT `retailer_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
