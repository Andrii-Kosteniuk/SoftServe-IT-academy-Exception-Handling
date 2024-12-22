package com.softserve.itacademy.service;

import com.softserve.itacademy.exception.CustomErrorsUtils;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ToDoService {
    private final ToDoRepository todoRepository;
    private final CustomErrorsUtils customErrorsUtils;
    
    @Autowired
    public ToDoService(ToDoRepository todoRepository, CustomErrorsUtils customErrorsUtils) {
		this.todoRepository = todoRepository;
		this.customErrorsUtils = customErrorsUtils;
	}

	public ToDo create(ToDo todo) {
        log.info("Creating a new ToDo: {}", todo);
        customErrorsUtils.validateArgumentLogAndThrow(todo, "ToDo cannot be null", "ToDo creation failed: ToDo is null");
        ToDo savedToDo = todoRepository.save(todo);
        log.info("ToDo created successfully with ID: {}", savedToDo.getId());
        return savedToDo;
    }

    public ToDo readById(long id) throws  EntityNotFoundException{
        log.info("Reading ToDo with ID: {}", id);
        var todoOpt = customErrorsUtils.returnValidatedFindByIdCallOrElseThrow(todoRepository.findById(id), "ToDo", id);
        return todoOpt.get();
    }

    public ToDo update(ToDo todo) {
    	
    	customErrorsUtils.validateArgumentLogAndThrow(todo, "ToDo cannot be null", "ToDo update failed: ToDo is null");

        log.info("Updating ToDo with ID: {}", todo.getId());

        readById(todo.getId()); 
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
