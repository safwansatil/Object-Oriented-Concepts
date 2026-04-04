import { Plus, Info } from 'lucide-react';
import { cn } from '../../lib/utils';
import { Badge } from '../ui/Badge';
import { Button } from '../ui/Button';
import PropTypes from 'prop-types';

/**
 * MenuItemCard: Editorial-style card for menu items.
 */
export function MenuItemCard({ item, onAdd }) {
  const { id, name, description, price, imageUrl, isVegetarian, isPopular } = item;

  return (
    <div className="group surface-card overflow-hidden hover:-translate-y-1 transition-all duration-300 flex border border-border/10">
      <div className="flex-1 p-6 relative">
        <div className="flex items-center gap-2 mb-2">
          {isVegetarian && (
            <div className="w-4 h-4 border-2 border-green-600 flex items-center justify-center p-0.5" title="Vegetarian">
              <div className="w-full h-full bg-green-600 rounded-full" />
            </div>
          )}
          {isPopular && (
            <Badge variant="secondary" className="bg-accent/5 text-accent border-none text-[10px] uppercase tracking-tighter font-bold">
              Chef's Pick
            </Badge>
          )}
        </div>
        
        <h4 className="title-md mb-2 group-hover:text-accent transition-colors">
          {name}
        </h4>
        
        <p className="body-lg text-sm mb-6 line-clamp-2 min-h-[40px]">
          {description}
        </p>
        
        <div className="flex items-center justify-between">
          <span className="font-serif text-2xl font-bold text-text-primary">
            ${price.toFixed(2)}
          </span>
          <Button 
            size="sm" 
            variant="default"
            onClick={() => onAdd(item)}
            className="rounded-pill shadow-sm"
          >
            <Plus size={18} className="mr-2" />
            Add
          </Button>
        </div>
      </div>
      
      <div className="w-40 md:w-48 bg-surface relative overflow-hidden">
        <img 
          src={imageUrl || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&q=80&w=400'} 
          alt={name}
          className="w-full h-full object-cover grayscale-[0.2] group-hover:grayscale-0 transition-all duration-500 group-hover:scale-105"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-white via-transparent to-transparent opacity-40" />
      </div>
    </div>
  );
}

MenuItemCard.propTypes = {
  item: PropTypes.shape({
    id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    name: PropTypes.string.isRequired,
    description: PropTypes.string,
    price: PropTypes.number.isRequired,
    imageUrl: PropTypes.string,
    isVegetarian: PropTypes.bool,
    isPopular: PropTypes.bool,
  }).isRequired,
  onAdd: PropTypes.func.isRequired,
};
