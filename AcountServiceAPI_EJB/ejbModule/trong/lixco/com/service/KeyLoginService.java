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

import trong.lixco.com.entity.KeyLogin;
import trong.lixco.com.util.Configuage;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KeyLoginService extends AbstractService<KeyLogin> {
	@Inject
	private EntityManager em;
	@Resource
	private SessionContext ct;


	@Override
	protected Class<KeyLogin> getEntityClass() {
		return KeyLogin.class;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected SessionContext getUt() {
		return ct;
	}
	public KeyLogin findByKeyAuth(String keyAuth){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KeyLogin> cq = cb.createQuery(KeyLogin.class);
		Root<KeyLogin> root = cq.from(KeyLogin.class);
		cq.select(root).where(cb.equal(root.get("keyAuth"), keyAuth));
		TypedQuery<KeyLogin> query = em.createQuery(cq);
		List<KeyLogin> results = query.getResultList();
		if (results.size() != 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

}
