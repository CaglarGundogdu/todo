package com.ws.todoserver.specification;

import java.util.List;

public class SearchCriteria {

	private String key;
	private SearchOperation searchOperation;
	private boolean isOrOperation;
	private List<Object> arguments;

	public SearchCriteria(String key, SearchOperation searchOperation, boolean isOrOperation, List<Object> arguments) {
		super();
		this.key = key;
		this.searchOperation = searchOperation;
		this.isOrOperation = isOrOperation;
		this.arguments = arguments;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SearchOperation getSearchOperation() {
		return searchOperation;
	}

	public void setSearchOperation(SearchOperation searchOperation) {
		this.searchOperation = searchOperation;
	}

	public boolean isOrOperation() {
		return isOrOperation;
	}

	public void setOrOperation(boolean isOrOperation) {
		this.isOrOperation = isOrOperation;
	}

	public List<Object> getArguments() {
		return arguments;
	}

	public void setArguments(List<Object> arguments) {
		this.arguments = arguments;
	}
}
