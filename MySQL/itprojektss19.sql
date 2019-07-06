-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 04. Jul 2019 um 11:30
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
  `articleId` int(100) NOT NULL,
  `articleName` varchar(100) NOT NULL,
  `articleQuantity` int(100) NOT NULL,
  `articleUnit` varchar(100) NOT NULL,
  `articleRetailerId` int(100) DEFAULT NULL,
  `articleOwnerId` int(100) DEFAULT NULL,
  `articleGroupId` int(11) DEFAULT NULL,
  `articleCreationDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `articleModDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `articleBoolean` tinyint(1) NOT NULL,
  `articleFav` tinyint(1) NOT NULL,
  `articleDelDat` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `grocerylist`
--

CREATE TABLE `grocerylist` (
  `groceryListId` int(100) NOT NULL,
  `groceryListName` varchar(100) NOT NULL,
  `groceryListCreationDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `groceryListModDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `groceryListOwnerId` int(100) DEFAULT NULL,
  `groceryListGroupId` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `grocerylistarticle`
--

CREATE TABLE `grocerylistarticle` (
  `grocerylistId` int(11) NOT NULL,
  `articleId` int(11) NOT NULL,
  `retailerId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groups`
--

CREATE TABLE `groups` (
  `groupId` int(100) NOT NULL,
  `groupName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groupuser`
--

CREATE TABLE `groupuser` (
  `groupId` int(100) NOT NULL,
  `userId` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `retailer`
--

CREATE TABLE `retailer` (
  `retailerId` int(100) NOT NULL,
  `retailerName` varchar(100) NOT NULL,
  `retailerGroupId` int(11) NOT NULL,
  `retailerUserId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE `user` (
  `userId` int(100) NOT NULL,
  `userEmail` varchar(100) NOT NULL,
  `userName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`articleId`),
  ADD KEY `retailerArticle` (`articleRetailerId`),
  ADD KEY `ownerId` (`articleOwnerId`),
  ADD KEY `groupId` (`articleGroupId`);

--
-- Indizes für die Tabelle `grocerylist`
--
ALTER TABLE `grocerylist`
  ADD PRIMARY KEY (`groceryListId`),
  ADD KEY `groupGrocerylist` (`groceryListGroupId`),
  ADD KEY `owner` (`groceryListOwnerId`);

--
-- Indizes für die Tabelle `grocerylistarticle`
--
ALTER TABLE `grocerylistarticle`
  ADD PRIMARY KEY (`articleId`,`grocerylistId`) USING BTREE,
  ADD KEY `grocerylistId` (`grocerylistId`),
  ADD KEY `grocerylistarticle_ibfk_3` (`retailerId`);

--
-- Indizes für die Tabelle `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`groupId`);

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
  ADD PRIMARY KEY (`retailerId`),
  ADD KEY `groupId` (`retailerGroupId`),
  ADD KEY `accountable` (`retailerUserId`),
  ADD KEY `accountable_2` (`retailerUserId`);

--
-- Indizes für die Tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userId`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `article`
--
ALTER TABLE `article`
  MODIFY `articleId` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `grocerylist`
--
ALTER TABLE `grocerylist`
  MODIFY `groceryListId` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT für Tabelle `groups`
--
ALTER TABLE `groups`
  MODIFY `groupId` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT für Tabelle `retailer`
--
ALTER TABLE `retailer`
  MODIFY `retailerId` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `user`
--
ALTER TABLE `user`
  MODIFY `userId` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `article_ibfk_1` FOREIGN KEY (`articleOwnerId`) REFERENCES `user` (`userId`) ON DELETE SET NULL,
  ADD CONSTRAINT `groupId` FOREIGN KEY (`articleGroupId`) REFERENCES `groups` (`groupId`) ON DELETE SET NULL,
  ADD CONSTRAINT `retailerArticle` FOREIGN KEY (`articleRetailerId`) REFERENCES `retailer` (`retailerId`) ON DELETE SET NULL;

--
-- Constraints der Tabelle `grocerylist`
--
ALTER TABLE `grocerylist`
  ADD CONSTRAINT `grocerylist_ibfk_1` FOREIGN KEY (`groceryListOwnerId`) REFERENCES `user` (`userId`) ON DELETE SET NULL,
  ADD CONSTRAINT `groupGrocerylist` FOREIGN KEY (`groceryListGroupId`) REFERENCES `groups` (`groupId`) ON DELETE CASCADE;

--
-- Constraints der Tabelle `grocerylistarticle`
--
ALTER TABLE `grocerylistarticle`
  ADD CONSTRAINT `grocerylistarticle_ibfk_1` FOREIGN KEY (`articleId`) REFERENCES `article` (`articleId`) ON DELETE CASCADE,
  ADD CONSTRAINT `grocerylistarticle_ibfk_2` FOREIGN KEY (`grocerylistId`) REFERENCES `grocerylist` (`groceryListId`) ON DELETE CASCADE,
  ADD CONSTRAINT `grocerylistarticle_ibfk_3` FOREIGN KEY (`retailerId`) REFERENCES `retailer` (`retailerId`) ON DELETE CASCADE;

--
-- Constraints der Tabelle `groupuser`
--
ALTER TABLE `groupuser`
  ADD CONSTRAINT `groupuser_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `groups` (`groupId`) ON DELETE CASCADE,
  ADD CONSTRAINT `groupuser_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE;

--
-- Constraints der Tabelle `retailer`
--
ALTER TABLE `retailer`
  ADD CONSTRAINT `retailer_ibfk_1` FOREIGN KEY (`retailerGroupId`) REFERENCES `groups` (`groupId`) ON DELETE CASCADE,
  ADD CONSTRAINT `retailer_ibfk_2` FOREIGN KEY (`retailerUserId`) REFERENCES `user` (`userId`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
