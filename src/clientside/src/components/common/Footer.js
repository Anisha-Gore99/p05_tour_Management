import React from 'react';
import '../../css/footer.css';

const Footer = () => {
    return (
        <footer className="main-footer">
            <div className="footer-content">
                <div className="footer-section about">
                    <h3>About TourNest</h3>
                    <p>Your journey begins here. We are dedicated to providing the best travel experiences for every explorer.</p>
                </div>
                <div className="footer-section contact">
                    <h3>Contact Us</h3>
                    <p>Email: contact@tournest.com</p>
                    <p>Phone: +123 456 7890</p>
                    <p>Address: 123 Travel Lane, Adventure City, Global</p>
                </div>
                <div className="footer-section social">
                    <h3>Follow Us</h3>
                    <a href="https://facebook.com" target="_blank" rel="noopener noreferrer">Facebook</a>
                    <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">Twitter</a>
                    <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">Instagram</a>
                </div>
            </div>
            <div className="footer-bottom">
                <p>&copy; {new Date().getFullYear()} TourNest. All rights reserved.</p>
            </div>
        </footer>
    );
};

export default Footer;