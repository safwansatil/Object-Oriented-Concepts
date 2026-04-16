import client from './client'

export const login = (email, password) => {
  return client.post('/api/users/login', { email, password })
}

export const register = (userData) => {
  return client.post('/api/users/register', userData)
}
