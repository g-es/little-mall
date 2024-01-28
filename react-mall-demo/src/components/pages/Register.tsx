import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../css/styles.css';
import { useAuth } from "../AuthContext";

const RegisterPage: React.FC = () => {
    const [user, setUser] = useState({
        mallUserName: '',
        mallUserRealName: '',
        mallUserPassword: '',
        mallUserGender: 'boy', // Default value for gender
    });

    const [verifyCode, setVerifyCode] = useState('');
    const [regError, setRegError] = useState('');
    const [userNameIsExist, setUserNameIsExist] = useState('');
    const [verifyError, setVerifyError] = useState('');
    const navigate = useNavigate();
    const source = 'http://localhost:8751/malluser-service/malluser-manage-api/v1';
    const { login } = useAuth();

    const [verifyImgSrc, setVerifyImgSrc] = useState<string>('http://localhost:8751/malluser-service/malluser-manage-api/v1/verifyImg');
    const getVerifyImg = () => {
        setVerifyImgSrc(`${source}/verifyImg?${new Date().getTime()}`);
    };

    const send = () => {
        axios.post(`${source}/matchVerifyCode/?verifyCode=${verifyCode}`, null, { withCredentials: true }).then((result) => {
            console.log("what error?", result.data);
            if (result.data.code === '0') {
                const mallUserParam = {
                    mallUserName: user.mallUserName,
                    mallUserPassword: user.mallUserPassword,
                    mallUserRealName: user.mallUserRealName,
                    mallUserGender: user.mallUserGender,
                };

                axios.post(`${source}/userRegister`, mallUserParam, { withCredentials: true }).then((resp) => {
                    if (resp.data.code === '0') {
                        login(resp.data.data.mallUserName, resp.data.data.mallUserId, resp.data.data.mallUserRealName, resp.data.data.mallUserId);
                        // Redirect to /empGoodsList
                        navigate("/empGoodsList", { replace: true });
                    } else {
                        console.log("Registration failed:", resp.data.msg);
                        setRegError(resp.data.msg);
                    }
                });
            } else {
                console.log("Verification code error:", result.data.msg);
                setVerifyError(result.data.msg);
            }
        });
    };

    return (
        <div id="wrap">
            <div id="top_content">
                <div id="content">
                    <h1>Register</h1>
                    <h5><span className="error">{regError}</span></h5>
                    <form className="form-container">
                        <div className="form-group">
                            <label className="input-label">UserName:</label>
                            <input
                                type="text"
                                className="inputgri"
                                value={user.mallUserName}
                                onChange={(e) => setUser({ ...user, mallUserName: e.target.value })}
                            />
                            <span className="error">{userNameIsExist}</span>
                        </div>

                        <div className="form-group">
                            <label className="input-label">RealName:</label>
                            <input
                                type="text"
                                className="inputgri"
                                value={user.mallUserRealName}
                                onChange={(e) => setUser({ ...user, mallUserRealName: e.target.value })}
                            />
                        </div>

                        <div className="form-group">
                            <label className="input-label">Password:</label>
                            <input
                                type="password"
                                className="inputgri"
                                value={user.mallUserPassword}
                                onChange={(e) => setUser({ ...user, mallUserPassword: e.target.value })}
                            />
                        </div>

                        <div className="form-group">
                            <label className="input-label">Gender:</label>
                            <input
                                type="radio"
                                className="inputgri"
                                value="boy"
                                checked={user.mallUserGender === 'boy'}
                                onChange={() => setUser({ ...user, mallUserGender: 'boy' })}
                            />
                            <span>Male</span>
                            <input
                                type="radio"
                                className="inputgri"
                                value="girl"
                                checked={user.mallUserGender === 'girl'}
                                onChange={() => setUser({ ...user, mallUserGender: 'girl' })}
                            />
                            <span>Female</span>
                        </div>

                        <div className="form-group">
                            <label className="input-label">Verification Code:</label>
                            <input
                                type="text"
                                className="inputgri"
                                value={verifyCode}
                                onChange={(e) => setVerifyCode(e.target.value)}
                            />
                            <span className="error">{verifyError}</span>
                        </div>

                        <div className="form-group">
                            <img id="verifyImg" src={verifyImgSrc} alt="Verification Code" />
                            <a href="javascript:;" onClick={getVerifyImg}>Get another one!</a>
                        </div>

                        <div className="form-group">
                            <input type="button" className="button" value="Submit &raquo;" onClick={send} />
                        </div>
                    </form>
                </div>
            </div>
            <div id="footer">
                <div id="footer_bg">geswander@gmail.com</div>
            </div>
        </div>
    );
};

export default RegisterPage;