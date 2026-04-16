import React, { useState } from 'react';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter, DialogDescription } from '../ui/Dialog';
import { Button } from '../ui/Button';
import { ShoppingCart, Plus, Minus } from 'lucide-react';
import { cn } from '../../lib/utils';
import PropTypes from 'prop-types';

/**
 * AddonSelector: Modal to select addons and quantity for a menu item.
 */
export function AddonSelector({ item, isOpen, onOpenChange, onConfirm }) {
  const [selectedAddons, setSelectedAddons] = useState([]);
  const [quantity, setQuantity] = useState(1);

  const toggleAddon = (addon) => {
    setSelectedAddons(prev => 
      prev.find(a => a.id === addon.id)
        ? prev.filter(a => a.id !== addon.id)
        : [...prev, addon]
    );
  };

  const totalPrice = (Number(item.basePrice || 0) + selectedAddons.reduce((sum, a) => sum + (a.extraPrice || 0), 0)) * quantity;

  const handleConfirm = () => {
    onConfirm({
      ...item,
      selectedAddons,
      quantity,
    });
    onOpenChange(false);
  };

  if (!item) return null;

  return (
    <Dialog open={isOpen} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[480px] p-0 overflow-hidden">
        <div className="h-48 relative">
          <img 
            src={item.imageUrl} 
            alt={item.name} 
            className="w-full h-full object-cover"
          />
          <div className="absolute inset-0 bg-black/20" />
        </div>
        
        <div className="p-8">
          <DialogHeader className="text-left mb-6">
            <div className="flex items-center justify-between">
              <DialogTitle className="headline-md mb-2">{item.name}</DialogTitle>
              <span className="font-serif text-2xl font-bold text-accent">
                ${Number(item.basePrice || 0).toFixed(2)}
              </span>
            </div>
            <DialogDescription className="body-lg text-sm">
              {item.description}
            </DialogDescription>
          </DialogHeader>

          {item.addons && item.addons.length > 0 && (
            <div className="mb-8">
              <h5 className="label-md font-bold uppercase tracking-widest text-text-muted mb-4 pb-2 border-b border-border/50">
                Customise Your Order
              </h5>
              <div className="space-y-4">
                {item.addons.map((addon) => (
                  <div 
                    key={addon.id} 
                    className="flex items-center justify-between group cursor-pointer"
                    onClick={() => toggleAddon(addon)}
                  >
                    <div className="flex items-center gap-3">
                      <div className={cn(
                        "w-5 h-5 rounded-sm border-2 transition-all flex items-center justify-center",
                        selectedAddons.find(a => a.id === addon.id)
                          ? "bg-accent border-accent"
                          : "border-border group-hover:border-accent/40"
                      )}>
                        {selectedAddons.find(a => a.id === addon.id) && (
                          <div className="w-2.5 h-2.5 bg-white rounded-[1px]" />
                        )}
                      </div>
                      <span className="label-md font-semibold text-text-primary">{addon.name}</span>
                    </div>
                    <span className="label-md text-text-muted">
                  +${Number(addon.extraPrice || 0).toFixed(2)}
                    </span>
                  </div>
                ))}
              </div>
            </div>
          )}

          <div className="flex items-center justify-between pt-6 border-t border-border/20">
            <div className="flex items-center bg-surface rounded-pill p-1">
              <button 
                onClick={() => setQuantity(prev => Math.max(1, prev - 1))}
                className="w-10 h-10 flex items-center justify-center rounded-full hover:bg-white transition-colors"
              >
                <Minus size={16} />
              </button>
              <span className="w-12 text-center font-bold text-lg">{quantity}</span>
              <button 
                onClick={() => setQuantity(prev => prev + 1)}
                className="w-10 h-10 flex items-center justify-center rounded-full hover:bg-white transition-colors"
              >
                <Plus size={16} />
              </button>
            </div>
            
            <Button 
              size="lg" 
              className="flex-1 ml-6 rounded-pill premium"
              onClick={handleConfirm}
            >
              Add to Cart — ${totalPrice.toFixed(2)}
            </Button>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}

AddonSelector.propTypes = {
  item: PropTypes.object,
  isOpen: PropTypes.bool.isRequired,
  onOpenChange: PropTypes.func.isRequired,
  onConfirm: PropTypes.func.isRequired,
};
