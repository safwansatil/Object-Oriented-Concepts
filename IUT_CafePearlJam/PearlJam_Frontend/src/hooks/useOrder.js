import { useQuery } from '@tanstack/react-query'
import { getOrderStatus } from '../api/orders'

export const useOrder = (id) => {
  return useQuery({
    queryKey: ['order', id],
    queryFn: () => getOrderStatus(id),
    enabled: !!id,
    refetchInterval: (query) => {
      const order = query.state.data || {}
      if (!order) return 15000
      
      const status = String(order.status || '').toUpperCase()
      const isFinished = status === 'DELIVERED' || status === 'CANCELLED'
      return isFinished ? false : 15000
    },
  })
}
