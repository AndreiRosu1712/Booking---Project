import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function Home() {
  const [hotels, setHotels] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const idClient = localStorage.getItem('idClient');
    if (idClient) {
      api.get('/hotels')
        .then(res => setHotels(res.data))
        .catch(err => console.error(err));
    }
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('idClient');
    navigate('/login');
  };

  return (
    <div style={{ padding: 20 }}>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <h2>Lista hoteluri</h2>
        <button onClick={handleLogout}>Logout</button>
        <button onClick={() => navigate('/my-bookings')}>Rezervările mele</button>

      </div>

      {hotels.map(hotel => (
        <div key={hotel.id} style={{ border: '1px solid gray', margin: 10, padding: 10 }}>
          <h3>{hotel.nume}</h3>
          <p>{hotel.locatie}</p>
          <button onClick={() => navigate(`/hotel/${hotel.id}`)}>Vezi detalii</button>
        </div>
      ))}
    </div>
  );
}