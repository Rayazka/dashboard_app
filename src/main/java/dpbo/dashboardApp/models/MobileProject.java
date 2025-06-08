package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

public class MobileProject extends Project {

    private String platform;

    public MobileProject(String id, String title, String description, String client, LocalDateTime deadline, String platform) {
        super(id, title, description, client, deadline);
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        try {
            return super.toString() + ", Platform: " + platform;
        } catch (Exception e) {
            return "Error generating toString: " + e.getMessage();
        }
    }

    @Override
    public void displayProjectDetails() {
        try {
            System.out.println("Mobile Project Detail:\n" + this.toString());
        } catch (Exception e) {
            System.out.println("Failed to display project details: " + e.getMessage());
        }
    }

    @Override
    public double calculateEstimateBudget() {
<<<<<<< HEAD
        try {
            return 1500.0 + getRevision().size() * 200;
        } catch (Exception e) {
            System.out.println("Failed to calculate budget: " + e.getMessage());
            return 0.0;
        }
    }

    @Override
    public LocalDateTime calculateEstimateProjectComplete() {
        try {
            return getDeadline().minusDays(2);
        } catch (Exception e) {
            System.out.println("Failed to calculate completion date: " + e.getMessage());
            return LocalDateTime.now();
        }
    }
}
=======
        double budget = 8_000_000;
        if ("iOS".equalsIgnoreCase(this.platform)) budget += 2_000_000;
        else if ("Android".equalsIgnoreCase(this.platform)) budget += 500_000;
        int revisionImpact = getRevisions().size() * 150_000;
        budget += revisionImpact;
        return budget;
    }

    @Override
    public LocalDate calculateEstimateProjectComplete() {
        LocalDate estimatedComplete = getDeadline();
        int revisionCount = getRevisions().size();
        long extraDays = (long)revisionCount * 5;
        return estimatedComplete.plusDays(extraDays);
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format(" | Type: Mobile | Platform: %s",
                             (platform != null ? platform : "N/A"));
    }
}
>>>>>>> 78b702b (add ProjectManager class)
