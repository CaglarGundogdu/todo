package com.ws.todoserver.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = 8382801198239818917L;

	private SearchCriteria searchCriteria;

	public GenericSpecification(final SearchCriteria searchCriteria) {
		super();
		this.searchCriteria = searchCriteria;
	}

	@SuppressWarnings({ "incomplete-switch", "unchecked", "rawtypes" })
	@Override
	   public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	      List<Object> arguments = searchCriteria.getArguments();
	      Object arg = arguments.get(0);

	      switch (searchCriteria.getSearchOperation()) {
	         case EQUALITY:
	            return criteriaBuilder.equal(root.get(searchCriteria.getKey()), arg);
	         case LESS_THAN:
	            return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()), (Comparable) arg);
	      }
		return null;
	   }
}