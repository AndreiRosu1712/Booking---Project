// src/pages/LandingPage.jsx
import { Link } from 'react-router-dom';

export default function LandingPage() {
  return (
    <div style={{ textAlign: 'center', marginTop: '100px' }}>
      <h1>Bun venit!</h1>
      <Link to="/login"><button>Login</button></Link>
      <Link to="/register"><button>Register</button></Link>
    </div>
  );
}
