import { Link } from 'react-router-dom';
import { Star, Clock, MapPin } from 'lucide-react';
import { cn } from '../../lib/utils';
import { Badge } from '../ui/Badge';
import PropTypes from 'prop-types';

/**
 * RestaurantCard: Image-first editorial-style card for restaurant listings.
 */
export function RestaurantCard({ restaurant }) {
  const { id, name, imageUrl, cuisineType, rating, deliveryTime, isOpen, address } = restaurant;

  return (
    <Link 
      to={`/restaurant/${id}`}
      className={cn(
        "group block surface-card overflow-hidden transition-all hover:-translate-y-1",
        !isOpen && "opacity-75 grayscale-[0.5]"
      )}
    >
      <div className="relative aspect-[16/10] overflow-hidden">
        <img 
          src={imageUrl || 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&q=80&w=800'} 
          alt={name}
          className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-105"
        />
        {!isOpen && (
          <div className="absolute inset-0 bg-black/40 flex items-center justify-center">
            <span className="px-4 py-2 bg-white/90 rounded-pill text-sm font-bold text-text-primary">
              Currently Closed
            </span>
          </div>
        )}
        <div className="absolute top-4 right-4">
          <Badge className="bg-white/90 text-accent backdrop-blur-md border-none shadow-sm font-bold">
            {cuisineType}
          </Badge>
        </div>
      </div>
      
      <div className="p-6">
        <div className="flex items-start justify-between mb-2">
          <h3 className="title-md group-hover:text-accent transition-colors truncate pr-2">
            {name}
          </h3>
          <div className="flex items-center gap-1 text-accent font-bold">
            <Star size={16} fill="currentColor" />
            <span className="text-sm">{rating || 'New'}</span>
          </div>
        </div>
        
        <p className="text-sm text-text-muted mb-4 line-clamp-1 flex items-center gap-1">
          <MapPin size={14} />
          {address}
        </p>
        
        <div className="flex items-center gap-6 pt-4 border-t border-border/10">
          <div className="flex items-center gap-2 text-text-muted">
            <Clock size={16} />
            <span className="label-md text-xs">{deliveryTime || '25-35'} min</span>
          </div>
          <div className="label-md text-xs text-accent font-bold">
            Free Delivery
          </div>
        </div>
      </div>
    </Link>
  );
}

RestaurantCard.propTypes = {
  restaurant: PropTypes.shape({
    id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    name: PropTypes.string.isRequired,
    imageUrl: PropTypes.string,
    cuisineType: PropTypes.string,
    rating: PropTypes.number,
    deliveryTime: PropTypes.string,
    isOpen: PropTypes.bool,
    address: PropTypes.string,
  }).isRequired,
};
