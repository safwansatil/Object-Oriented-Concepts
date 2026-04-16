import client from './client'

export const getMenuByRestaurant = (restaurantId) => {
  return client.get(`/api/restaurants/${restaurantId}/menu`)
}

export const addMenuCategory = (payload) => {
  return client.post('/api/menu/categories', payload)
}

export const addMenuItem = (payload) => {
  return client.post('/api/menu/items', payload)
}

export const setMenuItemAvailability = (itemId, available) => {
  return client.patch(`/api/menu/items/${itemId}/availability`, { available })
}

export const updateMenuItemStock = (itemId, quantity) => {
  return client.patch(`/api/menu/items/${itemId}/stock`, { quantity })
}
