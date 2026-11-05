// La sesión vive en sessionStorage: se borra al cerrar la pestaña.
const CLAVE = 'sesion';

export function guardarSesion(sesion) { sessionStorage.setItem(CLAVE, JSON.stringify(sesion)); }
export function leerSesion() {
  try { return JSON.parse(sessionStorage.getItem(CLAVE)); } catch { return null; }
}
export function cerrarSesion() { sessionStorage.removeItem(CLAVE); }
