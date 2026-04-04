import PropTypes from 'prop-types';
import { cn } from '../../lib/utils';

/**
 * StatusBadge component: maps OrderStatus to styled pills.
 */
export function StatusBadge({ status }) {
  const statusConfig = {
    PENDING: {
      label: 'Pending',
      classes: 'bg-status-pending/10 text-status-pending',
    },
    CONFIRMED: {
      label: 'Confirmed',
      classes: 'bg-status-confirmed/10 text-status-confirmed',
    },
    PREPARING: {
      label: 'Preparing',
      classes: 'bg-status-preparing/10 text-status-preparing',
    },
    READY: {
      label: 'Ready',
      classes: 'bg-status-ready/10 text-status-ready',
    },
    DELIVERING: {
      label: 'Delivering',
      classes: 'bg-status-delivering/10 text-status-delivering',
    },
    DELIVERED: {
      label: 'Delivered',
      classes: 'bg-status-delivered/10 text-status-delivered',
    },
    CANCELLED: {
      label: 'Cancelled',
      classes: 'bg-status-cancelled/10 text-status-cancelled',
    },
  };

  const config = statusConfig[status?.toUpperCase()] || statusConfig.PENDING;

  return (
    <span className={cn(
      "px-3 py-1 rounded-pill text-xs font-semibold uppercase tracking-wider",
      config.classes
    )}>
      {config.label}
    </span>
  );
}

StatusBadge.propTypes = {
  status: PropTypes.string.isRequired,
};
