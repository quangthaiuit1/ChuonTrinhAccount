package trong.lixco.com.impl;

import java.util.List;

import trong.lixco.com.entity.NoticeSystem;

public interface ImplNoticeSystem {
	NoticeSystem findId(long id);

	List<NoticeSystem> findAll();

	List<NoticeSystem> findSearch(String searchText, String[] params);

	NoticeSystem createOrUpdate(NoticeSystem member);
	boolean delete(NoticeSystem member);
	
	NoticeSystem findByCode(String param, String value);
}
