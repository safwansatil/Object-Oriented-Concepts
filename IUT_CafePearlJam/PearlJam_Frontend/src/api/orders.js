import client from './client'

export const placeOrder = (orderData) => {
  return client.post('/orders', orderData)
}

export const getOrderById = (id) => {
  return client.get(`/orders/${id}`)
}

export const getMyOrders = () => {
  return client.get('/orders/my')
}

export const getAvailableOrders = () => {
  return client.get('/orders/available')
}

export const claimOrder = (orderId, riderId) => {
  return client.patch(`/orders/${orderId}/assign`, { riderId })
}

export const updateOrderStatus = (id, status) => {
  return client.patch(`/orders/${id}/status`, { status })
}
