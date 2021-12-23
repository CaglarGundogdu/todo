package com.ws.todoserver.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ws.todoserver.model.ApiResponse;
import com.ws.todoserver.model.entity.Todo;
import com.ws.todoserver.model.entity.TodoGroup;
import com.ws.todoserver.model.entity.TodoPriority;
import com.ws.todoserver.model.entity.User;
import com.ws.todoserver.service.TodoService;
import com.ws.todoserver.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class TodoController {

	@Autowired
	private UserService userService;

	@Autowired
	private TodoService todoService;

	@GetMapping("/todo-groups")
	@Operation(summary = "Get Todo Groups")
	public ResponseEntity<ApiResponse> getTodoGroups() {
		List<TodoGroup> todoGroupList = new ArrayList<TodoGroup>();
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			Long userId = userService.userId(((UserDetails) userDetails).getUsername());

			todoGroupList = todoService.findTodoGroups(userId);
		}
		return new ResponseEntity<>(new ApiResponse(true, "All Todo Groups", todoGroupList), HttpStatus.OK);
	}

	@GetMapping("/active-todos")
	@Operation(summary = "Get active todos with given TodoGroup id")
	public ResponseEntity<ApiResponse> getActiveTodosByGroup(@RequestParam Long todoGroupId) {

		if (isAuthorizedUser(todoGroupId)) {
			TodoGroup td = new TodoGroup();
			td.setId(todoGroupId);
			List<Todo> todoList = todoService.findActiveTodosByGroup(td);
			return new ResponseEntity<>(new ApiResponse(true, "Active todo list", todoList), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Accessing unautherized group"), HttpStatus.OK);
		}
	}

	@GetMapping("/completed-todos")
	@Operation(summary = "Get completed todos with given TodoGroup id")
	public ResponseEntity<ApiResponse> getCompletedTodosByGroup(@RequestParam Long todoGroupId) {

		if (isAuthorizedUser(todoGroupId)) {
			TodoGroup td = new TodoGroup();
			td.setId(todoGroupId);
			List<Todo> todoList = todoService.findCompletedTodosByGroup(td);
			return new ResponseEntity<>(new ApiResponse(true, "Completed todo list", todoList), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
		}
	}

	@GetMapping("/todos")
	@Operation(summary = "Get all todos with given TodoGroup id")
	public ResponseEntity<ApiResponse> getTodosByGroup(@RequestParam Long todoGroupId) {

		if (isAuthorizedUser(todoGroupId)) {
			TodoGroup td = new TodoGroup();
			td.setId(todoGroupId);
			List<Todo> todoList = todoService.findAllTodosByGroup(td);
			return new ResponseEntity<>(new ApiResponse(true, "Completed todo list", todoList), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
		}
	}

	@PostMapping("/todo-group")
	@Operation(summary = "Create a new TodoGroup")
	public ResponseEntity<ApiResponse> createTodoGroup(@RequestBody TodoGroup todoGroup) {

		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			Long userId = userService.userId(((UserDetails) userDetails).getUsername());

			User user = new User();
			user.setId(userId);
			todoGroup.setUser(user);
			todoGroup.setCreateDate(new Timestamp(new Date().getTime()));

			TodoGroup createdTodoGroup = todoService.createTodoGroup(todoGroup);

			return new ResponseEntity<>(new ApiResponse(true, "Group created", createdTodoGroup), HttpStatus.OK);
		}

		return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
	}

	@PutMapping("/todo-group")
	@Operation(summary = "Update TodoGroup")
	public ResponseEntity<ApiResponse> updateTodoGroup(@RequestBody TodoGroup todoGroup) {

		if (isAuthorizedUser(todoGroup.getId())) {
			Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (userDetails instanceof UserDetails) {
				Long userId = userService.userId(((UserDetails) userDetails).getUsername());

				User user = new User();
				user.setId(userId);
				todoGroup.setUser(user);
			
				todoService.updateTodoGroup(todoGroup);
			}
			return new ResponseEntity<>(new ApiResponse(true, "Group updated", todoGroup), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/todo-group/{id}")
	@Operation(summary = "Delete a TodoGroup with given id")
	public ResponseEntity<ApiResponse> deleteTodoGroup(@PathVariable Long id) {

		if (isAuthorizedUser(id)) {
			TodoGroup todoGroup = new TodoGroup();
			todoGroup.setId(id);
			todoService.deleteTodoGroup(todoGroup);
			return new ResponseEntity<>(new ApiResponse(true, "Group deleted", todoGroup), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
		}
	}

	@GetMapping("/todo/{id}")
	@Operation(summary = "Get a Todo with given id")
	public ResponseEntity<ApiResponse> getTodo(@PathVariable(value = "id") Long todoId) {
		Todo todo = todoService.findById(todoId);
		if (isAuthorizedUser(todo.getGroup())) {
			return new ResponseEntity<>(new ApiResponse(true, "Todo", todo), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
		}
	}

	@DeleteMapping("/todo/{id}")
	@Operation(summary = "Delete a Todo with given id")
	public ResponseEntity<ApiResponse> deleteTodo(@PathVariable Long id) {

		Todo todo = todoService.findById(id);
		if (isAuthorizedUser(todo.getGroup())) {
			todoService.deleteTodo(todo);
			return new ResponseEntity<>(new ApiResponse(true, "Todo deleted", todo), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
		}
	}

	@PostMapping("/todo")
	@Operation(summary = "Create a new todo")
	public ResponseEntity<?> createTodo(@RequestBody Todo todo) {

		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			Long userId = userService.userId(((UserDetails) userDetails).getUsername());

			User user = new User();
			user.setId(userId);
			todo.setCreateDate(new Timestamp(new Date().getTime()));

			if (todo.getGroup() == null) {
				return new ResponseEntity<>(new ApiResponse(false, "Group missing"), HttpStatus.OK);
			}

			TodoGroup group = todoService.findTodoGroupById(todo.getGroup().getId());

			if (group.getUser().getId() != userId) {
				return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
			}
			
			todo.setUserId(userId);

			Todo createdTodo = todoService.createTodo(todo);

			return new ResponseEntity<>(new ApiResponse(true, "Todo created", createdTodo), HttpStatus.OK);
		}

		return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
	}

	@PutMapping("/todo")
	@Operation(summary = "Updata given todo")
	public ResponseEntity<?> todoCompleted(@RequestBody Todo todo) {

		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			Long userId = userService.userId(((UserDetails) userDetails).getUsername());

			User user = new User();
			user.setId(userId);

			if (todo.getGroup() == null) {
				return new ResponseEntity<>(new ApiResponse(false, "Group missing"), HttpStatus.OK);
			}

			TodoGroup group = todoService.findTodoGroupById(todo.getGroup().getId());

			if (group.getUser().getId() != userId) {
				return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
			}

			todo.setUpdateDate(new Timestamp(new Date().getTime()));
			Todo updatedTodo = todoService.updateTodo(todo);

			return new ResponseEntity<>(new ApiResponse(true, "Todo updated", updatedTodo), HttpStatus.OK);
		}

		return new ResponseEntity<>(new ApiResponse(false, "Access Denied"), HttpStatus.OK);
	}

	@GetMapping("/priorities" )
	@Operation(summary = "Get Priority List")
	public ResponseEntity<ApiResponse> getPriorities() {

		List<TodoPriority> todoGroupList = todoService.findAllTodoPriorities();

		return new ResponseEntity<>(new ApiResponse(true, "Priorities", todoGroupList), HttpStatus.OK);
	}

	@GetMapping("/search")
	@Operation(summary = "Filter Todo List")
	public ResponseEntity<ApiResponse> getFilteredTodos(@RequestParam(required = false) Integer todoGroupId,
			@RequestParam(required = false) Integer priorityId, @RequestParam(required = false)  String dueDate, @RequestParam(required = false) Boolean isCompleted) {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			Long userId = userService.userId(((UserDetails) userDetails).getUsername());
			
			//@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
			Timestamp dueDateAsTimestamp = null;
			if (dueDate != null && !dueDate.isEmpty()) {
				try {
					dueDateAsTimestamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dueDate).getTime());
				} catch (ParseException e) {
					// Intentionally blank
				}
			}
			
			
			List<Todo> todoList = todoService.filterTodos(userId, todoGroupId != null ? new Long(todoGroupId) : null,
					priorityId != null ? new Long(priorityId) : null, dueDateAsTimestamp, isCompleted);
			
			return  new ResponseEntity<>(new ApiResponse(true, "Filtered Todos", todoList), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse(true, "Filtered Todos", new ArrayList<Todo>()), HttpStatus.OK);
		
	}

	private boolean isAuthorizedUser(TodoGroup todoGroup) {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			Long userId = userService.userId(((UserDetails) userDetails).getUsername());

			TodoGroup group = todoService.findTodoGroupById(todoGroup.getId());

			return group.getUser().getId() == userId;
		}
		return false;
	}

	private boolean isAuthorizedUser(Long todoGroupId) {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			Long userId = userService.userId(((UserDetails) userDetails).getUsername());

			TodoGroup group = todoService.findTodoGroupById(todoGroupId);

			return group.getUser().getId() == userId;
		}
		return false;
	}

}
