import { useState, useCallback, useMemo } from 'react'
import { useMutation, useQueryClient } from '@tanstack/react-query'
import * as authApi from '../api/users'

export const useAuth = () => {
  const queryClient = useQueryClient()
  const [user, setUser] = useState(() => {
    const saved = localStorage.getItem('pearljam-user')
    return saved ? JSON.parse(saved) : null
  })

  const loginMutation = useMutation({
    mutationFn: ({ email, password }) => authApi.login(email, password),
    onSuccess: (data) => {
      setUser(data)
      localStorage.setItem('pearljam-user', JSON.stringify(data))
      queryClient.setQueryData(['user'], data)
    },
  })

  const logout = useCallback(async () => {
    try {
      await authApi.logoutApi()
    } finally {
      setUser(null)
      localStorage.removeItem('pearljam-user')
      queryClient.clear()
      window.location.href = '/'
    }
  }, [queryClient])

  const isAuthenticated = !!user

  return useMemo(() => ({
    user,
    login: loginMutation.mutateAsync,
    isLoading: loginMutation.isPending,
    error: loginMutation.error,
    logout,
    isAuthenticated,
  }), [user, loginMutation, logout, isAuthenticated])
}
