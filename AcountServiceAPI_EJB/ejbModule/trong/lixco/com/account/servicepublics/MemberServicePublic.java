package trong.lixco.com.account.servicepublics;

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
import trong.lixco.com.entity.Member;
import trong.lixco.com.impl.ImplMemberPublic;
import trong.lixco.com.util.Configuage;

@Stateless
@WebService
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MemberServicePublic implements ImplMemberPublic {
	@Inject
	private EntityManager em;
	@Inject
	private Configuage config;


	@Override
	public List<Member> findSearch(String searchText, String[] params) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		Root<Member> root = cq.from(Member.class);
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

		TypedQuery<Member> query = em.createQuery(cq);
		for (int i = 0; i < paramExps.size(); i++) {
			query.setParameter(paramExps.get(i), "%" + searchText + "%");
		}

		// Chu y set null chu ky null
		List<Member> results = query.getResultList();
		return results;
	}

	@Override
	public List<Member> findSearchWarehouse(String searchText, String[] params, Boolean stocker) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		Root<Member> root = cq.from(Member.class);
		List<ParameterExpression<String>> paramExps = new LinkedList<ParameterExpression<String>>();

		for (int i = 0; i < params.length; i++) {
			String selectedParam = params[i];
			Path<String> path = root.get(selectedParam);
			ParameterExpression<String> p = cb.parameter(String.class);
			Predicate likePredicate = cb.like(path, p);
			predicates.add(likePredicate);
			paramExps.add(p);
		}

		cq.select(root).where(cb.or(predicates.toArray(new Predicate[0])), cb.equal(root.get("stocker"), stocker))
				.distinct(true);

		TypedQuery<Member> query = em.createQuery(cq);
		for (int i = 0; i < paramExps.size(); i++) {
			query.setParameter(paramExps.get(i), "%" + searchText + "%");
		}
		List<Member> results = query.getResultList();
		return results;
	}

	@Override
	public List<Member> findByDepartment(Department department) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> root = cq.from(Member.class);
		cq.select(root).where(cb.equal(root.get("department"), department)).distinct(true);
		TypedQuery<Member> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public List<Member> findByCodeDepart(String codeDepart) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> root = cq.from(Member.class);
		cq.select(root).where(cb.equal(root.get("department").get("code"), codeDepart)).distinct(true);
		TypedQuery<Member> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public Member findLike(String param, String value) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		Root<Member> root = cq.from(Member.class);

		Path<String> path = root.get(param);
		ParameterExpression<String> p = cb.parameter(String.class);
		Predicate likePredicate = cb.like(path, p);
		predicates.add(likePredicate);

		cq.select(root).where(cb.or(predicates.toArray(new Predicate[0]))).distinct(true);

		TypedQuery<Member> query = em.createQuery(cq);
		query.setParameter(p, value);
		List<Member> results = query.getResultList();
		if (results.size() != 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Member findByCode(String value) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> root = cq.from(Member.class);
		cq.select(root).where(cb.equal(root.get("code"), value));
		TypedQuery<Member> query = em.createQuery(cq);
		List<Member> results = query.getResultList();
		if (results.size() != 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Member> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> root = cq.from(Member.class);
		cq.select(root);
		TypedQuery<Member> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public Member findId(long id) {
		return em.find(Member.class, id);
	}

	@Override
	public Member create(Member member) {
		em.persist(member);
		return em.merge(member);
	}

	@Override
	public Member update(Member member) {
		return em.merge(member);
	}

	@Override
	public boolean delete(Member member) {
		try {
			Member merge = em.merge(member);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
