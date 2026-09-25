import { useState, useEffect } from 'react';
import api from './api';

function StockForm({ onSuccess }) {
  const [products, setProducts] = useState([]);
  const [productId, setProductId] = useState('');
  const [quantity, setQuantity] = useState('');
  const [reason, setReason] = useState('');
  const [type, setType] = useState('IN');
  const [message, setMessage] = useState('');

  useEffect(() => {
    api.get('/products').then((res) => setProducts(res.data));
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');

    try {
      const endpoint = type === 'IN' ? '/stock/in' : '/stock/out';
      await api.post(endpoint, {
        productId: Number(productId),
        quantity: Number(quantity),
        reason,
        userId: 1, // hardcoded for now, will come from login later
      });

      setMessage(
        type === 'IN'
          ? '✅ Stock added successfully.'
          : '✅ Stock removal request submitted (pending approval).'
      );
      setQuantity('');
      setReason('');
      if (onSuccess) onSuccess();
    } catch (err) {
      setMessage('❌ ' + (err.response?.data?.error || 'Something went wrong.'));
    }
  };

  return (
    <div className="container">
      <h2>Stock In / Out</h2>
      <form onSubmit={handleSubmit} className="stock-form">
        <label>
          Type
          <select value={type} onChange={(e) => setType(e.target.value)}>
            <option value="IN">Stock In (add)</option>
            <option value="OUT">Stock Out (remove)</option>
          </select>
        </label>

        <label>
          Product
          <select
            value={productId}
            onChange={(e) => setProductId(e.target.value)}
            required
          >
            <option value="">-- Select Product --</option>
            {products.map((p) => (
              <option key={p.id} value={p.id}>
                {p.name} ({p.sku})
              </option>
            ))}
          </select>
        </label>

        <label>
          Quantity
          <input
            type="number"
            min="1"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
            required
          />
        </label>

        <label>
          Reason
          <input
            type="text"
            placeholder="e.g. Purchase, Sale, Damaged"
            value={reason}
            onChange={(e) => setReason(e.target.value)}
            required
          />
        </label>

        <button type="submit">Submit</button>
      </form>
      {message && <p className="form-message">{message}</p>}
    </div>
  );
}

export default StockForm;