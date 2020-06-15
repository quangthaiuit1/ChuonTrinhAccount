package trong.lixco.com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import trong.lixco.com.entity.Department;
import trong.lixco.com.util.Configuage;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DepartmentService extends AbstractService<Department> {
	@Inject
	private EntityManager em;
	@Inject
	Configuage config;
	@Resource
	private SessionContext ct;
	private static List<Department> departments;
	static {
		departments=new ArrayList<Department>();
	}

	public List<Department> findSearch(String searchText, String[] params) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		List<Predicate> predicates = new LinkedList<Predicate>();
		Root<Department> root = cq.from(Department.class);
		List<ParameterExpression<String>> paramExps = new LinkedList<ParameterExpression<String>>();

		for (int i = 0; i < params.length; i++) {
			String selectedParam = params[i];
			Path<String> path = root.get(selectedParam);
			ParameterExpression<String> p = cb.parameter(String.class);
			Predicate likePredicate = cb.like(path, p);
			predicates.add(likePredicate);
			paramExps.add(p);
		}

		cq.select(root).where(cb.or(predicates.toArray(new Predicate[0]))).distinct(true);

		TypedQuery<Department> query = em.createQuery(cq);
		for (int i = 0; i < paramExps.size(); i++) {
			query.setParameter(paramExps.get(i), "%" + searchText + "%");
		}
		List<Department> results = query.getResultList();
		return results;
	}

	public Department findByCode(String code) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		Root<Department> root = cq.from(Department.class);
		cq.select(root).where(cb.equal(root.get("code"), code));
		TypedQuery<Department> query = em.createQuery(cq);
		List<Department> results = query.getResultList();
		if (results.size() != 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	public List<Department> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		Root<Department> root = cq.from(Department.class);
		cq.select(root);
		TypedQuery<Department> query = em.createQuery(cq);
		return query.getResultList();
	}

	public List<Department> findAllDisableSort() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(getEntityClass());
		Root<Department> root = cq.from(getEntityClass());
		cq.select(root).where(cb.equal(root.get("disable"), false));
		TypedQuery<Department> query = getEntityManager().createQuery(cq);
		List<Department> results = query.getResultList();
		return sort(results);
	}

	public List<Department> getDepartmentAllSub(Department departmentParent) {
		departments.clear();
		departments.add(departmentParent);
		showAllDeparmentChild(departmentParent);
		return this.departments;
	}
	private void showAllDeparmentChild(Department departmentParent) {
		List<Department> departments = listDepartmentParent(departmentParent);
		for (int i = 0; i < departments.size(); i++) {
			this.departments.add(departments.get(i));
			showAllDeparmentChild(departments.get(i));
		}
	}
	private List<Department> listDepartmentParent(Department departmentParent) {
		// lay danh sach member theo idmembercha
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		Root<Department> root = cq.from(Department.class);
		cq.select(root).where(cb.equal(root.get("department"), departmentParent), cb.equal(root.get("disable"), false));
		TypedQuery<Department> query = em.createQuery(cq);
		List<Department> results = query.getResultList();
		return results;
	}
	private List<Department> sort(List<Department> items) {
		try {
			Collections.sort(items, new Comparator<Department>() {
				@Override
				public int compare(Department sv1, Department sv2) {
					try {
						return sv1.showAllNameDepartFull().compareTo(sv2.showAllNameDepartFull());
					} catch (Exception e) {
						return -1;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	protected Class<Department> getEntityClass() {
		// TODO Auto-generated method stub
		return Department.class;
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
