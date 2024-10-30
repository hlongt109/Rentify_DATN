// src/pages/Home.js
import React from 'react';
import '../assets/style/Home.css'; // Import file CSS cho Home

function Home() {
  return (
    <div className="home-container"> {/* Container bao ngoài */}
      <div className="home-page">
        <h1>Home Page</h1>
        <p>Welcome to the Home page!</p>
      </div>
    </div>
  );
}

export default Home;
