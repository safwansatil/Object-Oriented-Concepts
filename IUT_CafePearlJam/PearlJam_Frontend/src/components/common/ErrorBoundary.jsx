import React from 'react';
import { EmptyState } from './EmptyState';
import { RefreshCcw } from 'lucide-react';
import PropTypes from 'prop-types';

/**
 * ErrorBoundary: standard class component to catch JS errors in children.
 */
class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, error };
  }

  componentDidCatch(error, errorInfo) {
    console.error("Uncaught error:", error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      return (
        <div className="min-h-[400px] flex items-center justify-center p-6">
          <EmptyState
            title="Something went wrong"
            message="We encountered an unexpected error. Our team has been notified. Please try refreshing the page."
            icon={RefreshCcw}
            action="Refresh Page"
            onRetry={() => window.location.reload()}
          />
        </div>
      );
    }

    return this.props.children;
  }
}

ErrorBoundary.propTypes = {
  children: PropTypes.node.isRequired,
};

export { ErrorBoundary };
