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

import trong.lixco.com.entity.DepartNoticeRela;
import trong.lixco.com.entity.Department;
import trong.lixco.com.entity.NoticeSystem;
import trong.lixco.com.impl.ImplDepartNoticeRela;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DepartNoticeRelaService implements ImplDepartNoticeRela {
	@Inject
	private EntityManager em;

	@Override
	public List<DepartNoticeRela> findByNotice(NoticeSystem noticeSystem) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DepartNoticeRela> cq = cb.createQuery(DepartNoticeRela.class);
		Root<DepartNoticeRela> root = cq.from(DepartNoticeRela.class);
		cq.select(root).where(cb.equal(root.get("noticeSystem"), noticeSystem)).distinct(true);
		TypedQuery<DepartNoticeRela> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public List<DepartNoticeRela> findByDepartment(Department department) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DepartNoticeRela> cq = cb.createQuery(DepartNoticeRela.class);
		Root<DepartNoticeRela> root = cq.from(DepartNoticeRela.class);
		cq.select(root).where(cb.equal(root.get("department"), department)).distinct(true);
		TypedQuery<DepartNoticeRela> query = em.createQuery(cq);
		return query.getResultList();
	}
	@Override
	public DepartNoticeRela saveOrUpdater(DepartNoticeRela member) {
		em.persist(member);
		return em.merge(member);
	}

}
