package com.bolsadeideas.springboot.backend.chat.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.backend.chat.models.dao.IChatRepository;
import com.bolsadeideas.springboot.backend.chat.models.documents.Mensaje;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private IChatRepository chatRepository;

	@Override
	public List<Mensaje> obtenerUltimos10Mensajes() {
		return this.chatRepository.findFirst10ByOrderByFechaDesc();
	}

	@Override
	public Mensaje guardar(Mensaje mensaje) {
		return this.chatRepository.save(mensaje);
	}

}
