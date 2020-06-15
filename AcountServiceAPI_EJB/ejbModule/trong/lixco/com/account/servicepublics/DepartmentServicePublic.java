package trong.lixco.com.account.servicepublics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import trong.lixco.com.entity.Department;
import trong.lixco.com.impl.ImplDepartmentPublic;

@Stateless
@WebService
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DepartmentServicePublic implements ImplDepartmentPublic {
	@Inject
	private EntityManager em;


	List<Department> departments;

	@Override
	public List<Department> getAllDepartSubByParent(String codeDepart) {
		departments = new ArrayList<Department>();
		showAllDeparmentChild(codeDepart);
		return this.departments;
	}

	private void showAllDeparmentChild(String departmentParent) {
		List<Department> departments = listDepartmentParent(departmentParent);
		for (int i = 0; i < departments.size(); i++) {
			this.departments.add(departments.get(i));
			showAllDeparmentChild(departments.get(i).getCode());
		}
	}

	private List<Department> listDepartmentParent(String departmentParent) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		Root<Department> root = cq.from(Department.class);
		cq.select(root).where(cb.equal(root.get("department").get("code"), departmentParent),
				cb.equal(root.get("disable"), false));
		TypedQuery<Department> query = em.createQuery(cq);
		List<Department> results = query.getResultList();
		return results;
	}

	@Override
	public List<Department> findSearch(String searchText, String[] params) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		Root<Department> root = cq.from(Department.class);
		List<ParameterExpression<String>> paramExps = new LinkedList<ParameterExpression<String>>();

		for (int i = 0; i < params.length; i++) {
			String selectedParam = params[i];
			Path<String> path = root.get(selectedParam);
			ParameterExpression<String> p = cb.parameter(String.class);
			Predicate likePredicate = cb.like(path, p);
			predicates.add(likePredicate);
			paramExps.add(p);
		}

		cq.select(root).where(cb.or(predicates.toArray(new Predicate[0]))).distinct(true);

		TypedQuery<Department> query = em.createQuery(cq);
		for (int i = 0; i < paramExps.size(); i++) {
			query.setParameter(paramExps.get(i), "%" + searchText + "%");
		}
		List<Department> results = query.getResultList();
		return results;
	}

	@Override
	public Department findByCode(String param, String value) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		Root<Department> root = cq.from(Department.class);

		Path<String> path = root.get(param);
		ParameterExpression<String> p = cb.parameter(String.class);
		Predicate likePredicate = cb.like(path, p);
		predicates.add(likePredicate);

		cq.select(root).where(cb.or(predicates.toArray(new Predicate[0]))).distinct(true);

		TypedQuery<Department> query = em.createQuery(cq);
		query.setParameter(p, value);
		List<Department> results = query.getResultList();
		if (results.size() != 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Department> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		Root<Department> root = cq.from(Department.class);
		cq.select(root);
		TypedQuery<Department> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public Department findId(long id) {
		return em.find(Department.class, id);
	}

	@Override
	public Department create(Department member) {
		em.persist(member);
		return em.merge(member);
	}

	@Override
	public Department update(Department member) {
		return em.merge(member);
	}

	@Override
	public boolean delete(Department member) {
		try {
			Department merge = em.merge(member);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
