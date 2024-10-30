// src/index.js
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { createRoot } from 'react-dom/client'; // Import createRoot
import App from './App';
const rootElement = document.getElementById('root'); // Lấy phần tử DOM
const root = createRoot(rootElement); // Tạo root

root.render(<App />); // Render ứng dụng
