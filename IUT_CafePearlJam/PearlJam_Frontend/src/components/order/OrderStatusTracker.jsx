import React from 'react';
import { CheckCircle2, Clock, Truck, ChefHat, PackageCheck, AlertCircle } from 'lucide-react';
import { cn } from '../../lib/utils';
import PropTypes from 'prop-types';

/**
 * OrderStatusTracker: Visual representation of order progress.
 */
export function OrderStatusTracker({ status, updatedAt }) {
  const steps = [
    { id: 'PENDING', label: 'Order Received', icon: Clock, desc: 'Waiting for restaurant confirmation' },
    { id: 'CONFIRMED', label: 'Confirmed', icon: CheckCircle2, desc: 'Restaurant has accepted your order' },
    { id: 'PREPARING', label: 'Preparing', icon: ChefHat, desc: 'Chef is crafting your meal' },
    { id: 'READY', label: 'Ready', icon: PackageCheck, desc: 'Order is packed and ready for pickup' },
    { id: 'DELIVERING', label: 'On the Way', icon: Truck, desc: 'Courier is heading to your location' },
    { id: 'DELIVERED', label: 'Delivered', icon: CheckCircle2, desc: 'Enjoy your meal!' },
  ];

  const currentStepIndex = steps.findIndex(s => s.id === status?.toUpperCase());
  const isCancelled = status?.toUpperCase() === 'CANCELLED';

  if (isCancelled) {
    return (
      <div className="p-8 bg-danger/10 rounded-card flex items-center gap-6 border-2 border-danger/20">
        <div className="w-16 h-16 rounded-full bg-danger text-white flex items-center justify-center">
          <AlertCircle size={32} />
        </div>
        <div className="flex-1">
          <h4 className="headline-md text-danger text-2xl mb-1">Order Cancelled</h4>
          <p className="body-lg text-text-muted">Something went wrong. Please check your email or contact support.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-12 bg-white p-10 rounded-card shadow-sm border border-border/10">
      <div className="flex items-center justify-between border-b border-border/20 pb-8">
        <div>
          <h3 className="headline-md text-3xl mb-1">Tracking Order</h3>
          <p className="label-md font-bold text-accent uppercase tracking-widest">
            Last Update: {new Date(updatedAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
          </p>
        </div>
        <div className="hidden lg:block">
          <div className="flex items-center gap-2 text-text-muted label-md">
            <span className="w-2 h-2 bg-accent rounded-full animate-pulse" />
            Live Tracking Enabled
          </div>
        </div>
      </div>

      <div className="relative">
        <div className="absolute left-7 top-0 bottom-0 w-0.5 bg-border/40" />
        
        <div className="space-y-12">
          {steps.map((step, idx) => {
            const isCompleted = idx < currentStepIndex;
            const isCurrent = idx === currentStepIndex;
            const isFuture = idx > currentStepIndex;

            const Icon = step.icon;

            return (
              <div 
                key={step.id} 
                className={cn(
                  "relative flex items-start gap-8 group pt-1",
                  isFuture && "opacity-40 grayscale-[0.5]"
                )}
              >
                <div className={cn(
                  "w-14 h-14 rounded-full flex items-center justify-center transition-all duration-700 z-10",
                  isCompleted ? "bg-accent text-white shadow-xl scale-110" : 
                  isCurrent ? "bg-accent/10 border-2 border-accent text-accent shadow-accent/20 shadow-md animate-pulse" : 
                  "bg-surface border-2 border-border text-text-muted"
                )}>
                  {isCompleted ? <CheckCircle2 size={24} /> : <Icon size={24} />}
                </div>

                <div className="flex-1">
                  <h4 className={cn(
                    "headline-md text-xl mb-1 transition-colors",
                    isCurrent && "text-accent"
                  )}>
                    {step.label}
                  </h4>
                  <p className="body-lg text-sm text-text-muted max-w-sm">
                    {step.desc}
                  </p>
                </div>

                {isCurrent && (
                  <div className="hidden md:flex flex-col items-end pt-2">
                    <span className="label-md text-accent font-bold animate-bounce uppercase">Active Now</span>
                  </div>
                )}
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

OrderStatusTracker.propTypes = {
  status: PropTypes.string,
  updatedAt: PropTypes.string,
};
