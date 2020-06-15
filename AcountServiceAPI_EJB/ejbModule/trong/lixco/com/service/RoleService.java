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

import trong.lixco.com.entity.Program;
import trong.lixco.com.entity.Role;
import trong.lixco.com.impl.ImplRole;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RoleService implements ImplRole {
	@Inject
	private EntityManager em;

	@Override
	public List<Role> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Role> cq = cb.createQuery(Role.class);
		Root<Role> root = cq.from(Role.class);
		cq.select(root);
		TypedQuery<Role> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public Role findId(long id) {
		return em.find(Role.class, id);
	}

	@Override
	public Role create(Role role) {
		em.persist(role);
		return em.merge(role);
	}

	@Override
	public Role update(Role role) {
		return em.merge(role);
	}

	@Override
	public boolean delete(Role role) {
		try {
			Role merge = em.merge(role);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Role> findByProgram(Program program) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Role> cq = cb.createQuery(Role.class);
		Root<Role> root = cq.from(Role.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("program"), program));
		TypedQuery<Role> query = em.createQuery(cq);
		List<Role> roles = query.getResultList();
		return roles;

	}
}
