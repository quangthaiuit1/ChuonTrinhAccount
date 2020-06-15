package trong.lixco.com.bean;

/**
 * Danh muc menu
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.component.tree.Tree;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import trong.lixco.com.entity.Menu;
import trong.lixco.com.entity.Program;
import trong.lixco.com.impl.ImplMenu;
import trong.lixco.com.impl.ImplProgram;
import trong.lixco.com.util.IconAwesome;
import trong.lixco.com.util.Notify;

@Named
@ViewScoped
public class MenuBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Notify notify;
	private List<Menu> menus;
	private List<Program> programs;
	private Program program;
	private List<String> icons;
	private TreeNode selectedNode;
	private TreeNode root;
	private Menu menu;
	@EJB
	private ImplProgram programService;
	@EJB
	private ImplMenu menuService;

	private MenuModel model;

	/**
	 * Cai dat hien thi dau tien khi load form
	 */
	@PostConstruct
	public void installMenu() {
		init();
		setupMenuModel();
		setupMenuPreview();
//		 setupListIcon();
	}

	public void init() {
		menu = new Menu();
		program = programService.findByName("hethong");
	}

	public void ajax_setupMenu() {
		setupMenuPreview();
		setupListIcon();
	}

	/*
	 * Cai dat danh sach Icon
	 */
	public void setupListIcon() {
		icons = new ArrayList<String>();
		String[] temp = IconAwesome.getIcon();
		for (int i = 0; i < temp.length; i++) {
			icons.add("fa "+temp[i]);
		}
	}

	public void setupicon(String param) {
		menu.setIcon(param);
	}

	/*
	 * Cai dat danh sach menu preview
	 */
	public void setupMenuModel() {
		if (program != null) {
		menus = menuService.find_Program(program);
		programs = programService.findAll();
		model = new DefaultMenuModel();
		createMenu(menus);
		}
	}

	public void createMenuModel(List<Menu> menus) {
		for (Menu menu : menus) {
			if (menu.getParent() == null) {
				boolean statusSubmenu = false;
				for (Menu checkM : menus) {
					if (menu.equals(checkM.getParent())) {
						statusSubmenu = true;
						break;
					}
				}
				if (statusSubmenu) {
					DefaultSubMenu submenu = new DefaultSubMenu(menu.getId()+"_"+menu.getTenHienThi());
					submenu.setIcon(menu.getIcon());
					List<Object> objects = createDynamicMenuModel(menu, menus);
					for (int i = 0; i < objects.size(); i++) {
						try {
							DefaultMenuItem item = (DefaultMenuItem) objects.get(i);
							submenu.addElement(item);
						} catch (Exception e) {
							DefaultSubMenu item = (DefaultSubMenu) objects.get(i);
							submenu.addElement(item);
						}
					}

					model.addElement(submenu);
				} else {
					DefaultMenuItem item = new DefaultMenuItem(menu.getId()+"_"+menu.getTenHienThi());
					item.setUrl(menu.getUrl());
					item.setIcon(menu.getIcon());
					model.addElement(item);
				}
			}
		}
	}

	public List<Object> createDynamicMenuModel(Menu menu, List<Menu> menus) {
		List<Object> results = new ArrayList<Object>();
		// Kiem tra co menu con hay khong
		List<Menu> subs = new ArrayList<Menu>();
		for (Menu checkM : menus) {
			if (menu.equals(checkM.getParent())) {
				subs.add(checkM);
			}
		}
		for (Menu subM : subs) {
			boolean statusSubmenu = false;
			for (Menu checkM : menus) {
				if (subM.equals(checkM.getParent())) {
					statusSubmenu = true;
					break;
				}
			}
			if (statusSubmenu) {
				DefaultSubMenu item = new DefaultSubMenu(subM.getId()+"_"+subM.getTenHienThi());
				List<Object> objSub = createDynamicMenuModel(subM, menus);
				for (int i = 0; i < objSub.size(); i++) {
					try {
						DefaultMenuItem itemS = (DefaultMenuItem) objSub.get(i);
						item.addElement(itemS);
					} catch (Exception e) {
						DefaultSubMenu itemS = (DefaultSubMenu) objSub.get(i);
						item.addElement(itemS);
					}
				}
				results.add(item);
			} else {
				DefaultMenuItem item = new DefaultMenuItem(subM.getId()+"_"+subM.getTenHienThi());
				item.setUrl(subM.getUrl());
				item.setIcon(subM.getIcon());
				results.add(item);
			}
		}
		return results;
	}

	/*
	 * Cai dat danh sach menu preview
	 */
	private Tree tree;
		
	public void setupMenuPreview() {
		if (program != null) {
			menus = menuService.find_Program(program);
			programs = programService.findAll();
			treePreview(menus);
		}
	}

	/*
	 * Luu menu moi
	 */
	public void createOrUpdate() {
		notify = new Notify(FacesContext.getCurrentInstance());
		try {
			if (menu != null) {
				menu.setProgram(program);
				if (menu.getId() == null) {
					// Tao moi
					menu = menuService.create(menu);
					setupListIcon();
				} else {
					// Cap nhat
					// Tranh truong hợp loi
					// "A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance"
					// menu.setSubMenu(new ArrayList<Menu>());
					menu = menuService.update(menu);
				}
				// Cap nhat lai cac thanh phan giao dien
				setupMenuPreview();
				notify.success();
			}
		} catch (Exception e) {
			notify.error();
		}
	}

	/*
	 * Reset lai form tao menu (xoa thong tin tren form)
	 */
	public void reset() {
		menu = new Menu();
	}

	/*
	 * Hien thi menu chinh sua
	 */
	public void displayMenu(NodeSelectEvent event) {
		String id = event.getTreeNode().toString().substring(0, event.getTreeNode().toString().indexOf("_"));
		menu = menuService.find_ID(Long.parseLong(id));
	}

	/*
	 * Xoa menu
	 */
	public void deleteMenu() {
		String menuStr=selectedNode.toString();
		menuStr=menuStr.substring(0, menuStr.indexOf("_"));
		Menu menu = menuService.find_ID(Long.parseLong(menuStr));
		menuService.delete(menu);
		selectedNode.getChildren().clear();
		selectedNode.getParent().getChildren().remove(selectedNode);
		selectedNode.setParent(null);
		selectedNode = null;
		setupMenuPreview();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * Tao cay Submenu
	 * 
	 * @param menu
	 *            : Menu cha
	 * @param nodeParent
	 *            : Node cha
	 * @return mot list menu con cua menu cha
	 */
	public List<TreeNode> subTreePreview(Menu menu, TreeNode nodeParent) {
		List<TreeNode> results = new ArrayList<TreeNode>();
		List<Menu> subs = new ArrayList<Menu>();
		for (Menu checkM : menus) {
			if (menu.equals(checkM.getParent())) {
				subs.add(checkM);
			}
		}
		for (Menu subM : subs) {
			boolean statusSubmenu = false;
			for (Menu checkM : menus) {
				if (subM.equals(checkM.getParent())) {
					statusSubmenu = true;
					break;
				}
			}
			TreeNode item = new DefaultTreeNode(subM.getId()+"_"+subM.getTenHienThi(), nodeParent);
			if (statusSubmenu) {
				List<TreeNode> objSub = subTreePreview(subM, item);
				for (int i = 0; i < objSub.size(); i++) {
					item.getChildren().add(objSub.get(i));
				}
			}
			item.setExpanded(true);
			results.add(item);
		}
		return results;
	}

	/**
	 * tao cay preview danh sach menu truyen vao "root"
	 * 
	 * @param menus
	 *            : danh sach tat ca cac cap menu
	 */

	public void treePreview(List<Menu> menus) {
		if(tree!=null){
			tree.setAnimate(true);
		}
		root = new DefaultTreeNode("Root", null);
		root.setExpanded(true);
		for (Menu menu : menus) {
			if (menu.getParent() == null) {
				boolean statusSubmenu = false;
				for (Menu checkM : menus) {
					if (menu.equals(checkM.getParent())) {
						statusSubmenu = true;
						break;
					}
				}
				TreeNode node = new DefaultTreeNode(menu.getId()+"_"+menu.getTenHienThi(), root);
				node.setExpanded(true);
				if (statusSubmenu) {
					List<TreeNode> objects = subTreePreview(menu, node);
					for (int i = 0; i < objects.size(); i++) {
						TreeNode item = objects.get(i);
						item.setExpanded(true);
						node.getChildren().add(item);
						
					}

				}
				root.getChildren().add(node);
			}
		}
	}

	/**
	 * Xay dung he thong menu hien thi cho moi chuong trinh
	 * 
	 * @param menus
	 *            : lay he danh sach menu cua tat ca cac chuong trinh
	 */
	public void createMenu(List<Menu> menus) {
		for (Menu menu : menus) {
			if (menu.getParent() == null) {
				boolean statusSubmenu = false;
				for (Menu checkM : menus) {
					if (menu.equals(checkM.getParent())) {
						statusSubmenu = true;
						break;
					}
				}
				if (statusSubmenu) {
					DefaultSubMenu submenu = new DefaultSubMenu(menu.getId()+"_"+menu.getTenHienThi());
					submenu.setIcon(menu.getIcon());
					List<Object> objects = createDynamicMenu(menu);
					for (int i = 0; i < objects.size(); i++) {
						try {
							DefaultMenuItem item = (DefaultMenuItem) objects.get(i);
							submenu.addElement(item);
						} catch (Exception e) {
							DefaultSubMenu item = (DefaultSubMenu) objects.get(i);
							submenu.addElement(item);
						}
					}

					model.addElement(submenu);
				} else {
					DefaultMenuItem item = new DefaultMenuItem(menu.getId()+"_"+menu.getTenHienThi());
					item.setUrl(menu.getUrl());
					item.setIcon(menu.getIcon());
					model.addElement(item);
				}
			}
		}
	}

	/**
	 * Ham cai dat cho menu he thong Ham goi de quy de tim tat ca cac menu con
	 * 
	 * @param menu
	 *            : menu cha
	 * @return danh sach menu con theo menu cha truyen vao
	 */
	public List<Object> createDynamicMenu(Menu menu) {
		List<Object> results = new ArrayList<Object>();
		// Kiem tra co menu con hay khong
		List<Menu> subs = new ArrayList<Menu>();
		for (Menu checkM : menus) {
			if (menu.equals(checkM.getParent())) {
				subs.add(checkM);
			}
		}
		for (Menu subM : subs) {
			boolean statusSubmenu = false;
			for (Menu checkM : menus) {
				if (subM.equals(checkM.getParent())) {
					statusSubmenu = true;
					break;
				}
			}
			if (statusSubmenu) {
				DefaultSubMenu item = new DefaultSubMenu(subM.getId()+"_"+subM.getTenHienThi());
				List<Object> objSub = createDynamicMenu(subM);
				for (int i = 0; i < objSub.size(); i++) {
					try {
						DefaultMenuItem itemS = (DefaultMenuItem) objSub.get(i);
						item.addElement(itemS);
					} catch (Exception e) {
						DefaultSubMenu itemS = (DefaultSubMenu) objSub.get(i);
						item.addElement(itemS);
					}
				}
				results.add(item);
			} else {
				DefaultMenuItem item = new DefaultMenuItem(subM.getId()+"_"+subM.getTenHienThi());
				item.setUrl(subM.getUrl());
				item.setIcon(subM.getIcon());
				results.add(item);
			}
		}
		return results;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public List<Program> getPrograms() {
		return programs;
	}

	public MenuModel getModel() {
		return model;
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<String> getIcons() {
		return icons;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
	

}
