/* * 
Funciones para manejar la conexión y comunicación con el servidor WebSocket*/
// Variables globales
let clienteAdmin = null
/**
 * Función para habilitar o deshabilitar los botones y campos de entrada
 * @param {boolean} conectado - Indica si el cliente está conectado o no  
 */
function setConectado(conectado) {
  document.getElementById('btnConectar').disabled = conectado;
  document.getElementById('nombre').disabled = conectado;
  document.getElementById('btnDesconectar').disabled = !conectado;
  document.getElementById('notificaciones').hidden = !conectado;
}
function mostrarMensajeExito() {
    console.log('Administrador conectado exitosamente');
    // Mostrar mensaje de éxito con la librería Toastify
    Toastify({
        text: "✅ ¡Conexión exitosa!",
        duration: 2000,            // Duración: 2 segundos
        gravity: "top",             // Posición: arriba
        position: "right",          // Alineación: derecha
        style: {
            background: "rgba(0, 128, 0, 0.8)",  // Verde con transparencia
            color: "#fff",                      // Texto blanco
            borderRadius: "12px",               // Esquinas redondeadas
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.3)", // Sombra ligera
            padding: "12px 20px"               // Más relleno
        },
        stopOnFocus: true, // No desaparecer al pasar el mouse
    }).showToast();
}
function mostrarMensajeError(error) {
  console.error('Error al crear el cliente Stomp:', error);
  Toastify({
      text: "❌ Error al conectar al servidor",
      duration: 3000,
      gravity: "top",
      position: "right",
      style: {
          background: "rgba(255, 0, 0, 0.8)", // Rojo con transparencia
          color: "#fff",
          borderRadius: "12px",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.3)",
          padding: "12px 20px"
      },
      stopOnFocus: true,
  }).showToast();
}
function validarCampoObligatorio(campo, errorElement, mensaje) {
    if (campo.value.trim() === '') {
        errorElement.textContent = mensaje;
        return false;
    } else {
        errorElement.textContent = '';
        return true;
    }
}
function validarArea(area, errorElement, mensaje) {
    let seleccionado = Array.from(area).some(radio => radio.checked);    
    // Verifica si al menos un radio button está seleccionado
    if (!seleccionado) {
        errorElement.textContent = mensaje;
        return false;
    } else {
        errorElement.textContent = '';
        return true;
    }
}
function validarCampos() {
  const inputNombre = document.getElementById('nombre');    
  const inputEstado = document.getElementsByName('area');
  const labelErrorNombre = document.getElementById('errorNombre');
  const labelErrorArea = document.getElementById('errorArea');
  const nombreValido = validarCampoObligatorio(inputNombre, labelErrorNombre, 'El nombre es obligatorio');
  const areaValida = validarArea(inputEstado, labelErrorArea, 'Debe seleccionar un área');
  if (!nombreValido || !areaValida) {
    return false;
  }
  return true;
}
function conectar() {  
  if(!validarCampos()) { return; }
  try {
    const socket = new SockJS('http://localhost:5000/ws');
    clienteAdmin = Stomp.over(socket)
    const nombre = document.getElementById('nombre').value
    const areaSeleccionada = document.querySelector('input[name="area"]:checked');
    clienteAdmin.connect(
      { nombre: nombre },
      { area: areaSeleccionada },
      suscripcionAChat,
      function(error) { // <-- Callback de error
        mostrarMensajeError(error);
        setConectado(false);
      }
    );
  } catch (error) {
    mostrarMensajeError(error);
    return;
  }
}
function desconectar() {
  if (clienteAdmin !== null) {
    clienteAdmin.disconnect(() => {          
      setConectado(false);
    });
    clienteAdmin = null;
  }
}
function suscripcionAChat(frame) {     
  // Suscripción al canal de notificaciones generales
  clienteAdmin.subscribe('/notificaciones/generales', recibirNotificacion);
  const areaSeleccionada = document.querySelector('input[name="area"]:checked');
  // Suscripción al canal personalizado del administrador
  clienteAdmin.subscribe(`/notificaciones/${areaSeleccionada}`, recibirNotificacion);
  setConectado(true);
}
function recibirNotificacion(message) {
  const data = JSON.parse(message.body);
  const referenciaDivNotificaciones = document.getElementById('listaNotifiaciones');
  const nuevoParrafo = document.createElement('li');
  nuevoParrafo.textContent = data.contenido;
  referenciaDivNotificaciones.appendChild(nuevoParrafo);
}

