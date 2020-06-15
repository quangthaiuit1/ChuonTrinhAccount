package trong.lixco.com.bean;

/**
 * Danh muc menu
 */

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import trong.lixco.com.entity.Department;
import trong.lixco.com.entity.LevelDep;
import trong.lixco.com.service.DepartmentService;
import trong.lixco.com.service.LevelDepService;
import trong.lixco.com.util.Notify;

@ManagedBean
@ViewScoped
public class DeparmentBean {
	private List<Department> departments;
	private List<Department> departmentParams;
	private Department department;
	private String searchText;
	@Inject
	private DepartmentService departmentService;
	private Department departmentEdit;
	@Inject
	private LevelDepService levelDepService;

	private List<LevelDep> levelDeps;

	@PostConstruct
	protected void initItem() {
		department = new Department();
		levelDeps = levelDepService.findAllDisable();
		departmentParams = departmentService.findAllDisableSort();
		searchItem();
	}

	

	/*
	 * Reset lai form tao member (xoa thong tin tren form)
	 */
	public void reset() {
		department = new Department();
	}

	public void showEdit() {
		this.department = departmentEdit;
	}

	/*
	 * Hien thi member chinh sua
	 */
	public void display(Department event) {
		setDepartment(event);
	}

	public void searchItem() {
		departments = departmentService.findAll();
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartmentEdit() {
		return departmentEdit;
	}

	public void setDepartmentEdit(Department departmentEdit) {
		this.departmentEdit = departmentEdit;
	}

	public List<LevelDep> getLevelDeps() {
		return levelDeps;
	}

	public void setLevelDeps(List<LevelDep> levelDeps) {
		this.levelDeps = levelDeps;
	}

	public List<Department> getDepartmentParams() {
		return departmentParams;
	}

	public void setDepartmentParams(List<Department> departmentParams) {
		this.departmentParams = departmentParams;
	}

}
