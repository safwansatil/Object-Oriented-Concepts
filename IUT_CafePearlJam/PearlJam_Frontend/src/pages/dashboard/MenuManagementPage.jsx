import React, { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { addMenuCategory, addMenuItem, setMenuItemAvailability, updateMenuItemStock } from '../../api/menu';
import { Button } from '../../components/ui/Button';
import { Input } from '../../components/ui/Input';

/**
 * MenuManagementPage: Restaurant owner menu item management.
 */
export function MenuManagementPage() {
  const restaurantId = localStorage.getItem('pearljam-restaurant-id') || '';
  const [category, setCategory] = useState('');
  const [item, setItem] = useState({ categoryId: '', name: '', description: '', basePrice: '', prepTime: '10', initialStock: '20' });
  const [availabilityItemId, setAvailabilityItemId] = useState('');
  const [stockItemId, setStockItemId] = useState('');
  const [stockValue, setStockValue] = useState('');
  const [message, setMessage] = useState('');

  const addCategoryMutation = useMutation({
    mutationFn: addMenuCategory,
    onSuccess: (data) => {
      setItem((prev) => ({ ...prev, categoryId: data.id }));
      setMessage(`Category created: ${data.name} (${data.id})`);
    },
    onError: (e) => setMessage(e.message),
  });
  const addItemMutation = useMutation({
    mutationFn: addMenuItem,
    onSuccess: (data) => setMessage(`Menu item created: ${data.name} (${data.id})`),
    onError: (e) => setMessage(e.message),
  });
  const availabilityMutation = useMutation({
    mutationFn: ({ id, available }) => setMenuItemAvailability(id, available),
    onSuccess: () => setMessage('Availability updated.'),
    onError: (e) => setMessage(e.message),
  });
  const stockMutation = useMutation({
    mutationFn: ({ id, quantity }) => updateMenuItemStock(id, quantity),
    onSuccess: () => setMessage('Stock updated.'),
    onError: (e) => setMessage(e.message),
  });

  return (
    <div className="space-y-6">
      <h1 className="headline-md text-3xl">Menu Management</h1>
      {!restaurantId && <p className="text-danger text-sm font-bold">Please register a restaurant first in Overview.</p>}
      <div className="bg-white p-6 rounded-card border border-border/20 space-y-4">
        <h2 className="font-bold">1) Create Category</h2>
        <Input placeholder="Category name" value={category} onChange={(e) => setCategory(e.target.value)} />
        <Button
          disabled={!restaurantId || !category || addCategoryMutation.isPending}
          onClick={() => addCategoryMutation.mutate({ restaurantId, name: category, displayOrder: 1 })}
        >
          Create Category
        </Button>
      </div>
      <div className="bg-white p-6 rounded-card border border-border/20 space-y-4">
        <h2 className="font-bold">2) Create Menu Item</h2>
        <Input placeholder="Category ID" value={item.categoryId} onChange={(e) => setItem({ ...item, categoryId: e.target.value })} />
        <Input placeholder="Name" value={item.name} onChange={(e) => setItem({ ...item, name: e.target.value })} />
        <Input placeholder="Description" value={item.description} onChange={(e) => setItem({ ...item, description: e.target.value })} />
        <Input placeholder="Base Price" value={item.basePrice} onChange={(e) => setItem({ ...item, basePrice: e.target.value })} />
        <Input placeholder="Preparation Time (minutes)" value={item.prepTime} onChange={(e) => setItem({ ...item, prepTime: e.target.value })} />
        <Input placeholder="Initial Stock" value={item.initialStock} onChange={(e) => setItem({ ...item, initialStock: e.target.value })} />
        <Button
          disabled={!restaurantId || !item.categoryId || !item.name || !item.basePrice || addItemMutation.isPending}
          onClick={() => addItemMutation.mutate({
            restaurantId,
            categoryId: item.categoryId,
            name: item.name,
            description: item.description,
            basePrice: Number(item.basePrice),
            imageUrl: '',
            prepTime: Number(item.prepTime),
            trackQuantity: true,
            initialStock: Number(item.initialStock),
          })}
        >
          Create Menu Item
        </Button>
      </div>
      <div className="bg-white p-6 rounded-card border border-border/20 space-y-3">
        <h2 className="font-bold">3) Toggle Availability</h2>
        <Input placeholder="Menu Item ID" value={availabilityItemId} onChange={(e) => setAvailabilityItemId(e.target.value)} />
        <div className="flex gap-2">
          <Button onClick={() => availabilityMutation.mutate({ id: availabilityItemId, available: true })}>Set Available</Button>
          <Button variant="outline" onClick={() => availabilityMutation.mutate({ id: availabilityItemId, available: false })}>Set Unavailable</Button>
        </div>
      </div>
      <div className="bg-white p-6 rounded-card border border-border/20 space-y-3">
        <h2 className="font-bold">4) Update Stock</h2>
        <Input placeholder="Menu Item ID" value={stockItemId} onChange={(e) => setStockItemId(e.target.value)} />
        <Input placeholder="Quantity" value={stockValue} onChange={(e) => setStockValue(e.target.value)} />
        <Button onClick={() => stockMutation.mutate({ id: stockItemId, quantity: Number(stockValue) })}>Update Stock</Button>
      </div>
      {message && <p className="text-sm text-accent font-bold">{message}</p>}
    </div>
  );
}
