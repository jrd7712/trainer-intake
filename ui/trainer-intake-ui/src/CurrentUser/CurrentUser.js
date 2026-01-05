import { useEffect, useState } from "react";
import { apiFetch } from "../api";

export function useCurrentUser() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");

    // If no token, user is not logged in — do NOT call /auth/me
    if (!token) {
      setLoading(false);
      return;
    }

    let cancelled = false;

    async function loadUser() {
      try {
        const data = await apiFetch("http://localhost:8080/auth/me");
        if (!cancelled) setUser(data);
      } catch (err) {
        console.error("Failed to load user", err);
      } finally {
        if (!cancelled) setLoading(false);
      }
    }

    loadUser();

    return () => {
      cancelled = true; // prevents Strict Mode double-fetch
    };
  }, []);

  return { user, loading };
}