-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 26, 2020 lúc 05:13 PM
-- Phiên bản máy phục vụ: 10.4.11-MariaDB
-- Phiên bản PHP: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `pizza`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietgiamgia`
--

CREATE TABLE `chitietgiamgia` (
  `ID_Giamgia` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_Product` varchar(10) CHARACTER SET utf8 NOT NULL,
  `NoiDungGiamGia` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `ID_Hoadon` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_Product` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_Khachhang` varchar(10) CHARACTER SET utf8 NOT NULL,
  `SoLuong` int(11) NOT NULL,
  `ThanhTien` double NOT NULL,
  `Ngaylap` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `chitiethoadon`
--

INSERT INTO `chitiethoadon` (`ID_Hoadon`, `ID_Product`, `ID_Khachhang`, `SoLuong`, `ThanhTien`, `Ngaylap`) VALUES
('HD1', 'PZS3', 'KH1', 3, 500000, '2020-01-01'),
('HD1', 'PZS4', 'KH1', 1, 99000, '2020-01-01');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietnhapkho`
--

CREATE TABLE `chitietnhapkho` (
  `ID_NhapKho` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_Product` varchar(10) CHARACTER SET utf8 NOT NULL,
  `SoLuong` int(11) NOT NULL,
  `GiaNhap` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chuongtrinhgiamgia`
--

CREATE TABLE `chuongtrinhgiamgia` (
  `ID_Giamgia` varchar(10) CHARACTER SET utf8 NOT NULL,
  `TenChuongTrinh` varchar(30) CHARACTER SET utf8 NOT NULL,
  `LoaiChuongTrinh` varchar(30) CHARACTER SET utf8 NOT NULL,
  `ThoiGiamBatDau` date NOT NULL,
  `ThoiGiamKetThuc` date NOT NULL,
  `NoiDungGiamGIa` varchar(100) CHARACTER SET utf8 NOT NULL,
  `DieuKienChiTieu` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dangnhap`
--

