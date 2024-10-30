// src/App.js
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Header from './components/Header';
import Home from './pages/Home';
import About from './pages/About';
import './assets/style/App.css';
import UserManagement from './pages/UserManagement';

function App() {
  return (
    <Router>
      <div className="app-container">
        <Navbar />

        <Header /> {/* Đưa Header ra ngoài content */}

        <div className="content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/post-management" element={<About />} />
            <Route path="/user-management" element={<UserManagement />} />
            <Route path="/report-management" element={<About />} />
            <Route path="/statistics" element={<About />} />
            <Route path="/customer-support-management" element={<About />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
