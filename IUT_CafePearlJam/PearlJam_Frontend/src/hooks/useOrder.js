import { useQuery } from '@tanstack/react-query'
import { getOrderById } from '../api/orders'

export const useOrder = (id) => {
  return useQuery({
    queryKey: ['order', id],
    queryFn: () => getOrderById(id),
    enabled: !!id,
    refetchInterval: (query) => {
      const order = query.state.data
      if (!order) return 15000
      
      const isFinished = order.status === 'DELIVERED' || order.status === 'CANCELLED'
      return isFinished ? false : 15000
    },
  })
}
