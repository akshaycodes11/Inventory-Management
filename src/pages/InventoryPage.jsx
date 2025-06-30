import React, { useEffect, useState } from 'react';
import inventoryService from '../services/inventoryService';

export default function InventoryPage() {
  const [items, setItems] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    inventoryService.getAll()
      .then(setItems)
      .catch((err) => setError(err.message));
  }, []);

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Inventory</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <ul>
        {items.map((it) => (
          <li key={it.id}>
            <strong>{it.name}</strong> — {it.quantity}
          </li>
        ))}
      </ul>
    </div>
  );
}
