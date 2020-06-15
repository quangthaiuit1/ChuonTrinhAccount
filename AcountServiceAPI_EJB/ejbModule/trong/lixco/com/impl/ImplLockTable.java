package trong.lixco.com.impl;

import java.util.List;

import trong.lixco.com.entity.LockTable;
import trong.lixco.com.entity.Program;

public interface ImplLockTable {
	// Lay chuong trinh theo ID
	LockTable findId(long id);

	// Lay het LockTable
	List<LockTable> findAll();

	LockTable findMonthYear(int month, int year, Program program);

	// Tao menu
	LockTable create(LockTable role);

	// Cap nhat menu
	LockTable update(LockTable role);

	// Xoa menu
	boolean delete(LockTable role);
	
	List<LockTable> findByProgram(Program program);
}
