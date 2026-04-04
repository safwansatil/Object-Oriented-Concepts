import client from './client'

export const validateCoupon = (code, subtotal) => {
  return client.get('/coupons/validate', { params: { code, subtotal } })
}
