import axios from 'axios';

const API_URL = 'http://localhost:8080/api/inventory';

const getAll = async () => {
  const token = localStorage.getItem('token');
  const response = await axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return response.data;
};

const inventoryService = { getAll };
export default inventoryService;