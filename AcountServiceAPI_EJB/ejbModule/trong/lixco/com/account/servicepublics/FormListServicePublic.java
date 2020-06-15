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

import trong.lixco.com.entity.FormList;
import trong.lixco.com.entity.Program;
import trong.lixco.com.impl.ImplFormList;
import trong.lixco.com.util.Configuage;

@Stateless
@WebService
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FormListServicePublic implements ImplFormList {
	@Inject
	private EntityManager em;
	@Inject
	private Configuage config;


	@Override
	public List<FormList> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FormList> cq = cb.createQuery(FormList.class);
		Root<FormList> root = cq.from(FormList.class);
		cq.select(root);
		TypedQuery<FormList> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public FormList findId(long id) {
		return em.find(FormList.class, id);
	}

	@Override
	public FormList create(FormList formList) {
		em.persist(formList);
		return em.merge(formList);
	}

	@Override
	public FormList update(FormList formList) {
		return em.merge(formList);
	}

	@Override
	public boolean delete(FormList formList) {
		try {
			FormList merge = em.merge(formList);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<FormList> findByProgram(Program program) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FormList> cq = cb.createQuery(FormList.class);
		Root<FormList> root = cq.from(FormList.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("program"), program));
		TypedQuery<FormList> query = em.createQuery(cq);
		List<FormList> formLists = query.getResultList();
		return formLists;

	}

}
