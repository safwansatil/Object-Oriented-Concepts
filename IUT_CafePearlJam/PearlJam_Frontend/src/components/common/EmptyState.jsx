import { AlertCircle } from 'lucide-react';
import { cn } from '../../lib/utils';
import PropTypes from 'prop-types';

/**
 * EmptyState: show a friendly message when no data is found or an error occurs.
 */
export function EmptyState({ title, message, icon: Icon = AlertCircle, action, onRetry }) {
  return (
    <div className="flex flex-col items-center justify-center p-12 text-center bg-surface/30 rounded-card">
      <div className="p-4 bg-white rounded-full shadow-sm mb-6">
        <Icon size={48} className="text-accent" />
      </div>
      <h3 className="headline-md text-text-primary mb-2">{title}</h3>
      <p className="body-lg max-w-md mx-auto mb-8">{message}</p>
      
      {onRetry && (
        <button 
          onClick={onRetry}
          className="px-8 py-3 bg-accent text-white rounded-pill hover:bg-accent-hover transition-colors font-medium shadow-sm"
        >
          {action || 'Try Again'}
        </button>
      )}
    </div>
  );
}

EmptyState.propTypes = {
  title: PropTypes.string.isRequired,
  message: PropTypes.string.isRequired,
  icon: PropTypes.elementType,
  action: PropTypes.string,
  onRetry: PropTypes.func,
};
