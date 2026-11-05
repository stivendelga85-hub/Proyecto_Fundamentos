import { api } from './core/api.js';
import { guardarSesion } from './core/session.js';
import { mostrarAviso } from './core/utils.js';

document.getElementById('form-login').addEventListener('submit', async (evento) => {
  evento.preventDefault();
  const username = document.getElementById('username').value.trim();
  const password = document.getElementById('password').value;
  try {
    guardarSesion(await api.login(username, password));
    window.location.href = 'matriculas.html';
  } catch (error) {
    mostrarAviso(error.message, true);
  }
});
