import { useState, useEffect } from 'react'
import { MapContainer, TileLayer, Marker, Tooltip, Polyline, useMap, ZoomControl } from 'react-leaflet'
import apiClient from './api/axiosConfig' 
import { AuthProvider } from './context/AuthContext' 
import AuthOverlay from './components/AuthOverlay'
import 'leaflet/dist/leaflet.css'
import L from 'leaflet'
import './App.css'
import Sidebar from './Sidebar'


import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';
let DefaultIcon = L.icon({
    iconUrl: icon, shadowUrl: iconShadow,
    iconSize: [25, 41], iconAnchor: [12, 41]
});
L.Marker.prototype.options.icon = DefaultIcon;

// --- Component to Auto-Zoom to User ---
function LocationMarker({ setUserLocation }) {
  const map = useMap();

  useEffect(() => {
    map.locate().on("locationfound", function (e) {
      setUserLocation(e.latlng);
      map.flyTo(e.latlng, 13);
      L.circle(e.latlng, { radius: 200 }).addTo(map); 
    });
  }, [map]);

  return null;
}

const addDefaultImg = (ev) => {
    ev.target.src = "https://placehold.co/200x150/EFEFEF/666666?text=No+Image";
}

function MapApp() {
  const [position] = useState([11.9416, 79.8083]) 
  const [places, setPlaces] = useState([]) 
  const [loading, setLoading] = useState(true)
  const [selectedSpots, setSelectedSpots] = useState([]) 
  const [routePath, setRoutePath] = useState(null)
  const [userLocation, setUserLocation] = useState(null)
  const [useMyLocation, setUseMyLocation] = useState(false)

  // Fetch places on load
  useEffect(() => {
    const fetchPlaces = async () => {
      try {
        const response = await apiClient.get('/places')
        console.log("Backend Response: ", response.data);

        // 1. Map the data to ensure 'lat' and 'lon' exist
        const formattedPlaces = response.data.map(place => ({
            ...place, 
            lat: place.latitude || place.lat, // Handle both cases
            lon: place.longitude || place.lon || place.lng
        }));

        setPlaces(formattedPlaces) 
      } catch (error) { 
        console.error(error) 
      } finally {
        setLoading(false)
      }
    }
    fetchPlaces()
  }, [])

  if (loading) {
    return <div style={{textAlign: 'center', marginTop: '20%'}}>Loading Map...</div>
  }

  const handleSpotClick = (spot) => {
    if (!selectedSpots.find(s => s.id === spot.id)) {
      setSelectedSpots([...selectedSpots, spot])
    }
  }

  const removeSpot = (id) => {
    setSelectedSpots(selectedSpots.filter(s => s.id !== id))
  }

  const handleOptimize = async () => {
    let startLat, startLon;
    let stops = [...selectedSpots]; 

    if (useMyLocation && userLocation) {
        startLat = userLocation.lat;
        startLon = userLocation.lng;
    } else {
        if (stops.length < 2) { alert("Select at least 2 spots (or use 'My Location')!"); return; }
        const start = stops.shift(); 
        startLat = start.lat;
        startLon = start.lon;
    }

    if (stops.length === 0) { alert("Select a destination!"); return; }
    const end = stops.pop(); 

    const waypoints = stops.map(p => [p.lat, p.lon]);

    const payload = {
        fromLat: startLat, fromLon: startLon,
        toLat: end.lat, toLon: end.lon,
        waypoints: waypoints,
        vehicle: "car" 
    };

    try {
        const response = await apiClient.post('/route', payload);
        const path = response.data.waypoints.map(p => [p[1], p[0]]);
        setRoutePath(path);
    } catch (error) {
        console.error(error);
        alert("Route calculation failed! (Are you logged in?)");
    }
  }

  return (
    <div style={{ height: "100vh", width: "100vw", position: 'relative' }}>
      <AuthOverlay />
      <Sidebar 
        selectedSpots={selectedSpots} 
        removeSpot={removeSpot} 
        onOptimize={handleOptimize}
        useMyLocation={useMyLocation}
        setUseMyLocation={setUseMyLocation}
      />

      <MapContainer 
          center={position} 
          zoom={13} 
          zoomControl={false} 
          style={{ height: "100%", width: "100%" }}
      >
        <TileLayer attribution='&copy; OpenStreetMap' url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

        {/* 3. MERGED LOOP: Safety checks + Rendering logic in one place */}
        {!loading && places.length > 0 && places.map((place) => {
          // Safety Check: Skip invalid data to prevent crashes
          if (!place.lat || !place.lon) return null;

          return (
            <Marker 
                key={place.id} 
                position={[place.lat, place.lon]}
                eventHandlers={{ click: () => handleSpotClick(place) }}
            >
                <Tooltip 
                    direction="top" 
                    offset={[0, -20]} 
                    opacity={1} 
                    className="photo-tooltip" 
                    sticky={true} 
                >
                    <div className="tooltip-card-content">
                        <img 
                            src={place.imageUrl} 
                            alt={place.name} 
                            className="tooltip-image" 
                            onError={addDefaultImg} 
                        />
                        <div className="tooltip-text">
                            <h4>{place.name}</h4>
                            <p>{place.description}</p>
                        </div>
                    </div>
                </Tooltip>
            </Marker>
          )
        })}
        
        <ZoomControl position="bottomright" />
        <LocationMarker setUserLocation={setUserLocation} />
        {routePath && <Polyline positions={routePath} color="#2196F3" weight={6} opacity={0.8} />}

      </MapContainer>
    </div>
  )
}

function App() {
    return (
        <AuthProvider>
            <MapApp />
        </AuthProvider>
    );
}

export default App