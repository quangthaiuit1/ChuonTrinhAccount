package trong.lixco.com.bean;

/**
 * Danh muc menu
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class TestBean {
	String name = "trong";

	public void proccessListenner() {
		name = "oghin";
		System.out.println("actionListenner TestBean");
	}

	public void proccess() {
		System.out.println("Action");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
