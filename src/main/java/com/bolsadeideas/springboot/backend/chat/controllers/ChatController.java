package com.bolsadeideas.springboot.backend.chat.controllers;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.bolsadeideas.springboot.backend.chat.models.documents.Mensaje;
import com.bolsadeideas.springboot.backend.chat.models.services.ChatService;

@Controller
public class ChatController {
	
	private String[] colores = {"red", "blue", "orange", "pink", "green", "purple"};
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private SimpMessagingTemplate webSocket;

	@MessageMapping("/mensaje") //Para publicar mensaje /app/mensaje (se debe agregar el /app configurado en WebSocketConfig)
	@SendTo("/chat/mensaje") //Para enviar el objeto mensaje
	public Mensaje recibeMensaje(Mensaje mensaje) {
		mensaje.setFecha(new Date().getTime());
		
		if(mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setTexto("Nuevo usuario");			
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
		} else {
			this.chatService.guardar(mensaje);
		}
		
		return mensaje;
	}
	
	@MessageMapping("/escribiendo") 
	@SendTo("/chat/escribiendo")
	public String estaEscribiendo(String username) {
		return username.concat(" está escribiendo...");
	}
	
	@MessageMapping("/historial")
	public void historial(String clienteId) {
		this.webSocket.convertAndSend("/chat/historial/" + clienteId, this.chatService.obtenerUltimos10Mensajes());
	}

}
