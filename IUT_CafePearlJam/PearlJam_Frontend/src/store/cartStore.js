import { create } from 'zustand'
import { persist } from 'zustand/middleware'

export const useCartStore = create(
  persist(
    (set, get) => ({
      restaurantId: null,
      restaurantName: null,
      items: [],

      addItem: (item) => {
        const { restaurantId, items } = get()
        
        // If different restaurant, prompt handled by component, but logic here for safety
        if (restaurantId && restaurantId !== item.restaurantId) {
          // This should be pre-handled by UI to ask for clear, but if it gets here:
          set({
            restaurantId: item.restaurantId,
            restaurantName: item.restaurantName,
            items: [item]
          })
          return
        }

        const existingItemIndex = items.findIndex(i => 
          i.menuItemId === item.menuItemId && 
          JSON.stringify(i.selectedAddons) === JSON.stringify(item.selectedAddons)
        )

        if (existingItemIndex > -1) {
          const newItems = [...items]
          newItems[existingItemIndex].quantity += item.quantity
          set({ items: newItems })
        } else {
          set({ 
            restaurantId: item.restaurantId, 
            restaurantName: item.restaurantName,
            items: [...items, item] 
          })
        }
      },

      removeItem: (menuItemId, selectedAddons) => {
        const { items } = get()
        const newItems = items.filter(i => 
          !(i.menuItemId === menuItemId && JSON.stringify(i.selectedAddons) === JSON.stringify(selectedAddons))
        )
        
        if (newItems.length === 0) {
          set({ items: [], restaurantId: null, restaurantName: null })
        } else {
          set({ items: newItems })
        }
      },

      updateQuantity: (menuItemId, selectedAddons, qty) => {
        const { items } = get()
        const newItems = items.map(i => {
          if (i.menuItemId === menuItemId && JSON.stringify(i.selectedAddons) === JSON.stringify(selectedAddons)) {
            return { ...i, quantity: Math.max(1, qty) }
          }
          return i
        })
        set({ items: newItems })
      },

      clearCart: () => set({ items: [], restaurantId: null, restaurantName: null }),

      getSubtotal: () => {
        const { items } = get()
        return items.reduce((total, item) => {
          const addonsPrice = item.selectedAddons.reduce((sum, a) => sum + a.extraPrice, 0)
          return total + (item.basePrice + addonsPrice) * item.quantity
        }, 0)
      },

      getItemCount: () => {
        const { items } = get()
        return items.reduce((total, item) => total + item.quantity, 0)
      }
    }),
    {
      name: 'pearljam-cart-storage',
    }
  )
)
