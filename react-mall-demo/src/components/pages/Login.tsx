import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../css/styles.css';
import { useAuth } from "../AuthContext";

const Login: React.FC = () => {
    const [msg, setMsg] = useState<string>('');
    const [user, setUser] = useState<{ mallUserName?: string; mallUserPassword?: string }>({});
    const [verifyError, setVerifyError] = useState<string>('');
    const [verifyCode, setVerifyCode] = useState<string>('');
    const [mallUserPasswordError, setMallUserPasswordError] = useState<string>('');
    const [mallUserNameError, setMallUserNameError] = useState<string>('');

    const source = 'http://localhost:8751/malluser-service/malluser-manage-api/v1';
    const [verifyImgSrc, setVerifyImgSrc] = useState<string>('http://localhost:8751/malluser-service/malluser-manage-api/v1/verifyImg');
    const navigate = useNavigate();
    const { login } = useAuth();

    const getVerifyImg = () => {
        setVerifyImgSrc(`${source}/verifyImg?${new Date().getTime()}`);
    };

    const loginUser = () => {
        axios.post(`${source}/matchVerifyCode/?verifyCode=${verifyCode}`, null, { withCredentials: true }).then((result) => {
            if (result.data.code === '0') {
                const mallUserParam = {
                    mallUserName: user.mallUserName,
                    mallUserPassword: user.mallUserPassword,
                };

                axios.post(`${source}/userLogin`, mallUserParam, { withCredentials: true }).then((resp) => {
                    if (resp.data.code === '0') {
                        login(resp.data.data.mallUserName, resp.data.data.mallUserId, resp.data.data.mallUserRealName, resp.data.data.mallUserId);
                        // Redirect to order history
                        navigate("/empGoodsList", { replace: true });
                    } else {
                        setMsg(resp.data.msg);
                        setMallUserNameError(resp.data.code === 'mallUserName' ? resp.data.msg : '');
                        setMallUserPasswordError(resp.data.code === 'mallUserPassword' ? resp.data.msg : '');
                    }
                });
            } else {
                setVerifyError(result.data.msg);
            }
        });
    };


    return (
        <div id="wrap">
            <div id="top_content">
                <div id="content">
                    <p id="whereami"></p>
                    <h1>Login</h1>
                    <div className="form-container">
                        <div className="form-group">
                            <label className="input-label">Username</label>
                            <input type="text" className="inputgri" value={user.mallUserName} onChange={(e) => setUser({ ...user, mallUserName: e.target.value })} />
                            <span className="error">{mallUserNameError}</span>
                        </div>
                        <div className="form-group">
                            <label className="input-label">Password</label>
                            <input type="password" className="inputgri" value={user.mallUserPassword} onChange={(e) => setUser({ ...user, mallUserPassword: e.target.value })} />
                            <span className="error">{mallUserPasswordError}</span>
                        </div>
                        <p className="error">{msg}</p>
                        <div className="form-group">
                            <label className="input-label">Verification Code</label>
                            <input type="text" className="inputgri" value={verifyCode} onChange={(e) => setVerifyCode(e.target.value)} />
                            <span className="error">{verifyError}</span>
                        </div>
                        <div className="form-group">
                            <img id="verifyImg" src={verifyImgSrc} alt="Verification Code" />
                            <a href="javascript:;" onClick={getVerifyImg}>Get another one!</a>
                        </div>
                        <div className="form-group">
                            <button type="button" className="button" onClick={loginUser}>Login</button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="footer">
                <div id="footer_bg">geswander@gmail.com</div>
            </div>
        </div>
    );
};

export default Login;