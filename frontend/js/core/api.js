import { API_BASE } from './config.js';

/** Llama al backend y lanza Error con el mensaje que devuelve la API. */
async function pedir(ruta, opciones = {}) {
  const respuesta = await fetch(`${API_BASE}${ruta}`, {
    headers: { 'Content-Type': 'application/json' },
    ...opciones,
  });
  if (!respuesta.ok) {
    let mensaje = `Error ${respuesta.status}`;
    try { mensaje = (await respuesta.json()).mensaje || mensaje; } catch { /* sin cuerpo JSON */ }
    throw new Error(mensaje);
  }
  return respuesta.json();
}

export const api = {
  login: (username, password) =>
    pedir('/auth/login', { method: 'POST', body: JSON.stringify({ username, password }) }),
  estudiantePorId: (id) => pedir(`/estudiantes/${id}`),
  gruposDisponibles: (estudianteId) => pedir(`/grupos/disponibles?estudianteId=${estudianteId}`),
  cargaAcademica: (estudianteId) => pedir(`/matriculas?estudianteId=${estudianteId}`),
  matricular: (estudianteId, grupoId) =>
    pedir('/matriculas', { method: 'POST', body: JSON.stringify({ estudianteId, grupoId }) }),
};
