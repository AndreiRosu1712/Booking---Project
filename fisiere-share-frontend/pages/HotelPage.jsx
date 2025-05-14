import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function HotelPage() {
  const { id } = useParams();
  const [hotel, setHotel] = useState(null);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const navigate = useNavigate();
  const idClient = localStorage.getItem('idClient');

  useEffect(() => {
    if (!idClient) {
      navigate('/login');
      return;
    }
    api.get(`/hotels/${id}`)
      .then(res => setHotel(res.data))
      .catch(err => console.error(err));
  }, [id, idClient, navigate]);

  const loadMessages = () => {
    if (!idClient || !id) return;
    api.get(`/conversations/${idClient}/${id}`)
      .then(res => setMessages(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    loadMessages();
  }, [idClient, id]);

  const handleSend = async () => {
    if (!newMessage.trim()) return;

    try {
      await api.post(`/conversations/send`, {
        idHotel: id,
        idClient,
        idExpeditor: idClient,
        text: newMessage,
        expeditor: 'client'
      });
      setNewMessage('');
      loadMessages();
    } catch (err) {
      alert('Eroare la trimiterea mesajului');
    }
  };

  return (
    <div style={{ padding: 20 }}>
      {hotel && (
        <>
          <h2>{hotel.nume}</h2>
          <p><strong>Locație:</strong> {hotel.locatie}</p>
          <p><strong>Descriere:</strong> {hotel.descriere}</p>
          <p><strong>Manager:</strong> {hotel.numeManager}</p>

          <h3>Conversație</h3>
          <div style={{
            marginBottom: 10,
            padding: 10,
            border: '1px solid #ccc',
            maxHeight: 300,
            overflowY: 'auto',
            backgroundColor: '#f9f9f9'
          }}>
            {messages.length === 0 && <p>Nu există mesaje încă.</p>}
            {messages.map((msg, idx) => (
              <div key={idx} style={{ marginBottom: 8 }}>
                <strong>{msg.expeditor === 'client' ? 'Tu' : 'Manager'}:</strong>
                <p style={{ margin: 0 }}>{msg.text}</p>
                <small style={{ color: '#888' }}>{new Date(msg.dataTrimitere).toLocaleString()}</small>
              </div>
            ))}
          </div>
          <input
            value={newMessage}
            onChange={e => setNewMessage(e.target.value)}
            placeholder="Scrie un mesaj..."
            style={{ width: '70%', marginRight: 10 }}
          />
          <button onClick={handleSend}>Trimite</button>
        </>
      )}
    </div>
  );
}
