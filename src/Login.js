import React, { useState } from 'react';
import axios from 'axios';

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/login', {
        username,
        password
      });
      setMessage(response.data);
      onLogin(username); // Switch to dashboard on success
    } catch (error) {
      setMessage('Login failed: ' + (error.response?.data || 'Server error'));
    }
  };

  const styles = {
    container: {
      width: '300px',
      margin: 'auto',
      padding: '20px',
      border: '1px solid gray',
      borderRadius: '8px',
      marginTop: '100px',
      fontFamily: 'Arial'
    },
    input: {
      width: '100%',
      padding: '10px',
      marginBottom: '10px',
      borderRadius: '4px',
      border: '1px solid #ccc'
    },
    button: {
      width: '100%',
      padding: '10px',
      backgroundColor: '#007bff',
      color: 'white',
      border: 'none',
      borderRadius: '4px',
      cursor: 'pointer'
    },
    message: {
      color: 'red',
      marginTop: '10px',
      textAlign: 'center'
    }
  };

  return (
    <div style={styles.container}>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          style={styles.input}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={styles.input}
        />
        <button type="submit" style={styles.button}>Login</button>
      </form>
      {message && <div style={styles.message}>{message}</div>}
    </div>
  );
};

export default Login; 