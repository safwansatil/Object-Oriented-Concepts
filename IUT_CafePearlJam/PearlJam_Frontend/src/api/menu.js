import client from './client'

export const getMenuByRestaurant = (restaurantId) => {
  return client.get(`/restaurants/${restaurantId}/menu`)
}
