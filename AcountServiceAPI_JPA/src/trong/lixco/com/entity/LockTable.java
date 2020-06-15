package trong.lixco.com.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
@Entity
public class LockTable extends AbstractEntity{
	private int month;
	private int year;
	private boolean locks;
	@OneToOne
	private Program program;
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public boolean isLocks() {
		return locks;
	}
	public void setLocks(boolean lock) {
		this.locks = lock;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	
	
	
}
