import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Dashboard({ user }) {
  const [records, setRecords] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/retries') // adjust endpoint as needed
      .then(response => {
        setRecords(response.data);
      })
      .catch(error => {
        console.error('Error fetching records:', error);
      });
  }, []);

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Welcome, {user}</h2>
      <h3>Retry/Replay Records</h3>
      <table border="1" cellPadding="10" cellSpacing="0">
        <thead>
          <tr>
            <th>ID</th>
            <th>Transaction ID</th>
            <th>Status</th>
            <th>Retry Count</th>
            <th>Strategy</th>
            <th>Last Updated</th>
          </tr>
        </thead>
        <tbody>
          {records.map(record => (
            <tr key={record.id}>
              <td>{record.id}</td>
              <td>{record.transactionId}</td>
              <td>{record.status}</td>
              <td>{record.retryCount}</td>
              <td>{record.strategy}</td>
              <td>{record.lastModifiedDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Dashboard;