import client from './client'

export const getRestaurants = (area, cuisine) => {
  return client.get('/restaurants', { params: { area, cuisine } })
}

export const getRestaurantById = (id) => {
  return client.get(`/restaurants/${id}`)
}
