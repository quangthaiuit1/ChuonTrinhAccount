package trong.lixco.com.bean;

/**
 * Danh muc menu
 */

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;

import trong.lixco.com.entity.Menu;
import trong.lixco.com.service.MenuService;

@ManagedBean
@ViewScoped
public class TestDetailBean {
	private static final ArrayList<Menu> menus = new ArrayList<Menu>();

	public ArrayList<Menu> getMenus() {
		return menus;
	}


	@PostConstruct
	public void init() {
		Menu menu1 = new Menu();
		menu1.setTenHienThi("Menu 1");
		menu1.setUrl("Url 1");
		menus.add(menu1);
	}

	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Item Edited", ((Menu) event.getObject()).getTenHienThi());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Item Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		menus.remove((Menu) event.getObject());
	}
}
