import React, { useState, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { useRestaurant } from '../hooks/useRestaurants';
import { useMenu } from '../hooks/useMenu';
import { useCartStore } from '../store/cartStore';
import { MenuItemCard } from '../components/menu/MenuItemCard';
import { AddonSelector } from '../components/menu/AddonSelector';
import { OrderSummaryPanel } from '../components/order/OrderSummaryPanel';
import { LoadingSpinner } from '../components/common/LoadingSpinner';
import { EmptyState } from '../components/common/EmptyState';
import { Star, Clock, Info, ArrowLeft, ChevronRight } from 'lucide-react';
import { cn } from '../lib/utils';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter, DialogDescription } from '../components/ui/Dialog';
import { Badge } from '../components/ui/Badge';

/**
 * RestaurantDetailPage: Detailed view of a restaurant with its menu.
 */
export function RestaurantDetailPage() {
  const { id } = useParams();
  const { data: restaurant, isLoading: isResLoading } = useRestaurant(id);
  const { data: menuByCategory = {}, isLoading: isMenuLoading } = useMenu(id);
  const { addItem, restaurantId: cartResId, clearCart } = useCartStore();

  const [activeCategory, setActiveCategory] = useState('');
  const [selectedItem, setSelectedItem] = useState(null);
  const [showAddonSelector, setShowAddonSelector] = useState(false);
  const [showCartClearDialog, setShowCartClearDialog] = useState(false);

  const categoryRefs = useRef({});

  // Intersection Observer for scrollspy
  useEffect(() => {
    if (!menuByCategory) return;

    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            setActiveCategory(entry.target.id);
          }
        });
      },
      { rootMargin: '-10% 0px -80% 0px' }
    );

    Object.values(categoryRefs.current).forEach((ref) => {
      if (ref) observer.observe(ref);
    });

    return () => observer.disconnect();
  }, [menuByCategory]);

  const handleAddItem = (item) => {
    if (cartResId && cartResId !== id) {
      setSelectedItem(item);
      setShowCartClearDialog(true);
      return;
    }
    setSelectedItem(item);
    setShowAddonSelector(true);
  };

  const onConfirmAdd = (itemWithAddons) => {
    addItem({
      ...itemWithAddons,
      restaurantId: id,
      restaurantName: restaurant.name,
      menuItemId: itemWithAddons.id,
    });
  };

  const scrollToCategory = (cat) => {
    setActiveCategory(cat);
    const element = document.getElementById(cat);
    if (element) {
      window.scrollTo({
        top: element.offsetTop - 120, // Adjust for sticky header
        behavior: 'smooth',
      });
    }
  };

  if (isResLoading || isMenuLoading) return <LoadingSpinner size={48} className="py-40" />;
  if (!restaurant) return <EmptyState title="Restaurant Not Found" message="The requested kitchen is currently unavailable." />;

  return (
    <div className="min-h-screen bg-background pb-20">
      {/* Hero Header */}
      <section className="relative h-[45vh] overflow-hidden">
        <img 
          src={restaurant.imageUrl || 'https://images.unsplash.com/photo-1514362545857-3bc16c4c7d1b?auto=format&fit=crop&q=80&w=2000'} 
          alt={restaurant.name}
          className="w-full h-full object-cover grayscale-[0.2]"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent" />
        <div className="absolute bottom-0 left-0 w-full p-8 md:p-16">
          <div className="container mx-auto">
            <div className="flex items-center gap-4 mb-4">
              <Badge className="bg-accent text-white border-none px-4 py-1 font-bold">
                {restaurant.cuisineType}
              </Badge>
              <div className="flex items-center gap-1 text-white font-bold bg-black/40 px-3 py-1 rounded-pill backdrop-blur-md">
                <Star size={16} fill="#FACC15" className="text-[#FACC15]" />
                {restaurant.rating || 'New'}
              </div>
            </div>
            <h1 className="display-lg text-white mb-4 leading-tight">{restaurant.name}</h1>
            <div className="flex flex-wrap items-center gap-8 text-white/90 label-md">
              <div className="flex items-center gap-2">
                <Clock size={18} />
                <span>25-40 min delivery</span>
              </div>
              <div className="flex items-center gap-2">
                <Info size={18} />
                <span>Min. order $15.00</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Sticky Category Tabs */}
      <div className="sticky top-20 z-40 bg-white/90 backdrop-blur-md border-b border-border/10 py-4 shadow-sm overflow-x-auto no-scrollbar">
        <div className="container mx-auto px-6 flex items-center gap-4">
          <div className="flex items-center gap-8 pr-8 border-r border-border/20 shrink-0">
            {Object.keys(menuByCategory).map((cat) => (
              <button
                key={cat}
                onClick={() => scrollToCategory(cat)}
                className={cn(
                  "label-md font-bold uppercase tracking-widest transition-all",
                  activeCategory === cat ? "text-accent border-b-2 border-accent pb-2" : "text-text-muted hover:text-text-primary"
                )}
              >
                {cat}
              </button>
            ))}
          </div>
          <p className="hidden lg:block text-xs font-bold text-accent italic shrink-0">
            Freshness Prepared Daily
          </p>
        </div>
      </div>

      <div className="container mx-auto px-6 py-12">
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-start">
          {/* Menu Sections */}
          <div className="lg:col-span-8 space-y-20">
            {Object.entries(menuByCategory).map(([cat, items]) => (
              <section 
                key={cat} 
                id={cat}
                ref={(el) => (categoryRefs.current[cat] = el)}
                className="scroll-mt-32"
              >
                <div className="flex items-center gap-4 mb-8">
                  <h2 className="headline-md text-3xl">{cat}</h2>
                  <div className="flex-1 h-[1px] bg-border/20" />
                </div>
                <div className="grid md:grid-cols-2 gap-6">
                  {items.map((item) => (
                    <MenuItemCard 
                      key={item.id} 
                      item={item} 
                      onAdd={handleAddItem} 
                    />
                  ))}
                </div>
              </section>
            ))}
          </div>

          {/* Sticky Cart Sidebar */}
          <div className="lg:col-span-4 lg:block">
            <OrderSummaryPanel />
          </div>
        </div>
      </div>

      {/* Addon Selector Modal */}
      {selectedItem && (
        <AddonSelector 
          item={selectedItem}
          isOpen={showAddonSelector}
          onOpenChange={setShowAddonSelector}
          onConfirm={onConfirmAdd}
        />
      )}

      {/* Cart Clear Confirmation Modal */}
      {showCartClearDialog && (
        <Dialog open={showCartClearDialog} onOpenChange={setShowCartClearDialog}>
          <DialogContent className="sm:max-w-md p-10">
            <DialogHeader className="text-center">
              <div className="mx-auto w-16 h-16 rounded-full bg-danger/10 text-danger flex items-center justify-center mb-6">
                <ArrowLeft size={32} />
              </div>
              <DialogTitle className="headline-md text-2xl mb-4">Start New Cart?</DialogTitle>
              <DialogDescription className="body-lg text-sm text-text-muted">
                You already have items from another restaurant. Adding this will clear your current cart.
              </DialogDescription>
            </DialogHeader>
            <DialogFooter className="flex flex-col sm:flex-row gap-4 mt-8">
              <Button variant="outline" className="w-full" onClick={() => setShowCartClearDialog(false)}>
                Go Back
              </Button>
              <Button 
                variant="destructive" 
                className="w-full"
                onClick={() => {
                  clearCart();
                  setShowCartClearDialog(false);
                  setShowAddonSelector(true);
                }}
              >
                Clear & Add Item
              </Button>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      )}
    </div>
  );

}
