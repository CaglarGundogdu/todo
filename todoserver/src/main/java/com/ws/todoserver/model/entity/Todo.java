package com.ws.todoserver.model.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "todo", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(columnDefinition = "text", nullable = false)
	private String title;

	@Column(columnDefinition = "text", nullable = true)
	private String description;

	@Column
	private boolean completed;

	@ManyToOne
	@JoinColumn(name="priority", referencedColumnName = "id") 
	private TodoPriority priority;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private TodoGroup group;
	
	@Column(nullable = false)
	private Timestamp dueDate;
	@Column
	private Timestamp createDate;

	@Column
	private Timestamp updateDate;

	@Column
	private Long userId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public TodoPriority getPriority() {
		return priority;
	}

	public void setPriority(TodoPriority priority) {
		this.priority = priority;
	}

	public Timestamp getDueDate() {
		return dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public TodoGroup getGroup() {
		return group;
	}

	public void setGroup(TodoGroup group) {
		this.group = group;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}



}