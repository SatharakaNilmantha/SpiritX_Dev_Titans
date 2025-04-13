import React, { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";

import "./SignUpPage.css";

function SignUpPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState({});

  const navigate = useNavigate();

  const validateUsername = (value) => {
    return value.length >= 8 ? "" : "Username must be at least 8 characters long.";
  };

  const validatePassword = (value) => {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}$/;
    return regex.test(value)
      ? ""
      : "Password must contain at least one lowercase, one uppercase, one special character, and be at least 8 characters long.";
  };

  const validateConfirmPassword = (value) => {
    return value === password ? "" : "Passwords do not match.";
  };

  const handleUsernameChange = (e) => {
    const value = e.target.value;
    setUsername(value);
    setErrors((prev) => ({ ...prev, username: validateUsername(value) }));
  };

  const handlePasswordChange = (e) => {
    const value = e.target.value;
    setPassword(value);
    setErrors((prev) => ({ ...prev, password: validatePassword(value) }));
  };

  const handleConfirmPasswordChange = (e) => {
    const value = e.target.value;
    setConfirmPassword(value);
    setErrors((prev) => ({ ...prev, confirmPassword: validateConfirmPassword(value) }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newErrors = {
      username: validateUsername(username),
      password: validatePassword(password),
      confirmPassword: validateConfirmPassword(confirmPassword),
    };
    setErrors(newErrors);

    if (!newErrors.username && !newErrors.password && !newErrors.confirmPassword) {
      try {
        const response = await axios.post("http://localhost:8080/api/v1/signup", {
          username,
          password,
        });

        // Handle successful signup
        if (response.status === 201) {
          // Store the JWT token in localStorage
          localStorage.setItem("jwtToken", response.data.token);

          toast.success(response.data.message, { position: "bottom-right" });

          // Redirect to login page after 2 seconds
          setTimeout(() => {
            navigate("/"); // Navigate to login page
          }, 2000);
        }
      } catch (error) {
        if (error.response) {
          toast.error(error.response.data.message, { position: "bottom-right" });
        } else {
          toast.error("Signup failed. Please try again.", { position: "bottom-right" });
        }
      }
    }
  };

  return (
    <div className="signup-container">
      <form className="signup-form" onSubmit={handleSubmit}>
        <h2 className="signup-title">Sign Up</h2>

        {/* Username Field */}
        <div>
          <label className="signup-label">Username</label>
          <input
            type="text"
            className={`signup-input ${errors.username ? "input-error" : username ? "input-success" : ""}`}
            value={username}
            onChange={handleUsernameChange}
          />
          {errors.username ? (
            <p className="signup-error">{errors.username}</p>
          ) : (
            username && <p className="signup-success">Valid username</p>
          )}
        </div>

        {/* Password Field */}
        <div>
          <label className="signup-label">Password</label>
          <input
            type="password"
            className={`signup-input ${errors.password ? "input-error" : password ? "input-success" : ""}`}
            value={password}
            onChange={handlePasswordChange}
          />
          {errors.password ? (
            <p className="signup-error">{errors.password}</p>
          ) : (
            password && <p className="signup-success">Strong password</p>
          )}
        </div>

        {/* Confirm Password Field */}
        <div>
          <label className="signup-label">Confirm Password</label>
          <input
            type="password"
            className={`signup-input ${errors.confirmPassword ? "input-error" : confirmPassword ? "input-success" : ""}`}
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
          />
          {errors.confirmPassword ? (
            <p className="signup-error">{errors.confirmPassword}</p>
          ) : (
            confirmPassword && <p className="signup-success">Passwords match</p>
          )}
        </div>

        <button type="submit" className="signup-button">Sign Up</button>

        {/* Link to Login Page */}
        <div className="signup-link">
          <p>Already have an account? <Link to="/">Back to Login</Link></p>
        </div>
      </form>
      <ToastContainer />
    </div>
  );
}

export default SignUpPage;
