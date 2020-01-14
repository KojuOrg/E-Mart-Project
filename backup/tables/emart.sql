-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 19, 2019 at 02:05 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 5.6.39

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `emart`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` bigint(20) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `contact_num` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `comment_details`
--

CREATE TABLE `comment_details` (
  `comment_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `comment` text NOT NULL,
  `reg_date` date NOT NULL,
  `del_date` date NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `comment_reply`
--

CREATE TABLE `comment_reply` (
  `reply_id` bigint(20) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  `reply` text NOT NULL,
  `reg_date` date NOT NULL,
  `del_date` date NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `feedback_details`
--

CREATE TABLE `feedback_details` (
  `feedback_id` bigint(20) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `feedback` text NOT NULL,
  `reg_date` date NOT NULL,
  `del_date` date NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `product_name` varchar(30) NOT NULL,
  `price` int(11) NOT NULL,
  `category` varchar(30) NOT NULL,
  `price_negotiable` tinyint(1) NOT NULL,
  `condition` tinyint(4) NOT NULL,
  `used_for` varchar(40) NOT NULL,
  `product_specification` text NOT NULL,
  `delivery_area` varchar(50) NOT NULL,
  `warranty_period` varchar(30) NOT NULL,
  `delivery_charges` mediumint(9) NOT NULL,
  `reg_date` date NOT NULL,
  `del_date` date NOT NULL,
  `num_of_views` int(11) NOT NULL,
  `photo1` longblob NOT NULL,
  `photo2` longblob NOT NULL,
  `photo3` longblob NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `address` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `contact_num` varchar(10) NOT NULL,
  `password` varchar(30) NOT NULL,
  `reg_date` date NOT NULL,
  `del_date` date NOT NULL,
  `num_of_uploaded_items` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `comment_details`
--
ALTER TABLE `comment_details`
  ADD PRIMARY KEY (`comment_id`);

--
-- Indexes for table `comment_reply`
--
ALTER TABLE `comment_reply`
  ADD PRIMARY KEY (`reply_id`);

--
-- Indexes for table `feedback_details`
--
ALTER TABLE `feedback_details`
  ADD PRIMARY KEY (`feedback_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `comment_details`
--
ALTER TABLE `comment_details`
  MODIFY `comment_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `comment_reply`
--
ALTER TABLE `comment_reply`
  MODIFY `reply_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `feedback_details`
--
ALTER TABLE `feedback_details`
  MODIFY `feedback_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
