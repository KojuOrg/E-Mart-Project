-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 25, 2020 at 02:46 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.3.14

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
  `contact_num` varchar(10) NOT NULL,
  `invalid_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `username`, `password`, `email`, `contact_num`, `invalid_count`) VALUES
(1, 'admin', 'admin', 'myselfsujan67@gmail.com', '9849675658', 2);

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

--
-- Dumping data for table `comment_details`
--

INSERT INTO `comment_details` (`comment_id`, `user_id`, `product_id`, `comment`, `reg_date`, `del_date`, `status`) VALUES
(1, 1, 2, 'Do you have many pieces of this is only one ?', '2020-01-10', '1111-11-11', 0);

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

--
-- Dumping data for table `comment_reply`
--

INSERT INTO `comment_reply` (`reply_id`, `comment_id`, `reply`, `reg_date`, `del_date`, `status`) VALUES
(1, 1, 'Yes this is the single piece.', '2020-01-11', '1111-11-11', 0);

-- --------------------------------------------------------

--
-- Table structure for table `feedback_details`
--

CREATE TABLE `feedback_details` (
  `feedback_id` bigint(20) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `feedback` text NOT NULL,
  `reg_date` date NOT NULL,
  `del_date` date NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `feedback_details`
--

INSERT INTO `feedback_details` (`feedback_id`, `full_name`, `email`, `subject`, `feedback`, `reg_date`, `del_date`, `status`) VALUES
(1, 'Sujan Koju', 'myselfsujan67@gmail.com', 'Test', 'This is a test message.', '2020-02-25', '1111-11-11', 0),
(2, 'Suman Koju', 'myselfkoju@gmail.com', 'Good Working', 'You poeple are working good on this project as every thing is going fine. Hope you will complete this project as soon as possible.\r\nThankyou.!!!', '2020-02-25', '2020-02-25', 0),
(3, 'Sunita Koju', 'sunita@gmail.com', 'Emart a Perfact Place', 'Emart is the perfect place to look for both brand new and the used products. I am able to sell my product through the E-Mart. \r\nThanks.', '2020-02-25', '2020-02-25', 0);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `product_name` varchar(30) NOT NULL,
  `price` float NOT NULL,
  `category` varchar(30) NOT NULL,
  `price_negotiable` tinyint(1) NOT NULL,
  `product_condition` tinyint(4) NOT NULL,
  `used_for` varchar(40) NOT NULL,
  `product_specification` text NOT NULL,
  `delivery_area` varchar(50) NOT NULL,
  `warranty_period` varchar(30) NOT NULL,
  `delivery_charges` float NOT NULL,
  `reg_date` date NOT NULL,
  `del_date` date NOT NULL,
  `num_of_views` int(11) NOT NULL,
  `photo1` longblob NOT NULL,
  `photo2` longblob NOT NULL,
  `photo3` longblob NOT NULL,
  `photo1_name` varchar(255) NOT NULL,
  `photo2_name` varchar(255) NOT NULL,
  `photo3_name` varchar(255) NOT NULL,
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
  `status` tinyint(1) NOT NULL,
  `invalid_count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `full_name`, `user_name`, `address`, `email`, `contact_num`, `password`, `reg_date`, `del_date`, `num_of_uploaded_items`, `status`, `invalid_count`) VALUES
(1, 'Sujan Koju', 'sujan5658', 'Golmadhi', 'myselfsujan67@gmail.com', '9849675658', 'Difficult', '2019-12-07', '1111-11-11', 11, 0, 0),
(2, 'suman', 'suman123', 'Kathmandu', 'sujan458koju@gmail.com', '9849172159', 'helloworld', '2020-01-16', '1111-11-11', 7, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `username` (`username`,`email`);

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
  MODIFY `admin_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `comment_details`
--
ALTER TABLE `comment_details`
  MODIFY `comment_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `comment_reply`
--
ALTER TABLE `comment_reply`
  MODIFY `reply_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `feedback_details`
--
ALTER TABLE `feedback_details`
  MODIFY `feedback_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
