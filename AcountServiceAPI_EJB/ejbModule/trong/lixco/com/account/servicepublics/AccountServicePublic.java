package trong.lixco.com.account.servicepublics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.Department;
import trong.lixco.com.entity.Member;
import trong.lixco.com.entity.Menu;
import trong.lixco.com.entity.Program;
import trong.lixco.com.entity.Role;
import trong.lixco.com.entity.SingleSignOn;
import trong.lixco.com.entity.UserRight;
import trong.lixco.com.impl.ImplAccount;
import trong.lixco.com.util.Configuage;

@Stateless
@WebService
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AccountServicePublic implements ImplAccount {
	@Inject
	private EntityManager em;


	@Override
	public Account create(Account account) {
		em.persist(account);
		return em.merge(account);
	}

	@Override
	public Account update(Account account) {
		return em.merge(account);
	}

	@Override
	public boolean delete(Account account) {
		try {
			Account merge = em.merge(account);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Account find_User_Pass(String user, String pass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> cq = cb.createQuery(Account.class);
		Root<Account> root = cq.from(Account.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("userName"), user), cb.equal(root.get("password"), pass));
		TypedQuery<Account> query = em.createQuery(cq);
		List<Account> account = query.getResultList();
		if (account.size() != 0) {
			return account.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Account> findAllNotAccount(Account notAccount) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> cq = cb.createQuery(Account.class);
		Root<Account> root = cq.from(Account.class);
		// Query final
		cq.select(root).where(cb.notEqual(root.get("id"), notAccount.getId()));
		TypedQuery<Account> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public Account findMember(Member member) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> cq = cb.createQuery(Account.class);
		Root<Account> root = cq.from(Account.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("member"), member));
		TypedQuery<Account> query = em.createQuery(cq);
		List<Account> accounts = query.getResultList();
		if (accounts.size() != 0) {
			return accounts.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Account find_User_Pass_Ext(String user, String pass, String isLogin) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> cq = cb.createQuery(Account.class);
		Root<Account> root = cq.from(Account.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("userName"), user), cb.equal(root.get("password"), pass),
				cb.equal(root.get("isOnline"), isLogin));
		TypedQuery<Account> query = em.createQuery(cq);
		List<Account> account = query.getResultList();
		if (account.size() != 0) {
			return account.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Set<Program> findProgramByAccount(Account account) {
		String sql = "SELECT p.id, p.name,p.fullname,p.description FROM program as p "
				+ "INNER JOIN role as r on r.program_id = p.id "
				+ "INNER JOIN accrolerelationship as ar on r.id = ar.role_id "
				+ "INNER JOIN account as acc on acc.id = ar.account_id " + "WHERE acc.id = ?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, account.getId());
		List<Object[]> returns = query.getResultList();
		Set<Program> results = new HashSet<Program>();
		for (Object[] values : returns) {
			final long id = Long.parseLong(values[0] + "");
			final String name = values[1] + "";
			final String fullname = values[2] + "";
			final String description = values[3] + "";
			Program pro = new Program();
			pro.setId(id);
			pro.setName(name);
			pro.setFullname(fullname);
			pro.setDescription(description);
			results.add(pro);
		}
		return results;
	}

	@Override
	public List<Menu> findMenuByProgram(Program program) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
		Root<Menu> root = cq.from(Menu.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("program"), program));
		TypedQuery<Menu> query = em.createQuery(cq);
		List<Menu> menus = query.getResultList();
		return menus;

	}

	@Override
	public List<Role> findRoleByAccPro(Account account, Program program) {
		String sql = "SELECT r.id, r.name, r.description FROM role as r "
				+ "INNER JOIN accrolerelationship as ar on r.id = ar.role_id "
				+ "INNER JOIN account as acc on acc.id = ar.account_id " + "WHERE acc.id = ? AND r.program_id = ?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, account.getId());
		query.setParameter(2, program.getId());
		List<Object[]> returns = query.getResultList();
		List<Role> results = new ArrayList<Role>();
		for (Object[] values : returns) {
			final long id = Long.parseLong(values[0] + "");
			final String ten = values[1] + "";
			final String moTa = values[2] + "";
			Role pro = new Role();
			pro.setId(id);
			pro.setName(ten);
			pro.setDescription(moTa);
			results.add(pro);
		}
		return results;
	}

	@Override
	public List<UserRight> findUserRightByAccPro(Account account, Program program) {
		String sql = "SELECT ur.id, ur.isAllow " + "FROM userright as ur "
				+ "INNER JOIN role as r on r.id = ur.role_id "
				+ "INNER JOIN accrolerelationship as ar on r.id = ar.role_id "
				+ "INNER JOIN account as acc on acc.id = ar.account_id " + "WHERE acc.id = ? AND r.program_id = ?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, account.getId());
		query.setParameter(2, program.getId());

		List<Object[]> returns = query.getResultList();
		List<UserRight> results = new ArrayList<UserRight>();

		for (Object[] values : returns) {
			final long id = Long.parseLong(values[0] + "");
			UserRight userRight = em.find(UserRight.class, id);
			results.add(userRight);
		}
		return results;
	}

	@Override
	public SingleSignOn createSSO(SingleSignOn singleSignOn) {
		em.persist(singleSignOn);
		return em.merge(singleSignOn);
	}

	@Override
	public boolean deleteSSO(SingleSignOn singleSignOn) {
		try {
			SingleSignOn merge = em.merge(singleSignOn);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Account findById(long id) {
		return em.find(Account.class, id);
	}

	@Override
	public SingleSignOn findSSOById(long id) {
		return em.find(SingleSignOn.class, id);
	}
	@Override
	public List<SingleSignOn> findSSOByValue(String param) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SingleSignOn> cq = cb.createQuery(SingleSignOn.class);
		Root<SingleSignOn> root = cq.from(SingleSignOn.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("value"), param));
		TypedQuery<SingleSignOn> query = em.createQuery(cq);
		List<SingleSignOn> singleSignOns = query.getResultList();
		return singleSignOns;
	}

	@Override
	public Account find_User(String user) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Account> cq = cb.createQuery(Account.class);
		Root<Account> root = cq.from(Account.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("userName"), user));
		TypedQuery<Account> query = em.createQuery(cq);
		List<Account> account = query.getResultList();
		if (account.size() != 0) {
			return account.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Account> findByDepartment(Department department) {
		// TODO Auto-generated method stub
		return null;
	}
}
