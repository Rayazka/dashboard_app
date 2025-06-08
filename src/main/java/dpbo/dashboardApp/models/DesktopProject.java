package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

public class DesktopProject extends Project {

    private String operationSystem;

    public DesktopProject(String id, String title, String description, String client, LocalDateTime deadline, String operationSystem) {
        super(id, title, description, client, deadline);
        this.operationSystem = operationSystem;
    }

    public String getOS() {
        return operationSystem;
    }

    public void setOS(String os) {
        this.operationSystem = os;
    }

    @Override
    public String toString() {
        try {
            return super.toString() + ", OS: " + operationSystem;
        } catch (Exception e) {
            return "Error generating toString: " + e.getMessage();
        }
    }

    @Override
    public void displayProjectDetails() {
        try {
            System.out.println("Desktop Project Detail:\n" + this.toString());
        } catch (Exception e) {
            System.out.println("Failed to display project details: " + e.getMessage());
        }
    }

    @Override
    public double calculateEstimateBudget() {
<<<<<<< HEAD
        try {
            return 1200.0 + getRevision().size() * 100;
        } catch (Exception e) {
            System.out.println("Failed to calculate budget: " + e.getMessage());
            return 0.0;
        }
    }

    @Override
    public LocalDateTime calculateEstimateProjectComplete() {
        try {
            return getDeadline().minusDays(1);
        } catch (Exception e) {
            System.out.println("Failed to calculate completion date: " + e.getMessage());
            return LocalDateTime.now();
        }
    }
}
=======
        double budget = 6000000;
        if ("macOS".equalsIgnoreCase(this.operatingSystem)) budget += 1500000;
        int revisionImpact = getRevisions().size() * 120000;
        budget += revisionImpact;
        return budget;
    }

    @Override
    public LocalDate calculateEstimateProjectComplete() {
        LocalDate estimatedComplete = getDeadline();
        int revisionCount = getRevisions().size();
        long extraDays = (long)(revisionCount / 3.0) * 7; // 1 week for every 3 revisions
        return estimatedComplete.plusDays(extraDays);
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | Type: Desktop | OS: %s",
                             (operatingSystem != null ? operatingSystem : "N/A"));
    }
}
>>>>>>> 78b702b (add ProjectManager class)
