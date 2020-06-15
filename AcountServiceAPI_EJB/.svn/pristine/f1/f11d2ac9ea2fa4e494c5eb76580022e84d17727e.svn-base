package trong.lixco.com.impl;

import java.util.List;

import trong.lixco.com.entity.AccRoleRelationship;
import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.Program;
import trong.lixco.com.entity.Role;

public interface ImplAccRoleRelationship {

	// Find id
	AccRoleRelationship findById(long id);

	// Kiem tra quyen
	boolean check(Account account, Role role);

	// Tim kiem theo Account
	List<AccRoleRelationship> find_Account(Account account);
	List<AccRoleRelationship> find_Account(Account account,Program program);

	// Tim kiem theo List Account
	List<AccRoleRelationship> find_Account_list(List<Account> accounts);

	// Tim kiem theo Role
	List<AccRoleRelationship> find_Role(Role role);

	// Tim kiem theo List Account
	List<AccRoleRelationship> find_Role_list(List<Role> roles);

	List<Account> find_Account(Role role);

	List<Role> find_Role(Account account);

	// Tao mot Relation moi
	AccRoleRelationship create(AccRoleRelationship messages);

	// Cap nhat Relation tai khoan
	AccRoleRelationship update(AccRoleRelationship accRoleRelationship);

	// Xoa relation
	boolean delete(AccRoleRelationship accRoleRelationship);

	// Xoa relation
	boolean deletes(List<AccRoleRelationship> accRoleRelationship);

}
