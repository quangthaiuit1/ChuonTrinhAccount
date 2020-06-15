package trong.lixco.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Entity
public class Menu extends AbstractEntity {
	@Column(nullable = false)
	private String tenHienThi;
	private String icon;
	@NotNull
	private String url;
	private String moTa;

	@ManyToOne
	private Program program;

	@ManyToOne
	private Menu parent;
	

	public String getTenHienThi() {
		return tenHienThi;
	}

	public void setTenHienThi(String tenHienThi) {
		this.tenHienThi = tenHienThi;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	

}
