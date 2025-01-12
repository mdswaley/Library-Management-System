import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login1 from './components/Login/Login1';
import AdminDash from './components/Dashboard/AdminDash';
import AdminHome from './components/HomePage/AdminHome';
import AdminLayout from './components/HomePage/AdminLayout';
import UserLayout from './components/HomePage/UserLayout';
import UserHome from './components/HomePage/UserHome';
import ManageBooks from './components/Books/ManageBooks';
import IssueBooks from './components/Issue/IssueBooks';

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
          <Route path="manage-books" element={<ManageBooks />} />
        </Route>

        {/* User Dashboard */}
        <Route path="/user/*" element={<UserLayout />}>
          <Route path="home" element={<UserHome />} />
          <Route path="issue-return-books" element={<IssueBooks />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
