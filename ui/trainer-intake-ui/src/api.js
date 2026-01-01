// src/api.js

/**
 * Unified API fetch helper with JWT support
 * - Automatically attaches Authorization header if token exists
 * - Handles JSON requests/responses
 * - Redirects to login on 401/403
 */
export async function apiFetch(url, options = {}) {
  const token = localStorage.getItem("token");

  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {}),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
  };

  const response = await fetch(url, {
    ...options,
    headers,
  });

  if (!response.ok) {
    if (response.status === 401 || response.status === 403) {
      alert("Session expired or unauthorized. Please log in again.");
      window.location.href = "/login";
    }
    throw new Error(`HTTP error! Status: ${response.status}`);
  }

  // ✅ Read body once, then decide how to parse
  const raw = await response.text();

  try {
    return JSON.parse(raw); // attempt JSON parse
  } catch {
    return raw; // fallback to plain text
  }
}