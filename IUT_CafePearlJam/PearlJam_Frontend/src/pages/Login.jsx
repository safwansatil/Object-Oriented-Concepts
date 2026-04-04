import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';
import { Label } from '../components/ui/Label';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '../components/ui/Card';
import { LogIn, ArrowRight, ShieldCheck, Mail, Lock } from 'lucide-react';
import { cn } from '../lib/utils';

/**
 * Login: Minimalist, editorial-style login page.
 */
export function Login() {
  const navigate = useNavigate();
  const location = useLocation();
  const { login, isLoading, error } = useAuth();
  
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const from = location.state?.from?.pathname || '/';

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await login({ email, password });
      navigate(from, { replace: true });
    } catch (err) {
      // Handled by useAuth mutation error
    }
  };

  return (
    <div className="min-h-screen bg-surface/30 flex items-center justify-center p-6">
      <div className="w-full max-w-lg animate-in fade-in zoom-in-95 duration-500">
        <div className="text-center mb-12">
          <Link to="/" className="inline-block transition-transform hover:scale-105">
            <span className="font-serif text-5xl font-bold text-accent tracking-tighter">
              PEARL<span className="text-text-primary">JAM</span>
            </span>
          </Link>
          <div className="mt-4 flex items-center justify-center gap-2 text-text-muted label-md">
            <ShieldCheck size={14} className="text-accent" />
            Editorial-Grade Security
          </div>
        </div>

        <Card className="shadow-2xl border border-border/10">
          <CardHeader className="p-10 pb-6 text-center">
            <CardTitle className="headline-md text-3xl mb-2">Welcome Back</CardTitle>
            <CardDescription className="body-lg text-sm">Sign in to your editorial kitchen or orders.</CardDescription>
          </CardHeader>
          
          <CardContent className="p-10 pt-0">
            <form onSubmit={handleSubmit} className="space-y-8">
              <div className="space-y-4">
                <Label className="label-md font-bold uppercase tracking-widest text-[10px] ml-1">Email Address</Label>
                <div className="relative group">
                  <Mail className="absolute left-4 top-1/2 -translate-y-1/2 text-text-muted group-focus-within:text-accent transition-colors" size={20} />
                  <Input 
                    type="email" 
                    placeholder="name@example.com"
                    className="pl-12 bg-surface/50 border-none rounded-pill"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                  />
                </div>
              </div>

              <div className="space-y-4">
                <div className="flex justify-between items-center px-1">
                  <Label className="label-md font-bold uppercase tracking-widest text-[10px]">Password</Label>
                  <Link to="/forgot-password" size="sm" className="text-[10px] font-bold text-accent uppercase tracking-widest hover:underline">
                    Forgot?
                  </Link>
                </div>
                <div className="relative group">
                  <Lock className="absolute left-4 top-1/2 -translate-y-1/2 text-text-muted group-focus-within:text-accent transition-colors" size={20} />
                  <Input 
                    type="password" 
                    placeholder="••••••••"
                    className="pl-12 bg-surface/50 border-none rounded-pill"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>
              </div>

              {error && (
                <div className="p-4 bg-danger/10 border border-danger/20 rounded-card text-danger text-xs font-bold text-center">
                  {error.message || 'Invalid email or password. Please try again.'}
                </div>
              )}

              <Button 
                type="submit" 
                className="w-full premium h-14 rounded-pill text-lg font-bold shadow-xl shadow-accent/20 group"
                disabled={isLoading}
              >
                {isLoading ? (
                  'Verification in Progress...'
                ) : (
                  <>
                    Sign In
                    <ArrowRight className="ml-2 group-hover:translate-x-1 transition-transform" />
                  </>
                )}
              </Button>
            </form>

            <div className="mt-12 pt-8 border-t border-border/10 text-center">
              <p className="body-lg text-sm text-text-muted mb-4">New to the greenhouse?</p>
              <Link 
                to="/register" 
                className="label-md font-bold text-accent uppercase tracking-[0.2em] border-b-2 border-accent/20 hover:border-accent transition-all pb-1"
              >
                Join PearlJam
              </Link>
            </div>
          </CardContent>
        </Card>

        {/* Floating Test Credentials (Optional/Demo) */}
        <div className="mt-12 p-6 bg-white/50 backdrop-blur-sm rounded-card border border-border/10 text-center">
          <p className="text-[10px] text-text-muted font-bold uppercase tracking-widest mb-2">Test Environment Credentials</p>
          <div className="flex items-center justify-center gap-8 text-xs text-text-muted">
            <div>
              <span className="font-bold text-accent">Customer:</span> customer@pearljam.com
            </div>
            <div>
              <span className="font-bold text-accent">Owner:</span> owner@pearljam.com
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
