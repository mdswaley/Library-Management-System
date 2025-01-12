import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login1 from './components/Login/Login1';
import AdminDash from './components/Dashboard/AdminDash';
import UserDash from './components/Dashboard/UserDash';
import AdminHome from './components/HomePage/AdminHome';
import AdminLayout from './components/HomePage/AdminLayout';

function App() {
  return (
    <Router>
      <Routes>
        {/* Login Route */}
        <Route path="/" element={<Login1 />} />

        {/* Admin Routes */}
        <Route path="/admin/*" element={<AdminLayout />}>
          <Route path="home" element={<AdminHome />} />
          <Route path="manage-users" element={<AdminDash />} />
        </Route>

        {/* User Dashboard */}
        <Route path="/user-dashboard" element={<UserDash />} />
      </Routes>
    </Router>
  );
}

export default App;
