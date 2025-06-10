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
  setTimeout(() => {
    // Aquí haz la petición HTTP al endpoint sincrónico
  }, 500);
  if(!validarCampos()) { return; }
  try {
    const socket = new SockJS('http://localhost:5000/ws');
    clienteAdmin = Stomp.over(socket);
    const nombre = document.getElementById('nombre').value;
    const areaSeleccionada = document.querySelector('input[name="area"]:checked').value;
    clienteAdmin.connect(
      { nombre: nombre, area: areaSeleccionada },
      suscripcionAChat,
      function(error) {
        mostrarMensajeError(error);
        setConectado(false);
      }
    );
    mostrarMensajeExito();
  } catch (error) {
    mostrarMensajeError(error);
    return;
  }
}


function desconectar() {
  if (clienteAdmin !== null) {
    clienteAdmin.disconnect(() => {          
      setConectado(false);
      // Limpiar notificaciones al desconectar
      const lista = document.getElementById('listaNotificaciones');
      if (lista) lista.innerHTML = "";
    });
    clienteAdmin = null;
  }
}
function suscripcionAChat(frame) {     
  setConectado(true); // <-- Movido al inicio de la función
  // Suscripción al canal de notificaciones generales
  clienteAdmin.subscribe('/notificaciones/generales', recibirNotificacion);
  const areaSeleccionada = document.querySelector('input[name="area"]:checked').value;
  // Suscripción al canal personalizado del administrador
  clienteAdmin.subscribe(`/notificaciones/${areaSeleccionada}`, recibirNotificacion);
}
function recibirNotificacion(message) {  
  const data = JSON.parse(message.body);
  const referenciaDivNotificaciones = document.getElementById('listaNotificaciones');
  if (!referenciaDivNotificaciones) return;
  const nuevoParrafo = document.createElement('li');
  nuevoParrafo.className = "list-group-item d-flex flex-column";

  // Contenido principal de la notificación
  const rowDiv = document.createElement('div');
  rowDiv.className = "d-flex align-items-center justify-content-between w-100";

  const contenido = document.createElement('span');
  contenido.innerHTML = `<span style="font-size:1.2em; margin-right:8px;">🔔</span> ${data.contenido ? data.contenido : JSON.stringify(data)}`;

  // Botón de eliminar
  const btnEliminar = document.createElement('button');
  btnEliminar.className = "btn btn-sm btn-outline-danger ms-2";
  btnEliminar.innerHTML = "✖";
  btnEliminar.title = "Eliminar notificación";
  btnEliminar.onclick = function() { nuevoParrafo.remove(); };

  rowDiv.appendChild(contenido);
  rowDiv.appendChild(btnEliminar);
  nuevoParrafo.appendChild(rowDiv);

  // Si hay deudas, crea un acordeón Bootstrap usando solo DOM
  if (data.deudas && Array.isArray(data.deudas) && data.deudas.length > 0) {
    const accordionId = `acordeonDeudas${Date.now()}${Math.floor(Math.random()*1000)}`;
    const accordionDiv = document.createElement('div');
    accordionDiv.className = "accordion mt-2";
    accordionDiv.id = accordionId;

    const itemDiv = document.createElement('div');
    itemDiv.className = "accordion-item";

    // Header
    const header = document.createElement('h2');
    header.className = "accordion-header";
    header.id = `heading${accordionId}`;

    const button = document.createElement('button');
    button.className = "accordion-button collapsed";
    button.type = "button";
    button.setAttribute("data-bs-toggle", "collapse");
    button.setAttribute("data-bs-target", `#collapse${accordionId}`);
    button.setAttribute("aria-expanded", "false");
    button.setAttribute("aria-controls", `collapse${accordionId}`);
    button.textContent = `Ver deudas (${data.deudas.length})`;

    header.appendChild(button);

    // Collapse div
    const collapseDiv = document.createElement('div');
    collapseDiv.id = `collapse${accordionId}`;
    collapseDiv.className = "accordion-collapse collapse";
    collapseDiv.setAttribute("aria-labelledby", `heading${accordionId}`);
    collapseDiv.setAttribute("data-bs-parent", `#${accordionId}`);

    // Accordion body
    const bodyDiv = document.createElement('div');
    bodyDiv.className = "accordion-body";

    // Tabla de deudas
    const table = document.createElement('table');
    table.className = "table table-sm table-bordered mb-0";
    const thead = document.createElement('thead');
    const trHead = document.createElement('tr');
    // Asume que todas las deudas tienen las mismas claves
    const firstDeuda = data.deudas[0];
    Object.keys(firstDeuda).forEach(key => {
      const th = document.createElement('th');
      th.textContent = key;
      trHead.appendChild(th);
    });
    thead.appendChild(trHead);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');
    data.deudas.forEach(deuda => {
      const tr = document.createElement('tr');
      Object.values(deuda).forEach(value => {
        const td = document.createElement('td');
        // Si es una fecha tipo [2025,5,1], formatea a yyyy-mm-dd
        if (Array.isArray(value) && value.length === 3 && value.every(n => typeof n === 'number')) {
          td.textContent = `${value[0]}-${String(value[1]).padStart(2, '0')}-${String(value[2]).padStart(2, '0')}`;
        } else {
          td.textContent = value;
        }
        tr.appendChild(td);
      });
      tbody.appendChild(tr);
    });
    table.appendChild(tbody);

    bodyDiv.appendChild(table);
    collapseDiv.appendChild(bodyDiv);

    itemDiv.appendChild(header);
    itemDiv.appendChild(collapseDiv);
    accordionDiv.appendChild(itemDiv);
    nuevoParrafo.appendChild(accordionDiv);
  }

  referenciaDivNotificaciones.appendChild(nuevoParrafo);
}