import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/foodapp/api'

const client = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor: attach auth token
client.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem('pearljam-user'))
    if (user?.token) {
      config.headers.Authorization = `Bearer ${user.token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

// Response interceptor: handle 401 and generic errors
client.interceptors.response.use(
  (response) => response.data,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('pearljam-user')
      if (window.location.pathname !== '/login') {
        window.location.href = '/login'
      }
    }

    const appError = {
      message: error.response?.data?.message || 'An unexpected error occurred',
      status: error.response?.status || 500,
      code: error.response?.data?.code || 'UNKNOWN_ERROR',
      original: error,
    }

    return Promise.reject(appError)
  }
)

export default client
