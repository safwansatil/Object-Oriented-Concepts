import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { ShoppingCart, TrendingUp, Users, DollarSign, Clock } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '../../components/ui/Card';
import { StatusBadge } from '../../components/common/StatusBadge';
import { LoadingSpinner } from '../../components/common/LoadingSpinner';
import client from '../../api/client';

/**
 * OverviewPage: Restaurant owner's main performance dashboard.
 */
export function OverviewPage() {
  const { data: stats, isLoading: isStatsLoading } = useQuery({
    queryKey: ['dashboard-stats'],
    queryFn: () => client.get('/dashboard/stats'),
  });

  const { data: recentOrders, isLoading: isOrdersLoading } = useQuery({
    queryKey: ['recent-orders'],
    queryFn: () => client.get('/dashboard/recent-orders'),
  });

  if (isStatsLoading || isOrdersLoading) return <LoadingSpinner size={48} className="py-40" />;

  const statCards = [
    { title: 'Total Sales', value: `$${stats?.totalSales || 0}`, icon: DollarSign, trend: '+12.5%' },
    { title: 'Active Orders', value: stats?.activeOrders || 0, icon: ShoppingCart, trend: '+2 new' },
    { title: 'New Customers', value: stats?.newCustomers || 0, icon: Users, trend: '+5.2%' },
    { title: 'Satisfaction', value: stats?.satisfaction || '4.9/5', icon: TrendingUp, trend: 'Top 5%' },
  ];

  return (
    <div className="space-y-10 animate-in fade-in slide-in-from-bottom-4 duration-500">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="headline-md text-4xl mb-1">Kitchen Overview</h1>
          <p className="body-lg text-sm text-text-muted">Monitor your editorial performance in real-time.</p>
        </div>
        <div className="flex items-center gap-2 bg-white px-4 py-2 rounded-pill shadow-sm text-xs font-bold text-accent uppercase tracking-widest border border-border/10">
          <Clock size={14} />
          Refreshed Just Now
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {statCards.map((stat) => (
          <Card key={stat.title}>
            <CardHeader className="flex flex-row items-center justify-between pb-2">
              <CardTitle className="label-md font-bold text-text-muted uppercase tracking-widest text-[10px]">
                {stat.title}
              </CardTitle>
              <stat.icon size={20} className="text-accent" />
            </CardHeader>
            <CardContent>
              <div className="font-serif text-3xl font-bold mb-1">{stat.value}</div>
              <p className="text-xs text-accent font-bold">{stat.trend} <span className="text-text-muted font-normal ml-1">v last month</span></p>
            </CardContent>
          </Card>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-10">
        <Card className="lg:col-span-2">
          <CardHeader className="border-b border-border/10 pb-6 mb-6">
            <CardTitle className="headline-md text-2xl">Recent Editorial Orders</CardTitle>
          </CardHeader>
          <CardContent className="p-0">
            <div className="overflow-x-auto">
              <table className="w-full text-left">
                <thead>
                  <tr className="bg-surface text-text-muted label-md text-[10px] uppercase font-bold tracking-widest">
                    <th className="px-6 py-4">Ref#</th>
                    <th className="px-6 py-4">Customer</th>
                    <th className="px-6 py-4">Status</th>
                    <th className="px-6 py-4 text-right">Amount</th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-border/10">
                  {recentOrders && recentOrders.length > 0 ? recentOrders.map((order) => (
                    <tr key={order.id} className="hover:bg-surface/30 transition-colors">
                      <td className="px-6 py-6 font-bold text-xs uppercase tracking-widest">PJ-{order.id.slice(-8)}</td>
                      <td className="px-6 py-6 label-md font-semibold">{order.customerName}</td>
                      <td className="px-6 py-6"><StatusBadge status={order.status} /></td>
                      <td className="px-6 py-6 text-right font-serif font-bold text-lg">${order.totalAmount.toFixed(2)}</td>
                    </tr>
                  )) : (
                    <tr>
                      <td colSpan="4" className="px-6 py-20 text-center text-text-muted italic">No recent orders found.</td>
                    </tr>
                  )}
                </tbody>
              </table>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="border-b border-border/10 pb-6 mb-6">
            <CardTitle className="headline-md text-2xl">Daily Performance</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-8">
              {[
                { label: 'Wait Time Avg.', value: '18 min', max: '30 min', percent: 60 },
                { label: 'Quality Score', value: '4.8/5', max: '5', percent: 96 },
                { label: 'Kitchen Capacity', value: '75%', max: '100%', percent: 75 }
              ].map((m) => (
                <div key={m.label} className="space-y-3">
                  <div className="flex justify-between items-center label-md font-bold">
                    <span className="text-text-muted text-xs uppercase tracking-widest">{m.label}</span>
                    <span className="text-accent">{m.value}</span>
                  </div>
                  <div className="h-2 w-full bg-surface rounded-pill overflow-hidden">
                    <div 
                      className="h-full bg-accent transition-all duration-1000" 
                      style={{ width: `${m.percent}%` }}
                    />
                  </div>
                </div>
              ))}
            </div>
            
            <div className="mt-12 p-6 bg-surface/50 rounded-card border border-border/10">
              <h4 className="title-md text-lg mb-2 underline decoration-accent/20">Kitchen Pro-Tip</h4>
              <p className="text-xs text-text-muted italic leading-relaxed">
                "Maintaining prepared ingredients under our Editorial Standard 12 increases customer satisfaction by 15%."
              </p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
