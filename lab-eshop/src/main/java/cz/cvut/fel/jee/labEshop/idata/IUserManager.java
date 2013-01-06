package cz.cvut.fel.jee.labEshop.idata;

import java.util.Set;

import cz.cvut.fel.jee.labEshop.model.User;

/**
 * This interaface specify action which must have UserManager.
 * @author Tom
 *
 */
public interface IUserManager {

	/**
	 * This method find user in database based on username
	 * @param username of user to find
	 * @return entity User which has username given as parameter. If there is no user or more then one user then null is returned 
	 */
	public User findUserByUsername(String username);
	
	
	/**
	 * This method add new user to database and set them necessary roles.
	 * @param userToadd user which will be added to database
	 * @param rolesOfUser role which will user has
	 */
	public void addUser(User userToadd, Set<String> rolesOfUser);
	
}
