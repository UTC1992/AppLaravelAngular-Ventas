-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-05-2019 a las 06:22:12
-- Versión del servidor: 10.1.36-MariaDB
-- Versión de PHP: 7.1.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `contabilidad`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_productos_mas_vendidos` (IN `empresa` INT, IN `inicio` DATE, IN `fin` DATE)  BEGIN
select T1.name as product,T1.description, count(T1.id) as cantidad_vendido from nty_invoice_items as T0
inner join nty_items as T1 on T0.item_id=T1.id
where T1.company_id=empresa and date(T0.created_at) between inicio and fin
group by T1.id
order by cantidad_vendido desc limit 15;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ventas_diario` (IN `empresa` INT, IN `inicio` DATE)  BEGIN

	select * from nty_invoices where invoice_status_code!='draft'
	and date(created_at)=inicio and company_id=empresa;
    END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_accounts`
--

CREATE TABLE `nty_accounts` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `number` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `opening_balance` double(15,4) NOT NULL DEFAULT '0.0000',
  `bank_name` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bank_phone` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bank_address` text COLLATE utf8mb4_unicode_ci,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_accounts`
--

INSERT INTO `nty_accounts` (`id`, `company_id`, `name`, `number`, `currency_code`, `opening_balance`, `bank_name`, `bank_phone`, `bank_address`, `enabled`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'Efectivo', '1', 'USD', 0.0000, 'Efectivo', NULL, NULL, 1, '2019-04-30 23:49:43', '2019-04-30 23:49:43', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_bills`
--

CREATE TABLE `nty_bills` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `bill_number` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_number` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bill_status_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `billed_at` datetime NOT NULL,
  `due_at` datetime NOT NULL,
  `amount` double(15,4) NOT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `currency_rate` double(15,8) NOT NULL,
  `vendor_id` int(11) NOT NULL,
  `vendor_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vendor_email` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vendor_tax_number` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vendor_phone` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vendor_address` text COLLATE utf8mb4_unicode_ci,
  `notes` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `category_id` int(11) NOT NULL DEFAULT '1',
  `parent_id` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_bill_histories`
--

CREATE TABLE `nty_bill_histories` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `status_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `notify` tinyint(1) NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_bill_items`
--

CREATE TABLE `nty_bill_items` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sku` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantity` double(7,2) NOT NULL,
  `price` double(15,4) NOT NULL,
  `total` double(15,4) NOT NULL,
  `tax` double(15,4) NOT NULL DEFAULT '0.0000',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_bill_item_taxes`
--

CREATE TABLE `nty_bill_item_taxes` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `bill_item_id` int(11) NOT NULL,
  `tax_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` double(15,4) NOT NULL DEFAULT '0.0000',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_bill_payments`
--

CREATE TABLE `nty_bill_payments` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `paid_at` datetime NOT NULL,
  `amount` double(15,4) NOT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `currency_rate` double(15,8) NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `payment_method` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reference` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `reconciled` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_bill_statuses`
--

CREATE TABLE `nty_bill_statuses` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_bill_statuses`
--

INSERT INTO `nty_bill_statuses` (`id`, `company_id`, `name`, `code`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'Borrador', 'draft', '2019-04-30 23:49:43', '2019-04-30 23:49:43', NULL),
(2, 1, 'Recibido', 'received', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(3, 1, 'Parcial', 'partial', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(4, 1, 'Pagado', 'paid', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_bill_totals`
--

CREATE TABLE `nty_bill_totals` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  `code` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` double(15,4) NOT NULL,
  `sort_order` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_categories`
--

CREATE TABLE `nty_categories` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `color` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_categories`
--

INSERT INTO `nty_categories` (`id`, `company_id`, `name`, `type`, `color`, `enabled`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'Transferencia ', 'other', '#605ca8', 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(2, 1, 'Depósito', 'income', '#f39c12', 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(3, 1, 'Ventas', 'income', '#6da252', 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(4, 1, 'Otro', 'expense', '#d2d6de', 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(5, 1, 'General', 'item', '#00c0ef', 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_companies`
--

CREATE TABLE `nty_companies` (
  `id` int(10) UNSIGNED NOT NULL,
  `domain` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_companies`
--

INSERT INTO `nty_companies` (`id`, `domain`, `enabled`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'www.tecnosolutionscorp.com', 1, '2019-04-30 23:49:43', '2019-05-01 00:53:20', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_currencies`
--

CREATE TABLE `nty_currencies` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rate` double(15,8) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `precision` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `symbol` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `symbol_first` int(11) NOT NULL DEFAULT '1',
  `decimal_mark` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `thousands_separator` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_currencies`
--

INSERT INTO `nty_currencies` (`id`, `company_id`, `name`, `code`, `rate`, `enabled`, `created_at`, `updated_at`, `deleted_at`, `precision`, `symbol`, `symbol_first`, `decimal_mark`, `thousands_separator`) VALUES
(1, 1, 'Dólar EEUU', 'USD', 1.00000000, 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL, '2', '$', 1, '.', ','),
(2, 1, 'Euro', 'EUR', 1.25000000, 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL, '2', '€', 1, ',', '.'),
(3, 1, 'Libra Esterlina', 'GBP', 1.60000000, 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL, '2', '£', 1, '.', ','),
(4, 1, 'Libra Turca', 'TRY', 0.80000000, 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL, '2', '₺', 1, ',', '.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_customers`
--

CREATE TABLE `nty_customers` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tax_number` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` text COLLATE utf8mb4_unicode_ci,
  `website` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `reference` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_customers`
--

INSERT INTO `nty_customers` (`id`, `company_id`, `user_id`, `name`, `email`, `tax_number`, `phone`, `address`, `website`, `currency_code`, `enabled`, `created_at`, `updated_at`, `deleted_at`, `reference`) VALUES
(1, 1, NULL, 'Omar Guanoluisa', 'omar.guanoluisa25@gmail.com', '0503254849001', '2103005', 'Isimbo #2', NULL, 'USD', 1, '2019-05-01 04:19:48', '2019-05-01 04:19:48', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_failed_jobs`
--

CREATE TABLE `nty_failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_invoices`
--

CREATE TABLE `nty_invoices` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `invoice_number` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order_number` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `invoice_status_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `invoiced_at` datetime NOT NULL,
  `due_at` datetime NOT NULL,
  `amount` double(15,4) NOT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `currency_rate` double(15,8) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `customer_email` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_tax_number` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_phone` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `customer_address` text COLLATE utf8mb4_unicode_ci,
  `notes` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `category_id` int(11) NOT NULL DEFAULT '1',
  `parent_id` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_invoices`
--

INSERT INTO `nty_invoices` (`id`, `company_id`, `invoice_number`, `order_number`, `invoice_status_code`, `invoiced_at`, `due_at`, `amount`, `currency_code`, `currency_rate`, `customer_id`, `customer_name`, `customer_email`, `customer_tax_number`, `customer_phone`, `customer_address`, `notes`, `created_at`, `updated_at`, `deleted_at`, `category_id`, `parent_id`) VALUES
(1, 1, 'INV-00001', NULL, 'paid', '2019-04-30 23:54:13', '2019-04-30 23:54:13', 1.1200, 'USD', 1.00000000, 1, 'Omar Guanoluisa', 'omar.guanoluisa25@gmail.com', '0503254849001', '2103005', 'Isimbo #2', NULL, '2019-05-01 04:33:13', '2019-05-01 04:54:14', NULL, 3, 0),
(2, 1, 'INV-00002', NULL, 'paid', '2019-04-30 23:57:38', '2019-04-30 23:57:38', 22.4000, 'USD', 1.00000000, 1, 'Omar Guanoluisa', 'omar.guanoluisa25@gmail.com', '0503254849001', '2103005', 'Isimbo #2', NULL, '2019-05-01 04:55:24', '2019-05-01 04:57:38', NULL, 3, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_invoice_histories`
--

CREATE TABLE `nty_invoice_histories` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `status_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `notify` tinyint(1) NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_invoice_histories`
--

INSERT INTO `nty_invoice_histories` (`id`, `company_id`, `invoice_id`, `status_code`, `notify`, `description`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 1, 'draft', 0, '¡INV-00001 agregado!', '2019-05-01 04:33:14', '2019-05-01 04:33:14', NULL),
(2, 1, 1, 'paid', 0, '$1.12 Pago ', '2019-05-01 04:53:15', '2019-05-01 04:53:15', NULL),
(3, 1, 2, 'draft', 0, '¡INV-00002 agregado!', '2019-05-01 04:55:24', '2019-05-01 04:55:24', NULL),
(4, 1, 2, 'paid', 0, '$22.40 Pago ', '2019-05-01 04:56:11', '2019-05-01 04:56:11', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_invoice_items`
--

CREATE TABLE `nty_invoice_items` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sku` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantity` double(7,2) NOT NULL,
  `price` double(15,4) NOT NULL,
  `total` double(15,4) NOT NULL,
  `tax` double(15,4) NOT NULL DEFAULT '0.0000',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_invoice_items`
--

INSERT INTO `nty_invoice_items` (`id`, `company_id`, `invoice_id`, `item_id`, `name`, `sku`, `quantity`, `price`, `total`, `tax`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 1, 1, 'Pinta labios', '0987654321', 1.00, 1.0000, 1.0000, 0.1200, '2019-05-01 04:33:13', '2019-05-01 04:54:13', '2019-05-01 04:54:13'),
(2, 1, 1, 1, 'Pinta labios', '0987654321', 1.00, 1.0000, 1.0000, 0.1200, '2019-05-01 04:54:14', '2019-05-01 04:54:14', NULL),
(3, 1, 2, 1, 'Pinta labios', '0987654321', 20.00, 1.0000, 20.0000, 2.4000, '2019-05-01 04:55:24', '2019-05-01 04:57:38', '2019-05-01 04:57:38'),
(4, 1, 2, 1, 'Pinta labios', '0987654321', 20.00, 1.0000, 20.0000, 2.4000, '2019-05-01 04:57:38', '2019-05-01 04:57:38', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_invoice_item_taxes`
--

CREATE TABLE `nty_invoice_item_taxes` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `invoice_item_id` int(11) NOT NULL,
  `tax_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` double(15,4) NOT NULL DEFAULT '0.0000',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_invoice_item_taxes`
--

INSERT INTO `nty_invoice_item_taxes` (`id`, `company_id`, `invoice_id`, `invoice_item_id`, `tax_id`, `name`, `amount`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 1, 1, 1, 'IVA', 0.1200, '2019-05-01 04:33:13', '2019-05-01 04:33:13', NULL),
(2, 1, 1, 2, 1, 'IVA', 0.1200, '2019-05-01 04:54:14', '2019-05-01 04:54:14', NULL),
(3, 1, 2, 3, 1, 'IVA', 2.4000, '2019-05-01 04:55:24', '2019-05-01 04:55:24', NULL),
(4, 1, 2, 4, 1, 'IVA', 2.4000, '2019-05-01 04:57:38', '2019-05-01 04:57:38', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_invoice_payments`
--

CREATE TABLE `nty_invoice_payments` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `paid_at` datetime NOT NULL,
  `amount` double(15,4) NOT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `currency_rate` double(15,8) NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `payment_method` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reference` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `reconciled` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_invoice_payments`
--

INSERT INTO `nty_invoice_payments` (`id`, `company_id`, `invoice_id`, `account_id`, `paid_at`, `amount`, `currency_code`, `currency_rate`, `description`, `payment_method`, `reference`, `created_at`, `updated_at`, `deleted_at`, `reconciled`) VALUES
(1, 1, 1, 1, '2019-04-30 23:53:15', 1.1200, 'USD', 1.00000000, NULL, 'offlinepayment.cash.1', NULL, '2019-05-01 04:53:15', '2019-05-01 04:53:15', NULL, 0),
(2, 1, 2, 1, '2019-04-30 00:00:00', 22.4000, 'USD', 1.00000000, NULL, 'offlinepayment.cash.1', NULL, '2019-05-01 04:56:11', '2019-05-01 04:56:11', NULL, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_invoice_statuses`
--

CREATE TABLE `nty_invoice_statuses` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_invoice_statuses`
--

INSERT INTO `nty_invoice_statuses` (`id`, `company_id`, `name`, `code`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'Borrador', 'draft', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(2, 1, 'Enviado', 'sent', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(3, 1, 'Visto', 'viewed', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(4, 1, 'Aprobado', 'approved', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(5, 1, 'Parcial', 'partial', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(6, 1, 'Pagado', 'paid', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_invoice_totals`
--

CREATE TABLE `nty_invoice_totals` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `code` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` double(15,4) NOT NULL,
  `sort_order` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_invoice_totals`
--

INSERT INTO `nty_invoice_totals` (`id`, `company_id`, `invoice_id`, `code`, `name`, `amount`, `sort_order`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 1, 'sub_total', 'invoices.sub_total', 1.0000, 1, '2019-05-01 04:33:13', '2019-05-01 04:54:14', '2019-05-01 04:54:14'),
(2, 1, 1, 'tax', 'IVA', 0.1200, 2, '2019-05-01 04:33:13', '2019-05-01 04:54:14', '2019-05-01 04:54:14'),
(3, 1, 1, 'total', 'invoices.total', 1.1200, 3, '2019-05-01 04:33:14', '2019-05-01 04:54:14', '2019-05-01 04:54:14'),
(4, 1, 1, 'sub_total', 'invoices.sub_total', 1.0000, 1, '2019-05-01 04:54:14', '2019-05-01 04:54:14', NULL),
(5, 1, 1, 'tax', 'IVA', 0.1200, 2, '2019-05-01 04:54:14', '2019-05-01 04:54:14', NULL),
(6, 1, 1, 'total', 'invoices.total', 1.1200, 3, '2019-05-01 04:54:14', '2019-05-01 04:54:14', NULL),
(7, 1, 2, 'sub_total', 'invoices.sub_total', 20.0000, 1, '2019-05-01 04:55:24', '2019-05-01 04:57:38', '2019-05-01 04:57:38'),
(8, 1, 2, 'tax', 'IVA', 2.4000, 2, '2019-05-01 04:55:24', '2019-05-01 04:57:38', '2019-05-01 04:57:38'),
(9, 1, 2, 'total', 'invoices.total', 22.4000, 3, '2019-05-01 04:55:24', '2019-05-01 04:57:39', '2019-05-01 04:57:39'),
(10, 1, 2, 'sub_total', 'invoices.sub_total', 20.0000, 1, '2019-05-01 04:57:39', '2019-05-01 04:57:39', NULL),
(11, 1, 2, 'tax', 'IVA', 2.4000, 2, '2019-05-01 04:57:39', '2019-05-01 04:57:39', NULL),
(12, 1, 2, 'total', 'invoices.total', 22.4000, 3, '2019-05-01 04:57:39', '2019-05-01 04:57:39', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_items`
--

CREATE TABLE `nty_items` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sku` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `sale_price` double(15,4) NOT NULL,
  `purchase_price` double(15,4) NOT NULL,
  `quantity` int(11) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `tax_id` int(11) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_items`
--

INSERT INTO `nty_items` (`id`, `company_id`, `name`, `sku`, `description`, `sale_price`, `purchase_price`, `quantity`, `category_id`, `tax_id`, `enabled`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'Pinta labios', '0987654321', 'Pinta labios', 1.0000, 0.5000, 79, 5, 1, 1, '2019-05-01 03:10:35', '2019-05-01 04:57:38', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_jobs`
--

CREATE TABLE `nty_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `queue` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `attempts` tinyint(3) UNSIGNED NOT NULL,
  `reserved_at` int(10) UNSIGNED DEFAULT NULL,
  `available_at` int(10) UNSIGNED NOT NULL,
  `created_at` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_media`
--

CREATE TABLE `nty_media` (
  `id` int(10) UNSIGNED NOT NULL,
  `disk` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `directory` varchar(68) COLLATE utf8mb4_unicode_ci NOT NULL,
  `filename` varchar(121) COLLATE utf8mb4_unicode_ci NOT NULL,
  `extension` varchar(28) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mime_type` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `aggregate_type` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `size` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_media`
--

INSERT INTO `nty_media` (`id`, `disk`, `directory`, `filename`, `extension`, `mime_type`, `aggregate_type`, `size`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'uploads', '1/settings', 'bañoperritos', 'jpg', 'image/jpeg', 'image', 55855, '2019-05-01 00:50:46', '2019-05-01 00:52:32', '2019-05-01 00:52:32'),
(2, 'uploads', '1/settings', 'logoTecnoSolutiosCorp', 'png', 'image/png', 'image', 33058, '2019-05-01 00:53:20', '2019-05-01 08:52:37', '2019-05-01 08:52:37'),
(3, 'uploads', '1/items', 'pintalabios', 'jpg', 'image/jpeg', 'image', 50943, '2019-05-01 03:10:35', '2019-05-01 03:10:35', NULL),
(4, 'uploads', '1/settings', 'garfield', 'png', 'image/png', 'image', 173939, '2019-05-01 08:52:57', '2019-05-01 08:52:57', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_mediables`
--

CREATE TABLE `nty_mediables` (
  `media_id` int(10) UNSIGNED NOT NULL,
  `mediable_type` varchar(152) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mediable_id` int(10) UNSIGNED NOT NULL,
  `tag` varchar(68) COLLATE utf8mb4_unicode_ci NOT NULL,
  `order` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_mediables`
--

INSERT INTO `nty_mediables` (`media_id`, `mediable_type`, `mediable_id`, `tag`, `order`) VALUES
(1, 'App\\Models\\Common\\Company', 1, 'company_logo', 1),
(3, 'App\\Models\\Common\\Item', 1, 'picture', 1),
(2, 'App\\Models\\Common\\Company', 1, 'company_logo', 2),
(4, 'App\\Models\\Common\\Company', 1, 'company_logo', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_migrations`
--

CREATE TABLE `nty_migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_migrations`
--

INSERT INTO `nty_migrations` (`id`, `migration`, `batch`) VALUES
(1, '2017_09_01_000000_create_accounts_table', 1),
(2, '2017_09_01_000000_create_bills_table', 1),
(3, '2017_09_01_000000_create_categories_table', 1),
(4, '2017_09_01_000000_create_companies_table', 1),
(5, '2017_09_01_000000_create_currencies_table', 1),
(6, '2017_09_01_000000_create_customers_table', 1),
(7, '2017_09_01_000000_create_invoices_table', 1),
(8, '2017_09_01_000000_create_items_table', 1),
(9, '2017_09_01_000000_create_jobs_table', 1),
(10, '2017_09_01_000000_create_modules_table', 1),
(11, '2017_09_01_000000_create_notifications_table', 1),
(12, '2017_09_01_000000_create_password_resets_table', 1),
(13, '2017_09_01_000000_create_payments_table', 1),
(14, '2017_09_01_000000_create_revenues_table', 1),
(15, '2017_09_01_000000_create_roles_table', 1),
(16, '2017_09_01_000000_create_sessions_table', 1),
(17, '2017_09_01_000000_create_settings_table', 1),
(18, '2017_09_01_000000_create_taxes_table', 1),
(19, '2017_09_01_000000_create_transfers_table', 1),
(20, '2017_09_01_000000_create_users_table', 1),
(21, '2017_09_01_000000_create_vendors_table', 1),
(22, '2017_09_19_delete_offline_file', 1),
(23, '2017_10_11_000000_create_bill_totals_table', 1),
(24, '2017_10_11_000000_create_invoice_totals_table', 1),
(25, '2017_11_16_000000_create_failed_jobs_table', 1),
(26, '2017_12_09_000000_add_currency_columns', 1),
(27, '2017_12_30_000000_create_mediable_tables', 1),
(28, '2018_01_03_000000_drop_attachment_column_bill_payments_table', 1),
(29, '2018_01_03_000000_drop_attachment_column_bills_table', 1),
(30, '2018_01_03_000000_drop_attachment_column_invoice_payments_table', 1),
(31, '2018_01_03_000000_drop_attachment_column_invoices_table', 1),
(32, '2018_01_03_000000_drop_attachment_column_payments_table', 1),
(33, '2018_01_03_000000_drop_attachment_column_revenues_table', 1),
(34, '2018_01_03_000000_drop_picture_column_items_table', 1),
(35, '2018_01_03_000000_drop_picture_column_users_table', 1),
(36, '2018_04_23_000000_add_category_column_invoices_bills', 1),
(37, '2018_04_26_000000_create_recurring_table', 1),
(38, '2018_04_30_000000_add_parent_column', 1),
(39, '2018_06_23_000000_modify_email_column', 1),
(40, '2018_06_30_000000_modify_enabled_column', 1),
(41, '2018_07_07_000000_modify_date_column', 1),
(42, '2018_09_26_000000_add_reference_column_customers', 1),
(43, '2018_09_26_000000_add_reference_column_vendors', 1),
(44, '2018_10_22_000000_create_bill_item_taxes_table', 1),
(45, '2018_10_22_000000_create_invoice_item_taxes_table', 1),
(46, '2018_10_27_000000_add_reconciled_column', 1),
(47, '2018_10_27_000000_create_reconciliations_table', 1),
(48, '2018_11_05_000000_add_tax_columns', 1),
(49, '2019_01_07_000000_drop_tax_id_column', 1),
(50, '2019_02_04_000000_modify_deleted_at_column_media_table', 1),
(51, '2020_01_01_000000_add_locale_column', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_modules`
--

CREATE TABLE `nty_modules` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `alias` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_modules`
--

INSERT INTO `nty_modules` (`id`, `company_id`, `alias`, `status`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 'offlinepayment', 1, '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(2, 1, 'paypalstandard', 1, '2019-04-30 23:49:45', '2019-04-30 23:49:45', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_module_histories`
--

CREATE TABLE `nty_module_histories` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  `category` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `version` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_module_histories`
--

INSERT INTO `nty_module_histories` (`id`, `company_id`, `module_id`, `category`, `version`, `description`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 1, 'payment-gateway', '1.0.0', 'OfflinePayment instalado', '2019-04-30 23:49:44', '2019-04-30 23:49:44', NULL),
(2, 1, 2, 'payment-gateway', '1.0.0', 'PaypalStandard instalado', '2019-04-30 23:49:45', '2019-04-30 23:49:45', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_notifications`
--

CREATE TABLE `nty_notifications` (
  `id` char(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `notifiable_id` int(10) UNSIGNED NOT NULL,
  `notifiable_type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `data` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `read_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_password_resets`
--

CREATE TABLE `nty_password_resets` (
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_payments`
--

CREATE TABLE `nty_payments` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `paid_at` datetime NOT NULL,
  `amount` double(15,4) NOT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `currency_rate` double(15,8) NOT NULL,
  `vendor_id` int(11) DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `category_id` int(11) NOT NULL,
  `payment_method` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reference` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `reconciled` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_permissions`
--

CREATE TABLE `nty_permissions` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `display_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_permissions`
--

INSERT INTO `nty_permissions` (`id`, `name`, `display_name`, `description`, `created_at`, `updated_at`) VALUES
(1, 'read-admin-panel', 'Read Admin Panel', 'Read Admin Panel', '2019-04-30 23:48:40', '2019-04-30 23:48:40'),
(2, 'read-api', 'Read Api', 'Read Api', '2019-04-30 23:48:40', '2019-04-30 23:48:40'),
(3, 'create-auth-users', 'Create Auth Users', 'Create Auth Users', '2019-04-30 23:48:40', '2019-04-30 23:48:40'),
(4, 'read-auth-users', 'Read Auth Users', 'Read Auth Users', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(5, 'update-auth-users', 'Update Auth Users', 'Update Auth Users', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(6, 'delete-auth-users', 'Delete Auth Users', 'Delete Auth Users', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(7, 'create-auth-roles', 'Create Auth Roles', 'Create Auth Roles', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(8, 'read-auth-roles', 'Read Auth Roles', 'Read Auth Roles', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(9, 'update-auth-roles', 'Update Auth Roles', 'Update Auth Roles', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(10, 'delete-auth-roles', 'Delete Auth Roles', 'Delete Auth Roles', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(11, 'create-auth-permissions', 'Create Auth Permissions', 'Create Auth Permissions', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(12, 'read-auth-permissions', 'Read Auth Permissions', 'Read Auth Permissions', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(13, 'update-auth-permissions', 'Update Auth Permissions', 'Update Auth Permissions', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(14, 'delete-auth-permissions', 'Delete Auth Permissions', 'Delete Auth Permissions', '2019-04-30 23:48:41', '2019-04-30 23:48:41'),
(15, 'read-auth-profile', 'Read Auth Profile', 'Read Auth Profile', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(16, 'update-auth-profile', 'Update Auth Profile', 'Update Auth Profile', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(17, 'create-common-companies', 'Create Common Companies', 'Create Common Companies', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(18, 'read-common-companies', 'Read Common Companies', 'Read Common Companies', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(19, 'update-common-companies', 'Update Common Companies', 'Update Common Companies', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(20, 'delete-common-companies', 'Delete Common Companies', 'Delete Common Companies', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(21, 'create-common-import', 'Create Common Import', 'Create Common Import', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(22, 'create-common-items', 'Create Common Items', 'Create Common Items', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(23, 'read-common-items', 'Read Common Items', 'Read Common Items', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(24, 'update-common-items', 'Update Common Items', 'Update Common Items', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(25, 'delete-common-items', 'Delete Common Items', 'Delete Common Items', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(26, 'read-common-uploads', 'Read Common Uploads', 'Read Common Uploads', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(27, 'delete-common-uploads', 'Delete Common Uploads', 'Delete Common Uploads', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(28, 'create-common-notifications', 'Create Common Notifications', 'Create Common Notifications', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(29, 'read-common-notifications', 'Read Common Notifications', 'Read Common Notifications', '2019-04-30 23:48:42', '2019-04-30 23:48:42'),
(30, 'update-common-notifications', 'Update Common Notifications', 'Update Common Notifications', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(31, 'delete-common-notifications', 'Delete Common Notifications', 'Delete Common Notifications', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(32, 'create-incomes-invoices', 'Create Incomes Invoices', 'Create Incomes Invoices', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(33, 'read-incomes-invoices', 'Read Incomes Invoices', 'Read Incomes Invoices', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(34, 'update-incomes-invoices', 'Update Incomes Invoices', 'Update Incomes Invoices', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(35, 'delete-incomes-invoices', 'Delete Incomes Invoices', 'Delete Incomes Invoices', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(36, 'create-incomes-revenues', 'Create Incomes Revenues', 'Create Incomes Revenues', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(37, 'read-incomes-revenues', 'Read Incomes Revenues', 'Read Incomes Revenues', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(38, 'update-incomes-revenues', 'Update Incomes Revenues', 'Update Incomes Revenues', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(39, 'delete-incomes-revenues', 'Delete Incomes Revenues', 'Delete Incomes Revenues', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(40, 'create-incomes-customers', 'Create Incomes Customers', 'Create Incomes Customers', '2019-04-30 23:48:43', '2019-04-30 23:48:43'),
(41, 'read-incomes-customers', 'Read Incomes Customers', 'Read Incomes Customers', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(42, 'update-incomes-customers', 'Update Incomes Customers', 'Update Incomes Customers', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(43, 'delete-incomes-customers', 'Delete Incomes Customers', 'Delete Incomes Customers', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(44, 'create-expenses-bills', 'Create Expenses Bills', 'Create Expenses Bills', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(45, 'read-expenses-bills', 'Read Expenses Bills', 'Read Expenses Bills', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(46, 'update-expenses-bills', 'Update Expenses Bills', 'Update Expenses Bills', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(47, 'delete-expenses-bills', 'Delete Expenses Bills', 'Delete Expenses Bills', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(48, 'create-expenses-payments', 'Create Expenses Payments', 'Create Expenses Payments', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(49, 'read-expenses-payments', 'Read Expenses Payments', 'Read Expenses Payments', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(50, 'update-expenses-payments', 'Update Expenses Payments', 'Update Expenses Payments', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(51, 'delete-expenses-payments', 'Delete Expenses Payments', 'Delete Expenses Payments', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(52, 'create-expenses-vendors', 'Create Expenses Vendors', 'Create Expenses Vendors', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(53, 'read-expenses-vendors', 'Read Expenses Vendors', 'Read Expenses Vendors', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(54, 'update-expenses-vendors', 'Update Expenses Vendors', 'Update Expenses Vendors', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(55, 'delete-expenses-vendors', 'Delete Expenses Vendors', 'Delete Expenses Vendors', '2019-04-30 23:48:44', '2019-04-30 23:48:44'),
(56, 'create-banking-accounts', 'Create Banking Accounts', 'Create Banking Accounts', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(57, 'read-banking-accounts', 'Read Banking Accounts', 'Read Banking Accounts', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(58, 'update-banking-accounts', 'Update Banking Accounts', 'Update Banking Accounts', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(59, 'delete-banking-accounts', 'Delete Banking Accounts', 'Delete Banking Accounts', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(60, 'create-banking-transfers', 'Create Banking Transfers', 'Create Banking Transfers', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(61, 'read-banking-transfers', 'Read Banking Transfers', 'Read Banking Transfers', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(62, 'update-banking-transfers', 'Update Banking Transfers', 'Update Banking Transfers', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(63, 'delete-banking-transfers', 'Delete Banking Transfers', 'Delete Banking Transfers', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(64, 'read-banking-transactions', 'Read Banking Transactions', 'Read Banking Transactions', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(65, 'create-banking-reconciliations', 'Create Banking Reconciliations', 'Create Banking Reconciliations', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(66, 'read-banking-reconciliations', 'Read Banking Reconciliations', 'Read Banking Reconciliations', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(67, 'update-banking-reconciliations', 'Update Banking Reconciliations', 'Update Banking Reconciliations', '2019-04-30 23:48:45', '2019-04-30 23:48:45'),
(68, 'delete-banking-reconciliations', 'Delete Banking Reconciliations', 'Delete Banking Reconciliations', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(69, 'create-settings-categories', 'Create Settings Categories', 'Create Settings Categories', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(70, 'read-settings-categories', 'Read Settings Categories', 'Read Settings Categories', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(71, 'update-settings-categories', 'Update Settings Categories', 'Update Settings Categories', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(72, 'delete-settings-categories', 'Delete Settings Categories', 'Delete Settings Categories', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(73, 'read-settings-settings', 'Read Settings Settings', 'Read Settings Settings', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(74, 'update-settings-settings', 'Update Settings Settings', 'Update Settings Settings', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(75, 'create-settings-taxes', 'Create Settings Taxes', 'Create Settings Taxes', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(76, 'read-settings-taxes', 'Read Settings Taxes', 'Read Settings Taxes', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(77, 'update-settings-taxes', 'Update Settings Taxes', 'Update Settings Taxes', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(78, 'delete-settings-taxes', 'Delete Settings Taxes', 'Delete Settings Taxes', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(79, 'create-settings-currencies', 'Create Settings Currencies', 'Create Settings Currencies', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(80, 'read-settings-currencies', 'Read Settings Currencies', 'Read Settings Currencies', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(81, 'update-settings-currencies', 'Update Settings Currencies', 'Update Settings Currencies', '2019-04-30 23:48:46', '2019-04-30 23:48:46'),
(82, 'delete-settings-currencies', 'Delete Settings Currencies', 'Delete Settings Currencies', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(83, 'read-settings-modules', 'Read Settings Modules', 'Read Settings Modules', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(84, 'update-settings-modules', 'Update Settings Modules', 'Update Settings Modules', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(85, 'read-modules-home', 'Read Modules Home', 'Read Modules Home', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(86, 'read-modules-tiles', 'Read Modules Tiles', 'Read Modules Tiles', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(87, 'create-modules-item', 'Create Modules Item', 'Create Modules Item', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(88, 'read-modules-item', 'Read Modules Item', 'Read Modules Item', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(89, 'update-modules-item', 'Update Modules Item', 'Update Modules Item', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(90, 'delete-modules-item', 'Delete Modules Item', 'Delete Modules Item', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(91, 'create-modules-token', 'Create Modules Token', 'Create Modules Token', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(92, 'update-modules-token', 'Update Modules Token', 'Update Modules Token', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(93, 'read-modules-my', 'Read Modules My', 'Read Modules My', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(94, 'read-install-updates', 'Read Install Updates', 'Read Install Updates', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(95, 'update-install-updates', 'Update Install Updates', 'Update Install Updates', '2019-04-30 23:48:47', '2019-04-30 23:48:47'),
(96, 'read-notifications', 'Read Notifications', 'Read Notifications', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(97, 'update-notifications', 'Update Notifications', 'Update Notifications', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(98, 'read-reports-income-summary', 'Read Reports Income Summary', 'Read Reports Income Summary', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(99, 'read-reports-expense-summary', 'Read Reports Expense Summary', 'Read Reports Expense Summary', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(100, 'read-reports-income-expense-summary', 'Read Reports Income Expense Summary', 'Read Reports Income Expense Summary', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(101, 'read-reports-profit-loss', 'Read Reports Profit Loss', 'Read Reports Profit Loss', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(102, 'read-reports-tax-summary', 'Read Reports Tax Summary', 'Read Reports Tax Summary', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(103, 'create-wizard-companies', 'Create Wizard Companies', 'Create Wizard Companies', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(104, 'read-wizard-companies', 'Read Wizard Companies', 'Read Wizard Companies', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(105, 'update-wizard-companies', 'Update Wizard Companies', 'Update Wizard Companies', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(106, 'create-wizard-currencies', 'Create Wizard Currencies', 'Create Wizard Currencies', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(107, 'read-wizard-currencies', 'Read Wizard Currencies', 'Read Wizard Currencies', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(108, 'update-wizard-currencies', 'Update Wizard Currencies', 'Update Wizard Currencies', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(109, 'create-wizard-taxes', 'Create Wizard Taxes', 'Create Wizard Taxes', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(110, 'read-wizard-taxes', 'Read Wizard Taxes', 'Read Wizard Taxes', '2019-04-30 23:48:48', '2019-04-30 23:48:48'),
(111, 'update-wizard-taxes', 'Update Wizard Taxes', 'Update Wizard Taxes', '2019-04-30 23:48:49', '2019-04-30 23:48:49'),
(112, 'create-wizard-finish', 'Create Wizard Finish', 'Create Wizard Finish', '2019-04-30 23:48:49', '2019-04-30 23:48:49'),
(113, 'read-wizard-finish', 'Read Wizard Finish', 'Read Wizard Finish', '2019-04-30 23:48:49', '2019-04-30 23:48:49'),
(114, 'update-wizard-finish', 'Update Wizard Finish', 'Update Wizard Finish', '2019-04-30 23:48:49', '2019-04-30 23:48:49'),
(115, 'read-customer-panel', 'Read Customer Panel', 'Read Customer Panel', '2019-04-30 23:48:52', '2019-04-30 23:48:52'),
(116, 'read-customers-invoices', 'Read Customers Invoices', 'Read Customers Invoices', '2019-04-30 23:48:53', '2019-04-30 23:48:53'),
(117, 'update-customers-invoices', 'Update Customers Invoices', 'Update Customers Invoices', '2019-04-30 23:48:53', '2019-04-30 23:48:53'),
(118, 'read-customers-payments', 'Read Customers Payments', 'Read Customers Payments', '2019-04-30 23:48:53', '2019-04-30 23:48:53'),
(119, 'update-customers-payments', 'Update Customers Payments', 'Update Customers Payments', '2019-04-30 23:48:53', '2019-04-30 23:48:53'),
(120, 'read-customers-transactions', 'Read Customers Transactions', 'Read Customers Transactions', '2019-04-30 23:48:53', '2019-04-30 23:48:53'),
(121, 'read-customers-profile', 'Read Customers Profile', 'Read Customers Profile', '2019-04-30 23:48:53', '2019-04-30 23:48:53'),
(122, 'update-customers-profile', 'Update Customers Profile', 'Update Customers Profile', '2019-04-30 23:48:53', '2019-04-30 23:48:53'),
(123, 'read-reports-daily-summary', 'Read Reports Daily Summary', 'Read Reports Daily Summary', '2019-05-01 23:01:52', '2019-05-01 23:01:52'),
(124, 'read-reports-most-salled', 'Read Reports Most Salled', 'Read Reports Most Salled', '2019-05-01 23:03:41', '2019-05-01 23:03:41');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_reconciliations`
--

CREATE TABLE `nty_reconciliations` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `started_at` datetime NOT NULL,
  `ended_at` datetime NOT NULL,
  `closing_balance` double(15,4) NOT NULL DEFAULT '0.0000',
  `reconciled` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_recurring`
--

CREATE TABLE `nty_recurring` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `recurable_id` int(10) UNSIGNED NOT NULL,
  `recurable_type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `frequency` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `interval` int(11) NOT NULL DEFAULT '1',
  `started_at` datetime NOT NULL,
  `count` int(11) NOT NULL DEFAULT '0',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_recurring`
--

INSERT INTO `nty_recurring` (`id`, `company_id`, `recurable_id`, `recurable_type`, `frequency`, `interval`, `started_at`, `count`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, 2, 'App\\Models\\Income\\Invoice', 'daily', 1, '2019-04-30 23:55:24', 1, '2019-05-01 04:55:26', '2019-05-01 04:57:39', '2019-05-01 04:57:39');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_revenues`
--

CREATE TABLE `nty_revenues` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `paid_at` datetime NOT NULL,
  `amount` double(15,4) NOT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `currency_rate` double(15,8) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `category_id` int(11) NOT NULL,
  `payment_method` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `reference` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `reconciled` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_roles`
--

CREATE TABLE `nty_roles` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `display_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_roles`
--

INSERT INTO `nty_roles` (`id`, `name`, `display_name`, `description`, `created_at`, `updated_at`) VALUES
(1, 'admin', 'Admin', 'Admin', '2019-04-30 23:48:40', '2019-04-30 23:48:40'),
(2, 'manager', 'Manager', 'Manager', '2019-04-30 23:48:49', '2019-04-30 23:48:49'),
(3, 'customer', 'Customer', 'Customer', '2019-04-30 23:48:52', '2019-04-30 23:48:52');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_role_permissions`
--

CREATE TABLE `nty_role_permissions` (
  `role_id` int(10) UNSIGNED NOT NULL,
  `permission_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_role_permissions`
--

INSERT INTO `nty_role_permissions` (`role_id`, `permission_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 21),
(1, 22),
(1, 23),
(1, 24),
(1, 25),
(1, 26),
(1, 27),
(1, 28),
(1, 29),
(1, 30),
(1, 31),
(1, 32),
(1, 33),
(1, 34),
(1, 35),
(1, 36),
(1, 37),
(1, 38),
(1, 39),
(1, 40),
(1, 41),
(1, 42),
(1, 43),
(1, 44),
(1, 45),
(1, 46),
(1, 47),
(1, 48),
(1, 49),
(1, 50),
(1, 51),
(1, 52),
(1, 53),
(1, 54),
(1, 55),
(1, 56),
(1, 57),
(1, 58),
(1, 59),
(1, 60),
(1, 61),
(1, 62),
(1, 63),
(1, 64),
(1, 65),
(1, 66),
(1, 67),
(1, 68),
(1, 69),
(1, 70),
(1, 71),
(1, 72),
(1, 73),
(1, 74),
(1, 75),
(1, 76),
(1, 77),
(1, 78),
(1, 79),
(1, 80),
(1, 81),
(1, 82),
(1, 83),
(1, 84),
(1, 85),
(1, 86),
(1, 87),
(1, 88),
(1, 89),
(1, 90),
(1, 91),
(1, 92),
(1, 93),
(1, 94),
(1, 95),
(1, 96),
(1, 97),
(1, 98),
(1, 99),
(1, 100),
(1, 101),
(1, 102),
(1, 103),
(1, 104),
(1, 105),
(1, 106),
(1, 107),
(1, 108),
(1, 109),
(1, 110),
(1, 111),
(1, 112),
(1, 113),
(1, 114),
(1, 123),
(1, 124),
(2, 1),
(2, 15),
(2, 16),
(2, 17),
(2, 18),
(2, 19),
(2, 20),
(2, 21),
(2, 22),
(2, 23),
(2, 24),
(2, 25),
(2, 26),
(2, 28),
(2, 29),
(2, 30),
(2, 31),
(2, 32),
(2, 33),
(2, 34),
(2, 35),
(2, 36),
(2, 37),
(2, 38),
(2, 39),
(2, 40),
(2, 41),
(2, 42),
(2, 43),
(2, 44),
(2, 45),
(2, 46),
(2, 47),
(2, 48),
(2, 49),
(2, 50),
(2, 51),
(2, 52),
(2, 53),
(2, 54),
(2, 55),
(2, 56),
(2, 57),
(2, 58),
(2, 59),
(2, 60),
(2, 61),
(2, 62),
(2, 63),
(2, 64),
(2, 65),
(2, 66),
(2, 67),
(2, 68),
(2, 69),
(2, 70),
(2, 71),
(2, 72),
(2, 73),
(2, 74),
(2, 75),
(2, 76),
(2, 77),
(2, 78),
(2, 79),
(2, 80),
(2, 81),
(2, 82),
(2, 83),
(2, 84),
(2, 94),
(2, 95),
(2, 96),
(2, 97),
(2, 98),
(2, 99),
(2, 100),
(2, 101),
(2, 102),
(3, 115),
(3, 116),
(3, 117),
(3, 118),
(3, 119),
(3, 120),
(3, 121),
(3, 122);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_sessions`
--

CREATE TABLE `nty_sessions` (
  `id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(10) UNSIGNED DEFAULT NULL,
  `ip_address` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_agent` text COLLATE utf8mb4_unicode_ci,
  `payload` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_activity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_settings`
--

CREATE TABLE `nty_settings` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `key` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `value` text COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_settings`
--

INSERT INTO `nty_settings` (`id`, `company_id`, `key`, `value`) VALUES
(1, 1, 'general.default_account', '1'),
(2, 1, 'general.financial_start', '01-01'),
(3, 1, 'general.timezone', 'America/Guayaquil'),
(4, 1, 'general.date_format', 'd m Y'),
(5, 1, 'general.date_separator', 'dash'),
(6, 1, 'general.percent_position', 'after'),
(7, 1, 'general.invoice_number_prefix', 'INV-'),
(8, 1, 'general.invoice_number_digit', '5'),
(9, 1, 'general.invoice_number_next', '3'),
(10, 1, 'general.default_payment_method', 'offlinepayment.cash.1'),
(11, 1, 'general.email_protocol', 'mail'),
(12, 1, 'general.email_sendmail_path', '/usr/sbin/sendmail -bs'),
(13, 1, 'general.send_invoice_reminder', '0'),
(14, 1, 'general.schedule_invoice_days', '1,3,5,10'),
(15, 1, 'general.send_bill_reminder', '0'),
(16, 1, 'general.schedule_bill_days', '10,5,3,1'),
(17, 1, 'general.send_item_reminder', '0'),
(18, 1, 'general.schedule_item_stocks', '3,5,7'),
(19, 1, 'general.schedule_time', '09:00'),
(20, 1, 'general.admin_theme', 'skin-green-light'),
(21, 1, 'general.list_limit', '25'),
(22, 1, 'general.use_gravatar', '0'),
(23, 1, 'general.session_handler', 'file'),
(24, 1, 'general.session_lifetime', '30'),
(25, 1, 'general.file_size', '2'),
(26, 1, 'general.file_types', 'pdf,jpeg,jpg,png'),
(27, 1, 'general.wizard', '1'),
(28, 1, 'general.invoice_item', 'settings.invoice.item'),
(29, 1, 'general.invoice_price', 'settings.invoice.price'),
(30, 1, 'general.invoice_quantity', 'settings.invoice.quantity'),
(31, 1, 'general.company_name', 'Fantacias Garfield'),
(32, 1, 'general.company_email', 'omar.guanoluisa25@gmail.com'),
(33, 1, 'general.default_currency', 'USD'),
(34, 1, 'general.default_locale', 'es-MX'),
(35, 1, 'offlinepayment.methods', '[{\"code\":\"offlinepayment.cash.1\",\"name\":\"Cash\",\"order\":\"1\",\"description\":null},{\"code\":\"offlinepayment.bank_transfer.2\",\"name\":\"Bank Transfer\",\"order\":\"2\",\"description\":null}]'),
(39, 1, 'general.company_logo', '4'),
(48, 1, 'general.company_tax_number', '0503254849001'),
(49, 1, 'general.company_phone', '0979208483');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_taxes`
--

CREATE TABLE `nty_taxes` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rate` double(15,4) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'normal'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_taxes`
--

INSERT INTO `nty_taxes` (`id`, `company_id`, `name`, `rate`, `enabled`, `created_at`, `updated_at`, `deleted_at`, `type`) VALUES
(1, 1, 'IVA', 12.0000, 1, '2019-05-01 00:51:19', '2019-05-01 00:51:19', NULL, 'normal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_transfers`
--

CREATE TABLE `nty_transfers` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `payment_id` int(11) NOT NULL,
  `revenue_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_users`
--

CREATE TABLE `nty_users` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_logged_in_at` timestamp NULL DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `locale` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'es-MX'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_users`
--

INSERT INTO `nty_users` (`id`, `name`, `email`, `password`, `remember_token`, `last_logged_in_at`, `enabled`, `created_at`, `updated_at`, `deleted_at`, `locale`) VALUES
(1, 'Omar Guanoluisa', 'omar.guanoluisa25@gmail.com', '$2y$10$YiKjiDBlxtkEZDAwAf5kfeAAFH/gbQSV7Z2vqmCcR8Q1FpTob/IZO', 'LnMHiWXBmrALxGLDWmQ3K8oa5e23nuevdFpnLasrhlsCLFYH85X7UXuLyB9k', '2019-05-02 04:19:50', 1, '2019-04-30 23:49:45', '2019-05-02 04:19:50', NULL, 'es-MX');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_user_companies`
--

CREATE TABLE `nty_user_companies` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `company_id` int(10) UNSIGNED NOT NULL,
  `user_type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_user_companies`
--

INSERT INTO `nty_user_companies` (`user_id`, `company_id`, `user_type`) VALUES
(1, 1, 'users');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_user_permissions`
--

CREATE TABLE `nty_user_permissions` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `permission_id` int(10) UNSIGNED NOT NULL,
  `user_type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_user_roles`
--

CREATE TABLE `nty_user_roles` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `role_id` int(10) UNSIGNED NOT NULL,
  `user_type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `nty_user_roles`
--

INSERT INTO `nty_user_roles` (`user_id`, `role_id`, `user_type`) VALUES
(1, 1, 'users');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nty_vendors`
--

CREATE TABLE `nty_vendors` (
  `id` int(10) UNSIGNED NOT NULL,
  `company_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tax_number` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` text COLLATE utf8mb4_unicode_ci,
  `website` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `currency_code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `reference` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `nty_accounts`
--
ALTER TABLE `nty_accounts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `accounts_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_bills`
--
ALTER TABLE `nty_bills`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `bills_company_id_bill_number_deleted_at_unique` (`company_id`,`bill_number`,`deleted_at`),
  ADD KEY `bills_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_bill_histories`
--
ALTER TABLE `nty_bill_histories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_histories_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_bill_items`
--
ALTER TABLE `nty_bill_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_items_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_bill_item_taxes`
--
ALTER TABLE `nty_bill_item_taxes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_item_taxes_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_bill_payments`
--
ALTER TABLE `nty_bill_payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_payments_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_bill_statuses`
--
ALTER TABLE `nty_bill_statuses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_statuses_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_bill_totals`
--
ALTER TABLE `nty_bill_totals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_totals_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_categories`
--
ALTER TABLE `nty_categories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categories_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_companies`
--
ALTER TABLE `nty_companies`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `nty_currencies`
--
ALTER TABLE `nty_currencies`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `currencies_company_id_code_deleted_at_unique` (`company_id`,`code`,`deleted_at`),
  ADD KEY `currencies_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_customers`
--
ALTER TABLE `nty_customers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `customers_company_id_email_deleted_at_unique` (`company_id`,`email`,`deleted_at`),
  ADD KEY `customers_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_failed_jobs`
--
ALTER TABLE `nty_failed_jobs`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `nty_invoices`
--
ALTER TABLE `nty_invoices`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `invoices_company_id_invoice_number_deleted_at_unique` (`company_id`,`invoice_number`,`deleted_at`),
  ADD KEY `invoices_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_invoice_histories`
--
ALTER TABLE `nty_invoice_histories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_histories_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_invoice_items`
--
ALTER TABLE `nty_invoice_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_items_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_invoice_item_taxes`
--
ALTER TABLE `nty_invoice_item_taxes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_item_taxes_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_invoice_payments`
--
ALTER TABLE `nty_invoice_payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_payments_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_invoice_statuses`
--
ALTER TABLE `nty_invoice_statuses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_statuses_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_invoice_totals`
--
ALTER TABLE `nty_invoice_totals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `invoice_totals_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_items`
--
ALTER TABLE `nty_items`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `items_company_id_sku_deleted_at_unique` (`company_id`,`sku`,`deleted_at`),
  ADD KEY `items_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_jobs`
--
ALTER TABLE `nty_jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jobs_queue_reserved_at_index` (`queue`,`reserved_at`);

--
-- Indices de la tabla `nty_media`
--
ALTER TABLE `nty_media`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `media_disk_directory_filename_extension_deleted_at_unique` (`disk`,`directory`,`filename`,`extension`,`deleted_at`),
  ADD KEY `media_disk_directory_index` (`disk`,`directory`),
  ADD KEY `media_aggregate_type_index` (`aggregate_type`);

--
-- Indices de la tabla `nty_mediables`
--
ALTER TABLE `nty_mediables`
  ADD PRIMARY KEY (`media_id`,`mediable_type`,`mediable_id`,`tag`),
  ADD KEY `mediables_mediable_id_mediable_type_index` (`mediable_id`,`mediable_type`),
  ADD KEY `mediables_tag_index` (`tag`),
  ADD KEY `mediables_order_index` (`order`);

--
-- Indices de la tabla `nty_migrations`
--
ALTER TABLE `nty_migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `nty_modules`
--
ALTER TABLE `nty_modules`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `modules_company_id_alias_deleted_at_unique` (`company_id`,`alias`,`deleted_at`),
  ADD KEY `modules_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_module_histories`
--
ALTER TABLE `nty_module_histories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `module_histories_company_id_module_id_index` (`company_id`,`module_id`);

--
-- Indices de la tabla `nty_notifications`
--
ALTER TABLE `nty_notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `notifications_notifiable_id_notifiable_type_index` (`notifiable_id`,`notifiable_type`);

--
-- Indices de la tabla `nty_password_resets`
--
ALTER TABLE `nty_password_resets`
  ADD KEY `password_resets_email_index` (`email`);

--
-- Indices de la tabla `nty_payments`
--
ALTER TABLE `nty_payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `payments_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_permissions`
--
ALTER TABLE `nty_permissions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `permissions_name_unique` (`name`);

--
-- Indices de la tabla `nty_reconciliations`
--
ALTER TABLE `nty_reconciliations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reconciliations_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_recurring`
--
ALTER TABLE `nty_recurring`
  ADD PRIMARY KEY (`id`),
  ADD KEY `recurring_recurable_id_recurable_type_index` (`recurable_id`,`recurable_type`);

--
-- Indices de la tabla `nty_revenues`
--
ALTER TABLE `nty_revenues`
  ADD PRIMARY KEY (`id`),
  ADD KEY `revenues_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_roles`
--
ALTER TABLE `nty_roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `roles_name_unique` (`name`);

--
-- Indices de la tabla `nty_role_permissions`
--
ALTER TABLE `nty_role_permissions`
  ADD PRIMARY KEY (`role_id`,`permission_id`),
  ADD KEY `role_permissions_permission_id_foreign` (`permission_id`);

--
-- Indices de la tabla `nty_sessions`
--
ALTER TABLE `nty_sessions`
  ADD UNIQUE KEY `sessions_id_unique` (`id`);

--
-- Indices de la tabla `nty_settings`
--
ALTER TABLE `nty_settings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `settings_company_id_key_unique` (`company_id`,`key`),
  ADD KEY `settings_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_taxes`
--
ALTER TABLE `nty_taxes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `taxes_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_transfers`
--
ALTER TABLE `nty_transfers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `transfers_company_id_index` (`company_id`);

--
-- Indices de la tabla `nty_users`
--
ALTER TABLE `nty_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_deleted_at_unique` (`email`,`deleted_at`);

--
-- Indices de la tabla `nty_user_companies`
--
ALTER TABLE `nty_user_companies`
  ADD PRIMARY KEY (`user_id`,`company_id`,`user_type`);

--
-- Indices de la tabla `nty_user_permissions`
--
ALTER TABLE `nty_user_permissions`
  ADD PRIMARY KEY (`user_id`,`permission_id`,`user_type`),
  ADD KEY `user_permissions_permission_id_foreign` (`permission_id`);

--
-- Indices de la tabla `nty_user_roles`
--
ALTER TABLE `nty_user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`,`user_type`),
  ADD KEY `user_roles_role_id_foreign` (`role_id`);

--
-- Indices de la tabla `nty_vendors`
--
ALTER TABLE `nty_vendors`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `vendors_company_id_email_deleted_at_unique` (`company_id`,`email`,`deleted_at`),
  ADD KEY `vendors_company_id_index` (`company_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `nty_accounts`
--
ALTER TABLE `nty_accounts`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `nty_bills`
--
ALTER TABLE `nty_bills`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_bill_histories`
--
ALTER TABLE `nty_bill_histories`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_bill_items`
--
ALTER TABLE `nty_bill_items`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_bill_item_taxes`
--
ALTER TABLE `nty_bill_item_taxes`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_bill_payments`
--
ALTER TABLE `nty_bill_payments`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_bill_statuses`
--
ALTER TABLE `nty_bill_statuses`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `nty_bill_totals`
--
ALTER TABLE `nty_bill_totals`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_categories`
--
ALTER TABLE `nty_categories`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `nty_companies`
--
ALTER TABLE `nty_companies`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `nty_currencies`
--
ALTER TABLE `nty_currencies`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `nty_customers`
--
ALTER TABLE `nty_customers`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `nty_failed_jobs`
--
ALTER TABLE `nty_failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_invoices`
--
ALTER TABLE `nty_invoices`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `nty_invoice_histories`
--
ALTER TABLE `nty_invoice_histories`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `nty_invoice_items`
--
ALTER TABLE `nty_invoice_items`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `nty_invoice_item_taxes`
--
ALTER TABLE `nty_invoice_item_taxes`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `nty_invoice_payments`
--
ALTER TABLE `nty_invoice_payments`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `nty_invoice_statuses`
--
ALTER TABLE `nty_invoice_statuses`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `nty_invoice_totals`
--
ALTER TABLE `nty_invoice_totals`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `nty_items`
--
ALTER TABLE `nty_items`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `nty_jobs`
--
ALTER TABLE `nty_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_media`
--
ALTER TABLE `nty_media`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `nty_migrations`
--
ALTER TABLE `nty_migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT de la tabla `nty_modules`
--
ALTER TABLE `nty_modules`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `nty_module_histories`
--
ALTER TABLE `nty_module_histories`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `nty_payments`
--
ALTER TABLE `nty_payments`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_permissions`
--
ALTER TABLE `nty_permissions`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;

--
-- AUTO_INCREMENT de la tabla `nty_reconciliations`
--
ALTER TABLE `nty_reconciliations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_recurring`
--
ALTER TABLE `nty_recurring`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `nty_revenues`
--
ALTER TABLE `nty_revenues`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_roles`
--
ALTER TABLE `nty_roles`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `nty_settings`
--
ALTER TABLE `nty_settings`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT de la tabla `nty_taxes`
--
ALTER TABLE `nty_taxes`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `nty_transfers`
--
ALTER TABLE `nty_transfers`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `nty_users`
--
ALTER TABLE `nty_users`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `nty_vendors`
--
ALTER TABLE `nty_vendors`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `nty_mediables`
--
ALTER TABLE `nty_mediables`
  ADD CONSTRAINT `mediables_media_id_foreign` FOREIGN KEY (`media_id`) REFERENCES `nty_media` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `nty_role_permissions`
--
ALTER TABLE `nty_role_permissions`
  ADD CONSTRAINT `role_permissions_permission_id_foreign` FOREIGN KEY (`permission_id`) REFERENCES `nty_permissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `role_permissions_role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `nty_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `nty_user_permissions`
--
ALTER TABLE `nty_user_permissions`
  ADD CONSTRAINT `user_permissions_permission_id_foreign` FOREIGN KEY (`permission_id`) REFERENCES `nty_permissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `nty_user_roles`
--
ALTER TABLE `nty_user_roles`
  ADD CONSTRAINT `user_roles_role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `nty_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
