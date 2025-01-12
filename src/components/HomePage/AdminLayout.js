import React from "react";
import { Outlet } from "react-router-dom";
import AdminNav from "../Nav/AdminNav";

const AdminLayout = () => {
  return (
    <div>
      <AdminNav />

      <main style={{ marginTop: "80px", padding: "20px" }}>
        <Outlet />
      </main>
    </div>
  );
};

export default AdminLayout;
