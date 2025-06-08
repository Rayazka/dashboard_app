package dpbo.dashboardApp.models;

import dpbo.dashboardApp.db.UserDbController;

public class User extends UserDbController {
	private int id;
	private String username;

	public User(int id) throws Exception{
		super();
		this.id = id;
		this.username = super.getName(id);
	}
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}
}
