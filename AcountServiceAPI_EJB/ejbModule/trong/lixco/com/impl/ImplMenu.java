package trong.lixco.com.impl;

import java.util.List;

import trong.lixco.com.entity.Menu;
import trong.lixco.com.entity.Program;

public interface ImplMenu {
	// Lay het Menu
	List<Menu> findAll();
	// Lay menu theo chuong trinh
	List<Menu> find_Program(Program program);
	// Tim kiem menu theo ten
	Menu find_Name(String name,Program program);
	Menu find_ID(long id);
	// Tao menu
	Menu create(Menu menu);
	// Cap nhat menu
	Menu update(Menu menu);
	// Xoa menu
	boolean delete(Menu menu);
}
