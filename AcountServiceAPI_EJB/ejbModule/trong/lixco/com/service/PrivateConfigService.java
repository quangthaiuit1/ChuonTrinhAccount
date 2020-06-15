package trong.lixco.com.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import trong.lixco.com.entity.PrivateConfig;
import trong.lixco.com.impl.ImplMember;
import trong.lixco.com.impl.ImplPrivateConfig;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PrivateConfigService implements ImplPrivateConfig {
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
	@Inject
	private ImplMember memberService;


	

}
