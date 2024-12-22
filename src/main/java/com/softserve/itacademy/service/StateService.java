package com.softserve.itacademy.service;

import com.softserve.itacademy.dto.StateDto;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository stateRepository;

    private static void checkOnNullEntity(State state) {
        if (state == null) throw new NullEntityReferenceException("State cannot be null");
    }

    public State create(State state) {
        log.debug("Creating state {}", state);

        checkOnNullEntity(state);

        state = stateRepository.save(state);
        log.debug("State {} was created", state);
        return state;
    }

    public State readById(long id) {
        log.debug("Fetching state with id: {}", id);
        State state = stateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("State with id {} doesn't exists", id);
                    return new EntityNotFoundException("State with id " + id + " not found");
                });
        log.debug("Get state {}", state);
        return state;
    }

    public State update(State state) {
        log.debug("Updating state {}", state);
        checkOnNullEntity(state);

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
        State state;
        if (! name.isEmpty()) {
            state = stateRepository.findByName(name);
            checkOnNullEntity(state);
            return state;
        }
        throw new EntityNotFoundException("State with name '' not found");
    }

    public List<StateDto> findAll() {
        log.debug("Fetching all states as StateDto}");
        List<StateDto> dtos = stateRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
        log.debug("Get all states as StateDto");
        return dtos;
    }

    private StateDto toDto(State state) {
        checkOnNullEntity(state);
        log.debug("Converting {} from State object to StateDto object", state);
        StateDto dto = StateDto.builder()
                .name(state.getName())
                .build();
        log.debug("Converted {} from State object to StateDto object", state);
        return dto;
    }
}
