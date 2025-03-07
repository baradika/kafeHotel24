-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 06, 2025 at 04:17 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasircafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `harga`
--

CREATE TABLE `harga` (
  `id` int(10) NOT NULL,
  `menu` varchar(20) NOT NULL,
  `item1` varchar(20) NOT NULL,
  `item2` varchar(20) NOT NULL,
  `item3` varchar(20) NOT NULL,
  `item4` varchar(20) NOT NULL,
  `total_harga` varchar(20) NOT NULL,
  `harga_jual` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `harga`
--

INSERT INTO `harga` (`id`, `menu`, `item1`, `item2`, `item3`, `item4`, `total_harga`, `harga_jual`) VALUES
(1, 'Thai Tea cincau', 'teh', 'cincau', 'pack', 'es', '83051.0', '10000'),
(2, 'Dimsum 3pcs', 'packaging', 'Dimsum', 'garpu', 'saus', '8600.0', '10,000');

-- --------------------------------------------------------

--
-- Table structure for table `laporan`
--

CREATE TABLE `laporan` (
  `tanggal` date NOT NULL,
  `untung` int(11) NOT NULL,
  `rugi` int(11) NOT NULL,
  `deskripsi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id` int(5) NOT NULL,
  `barang` varchar(30) NOT NULL,
  `stok` int(10) NOT NULL,
  `harga` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id`, `barang`, `stok`, `harga`) VALUES
(1, 'Thai Tea', 50, '5000'),
(3, 'Thai Tea cincau', 20, '13000'),
(5, 'Thai Tea (cincau)', 29, '13000'),
(6, 'Dimsum 3pcs', 10, '10000'),
(7, 'Dimsum 5pcs', 10, '15000'),
(8, 'Dalgona', 10, '8000');

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `no_faktur` varchar(20) NOT NULL,
  `tgl` varchar(12) NOT NULL,
  `item` varchar(70) NOT NULL,
  `qty` varchar(20) NOT NULL,
  `harga` varchar(30) NOT NULL,
  `jumlah` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `penjualan`
--

INSERT INTO `penjualan` (`no_faktur`, `tgl`, `item`, `qty`, `harga`, `jumlah`) VALUES
('CCH00001', 'Tanggal', 'Thai Tea cincau', '1', '13,000', '13,000'),
('CCH00001', 'Tanggal', 'Thai Tea', '1', '5,000', '5,000'),
('CCH00002', 'Tanggal', 'Thai Tea cincau', '1', '13,000', '13,000'),
('CCH00003', '05/03/2025', 'Thai Tea cincau', '2', '13,000', '26,000'),
('CCH00003', '05/03/2025', 'Thai Tea', '7', '5,000', '35,000'),
('CCH00004', '05/03/2025', 'Thai Tea cincau', '1', '13,000', '13,000'),
('CCH00004', '05/03/2025', 'Thai Tea', '1', '5,000', '5,000'),
('CCH00005', '05/03/2025', 'Thai Tea cincau', '1', '13,000', '13,000'),
('CCH00006', '05/03/2025', 'Thai Tea cincau', '1', '13,000', '13,000'),
('CCH00007', '05/03/2025', 'Thai Tea', '6', '5,000', '30,000'),
('CCH00008', '05/03/2025', 'Thai Tea cincau', '5', '13,000', '65,000'),
('CCH00009', '05/03/2025', 'Thai Tea', '10', '5,000', '50,000'),
('CCH00010', '05/03/2025', 'Thai Tea cincau', '12', '13,000', '156,000'),
('CCH00011', '05/03/2025', 'Thai Tea cincau', '13', '13,000', '169,000'),
('CCH00012', '05/03/2025', 'Thai Tea', '100', '5,000', '500,000'),
('CCH00013', '05/03/2025', 'Thai Tea cincau', '1', '13,000', '13,000'),
('CCH00014', '05/03/2025', 'Thai Tea', '12', '5,000', '60,000'),
('CCH00015', '05/03/2025', 'Thai Tea cincau', '5', '13,000', '65,000'),
('CCH00016', '05/03/2025', 'Thai Tea cincau', '1', '13,000', '13,000'),
('CCH00017', '05/03/2025', 'Thai Tea cincau', '1', '13,000', '13,000'),
('CCH00017', '05/03/2025', 'Thai Tea', '5', '5,000', '25,000'),
('CCH00018', '06/03/2025', 'Dalgona', '1', '8,000', '8,000');

--
-- Triggers `penjualan`
--
DELIMITER $$
CREATE TRIGGER `kurangi_stok_penjualan` AFTER INSERT ON `penjualan` FOR EACH ROW BEGIN
    
    IF EXISTS (SELECT 1 FROM menu WHERE barang = NEW.item) THEN
        
        IF (SELECT stok FROM menu WHERE barang = NEW.item) >= NEW.qty THEN
            
            UPDATE menu 
            SET stok = stok - NEW.qty
            WHERE barang = NEW.item;
        ELSE
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Stok tidak mencukupi!';
        END IF;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `kode` int(10) NOT NULL,
  `toko` varchar(20) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `barang` varchar(30) NOT NULL,
  `harga` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`kode`, `toko`, `kategori`, `barang`, `harga`) VALUES
(1, 'Sumber Makmur', 'Makanan', 'Dimsum 50pcs', '85500'),
(3, 'bood bakrie', 'Minuman', 'bubuk teh thailand', '49800'),
(4, 'Riko agen', 'Minuman', 'Gula 500gr', '25000'),
(5, 'Riko agen', 'Minuman', 'susu uht 1lt', '27900'),
(6, 'Kimme plastik', 'Packaging', 'cup 16oz', '13700'),
(7, 'Kimme plastik', 'Packaging', 'mika 15cm', '10500'),
(8, 'Kimme plastik', 'Packaging', 'sedotan hitam', '8900'),
(9, 'Kimme plastik', 'Packaging', 'garpu kue', '6900'),
(10, 'Kimme plastik', 'Packaging', 'tutup cup 16oz', '10700'),
(11, 'Sumber makmur', 'Makanan', 'jelly cincau 100gr', '5000');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` varchar(8) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(30) NOT NULL,
  `role` enum('Kitchen','Cashier','','') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nama`, `username`, `password`, `role`) VALUES
('CH7924', 'Eliza', 'CH_eliza', 'eliza123', 'Kitchen'),
('CH6924', 'Gerry', 'CH_gerry', 'gerry123', 'Kitchen'),
('', '', '', '', ''),
('CH5424', 'Regan', 'CH_regan', 'regan123', 'Cashier');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `harga`
--
ALTER TABLE `harga`
  ADD PRIMARY KEY (`id`),
  ADD KEY `menu` (`menu`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `barang` (`barang`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`kode`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `harga`
--
ALTER TABLE `harga`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `kode` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `harga`
--
ALTER TABLE `harga`
  ADD CONSTRAINT `menu` FOREIGN KEY (`menu`) REFERENCES `menu` (`barang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
