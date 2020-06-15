package trong.lixco.com.bean;

/**
 * Danh muc menu
 */

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import trong.lixco.com.entity.FormList;
import trong.lixco.com.entity.Program;
import trong.lixco.com.entity.Role;
import trong.lixco.com.entity.UserRight;
import trong.lixco.com.impl.ImplFormList;
import trong.lixco.com.impl.ImplRole;
import trong.lixco.com.impl.ImplUserRight;
import trong.lixco.com.util.Notify;

@ManagedBean
@ViewScoped
public class RoleBean {
	private Notify notify;
	private Program program;
	private List<Role> roles;
	private List<UserRight> userRights;
	private Role role;
	@EJB
	private ImplRole roleService;
	private Role roleEdit;
	@EJB
	private ImplUserRight userRightService;
	@EJB
	private ImplFormList formListService;
	@Inject
	private MenuBean menuBean;

	@PostConstruct
	public void init() {
		if (menuBean.getPrograms().size() != 0) {
			program = menuBean.getPrograms().get(0);
		}
		role = new Role();
		roles = roleService.findByProgram(program);

		List<FormList> formLists = formListService.findByProgram(program);
		userRights = new ArrayList<UserRight>();
		for (FormList fl : formLists) {
			UserRight ur = new UserRight();
			ur.setFormList(fl);
			userRights.add(ur);
		}
	}

	public void ajax_selectProgram() {
		// cai dat lai danh sach role
		roles = roleService.findByProgram(program);
		// Cai dat lai danh sach Useright
		List<FormList> formLists = formListService.findByProgram(program);
		userRights.clear();
		for (FormList fl : formLists) {
			UserRight ur = new UserRight();
			ur.setFormList(fl);
			userRights.add(ur);
		}
		// Reset lai form
		reset();
	}

	/*
	 * Luu role moi
	 */
	public void createOrUpdate() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (role != null) {
			if (role.getId() == null) {
				role.setProgram(program);
				role = roleService.create(role);
				roles.add(role);
			} else {
				role = roleService.update(role);
				int index = roles.indexOf(role);
				roles.set(index, role);
			}
			for (UserRight ur : userRights) {
				if (ur.getId() == null) {
					ur.setRole(role);
					userRightService.create(ur);
				} else {
					ur.setRole(role);
					userRightService.update(ur);
				}
			}
			notify.success();
		}
	}

	/*
	 * Reset lai form tao program (xoa thong tin tren form)
	 */
	public void reset() {
		role = new Role();
		List<FormList> formLists = formListService.findByProgram(program);
		userRights = new ArrayList<UserRight>();
		for (FormList fl : formLists) {
			UserRight ur = new UserRight();
			ur.setFormList(fl);
			userRights.add(ur);
		}
	}

	/*
	 * Chá»‰nh sá»­a lai form tao program (xoa thong tin tren form)
	 */
	public void showEdit() {
		this.role = roleEdit;
		List<FormList> formLists = formListService.findByProgram(program);
		userRights = new ArrayList<UserRight>();
		for (FormList fl : formLists) {
			UserRight ur = new UserRight();
			ur.setFormList(fl);
			userRights.add(ur);
		}
		List<UserRight> temp = userRightService.findUserRightByRole(role);
		for (int i = 0; i < userRights.size(); i++) {
			for (int j = 0; j < temp.size(); j++) {
				if (userRights.get(i).getFormList().getId().equals(temp.get(j).getFormList().getId())) {
					userRights.set(i, temp.get(j));
					break;
				}
			}

		}
	}

	/*
	 * Hien thi program chinh sua
	 */
	public void displayRole(Role event) {
		setRole(event);
	}

	/*
	 * Xoa program
	 */
	public void deleteRole() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (role.getId() != null) {
			boolean status = roleService.delete(role);
			if (status) {
				roles.remove(role);
				reset();
				notify.success();
			} else {
				notify.error();
			}
		} else {
			notify.warning("Chưa chọn quyền!");
		}
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> programs) {
		this.roles = programs;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role program) {
		this.role = program;
	}

	public Role getRoleEdit() {
		return roleEdit;
	}

	public void setRoleEdit(Role programEdit) {
		this.roleEdit = programEdit;
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
