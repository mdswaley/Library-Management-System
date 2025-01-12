import React from "react";
import { Outlet } from "react-router-dom";
import UserNav from "../Nav/UserNav";

const UserLayout = () => {
  return (
    <div>
      <UserNav />

      <main style={{ marginTop: "80px", padding: "20px" }}>
        <Outlet />
      </main>
    </div>
  );
};

export default UserLayout;
