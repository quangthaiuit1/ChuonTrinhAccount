package trong.lixco.com.impl;

import java.util.List;

import trong.lixco.com.entity.Department;
import trong.lixco.com.entity.Member;

public interface ImplMemberPublic {
	Member findId(long id);

	List<Member> findAll();

	List<Member> findSearch(String searchText, String[] params);
	List<Member> findSearchWarehouse(String searchText, String[] params, Boolean stocker);

	Member create(Member member);

	Member update(Member member);

	boolean delete(Member member);

	Member findLike(String param, String value);
	Member findByCode(String value);

	List<Member> findByDepartment(Department department);
	List<Member> findByCodeDepart(String codeDepart);
}
