// src/components/Navbar.js
import React from 'react';
import { Link } from 'react-router-dom';
import '../assets/style/Navbar.css';
import { FaHome, FaClipboardList, FaUsers, FaExclamationTriangle, FaChartBar, FaHeadset, FaSignOutAlt } from 'react-icons/fa';

function Navbar() {
  return (
    <nav>
      <ul>
        <li>
          <Link to="/">
            <FaHome /> <span>Trang chủ</span>
          </Link>
        </li>
        <li>
          <Link to="/post-management">
            <FaClipboardList /> <span>Quản lý bài đăng</span>
          </Link>
        </li>
        <li>
          <Link to="/user-management">
            <FaUsers /> <span>Quản lý người dùng</span>
          </Link>
        </li>
        <li>
          <Link to="/report-management">
            <FaExclamationTriangle /> <span>Quản lý báo cáo</span>
          </Link>
        </li>
        <li>
          <Link to="/statistics">
            <FaChartBar /> <span>Thống kê</span>
          </Link>
        </li>
        <li>
          <Link to="/customer-support-management">
            <FaHeadset /> <span>Hỗ trợ khách hàng</span>
          </Link>
        </li>
        <li>
          <Link to="/logout">
            <FaSignOutAlt /> <span>Đăng xuất</span>
          </Link>
        </li>
      </ul>
    </nav>
  );
}

export default Navbar;
