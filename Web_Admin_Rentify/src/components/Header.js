// src/components/Header.js
import React from 'react';
import { FaBell, FaEnvelope, FaUserCircle, FaSearch } from 'react-icons/fa'; // Import biểu tượng cho thông báo, nhắn tin và avatar 
import '../assets/style/Header.css';

const Header = () => {
  return (
    <header className="header">
      <div className="search-container">
        <input type="text" placeholder="Tìm kiếm..." />
        <FaSearch className="search-icon" />
      </div>
      <div className="right">
        <FaBell className="icon" />
        <FaEnvelope className="icon" />
        <FaUserCircle className="avatar" />
      </div>
    </header>
  );
};

export default Header;
