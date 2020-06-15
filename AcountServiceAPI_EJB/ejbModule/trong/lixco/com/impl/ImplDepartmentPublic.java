package trong.lixco.com.impl;

import java.util.List;

import trong.lixco.com.entity.Department;

public interface ImplDepartmentPublic {
	// Lay chuong trinh theo ID
	Department findId(long id);

	// Lay het Department
	List<Department> findAll();

	// Lay het Department
	List<Department> findSearch(String searchText, String[] params);

	// Tao menu
	Department create(Department member);

	// Cap nhat menu
	Department update(Department member);

	// Xoa menu
	boolean delete(Department member);
	
	Department findByCode(String param, String value);
	List<Department> getAllDepartSubByParent(String codePart);
}
