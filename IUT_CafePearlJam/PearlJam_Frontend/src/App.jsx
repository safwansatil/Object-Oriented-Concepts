import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { Layout } from './components/layout/Layout';
import { DashboardLayout } from './components/layout/DashboardLayout';
import { LandingPage } from './pages/LandingPage';
import { RestaurantListPage } from './pages/RestaurantListPage';
import { RestaurantDetailPage } from './pages/RestaurantDetailPage';
import { CheckoutPage } from './pages/CheckoutPage';
import { OrderTrackingPage } from './pages/OrderTrackingPage';
import { Login } from './pages/Login';
import { Register } from './pages/Register';
import { OverviewPage } from './pages/dashboard/OverviewPage';
import { OrdersPage } from './pages/dashboard/OrdersPage';
import { MenuManagementPage } from './pages/dashboard/MenuManagementPage';
import { ErrorBoundary } from './components/common/ErrorBoundary';
import { useAuth } from './hooks/useAuth';

const queryClient = new QueryClient();

function ProtectedRoute({ children, role }) {
  const { user, isAuthenticated, isLoading } = useAuth();
  
  if (isLoading) return <div className="h-screen w-full flex items-center justify-center bg-background">Loading Editorial Experience...</div>;
  
  if (!isAuthenticated) return <Navigate to="/login" replace />;
  
  if (role && user?.role !== role) {
    return <Navigate to="/" replace />;
  }
  
  return children;
}

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ErrorBoundary>
        <Router>
          <Routes>
            {/* Public Layout */}
            <Route path="/" element={<Layout />}>
              <Route index element={<LandingPage />} />
              <Route path="restaurants" element={<RestaurantListPage />} />
              <Route path="restaurant/:id" element={<RestaurantDetailPage />} />
              <Route 
                path="checkout" 
                element={
                  <ProtectedRoute>
                    <CheckoutPage />
                  </ProtectedRoute>
                } 
              />
              <Route 
                path="order/:id" 
                element={
                  <ProtectedRoute>
                    <OrderTrackingPage />
                  </ProtectedRoute>
                } 
              />
            </Route>
            
            {/* Auth Routes */}
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            
            {/* Dashboard Layout */}
            <Route 
              path="/dashboard" 
              element={
                <ProtectedRoute>
                  <DashboardLayout />
                </ProtectedRoute>
              }
            >
              <Route index element={
                <ProtectedRoute role="RESTAURANT_OWNER">
                  <OverviewPage />
                </ProtectedRoute>
              } />
              <Route path="orders" element={
                <ProtectedRoute role="RESTAURANT_OWNER">
                  <OrdersPage />
                </ProtectedRoute>
              } />
              <Route path="menu" element={
                <ProtectedRoute role="RESTAURANT_OWNER">
                  <MenuManagementPage />
                </ProtectedRoute>
              } />
            </Route>

            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </Router>
      </ErrorBoundary>
    </QueryClientProvider>
  );
}
