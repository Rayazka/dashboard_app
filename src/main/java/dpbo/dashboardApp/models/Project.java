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

public abstract class Project extends DatabaseManager {

    // Atribut
    private String id;
    private String title;
    private String description;
    private Map<String, Revision> revisions;
    private String client;
    private LocalDateTime deadline;

    private Connection connection;

    // Konstruktor
    public Project(String id, String title, String description, String client, LocalDateTime deadline) {
        super();
        try {
            this.id = id;
            this.title = title;
            this.description = description;
            this.client = client;
            this.deadline = deadline;
            this.revisions = new HashMap<>();
            this.connection = super.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter dan Setter (sesuai UML)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public List<Revision> getRevision() {
        return new ArrayList<>(revisions.values());
    }

    public void addRevision(String key, Revision revision) {
        this.revisions.put(key, revision);
    }

    public void setStatus(String status) {
        // Optional: implement if you have a status field
    }

    // Implementasi database
    public String getProjectNameById(String id) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT title FROM Project WHERE id = '" + id + "'");
        if (rs.next()) {
            return rs.getString("title");
        } else {
            throw new Exception("Project not found with id: " + id);
        }
    }

    public String updateProjectTitle(String id, String title) throws Exception {
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate("UPDATE Project SET title = '" + title + "' WHERE id = '" + id + "'");
        if (rows > 0) {
            return "Title updated successfully.";
        } else {
            throw new Exception("Project not found with id: " + id);
        }
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
        return new ArrayList<>(revisions.values());
    }

    // Abstract methods
    public abstract void displayProjectDetails();

    public abstract double calculateEstimateBudget();

    public abstract LocalDateTime calculateEstimateProjectComplete();
}
