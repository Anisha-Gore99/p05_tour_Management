import React from 'react';
import { Link } from 'react-router-dom';
import '../../css/tour.css';

// 1. Import static images
import himalayanTrekImage from '../../assets/HimalayanTrek.jpg';
import goaBeachFunImage from '../../assets/GoaBeach.jpg';
import kedarnathImage from '../../assets/Kedarnath.jpg';
import ranthamboreSafariImage from '../../assets/ranthamboreSafari.jpg';
import keralaBackwatersImage from '../../assets/kerala-backwaters.jpg';
import goldenTriangleImage from '../../assets/golden-triangle.jpg';
import andamanAdventureImage from '../../assets/andaman-adventure.jpg';
import spitiValleyImage from '../../assets/spiti-valley-trek.jpg';
import jimCorbettImage from '../../assets/jim-corbett-safari.jpg';
import chardhamyatraImage from '../../assets/CharDhamYatra.jpg';
import swissalpshikingImage from '../../assets/SwissAlpsHiking.jpg';
import serengetisafariImage from '../../assets/SerengetiSafari.jpg';
import thailandislandshoppingImage from '../../assets/thailandislandshopping.jpg';
import egyptpyramidsnileImage from '../../assets/egyptpyramidsnile.jpg';
import kilimanjarosummitImage from '../../assets/kilimanjarosummit.jpg';
import australiacoastaldriveImage from '../../assets/australiacoastaldrive.jpg';
import amarnathcavehimalayantrekImage from '../../assets/amarnathcavehimalayantrek.jpg';
import varanasispiritualtourImage from '../../assets/varanasispiritualtour.jpg';
import manaliadventureescapeImage from '../../assets/manaliadventureescape.jpg';
import darjeelingteagardensImage from '../../assets/darjeelingteagardens.jpg';
import ootymistymountainsImage from '../../assets/ootymistymountains.jpg';
import maldivesparadiseescapeImage from '../../assets/maldivesparadiseescape.jpg';
import phukettropicalgetawayImage from '../../assets/phukettropicalgetaway.jpg';
import jaipurroyalheritageImage from '../../assets/jaipurroyalheritage.jpg';
import gangaaarticeremonyImage from '../../assets/gangaaarticeremony.jpg';
import chennaicoastalcharmImage from '../../assets/chennaicoastalcharm.jpg';
import jaisalmerdesertsafariImage from '../../assets/jaisalmerdesertsafari.jpg';
import hampiruinsexplorationImage from '../../assets/hampiruinsexploration.jpg';
import khajurahotemplevisitImage from '../../assets/khajurahotemplevisit.jpg';
import ajantaelloracavesImage from '../../assets/ajantaelloracaves.jpg';
import rajasthanfamilyfunImage from '../../assets/rajasthanfamilyfun.jpg';
import dubaifamilyadventureImage from '../../assets/dubaifamilyadventure.jpg';
import singaporefuntripImage from '../../assets/singaporefuntrip.jpg';


// 2. Create a map to link the tour name (from the backend) to the imported image.
//   The keys in this map MUST exactly match the tour package names from database.
const tourImageMap = {
    "Himalayan Trek": himalayanTrekImage,
    "Goa Beach Fun": goaBeachFunImage,
    "Kedarnath Pilgrimage": kedarnathImage,
    "Ranthambore Safari": ranthamboreSafariImage,
    "Kerala Backwaters": keralaBackwatersImage,
    "Golden Triangle": goldenTriangleImage,
    "Andaman Adventure": andamanAdventureImage,
    "Spiti Valley Trek": spitiValleyImage,
    "Jim Corbett Safari": jimCorbettImage,
    "Char Dham Yatra" : chardhamyatraImage,
    "Swiss Alps Hiking" : swissalpshikingImage,
    "Serengeti Safari" : serengetisafariImage,
    "Thailand Islands Hopping" : thailandislandshoppingImage,
    "Egypt Pyramids & Nile" : egyptpyramidsnileImage,
    "Kilimanjaro Summit" : kilimanjarosummitImage,
    "Australia Coastal Drive" : australiacoastaldriveImage,
    "Amarnath Cave Trek" : amarnathcavehimalayantrekImage,
    "Varanasi Spiritual Tour" : varanasispiritualtourImage,
    "Manali Adventure Escape" : manaliadventureescapeImage,
    "Darjeeling Tea Gardens" : darjeelingteagardensImage,
    "Ooty Misty Mountains" : ootymistymountainsImage,
    "Maldives Paradise Escape" : maldivesparadiseescapeImage,
    "Phuket Tropical Getaway" : phukettropicalgetawayImage,
    "Jaipur Royal Heritage" : jaipurroyalheritageImage,
    "Ganga Aarti Ceremony" : gangaaarticeremonyImage,
    "Chennai Coastal Charm" : chennaicoastalcharmImage,
    "Jaisalmer Desert Safari" : jaisalmerdesertsafariImage,
    "Hampi Ruins Exploration" : hampiruinsexplorationImage,
    "Khajuraho Temple Visit" : khajurahotemplevisitImage,
    "Ajanta Ellora Caves" : ajantaelloracavesImage,
    "Rajasthan Family Fun" :  rajasthanfamilyfunImage,
    "Dubai Family Adventure" : dubaifamilyadventureImage,
    "Singapore Fun Trip" : singaporefuntripImage,
};

const SimpleTourCard = ({ tour }) => {
    // Get the correct image from the map based on the tour name.
    const imageUrl = tourImageMap[tour.pname] || "https://placehold.co/600x400/E9F5FF/000000?text=Image+Not+Found";
    
    return (
        <div className="tour-card">
            <Link to={`/tours/${tour.packageId}`} className="tour-card-link-container">
                <img src={imageUrl} alt={tour.pname} className="tour-card-image" />
                <div className="tour-card-body-simple">
                    <h3 className="tour-card-title">{tour.pname}</h3>
                </div>
            </Link>
        </div>
    );
};

export default SimpleTourCard;
