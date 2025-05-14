// src/pages/ManagerChatPage.js
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../services/api';

export default function ManagerChatPage() {
  const { idHotel, idClient } = useParams();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');

  useEffect(() => {
    api.get(`/conversations/hotel/${idHotel}/client/${idClient}`)
      .then(res => setMessages(res.data))
      .catch(err => console.error(err));
  }, [idHotel, idClient]);

  const handleSend = async () => {
    const idExpeditor = idHotel;
    try {
      await api.post('/conversations/send', {
        idHotel,
        idClient,
        idExpeditor,
        text: newMessage,
        expeditor: 'manager'
      });
      setNewMessage('');
      const res = await api.get(`/conversations/hotel/${idHotel}/client/${idClient}`);
      setMessages(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Conversație cu clientul {idClient}</h2>
      <div style={{ maxHeight: 300, overflowY: 'auto', border: '1px solid gray', marginBottom: 10, padding: 10 }}>
        {messages.map((msg, idx) => (
          <div key={idx} style={{ marginBottom: 8 }}>
            <strong>{msg.expeditor === 'manager' ? 'Hotel' : 'Client'}:</strong>
            <p>{msg.text}</p>
            <small style={{ color: '#999' }}>{new Date(msg.dataTrimitere).toLocaleString()}</small>
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
    </div>
  );
}
