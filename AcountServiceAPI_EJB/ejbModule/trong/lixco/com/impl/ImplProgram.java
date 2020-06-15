package trong.lixco.com.impl;

import java.util.List;

import trong.lixco.com.entity.Program;

public interface ImplProgram {
	// Lay chuong trinh theo ID
	Program findId(long id);
	public List<Program> findIndex(int index);

	// Lay het Program
	List<Program> findAll();

	// Tao menu
	Program create(Program program);

	// Cap nhat menu
	Program update(Program program);

	// Xoa menu
	boolean delete(Program program);

	Program getFirst();

	Program findByName(String name);
}
