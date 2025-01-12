import React from "react";
import { Link } from "react-router-dom";
import "./Nav.css"; 

const UserNav = () => {
  return (
    <header className="admin-navbar">
      <div className="logo">Library Management</div>
      <nav>
        <ul className="nav-links">
          <li><Link to="/user/home">Home</Link></li>
          <li><Link to="/user/issue-return-books">Issue/Return Books</Link></li>
          <li><Link to="/">Logout</Link></li>
        </ul>
      </nav>
    </header>
  );
};

export default UserNav;
