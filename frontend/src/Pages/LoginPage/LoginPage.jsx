import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./LoginPage.css";
import PopupMessage from "../../Components/PopupMessage/popupMessage";

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [popup, setPopup] = useState({ type: "", message: "" });
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    if (!username || !password) {
      setPopup({ type: "error", message: "Invalid Username or Password" });
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/api/v1/signin", {
        username,
        password,
      });

      if (response.status === 200) {
        const { token } = response.data;
        localStorage.setItem("jwtToken", token); // Store JWT token

        setPopup({ type: "success", message: "Login Successful!" });

        setTimeout(() => {
          navigate("/HomePage", { state: { username } });
        }, 1500);
      }
    } catch (error) {
      setPopup({ type: "error", message: "Invalid Username or Password" });
    }

    setTimeout(() => {
      setPopup({ type: "", message: "" });
    }, 2000);
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
        <h2 className="login-title">Login</h2>

        <div>
          <label className="login-label">Username</label>
          <input
            type="text"
            className="login-input"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>

        <div>
          <label className="login-label">Password</label>
          <input
            type="password"
            className="login-input"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button type="submit" className="login-button">Login</button>

        <p className="login-link">
          Don't have an account? <Link to="/SignUpPage">Sign up here</Link>
        </p>
      </form>

      <PopupMessage type={popup.type} message={popup.message} />
    </div>
  );
}

export default LoginPage;
