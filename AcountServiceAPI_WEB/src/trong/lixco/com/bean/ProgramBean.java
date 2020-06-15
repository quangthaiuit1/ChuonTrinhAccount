package trong.lixco.com.bean;

/**
 * Danh muc menu
 */

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import trong.lixco.com.entity.Program;
import trong.lixco.com.impl.ImplProgram;
import trong.lixco.com.util.Notify;
import trong.lixco.com.util.ResizeImage;

@ManagedBean
@ViewScoped
public class ProgramBean {
	private Notify notify;
	private List<Program> programs;
	private Program program;
	@EJB
	private ImplProgram programService;
	private Program programEdit;

	@PostConstruct
	public void init() {
		program = new Program();
		programs = programService.findAll();
	}

	/*
	 * Luu menu moi
	 */
	public void createOrUpdate() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (program != null) {
			if (program.getId() == null) {
				program = programService.create(program);
				programs.add(program);
			} else {
				// program.setMenus(new ArrayList<Menu>());
				// Tranh truong hop loi
				// "A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance"
				program = programService.update(program);
				int index = programs.indexOf(program);
				programs.set(index, program);
			}
			notify.success();
		}
	}

	// Cap nhat avatar
	public void uploadAlbum(FileUploadEvent event) {
		notify = new Notify(FacesContext.getCurrentInstance());
		try (InputStream input = event.getFile().getInputstream()) {
			byte[] file = ResizeImage.toByteArray(input);
			program.setLogo(file);
		} catch (Exception e) {
			e.printStackTrace();
			notify.error();
		}

	}

	/*
	 * Reset lai form tao program (xoa thong tin tren form)
	 */
	public void reset() {
		program = new Program();
	}

	/*
	 * Chá»‰nh sá»­a lai form tao program (xoa thong tin tren form)
	 */
	public void showEdit() {
		this.program = programEdit;
	}

	/*
	 * Hien thi program chinh sua
	 */
	public void displayProgram(Program event) {
		setProgram(event);
	}

	/*
	 * Xoa program
	 */
	public void deleteProgram() {
		notify = new Notify(FacesContext.getCurrentInstance());
		if (program.getId() != null) {
			boolean status = programService.delete(program);
			if (status) {
				programs.remove(program);
				notify.success();
			} else {
				notify.error();
			}
		} else {
			notify.warning("Chưa chọn chương trình!");
		}
	}

	public List<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(List<Program> programs) {
		this.programs = programs;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Program getProgramEdit() {
		return programEdit;
	}

	public void setProgramEdit(Program programEdit) {
		this.programEdit = programEdit;
	}

}
