import { ingresar } from './features/matricula.js';
import { leerSesion, cerrarSesion } from './core/session.js';
import { mostrarAviso } from './core/utils.js';

const sesion = leerSesion();
if (!sesion) {
  window.location.href = 'index.html';
} else {
  document.getElementById('usuario-actual').textContent = `${sesion.nombre} (${sesion.rol})`;
  document.getElementById('btn-salir').addEventListener('click', () => {
    cerrarSesion();
    window.location.href = 'index.html';
  });

  if (sesion.rol !== 'ESTUDIANTE') {
    document.getElementById('contenido').hidden = true;
    mostrarAviso('Las pantallas de este rol se construyen en el siguiente avance. Entra como estudiante (ana, luis o sofia).');
  } else {
    ingresar(sesion.estudianteId).catch((error) => mostrarAviso(error.message, true));
  }
}
