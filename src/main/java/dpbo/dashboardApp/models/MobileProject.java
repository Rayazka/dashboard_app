package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

public class MobileProject extends Project {

    private String platform;

    public MobileProject(int id, String platform) throws Exception {
        super(id);
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
