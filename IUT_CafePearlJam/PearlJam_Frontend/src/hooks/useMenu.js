import { useQuery } from '@tanstack/react-query'
import { getMenuByRestaurant } from '../api/menu'

export const useMenu = (restaurantId) => {
  return useQuery({
    queryKey: ['menu', restaurantId],
    queryFn: () => getMenuByRestaurant(restaurantId),
    enabled: !!restaurantId,
  })
}
