package cz.cvut.fel.jee.labEshop.web.category;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.status.Messages;
import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.manager.CategoryManager;
import cz.cvut.fel.jee.labEshop.model.Category;

/**
 * Categories JSF controller.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Named("categoriesBean")
@ViewScoped
public class CategoriesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private Messages messages;

	@Inject
	private CategoryManager categoryManager;

	private transient List<Category> categories;

	private Category selected;

	public String submit() {
		if (selected == null) {
			throw new IllegalStateException();
		}

		if (isNew()) {
			categoryManager.createCategory(selected);
			log.info("Category \"{}\" created.", selected.getName());
			messages.info("Category created.");
		} else {
			selected = categoryManager.updateCategory(selected);
			log.info("Category [id={}] \"{}\" updated.", selected.getId(), selected.getName());
			messages.info("Category updated.");
		}

		resetForm();
		selected = null;
		categories = null;
		return null;
	}

	public String newCategory() {
		resetForm();
		selected = new Category();

		return null;
	}

	public String selectCategory(Category category) {
		resetForm();
		selected = category;

		return null;
	}

	private void resetForm() {
		UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
		((UIInput) root.findComponent(":detailForm:categoryName")).resetValue();
		((UIInput) root.findComponent(":detailForm:categoryComment")).resetValue();
	}

	public Category getSelected() {
		return selected;
	}

	public List<Category> getCategories() {
		if (categories == null) {
			categories = categoryManager.findAllCategories();
		}

		return categories;
	}

	public boolean isNew() {
		return selected != null && selected.getId() == null ? true : false;
	}

	public boolean isUpdate() {
		return selected != null && selected.getId() != null ? true : false;
	}

	public boolean isSelection() {
		return selected != null ? true : false;
	}

}
