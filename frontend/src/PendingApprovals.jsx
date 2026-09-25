import { useEffect, useState } from 'react';
import api from './api';

function PendingApprovals() {
  const [pending, setPending] = useState([]);

  const fetchPending = async () => {
    const res = await api.get('/stock/pending');
    setPending(res.data);
  };

  useEffect(() => {
    fetchPending();
  }, []);

  const handleApprove = async (id) => {
    await api.put(`/stock/approve/${id}`);
    fetchPending();
  };

  const handleReject = async (id) => {
    await api.put(`/stock/reject/${id}`);
    fetchPending();
  };

  return (
    <div className="container">
      <h2>Pending Stock Removal Approvals</h2>
      {pending.length === 0 ? (
        <p>No pending requests.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Product</th>
              <th>Quantity</th>
              <th>Reason</th>
              <th>Requested By</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {pending.map((t) => (
              <tr key={t.id}>
                <td>{t.product?.name}</td>
                <td>{t.quantity}</td>
                <td>{t.reason}</td>
                <td>{t.user?.username}</td>
                <td>
                  <button onClick={() => handleApprove(t.id)}>Approve</button>
                  <button onClick={() => handleReject(t.id)} className="reject-btn">
                    Reject
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default PendingApprovals;