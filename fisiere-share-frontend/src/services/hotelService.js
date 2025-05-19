import api from './api';

export const getAllHotels = async () => {
  const response = await api.get('/hotels');
  return response.data;
};

export const getHotelById = async (id) => {
  const response = await api.get(`/hotels/${id}`);
  return response.data;
};
