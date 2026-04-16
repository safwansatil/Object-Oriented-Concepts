import client from './client'

export const placeOrder = (orderData) => {
  return client.post('/api/orders', orderData)
}

export const getOrderById = (id) => {
  return client.get(`/api/orders/${id}`)
}

export const getOrderStatus = (id) => {
  return client.get(`/api/orders/${id}/status`)
}

export const getOrdersByCustomer = (customerId) => {
  return client.get(`/api/orders/customer/${customerId}`)
}

export const updateOrderStatus = (id, status, actorId = 'restaurant-owner') => {
  return client.patch(`/api/orders/${id}/status`, { status, actorId })
}

export const assignRider = (id, riderId) => {
  return client.patch(`/api/orders/${id}/assign-rider`, { riderId })
}

export const processPayment = (id) => {
  return client.patch(`/api/orders/${id}/payment`, {})
}
