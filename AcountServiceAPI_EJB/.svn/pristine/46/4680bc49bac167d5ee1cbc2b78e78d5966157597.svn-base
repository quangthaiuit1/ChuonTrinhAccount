package trong.lixco.com.service;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import trong.lixco.com.entity.NoticeSystem;
import trong.lixco.com.impl.ImplNoticeSystem;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NoticeSystemService implements ImplNoticeSystem {
	@Inject
	private EntityManager em;

	@Override
	public List<NoticeSystem> findSearch(String searchText, String[] params) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<NoticeSystem> cq = cb.createQuery(NoticeSystem.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		Root<NoticeSystem> root = cq.from(NoticeSystem.class);
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

		TypedQuery<NoticeSystem> query = em.createQuery(cq);
		for (int i = 0; i < paramExps.size(); i++) {
			query.setParameter(paramExps.get(i), "%" + searchText + "%");
		}
		List<NoticeSystem> results = query.getResultList();
		return results;
	}

	@Override
	public NoticeSystem findByCode(String param, String value) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<NoticeSystem> cq = cb.createQuery(NoticeSystem.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		Root<NoticeSystem> root = cq.from(NoticeSystem.class);

		Path<String> path = root.get(param);
		ParameterExpression<String> p = cb.parameter(String.class);
		Predicate likePredicate = cb.like(path, p);
		predicates.add(likePredicate);

		cq.select(root).where(cb.or(predicates.toArray(new Predicate[0]))).distinct(true);

		TypedQuery<NoticeSystem> query = em.createQuery(cq);
		query.setParameter(p, value);
		List<NoticeSystem> results = query.getResultList();
		if (results.size() != 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<NoticeSystem> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<NoticeSystem> cq = cb.createQuery(NoticeSystem.class);
		Root<NoticeSystem> root = cq.from(NoticeSystem.class);
		cq.select(root);
		TypedQuery<NoticeSystem> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public NoticeSystem findId(long id) {
		return em.find(NoticeSystem.class, id);
	}

	@Override
	public NoticeSystem createOrUpdate(NoticeSystem member) {
		if (member.getId() == null) {
			em.persist(member);
		}
		member = em.merge(member);
		return member;
	}

	@Override
	public boolean delete(NoticeSystem member) {
		try {
			NoticeSystem merge = em.merge(member);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
