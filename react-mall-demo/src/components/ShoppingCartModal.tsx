
import React from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import { Emp } from './pages/MallGoodsList';
import DeleteIcon from '@mui/icons-material/Delete';

interface ShoppingCartModalProps {
    isOpen: boolean;
    onRequestClose: () => void;
    cartItems: Emp[];
    setCartItems: React.Dispatch<React.SetStateAction<Emp[]>>;
}

const ShoppingCartModal: React.FC<ShoppingCartModalProps> = ({ isOpen, onRequestClose, cartItems, setCartItems }) => {
    const storedMallUserId = sessionStorage.getItem('mallUserId');
    const storedMallUserName = sessionStorage.getItem('mallUserName');
    const source = 'http://localhost:8751/malluser-service/malluser-manage-api/v1';

    const removeItemFromCart = (mallGoodId: string) => {
        setCartItems((prevItems) => prevItems.filter((item: Emp) => item.mallGoodId !== mallGoodId));
    };

    const purchaseItems = async () => {
        try {
            const response = await axios.post(`${source}/saveUserOrders`, {
                mallUserId: storedMallUserId,
                mallUserName: storedMallUserName,
                mallGoodsList: cartItems.map((item: Emp) => item.mallGoodId),
            });

            if (response.data.code === '0') {
                alert('Shopping Success');
                setCartItems([]); // Clear the cart after successful purchase
                onRequestClose(); // Close the modal
            } else {
                alert('Shopping failed');
            }
        } catch (error) {
            console.error('Error while adding order:', error);
            alert('Server error. Please try again later.');
        }
    };

    const deleteButtonStyle: React.CSSProperties = {
        background: 'none',
        border: 'none',
        cursor: 'pointer',
    };

    const renderCartItems = () => {
        return cartItems.map((item) => (
            <div key={item.mallGoodId} className="cart-item">
                ID: {item.mallGoodId}, Name: {item.mallGoodName} - 
                <button onClick={() => removeItemFromCart(item.mallGoodId)} style={deleteButtonStyle}>
                    <DeleteIcon />
                </button>
            </div>
        ));
    };

    return (
        <Modal
            isOpen={isOpen}
            onRequestClose={onRequestClose}
            contentLabel="Shopping Cart"
            className="cart-modal"
            overlayClassName="cart-overlay"
        >
            <div className="modal-header">
                <p>Shopping Cart</p>
            </div>
            <div className="modal-body">
                {renderCartItems()}
                {cartItems.length > 0 && (
                    <div className="modal-footer">
                        <button className="purchase-button" onClick={purchaseItems}>
                            Purchase
                        </button>
                    </div>
                )}
            </div>
        </Modal>
    );
};

export default ShoppingCartModal;