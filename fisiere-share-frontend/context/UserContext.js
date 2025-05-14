import React, { createContext, useState, useEffect } from 'react';

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [idClient, setIdClient] = useState(null);

  useEffect(() => {
    const storedId = localStorage.getItem('idClient');
    if (storedId) setIdClient(storedId);
  }, []);

  const login = (id) => {
    localStorage.setItem('idClient', id);
    setIdClient(id);
  };

  const logout = () => {
    localStorage.removeItem('idClient');
    setIdClient(null);
  };

  return (
    <UserContext.Provider value={{ idClient, login, logout }}>
      {children}
    </UserContext.Provider>
  );
};