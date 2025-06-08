package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

import dpbo.dashboardApp.db.RevisionDbController;

public class Revision extends RevisionDbController{
	private int id;
	private String notes;
	private LocalDateTime createdAt;
	private int ProjectId;

	public Revision(int id) throws Exception {
		super();
		this.id = id;
		this.notes = super.getNotes(id);
		this.createdAt = super.getCreatedAt(id);
		this.ProjectId = super.getProjectId(id);
	}
	public int getId() {
		return id;
	}
	public String getNotes() {
		return notes;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public int getProjectId() {
		return ProjectId;
	}
	public void setNotes(String notes) throws Exception {
		this.notes = super.setNotes(id, notes);
	}
	@Override
	public String toString() {
		return "Revision [id=" + id + "notes="+ notes + ", createdAt=" + createdAt + "]";
	}
}
