import { useQuery } from '@tanstack/react-query'
import { getRestaurants, getRestaurantById } from '../api/restaurants'

export const useRestaurants = (area, cuisine) => {
  return useQuery({
    queryKey: ['restaurants', area, cuisine],
    queryFn: () => getRestaurants(area, cuisine),
    enabled: !!area,
  })
}

export const useRestaurant = (id) => {
  return useQuery({
    queryKey: ['restaurant', id],
    queryFn: () => getRestaurantById(id),
    enabled: !!id,
  })
}
