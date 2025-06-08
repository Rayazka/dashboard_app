package dpbo.dashboardApp.models;

import java.time.LocalDateTime;

public class WebProject extends Project {

    private String domainName;

    public WebProject(int id,String domainName) throws Exception {
        super(id);
        this.domainName = domainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domain) {
        this.domainName = domain;
    }

    @Override
    public String toString() {
        try {
            return super.toString() + ", Domain: " + domainName;
        } catch (Exception e) {
            return "Error generating toString: " + e.getMessage();
        }
    }

    @Override
    public void displayProjectDetails() {
        try {
            System.out.println("Web Project Detail:\n" + this.toString());
        } catch (Exception e) {
            System.out.println("Failed to display project details: " + e.getMessage());
        }
    }

    @Override
    public double calculateEstimateBudget() {
        try {
            return 1000.0 + getRevision().size() * 150;
        } catch (Exception e) {
            System.out.println("Failed to calculate budget: " + e.getMessage());
            return 0.0;
        }
    }

    @Override
    public LocalDateTime calculateEstimateProjectComplete() {
        try {
            return getDeadline().minusDays(3);
        } catch (Exception e) {
            System.out.println("Failed to calculate completion date: " + e.getMessage());
            return LocalDateTime.now();
        }
    }
}
