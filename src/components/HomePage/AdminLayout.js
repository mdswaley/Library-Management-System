import React from "react";
import { Outlet } from "react-router-dom";
import AdminNav from "../Nav/AdminNav";

const AdminLayout = () => {
  return (
    <div>
      {/* Fixed Admin Navigation Bar */}
      <AdminNav />

      {/* Dynamic Content Below the Navbar */}
      <main style={{ marginTop: "80px", padding: "20px" }}>
        <Outlet />
      </main>
    </div>
  );
};

export default AdminLayout;
