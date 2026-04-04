import * as React from "react"
import { Slot } from "@radix-ui/react-slot"
import { cva } from "class-variance-authority"
import { cn } from "../../lib/utils"

const buttonVariants = cva(
  "inline-flex items-center justify-center whitespace-nowrap rounded-pill text-sm font-medium ring-offset-background transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 active:scale-95",
  {
    variants: {
      variant: {
        default: "bg-accent text-white hover:bg-accent-hover shadow-sm",
        destructive:
          "bg-danger text-white hover:bg-danger/90",
        outline:
          "border border-border bg-transparent hover:bg-surface hover:text-accent",
        secondary:
          "bg-surface text-text-primary hover:bg-surface/80",
        ghost: "hover:bg-accent/10 hover:text-accent",
        link: "text-accent underline-offset-4 hover:underline",
        premium: "bg-gradient-to-tr from-accent to-accent-hover text-white shadow-md hover:shadow-lg",
      },
      size: {
        default: "h-12 px-8 py-2",
        sm: "h-9 rounded-pill px-4",
        lg: "h-14 rounded-pill px-10 text-base",
        icon: "h-10 w-10",
      },
    },
    defaultVariants: {
      variant: "default",
      size: "default",
    },
  }
)

const Button = React.forwardRef(({ className, variant, size, asChild = false, ...props }, ref) => {
  const Comp = asChild ? Slot : "button"
  return (
    <Comp
      className={cn(buttonVariants({ variant, size, className }))}
      ref={ref}
      {...props}
    />
  )
})
Button.displayName = "Button"

export { Button, buttonVariants }
