package trong.lixco.com.account.servicepublics;

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
import javax.persistence.criteria.Root;

import trong.lixco.com.entity.Role;
import trong.lixco.com.entity.UserRight;
import trong.lixco.com.impl.ImplUserRight;
import trong.lixco.com.util.Configuage;

@Stateless
@WebService
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserRightServicePublic implements ImplUserRight {
	@Inject
	private EntityManager em;
	@Override
	public UserRight create(UserRight userRight) {
		em.persist(userRight);
		return em.merge(userRight);
	}

	@Override
	public UserRight update(UserRight userRight) {
		return em.merge(userRight);
	}

	@Override
	public UserRight findId(long id) {
		return em.find(UserRight.class, id);
	}

	@Override
	public List<UserRight> findUserRightByRole(Role role) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserRight> cq = cb.createQuery(UserRight.class);
		Root<UserRight> root = cq.from(UserRight.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("role"), role));
		TypedQuery<UserRight> query = em.createQuery(cq);
		List<UserRight> roles = query.getResultList();
		return roles;
	}

}
