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
import trong.lixco.com.impl.ImplProgram;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProgramService implements ImplProgram {
	@Inject
	private EntityManager em;

	@Override
	public List<Program> findIndex(int index) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Program> cq = cb.createQuery(Program.class);
		Root<Program> root = cq.from(Program.class);
		cq.select(root).where(cb.equal(root.get("indexProgram"), index));
		TypedQuery<Program> query = em.createQuery(cq);
		List<Program> programs = query.getResultList();
		return programs;
	}

	@Override
	public List<Program> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Program> cq = cb.createQuery(Program.class);
		Root<Program> root = cq.from(Program.class);
		cq.select(root);
		TypedQuery<Program> query = em.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public Program getFirst() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Program> cq = cb.createQuery(Program.class);
		Root<Program> root = cq.from(Program.class);
		cq.select(root);
		TypedQuery<Program> query = em.createQuery(cq);
		List<Program> programs = query.getResultList();
		if (programs.size() != 0) {
			return programs.get(0);
		} else {
			return null;
		}
	}
	@Override
	public Program findByName(String name){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Program> cq = cb.createQuery(Program.class);
		Root<Program> root = cq.from(Program.class);
		cq.select(root).where(cb.equal(root.get("name"), name));
		TypedQuery<Program> query = em.createQuery(cq);
		List<Program> programs = query.getResultList();
		if (programs.size() != 0) {
			return programs.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Program findId(long id) {
		return em.find(Program.class, id);
	}

	@Override
	public Program create(Program program) {
		em.persist(program);
		return em.merge(program);
	}

	@Override
	public Program update(Program program) {
		return em.merge(program);
	}

	@Override
	public boolean delete(Program program) {
		try {
			Program merge = em.merge(program);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
