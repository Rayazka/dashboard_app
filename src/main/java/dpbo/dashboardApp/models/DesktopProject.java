package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

public class DesktopProject extends Project {

    private String operationSystem;

    public DesktopProject(int id, String operationSystem) throws Exception {
        super(id);
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
