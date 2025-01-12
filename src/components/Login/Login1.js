import React, { useState } from 'react';
import axios from 'axios';
import './Login.css';

const Login1 = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/user/login', {
        email,
        password,
      });

      const userData = response.data;

      if (userData.roles === 'ADMIN') {
        window.location.href = '/admin';
      } else {
        window.location.href = '/user';
      }
    } catch (err) {
      setError('Invalid email or password.');
    }
  };

  const handleSignupRedirect = () => {
    window.location.href = '/signup';
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      {error && <p className="error">{error}</p>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
      </form>
      <div className="signup-redirect">
        <p>Don't have an account?</p>
        <button onClick={handleSignupRedirect}>Sign Up</button>
      </div>
    </div>
  );
};

export default Login1;
