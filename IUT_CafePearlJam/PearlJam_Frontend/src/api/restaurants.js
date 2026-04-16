import client from './client'

export const getRestaurants = ({ area, query, sort } = {}) => {
  return client.get('/api/restaurants', { params: { area, query, sort } })
}

export const getRestaurantById = (id) => {
  return client.get(`/api/restaurants/${id}`)
}

export const registerRestaurant = (payload) => {
  return client.post('/api/restaurants/register', payload)
}
