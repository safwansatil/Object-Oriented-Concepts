import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCartStore } from '../store/cartStore';
import { useAuth } from '../hooks/useAuth';
import { placeOrder } from '../api/orders';
import { validateCoupon } from '../api/coupons';
import { OrderSummaryPanel } from '../components/order/OrderSummaryPanel';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';
import { Label } from '../components/ui/Label'; // Need to create this or use a simple one
import { MapPin, CreditCard, Tag, ArrowRight, ShieldCheck, Truck } from 'lucide-react';
import { cn } from '../lib/utils';
import { useMutation } from '@tanstack/react-query';
import { Badge } from '../components/ui/Badge';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter, DialogDescription } from '../components/ui/Dialog';

/**
 * CheckoutPage: Order confirmation and payment flow.
 */
export function CheckoutPage() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const { items, restaurantId, getSubtotal, clearCart } = useCartStore();
  
  const [address, setAddress] = useState('');
  const [couponCode, setCouponCode] = useState('');
  const [appliedCoupon, setAppliedCoupon] = useState(null);
  const [couponError, setCouponError] = useState('');

  const subtotal = getSubtotal();
  const deliveryFee = 5.00;
  const discount = appliedCoupon ? (subtotal * appliedCoupon.discountPercent / 100) : 0;
  const total = subtotal + deliveryFee - discount;

  const orderMutation = useMutation({
    mutationFn: (orderData) => placeOrder(orderData),
    onSuccess: (data) => {
      clearCart();
      navigate(`/order/${data.id}`);
    },
  });

  const couponMutation = useMutation({
    mutationFn: (code) => validateCoupon(code, subtotal),
    onSuccess: (data) => {
      setAppliedCoupon(data);
      setCouponError('');
    },
    onError: (err) => {
      setCouponError(err.message || 'Invalid coupon code');
      setAppliedCoupon(null);
    },
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!address.trim()) return;

    const orderData = {
      customerId: user.id,
      restaurantId,
      items: items.map(item => ({
        menuItemId: item.menuItemId,
        quantity: item.quantity,
        addonIds: item.selectedAddons.map(a => a.id),
      })),
      deliveryAddress: address,
      couponCode: appliedCoupon?.code,
      paymentMethod: 'CREDIT_CARD', // Hardcoded for demo
    };

    orderMutation.mutate(orderData);
  };

  if (items.length === 0) {
    return (
      <div className="container mx-auto px-6 py-40 text-center">
        <h1 className="headline-md mb-4">Your cart is empty</h1>
        <Button onClick={() => navigate('/restaurants')}>Browse Restaurants</Button>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background py-20">
      <div className="container mx-auto px-6">
        <div className="max-w-6xl mx-auto grid lg:grid-cols-12 gap-16 items-start">
          
          {/* Main Checkout Form */}
          <div className="lg:col-span-7 space-y-12">
            <section>
              <h1 className="display-lg text-4xl mb-2">Check Out</h1>
              <p className="body-lg text-sm text-text-muted">Confirm your editorial culinary experience.</p>
            </section>

            {/* Delivery Details */}
            <div className="bg-white p-10 rounded-card shadow-sm space-y-8 border border-border/10">
              <div className="flex items-center gap-4 mb-2">
                <div className="w-10 h-10 rounded-full bg-accent text-white flex items-center justify-center">
                  <MapPin size={20} />
                </div>
                <h3 className="headline-md text-2xl">Delivery Details</h3>
              </div>

              <div className="space-y-4">
                <Label className="label-md font-bold uppercase tracking-widest text-[10px]">Recipient Name</Label>
                <Input value={user?.name || ''} disabled className="bg-surface/30 cursor-not-allowed" />
              </div>

              <div className="space-y-4">
                <Label className="label-md font-bold uppercase tracking-widest text-[10px]">Delivery Address</Label>
                <textarea 
                  className="w-full min-h-[120px] rounded-input border border-border/20 bg-white p-4 text-sm focus:ring-2 focus:ring-accent outline-none"
                  placeholder="Enter your full street address, apartment number, and city..."
                  value={address}
                  onChange={(e) => setAddress(e.target.value)}
                  required
                />
              </div>
            </div>

            {/* Payment Method */}
            <div className="bg-white p-10 rounded-card shadow-sm space-y-8 border border-border/10">
              <div className="flex items-center gap-4 mb-2">
                <div className="w-10 h-10 rounded-full bg-accent text-white flex items-center justify-center">
                  <CreditCard size={20} />
                </div>
                <h3 className="headline-md text-2xl">Payment</h3>
              </div>

              <div className="p-6 border-2 border-accent rounded-card bg-accent/5 flex items-center justify-between">
                <div className="flex items-center gap-4">
                  <div className="w-12 h-8 bg-text-primary rounded-sm flex items-center justify-center text-white font-bold italic text-[10px]">
                    VISA
                  </div>
                  <div>
                    <p className="label-md font-bold text-text-primary">Ending in 4242</p>
                    <p className="text-[10px] text-text-muted">Expires 12/28</p>
                  </div>
                </div>
                <Badge className="bg-accent/10 text-accent border-none font-bold">DEFAULT</Badge>
              </div>
              
              <div className="flex items-center gap-2 text-[10px] text-text-muted justify-center">
                <ShieldCheck size={14} className="text-accent" />
                Secure Editorial Encryption Active
              </div>
            </div>
          </div>

          {/* Checkout Summary Card */}
          <div className="lg:col-span-5 space-y-8">
            <div className="sticky top-32 space-y-8">
              <div className="bg-white rounded-card shadow-sm border border-border/10 overflow-hidden">
                <OrderSummaryPanel showItems={true} isCheckout={true} />
                
                {/* Coupon Input */}
                <div className="p-8 border-t border-border/10">
                  <div className="flex items-center gap-4 mb-4">
                    <Tag size={20} className="text-accent" />
                    <span className="label-md font-bold uppercase tracking-widest text-xs">Promo Code</span>
                  </div>
                  
                  <div className="flex gap-2">
                    <Input 
                      placeholder="ENTER CODE" 
                      className="uppercase tracking-widest text-xs font-bold"
                      value={couponCode}
                      onChange={(e) => setCouponCode(e.target.value)}
                    />
                    <Button 
                      variant="outline" 
                      onClick={() => couponMutation.mutate(couponCode)}
                      disabled={!couponCode || couponMutation.isPending}
                    >
                      {couponMutation.isPending ? '...' : 'Apply'}
                    </Button>
                  </div>
                  
                  {couponError && <p className="mt-2 text-xs text-danger font-medium">{couponError}</p>}
                  {appliedCoupon && (
                    <p className="mt-2 text-xs text-accent font-bold">
                      SUCCESS! {appliedCoupon.discountPercent}% OFF APPLIED
                    </p>
                  )}
                </div>

                <div className="p-8 bg-accent text-white group">
                  <Button 
                    className="w-full bg-white text-accent hover:bg-white/90 rounded-pill h-16 text-lg font-bold shadow-xl transition-all group-active:scale-95"
                    disabled={!address.trim() || orderMutation.isPending}
                    onClick={handleSubmit}
                  >
                    {orderMutation.isPending ? (
                      'Placing Order...'
                    ) : (
                      <>
                        Place Order — ${total.toFixed(2)}
                        <ArrowRight className="ml-2 group-hover:translate-x-1 transition-transform" />
                      </>
                    )}
                  </Button>
                  <p className="text-[10px] text-center mt-6 text-white/60 tracking-wider">
                    By placing your order, you agree to the PearlJam Terms of Craftsmanship.
                  </p>
                </div>
              </div>

              <div className="flex items-center gap-4 p-6 bg-surface/30 rounded-card border border-border/10">
                <Truck size={24} className="text-accent shrink-0" />
                <p className="text-xs text-text-muted leading-relaxed">
                  Your order will be handled with editorial precision. Estimated hand-off to courier in <span className="font-bold text-text-primary">15-20 min</span>.
                </p>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  );

}
