package trong.lixco.com.service;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import trong.lixco.com.entity.DatabaseBranch;
import trong.lixco.com.util.Configuage;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DatabaseBranchService extends AbstractService<DatabaseBranch>{
	@Inject
	private EntityManager em;
	@Inject
	Configuage config;
	@Resource
	private SessionContext ct;

	@Override
	protected Class<DatabaseBranch> getEntityClass() {
		// TODO Auto-generated method stub
		return DatabaseBranch.class;
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
