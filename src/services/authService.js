import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

const login = async (username, password) => {
  const response = await axios.post(`${API_URL}/login`, { username, password });
  const token = response.data.token;
  console.log(token);
  localStorage.setItem('token', token);  // Save token
  return token;
};

const register = async (username, password, role) => {
  const response = await axios.post(`${API_URL}/register`, { username, password, role });
  return response.data;
};

const logout = () => {
  localStorage.removeItem('token');
};

const authService = {
  login,
  register,
  logout
};

export default authService;