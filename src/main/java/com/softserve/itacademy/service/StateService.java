package com.softserve.itacademy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.softserve.itacademy.dto.StateDto;
import com.softserve.itacademy.exception.CustomErrorsUtils;
import com.softserve.itacademy.exception.EntityNotFoundException;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;
    private final CustomErrorsUtils customErrorsUtils;

    public State create(State state) throws NullEntityReferenceException {
        log.debug("Creating state {}", state);
        customErrorsUtils.validateArgumentLogAndThrow(state, "State cannot be null", "");

        state = stateRepository.save(state);
        log.debug("State {} was created", state);
        return state;
    }

    public State readById(long id) throws EntityNotFoundException  {
        log.debug("Fetching state with id: {}", id);
		Optional<State> stateOpt = customErrorsUtils
				.returnValidatedFindByIdCallOrElseThrow(stateRepository.findById(id), "State", id);

		State state = stateOpt.get();
        log.debug("Get state {}", state);
        
        return state;
    }

    public State update(State state) throws NullEntityReferenceException {
        log.debug("Updating state {}", state);
        
        customErrorsUtils.validateArgumentLogAndThrow(state, "State cannot be null", "");
        
        readById(state.getId());
        state = stateRepository.save(state);
        log.debug("State {} was updated", state);
        return state;

    }

    public void delete(long id) {
        log.debug("Deleting state with id {}", id);
        State state = readById(id);
        stateRepository.delete(state);
        log.debug("State {} was deleted", state);
    }

    public List<State> getAll() {
        log.debug("Fetching all states");
        List<State> states = stateRepository.findAllByOrderById();
        log.debug("Get all states ");
        return states;
    }

	public State getByName(String name) {
		log.debug("Fetching state with name {}", name);
		State state = stateRepository.findByName(name);

		customErrorsUtils.validateArgumentLogAndThrow(state, 
				"State with name '%s' not found".formatted(name),
				"No state found with name: {} %s".formatted(name));

		log.debug("State found: {}", state);
		return state;
	}

    public List<StateDto> findAll() {
        log.debug("Fetching all states as StateDto}");
        List<StateDto> dtos;
        try {
        	dtos = stateRepository.findAll()
                    .stream()
                    .map(this::toDto)
                    .toList();
		} catch (IllegalArgumentException e) {
			log.debug("findAll method resulted with IllegalArgumentException");
			throw new EntityNotFoundException("Some of State objects was/were of 'null' value");
		}
        log.debug("Get all states as StateDto");
        return dtos;
    }

    private StateDto toDto(State state) {
        log.debug("Converting {} from State object to StateDto object", state);
        StateDto dto = StateDto.builder()
                .name(state.getName())
                .build();
        log.debug("Converted {} from State object to StateDto object", state);
        return dto;
    }
}
