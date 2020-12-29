

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apartment`
--

--
-- Table structure for table `apartmenttable`
--

CREATE TABLE `apartmenttable` (
  `Id` int(11) NOT NULL,
  `Buildingname` varchar(50) NOT NULL,
  `Flatnumber` varchar(50) NOT NULL,
  `Tenant` varchar(150) NOT NULL,
  `Rent` double NOT NULL DEFAULT '0',
  `StartDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `apartmenttable`
--

INSERT INTO `apartmenttable` (`Id`, `Buildingname`, `Flatnumber`, `Tenant`, `Rent`, `StartDate`) VALUES
(2, 'MCS Complex', '101', 'john', 10000, '2018-09-04'),
(3, 'MCS Complex', '102', 'Smith', 5000, '2018-09-06');

-- --------------------------------------------------------

--
-- Table structure for table `buildingtable`
--

CREATE TABLE `buildingtable` (
  `Id` int(11) NOT NULL,
  `BuildingName` varchar(50) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `Owner` varchar(150) NOT NULL,
  `Floors` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buildingtable`
--

INSERT INTO `buildingtable` (`Id`, `BuildingName`, `Address`, `Owner`, `Floors`) VALUES
(1, 'MCS Complex', 'street road ', 'john', '05');

-- --------------------------------------------------------

--
-- Table structure for table `ownertable`
--

CREATE TABLE `ownertable` (
  `Id` int(11) NOT NULL,
  `OwnerName` varchar(50) NOT NULL,
  `OwnerContact` varchar(100) NOT NULL,
  `OwnerAddress` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ownertable`
--

INSERT INTO `ownertable` (`Id`, `OwnerName`, `OwnerContact`, `OwnerAddress`) VALUES
(1, 'bob', '123456789', 'stadium main road');

-- --------------------------------------------------------

--
-- Table structure for table `paymenttable`
--

CREATE TABLE `paymenttable` (
  `Id` int(11) NOT NULL,
  `Date` date DEFAULT NULL,
  `Buildingname` varchar(50) NOT NULL,
  `tName` varchar(50) NOT NULL,
  `FlatNum` varchar(15) NOT NULL,
  `PaidRent` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paymenttable`
--

INSERT INTO `paymenttable` (`Id`, `Date`, `Buildingname`, `tName`, `FlatNum`, `PaidRent`) VALUES
(1, '2018-09-04', 'MCS Complex', 'john', '101', 10000);

-- --------------------------------------------------------

--
-- Table structure for table `tenanttable`
--

CREATE TABLE `tenanttable` (
  `Id` int(11) NOT NULL,
  `TenantName` varchar(50) NOT NULL,
  `TenantContact` varchar(100) NOT NULL,
  `TenantAddress` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tenanttable`
--

INSERT INTO `tenanttable` (`Id`, `TenantName`, `TenantContact`, `TenantAddress`) VALUES
(1, 'john', '123456789', 'street road texas');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `apartmenttable`
--
ALTER TABLE `apartmenttable`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `par_ind` (`Buildingname`);

--
-- Indexes for table `buildingtable`
--
ALTER TABLE `buildingtable`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `BuildingName` (`BuildingName`);

--
-- Indexes for table `ownertable`
--
ALTER TABLE `ownertable`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `paymenttable`
--
ALTER TABLE `paymenttable`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Buildingname` (`Buildingname`);

--
-- Indexes for table `tenanttable`
--
ALTER TABLE `tenanttable`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `apartmenttable`
--
ALTER TABLE `apartmenttable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `buildingtable`
--
ALTER TABLE `buildingtable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `ownertable`
--
ALTER TABLE `ownertable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `paymenttable`
--
ALTER TABLE `paymenttable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tenanttable`
--
ALTER TABLE `tenanttable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `apartmenttable`
--
ALTER TABLE `apartmenttable`
  ADD CONSTRAINT `fk_branchTable` FOREIGN KEY (`Buildingname`) REFERENCES `buildingtable` (`BuildingName`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
