import React, { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import client from '../../api/client';
import { LoadingSpinner } from '../../components/common/LoadingSpinner';
import { EmptyState } from '../../components/common/EmptyState';
import { Card, CardContent, CardHeader, CardTitle } from '../../components/ui/Card';
import { Button } from '../../components/ui/Button';
import { Plus, Edit, Trash2, Tag, UtensilsCrossed, Power } from 'lucide-react';
import { Badge } from '../../components/ui/Badge';
import { cn } from '../../lib/utils';
import { useAuth } from '../../hooks/useAuth';

/**
 * MenuManagementPage: Restaurant owner menu item management.
 */
export function MenuManagementPage() {
  const queryClient = useQueryClient();
  const { user } = useAuth(); // Need to import this or get from context

  const { data: menu, isLoading, error } = useQuery({
    queryKey: ['dashboard-menu'],
    queryFn: () => client.get(`/dashboard/menu`),
  });

  if (isLoading) return <LoadingSpinner size={48} className="py-40" />;
  if (error) return <EmptyState title="Error Loading Menu" message={error.message} />;

  return (
    <div className="space-y-10 animate-in fade-in slide-in-from-bottom-4 duration-500">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="headline-md text-4xl mb-1">Culinary Inventory</h1>
          <p className="body-lg text-sm text-text-muted">Curate and manage your editorial offerings.</p>
        </div>
        <Button className="rounded-pill premium px-8 shadow-lg group">
          <Plus size={20} className="mr-2 group-hover:rotate-90 transition-transform" />
          Add Masterpiece
        </Button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        {menu && menu.length > 0 ? menu.map((item) => (
          <Card key={item.id} className="group relative overflow-hidden flex flex-col">
            <div className="h-48 relative overflow-hidden bg-surface">
              <img 
                src={item.imageUrl} 
                alt={item.name} 
                className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black/60 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity" />
              <div className="absolute top-4 left-4">
                <Badge className={cn(
                  "border-none font-bold uppercase tracking-widest text-[10px] px-3",
                  item.available ? "bg-accent text-white" : "bg-danger text-white grayscale"
                )}>
                  {item.available ? 'In Stock' : 'Out of Stock'}
                </Badge>
              </div>
            </div>
            
            <CardContent className="p-8 flex-1 flex flex-col">
              <div className="flex justify-between items-start mb-4">
                <h3 className="title-md font-bold text-xl group-hover:text-accent transition-colors">{item.name}</h3>
                <span className="font-serif text-2xl font-bold italic text-accent">${item.price.toFixed(2)}</span>
              </div>
              
              <p className="text-xs text-text-muted leading-relaxed mb-8 flex-1 italic">
                "{item.description}"
              </p>
              
              <div className="flex items-center gap-3 pt-6 border-t border-border/10">
                <Button variant="outline" size="sm" className="flex-1 rounded-pill label-md font-bold text-[10px] uppercase tracking-widest hover:bg-accent/5">
                  <Edit size={14} className="mr-2" /> 
                  Refine
                </Button>
                <Button variant="outline" size="sm" className="flex-1 rounded-pill label-md font-bold text-[10px] uppercase tracking-widest hover:bg-danger/5 group/del">
                  <Trash2 size={14} className="mr-2 group-hover/del:text-danger" /> 
                  Retire
                </Button>
                <Button variant="outline" size="icon" className="shrink-0 rounded-full h-10 w-10">
                  <Power size={14} />
                </Button>
              </div>
            </CardContent>
          </Card>
        )) : (
          <div className="lg:col-span-3 py-20">
            <EmptyState 
              title="Your kitchen is empty" 
              message="Begin your editorial journey by adding your first menu item." 
              action="Curate Menu Item"
              onRetry={() => {}}
            />
          </div>
        )}
      </div>
    </div>
  );

}
