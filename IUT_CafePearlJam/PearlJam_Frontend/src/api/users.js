import client from './client'

export const login = (email, password) => {
  return client.post('/users/login', { email, password })
}

export const register = (userData) => {
  return client.post('/users/register', userData)
}

export const logoutApi = () => {
  return client.post('/users/logout')
}

export const getProfile = () => {
  return client.get('/users/profile')
}
