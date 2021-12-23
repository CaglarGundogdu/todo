package com.ws.todoserver.service;

import java.sql.Timestamp;
import java.util.List;

import com.ws.todoserver.model.entity.Todo;
import com.ws.todoserver.model.entity.TodoGroup;
import com.ws.todoserver.model.entity.TodoPriority;

public interface TodoService {
	public Todo findById(Long todoId); 
	
	public Todo createTodo(Todo todo);

	public Todo updateTodo(Todo todo);
	
	public void deleteTodo(Todo todo);
	
	public TodoGroup findTodoGroupById(Long todoGroupId);
	
	public TodoGroup createTodoGroup(TodoGroup todoGroup);
	
	public TodoGroup updateTodoGroup(TodoGroup todoGroup);
	
	public void deleteTodoGroup(TodoGroup todoGroup);
	
	public List<TodoGroup> findTodoGroups(Long userId);
	
	public List<Todo> findActiveTodosByGroup(TodoGroup todoGroup);
	
	public List<Todo> findCompletedTodosByGroup(TodoGroup todoGroup);
	
	public List<Todo> findAllTodosByGroup(TodoGroup todoGroup);
	
	public List<TodoPriority> findAllTodoPriorities();
	
	public List<Todo> filterTodos(Long userId, Long groupId, Long priorityId, Timestamp dueDate, Boolean isCompleted);
}
