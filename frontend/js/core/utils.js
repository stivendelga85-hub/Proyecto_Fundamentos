/** Crea un elemento con texto y atributos; textContent evita inyectar HTML (XSS). */
export function el(etiqueta, texto = '', atributos = {}) {
  const nodo = document.createElement(etiqueta);
  nodo.textContent = texto;
  Object.entries(atributos).forEach(([clave, valor]) => nodo.setAttribute(clave, valor));
  return nodo;
}

export function celda(texto) { return el('td', texto ?? ''); }

export function mostrarAviso(mensaje, esError = false) {
  const aviso = document.getElementById('aviso');
  aviso.textContent = mensaje;
  aviso.classList.toggle('error', esError);
  aviso.hidden = false;
}
