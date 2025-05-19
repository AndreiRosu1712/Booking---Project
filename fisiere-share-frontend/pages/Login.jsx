import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function Login() {
  const [identifier, setIdentifier] = useState('');
  const [parola, setParola] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await api.post('/auth/login', { identifier, parola });
      const { id, rol } = res.data;

      if (!id || !rol) throw new Error("ID sau rol lipsă!");

      localStorage.setItem("idClient", id);
      localStorage.setItem("rol", rol);

      console.log("✅ Login:", { id, rol });

      setTimeout(() => {
        if (rol === 'manager') {
          navigate('/manager-hotels');
        } else {
          navigate('/home');
        }
      }, 100);
    } catch (err) {
      console.error("Login error:", err);
      alert("Login eșuat");
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Login</h2>
      <input
        value={identifier}
        onChange={e => setIdentifier(e.target.value)}
        placeholder="Username sau Email"
      /><br />
      <input
        type="password"
        value={parola}
        onChange={e => setParola(e.target.value)}
        placeholder="Parola"
      /><br />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}
