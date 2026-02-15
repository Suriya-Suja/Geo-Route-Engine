import React from 'react';

const Sidebar = ({ selectedSpots, removeSpot, onOptimize, useMyLocation, setUseMyLocation }) => {
  
  // Helper to hide broken images automatically
  const addDefaultImg = (ev) => {
    ev.target.src = "https://placehold.co/60x60/EFEFEF/666666?text=No+Image" // Fallback "Camera" Icon
  }

  return (
    <div style={{
      position: 'absolute', top: '20px', left: '20px', width: '350px', maxHeight: '90vh',
      backgroundColor: 'rgba(255, 255, 255, 0.70)',
      backdropFilter: 'blur(12px)',
      border: '1px solid rgba(255, 255, 255, 0.3)',
      boxShadow: '0 8px 32px rgba(0, 0, 0, 0.15)',
      zIndex: 1000,
      padding: '25px', borderRadius: '16px', overflowY: 'auto', fontFamily: '"Segoe UI", Roboto, sans-serif'
    }}>
      
      {/* 1. The Header */}
      <div style={{marginBottom: '25px', borderBottom: '2px solid #f0f0f0', paddingBottom: '15px'}}>
        <h2 style={{ 
            margin: '0', 
            background: 'linear-gradient(45deg, #FF512F, #DD2476)', 
            WebkitBackgroundClip: 'text', 
            WebkitTextFillColor: 'transparent',
            fontSize: '24px',
            fontWeight: '800'
        }}>
          🌴 Pondy Trip
        </h2>
        <p style={{ margin: '5px 0 0 0', color: '#888', fontSize: '13px', fontWeight: '500' }}>
          Curate your perfect photo route
        </p>
      </div>

      {/* 📍 My Location Toggle */}
      <div style={{ 
          marginBottom: '20px', padding: '15px', 
          background: useMyLocation ? '#e3f2fd' : '#f8f9fa', 
          border: useMyLocation ? '1px solid #2196f3' : '1px solid #eee',
          borderRadius: '12px', display: 'flex', alignItems: 'center', gap: '12px',
          transition: 'all 0.3s ease'
      }}>
        <div style={{
            position: 'relative', width: '40px', height: '20px', 
            background: useMyLocation ? '#2196F3' : '#ccc', borderRadius: '20px',
            transition: '0.3s'
        }}>
            <input 
              type="checkbox" 
              checked={useMyLocation} 
              onChange={(e) => setUseMyLocation(e.target.checked)}
              style={{ opacity: 0, width: '100%', height: '100%', position: 'absolute', zIndex: 1, cursor: 'pointer' }}
            />
            <div style={{
                width: '16px', height: '16px', background: 'white', borderRadius: '50%',
                position: 'absolute', top: '2px', left: useMyLocation ? '22px' : '2px', transition: '0.3s'
            }}/>
        </div>
        <label style={{ fontSize: '14px', fontWeight: '600', color: '#444' }}>Start from My Location</label>
      </div>
      
      {/* 📋 The List */}
      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        {selectedSpots.length === 0 && !useMyLocation && 
            <div style={{textAlign: 'center', padding: '30px', color: '#aaa', border: '2px dashed #eee', borderRadius: '12px'}}>
                📸 <br/> Click pins on the map <br/> to start planning
            </div>
        }
        
        {selectedSpots.map((spot, index) => {
          let badge = null;
          // Logic: If using GPS, first spot is just a spot. If NOT using GPS, first spot is START.
          if (index === 0 && !useMyLocation) badge = <span style={{background:'#4CAF50', color:'white', fontSize:'10px', padding:'4px 8px', borderRadius:'12px', marginRight:'8px', fontWeight:'bold'}}>START</span>;
          else if (index === selectedSpots.length - 1 && selectedSpots.length > 1) badge = <span style={{background:'#FF512F', color:'white', fontSize:'10px', padding:'4px 8px', borderRadius:'12px', marginRight:'8px', fontWeight:'bold'}}>END</span>;

          return (
            <div key={index} style={{
              padding: '10px', border: '1px solid #eee', borderRadius: '12px',
              display: 'flex', justifyContent: 'space-between', alignItems: 'center', backgroundColor: 'white',
              boxShadow: '0 2px 5px rgba(0,0,0,0.03)'
            }}>
              <div style={{display:'flex', alignItems:'center'}}>
                {/* ✨ 2. The Image Fix with Error Handler */}
                <img 
                    src={spot.imageUrl} 
                    onError={addDefaultImg} 
                    alt="" 
                    style={{width:'40px', height:'40px', borderRadius:'8px', marginRight:'12px', objectFit:'cover', border: '1px solid #eee'}}
                />
                <div>
                  {badge}
                  <span style={{fontSize: '14px', fontWeight: '600', color: '#333'}}>{spot.name}</span>
                </div>
              </div>
              <button 
                onClick={() => removeSpot(spot.id)}
                style={{ 
                    background: '#fff0f0', border: 'none', color: '#ff4d4f', 
                    width: '24px', height: '24px', borderRadius: '50%', cursor: 'pointer', display: 'flex', alignItems: 'center', justifyContent: 'center'
                }}
              >
                &times;
              </button>
            </div>
          );
        })}
      </div>

      {/* Action Button */}
      {(selectedSpots.length >= 1) && (
        <button 
          onClick={onOptimize}
          style={{
            width: '100%', padding: '16px', 
            background: 'linear-gradient(45deg, #2196F3, #21CBF3)', 
            color: 'white', border: 'none', borderRadius: '12px', marginTop: '25px', cursor: 'pointer',
            fontWeight: '700', fontSize: '15px', letterSpacing: '0.5px',
            boxShadow: '0 4px 15px rgba(33, 150, 243, 0.4)',
            transition: 'transform 0.2s'
          }}
          onMouseOver={(e) => e.target.style.transform = 'translateY(-2px)'}
          onMouseOut={(e) => e.target.style.transform = 'translateY(0px)'}
        >
          {useMyLocation ? "📍 Route from Me" : "🚀 Plan Optimized Trip"}
        </button>
      )}
    </div>
  );
};

export default Sidebar;