package dpbo.dashboardApp.models;

public class Revision {
	private String id;
	private String title;
	private String description;
	private String status;
	private LocalDateTime createdAt;
	public Revision(String id, String title, String description, String status, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String notes) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String desc) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Revision [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status
				+ ", createdAt=" + createdAt + "]";
	}
}
