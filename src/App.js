import React, { useState } from 'react';
import Dashboard from './Dashboard';
import Login from './Login'; // Assuming you have Login.js already

function App() {
  const [user, setUser] = useState(null);

  return (
    <div>
      {user ? (
        <Dashboard user={user} />
      ) : (
        <Login onLogin={setUser} />
      )}
    </div>
  );
}

export default App;