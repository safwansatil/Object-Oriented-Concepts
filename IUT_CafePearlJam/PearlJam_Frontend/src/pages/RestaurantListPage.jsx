import React from 'react';
import { useSearchParams } from 'react-router-dom';
import { useRestaurants } from '../hooks/useRestaurants';
import { RestaurantCard } from '../components/restaurant/RestaurantCard';
import { LoadingSpinner } from '../components/common/LoadingSpinner';
import { EmptyState } from '../components/common/EmptyState';
import { Search, SlidersHorizontal } from 'lucide-react';
import { Input } from '../components/ui/Input';
import { cn } from '../lib/utils';

/**
 * RestaurantListPage: Filterable grid of restaurants.
 */
export function RestaurantListPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const area = searchParams.get('area') || '';
  const query = searchParams.get('query') || '';
  const sort = searchParams.get('sort') || '';

  const { data: restaurants = [], isLoading, error, refetch } = useRestaurants({ area, query, sort });

  const setParam = (key, value) => {
    const newParams = new URLSearchParams(searchParams);
    if (value) newParams.set(key, value);
    else newParams.delete(key);
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
              <p className="body-lg text-sm">Discover {restaurants.length} available restaurants.</p>
            </div>
            
            <div className="relative w-full max-w-md">
              <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-text-muted" size={20} />
              <Input 
                placeholder="Search by name or cuisine..." 
                className="pl-12 bg-surface/50 border-none rounded-pill h-12"
                value={query}
                onChange={(e) => {
                  setParam('query', e.target.value);
                }}
              />
            </div>
          </div>

          <div className="flex items-center gap-4 overflow-x-auto pb-4 no-scrollbar">
            <div className="flex items-center gap-2 pr-4 border-r border-border/30 shrink-0">
              <SlidersHorizontal size={18} className="text-accent" />
              <span className="label-md font-bold text-xs uppercase tracking-widest">Sort</span>
            </div>
            {['name', 'cuisine'].map((s) => (
              <button
                key={s}
                onClick={() => setParam('sort', sort === s ? '' : s)}
                className={cn(
                  "px-6 py-2 rounded-pill label-md font-semibold transition-all shrink-0",
                  sort === s
                    ? "bg-accent text-white shadow-md shadow-accent/20" 
                    : "bg-surface text-text-muted hover:bg-border/30 hover:text-text-primary"
                )}
              >
                {s}
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
        ) : restaurants.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
            {restaurants.map((r) => (
              <RestaurantCard key={r.id} restaurant={r} />
            ))}
          </div>
        ) : (
          <div className="py-20">
            <EmptyState 
              title="No restaurants found" 
              message="No restaurants match the current area/query filters."
              onRetry={() => setSearchParams({ area })}
              action="Clear Filters"
            />
          </div>
        )}
      </section>
    </div>
  );
}
