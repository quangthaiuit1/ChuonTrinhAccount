package trong.lixco.com.service;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import trong.lixco.com.entity.Branch;
import trong.lixco.com.util.Configuage;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BranchService extends AbstractService<Branch>{
	@Inject
	private EntityManager em;
	@Inject
	Configuage config;
	@Resource
	private SessionContext ct;

	public Branch getFirst() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Branch> cq = cb.createQuery(Branch.class);
		Root<Branch> root = cq.from(Branch.class);
		cq.select(root);
		TypedQuery<Branch> query = em.createQuery(cq);
		List<Branch> Branchs = query.getResultList();
		if (Branchs.size() != 0) {
			return Branchs.get(0);
		} else {
			return null;
		}
	}
	public Branch findByName(String name){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Branch> cq = cb.createQuery(Branch.class);
		Root<Branch> root = cq.from(Branch.class);
		cq.select(root).where(cb.equal(root.get("name"), name));
		TypedQuery<Branch> query = em.createQuery(cq);
		List<Branch> Branchs = query.getResultList();
		if (Branchs.size() != 0) {
			return Branchs.get(0);
		} else {
			return null;
		}
	}
	public Branch findByCode(String code){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Branch> cq = cb.createQuery(Branch.class);
		Root<Branch> root = cq.from(Branch.class);
		cq.select(root).where(cb.equal(root.get("code"), code));
		TypedQuery<Branch> query = em.createQuery(cq);
		List<Branch> Branchs = query.getResultList();
		if (Branchs.size() != 0) {
			return Branchs.get(0);
		} else {
			return null;
		}
	}

	@Override
	protected Class<Branch> getEntityClass() {
		// TODO Auto-generated method stub
		return Branch.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	protected SessionContext getUt() {
		// TODO Auto-generated method stub
		return ct;
	}

}
