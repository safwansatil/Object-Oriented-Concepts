import React from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { StatusBadge } from '../../components/common/StatusBadge';
import { LoadingSpinner } from '../../components/common/LoadingSpinner';
import { EmptyState } from '../../components/common/EmptyState';
import { Card, CardContent } from '../../components/ui/Card';
import { Button } from '../../components/ui/Button';
import { MapPin, Clock, Navigation, CheckCircle2, Package, User } from 'lucide-react';
import { getAvailableOrders, claimOrder, updateOrderStatus, getMyOrders } from '../../api/orders';
import { useAuth } from '../../hooks/useAuth';
// import { toast } from 'sonner';

/**
 * RiderDashboard: Delivery partner order management.
 */
export function RiderDashboard() {
  const queryClient = useQueryClient();
  const { user } = useAuth();
  
  // 1. Fetch orders available for pickup
  const { data: availableOrders, isLoading: loadingAvailable } = useQuery({
    queryKey: ['available-orders'],
    queryFn: getAvailableOrders,
  });

  // 2. Fetch orders assigned to this rider
  const { data: myDeliveries, isLoading: loadingMy } = useQuery({
    queryKey: ['my-deliveries'],
    queryFn: getMyOrders, // Backend OrderDAO.findByRiderId needs to be used here
  });

  const claimMutation = useMutation({
    mutationFn: (orderId) => claimOrder(orderId, user.id),
    onSuccess: () => {
      console.log('Order claimed successfully!');
      queryClient.invalidateQueries({ queryKey: ['available-orders'] });
      queryClient.invalidateQueries({ queryKey: ['my-deliveries'] });
    },
    onError: (err) => console.error(err.message),
  });

  const statusMutation = useMutation({
    mutationFn: ({ id, status }) => updateOrderStatus(id, status),
    onSuccess: () => {
      console.log('Status updated');
      queryClient.invalidateQueries({ queryKey: ['my-deliveries'] });
    },
  });

  if (loadingAvailable || loadingMy) return <LoadingSpinner size={48} className="py-40" />;

  const activeDeliveries = myDeliveries?.filter(o => o.status !== 'DELIVERED' && o.status !== 'CANCELLED') || [];

  return (
    <div className="space-y-12 animate-in fade-in slide-in-from-bottom-4 duration-500">
      <div>
        <h1 className="headline-md text-4xl mb-1 text-text-primary">Delivery Hero Dashboard</h1>
        <p className="body-lg text-sm text-text-muted italic">Supporting the culinary ecosystem, one hand-off at a time.</p>
      </div>

      {/* Section: Active Deliveries */}
      <section className="space-y-6">
        <div className="flex items-center gap-3 mb-4">
          <div className="w-8 h-8 rounded-full bg-accent/10 flex items-center justify-center text-accent">
            <Navigation size={16} />
          </div>
          <h2 className="headline-sm text-xl font-bold">Your Active Task</h2>
        </div>

        {activeDeliveries.length > 0 ? activeDeliveries.map((order) => (
          <Card key={order.id} className="overflow-hidden border-l-4 border-l-accent shadow-xl">
            <CardContent className="p-0">
              <div className="grid md:grid-cols-12">
                <div className="md:col-span-8 p-8 space-y-6">
                  <div className="flex items-center justify-between">
                    <span className="label-md font-bold text-[10px] uppercase tracking-widest text-accent">Active Delivery</span>
                    <StatusBadge status={order.status} />
                  </div>
                  
                  <div className="grid grid-cols-2 gap-8">
                    <div>
                      <h4 className="label-md font-bold text-[10px] uppercase text-text-muted mb-2">Merchant</h4>
                      <p className="body-lg text-sm font-bold">{order.restaurantName || "Artisanal Kitchen"}</p>
                    </div>
                    <div>
                      <h4 className="label-md font-bold text-[10px] uppercase text-text-muted mb-2">Destination</h4>
                      <p className="body-lg text-sm font-medium text-text-primary flex items-start gap-1">
                        <MapPin size={14} className="mt-1 shrink-0 text-accent" />
                        {order.deliveryAddress}
                      </p>
                    </div>
                  </div>
                </div>

                <div className="md:col-span-4 p-8 bg-surface/5 flex flex-col justify-center gap-4 border-l border-border/10">
                  {order.status === 'READY_FOR_PICKUP' && (
                    <Button 
                      className="w-full premium rounded-pill h-12"
                      onClick={() => statusMutation.mutate({ id: order.id, status: 'OUT_FOR_DELIVERY' })}
                      disabled={statusMutation.isPending}
                    >
                      Start Delivery
                    </Button>
                  )}
                  {order.status === 'OUT_FOR_DELIVERY' && (
                    <Button 
                      className="w-full bg-green-600 hover:bg-green-700 text-white rounded-pill h-12"
                      onClick={() => statusMutation.mutate({ id: order.id, status: 'DELIVERED' })}
                      disabled={statusMutation.isPending}
                    >
                      Complete Delivery
                      <CheckCircle2 className="ml-2" size={18} />
                    </Button>
                  )}
                  <Button variant="outline" className="w-full rounded-pill h-12 border-border/20">
                    Order Details
                  </Button>
                </div>
              </div>
            </CardContent>
          </Card>
        )) : (
          <EmptyState 
            title="No Active Task" 
            message="You are currently not on a delivery. Claim an order below to start earning."
            icon={Package}
          />
        )}
      </section>

      {/* Section: Available Orders */}
      <section className="space-y-6">
        <div className="flex items-center gap-3 mb-4">
          <div className="w-8 h-8 rounded-full bg-surface-variant flex items-center justify-center text-text-muted">
            <Clock size={16} />
          </div>
          <h2 className="headline-sm text-xl font-bold">New Opportunities</h2>
        </div>

        <div className="grid md:grid-cols-2 gap-6">
          {availableOrders && availableOrders.length > 0 ? availableOrders.map((order) => (
            <Card key={order.id} className="hover:shadow-2xl transition-all duration-300 border border-border/5">
              <CardContent className="p-6 space-y-6">
                <div className="flex justify-between items-start">
                  <div>
                    <h3 className="font-serif text-lg font-bold">PJ-{order.id.slice(-6).toUpperCase()}</h3>
                    <p className="text-[10px] text-text-muted font-bold uppercase tracking-widest">{order.deliveryArea}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-2xl font-serif font-bold text-text-primary font-bold">${order.total.toFixed(2)}</p>
                    <p className="text-[9px] text-accent font-bold uppercase">Estimated Fee: $4.50</p>
                  </div>
                </div>

                <div className="space-y-3">
                  <div className="flex items-center gap-2 text-xs text-text-muted font-medium">
                    <MapPin size={14} className="text-accent" />
                    {order.deliveryAddress}
                  </div>
                </div>

                <Button 
                  className="w-full premium rounded-pill h-12"
                  onClick={() => claimMutation.mutate(order.id)}
                  disabled={claimMutation.isPending}
                >
                  Claim This Delivery
                </Button>
              </CardContent>
            </Card>
          )) : (
            <div className="col-span-2">
              <EmptyState 
                title="Waiting for Orders" 
                message="The digital greenhouse is quiet. New orders will appear here as soon as they are confirmed." 
                icon={Clock}
              />
            </div>
          )}
        </div>
      </section>
    </div>
  );
}
