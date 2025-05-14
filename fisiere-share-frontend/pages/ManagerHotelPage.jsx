// src/pages/ManagerHotelPage.js
import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function ManagerHotelPage() {
  const { id } = useParams(); // idHotel
  const [conversations, setConversations] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    api.get(`/conversations/hotel/${id}`)
      .then(res => setConversations(res.data))
      .catch(err => console.error(err));
  }, [id]);

  return (
    <div style={{ padding: 20 }}>
      <h2>Conversațiile hotelului</h2>
      {conversations.map(conv => (
        <div key={conv.idClient} style={{ marginBottom: 10 }}>
          <p><strong>Client:</strong> {conv.idClient}</p>
          <button onClick={() => navigate(`/manager-hotel/${id}/client/${conv.idClient}`)}>Deschide conversația</button>
        </div>
      ))}
    </div>
  );
}
