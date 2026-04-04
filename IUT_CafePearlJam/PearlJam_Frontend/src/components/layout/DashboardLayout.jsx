import { Outlet, Navigate } from 'react-router-dom';
import { DashboardSidebar } from './DashboardSidebar';
import { useAuth } from '../../hooks/useAuth';

/**
 * DashboardLayout: Layout for the restaurant owner dashboard.
 */
export function DashboardLayout() {
  const { user, isAuthenticated, isLoading } = useAuth();

  if (isLoading) return null;
  if (!isAuthenticated) return <Navigate to="/login" />;
  if (user.role !== 'RESTAURANT_OWNER' && user.role !== 'RIDER') return <Navigate to="/" />;

  return (
    <div className="flex min-h-screen bg-surface/30">
      <DashboardSidebar />
      <main className="flex-1 p-10 overflow-y-auto">
        <div className="container mx-auto max-w-7xl">
          <Outlet />
        </div>
      </main>
    </div>
  );
}
