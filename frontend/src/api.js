import axios from 'axios';

const api = axios.create({
  baseURL: 'https://smart-inventory-system-m7pc.onrender.com/api',
});

export default api;