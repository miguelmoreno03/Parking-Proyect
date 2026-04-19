/** @type {import('tailwindcss').Config} */
export default {
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        primary: "#2563EB",
        background: "#020617",
        surface: "#0f172a",
        "surface-container": "#1e293b",
        "surface-container-high": "#334155",
        "on-background": "#f8fafc",
        "on-surface": "#f1f5f9",
        "on-surface-variant": "#94a3b8",
        outline: "#475569",
        "outline-variant": "#1e293b",
        tertiary: "#10b981",
        error: "#ef4444",
      },
      borderRadius: {
        DEFAULT: "0.25rem",
        lg: "0.25rem",
        xl: "0.25rem",
        full: "0.25rem",
      },
      fontFamily: {
        headline: ["Inter", "sans-serif"],
        body: ["Inter", "sans-serif"],
        label: ["Inter", "sans-serif"],
      },
    },
  },
};