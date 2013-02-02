package cz.cvut.fel.jee.labEshop.web.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;

import cz.cvut.fel.jee.labEshop.filter.SortModel;
import cz.cvut.fel.jee.labEshop.filter.UserListFilter;
import cz.cvut.fel.jee.labEshop.manager.UserManager;
import cz.cvut.fel.jee.labEshop.model.User;

/**
 * Users management bean.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Named
@ViewScoped
public class ViewUsersBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private UserManager userManager;

	private transient List<User> users;
	private transient LazyDataModel<User> tableModel;

	public List<User> getUsers() {
		if (users == null) {
			users = userManager.findAllUsers();
		}

		return users;
	}

	public LazyDataModel<User> getTableModel() {
		return tableModel;
	}

	@PostConstruct
	void init() {
		log.debug("Initializing table data model.");
		this.tableModel = new UsersTableDataModel();
	}

	private class UsersTableDataModel extends LazyDataModel<User> {

		private static final long serialVersionUID = 1L;

		@Override
		public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder,
				Map<String, String> filters) {
			// Refactor to some more generic solution ???
			UserListFilter filter = new UserListFilter();
			filter.setFirstRow(first);
			filter.setMaxRows(pageSize);
			if (sortField != null) {
				String field = sortField;
				if ("fullName".equals(sortField)) {
					field = "lastName";
				}
				SortModel sortModel = new SortModel();
				if (sortOrder == SortOrder.ASCENDING) {
					sortModel.addProperty(field, cz.cvut.fel.jee.labEshop.filter.SortOrder.ASCENDING);
				} else {
					sortModel.addProperty(field, cz.cvut.fel.jee.labEshop.filter.SortOrder.DESCENDING);
				}
				filter.setSortModel(sortModel);
			}
			for (Entry<String, String> entry : filters.entrySet()) {
				if ("username".equals(entry.getKey())) {
					filter.setUsername(entry.getValue());
				} else if ("email".equals(entry.getKey())) {
					filter.setEmail(entry.getValue());
				} else if ("fullName".equals(entry.getKey())) {
					filter.setName(entry.getValue());
				}
			}

			List<User> data = userManager.findByFilter(filter);
			setRowCount(userManager.countByFilter(filter));

			return data;
		}
	}
}
