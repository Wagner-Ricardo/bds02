package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;

import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.DataBaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;


@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	
	
	@Transactional
	public EventDTO update (Long id, EventDTO eventdto) {
		
		try {
			Event entity  =  eventRepository.getOne(id);
			entity.setName(eventdto.getName());
			entity.setDate(eventdto.getDate());
			entity.setUrl(eventdto.getUrl());
			entity.setCity(new City(eventdto.getCityId(), null));
			entity = eventRepository.save(entity);
			return new EventDTO(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	
		
	}

}
