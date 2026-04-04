/** @type {import('tailwindcss').Config} */
export default {
  darkMode: ["class"],
  content: [
    './pages/**/*.{js,jsx}',
    './components/**/*.{js,jsx}',
    './app/**/*.{js,jsx}',
    './src/**/*.{js,jsx}',
	],
  theme: {
    container: {
      center: true,
      padding: "2rem",
      screens: {
        "2xl": "1400px",
      },
    },
    extend: {
      colors: {
        background: '#FAF8F4',
        surface:    '#F0EDE6',
        border:     '#E2DDD5',
        text: {
          primary: '#1A1A18',
          muted:   '#6B6760',
        },
        accent: {
          DEFAULT: '#2D5016',
          hover:   '#3D6B1F',
          light:   '#EAF0E4',
        },
        danger:    '#C0392B',
        white:     '#FFFFFF',
        status: {
          pending:    '#D97706',
          confirmed:  '#2563EB',
          preparing:  '#7C3AED',
          ready:      '#0D9488',
          delivering: '#EA580C',
          delivered:  '#2D5016',
          cancelled:  '#C0392B',
        },
      },
      fontFamily: {
        sans:  ['Inter', 'sans-serif'],
        serif: ['Fraunces', 'Playfair Display', 'serif'],
      },
      borderRadius: {
        card:   '12px',
        pill:   '100px',
        input:  '8px',
        lg: "var(--radius)",
        md: "calc(var(--radius) - 2px)",
        sm: "calc(var(--radius) - 4px)",
      },
      keyframes: {
        "accordion-down": {
          from: { height: 0 },
          to: { height: "var(--radix-accordion-content-height)" },
        },
        "accordion-up": {
          from: { height: "var(--radix-accordion-content-height)" },
          to: { height: 0 },
        },
        "pulse-dot": {
          "0%, 100%": { opacity: 1, transform: "scale(1)" },
          "50%": { opacity: 0.5, transform: "scale(1.2)" },
        }
      },
      animation: {
        "accordion-down": "accordion-down 0.2s ease-out",
        "accordion-up": "accordion-up 0.2s ease-out",
        "pulse-dot": "pulse-dot 2s ease-in-out infinite",
      },
    },
  },
  plugins: [import("tailwindcss-animate")],
}
