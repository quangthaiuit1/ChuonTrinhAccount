package trong.lixco.com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import trong.lixco.com.entity.AccRoleRelationship;
import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.Department;
import trong.lixco.com.entity.Program;
import trong.lixco.com.entity.Role;
import trong.lixco.com.entity.UserRight;
import trong.lixco.com.impl.ImplAccRoleRelationship;
import trong.lixco.com.impl.ImplAccount;
import trong.lixco.com.impl.ImplMember;
import trong.lixco.com.impl.ImplRole;
import trong.lixco.com.impl.ImplUserRight;
import trong.lixco.com.service.DepartmentService;
import trong.lixco.com.util.Notify;

@ManagedBean
@ViewScoped
public class RelationshipRoleAccBean {
	private Notify notify;
	private List<Department> departments;
	private Department selectDep;
	private List<Account> accounts;
	private Account selectAcc;
	private List<Role> roles;
	private List<Role> selectRoles;
	private List<UserRight> userRights;

	private Program program;

	@Inject
	private DepartmentService departmentService;
	@EJB
	private ImplAccount accountService;
	@EJB
	private ImplRole roleService;
	@EJB
	private ImplMember memberService;
	@EJB
	private ImplAccRoleRelationship accRoleRelationshipService;
	@EJB
	private ImplUserRight userRightService;

	@PostConstruct
	public void init() {
		installList();
		accounts=new ArrayList<Account>();
		selectRoles = new ArrayList<Role>();
	}

	public void saveOrUpdate() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (selectAcc != null) {
			Account acc = accountService.findMember(selectAcc.getMember());
			if (acc != null) {
				List<AccRoleRelationship> deletes = accRoleRelationshipService.find_Account(acc, program);
				accRoleRelationshipService.deletes(deletes);
				if (selectRoles.size() != 0) {
					for (int i = 0; i < selectRoles.size(); i++) {
						AccRoleRelationship rl = new AccRoleRelationship();
						rl.setAccount(acc);
						rl.setRole(selectRoles.get(i));
						accRoleRelationshipService.create(rl);
					}
				}
				notify.success();
			} else {
				notify.warning("Nhân viên này chưa có tài khoản!");
			}
		}
	}

	public void detailRole(Role role) {
		userRights = userRightService.findUserRightByRole(role);
	}

	public void ajax_selectProgram() {
		// cai dat lai danh sach role
		roles = roleService.findByProgram(program);
		ajax_selectMember();

	}

	private void installList() {
		departments = departmentService.findAll();

	}

	public void ajax_selectDeparment() {
		if (selectDep != null) {
			accounts=accountService.findByDepartment(selectDep);
		}
	}

	public void ajax_selectMember() {
		if (selectAcc != null) {
			selectRoles = accRoleRelationshipService.find_Role(selectAcc);
		}
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getSelectDep() {
		return selectDep;
	}

	public void setSelectDep(Department selectDep) {
		this.selectDep = selectDep;
	}


	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}


	public Account getSelectAcc() {
		return selectAcc;
	}

	public void setSelectAcc(Account selectAcc) {
		this.selectAcc = selectAcc;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Role> getSelectRoles() {
		return selectRoles;
	}

	public void setSelectRoles(List<Role> selectRoles) {
		this.selectRoles = selectRoles;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public List<UserRight> getUserRights() {
		return userRights;
	}

	public void setUserRights(List<UserRight> userRights) {
		this.userRights = userRights;
	}

}
