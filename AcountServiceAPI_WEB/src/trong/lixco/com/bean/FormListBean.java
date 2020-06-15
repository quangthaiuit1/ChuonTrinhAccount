package trong.lixco.com.bean;

/**
 * Danh muc menu
 */

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import trong.lixco.com.entity.FormList;
import trong.lixco.com.entity.Program;
import trong.lixco.com.impl.ImplFormList;
import trong.lixco.com.util.Notify;
import java.io.Serializable;

@Named
@ViewScoped
public class FormListBean implements Serializable {

	private static final long serialVersionUID = 687179534772086156L;
	private Notify notify;
	private Program program;
	private List<FormList> formlists;
	private FormList formList;
	@EJB
	private ImplFormList formListService;
	private FormList formListEdit;
	@Inject
	private MenuBean menuBean;

	@PostConstruct
	public void init() {
		if (menuBean.getPrograms().size() != 0) {
			program = menuBean.getPrograms().get(0);
		}
		formList = new FormList();
		formlists = formListService.findByProgram(program);
	}

	public void ajax_selectProgram() {
		formlists = formListService.findByProgram(program);
	}

	public void test() {
		notify = new Notify(FacesContext.getCurrentInstance());
		notify.warning(menuBean.getMenu().getTenHienThi());
	}

	/*
	 * Luu formlist moi
	 */
	public void createOrUpdate() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (formList != null) {
			if (formList.getId() == null) {
				formList.setProgram(program);
				formList = formListService.create(formList);
				formlists.add(formList);
			} else {
				formList = formListService.update(formList);
				int index = formlists.indexOf(formList);
				formlists.set(index, formList);
			}
			notify.success();
		}
	}

	/*
	 * Reset lai form tao forlist (xoa thong tin tren form)
	 */
	public void reset() {
		formList = new FormList();
	}

	/*
	 * Chinh sua lai form tao formlist (xoa thong tin tren form)
	 */
	public void showEdit() {
		this.formList = formListEdit;
	}

	/*
	 * Xoa formlist
	 */
	public void deleteFormList() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (formList.getId() != null) {
			boolean status = formListService.delete(formList);
			if (status) {
				formlists.remove(formList);
				reset();
				notify.success();
			} else {
				notify.error();
			}
		} else {
			notify.warning("Chưa chọn form!");
		}
	}

	public List<FormList> getFormLists() {
		return formlists;
	}

	public void setFormLists(List<FormList> programs) {
		this.formlists = programs;
	}

	public FormList getFormList() {
		return formList;
	}

	public void setFormList(FormList program) {
		this.formList = program;
	}

	public FormList getFormListEdit() {
		return formListEdit;
	}

	public void setFormListEdit(FormList programEdit) {
		this.formListEdit = programEdit;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

}
