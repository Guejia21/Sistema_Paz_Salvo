    let clienteChat = null;

    function setConectado(conectado) {
      document.getElementById('btnConectar').disabled = conectado;
      document.getElementById('nicknameOrigen').disabled = conectado;
      document.getElementById('btnDesconectar').disabled = !conectado;
      document.getElementById('message').disabled = !conectado;      
      document.getElementById('btnEnviarGrupal').disabled = !conectado;
      document.getElementById('messagePrivado').disabled = !conectado;      
      document.getElementById('nicknameDestino').disabled = !conectado;
      document.getElementById('btnEnviarPrivado').disabled = !conectado;
    }
    

    function conectar() {
      const socket = new SockJS('http://localhost:5000/ws');
      clienteChat = Stomp.over(socket);

      const nickname = document.getElementById('nicknameOrigen').value;

      clienteChat.connect(
        { nickname: nickname },
        suscripcionAChat
      );
    }

    function desconectar() {
      if (clienteChat !== null) {
        clienteChat.disconnect(() => {          
          setConectado(false);
        });
        clienteChat = null;
      }
    }

    function suscripcionAChat(frame) {     
      // Suscripción al chat grupal
      clienteChat.subscribe('/chatGrupal/salaChatPublica', recibirMensajeGrupal);
      const nickname = document.getElementById('nicknameOrigen').value;    
      // Suscripción al canal privado del usuario
      clienteChat.subscribe(`/chatPrivado/${nickname}`, recibirMensajePrivado);
      // Suscripción a las notificaciones generales de conexión/desconexión de usuarios
      clienteChat.subscribe('/chatGrupal/notificaciones', recibirNotificacion);
      setConectado(true);
    }

    function recibirMensajeGrupal(message) {
      const data = JSON.parse(message.body);
      const referenciaDivMensajes = document.getElementById('mensajesGrupales');
      const nuevoParrafo = document.createElement('p');
      nuevoParrafo.textContent = data.contenido;
      referenciaDivMensajes.appendChild(nuevoParrafo);
    }

    function recibirMensajePrivado(message) {
      const data = JSON.parse(message.body);
      const referenciaDivMensajes = document.getElementById('mensajesPrivados');
      const nuevoParrafo = document.createElement('p');
      nuevoParrafo.textContent = data.contenido;
      referenciaDivMensajes.appendChild(nuevoParrafo);
    }

    function recibirNotificacion(message) {
      const data = message.body;
      const referenciaDivMensajes = document.getElementById('usuariosConectados');
      const nuevoParrafo = document.createElement('p');
      nuevoParrafo.textContent = data;
      referenciaDivMensajes.appendChild(nuevoParrafo);
    }

    function enviarMensajeGrupalServidor() {
      const input = document.getElementById('message');
      const nicknameInput = document.getElementById('nicknameOrigen');
      if (clienteChat && clienteChat.connected) {
          const mensaje = {
          contenido: input.value,
          nickname: nicknameInput.value
        };
    
        clienteChat.send("/apiChat/enviarMensajePublico", {}, JSON.stringify(mensaje));
        input.value = '';
      } else {
        alert("No estás conectado.");
      }
    }

    function enviarMensajePrivadoServidor() {
      const input = document.getElementById('messagePrivado');
      const nicknameOrigenInput = document.getElementById('nicknameOrigen');
      const nicknameDestinoInput = document.getElementById('nicknameDestino');
      if (clienteChat && clienteChat.connected) {
          const mensaje = {
          contenido: input.value,
          nicknameOrigen: nicknameOrigenInput.value,
          nicknameDestino: nicknameDestinoInput.value          

        };
    
        clienteChat.send("/apiChat/enviarMensajePrivado", {}, JSON.stringify(mensaje));
        input.value = '';
      } else {
        alert("No estás conectado.");
      }
    }