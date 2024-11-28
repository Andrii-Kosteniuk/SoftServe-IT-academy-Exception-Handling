package com.softserve.itacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softserve.itacademy.dto.TaskDto;
import com.softserve.itacademy.dto.TaskTransformer;
import com.softserve.itacademy.exception.CustomErrorsUtils;
import com.softserve.itacademy.exception.EntityNotFoundException;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.repository.ToDoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ToDoRepository toDoRepository;
    private final StateRepository stateRepository;
    private final TaskTransformer taskTransformer;
    private final CustomErrorsUtils customErrorsUtils;

    public TaskDto create(TaskDto taskDto) throws NullEntityReferenceException {
        log.info("Creating task with details: {}", taskDto);
        
        customErrorsUtils.validateArgumentLogAndThrow(taskDto, 
        		"Task cannot be null", 
        		"Failed to create task: taskDto is null");
        
        var todoOpt = customErrorsUtils.returnValidatedFindByIdCallOrElseThrow(
        		toDoRepository.findById(taskDto.getTodoId()), 
        		"ToDo", 
        		taskDto.getTodoId());
        State state = stateRepository.findByName("New");
        customErrorsUtils.validateArgumentLogAndThrow(state, 
        		"State cannot be null", 
        		"Failed to find State: state is null");
        
        Task task = taskTransformer.fillEntityFields(
                new Task(),
                taskDto,
                todoOpt.get(),
                state
        );

        Task savedTask = taskRepository.save(task);
        log.info("Task with id {} created successfully", savedTask.getId());
        return taskTransformer.convertToDto(savedTask);
    }

    public Task readById(long id) throws EntityNotFoundException {
        log.info("Reading task with id {}", id);
       
        var taskOpt = customErrorsUtils.returnValidatedFindByIdCallOrElseThrow(
        		taskRepository.findById(id), 
        		"Task", 
        		id);
        
        return taskOpt.get();
    }

    public TaskDto update(TaskDto taskDto) {
        log.info("Updating task with id {}", taskDto.getId());
        
        customErrorsUtils.validateArgumentLogAndThrow(taskDto, 
        		"Task cannot be 'null'", 
        		"Failed to update task: taskDto is null");
        
        var taskOpt = customErrorsUtils.returnValidatedFindByIdCallOrElseThrow(taskRepository.findById(taskDto.getId()), "Task", taskDto.getId());
        var todoOpt = customErrorsUtils.returnValidatedFindByIdCallOrElseThrow(toDoRepository.findById(taskDto.getTodoId()), "ToDo" , taskDto.getTodoId());
        var stateOpt = customErrorsUtils.returnValidatedFindByIdCallOrElseThrow(stateRepository.findById(taskDto.getStateId()), "State", taskDto.getStateId());
        
        Task updatedTask = taskTransformer.fillEntityFields(
        		taskOpt.get(),
                taskDto,
                todoOpt.get(),
                stateOpt.get()
        );

        Task savedTask = taskRepository.save(updatedTask);
        log.info("Task with id {} updated successfully", savedTask.getId());
        return taskTransformer.convertToDto(savedTask);
    }

    public void delete(long id) {
        log.info("Deleting task with id {}", id);
        Task task = readById(id);
        taskRepository.delete(task);
        log.info("Task with id {} was deleted", id);
    }

    public List<Task> getAll() {
        log.info("Retrieving all tasks");
        return taskRepository.findAll();
    }

    public List<Task> getByTodoId(long todoId) {
        log.info("Retrieving tasks for ToDo with id {}", todoId);
        return taskRepository.getByTodoId(todoId);
    }
    
}
