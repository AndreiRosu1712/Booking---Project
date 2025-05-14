import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function Register() {
  const [rol, setRol] = useState('client');
  const [nume, setNume] = useState('');
  const [prenume, setPrenume] = useState('');
  const [mail, setMail] = useState('');
  const [username, setUsername] = useState('');
  const [parola, setParola] = useState('');

  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      const res = await api.post('/auth/register', {
        rol,
        nume,
        prenume,
        mail,
        username,
        parola
      });

      alert('Cont creat cu succes!');
      navigate('/login');
    } catch (err) {
      console.error(err);
      alert('Eroare la înregistrare.');
    }
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h2>Înregistrare</h2>

      <select value={rol} onChange={(e) => setRol(e.target.value)}>
        <option value="client">Client</option>
        <option value="manager">Manager</option>
      </select>
      <br /><br />

      <input placeholder="Nume" value={nume} onChange={(e) => setNume(e.target.value)} />
      <br /><br />
      <input placeholder="Prenume" value={prenume} onChange={(e) => setPrenume(e.target.value)} />
      <br /><br />
      <input placeholder="Email" value={mail} onChange={(e) => setMail(e.target.value)} />
      <br /><br />
      <input placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
      <br /><br />
      <input type="password" placeholder="Parola" value={parola} onChange={(e) => setParola(e.target.value)} />
      <br /><br />

      <button onClick={handleRegister}>Register</button>
    </div>
  );
}
