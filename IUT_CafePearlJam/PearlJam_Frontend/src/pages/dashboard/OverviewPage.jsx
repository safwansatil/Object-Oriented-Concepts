import React, { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { registerRestaurant } from '../../api/restaurants';
import { Input } from '../../components/ui/Input';
import { Button } from '../../components/ui/Button';
import { useAuth } from '../../hooks/useAuth';

/**
 * OverviewPage: Restaurant owner's main performance dashboard.
 */
export function OverviewPage() {
  const { user } = useAuth();
  const [form, setForm] = useState({
    name: '',
    address: '',
    area: '',
    phone: '',
    cuisineType: '',
    opensAt: '09:00',
    closesAt: '22:00',
    description: '',
  });
  const [message, setMessage] = useState('');
  const mutation = useMutation({
    mutationFn: registerRestaurant,
    onSuccess: (data) => {
      localStorage.setItem('pearljam-restaurant-id', data.id);
      setMessage(`Restaurant registered. Active restaurant id: ${data.id}`);
    },
    onError: (e) => setMessage(e.message || 'Registration failed'),
  });

  const onSubmit = (e) => {
    e.preventDefault();
    mutation.mutate({
      ownerId: user.id,
      ...form,
      latitude: 0,
      longitude: 0,
    });
  };

  return (
    <div className="space-y-6">
      <h1 className="headline-md text-3xl">Restaurant Setup</h1>
      <p className="text-sm text-text-muted">
        Register your restaurant once. The saved restaurant id is used by Menu and Orders pages.
      </p>
      <form onSubmit={onSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-4 bg-white p-6 rounded-card border border-border/20">
        <Input placeholder="Restaurant name" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
        <Input placeholder="Cuisine type" value={form.cuisineType} onChange={(e) => setForm({ ...form, cuisineType: e.target.value })} required />
        <Input placeholder="Address" value={form.address} onChange={(e) => setForm({ ...form, address: e.target.value })} required />
        <Input placeholder="Area (e.g. Gulshan)" value={form.area} onChange={(e) => setForm({ ...form, area: e.target.value })} required />
        <Input placeholder="Phone" value={form.phone} onChange={(e) => setForm({ ...form, phone: e.target.value })} required />
        <Input placeholder="Opens At HH:mm" value={form.opensAt} onChange={(e) => setForm({ ...form, opensAt: e.target.value })} required />
        <Input placeholder="Closes At HH:mm" value={form.closesAt} onChange={(e) => setForm({ ...form, closesAt: e.target.value })} required />
        <Input placeholder="Description" value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} />
        <div className="md:col-span-2">
          <Button type="submit" disabled={mutation.isPending}>
            {mutation.isPending ? 'Registering...' : 'Register Restaurant'}
          </Button>
        </div>
      </form>
      {message && <p className="text-sm font-semibold text-accent">{message}</p>}
    </div>
  );
}
