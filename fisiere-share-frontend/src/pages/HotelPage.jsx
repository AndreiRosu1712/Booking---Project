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
  const [tipCamera, setTipCamera] = useState('double');
const [cantitate, setCantitate] = useState(1);


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

  const handleBooking = async () => {
  try {
    const dataCheckIn = new Date();
    const dataCheckOut = new Date();
    dataCheckOut.setDate(dataCheckIn.getDate() + 2); // exemplu: 2 nopți

    await api.post('/bookigs', {
      camereRezervate: [
        {
          tipCamera,
          cantitate,
          totalPerCamera: 700 // poți ajusta dinamic dacă vrei
        }
      ],
      dataCheckIn,
      dataCheckOut,
      idClient,
      idHotel: id,
      status: "platit",
      totalPlata: cantitate * 700
    });

    alert('Rezervare efectuată cu succes!');
  } catch (err) {
    console.error(err);
    alert('Eroare la rezervare');
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
          <h3>Rezervă camere</h3>
<div style={{ marginBottom: 20 }}>
  <label>Tip cameră: </label>
  <select value={tipCamera} onChange={(e) => setTipCamera(e.target.value)}>
    <option value="double">Double</option>
    <option value="tip1">Tip 1</option>
  </select>

  <br /><br />

  <label>Cantitate: </label>
  <input
    type="number"
    min="1"
    value={cantitate}
    onChange={(e) => setCantitate(Number(e.target.value))}
    style={{ width: 50, marginLeft: 10 }}
  />

  <br /><br />
  <button onClick={handleBooking}>Rezervă</button>
</div>

        </>
        
      )}
    </div>

  );

}
