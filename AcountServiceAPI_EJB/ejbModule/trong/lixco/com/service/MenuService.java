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

import trong.lixco.com.entity.Menu;
import trong.lixco.com.entity.Program;
import trong.lixco.com.impl.ImplMenu;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MenuService implements ImplMenu {
	@Inject
	private EntityManager em;

	@Override
	public List<Menu> findAll() {
		return null;
	}

	@Override
	public List<Menu> find_Program(Program program) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
		Root<Menu> root = cq.from(Menu.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("program"), program)).distinct(false);
		TypedQuery<Menu> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public Menu find_Name(String name, Program program) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
		Root<Menu> root = cq.from(Menu.class);
		// Query final
		cq.select(root).where(cb.equal(root.get("tenHienThi"), name), cb.equal(root.get("program"), program));
		TypedQuery<Menu> query = em.createQuery(cq);
		List<Menu> results = query.getResultList();
		if (results.size() != 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Menu find_ID(long id) {
		Menu menu = em.find(Menu.class, id);
		return menu;
	}

	@Override
	public Menu create(Menu menu) {
		em.persist(menu);
		return em.merge(menu);

	}

	@Override
	public Menu update(Menu menu) {
		return em.merge(menu);
	}

	@Override
	public boolean delete(Menu menu) {
		try {
			Menu merge = em.merge(menu);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
