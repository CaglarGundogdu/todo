package com.ws.todoserver.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.todoserver.model.entity.Todo;
import com.ws.todoserver.model.entity.TodoGroup;
import com.ws.todoserver.model.entity.TodoPriority;
import com.ws.todoserver.model.entity.User;
import com.ws.todoserver.repository.TodoGroupRepository;
import com.ws.todoserver.repository.TodoPriorityRepository;
import com.ws.todoserver.repository.TodoRepository;
import com.ws.todoserver.service.TodoService;
import com.ws.todoserver.specification.GenericSpecificationsBuilder;
import com.ws.todoserver.specification.SpecificationFactory;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoGroupRepository todoGroupRepository;

	@Autowired
	private TodoPriorityRepository todoPriorityRepository;

	@Autowired
	private SpecificationFactory<Todo> todoSpecificationFactory;
	
	@Override
	public Todo findById(Long todoId) {
		return todoRepository.findById(todoId).orElse(null);
	}

	@Override
	public Todo createTodo(Todo todo) {
		return todoRepository.save(todo);
	}

	@Override
	public Todo updateTodo(Todo todo) {
		return todoRepository.save(todo);
	}

	@Override
	public void deleteTodo(Todo todo) {
		todoRepository.delete(todo);
	}

	@Override
	public TodoGroup findTodoGroupById(Long todoGroupId) {
		return todoGroupRepository.findById(todoGroupId).orElse(null);
	}

	@Override
	public TodoGroup createTodoGroup(TodoGroup todoGroup) {
		return todoGroupRepository.save(todoGroup);
	}

	@Override
	public TodoGroup updateTodoGroup(TodoGroup todoGroup) {
		TodoGroup todoGroupFromDB = todoGroupRepository.findById(todoGroup.getId()).orElse(null);
		if (todoGroupFromDB == null) {
			return null;
		} 
		
		todoGroup.setTodos(todoGroupFromDB.getTodos());
		
		return todoGroupRepository.save(todoGroup);
	}

	@Override
	public void deleteTodoGroup(TodoGroup todoGroup) {
		todoGroupRepository.delete(todoGroup);
	}

	@Override
	public List<TodoGroup> findTodoGroups(Long userId) {
		User user = new User();
		user.setId(userId);

		return todoGroupRepository.findByUserOrderByIdAsc(user);
	}

	@Override
	public List<Todo> findActiveTodosByGroup(TodoGroup todoGroup) {
		return todoRepository.findByCompletedAndGroupOrderByIdAsc(false, todoGroup);
	}

	@Override
	public List<Todo> findCompletedTodosByGroup(TodoGroup todoGroup) {
		return todoRepository.findByCompletedAndGroupOrderByIdAsc(true, todoGroup);
	}

	@Override
	public List<Todo> findAllTodosByGroup(TodoGroup todoGroup) {
		return todoRepository.findByGroupOrderByCompleted(todoGroup);
	}

	@Override
	public List<TodoPriority> findAllTodoPriorities() {
		return todoPriorityRepository.findAllByOrderByIdAsc();
	}

	@Override
	public List<Todo> filterTodos(Long userId, Long groupId, Long priorityId, Timestamp dueDate, Boolean isCompleted) {
		GenericSpecificationsBuilder<Todo> builder = new GenericSpecificationsBuilder<>();
		if (userId != null) {
			builder.with(todoSpecificationFactory.isEqual("userId", userId));
			
			if (groupId != null) {
				builder.with(todoSpecificationFactory.isEqual("group",    groupId));
			}
			
			if (priorityId != null) {
				builder.with(todoSpecificationFactory.isEqual("priority",    priorityId));
			}
			
			if (dueDate != null) {
				builder.with(todoSpecificationFactory.isLessThan("dueDate",    dueDate));
			}
			
			if (isCompleted != null) {
				builder.with(todoSpecificationFactory.isEqual("completed",    isCompleted));
			}
			
			return todoRepository.findAll(builder.build());
		} 
		
		return null;
	}

}
