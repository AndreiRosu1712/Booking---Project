// src/pages/ManagerHotels.js
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function ManagerHotels() {
  const [hotels, setHotels] = useState([]);
  const navigate = useNavigate();
  const idManager = localStorage.getItem('idClient');

  useEffect(() => {
    if (!idManager) return;
    api.get(`/hotels/manager/${idManager}`)
      .then(res => setHotels(res.data))
      .catch(err => console.error(err));
  }, [idManager]);

  return (
    <div style={{ padding: 20 }}>
      <h2>Hotelurile tale</h2>
      {hotels.map(hotel => (
        <div key={hotel.id} style={{ border: '1px solid gray', margin: 10, padding: 10 }}>
          <h3>{hotel.nume}</h3>
          <p>{hotel.locatie}</p>
          <button onClick={() => navigate(`/manager-hotel/${hotel.id}`)}>Vezi conversații</button>
        </div>
      ))}
    </div>
  );
}
