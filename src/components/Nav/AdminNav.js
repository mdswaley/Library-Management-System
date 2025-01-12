import React from "react";
import { Link } from "react-router-dom";
import "./Nav.css"; 

const AdminNav = () => {
  return (
    <header className="admin-navbar">
      <div className="logo">Library Management</div>
      <nav>
        <ul className="nav-links">
          <li><Link to="/admin/home">Home</Link></li>
          <li><Link to="/admin/manage-users">Manage Users</Link></li>
          <li><Link to="/">Logout</Link></li>
        </ul>
      </nav>
    </header>
  );
};

export default AdminNav;
