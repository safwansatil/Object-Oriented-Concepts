import React from 'react';
import { useMutation } from '@tanstack/react-query';
import { Button } from '../../components/ui/Button';
import { updateOrderStatus, assignRider, getOrderById } from '../../api/orders';
import { Input } from '../../components/ui/Input';

/**
 * OrdersPage: Restaurant owner order management.
 */
export function OrdersPage() {
  const [orderId, setOrderId] = React.useState('');
  const [riderId, setRiderId] = React.useState('');
  const [status, setStatus] = React.useState('PREPARING');
  const [order, setOrder] = React.useState(null);
  const [message, setMessage] = React.useState('');

  const lookupMutation = useMutation({
    mutationFn: (id) => getOrderById(id),
    onSuccess: (data) => {
      setOrder(data);
      setMessage('');
    },
    onError: (e) => setMessage(e.message),
  });
  const statusMutation = useMutation({
    mutationFn: () => updateOrderStatus(orderId, status),
    onSuccess: (data) => {
      setOrder(data);
      setMessage('Order status updated.');
    },
    onError: (e) => setMessage(e.message),
  });
  const riderMutation = useMutation({
    mutationFn: () => assignRider(orderId, riderId),
    onSuccess: () => setMessage('Rider assigned.'),
    onError: (e) => setMessage(e.message),
  });

  return (
    <div className="space-y-6">
      <h1 className="headline-md text-3xl">Incoming Orders</h1>
      <p className="text-sm text-text-muted">Lookup order by id, then update status and assign rider.</p>
      <div className="bg-white p-6 rounded-card border border-border/20 space-y-3">
        <Input placeholder="Order ID" value={orderId} onChange={(e) => setOrderId(e.target.value)} />
        <Button onClick={() => lookupMutation.mutate(orderId)} disabled={!orderId || lookupMutation.isPending}>Lookup Order</Button>
        {order && (
          <div className="text-sm space-y-1">
            <p><strong>Order:</strong> {order.id}</p>
            <p><strong>Status:</strong> {order.status}</p>
            <p><strong>Customer:</strong> {order.customerId}</p>
          </div>
        )}
      </div>
      <div className="bg-white p-6 rounded-card border border-border/20 space-y-3">
        <Input placeholder="New Status (PREPARING, OUT_FOR_DELIVERY, DELIVERED...)" value={status} onChange={(e) => setStatus(e.target.value)} />
        <Button onClick={() => statusMutation.mutate()} disabled={!orderId || statusMutation.isPending}>Update Status</Button>
      </div>
      <div className="bg-white p-6 rounded-card border border-border/20 space-y-3">
        <Input placeholder="Rider ID" value={riderId} onChange={(e) => setRiderId(e.target.value)} />
        <Button onClick={() => riderMutation.mutate()} disabled={!orderId || !riderId || riderMutation.isPending}>Assign Rider</Button>
      </div>
      {message && <p className="text-sm font-bold text-accent">{message}</p>}
    </div>
  );
}
