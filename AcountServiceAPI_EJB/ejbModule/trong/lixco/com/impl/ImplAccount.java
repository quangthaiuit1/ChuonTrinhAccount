package trong.lixco.com.impl;

import java.util.List;
import java.util.Set;

import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.Department;
import trong.lixco.com.entity.Member;
import trong.lixco.com.entity.Menu;
import trong.lixco.com.entity.Program;
import trong.lixco.com.entity.Role;
import trong.lixco.com.entity.SingleSignOn;
import trong.lixco.com.entity.UserRight;

public interface ImplAccount {

	// Find id
	Account findById(long id);

	// Dang nhap
	Account find_User_Pass_Ext(String user, String pass, String isLogin);

	// Tim kiem tai khoan theo User, Pass
	Account find_User_Pass(String user, String pass);
	
	Account find_User(String user);

	// Lay het danh sach tai khoan
	List<Account> findAllNotAccount(Account notAccount);

	// Lay danh sach chuong trinh theo tai khoan
	Set<Program> findProgramByAccount(Account account);

	// Lay account theo member
	Account findMember(Member member);

	// Lay danh sach menu theo chuong trinh
	List<Menu> findMenuByProgram(Program program);

	// Lay danh sach quyen theo chuoeng trinh theo account
	List<Role> findRoleByAccPro(Account account, Program program);


	// Lay danh sach form va quyen tren tung form theo account
	List<UserRight> findUserRightByAccPro(Account account, Program program);

	// Tao mot tai khoan moi
	Account create(Account messages);

	// Cap nhat tai khoan
	Account update(Account account);

	// Xoa tai khoan
	boolean delete(Account account);

	// Tao mot session dang nhap
	SingleSignOn createSSO(SingleSignOn singleSignOn);

	// Xoa mot session dang nhap
	boolean deleteSSO(SingleSignOn singleSignOn);

	List<SingleSignOn> findSSOByValue(String param);
	SingleSignOn findSSOById(long id);
	List<Account> findByDepartment(Department department);
}
