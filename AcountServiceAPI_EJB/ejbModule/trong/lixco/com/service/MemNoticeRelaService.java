package trong.lixco.com.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import trong.lixco.com.entity.MemNoticeRela;
import trong.lixco.com.entity.Member;
import trong.lixco.com.entity.NoticeSystem;
import trong.lixco.com.impl.ImplMemNoticeRela;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MemNoticeRelaService implements ImplMemNoticeRela {
	@Inject
	private EntityManager em;

	@Override
	public List<MemNoticeRela> findByNotice(NoticeSystem noticeSystem) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemNoticeRela> cq = cb.createQuery(MemNoticeRela.class);
		Root<MemNoticeRela> root = cq.from(MemNoticeRela.class);
		cq.select(root).where(cb.equal(root.get("noticeSystem"), noticeSystem)).distinct(true);
		TypedQuery<MemNoticeRela> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public List<MemNoticeRela> findByMember(Member member) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<MemNoticeRela> cq = cb.createQuery(MemNoticeRela.class);
		Root<MemNoticeRela> root = cq.from(MemNoticeRela.class);
		cq.select(root).where(cb.equal(root.get("member"), member)).distinct(true);
		TypedQuery<MemNoticeRela> query = em.createQuery(cq);
		return query.getResultList();
	}
	@Override
	public MemNoticeRela saveOrUpdater(MemNoticeRela member) {
		em.persist(member);
		return em.merge(member);
	}

}
