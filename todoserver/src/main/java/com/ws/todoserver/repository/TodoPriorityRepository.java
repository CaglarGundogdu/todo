package com.ws.todoserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ws.todoserver.model.entity.TodoPriority;


@Repository
public interface TodoPriorityRepository extends JpaRepository<TodoPriority, Long> {
	public List<TodoPriority> findAllByOrderByIdAsc();
}
