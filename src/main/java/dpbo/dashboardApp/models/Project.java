package dpbo.dashboardApp.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dpbo.dashboardApp.db.DatabaseManager;
import dpbo.dashboardApp.db.ProjectDbController;

public abstract class Project extends ProjectDbController {

    // Atribut
    private int id;
    private String title;
    private String description;
    private Map<Integer, Revision> revisions;
    private int ownerId;
    private LocalDateTime deadline;

    private Connection connection;

    // Konstruktor
    public Project(int id) throws Exception {
        super();
        try {
            this.id = id;
			
			this.title = super.getTitle(id);
			this.description = super.getDescription(id);
			this.client = super.getOwnerId(id);
			this.deadline = super.getDeadline(id);


            this.revisions = new HashMap<Integer,Revision>();
            this.connection = super.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter dan Setter (sesuai UML)
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws Exception {
        this.title = title;
		super.setTitle(id, title);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public int getOwner() {
        return ownerId;
    }

    public void setOwner(int owner) throws Exception {
        this.ownerId = owner;
		super.setOwner(id, owner);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) throws Exception {
        this.deadline = deadline;
		super.setDeadline(id, deadline);
    }

    public List<Revision> getRevision() {
        return new ArrayList<Revision>(revisions.values());
    }

    // toString override
    @Override
    public String toString() {
        return "Project ID: " + id + ", Title: " + title + ", Client: " + client;
    }

    // Method Revisi
    public void addRevision(String key, Revision revision) {
        if (key != null && !key.isEmpty() && revision != null) {
            revisions.put(key, revision);
        } else {
            System.out.println("Invalid key or revision is null.");
        }
    }

    public boolean updateRevision(String key, Revision updatedRevision) {
        if (key == null || key.isEmpty() || updatedRevision == null) {
            System.out.println("Invalid key or updatedRevision is null.");
            return false;
        }
        if (revisions.containsKey(key)) {
            revisions.put(key, updatedRevision);
            return true;
        } else {
            System.out.println("Revision with key " + key + " not found.");
            return false;
        }
    }

    public boolean deleteRevision(String key) {
        if (key == null || key.isEmpty()) {
            System.out.println("Key is null or empty.");
            return false;
        }
        if (revisions.containsKey(key)) {
            revisions.remove(key);
            return true;
        } else {
            System.out.println("Revision with key " + key + " not found.");
            return false;
        }
    }

    public List<Revision> getAllRevisions() {
        return new ArrayList<Revision>(revisions.values());
    }

    // Abstract methods
    public abstract void displayProjectDetails();

    public abstract double calculateEstimateBudget();

    public abstract LocalDateTime calculateEstimateProjectComplete();
}
