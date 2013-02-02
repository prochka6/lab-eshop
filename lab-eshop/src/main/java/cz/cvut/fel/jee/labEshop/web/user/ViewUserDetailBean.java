package cz.cvut.fel.jee.labEshop.web.user;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.international.status.Messages;

import cz.cvut.fel.jee.labEshop.exceptions.EntityNotFoundException;
import cz.cvut.fel.jee.labEshop.manager.UserManager;
import cz.cvut.fel.jee.labEshop.model.User;

/**
 * Administrator view user detail controller.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
@Model
public class ViewUserDetailBean {

	@Inject
	private UserManager userManager;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Messages messages;

	private Long userId;
	private User user;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		if (user == null) {
			loadUser();
		}

		return user;
	}

	public void loadUser() {
		if (facesContext.isValidationFailed()) {
			navigateToUsersListPage();
			return;
		}
		try {
			user = userManager.findUser(userId);
		} catch (EntityNotFoundException enf) {
			navigateToUsersListPage();
		}
	}

	private void navigateToUsersListPage() {
		messages.error("User id={0} does not exist.", userId);
		facesContext.getApplication().getNavigationHandler()
				.handleNavigation(facesContext, null, "/admin/users?faces-redirect=true");
	}

}
