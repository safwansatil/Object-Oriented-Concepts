import { useQuery } from '@tanstack/react-query'
import { getMenuByRestaurant } from '../api/menu'

export const useMenu = (restaurantId) => {
  return useQuery({
    queryKey: ['menu', restaurantId],
    queryFn: async () => {
      const menu = await getMenuByRestaurant(restaurantId)
      
      // Group by category, preserving original order from backend if possible
      const menuByCategory = new Map()
      menu.forEach(item => {
        const categoryName = item.category?.name || 'Uncategorized'
        if (!menuByCategory.has(categoryName)) {
          menuByCategory.set(categoryName, [])
        }
        menuByCategory.get(categoryName).push(item)
      })
      
      return menuByCategory
    },
    enabled: !!restaurantId,
  })
}
