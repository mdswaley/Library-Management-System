import React, { useState, useEffect } from "react";
import axios from "axios";
import  './Admin.css';

const AdminDash = () => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    axios
      .get("http://localhost:8080/user")
      .then((response) => setUsers(response.data))
      .catch((err) => setError("Failed to fetch users."));
  }, []);

  const updateRole = (userId, newRole) => {
    axios
      .put(`http://localhost:8080/user/${userId}/role?newRole=${newRole}`)
      .then(() => {
        setUsers((prevUsers) =>
          prevUsers.map((user) =>
            user.id === userId ? { ...user, roles: newRole } : user
          )
        );
      })
      .catch(() => setError("Failed to update user role."));
  };

  return (
    <div>
      <h1>Admin Dashboard</h1>
      {error && <p>{error}</p>}
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>{user.roles}</td>
              <td>
                {user.roles === "USER" && (
                  <button onClick={() => updateRole(user.id, "ADMIN")}>
                    Promote to Admin
                  </button>
                )}
                {user.roles === "ADMIN" && (
                  <button onClick={() => updateRole(user.id, "USER")}>
                    Demote to User
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminDash;
