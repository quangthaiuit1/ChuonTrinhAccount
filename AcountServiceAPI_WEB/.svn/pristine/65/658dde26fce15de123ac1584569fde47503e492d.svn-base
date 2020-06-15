package trong.lixco.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.omnifaces.cdi.ViewScoped;

import trong.lixco.com.entity.LockTable;
import trong.lixco.com.entity.Program;
import trong.lixco.com.impl.ImplLockTable;
import trong.lixco.com.impl.ImplProgram;
import trong.lixco.com.util.Notify;

@Named
@ViewScoped
public class LockTablesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Notify notify;

	@EJB
	private ImplLockTable lockTableService;
	private List<LockTable> lockTables;
	private LockTable lockTable;
	private Program program;
	@EJB
	private ImplProgram programService;

	@PostConstruct
	public void init() {
		program = programService.getFirst();
		lockTables = lockTableService.findByProgram(program);
		lockTable = new LockTable();
		DateTime date = new DateTime(new Date());
		lockTable.setMonth(date.getMonthOfYear());
		lockTable.setYear(date.getYear());
	}

	public void createOrUpdate() {
		notify = new Notify(FacesContext.getCurrentInstance());
		LockTable lt = lockTableService.findMonthYear(lockTable.getMonth(), lockTable.getYear(), program);
		if (lt != null) {
			lt.setLocks(lockTable.isLocks());
			lockTable = lockTableService.update(lt);
			int index = lockTables.indexOf(lockTable);
			lockTables.set(index, lockTable);
			notify.success();
		} else {
			lockTable.setProgram(program);
			lockTable = lockTableService.create(lockTable);
			lockTables.add(lockTable);
			notify.success();
		}
		int month=lockTable.getMonth();
		int year=lockTable.getYear();
		lockTable = new LockTable();
		lockTable.setMonth(month);
		lockTable.setYear(year);
	}

	public void ajax_program() {
		lockTables = lockTableService.findByProgram(program);
	}

	public List<LockTable> getLockTables() {
		return lockTables;
	}

	public void setLockTables(List<LockTable> lockTables) {
		this.lockTables = lockTables;
	}

	public LockTable getLockTable() {
		return lockTable;
	}

	public void setLockTable(LockTable lockTable) {
		this.lockTable = lockTable;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

}
