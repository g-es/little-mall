import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface Good {
  mallGoodId: number;
  mallGoodName: string;
  mallGoodPrice: number;
  mallGoodType: string;
  mallGoodArea: string;
  mallGoodStatus: string;
  mallOrderName: string;
}

const EmpGoodsList: React.FC = () => {
  const [dateNow, setDateNow] = useState<string>('');
  const [mallUserRealName, setMallUserRealName] = useState<string | null>(null);
  const [mallUserName, setMallUserName] = useState<string | null>(null);
  const [mallUserId, setMallUserId] = useState<string | null>(null);
  const [mallUserPassword, setMallUserPassword] = useState<string | null>(null);
  const source = 'http://localhost:8751/malluser-service/malluser-manage-api/v1';

  const [emps, setEmps] = useState<Good[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Get current date
        const date = new Date();
        setDateNow(`${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`);

        // Get user information from session storage
        const storedMallUserRealName = sessionStorage.getItem('mallUserRealName');
        const storedMallUserName = sessionStorage.getItem('mallUserName');
        const storedMallUserPassword = sessionStorage.getItem('mallUserPassword');
        const storedMallUserId = sessionStorage.getItem('mallUserId');

        setMallUserRealName(storedMallUserRealName);
        setMallUserName(storedMallUserName);
        setMallUserPassword(storedMallUserPassword);
        setMallUserId(storedMallUserId);

        // Send axios asynchronous request
        const response = await axios.post(
          'http://localhost:8751/malluser-service/malluser-manage-api/v1/getAllGoodsOfUserName',
          {
            mallUserName: storedMallUserName,
            mallUserRealName: storedMallUserRealName,
            mallUserPassword: storedMallUserPassword,
          }
        );

        setEmps(response.data.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, [emps.length]);

  const redirectToAllGoods = () => {
    navigate('/allGoods');
  };

  const addGoodOrderById = (mallGoodId: number) => {
    axios.post(
      `${source}/saveUserOrders`,
      {
        mallUserName: mallUserName,
        mallUserId: mallUserId,
        mallGoodsList: [mallGoodId],
      }
    ).then((resp) => {
      if (resp.data.code === '0') {
        alert('Shopping Success');
        setEmps((prevEmps) => [...prevEmps, resp.data.data]);

      } else {
        alert('Shopping Failed');
      }
    });
  };

  const returnUserMallDetail = () => {
    // Redirect to the main page or other relevant page
    // location.href = "empGoodsList.html";
  };

  return (
    <div id="wrap">
      <div id="top_content">
        <div id="header">
          <div id="rightheader">
            <p>{dateNow}<br /></p>
          </div>
          <div id="navigation"></div>
        </div>
        <div id="content">
          <p id="whereami"></p>
          <h1>Welcome! {mallUserRealName}</h1>
          <h3 style={{ float: 'right', cursor: 'pointer' }} onClick={redirectToAllGoods}>
            Show all Goods
          </h3>
          <table className="table">
            <tr className="table_header">
              <td>OrderID</td>
              <td>GoodID</td>
              <td>Name</td>
              <td>Price</td>
              <td>Type</td>
              <td>Area</td>
              <td>Status</td>
              <td>Operation</td>
            </tr>
            {emps.map((emp) => (
              <tr key={emp.mallGoodId} className={emps.indexOf(emp) % 2 === 0 ? 'row1' : 'row2'}>
                <td>{emp.mallOrderName}</td>
                <td>{emp.mallGoodId}</td>
                <td>{emp.mallGoodName}</td>
                <td>{emp.mallGoodPrice}</td>
                <td>{emp.mallGoodType}</td>
                <td>{emp.mallGoodArea}</td>
                <td>{emp.mallGoodStatus}</td>
                <td>
                  <a href="javascript:;" onClick={() => addGoodOrderById(emp.mallGoodId)}>buy again</a>
                </td>
              </tr>
            ))}
          </table>
        </div>
      </div>
      <div id="footer">
        <div id="footer_bg">geswander@gmail.com</div>
      </div>
    </div>
  );
};

export default EmpGoodsList;