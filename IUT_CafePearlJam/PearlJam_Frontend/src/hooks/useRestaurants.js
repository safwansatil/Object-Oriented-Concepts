import { useQuery } from '@tanstack/react-query'
import { getRestaurants, getRestaurantById } from '../api/restaurants'

export const useRestaurants = (filters) => {
  return useQuery({
    queryKey: ['restaurants', filters],
    queryFn: () => getRestaurants(filters),
  })
}

export const useRestaurant = (id) => {
  return useQuery({
    queryKey: ['restaurant', id],
    queryFn: () => getRestaurantById(id),
    enabled: !!id,
  })
}
