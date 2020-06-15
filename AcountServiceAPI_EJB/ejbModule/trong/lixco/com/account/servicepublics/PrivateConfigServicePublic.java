package trong.lixco.com.account.servicepublics;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;

import trong.lixco.com.entity.PrivateConfig;
import trong.lixco.com.impl.ImplPrivateConfig;
import trong.lixco.com.util.Configuage;

@Stateless
@WebService
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PrivateConfigServicePublic implements ImplPrivateConfig {
	@Inject
	private EntityManager em;
	
	@Override
	public PrivateConfig findId(long id) {
		return em.find(PrivateConfig.class, id);
	}

	@Override
	public PrivateConfig create(PrivateConfig privateConfig) {
		em.persist(privateConfig);
		return em.merge(privateConfig);
	}

	@Override
	public PrivateConfig update(PrivateConfig privateConfig) {
		return em.merge(privateConfig);
	}

	@Override
	public boolean delete(PrivateConfig privateConfig) {
		try {
			PrivateConfig merge = em.merge(privateConfig);
			em.remove(merge);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	

}
