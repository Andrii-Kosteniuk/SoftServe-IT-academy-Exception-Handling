package com.softserve.itacademy.service;

import com.softserve.itacademy.ValidationHandler;
import com.softserve.itacademy.exception.EntityNotFoundException;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoRepository todoRepository;
    private final ValidationHandler handler;

    public ToDo create(ToDo todo) {
        log.info("Creating a new ToDo: {}", todo);
        handler.onNullValidation(todo, ToDo.class);
        ToDo savedToDo = todoRepository.save(todo);
        log.info("ToDo created successfully with ID: {}", savedToDo.getId());
        return savedToDo;
    }

    public ToDo readById(long id) {
        log.info("Reading ToDo with ID: {}", id);
        return todoRepository.findById(id).orElseThrow(() -> {
            log.error("ToDo with ID {} not found", id);
            return new EntityNotFoundException("ToDo with id " + id + " not found");
        });
    }

    public ToDo update(ToDo todo) {
        log.info("Updating ToDo with ID: {}", todo.getId());
        handler.onNullValidation(todo, ToDo.class);

        Optional.ofNullable(readById(todo.getId())).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + todo.getId() + " not found")
        );

        ToDo updatedToDo = todoRepository.save(todo);
        log.info("ToDo with ID {} updated successfully", updatedToDo.getId());
        return updatedToDo;
    }

    public void delete(long id) {
        log.info("Deleting ToDo with ID: {}", id);
        ToDo todo = readById(id);
        todoRepository.delete(todo);

        log.info("ToDo with ID {} deleted successfully", id);
    }

    public List<ToDo> getAll() {
        log.info("Fetching all ToDos");
        List<ToDo> todos = todoRepository.findAll();
        log.debug("Fetched ToDos: {}", todos);
        return todos;
    }

    public List<ToDo> getByUserId(long userId) {
        log.info("Fetching ToDos for User ID: {}", userId);
        List<ToDo> todos = todoRepository.getByUserId(userId);
        log.debug("Fetched ToDos for User ID {}: {}", userId, todos);
        return todos;
    }
}
