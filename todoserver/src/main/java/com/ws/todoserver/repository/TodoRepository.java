package com.ws.todoserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ws.todoserver.model.entity.Todo;
import com.ws.todoserver.model.entity.TodoGroup;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {
	public List<Todo> findByCompletedAndGroupOrderByIdAsc(boolean completed, TodoGroup group);

	public List<Todo> findByGroupOrderByCompleted(TodoGroup group);
}
