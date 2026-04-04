import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { register } from '../api/users';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';
import { Label } from '../components/ui/Label';
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from '../components/ui/Card';
import { LogIn, ArrowRight, ShieldCheck, Mail, Lock, User, Briefcase, Zap } from 'lucide-react';
import { cn } from '../lib/utils';
import { useMutation } from '@tanstack/react-query';

/**
 * Register: Multi-role enrollment page.
 */
export function Register() {
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    phone: '',
    role: 'CUSTOMER', // Default
  });

  const registerMutation = useMutation({
    mutationFn: (data) => register(data),
    onSuccess: () => {
      navigate('/login');
    },
  });

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleRoleSelect = (role) => {
    setFormData(prev => ({ ...prev, role }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    registerMutation.mutate(formData);
  };

  return (
    <div className="min-h-screen bg-surface/30 flex items-center justify-center p-6 py-20">
      <div className="w-full max-w-2xl animate-in fade-in zoom-in-95 duration-500">
        <div className="text-center mb-12">
          <Link to="/" className="inline-block transition-transform hover:scale-105">
            <span className="font-serif text-5xl font-bold text-accent tracking-tighter">
              PEARL<span className="text-text-primary">JAM</span>
            </span>
          </Link>
          <div className="mt-4 flex items-center justify-center gap-2 text-text-muted label-md font-bold uppercase tracking-widest text-[10px]">
            <Zap size={14} className="text-accent" />
            Cultivating Culinary Excellence
          </div>
        </div>

        <Card className="shadow-2xl border border-border/10 overflow-hidden">
          <div className="grid md:grid-cols-5 h-full">
            {/* Left Info Column */}
            <div className="md:col-span-2 bg-accent p-10 text-white flex flex-col justify-between relative overflow-hidden">
               <div className="relative z-10">
                 <h3 className="font-serif text-3xl font-bold mb-6">Join the Digital Greenhouse.</h3>
                 <ul className="space-y-6 text-xs font-semibold uppercase tracking-widest opacity-80">
                   <li className="flex items-center gap-3">
                     <div className="w-6 h-6 rounded-full bg-white/20 flex items-center justify-center"><ArrowRight size={12} /></div>
                     Editorial Curation
                   </li>
                   <li className="flex items-center gap-3">
                     <div className="w-6 h-6 rounded-full bg-white/20 flex items-center justify-center"><ArrowRight size={12} /></div>
                     Artisanal Partners
                   </li>
                   <li className="flex items-center gap-3">
                     <div className="w-6 h-6 rounded-full bg-white/20 flex items-center justify-center"><ArrowRight size={12} /></div>
                     Priority Hand-off
                   </li>
                 </ul>
               </div>
               
               <div className="label-md font-bold text-[10px] opacity-40">
                 EST. 2024
               </div>

               {/* Design Pulse background element */}
               <div className="absolute -bottom-20 -right-20 w-64 h-64 bg-white/5 rounded-full blur-3xl animate-pulse" />
            </div>

            {/* Form Column */}
            <CardContent className="md:col-span-3 p-10">
              <form onSubmit={handleSubmit} className="space-y-6">
                <div className="space-y-2 mb-8">
                  <h4 className="headline-md text-2xl">Registration</h4>
                  <p className="text-xs text-text-muted">Create your multi-purpose PearlJam account.</p>
                </div>

                <div className="grid grid-cols-3 gap-3">
                  <button 
                    type="button"
                    onClick={() => handleRoleSelect('CUSTOMER')}
                    className={cn(
                      "flex flex-col items-center gap-2 p-4 rounded-card border-2 transition-all",
                      formData.role === 'CUSTOMER' ? "border-accent bg-accent/5 text-accent" : "border-border/30 hover:border-accent/40 opacity-60"
                    )}
                  >
                    <User size={18} />
                    <span className="label-md font-bold text-[9px] uppercase">Enthusiast</span>
                  </button>
                  <button 
                    type="button"
                    onClick={() => handleRoleSelect('RESTAURANT_OWNER')}
                    className={cn(
                      "flex flex-col items-center gap-2 p-4 rounded-card border-2 transition-all",
                      formData.role === 'RESTAURANT_OWNER' ? "border-accent bg-accent/5 text-accent" : "border-border/30 hover:border-accent/40 opacity-60"
                    )}
                  >
                    <Briefcase size={18} />
                    <span className="label-md font-bold text-[9px] uppercase">Owner</span>
                  </button>
                  <button 
                    type="button"
                    onClick={() => handleRoleSelect('RIDER')}
                    className={cn(
                      "flex flex-col items-center gap-2 p-4 rounded-card border-2 transition-all",
                      formData.role === 'RIDER' ? "border-accent bg-accent/5 text-accent" : "border-border/30 hover:border-accent/40 opacity-60"
                    )}
                  >
                    <Zap size={18} />
                    <span className="label-md font-bold text-[9px] uppercase">Rider</span>
                  </button>
                </div>

                <div className="space-y-4">
                  <Label className="label-md font-bold uppercase tracking-widest text-[10px]">Full Name</Label>
                  <Input name="name" placeholder="John Doe" value={formData.name} onChange={handleChange} required />
                </div>

                <div className="space-y-4">
                  <Label className="label-md font-bold uppercase tracking-widest text-[10px]">Email Address</Label>
                  <Input type="email" name="email" placeholder="john@example.com" value={formData.email} onChange={handleChange} required />
                </div>

                <div className="space-y-4">
                  <Label className="label-md font-bold uppercase tracking-widest text-[10px]">Phone Number</Label>
                  <Input name="phone" placeholder="01711223344" value={formData.phone} onChange={handleChange} required />
                </div>

                <div className="space-y-4">
                  <Label className="label-md font-bold uppercase tracking-widest text-[10px]">Security Password</Label>
                  <Input type="password" name="password" placeholder="Min. 8 characters" value={formData.password} onChange={handleChange} required />
                </div>

                <Button 
                  type="submit" 
                  className="w-full premium h-14 rounded-pill text-lg font-bold shadow-xl border-none"
                  disabled={registerMutation.isPending}
                >
                  {registerMutation.isPending ? 'Enrollment Pending...' : 'Establish Account'}
                </Button>

                <div className="text-center pt-4">
                  <p className="text-xs text-text-muted">
                    Already a member? <Link to="/login" className="text-accent font-bold hover:underline">Sign In Here</Link>
                  </p>
                </div>
              </form>
            </CardContent>
          </div>
        </Card>
      </div>
    </div>
  );
}
