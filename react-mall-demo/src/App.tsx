import React from "react";
import Navbar from "./components/Navbar";
import {
    BrowserRouter as Router,
    Navigate,
    Routes,
    Route,
} from "react-router-dom";
import Login from "./components/pages/Login";
import Register from "./components/pages/Register";
import EmpGoodsList from "./components/pages/EmpGoodsList";
import MallGoodsList from "./components/pages/MallGoodsList";
import { AuthProvider } from "./components/AuthContext";

const App: React.FC = () => {
    return (
        <Router>
            <AuthProvider>
                <Navbar />
                <Routes>
                    <Route path="/" element={<Navigate to="/login" />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/empGoodsList" element={<EmpGoodsList />} />
                    <Route path="/allGoods" element={<MallGoodsList />} />
                </Routes>
            </AuthProvider>
        </Router>
    );
}

export default App;