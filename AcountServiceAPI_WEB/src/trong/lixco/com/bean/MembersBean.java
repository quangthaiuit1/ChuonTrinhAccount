package trong.lixco.com.bean;

/**
 * Danh muc menu
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.Department;
import trong.lixco.com.entity.LevelDep;
import trong.lixco.com.entity.Member;
import trong.lixco.com.impl.ImplAccount;
import trong.lixco.com.impl.ImplMember;
import trong.lixco.com.service.DepartmentService;
import trong.lixco.com.service.LevelDepService;
import trong.lixco.com.servicepublic.DepartmentServicePublic;
import trong.lixco.com.servicepublic.DepartmentServicePublicProxy;
import trong.lixco.com.servicepublic.EmployeeServicePublic;
import trong.lixco.com.servicepublic.EmployeeServicePublicProxy;
import trong.lixco.com.servicepublic.LevelDepServicePublic;
import trong.lixco.com.servicepublic.LevelDepServicePublicProxy;
import trong.lixco.com.util.Notify;
import trong.lixco.com.util.ResizeImage;

@ManagedBean
@ViewScoped
public class MembersBean {
	private Notify notify;
	private List<Member> members;
	private List<Member> memberFilters;
	private List<Department> departments;
	private Member member;
	private String searchText;
	@EJB
	private ImplMember memberService;
	@Inject
	private DepartmentService departmentService;
	@EJB
	private ImplAccount accountService;

	private Member memberEdit;

	private Account account;

	private Department departmentS;

	@Inject
	LevelDepService levelDepService;

	@PostConstruct
	public void init() {
		member = new Member();
		members = new ArrayList<Member>();
		departments = departmentService.findAll();
		searchItem();

	}

	// Cap nhat avatar
	public void uploadAlbum(FileUploadEvent event) {
		notify = new Notify(FacesContext.getCurrentInstance());
		try (InputStream input = event.getFile().getInputstream()) {
			byte[] file = ResizeImage.noResize(input);
			member.setSign(file);
			createOrUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			notify.error();
		}

	}

	/*
	 * Luu menu moi
	 */

	public void createOrUpdate() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (member != null) {
			if (member.getCode() == null || "".equals(member.getCode().trim()) || member.getName() == null
					|| "".equals(member.getName().trim())) {
				notice("Điền đầy đủ thông tin!");
			} else {
				if (member.getId() == null) {
					notice("Không tạo nhân viên mới tại đây.");
//					Account acc = accountService.find_User(account.getUserName());
//					if (acc == null) {
//						member = memberService.create(member);
//						account.setMember(member);
//						account = accountService.create(account);
//						members.add(member);
//						notify.success();
//					} else {
//						if ("".equals(account.getUserName())) {
//							member = memberService.create(member);
//							members.add(member);
//							notify.success();
//						} else {
//							notice("Tên đăng nhập đã tồn tại!");
//						}
//					}
				} else {
					if (account.getId() != null) {
						Account acc = accountService.findById(account.getId());
						if (!acc.getUserName().equals(account.getUserName())) {
							Account accTemp = accountService.find_User(account.getUserName());
							if (accTemp == null) {
								if (account.getId() != null) {
									account = accountService.update(account);
								} else {
									account.setMember(member);
									account = accountService.create(account);
								}
								member = memberService.update(member);
								int index = members.indexOf(member);
								members.set(index, member);
								notify.success();
							} else {
								if ("".equals(account.getUserName())) {
									member = memberService.create(member);
									members.add(member);
									notify.success();
								} else {
									notice("Không cập nhật được, tên đăng nhập đã tồn tại!");
								}
							}
						} else {
							if (account.getId() != null) {
								account = accountService.update(account);
							} else {
								account.setMember(member);
								account = accountService.create(account);
							}
							member = memberService.update(member);
							int index = members.indexOf(member);
							members.set(index, member);
							notify.success();
						}
					} else {
						try {
							account.setMember(member);
							account = accountService.create(account);
							member = memberService.update(member);
							int index = members.indexOf(member);
							members.set(index, member);
							notify.success();
						} catch (Exception e) {
							notice("Tên đăng nhập đã tồn tại!");
						}
						
					}
				}
			}
		}
	}

	public void syncCenter() {
		try {
			/*
			 * Dong bo vi tri phong
			 */
			LevelDepServicePublic levelDepServicePublic = new LevelDepServicePublicProxy();
			trong.lixco.com.servicepublic.LevelDep[] levelDeps = levelDepServicePublic.findAll();
			for (int i = 0; i < levelDeps.length; i++) {
				LevelDep levelDep = levelDepService.findByCode(levelDeps[i].getCode());
				if (levelDep != null) {
					levelDep.setName(levelDeps[i].getName());
					levelDep.setLevel(levelDeps[i].getLevel());
					levelDep.setDisable(levelDeps[i].isDisable());
					levelDepService.update(levelDep);
				} else {
					LevelDep lv = new LevelDep();
					lv.setCode(levelDeps[i].getCode());
					lv.setLevel(levelDeps[i].getLevel());
					lv.setName(levelDeps[i].getName());
					lv.setDisable(levelDeps[i].isDisable());
					levelDepService.create(lv);
				}
			}
			/*
			 * Dong bo phong ban
			 */
			DepartmentServicePublic departmentServicePublic = new DepartmentServicePublicProxy();
			trong.lixco.com.servicepublic.DepartmentDTO[] departments = departmentServicePublic.findAll();
			for (int i = 0; i < departments.length; i++) {
				Department department = departmentService.findByCode(departments[i].getCode());
				if (department != null) {
					department.setName(departments[i].getName());
					department.setLevelDep(levelDepService.findByCode(departments[i].getCodeLevelDep()));
					departmentService.update(department);
				} else {
					department = new Department();
					department.setCode(departments[i].getCode());
					department.setName(departments[i].getName());
					department.setLevelDep(levelDepService.findByCode(departments[i].getCodeLevelDep()));
					departmentService.update(department);
				}
			}
			/*
			 * Dog bo nhan vien
			 */
			EmployeeServicePublic employeeServicePublic = new EmployeeServicePublicProxy();
			trong.lixco.com.servicepublic.EmployeeDTO[] employees = employeeServicePublic.findAll();
			for (int i = 0; i < employees.length; i++) {
				Member member = memberService.findByCode(employees[i].getCode());
				if (member != null) {
					member.setName(employees[i].getName());
					member.setEmail(employees[i].getEmail());
					if (employees[i].getCodeDepart() != null) {
						member.setDepartment(departmentService.findByCode(employees[i].getCodeDepart()));
					}
					// member.setSign(employees[i].getSign());
					member.setDisable(employees[i].isLayOff());
					try {
						member.getAccount().setDisable(employees[i].isLayOff());
					} catch (Exception e) {
					}
					memberService.update(member);
				} else {
					member = new Member();
					member.setCode(employees[i].getCode());
					member.setName(employees[i].getName());
					member.setEmail(employees[i].getEmail());
					if (employees[i].getCodeDepart() != null) {
						member.setDepartment(departmentService.findByCode(employees[i].getCodeDepart()));
					}
					// member.setSign(employees[i].getSign());
					member.setDisable(employees[i].isLayOff());
					try {
						member.getAccount().setDisable(employees[i].isLayOff());
					} catch (Exception e) {
					}
					memberService.create(member);
				}
			}
			/*
			 * Dong bo truong don vi
			 */
			for (int i = 0; i < departments.length; i++) {
				Department department = departmentService.findByCode(departments[i].getCode());
				if (department != null) {
					if (departments[i].getCodeDepart() != null)
						department
								.setDepartment(departmentService.findByCode(departments[i].getCodeDepart()));
					if (departments[i].getCodeEmp() != null)
						department.setMember(memberService.findByCode(departments[i].getCodeEmp()));
					departmentService.update(department);
				}

			}
			notify = new Notify(FacesContext.getCurrentInstance());
			notice("Cập nhật thành công");
		} catch (Exception e) {
			e.printStackTrace();
			noticeError("Xảy ra lỗi trong quá trình cập nhật.");
		}
	}

	/*
	 * Reset lai form tao member (xoa thong tin tren form)
	 */
	public void reset() {
		member = new Member();
		account = new Account();
	}

	public void showEdit() {
		this.member = memberEdit;
		Account acc = accountService.findMember(member);
		if (acc != null) {
			this.account = acc;
		} else {
			this.account = new Account();
		}
	}

	/*
	 * Hien thi member chinh sua
	 */
	public void displayMember(Member event) {
		setMember(event);
	}

	/*
	 * Xoa member
	 */
	public void deleteMember() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (memberEdit != null) {
			boolean status = memberService.delete(memberEdit);
			if (status) {
				members.remove(memberEdit);
				notify.success();
			} else {
				notify.error();
			}
		} else {
			notify.warning("Chưa chọn nhân viên!");
		}
	}

	/*
	 * Tao nhan vien moi
	 */
	public void showNew() {
		member = new Member();
		account = new Account();
	}

	/*
	 * Tim kiem nhan vien (simple)
	 */
	public void searchItem() {
		if (searchText == null)
			searchText = "";
		members = memberService.findRange(departmentS, searchText, new String[] { "code", "name" });

		List<Member> linkedListTemp = new LinkedList<Member>();
		if (searchText.contains("D")) {
			linkedListTemp = memberService.findRange(departmentS, "%" + searchText.replace("D", "Đ") + "%",
					new String[] { "code", "name" });
			members.addAll(linkedListTemp);
		}
		if (searchText.contains("d")) {
			linkedListTemp = memberService.findRange(departmentS, "%" + searchText.replace("d", "đ") + "%",
					new String[] { "code", "name" });
			members.addAll(linkedListTemp);
		}
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getMemberEdit() {
		return memberEdit;
	}

	public void setMemberEdit(Member memberEdit) {
		this.memberEdit = memberEdit;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDepartmentS() {
		return departmentS;
	}

	public void setDepartmentS(Department departmentS) {
		this.departmentS = departmentS;
	}

	public List<Member> getMemberFilters() {
		return memberFilters;
	}

	public void setMemberFilters(List<Member> memberFilters) {
		this.memberFilters = memberFilters;
	}
	public void notice(String content) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Thông báo!", content);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	public void noticeError(String content) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Thông báo!", content);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
}
