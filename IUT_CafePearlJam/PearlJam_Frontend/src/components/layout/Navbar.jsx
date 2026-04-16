import { Link, useNavigate } from 'react-router-dom';
import { ShoppingBag, User, LogOut, Menu } from 'lucide-react';
import { useCartStore } from '../../store/cartStore';
import { useAuth } from '../../hooks/useAuth';
import { cn } from '../../lib/utils';
import { useState } from 'react';

/**
 * Navbar: The primary navigation bar for PearlJam.
 */
export function Navbar() {
  const { user, logout, isAuthenticated } = useAuth();
  const { getItemCount, getSubtotal } = useCartStore();
  const itemCount = getItemCount();
  const subtotal = getSubtotal();
  const navigate = useNavigate();
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const navLinks = [
    { name: 'Restaurants', path: '/restaurants' },
    { name: 'How it works', path: '/#how-it-works' },
    { name: 'Dashboard', path: '/dashboard', auth: true, role: 'RESTAURANT_OWNER' },
  ];

  return (
    <nav className="sticky top-0 z-50 glass h-20 flex items-center">
      <div className="container mx-auto px-6 h-full flex items-center justify-between">
        {/* Branding */}
        <Link to="/" className="flex items-center gap-2">
          <span className="font-serif text-3xl font-bold text-accent tracking-tighter">
            PEARL<span className="text-text-primary">JAM</span>
          </span>
        </Link>

        {/* Desktop Navigation */}
        <div className="hidden md:flex items-center gap-8">
          {navLinks.map((link) => (
            (!link.auth || isAuthenticated) && (!link.role || user?.role === link.role) && (
              <Link 
                key={link.name} 
                to={link.path} 
                className="label-md font-semibold text-text-primary hover:text-accent transition-colors"
              >
                {link.name}
              </Link>
            )
          ))}
        </div>

        {/* Action Buttons */}
        <div className="flex items-center gap-4">
          {isAuthenticated ? (
            <div className="flex items-center gap-2 pr-4 border-r border-border/50">
              <Link to="/dashboard" className="flex items-center gap-2 group">
                <div className="w-8 h-8 rounded-full bg-accent/10 flex items-center justify-center text-accent group-hover:bg-accent group-hover:text-white transition-all">
                  <User size={18} />
                </div>
                <span className="hidden lg:inline label-md font-semibold">{user.name}</span>
              </Link>
              <button 
                onClick={logout}
                className="p-2 text-text-muted hover:text-danger transition-colors"
                title="Logout"
              >
                <LogOut size={18} />
              </button>
            </div>
          ) : (
            <Link 
              to="/login" 
              className="hidden sm:inline-flex label-md font-semibold px-6 py-2 rounded-pill border border-border/30 hover:border-accent hover:text-accent transition-all"
            >
              Log In
            </Link>
          )}

          {/* Cart Trigger */}
          <Link 
            to="/checkout"
            className="flex items-center gap-3 bg-accent text-white px-5 py-2.5 rounded-pill hover:bg-accent-hover transition-all shadow-sm"
          >
            <div className="relative">
              <ShoppingBag size={20} />
              {itemCount > 0 && (
                <span className="absolute -top-2 -right-2 bg-danger text-white text-[10px] font-bold w-4 h-4 flex items-center justify-center rounded-full">
                  {itemCount}
                </span>
              )}
            </div>
            {itemCount > 0 && (
              <span className="hidden sm:inline font-semibold">${subtotal.toFixed(2)}</span>
            )}
          </Link>

          {/* Mobile Menu Toggle */}
          <button 
            className="md:hidden p-2"
            onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
          >
            <Menu size={24} />
          </button>
        </div>
      </div>

      {/* Mobile Menu Dropdown */}
      {isMobileMenuOpen && (
        <div className="absolute top-20 left-0 w-full bg-white border-b border-border p-6 md:hidden flex flex-col gap-6 shadow-xl animate-in slide-in-from-top-4">
          {navLinks.map((link) => (
            (!link.auth || isAuthenticated) && (!link.role || user?.role === link.role) && (
              <Link 
                key={link.name} 
                to={link.path} 
                onClick={() => setIsMobileMenuOpen(false)}
                className="headline-md text-2xl"
              >
                {link.name}
              </Link>
            )
          ))}
          {!isAuthenticated && (
            <Link 
              to="/login" 
              onClick={() => setIsMobileMenuOpen(false)}
              className="bg-accent text-white py-4 rounded-card text-center font-bold"
            >
              Join PearlJam
            </Link>
          )}
        </div>
      )}
    </nav>
  );
}
