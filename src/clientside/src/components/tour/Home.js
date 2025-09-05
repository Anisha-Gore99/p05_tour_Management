import React, { useEffect, useState } from 'react';
import { getAllTourPackages } from '../../api/tours';
import { getAllCategories } from '../../api/categories';
import '../../css/home.css';
import SimpleTourCard from './SimpleTourCard';
import heroImage from '../../assets/TravelHeroImg.jpg';

const Home = () => {
    const [categorizedTours, setCategorizedTours] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchAndCategorizeTours = async () => {
            try {
                // Fetch all tours and all categories
                const [toursData, categoriesData] = await Promise.all([
                    getAllTourPackages(),
                    getAllCategories()
                ]);

                // Create a map for quick category name lookup
                const categoryMap = new Map();
                categoriesData.forEach(cat => {
                    categoryMap.set(cat.catId, cat.cname);
                });

                // Group tours by category
                const groupedTours = toursData.reduce((acc, tour) => {
                    const categoryId = tour.category.catId;
                    const categoryName = categoryMap.get(categoryId) || 'Uncategorized';
                    
                    if (!acc[categoryId]) {
                        acc[categoryId] = {
                            categoryId: categoryId,
                            categoryName: categoryName,
                            tours: []
                        };
                    }
                    acc[categoryId].tours.push(tour);
                    return acc;
                }, {});

                setCategorizedTours(groupedTours);
                setLoading(false);
            } catch (err) {
                setError("Failed to load tour packages.");
                setLoading(false);
                console.error("Error:", err);
            }
        };

        fetchAndCategorizeTours();
    }, []);

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    if (error) {
        return <div className="error">{error}</div>;
    }
    
    // Convert object to an array for mapping in the JSX
    const categoriesArray = Object.values(categorizedTours);

    return (
        <main className="main-content">
            <section className="hero-section">
                <img src={heroImage} alt="Hero banner" className="hero-image" />
                <div className="hero-overlay">
                    <h1>Your next adventure awaits.</h1>
                    <p>Discover breathtaking destinations and create unforgettable memories with TourNest.</p>
                </div>
            </section>
            <section className="popular-destinations">
                <h2>Popular Destinations</h2>
                <div className="tour-categories">
                    {categoriesArray.length > 0 ? (
                        categoriesArray.map((categoryGroup) => (
                            <div key={categoryGroup.categoryId} className="category-section">
                                <h3>{categoryGroup.categoryName}</h3>
                                <div className="tour-cards-container">
                                    {categoryGroup.tours.map((tour) => (
                                        <SimpleTourCard key={tour.packageId} tour={tour} />
                                    ))}
                                </div>
                            </div>
                        ))
                    ) : (
                        <p className="no-tours-message">No popular tours to display at the moment.</p>
                    )}
                </div>
            </section>
        </main>
    );
};

export default Home;
