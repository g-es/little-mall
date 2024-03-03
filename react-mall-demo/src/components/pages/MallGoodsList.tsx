import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Modal from 'react-modal';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import ShoppingCartModal from '../ShoppingCartModal';


export interface Emp {
    mallGoodId: string;
    mallGoodName: string;
    mallGoodPrice: string;
    mallGoodType: string;
    mallGoodArea: string;
    mallGoodStatus: string;
}

const MallGoodsList: React.FC = () => {
    const [showCart, setShowCart] = useState(false);
    const [cartItems, setCartItems] = useState<Emp[]>([]);
    const [dateNow, setDateNow] = useState<string>('');
    const [emps, setEmps] = useState<Emp[]>([]);
    const source = 'http://localhost:8751/malluser-service/malluser-manage-api/v1';
    const storedMallUserName = sessionStorage.getItem('mallUserName');
    const storedMallUserId = sessionStorage.getItem('mallUserId');
    const navigate = useNavigate();

    const addToCart = (item: Emp) => {
        setCartItems((prevItems) => [...prevItems, item]);
    };
    const removeItemFromCart = (mallGoodId: string) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.mallGoodId !== mallGoodId));
    };

    const toggleShowCart = () => {
        setShowCart(!showCart);
    };



    useEffect(() => {
        // Fetch data on component mount
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await axios.get(`${source}/getAllGoods`, { withCredentials: true });
            setEmps(response.data.data);
        } catch (error) {
            console.error("Error fetching data:", error);
            // todo Handle error 
        }
    };



    const returnUserMallDetail = () => {
        navigate('/empGoodsList');
    };

    const renderTableRows = () => {
        return emps.map((emp, index) => (
            <tr key={emp.mallGoodId} className={index % 2 === 0 ? 'row1' : 'row2'}>
                <td>{emp.mallGoodId}</td>
                <td>{emp.mallGoodName}</td>
                <td>{emp.mallGoodPrice}</td>
                <td>{emp.mallGoodType}</td>
                <td>{emp.mallGoodArea}</td>
                <td>{emp.mallGoodStatus}</td>
                <td>
                    <a href="javascript:;" onClick={() => addToCart(emp)}>add to cart</a>

                </td>
            </tr>
        ));
    };

    return (
        <div id="wrap">
            <div id="top_content">
                <div id="header">
                    {/* <div id="rightheader">
                        <p>{dateNow}<br /></p>
                    </div>
                    <div id="topheader">
                        <h1 id="title"><a href="#">SpringBoot-MallDemo-MallUserManage</a></h1>
                    </div>
                    <div id="navigation"></div> */}
                </div>
                <div id="content">
                    <p id="whereami"></p>
                    <h1>Welcome! {storedMallUserName}</h1>
                    <h3 style={{ float: 'right', cursor: 'pointer' }} onClick={() => returnUserMallDetail()} >
                        Back to order history
                    </h3>
                    <div>
                        <button className="show-cart-button" onClick={toggleShowCart}>
                            <ShoppingCartIcon />
                        </button>
                        <ShoppingCartModal isOpen={showCart} onRequestClose={toggleShowCart} cartItems={cartItems} setCartItems={setCartItems} />
                    </div>
                    <table className="table">
                        <thead>
                            <tr className="table_header">
                                <td>ID</td>
                                <td>Name</td>
                                <td>Price</td>
                                <td>Type</td>
                                <td>Area</td>
                                <td>Status</td>
                                <td>Operation</td>
                            </tr>
                        </thead>
                        <tbody>
                            {renderTableRows()}
                        </tbody>
                    </table>


                </div>
            </div>
            <div id="footer">
                <div id="footer_bg">geswander@gmail.com</div>
            </div>
        </div>
    );
};

export default MallGoodsList;