package trong.lixco.com.service;

import java.util.LinkedList;
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
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import trong.lixco.com.entity.LevelDep;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LevelDepService extends AbstractService<LevelDep> {
	@Inject
	private EntityManager em;
	@Resource
	private SessionContext ct;

	@Override
	protected Class<LevelDep> getEntityClass() {
		// TODO Auto-generated method stub
		return LevelDep.class;
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

	public LevelDep findByCode(String code) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<LevelDep> cq = cb.createQuery(LevelDep.class);
		Root<LevelDep> root = cq.from(LevelDep.class);
		cq.select(root).where(cb.equal(root.get("code"), code));
		TypedQuery<LevelDep> query = em.createQuery(cq);
		List<LevelDep> results = query.getResultList();
		if (results.size() != 0) {
			return results.get(0);
		} else {
			return null;
		}
	}
}
