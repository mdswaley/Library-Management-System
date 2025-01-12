import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login1 from './components/Login/Login1';
import AdminDash from './components/Dashboard/AdminDash';
import UserDash from './components/Dashboard/UserDash';

function App() {
  return (
    <Router>
      <Routes>
      <Route path="/" element={<Login1 />} />
        <Route path="/admin-dashboard" element={<AdminDash />} />
        <Route path="/user-dashboard" element={<UserDash />} />
      </Routes>
    </Router>
  );
}

export default App;
