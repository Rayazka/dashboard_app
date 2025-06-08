package dpbo.dashboardApp.models;

class User {
	private String id;
	private String username;
	public User(String id, String username) {
		this.id = id;
		this.username = username;
	}
	public String getId() {
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
