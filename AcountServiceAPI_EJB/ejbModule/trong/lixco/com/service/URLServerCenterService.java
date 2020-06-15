package trong.lixco.com.service;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import trong.lixco.com.entity.URLServerCenter;
import trong.lixco.com.util.Configuage;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
	public class URLServerCenterService extends AbstractService<URLServerCenter> {
		@Inject
		private EntityManager em;
		@Resource
		private SessionContext ct;


		@Override
		protected Class<URLServerCenter> getEntityClass() {
			return URLServerCenter.class;
		}

		@Override
		protected EntityManager getEntityManager() {
			return em;
		}

		@Override
		protected SessionContext getUt() {
			return ct;
		}
}
