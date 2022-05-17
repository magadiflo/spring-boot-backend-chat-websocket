package com.bolsadeideas.springboot.backend.chat.controllers;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.bolsadeideas.springboot.backend.chat.models.documents.Mensaje;

@Controller
public class ChatController {

	@MessageMapping("/mensaje") //Para publicar mensaje /app/mensaje (se debe agregar el /app configurado en WebSocketConfig)
	@SendTo("/chat/mensaje") //Para enviar el objeto mensaje
	public Mensaje recibeMensaje(Mensaje mensaje) {
		mensaje.setFecha(new Date().getTime());
		mensaje.setTexto("Recibido por el broker: ".concat(mensaje.getTexto()));
		return mensaje;
	}

}
