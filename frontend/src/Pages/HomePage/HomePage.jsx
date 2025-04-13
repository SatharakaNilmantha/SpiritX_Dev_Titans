import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode"; // Ensure jwt-decode is installed
import { FaSignOutAlt } from "react-icons/fa";
import "./HomePage.css";

function HomePage() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("Guest");

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");

    if (!token) {
      navigate("/"); // Redirect to login if no token
    } else {
      try {
        // Decode the token to extract the username
        const decodedToken = jwtDecode(token);
        console.log("Decoded Token:", decodedToken); // Debugging: Check token structure

        // Use the correct key from your JWT payload (adjust as needed) and trim extra spaces
        setUsername(decodedToken.username?.trim() || decodedToken.sub?.trim() || "User");

      } catch (error) {
        console.error("Invalid Token:", error);
        localStorage.removeItem("jwtToken"); // Remove invalid token
        navigate("/"); // Redirect to login
      }
    }
  }, [navigate]);

  const handleLogout = () => {
    if (window.confirm("Are you sure you want to log out?")) {
      localStorage.removeItem("jwtToken"); // Remove JWT token
      navigate("/", { replace: true });

      // Prevent navigating back to the previous page
      window.history.pushState(null, "", "/");
      window.onpopstate = () => {
        window.history.go(1);
      };
    }
  };

  return (
    <div className="container">
      <button onClick={handleLogout} className="logout-button">
        <FaSignOutAlt className="logout-icon" /> Logout
      </button>

      <h1 className="title">Hello, {username}</h1>
    </div>
  );
}

export default HomePage;
