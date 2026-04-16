import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, MapPin, ArrowRight, Star, Clock, ChefHat, Truck } from 'lucide-react';
import { Button } from '../components/ui/Button';
import { Input } from '../components/ui/Input';

/**
 * LandingPage: High-impact hero and introductory content.
 */
export function LandingPage() {
  const navigate = useNavigate();
  const [area, setArea] = useState(() => localStorage.getItem('last-area') || '');

  const handleSearch = (e) => {
    e.preventDefault();
    if (!area.trim()) return;
    localStorage.setItem('last-area', area);
    navigate(`/restaurants?area=${encodeURIComponent(area)}`);
  };

  const cuisines = ['Burger', 'Pizza', 'Drinks', 'Dessert', 'Asian'];

  return (
    <div className="flex flex-col">
      {/* Hero Section */}
      <section className="relative min-h-[90vh] flex items-center overflow-hidden bg-background pt-20">
        <div className="container mx-auto px-6 relative z-10 grid lg:grid-cols-2 gap-16 items-center">
          <div className="max-w-2xl animate-in fade-in slide-in-from-left-8 duration-700">
            <span className="label-md font-bold text-accent uppercase tracking-[0.2em] mb-6 block border-l-4 border-accent pl-4">
              The Digital Greenhouse
            </span>
            <h1 className="display-lg mb-8 leading-[1.05]">
              Editorial Grade <br />
              <span className="text-accent italic">Culinary</span> Experiences.
            </h1>
            <p className="body-lg mb-10 max-w-lg">
              We move away from sterile interfaces. Experience food delivery as a curated journal of premium farm-to-table tastes.
            </p>

            <form onSubmit={handleSearch} className="relative flex flex-col sm:flex-row gap-4 p-2 bg-white rounded-[2rem] shadow-xl border border-border/10 max-w-xl group-focus-within:ring-2 ring-accent">
              <div className="flex-1 flex items-center gap-3 px-4">
                <MapPin className="text-accent shrink-0" size={24} />
                <Input 
                  placeholder="Enter your delivery area..." 
                  className="border-none bg-transparent focus-visible:ring-0 h-10 text-lg p-0"
                  value={area}
                  onChange={(e) => setArea(e.target.value)}
                />
              </div>
              <Button type="submit" size="lg" className="rounded-full px-10 premium group">
                Find Food
                <ArrowRight className="ml-2 group-hover:translate-x-1 transition-transform" size={20} />
              </Button>
            </form>

            <div className="mt-12 flex items-center gap-8">
              <div className="flex -space-x-3">
                {[1, 2, 3, 4].map(i => (
                  <div key={i} className="w-12 h-12 rounded-full border-4 border-white overflow-hidden bg-surface shadow-sm">
                    <img src={`https://i.pravatar.cc/100?u=${i+10}`} alt="User" />
                  </div>
                ))}
              </div>
              <div className="text-sm">
                <div className="flex items-center gap-1 text-accent font-bold">
                  <Star fill="currentColor" size={14} />
                  <span>4.9</span>
                </div>
                <p className="text-text-muted font-medium">Join 2,000+ local foodies</p>
              </div>
            </div>
          </div>

          <div className="relative lg:block hidden">
            <div className="aspect-[4/5] rounded-[3rem] overflow-hidden shadow-2xl relative animate-in zoom-in-95 duration-1000 delay-200">
              <img 
                src="https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&q=80&w=1000" 
                alt="Premium Food" 
                className="w-full h-full object-cover grayscale-[0.1] hover:grayscale-0 transition-all duration-700 scale-110"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black/40 via-transparent to-transparent" />
            </div>
            
            {/* Floating Editorial Card */}
            <div className="absolute -bottom-8 -left-12 bg-white p-8 rounded-card shadow-2xl max-w-xs border border-border/10 animate-in slide-in-from-bottom-8 duration-700 delay-500">
              <div className="flex items-center gap-4 mb-4">
                <div className="w-12 h-12 rounded-full bg-accent/10 flex items-center justify-center text-accent">
                  <ChefHat size={24} />
                </div>
                <div>
                  <h4 className="title-md text-lg leading-tight">Artisanal Choice</h4>
                  <p className="label-md text-xs text-text-muted">Direct from Farms</p>
                </div>
              </div>
              <p className="text-sm italic text-text-muted leading-relaxed">
                "We define boundaries through tonal transitions, ensuring every meal is a masterpiece."
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Cuisine Categories */}
      <section className="py-24 bg-surface/30">
        <div className="container mx-auto px-6">
          <div className="text-center mb-16">
            <h2 className="headline-md mb-4 uppercase tracking-[0.1em]">Browse by Palette</h2>
            <p className="body-lg mx-auto max-w-lg">Curated categories from the most refined local kitchens.</p>
          </div>
          
          <div className="flex flex-wrap justify-center gap-8">
            {cuisines.map((c) => (
              <button 
                key={c}
                onClick={() => navigate(`/restaurants?query=${encodeURIComponent(c)}`)}
                className="group flex flex-col items-center gap-4 p-8 bg-white rounded-card shadow-sm hover:shadow-xl hover:-translate-y-2 transition-all duration-300 w-44"
              >
                <span className="text-5xl grayscale group-hover:grayscale-0 transition-all duration-300 group-hover:scale-110">
                  🍽️
                </span>
                <span className="label-md font-bold text-text-muted group-hover:text-accent transition-colors">
                  {c}
                </span>
              </button>
            ))}
          </div>
        </div>
      </section>

      {/* How it works */}
      <section id="how-it-works" className="py-32">
        <div className="container mx-auto px-6">
          <div className="grid lg:grid-cols-3 gap-16">
            {[
              {
                step: '01',
                title: 'Curated Selections',
                desc: 'We partner with restaurants that prioritize artisanal ingredients and editorial presentation.',
                icon: Star
              },
              {
                step: '02',
                title: 'Real-time Crafting',
                desc: 'Your meal is prepared with precision. Monitor every stage from prep to package.',
                icon: Clock
              },
              {
                step: '03',
                title: 'Direct Delivery',
                desc: 'Handled with care, delivered fresh. No extra stops, just pure culinary focus.',
                icon: Truck
              }
            ].map((s, idx) => {
              const Icon = s.icon;
              return (
                <div key={s.step} className="group relative pt-12 border-t border-border/40">
                  <span className="font-serif text-8xl font-black text-accent/5 absolute top-0 -left-4 pointer-events-none group-hover:text-accent/10 transition-colors">
                    {s.step}
                  </span>
                  <h3 className="headline-md text-2xl mb-4 relative z-10">{s.title}</h3>
                  <p className="body-lg text-sm relative z-10">{s.desc}</p>
                </div>
              );
            })}
          </div>
        </div>
      </section>
    </div>
  );

}
