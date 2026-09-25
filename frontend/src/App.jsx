import { useEffect, useState } from 'react';
import api from './api';
import StockForm from './StockForm';
import PendingApprovals from './PendingApprovals';
import './App.css';

function Dashboard() {
  const [products, setProducts] = useState([]);
  const [stockLevels, setStockLevels] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const res = await api.get('/products');
      setProducts(res.data);
      const stockData = {};
      for (const p of res.data) {
        const stockRes = await api.get(`/products/${p.id}/stock`);
        stockData[p.id] = stockRes.data;
      }
      setStockLevels(stockData);
    } catch (err) {
      console.error('Failed to fetch products:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div className="container">Loading...</div>;

  return (
    <div className="container">
      <h1>Smart Inventory Dashboard</h1>
      <table>
        <thead>
          <tr>
            <th>Product</th>
            <th>SKU</th>
            <th>Category</th>
            <th>Current Stock</th>
            <th>Reorder Level</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p) => {
            const stock = stockLevels[p.id] ?? 0;
            const isLow = stock <= p.reorderLevel;
            return (
              <tr key={p.id} className={isLow ? 'low-stock' : ''}>
                <td>{p.name}</td>
                <td>{p.sku}</td>
                <td>{p.category?.name}</td>
                <td>{stock}</td>
                <td>{p.reorderLevel}</td>
                <td>{isLow ? '⚠️ Low Stock' : '✅ OK'}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

function App() {
  const [tab, setTab] = useState('dashboard');
  const [refreshKey, setRefreshKey] = useState(0);

  return (
    <div>
      <nav className="tabs">
        <button
          className={tab === 'dashboard' ? 'active' : ''}
          onClick={() => setTab('dashboard')}
        >
          Dashboard
        </button>
        <button
          className={tab === 'stock' ? 'active' : ''}
          onClick={() => setTab('stock')}
        >
          Stock In/Out
        </button>
        <button
          className={tab === 'pending' ? 'active' : ''}
          onClick={() => setTab('pending')}
        >
          Pending Approvals
        </button>
      </nav>

      {tab === 'dashboard' && <Dashboard key={refreshKey} />}
      {tab === 'stock' && (
        <StockForm onSuccess={() => setRefreshKey((k) => k + 1)} />
      )}
      {tab === 'pending' && <PendingApprovals />}
    </div>
  );
}

export default App;