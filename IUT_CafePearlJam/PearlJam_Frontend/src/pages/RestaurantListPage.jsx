import React, { useMemo } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useRestaurants } from '../hooks/useRestaurants';
import { RestaurantCard } from '../components/restaurant/RestaurantCard';
import { LoadingSpinner } from '../components/common/LoadingSpinner';
import { EmptyState } from '../components/common/EmptyState';
import { Search, Filter, SlidersHorizontal } from 'lucide-react';
import { Input } from '../components/ui/Input';
import { Button } from '../components/ui/Button';
import { Badge } from '../components/ui/Badge';
import { cn } from '../lib/utils';

/**
 * RestaurantListPage: Filterable grid of restaurants.
 */
export function RestaurantListPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const area = searchParams.get('area') || '';
  const cuisine = searchParams.get('cuisine') || '';
  const searchQuery = searchParams.get('q') || '';

  const { data: restaurants, isLoading, error, refetch } = useRestaurants(area, cuisine);

  const cuisines = ['Italian', 'Japanese', 'French', 'Healthy', 'Artisanal', 'Vegan', 'Mexican'];

  const filteredRestaurants = useMemo(() => {
    if (!restaurants) return [];
    return restaurants.filter(r => 
      r.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      r.cuisineType.toLowerCase().includes(searchQuery.toLowerCase())
    );
  }, [restaurants, searchQuery]);

  const toggleCuisine = (c) => {
    const newParams = new URLSearchParams(searchParams);
    if (cuisine === c) {
      newParams.delete('cuisine');
    } else {
      newParams.set('cuisine', c);
    }
    setSearchParams(newParams);
  };

  if (error) {
    return (
      <div className="container mx-auto px-6 py-20">
        <EmptyState 
          title="Couldn't load restaurants" 
          message={error.message} 
          onRetry={refetch} 
        />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background">
      {/* Header / Search Area */}
      <section className="bg-white border-b border-border/10 py-12">
        <div className="container mx-auto px-6">
          <div className="flex flex-col md:flex-row md:items-end justify-between gap-8 mb-12">
            <div>
              <h1 className="headline-md text-4xl mb-2">Restaurants in {area || 'your area'}</h1>
              <p className="body-lg text-sm">Discover {filteredRestaurants.length} editorial culinary destinations.</p>
            </div>
            
            <div className="relative w-full max-w-md">
              <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-text-muted" size={20} />
              <Input 
                placeholder="Search by name or cuisine..." 
                className="pl-12 bg-surface/50 border-none rounded-pill h-12"
                value={searchQuery}
                onChange={(e) => {
                  const newParams = new URLSearchParams(searchParams);
                  if (e.target.value) newParams.set('q', e.target.value);
                  else newParams.delete('q');
                  setSearchParams(newParams);
                }}
              />
            </div>
          </div>

          <div className="flex items-center gap-4 overflow-x-auto pb-4 no-scrollbar">
            <div className="flex items-center gap-2 pr-4 border-r border-border/30 shrink-0">
              <SlidersHorizontal size={18} className="text-accent" />
              <span className="label-md font-bold text-xs uppercase tracking-widest">Cuisines</span>
            </div>
            {cuisines.map((c) => (
              <button
                key={c}
                onClick={() => toggleCuisine(c)}
                className={cn(
                  "px-6 py-2 rounded-pill label-md font-semibold transition-all shrink-0",
                  cuisine === c 
                    ? "bg-accent text-white shadow-md shadow-accent/20" 
                    : "bg-surface text-text-muted hover:bg-border/30 hover:text-text-primary"
                )}
              >
                {c}
              </button>
            ))}
          </div>
        </div>
      </section>

      {/* Main Grid */}
      <section className="container mx-auto px-6 py-16">
        {isLoading ? (
          <div className="py-20">
            <LoadingSpinner size={48} />
          </div>
        ) : filteredRestaurants.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
            {filteredRestaurants.map((r) => (
              <RestaurantCard key={r.id} restaurant={r} />
            ))}
          </div>
        ) : (
          <div className="py-20">
            <EmptyState 
              title="No restaurants found" 
              message="We couldn't find any restaurants matching your filters. Try adjusting your search or category."
              onRetry={() => setSearchParams({ area })}
              action="Clear Filters"
            />
          </div>
        )}
      </section>
    </div>
  );
}
