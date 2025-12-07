// src/api.js

/**
 * Unified API fetch helper with JWT support
 * - Automatically attaches Authorization header if token exists
 * - Handles JSON requests/responses
 * - Redirects to login on 401/403
 */
export async function apiFetch(url, options = {}) {
  // Retrieve token from localStorage (make sure you save it here on login)
  const token = localStorage.getItem("token");

  // Build headers
  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {}),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
  };

  // Debug: uncomment if you want to see headers in console
  // console.log("Request URL:", url);
  // console.log("Request Headers:", headers);

  // Perform fetch
  const response = await fetch(url, {
    ...options,
    headers,
  });

  // Handle errors
  if (!response.ok) {
    if (response.status === 401 || response.status === 403) {
      // Token missing/expired/invalid → force re-login
      alert("Session expired or unauthorized. Please log in again.");
      window.location.href = "/login";
    }
    throw new Error(`HTTP error! Status: ${response.status}`);
  }

  // Parse JSON response
  try {
    return await response.json();
  } catch (err) {
    // If response is not JSON, return raw text
    return await response.text();
  }
}