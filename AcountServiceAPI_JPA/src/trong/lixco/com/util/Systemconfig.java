package trong.lixco.com.util;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import trong.lixco.com.entity.Program;

@Entity
public class Systemconfig {
	@Id
	@GeneratedValue
	private Long id;
	private String pathMySQL;
	private String userName;
	private String password;
	private String pathSaveDatabase;
	@OneToOne
	private Program program;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "systemconfig")
	private List<DatabaseName> databaseNames;

	public Systemconfig() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPathMySQL() {
		return pathMySQL;
	}

	public void setPathMySQL(String pathMySQL) {
		this.pathMySQL = pathMySQL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPathSaveDatabase() {
		return pathSaveDatabase;
	}

	public void setPathSaveDatabase(String pathSaveDatabase) {
		this.pathSaveDatabase = pathSaveDatabase;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public List<DatabaseName> getDatabaseNames() {
		return databaseNames;
	}

	public void setDatabaseNames(List<DatabaseName> databaseNames) {
		this.databaseNames = databaseNames;
	}
}
