import React from 'react';
import { useParams, Link } from 'react-router-dom';
import { useOrder } from '../hooks/useOrder';
import { OrderStatusTracker } from '../components/order/OrderStatusTracker';
import { OrderSummaryPanel } from '../components/order/OrderSummaryPanel';
import { LoadingSpinner } from '../components/common/LoadingSpinner';
import { EmptyState } from '../components/common/EmptyState';
import { ChevronRight, ArrowLeft, Phone, MapPin, Package } from 'lucide-react';
import { Button } from '../components/ui/Button';

/**
 * OrderTrackingPage: High-fidelity order status visualization with polling.
 */
export function OrderTrackingPage() {
  const { id } = useParams();
  const { data: order, isLoading, error } = useOrder(id);

  if (isLoading) return <LoadingSpinner size={48} className="py-40" />;
  if (error || !order) {
    return (
      <div className="container mx-auto px-6 py-40">
        <EmptyState 
          title="Order Not Found" 
          message="We couldn't track this order. It may have expired or the ID is incorrect."
          onRetry={() => window.location.reload()}
        />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background py-16">
      <div className="container mx-auto px-6">
        <div className="max-w-6xl mx-auto">
          {/* Breadcrumbs */}
          <div className="flex items-center gap-2 mb-10 label-md text-text-muted font-semibold">
            <Link to="/" className="hover:text-accent transition-colors">Home</Link>
            <ChevronRight size={14} />
            <Link to="/dashboard/orders" className="hover:text-accent transition-colors">My Orders</Link>
            <ChevronRight size={14} />
            <span className="text-text-primary">Track Order #{id.slice(-8).toUpperCase()}</span>
          </div>

          <div className="grid lg:grid-cols-12 gap-16 items-start">
            {/* Status Section */}
            <div className="lg:col-span-8 space-y-12">
              <div className="bg-white p-10 rounded-card shadow-sm border border-border/10">
                <div className="flex flex-col md:flex-row md:items-center justify-between gap-6 mb-10 pb-10 border-b border-border/20">
                  <div>
                    <h1 className="headline-md text-4xl mb-2">Order from <span className="text-accent underline decoration-border/50">{order.restaurantName}</span></h1>
                    <p className="label-md text-text-muted uppercase tracking-widest font-bold">Ref: PJ-{id.slice(-12).toUpperCase()}</p>
                  </div>
                  <Button variant="outline" className="rounded-pill px-8 group shrink-0">
                    <Phone size={18} className="mr-2 text-accent" />
                    Help Center
                  </Button>
                </div>

                <div className="grid md:grid-cols-2 gap-12">
                  <div className="space-y-6">
                    <div className="flex items-start gap-4">
                      <div className="w-10 h-10 rounded-full bg-surface flex items-center justify-center text-accent">
                        <MapPin size={20} />
                      </div>
                      <div>
                        <h4 className="label-md font-bold uppercase tracking-widest text-[10px] text-text-muted mb-1">Delivery Address</h4>
                        <p className="body-lg text-sm text-text-primary leading-relaxed font-semibold">
                          {order.deliveryAddress}
                        </p>
                      </div>
                    </div>
                  </div>
                  
                  <div className="space-y-6">
                    <div className="flex items-start gap-4">
                      <div className="w-10 h-10 rounded-full bg-surface flex items-center justify-center text-accent">
                        <Package size={20} />
                      </div>
                      <div>
                        <h4 className="label-md font-bold uppercase tracking-widest text-[10px] text-text-muted mb-1">Items Summary</h4>
                        <p className="body-lg text-sm text-text-primary leading-relaxed font-semibold">
                          {order.items?.length || 0} curated pieces arriving soon
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <OrderStatusTracker status={order.status} updatedAt={order.updatedAt} />

              {order.riderId && (
                <div className="bg-white p-8 rounded-card border border-border/10 shadow-sm flex items-center justify-between">
                  <div className="flex items-center gap-6">
                    <div className="w-16 h-16 rounded-full bg-accent/10 flex items-center justify-center text-accent">
                      <Package size={32} />
                    </div>
                    <div>
                      <h4 className="label-md font-bold uppercase tracking-widest text-[10px] text-text-muted mb-1">Your Delivery Hero</h4>
                      <p className="headline-md text-xl">{order.riderName || 'Rider Assigned'}</p>
                      <p className="text-xs text-text-muted">On their way to deliver your editorial selection.</p>
                    </div>
                  </div>
                  <Button variant="outline" className="rounded-pill px-6">
                    <Phone size={16} className="mr-2" />
                    Contact
                  </Button>
                </div>
              )}

              <div className="p-8 bg-accent/5 border-2 border-accent/10 rounded-card flex items-center justify-between">
                <div>
                  <h4 className="title-md text-accent mb-1 underline decoration-accent/20">The PearlJam Promise</h4>
                  <p className="text-xs text-text-muted font-medium">Your meal is prepared according to our editorial standards of craftsmanship.</p>
                </div>
                <div className="px-6 py-2 bg-white rounded-pill text-[10px] font-bold text-accent uppercase tracking-widest shadow-sm">
                  Verified Preparation
                </div>
              </div>
            </div>

            {/* Summary Sidebar */}
            <div className="lg:col-span-4 sticky top-32">
              <div className="bg-white rounded-card shadow-lg border border-border/10 overflow-hidden">
                <div className="p-6 bg-surface/50 border-b border-border/10">
                  <h3 className="label-md font-bold uppercase tracking-widest">Pricing & Summary</h3>
                </div>
                
                <div className="p-8 space-y-6">
                  {order.items?.map((item, idx) => (
                    <div key={idx} className="flex justify-between items-start">
                      <div className="flex-1">
                        <h5 className="label-md font-bold text-text-primary mb-1">{item.name} × {item.quantity}</h5>
                        <p className="text-[10px] text-text-muted italic">{item.addonSummary}</p>
                      </div>
                      <span className="label-md font-bold">${(item.price * item.quantity).toFixed(2)}</span>
                    </div>
                  ))}
                  
                  <div className="pt-6 border-t border-border/20 space-y-3">
                    <div className="flex justify-between label-md text-text-muted">
                      <span>Subtotal</span>
                      <span>${order.subtotal.toFixed(2)}</span>
                    </div>
                    {order.discount > 0 && (
                      <div className="flex justify-between label-md text-accent font-bold">
                        <span>Editorial Discount</span>
                        <span>-${order.discount.toFixed(2)}</span>
                      </div>
                    )}
                    <div className="flex justify-between label-md text-text-muted">
                      <span>Delivery Fee</span>
                      <span>$5.00</span>
                    </div>
                    <div className="flex justify-between pt-4 border-t border-border/50">
                      <span className="headline-md text-xl">Total</span>
                      <span className="headline-md text-xl text-accent font-black">${order.totalAmount.toFixed(2)}</span>
                    </div>
                  </div>
                </div>
              </div>

              <div className="mt-8 flex justify-center">
                <Link to="/" className="flex items-center gap-2 text-accent font-bold label-md hover:underline group">
                  <ArrowLeft size={16} className="group-hover:-translate-x-1 transition-transform" />
                  Return Home
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
