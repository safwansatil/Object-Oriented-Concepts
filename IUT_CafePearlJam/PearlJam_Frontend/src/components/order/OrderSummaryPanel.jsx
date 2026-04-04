import React from 'react';
import { useCartStore } from '../../store/cartStore';
import { Trash2, Plus, Minus } from 'lucide-react';
import { Button } from '../ui/Button';
import { cn } from '../../lib/utils';
import PropTypes from 'prop-types';

/**
 * OrderSummaryPanel: A detailed list of cart items and total calculations.
 */
export function OrderSummaryPanel({ showItems = true, isCheckout = false }) {
  const { items, restaurantName, removeItem, updateQuantity, getSubtotal } = useCartStore();
  const subtotal = getSubtotal();
  const deliveryFee = 5.00;
  const total = subtotal + deliveryFee;

  if (items.length === 0) {
    return (
      <div className="p-8 text-center bg-white rounded-card shadow-sm">
        <p className="body-lg text-text-muted mb-4">Your cart is empty.</p>
        <Button asChild variant="outline" className="w-full">
          <a href="/restaurants">Browse Restaurants</a>
        </Button>
      </div>
    );
  }

  return (
    <div className={cn(
      "bg-white rounded-card shadow-sm flex flex-col",
      isCheckout ? "border border-border/30" : "sticky top-28"
    )}>
      <div className="p-6 border-b border-border/10">
        <h3 className="headline-md text-xl mb-1">Your Order</h3>
        {restaurantName && (
          <p className="label-md font-bold text-accent uppercase tracking-widest">{restaurantName}</p>
        )}
      </div>

      {showItems && (
        <div className="flex-1 overflow-y-auto max-h-[400px] p-6 space-y-6">
          {items.map((item, idx) => (
            <div key={`${item.menuItemId}-${idx}`} className="flex gap-4 group">
              <div className="w-16 h-16 rounded-card overflow-hidden bg-surface flex-shrink-0">
                <img 
                  src={item.imageUrl} 
                  alt={item.name} 
                  className="w-full h-full object-cover"
                />
              </div>
              
              <div className="flex-1">
                <div className="flex items-start justify-between mb-1">
                  <h4 className="label-md font-bold text-text-primary">{item.name}</h4>
                  <span className="label-md font-bold text-text-primary">
                    ${((item.price + item.selectedAddons.reduce((s, a) => s + a.price, 0)) * item.quantity).toFixed(2)}
                  </span>
                </div>
                
                {item.selectedAddons.length > 0 && (
                  <p className="text-[10px] text-text-muted mb-3 italic">
                    {item.selectedAddons.map(a => a.name).join(', ')}
                  </p>
                )}
                
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-3 bg-surface rounded-pill p-0.5">
                    <button 
                      onClick={() => updateQuantity(item.menuItemId, item.selectedAddons, item.quantity - 1)}
                      className="w-6 h-6 flex items-center justify-center rounded-full hover:bg-white transition-colors"
                    >
                      <Minus size={12} />
                    </button>
                    <span className="text-xs font-bold w-4 text-center">{item.quantity}</span>
                    <button 
                      onClick={() => updateQuantity(item.menuItemId, item.selectedAddons, item.quantity + 1)}
                      className="w-6 h-6 flex items-center justify-center rounded-full hover:bg-white transition-colors"
                    >
                      <Plus size={12} />
                    </button>
                  </div>
                  
                  <button 
                    onClick={() => removeItem(item.menuItemId, item.selectedAddons)}
                    className="p-1.5 text-text-muted hover:text-danger hover:bg-danger/5 rounded-full transition-all"
                  >
                    <Trash2 size={14} />
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="p-8 bg-surface/30 rounded-b-card border-t border-border/10">
        <div className="space-y-3 mb-6">
          <div className="flex justify-between label-md">
            <span className="text-text-muted">Subtotal</span>
            <span className="font-bold">${subtotal.toFixed(2)}</span>
          </div>
          <div className="flex justify-between label-md">
            <span className="text-text-muted">Delivery Fee</span>
            <span className="font-bold">${deliveryFee.toFixed(2)}</span>
          </div>
          <div className="flex justify-between pt-4 border-t border-border/50">
            <span className="headline-md text-lg">Total</span>
            <span className="headline-md text-lg text-accent">${total.toFixed(2)}</span>
          </div>
        </div>
        
        {!isCheckout && (
          <Button 
            className="w-full premium" 
            size="lg"
            asChild
          >
            <a href="/checkout">Proceed to Checkout</a>
          </Button>
        )}
      </div>
    </div>
  );
}

OrderSummaryPanel.propTypes = {
  showItems: PropTypes.bool,
  isCheckout: PropTypes.bool,
};
