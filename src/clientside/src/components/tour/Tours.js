import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { getAllTourPackages } from '../../api/tours';
import TourCard from './TourCard';
import '../../css/tour.css';

const Tours = () => {
    const [tours, setTours] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const location = useLocation();
    const navigate = useNavigate(); 

    // Function to handle navigation to the tour details page
    const handleViewDetails = (packageId) => {
        navigate(`/tours/${packageId}`);
    };

    useEffect(() => {
        const fetchTours = async () => {
            setLoading(true);
            setError(null);

            const searchResults = location.state?.searchResults;

            if (searchResults) {
                const validTours = searchResults.filter(tour => tour.packageId);
                setTours(validTours);
                setLoading(false);
            } else {
                try {
                    const allTours = await getAllTourPackages();
                    console.log("Raw tours data from API:", allTours);
                    const validTours = allTours.filter(tour => tour.packageId);
                    setTours(validTours);
                } catch (err) {
                    setError("Failed to fetch tour packages.");
                } finally {
                    setLoading(false);
                }
            }
        };

        fetchTours();
    }, [location.state]);

    if (loading) {
        return <div className="loading">Loading tours...</div>;
    }

    if (error) {
        return <div className="error">{error}</div>;
    }

    if (tours.length === 0) {
        return <div className="no-tours">No tours found. Please try a different search.</div>;
    }

    return (
        <div className="tours-page">
            <h2>Available Tours</h2>
            <div className="tour-list-container">
                {tours.map(tour => (
                    <TourCard 
                        key={tour.packageId} 
                        tour={tour} 
                        onSelect={() => handleViewDetails(tour.packageId)} 
                    />
                ))}
            </div>
        </div>
    );
};

export default Tours;