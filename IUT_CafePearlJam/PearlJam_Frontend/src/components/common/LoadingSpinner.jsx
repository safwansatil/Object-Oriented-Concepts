import { Loader2 } from 'lucide-react';
import { cn } from '../../lib/utils';
import PropTypes from 'prop-types';

/**
 * LoadingSpinner component with theme-aware styling.
 */
export function LoadingSpinner({ className, size = 32 }) {
  return (
    <div className={cn("flex items-center justify-center p-8", className)}>
      <Loader2 
        className="animate-spin text-accent" 
        size={size} 
      />
    </div>
  );
}

LoadingSpinner.propTypes = {
  className: PropTypes.string,
  size: PropTypes.number,
};
