import { api } from '../core/api.js';
import { el, celda, mostrarAviso } from '../core/utils.js';

let estudiante = null;

export async function ingresar(estudianteId) {
  estudiante = await api.estudiantePorId(estudianteId);
  document.getElementById('perfil').textContent =
    `${estudiante.nombre} · ${estudiante.programa.nombre} · código ${estudiante.codigo}`;
  document.getElementById('contenido').hidden = false;
  await recargar();
}

async function recargar() {
  const [oferta, carga] = await Promise.all([
    api.gruposDisponibles(estudiante.id),
    api.cargaAcademica(estudiante.id),
  ]);
  const yaMatriculados = new Set(carga.map((m) => m.grupo.id));
  pintarOferta(oferta, yaMatriculados);
  pintarCarga(carga);
}

function filaVacia(cuerpo, columnas, texto) {
  const fila = el('tr');
  fila.append(el('td', texto, { colspan: columnas, class: 'vacio' }));
  cuerpo.append(fila);
}

function pintarOferta(grupos, yaMatriculados) {
  const cuerpo = document.getElementById('tabla-oferta');
  cuerpo.replaceChildren();
  if (!grupos.length) return filaVacia(cuerpo, 6, 'No hay grupos abiertos para tu programa en este periodo.');

  grupos.forEach((g) => {
    const fila = el('tr');
    const boton = el('button', yaMatriculados.has(g.id) ? 'Matriculada' : 'Matricular');
    boton.disabled = yaMatriculados.has(g.id) || g.cupoDisponible <= 0;
    boton.addEventListener('click', () => accion(() => api.matricular(estudiante.id, g.id), `Te matriculaste en ${g.asignatura}.`));
    const accionTd = el('td');
    accionTd.append(boton);
    fila.append(celda(g.asignatura), celda(g.numero), celda(g.horario), celda(g.creditos),
                celda(`${g.cupoDisponible} de ${g.cupo}`), accionTd);
    cuerpo.append(fila);
  });
}

function pintarCarga(matriculas) {
  const cuerpo = document.getElementById('tabla-carga');
  cuerpo.replaceChildren();
  const total = matriculas.reduce((suma, m) => suma + m.grupo.asignatura.creditos, 0);
  document.getElementById('total-creditos').textContent = `Total de créditos: ${total}`;
  if (!matriculas.length) return filaVacia(cuerpo, 4, 'Aún no tienes asignaturas matriculadas. Elige un grupo de la lista de arriba.');

  matriculas.forEach((m) => {
    const fila = el('tr');
    fila.append(celda(m.grupo.asignatura.nombre), celda(m.grupo.numero), celda(m.grupo.horario),
                celda(m.grupo.asignatura.creditos));
    cuerpo.append(fila);
  });
}

async function accion(operacion, mensajeOk) {
  try {
    await operacion();
    mostrarAviso(mensajeOk);
  } catch (error) {
    mostrarAviso(error.message, true);
  }
  await recargar();
}
