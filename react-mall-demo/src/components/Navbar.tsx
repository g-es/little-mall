import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import "./css/navbar.css";
import { useAuth } from "../components/AuthContext";


const Navbar: React.FC = () => {
    const { isLoggedIn, logout } = useAuth();

    return (
        <nav className="navbar">
            <ul className="nav-list">

                {isLoggedIn ? (
                    <li className="nav-item" onClick={logout}>
                        <Link to="/login" className="nav-link">Logout</Link>
                    </li>) : (<li className="nav-item">
                        <Link to="/login" className="nav-link">Login</Link>
                    </li>)}

                <li className="nav-item">
                    <Link to="/register" className="nav-link">Register</Link>
                </li>
            </ul>
        </nav >
    );
};

export default Navbar;