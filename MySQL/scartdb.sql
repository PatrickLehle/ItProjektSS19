SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `ITPROJEKTSS19`
--

-- --------------------------------------------------------

--
-- Table structure for table `article`
--

CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `quantity` int(100) NOT NULL,
  `unit` varchar(100) NOT NULL,
  `retailerId` int(11) NOT NULL,
  `ownerId` int(11) NOT NULL,
  `creationDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `grocerylist`
--

CREATE TABLE `grocerylist` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `creationDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modDat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `groupId` int(11) NOT NULL,
  `ownerId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `grocerylistarticle`
--

CREATE TABLE `grocerylistarticle` (
  `grocerylistId` int(11) NOT NULL,
  `articleId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `groupuser`
--

CREATE TABLE `groupuser` (
  `groupId` int(11) NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `retailer`
--

CREATE TABLE `retailer` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`),
  ADD KEY `retailerId` (`retailerId`),
  ADD KEY `article_ibfk_2` (`ownerId`);

--
-- Indexes for table `grocerylist`
--
ALTER TABLE `grocerylist`
  ADD PRIMARY KEY (`id`),
  ADD KEY `groupId` (`groupId`),
  ADD KEY `grocerylist_ibfk_2` (`ownerId`);

--
-- Indexes for table `grocerylistarticle`
--
ALTER TABLE `grocerylistarticle`
  ADD PRIMARY KEY (`grocerylistId`,`articleId`),
  ADD KEY `articleId` (`articleId`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `groupuser`
--
ALTER TABLE `groupuser`
  ADD KEY `groupId` (`groupId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `retailer`
--
ALTER TABLE `retailer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `article`
--
ALTER TABLE `article`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `grocerylist`
--
ALTER TABLE `grocerylist`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `retailer`
--
ALTER TABLE `retailer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `article_ibfk_1` FOREIGN KEY (`retailerId`) REFERENCES `retailer` (`id`),
  ADD CONSTRAINT `article_ibfk_2` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`);

--
-- Constraints for table `grocerylist`
--
ALTER TABLE `grocerylist`
  ADD CONSTRAINT `grocerylist_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `grocerylist_ibfk_2` FOREIGN KEY (`ownerId`) REFERENCES `user` (`id`);

--
-- Constraints for table `grocerylistarticle`
--
ALTER TABLE `grocerylistarticle`
  ADD CONSTRAINT `grocerylistarticle_ibfk_1` FOREIGN KEY (`articleId`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `grocerylistarticle_ibfk_2` FOREIGN KEY (`grocerylistId`) REFERENCES `grocerylist` (`id`);

--
-- Constraints for table `groupuser`
--
ALTER TABLE `groupuser`
  ADD CONSTRAINT `groupuser_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id`),
  ADD CONSTRAINT `groupuser_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`id`);
