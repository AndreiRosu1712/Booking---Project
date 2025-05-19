import { useEffect, useState } from 'react';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';

export default function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const idClient = localStorage.getItem('idClient');
  const navigate = useNavigate();

  useEffect(() => {
    if (!idClient) {
      navigate('/login');
      return;
    }

    api.get(`/bookigs/client/${idClient}`)
      .then(res => setBookings(res.data))
      .catch(err => console.error(err));
  }, [idClient, navigate]);

  return (
    <div style={{ padding: 20 }}>
      <h2>Rezervările mele</h2>

      {bookings.length === 0 && <p>Nu ai rezervări.</p>}

      {bookings.map((booking, idx) => (
        <div key={idx} style={{ border: '1px solid gray', marginBottom: 10, padding: 10 }}>
          <p><strong>Hotel:</strong> {booking.numeHotel}</p>
          <p><strong>Check-in:</strong> {new Date(booking.dataCheckIn).toLocaleDateString()}</p>
          <p><strong>Check-out:</strong> {new Date(booking.dataCheckOut).toLocaleDateString()}</p>
          <p><strong>Total plată:</strong> {booking.totalPlata} RON</p>
          <p><strong>Status:</strong> {booking.status}</p>
        </div>
      ))}
    </div>
  );
}
