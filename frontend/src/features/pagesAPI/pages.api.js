// frontend/src/features/pagesAPI/pages.api.js
// Requests go through the Vite dev proxy → http://localhost:5052

export async function fetchArtifacts(params = {}) {
  const url = new URL("/api/artifacts", window.location.origin);

  Object.keys(params).forEach((key) => {
    if (params[key] !== null && params[key] !== "") {
      url.searchParams.append(key, params[key]);
    }
  });

  const response = await fetch(url);
  if (!response.ok) {
    throw new Error("Failed to fetch artifacts");
  }

  return response.json();
}

export async function fetchArtifactById(id) {
  const response = await fetch(`/api/artifacts/${id}`);

  if (!response.ok) {
    throw new Error(`Artifact with ID ${id} not found`);
  }

  return response.json();
}
