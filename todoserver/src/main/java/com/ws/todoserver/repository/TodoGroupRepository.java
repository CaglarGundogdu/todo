package com.ws.todoserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ws.todoserver.model.entity.TodoGroup;
import com.ws.todoserver.model.entity.User;


@Repository
public interface TodoGroupRepository extends JpaRepository<TodoGroup, Long> {
	List<TodoGroup> findByUserOrderByIdAsc(User user);
}
