import React from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { StatusBadge } from '../../components/common/StatusBadge';
import { LoadingSpinner } from '../../components/common/LoadingSpinner';
import { EmptyState } from '../../components/common/EmptyState';
import { Card, CardContent, CardHeader, CardTitle } from '../../components/ui/Card';
import { Button } from '../../components/ui/Button';
import { ChevronRight, Clock, MapPin, Package, CheckCircle2, Users } from 'lucide-react';
import client from '../../api/client';
import { updateOrderStatus } from '../../api/orders';

/**
 * OrdersPage: Restaurant owner order management.
 */
export function OrdersPage() {
  const queryClient = useQueryClient();
  
  const { data: orders, isLoading, error } = useQuery({
    queryKey: ['dashboard-orders'],
    queryFn: () => client.get('/dashboard/orders'),
  });

  const updateMutation = useMutation({
    mutationFn: ({ id, status }) => updateOrderStatus(id, status),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['dashboard-orders'] });
    },
  });

  const getNextStatus = (currentStatus) => {
    const sequence = ['PENDING', 'CONFIRMED', 'PREPARING', 'READY', 'DELIVERING', 'DELIVERED'];
    const idx = sequence.indexOf(currentStatus.toUpperCase());
    return idx < sequence.length - 1 ? sequence[idx + 1] : null;
  };

  if (isLoading) return <LoadingSpinner size={48} className="py-40" />;
  if (error) return <EmptyState title="Error Loading Orders" message={error.message} />;

  return (
    <div className="space-y-10 animate-in fade-in slide-in-from-bottom-4 duration-500">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="headline-md text-4xl mb-1">Active Deliveries</h1>
          <p className="body-lg text-sm text-text-muted">Direct control over the hand-off process.</p>
        </div>
      </div>

      <div className="space-y-6">
        {orders && orders.length > 0 ? orders.map((order) => (
          <Card key={order.id} className="overflow-hidden border-l-4 border-l-accent">
            <CardContent className="p-0">
              <div className="grid md:grid-cols-12 items-stretch">
                {/* Order Meta */}
                <div className="md:col-span-3 p-8 border-r border-border/10 bg-surface/10">
                  <div className="flex flex-col h-full justify-between gap-4">
                    <div>
                      <p className="label-md font-bold text-accent uppercase tracking-widest text-[10px] mb-2">Order Ref</p>
                      <h4 className="font-serif text-xl font-bold">PJ-{order.id.slice(-8).toUpperCase()}</h4>
                    </div>
                    <div>
                      <p className="label-md font-bold text-text-muted uppercase tracking-widest text-[10px] mb-2">Time Elapsed</p>
                      <div className="flex items-center gap-2 text-text-primary label-md font-bold">
                        <Clock size={16} className="text-accent" />
                        12:45
                      </div>
                    </div>
                  </div>
                </div>

                {/* Customer & Address */}
                <div className="md:col-span-5 p-8 flex flex-col justify-center gap-6">
                  <div className="flex items-start gap-4">
                    <div className="w-10 h-10 rounded-full bg-surface flex items-center justify-center text-accent">
                      <Users size={18} />
                    </div>
                    <div>
                      <h4 className="label-md font-bold text-[10px] uppercase text-text-muted mb-1">Recipient</h4>
                      <p className="body-lg text-sm font-bold text-text-primary">{order.customerName}</p>
                    </div>
                  </div>
                  <div className="flex items-start gap-4">
                    <div className="w-10 h-10 rounded-full bg-surface flex items-center justify-center text-accent">
                      <MapPin size={18} />
                    </div>
                    <div>
                      <h4 className="label-md font-bold text-[10px] uppercase text-text-muted mb-1">Delivery Loc.</h4>
                      <p className="body-lg text-sm text-text-primary leading-tight line-clamp-1">{order.deliveryAddress}</p>
                    </div>
                  </div>
                </div>

                {/* Status & Actions */}
                <div className="md:col-span-4 p-8 flex flex-col justify-center items-end gap-6 bg-surface/5">
                  <div className="flex items-center gap-4">
                    <StatusBadge status={order.status} />
                    <span className="font-serif text-2xl font-bold text-text-primary">${order.totalAmount.toFixed(2)}</span>
                  </div>
                  
                  <div className="flex gap-2 w-full">
                    {getNextStatus(order.status) && (
                      <Button 
                        className="flex-1 rounded-pill premium shadow-sm"
                        onClick={() => updateMutation.mutate({ id: order.id, status: getNextStatus(order.status) })}
                        disabled={updateMutation.isPending}
                      >
                        Advance to {getNextStatus(order.status).toLowerCase()}
                        <ChevronRight className="ml-1" size={16} />
                      </Button>
                    )}
                    <Button variant="outline" size="icon" className="shrink-0 rounded-full">
                      <Package size={18} />
                    </Button>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        )) : (
          <EmptyState 
            title="All clear" 
            message="There are no active orders to display at the moment. Keep the kitchen prepped!" 
          />
        )}
      </div>
    </div>
  );

}
