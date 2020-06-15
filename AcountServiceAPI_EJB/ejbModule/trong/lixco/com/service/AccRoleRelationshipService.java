package trong.lixco.com.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import trong.lixco.com.entity.AccRoleRelationship;
import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.Program;
import trong.lixco.com.entity.Role;
import trong.lixco.com.impl.ImplAccRoleRelationship;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AccRoleRelationshipService implements ImplAccRoleRelationship {
	@Inject
	private EntityManager em;

	@Override
	public AccRoleRelationship create(AccRoleRelationship AccRoleRelationship) {
		em.persist(AccRoleRelationship);
		return em.merge(AccRoleRelationship);
	}

	@Override
	public AccRoleRelationship update(AccRoleRelationship AccRoleRelationship) {
		return em.merge(AccRoleRelationship);
	}

	@Override
	public boolean delete(AccRoleRelationship AccRoleRelationship) {
		try {
			AccRoleRelationship merge = em.merge(AccRoleRelationship);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deletes(List<AccRoleRelationship> accRoleRelationship) {
		try {
			for (int i = 0; i < accRoleRelationship.size(); i++) {
				AccRoleRelationship merge = em.merge(accRoleRelationship.get(i));
				em.remove(merge);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public AccRoleRelationship findById(long id) {
		return em.find(AccRoleRelationship.class, id);
	}

	@Override
	public boolean check(Account account, Role role) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AccRoleRelationship> cq = cb.createQuery(AccRoleRelationship.class);
		Root<AccRoleRelationship> root = cq.from(AccRoleRelationship.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("account"), account), cb.equal(root.get("role"), role));
		TypedQuery<AccRoleRelationship> query = em.createQuery(cq);
		List<AccRoleRelationship> accRoleRelationships = query.getResultList();
		if (accRoleRelationships.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<AccRoleRelationship> find_Account(Account account) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AccRoleRelationship> cq = cb.createQuery(AccRoleRelationship.class);
		Root<AccRoleRelationship> root = cq.from(AccRoleRelationship.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("account"), account));
		TypedQuery<AccRoleRelationship> query = em.createQuery(cq);
		List<AccRoleRelationship> accRoleRelationships = query.getResultList();
		return accRoleRelationships;
	}

	@EJB
	private ImplAccRoleRelationship accRoleRelationshipService;

	@Override
	public List<AccRoleRelationship> find_Account(Account account, Program program) {

		String sql = "SELECT ar.id,ar.note  FROM accrolerelationship as ar " + "INNER JOIN role as r on r.id = ar.role_id "
				+ "WHERE ar.account_id = ? AND r.program_id = ?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, account.getId());
		query.setParameter(2, program.getId());
		List<Object[]> returns = query.getResultList();
		List<AccRoleRelationship> results = new ArrayList<AccRoleRelationship>();
		for (Object[] values : returns) {
			final long id = Long.parseLong(values[0] + "");
			AccRoleRelationship ar = accRoleRelationshipService.findById(id);
			results.add(ar);
		}
		return results;
	}

	@Override
	public List<AccRoleRelationship> find_Account_list(List<Account> accounts) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AccRoleRelationship> cq = cb.createQuery(AccRoleRelationship.class);
		Root<AccRoleRelationship> root = cq.from(AccRoleRelationship.class);
		// Query final
		cq.select(root).where(cb.in(root.get("account")).value(accounts));
		TypedQuery<AccRoleRelationship> query = em.createQuery(cq);
		List<AccRoleRelationship> accRoleRelationships = query.getResultList();
		return accRoleRelationships;
	}

	@Override
	public List<AccRoleRelationship> find_Role(Role role) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AccRoleRelationship> cq = cb.createQuery(AccRoleRelationship.class);
		Root<AccRoleRelationship> root = cq.from(AccRoleRelationship.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("role"), role));
		TypedQuery<AccRoleRelationship> query = em.createQuery(cq);
		List<AccRoleRelationship> accRoleRelationships = query.getResultList();
		return accRoleRelationships;
	}

	@Override
	public List<AccRoleRelationship> find_Role_list(List<Role> roles) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AccRoleRelationship> cq = cb.createQuery(AccRoleRelationship.class);
		Root<AccRoleRelationship> root = cq.from(AccRoleRelationship.class);
		// Query final
		cq.select(root).where(cb.in(root.get("role")).value(roles));
		TypedQuery<AccRoleRelationship> query = em.createQuery(cq);
		List<AccRoleRelationship> accRoleRelationships = query.getResultList();
		return accRoleRelationships;
	}

	@Override
	public List<Account> find_Account(Role role) {
		List<Account> accounts = new ArrayList<Account>();
		List<AccRoleRelationship> relationships = find_Role(role);
		for (int i = 0; i < relationships.size(); i++) {
			accounts.add(relationships.get(i).getAccount());
		}
		return accounts;
	}

	@Override
	public List<Role> find_Role(Account account) {
		List<Role> roles = new ArrayList<Role>();
		List<AccRoleRelationship> relationships = find_Account(account);
		for (int i = 0; i < relationships.size(); i++) {
			roles.add(relationships.get(i).getRole());
		}
		return roles;
	}

}