CREATE TABLE `dangnhap` (
  `username` varchar(30) CHARACTER SET utf8 NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 NOT NULL,
  `ID_Nhanvien` varchar(10) CHARACTER SET utf8 NOT NULL,
  `Type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `dangnhap`
--

INSERT INTO `dangnhap` (`username`, `password`, `ID_Nhanvien`, `Type`) VALUES
('admin', 'admin', 'ADMIN', 1),
('hailong', '123456', 'NV1', 0),
('huuvinh', '1', 'NV2', 0),
('nhatminh', '123456', 'NV3', 0),
('quoctuan', '123456', 'NV4', 0),
('1', '1', 'NV5', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `ID_Hoadon` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_Nhanvien` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_khachhang` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_Giamgia` varchar(10) CHARACTER SET utf8 NOT NULL,
  `NgayLap` varchar(200) CHARACTER SET utf8 NOT NULL,
  `ThanhTien` double NOT NULL,
  `TrangThai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`ID_Hoadon`, `ID_Nhanvien`, `ID_khachhang`, `ID_Giamgia`, `NgayLap`, `ThanhTien`, `TrangThai`) VALUES
('HD1', 'NV5', 'KL', '0%', '2020-01-01', 1103000, 1),
('HD10', 'NV5', 'KH3', '0%', '2020-05-23', 894000, 1),
('HD11', 'NV5', 'KL', '0%', '2020-05-24', 556000, 1),
('HD12', 'NV5', 'KH1', '0%', '2020-05-24', 957000, 0),
('HD13', 'NV5', 'KL', '0%', '2020-05-20', 338000, 1),
('HD14', 'NV5', 'KL', '0%', '2020-06-24', 338000, 1),
('HD2', 'NV3', 'KL', 'GG1', '2020-02-21', 1000000, 1),
('HD3', 'NV5', 'KL', '0%', '2020-03-01', 1490000, 0),
('HD4', 'NV5', 'KL', '0%', '2020-03-04', 828000, 0),
('HD5', 'NV5', 'KL', '0%', '2020-05-20', 596000, 0),
('HD6', 'NV5', 'KH1', '0%', '2020-05-21', 3074000, 0),
('HD7', 'NV5', 'KL', '0%', '2020-05-21', 903000, 0),
('HD8', 'NV5', 'KH3', '0%', '2020-05-21', 258000, 0),
('HD9', 'NV5', 'KL', '0%', '2020-05-22', 258000, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `ID_Khachhang` varchar(10) CHARACTER SET utf8 NOT NULL,
  `TenKhachHang` varchar(30) CHARACTER SET utf8 NOT NULL,
  `SDT` varchar(10) CHARACTER SET utf8 NOT NULL,
  `Gmail` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`ID_Khachhang`, `TenKhachHang`, `SDT`, `Gmail`) VALUES
('KH1', 'Huấn Rose', '1254789999', 'huanrosepro@gmail.com'),
('KH2', 'Tú le', '0125487964', 'tuloile@gmail.com'),
('KH3', 'Ngô Khá Bảnh Toản', '169856325', 'khabanh@gmail.com'),
('KL', 'Khách Lẻ', '0', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ncc`
--

CREATE TABLE `ncc` (
  `ID_NCC` varchar(10) CHARACTER SET utf8 NOT NULL,
  `Ten_NCC` varchar(30) NOT NULL,
  `DiaChi_NCC` varchar(50) NOT NULL,
  `SDT_NCC` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `ncc`
--

INSERT INTO `ncc` (`ID_NCC`, `Ten_NCC`, `DiaChi_NCC`, `SDT_NCC`) VALUES
('NCCCPN', 'Pizza Companny', '80 Lê Hồng Phong , Quận 5 , TP HCM', 154789658),
('NCCHUT', 'Pizza HUT', '30 Lê Hồng Phong , Quận 5 , TP HCM', 123654786);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `ID_NhanVien` varchar(10) CHARACTER SET utf8 NOT NULL,
  `TenNhanVien` varchar(30) CHARACTER SET utf8 NOT NULL,
  `NgaySinh` varchar(50) CHARACTER SET utf8 NOT NULL,
  `GioiTinh` varchar(3) CHARACTER SET utf8 NOT NULL,
  `DiaChi` varchar(50) CHARACTER SET utf8 NOT NULL,
  `SDT` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`ID_NhanVien`, `TenNhanVien`, `NgaySinh`, `GioiTinh`, `DiaChi`, `SDT`) VALUES
('NV1', 'Đặng Hải Long', '0000-00-00', 'Nam', '0 biết', '0252625959'),
('NV2', 'Nguyễn Hữu Vinh', '09/08/2000', 'Nam', '', '1132'),
('NV3', 'Phạm Nhật Minh', '02/10/2000', 'Nam', 'quan cam', '0912169275'),
('NV4', 'Nguyễn Quốc Tuấn', '10/10/2000', 'Nam', 'QUAN 2', '0124'),
('NV5', 'tester', '00/00/0000', 'Nam', '1', '0000000000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhapkho`
--

CREATE TABLE `nhapkho` (
  `ID_NhapKho` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_NhanVien` varchar(10) CHARACTER SET utf8 NOT NULL,
  `ID_NCC` varchar(10) CHARACTER SET utf8 NOT NULL,
  `NgayNhap` date NOT NULL,
  `TinhTrang` varchar(10) CHARACTER SET utf8 NOT NULL,
  `TongGiaTri` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `ID_Product` varchar(5) CHARACTER SET utf8 NOT NULL,
  `Name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `Price` double NOT NULL,
  `amount` int(11) NOT NULL,
  `Category` varchar(30) CHARACTER SET utf8 NOT NULL,
  `img_path` varchar(50) CHARACTER SET utf8 NOT NULL,
  `TrangThai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`ID_Product`, `Name`, `Price`, `amount`, `Category`, `img_path`, `TrangThai`) VALUES
('PZNN1', 'Pizza Thịt Nguội Kiểu Canada', 190000, 0, 'Thập Cẩm ', '/Image/PizzaSP_5.PNG', 1),
('PZNN2', 'Pizza Gà Nướng 3 Vị', 190000, 0, 'Thập Cẩm ', '/Image/PizzaSP_6.PNG', 1),
('PZS1', 'Pizza Hải Sản Pesto Xanh', 169000, 0, 'Hải Sản', '/Image/PizzaSP_1.PNG', 1),
('PZS2', 'Pizza Hải Sản Nhiệt Đới', 129000, 0, 'Hải Sản', '/Image/PizzaSP_2.PNG', 1),
('PZS3', 'Pizza Hải Sản Cocktail', 129000, 0, 'Hải Sản', '/Image/PizzaSP_3.PNG', 1),
('PZS4', 'Pizza Hải Sản Cao Cấp', 129000, 0, 'Hải Sản', '/Image/PizzaSP_4.PNG', 1),
('PZTT1', 'Pizza Phô Mai', 99000, 0, 'Truyền Thống', '/Image//PizzaSP_7.PNG', 1),
('PZTT2', 'Pizza Xúc Xích', 99000, 0, 'Truyền Thống', '/Image//PizzaSP_8.PNG', 1),
('PZTT3', 'Pizza Lạc Xưởng', 99000, 0, 'Truyền Thống', '/Image//PizzaSP_8.PNG', 0),
('PZTT4', 'Pizza Trứng', 99000, 0, 'Truyền Thống', '/Image/0', 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietgiamgia`
--
ALTER TABLE `chitietgiamgia`
  ADD PRIMARY KEY (`ID_Giamgia`),
  ADD KEY `ID_Product` (`ID_Product`);

--
-- Chỉ mục cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`ID_Product`),
  ADD KEY `ID_Hoadon` (`ID_Hoadon`),
  ADD KEY `ID_Product` (`ID_Product`);

--
-- Chỉ mục cho bảng `chitietnhapkho`
--
ALTER TABLE `chitietnhapkho`
  ADD PRIMARY KEY (`ID_NhapKho`),
  ADD UNIQUE KEY `ID_NhapKho_2` (`ID_NhapKho`),
  ADD KEY `ID_NhapKho` (`ID_NhapKho`),
  ADD KEY `ID_Product` (`ID_Product`);

--
-- Chỉ mục cho bảng `chuongtrinhgiamgia`
--
ALTER TABLE `chuongtrinhgiamgia`
  ADD PRIMARY KEY (`ID_Giamgia`);

--
-- Chỉ mục cho bảng `dangnhap`
--
ALTER TABLE `dangnhap`
  ADD PRIMARY KEY (`ID_Nhanvien`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`ID_Hoadon`),
  ADD KEY `ID_Nhanvien` (`ID_Nhanvien`),
  ADD KEY `ID_khachhang` (`ID_khachhang`),
  ADD KEY `ID_Giamgia` (`ID_Giamgia`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`ID_Khachhang`);

--
-- Chỉ mục cho bảng `ncc`
--
ALTER TABLE `ncc`
  ADD PRIMARY KEY (`ID_NCC`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`ID_NhanVien`);

--
-- Chỉ mục cho bảng `nhapkho`
--
ALTER TABLE `nhapkho`
  ADD PRIMARY KEY (`ID_NhapKho`),
  ADD KEY `ID_NhanVien` (`ID_NhanVien`),
  ADD KEY `ID_NCC` (`ID_NCC`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ID_Product`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`ID_Product`) REFERENCES `product` (`ID_Product`),
  ADD CONSTRAINT `hoadon` FOREIGN KEY (`ID_Hoadon`) REFERENCES `hoadon` (`ID_Hoadon`);

--
-- Các ràng buộc cho bảng `chitietnhapkho`
--
ALTER TABLE `chitietnhapkho`
  ADD CONSTRAINT `chitietnhapkho_ibfk_1` FOREIGN KEY (`ID_Product`) REFERENCES `product` (`ID_Product`);

--
-- Các ràng buộc cho bảng `chuongtrinhgiamgia`
--
ALTER TABLE `chuongtrinhgiamgia`
  ADD CONSTRAINT `hd-gg` FOREIGN KEY (`ID_Giamgia`) REFERENCES `hoadon` (`ID_Giamgia`);

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`ID_Nhanvien`) REFERENCES `nhanvien` (`ID_NhanVien`),
  ADD CONSTRAINT `kh-hd` FOREIGN KEY (`ID_khachhang`) REFERENCES `khachhang` (`ID_Khachhang`);

--
-- Các ràng buộc cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD CONSTRAINT `nhanvien_ibfk_1` FOREIGN KEY (`ID_NhanVien`) REFERENCES `dangnhap` (`ID_Nhanvien`);

--
-- Các ràng buộc cho bảng `nhapkho`
--
ALTER TABLE `nhapkho`
  ADD CONSTRAINT `nhapkho_ibfk_1` FOREIGN KEY (`ID_NhapKho`) REFERENCES `chitietnhapkho` (`ID_NhapKho`),
  ADD CONSTRAINT `nhapkho_ibfk_2` FOREIGN KEY (`ID_NCC`) REFERENCES `ncc` (`ID_NCC`),
  ADD CONSTRAINT `nhapkho_ibfk_3` FOREIGN KEY (`ID_NhanVien`) REFERENCES `nhanvien` (`ID_NhanVien`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
