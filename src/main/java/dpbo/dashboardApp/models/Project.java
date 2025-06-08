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

<<<<<<< HEAD
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
=======
    public void setDeadline(LocalDate deadline) {
        this.deadline = (deadline != null ? deadline : LocalDate.now().plusMonths(1));
    }

    @Override
    public void addRevision(Revision revision) {
        if (revision != null && revision.getId() != null && !revision.getId().trim().isEmpty()) {
            this.revisionsMap.put(revision.getId(), revision);
        } else {
            System.out.println("Error: Cannot add invalid revision (null or no ID).");
        }
    }

    @Override
    public boolean removeRevision(String revisionId) {
        if (revisionId == null || revisionId.trim().isEmpty()) {
            return false;
        }
        return this.revisionsMap.remove(revisionId) != null;
    }

    @Override
    public Revision findRevision(String revisionId) throws RevisionNotFoundException {
        if (revisionId == null || revisionId.trim().isEmpty()) {
            throw new RevisionNotFoundException("Revision ID to find cannot be null or empty.");
        }
        Revision revision = this.revisionsMap.get(revisionId);
        if (revision == null) {
            throw new RevisionNotFoundException("Revision with ID '" + revisionId + "' not found in project '" + this.id + "'.");
        }
        return revision;
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Title: %-30s | Client: %-20s | Deadline: %s | Revisions: %d",
                (id != null ? id : "N/A"),
                (title != null ? title.substring(0, Math.min(title.length(), 30)) : "N/A"),
                (client != null ? client.substring(0, Math.min(client.length(), 20)) : "N/A"),
                getDeadlineAsString(),
                revisionsMap.size()
        );
>>>>>>> 78b702b (add ProjectManager class)
    }

    // Abstract methods
    public abstract void displayProjectDetails();
<<<<<<< HEAD

    public abstract double calculateEstimateBudget();

    public abstract LocalDateTime calculateEstimateProjectComplete();
}
=======
    public abstract double calculateEstimateBudget();
    public abstract LocalDate calculateEstimateProjectComplete();
}
>>>>>>> 78b702b (add ProjectManager class)
