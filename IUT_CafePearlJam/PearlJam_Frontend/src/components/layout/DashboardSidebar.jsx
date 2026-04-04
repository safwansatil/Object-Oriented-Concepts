import { NavLink, Link } from 'react-router-dom';
import { LayoutDashboard, ShoppingCart, UtensilsCrossed, Settings, HelpCircle, LogOut } from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import { cn } from '../../lib/utils';

/**
 * DashboardSidebar: Sidebar for the restaurant owner dashboard.
 */
export function DashboardSidebar() {
  const { logout, user } = useAuth();
  
  const navItems = [
    { name: 'Overview', icon: LayoutDashboard, path: '/dashboard' },
    { name: 'Orders', icon: ShoppingCart, path: '/dashboard/orders' },
    { name: 'Menu Management', icon: UtensilsCrossed, path: '/dashboard/menu' },
    { name: 'Settings', icon: Settings, path: '/dashboard/settings' },
  ];

  return (
    <div className="w-72 bg-white border-r border-border h-screen sticky top-0 flex flex-col pt-8">
      <div className="px-8 mb-12">
        <NavLink to="/" className="inline-block">
          <span className="font-serif text-3xl font-bold text-accent tracking-tighter">
            PEARL<span className="text-text-primary">JAM</span>
          </span>
        </NavLink>
      </div>

      <nav className="flex-1 px-4 space-y-2">
        {navItems.map((item) => (
          <NavLink
            key={item.name}
            to={item.path}
            end={item.path === '/dashboard'}
            className={({ isActive }) => cn(
              "flex items-center gap-4 px-4 py-3.5 rounded-pill transition-all label-md font-semibold",
              isActive 
                ? "bg-accent text-white shadow-sm" 
                : "text-text-muted hover:bg-surface hover:text-text-primary"
            )}
          >
            <item.icon size={20} />
            {item.name}
          </NavLink>
        ))}
      </nav>

      <div className="p-4 border-t border-border mt-auto mb-4">
        <button 
          onClick={logout}
          className="w-full flex items-center gap-4 px-4 py-3.5 rounded-pill text-text-muted hover:text-danger hover:bg-danger/5 transition-all label-md font-semibold"
        >
          <LogOut size={20} />
          Sign Out
        </button>
        
        <div className="mt-4 px-4">
          <Link to="/help" className="flex items-center gap-2 text-text-muted hover:text-accent transition-colors label-md font-medium text-xs">
            <HelpCircle size={14} />
            Support Center
          </Link>
        </div>
      </div>
    </div>
  );

}
