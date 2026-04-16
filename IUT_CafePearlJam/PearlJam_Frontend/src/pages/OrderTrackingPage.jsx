import React from 'react';
import { useParams, Link } from 'react-router-dom';
import { useOrder } from '../hooks/useOrder';
import { OrderStatusTracker } from '../components/order/OrderStatusTracker';
import { LoadingSpinner } from '../components/common/LoadingSpinner';
import { EmptyState } from '../components/common/EmptyState';
import { ChevronRight, ArrowLeft } from 'lucide-react';
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
                    <h1 className="headline-md text-4xl mb-2">Order Tracking</h1>
                    <p className="label-md text-text-muted uppercase tracking-widest font-bold">Ref: PJ-{id.slice(-12).toUpperCase()}</p>
                  </div>
                  <Button variant="outline" className="rounded-pill px-8 group shrink-0" onClick={() => window.location.reload()}>
                    Refresh
                  </Button>
                </div>
              </div>

              <OrderStatusTracker status={order.status} updatedAt={order.updatedAt} />
            </div>

            {/* Summary Sidebar */}
            <div className="lg:col-span-4 sticky top-32">
              <div className="bg-white rounded-card shadow-lg border border-border/10 overflow-hidden">
                <div className="p-6 bg-surface/50 border-b border-border/10">
                  <h3 className="label-md font-bold uppercase tracking-widest">Pricing & Summary</h3>
                </div>
                
                <div className="p-8 space-y-6">
                  <p className="text-sm text-text-muted">Live status is sourced from `GET /api/orders/{id}/status`.</p>
                  
                  <div className="pt-6 border-t border-border/20 space-y-3">
                    <div className="flex justify-between pt-4 border-t border-border/50">
                      <span className="headline-md text-xl">Current Status</span>
                      <span className="headline-md text-xl text-accent font-black">{order.status}</span>
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
