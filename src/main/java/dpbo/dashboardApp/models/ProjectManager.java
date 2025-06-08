package dpbo.dashboardApp.models;
import java.util.ArrayList;
import java.util.List;

import dpbo.dashboardApp.exceptions.ProjectNotFoundException;

class ProjectManager{
        private List<Project> projectList;

        public ProjectManager() {
            this.projectList = new ArrayList<Project>();
        }
        
        // Method: Menambahkan project
        public boolean addProject(Project project) {
        	return projectList.add(project);
        }
        
        // Method: Menemukan project berdasarkan ID project
        public Project findProjectById(String projectId) throws ProjectNotFoundException{
        	for(Project project : projectList) {
        		if(project.getId().equalsIgnoreCase(projectId)) {
        			return project;
        		}
        	}
        	throw new ProjectNotFoundException("Project dengan ID " + projectId + " tidak ditemukan.");
        }
        
        // Method: filter project berdasarkan client
        public List<Project> findProjectsByClient(String clientName) {
            List<Project> projects = new ArrayList<>();
            for (Project p : projectList) {
                if (p.getClient().equalsIgnoreCase(clientName)) {
                	projects.add(p);
                }
            }
            return projects;
        }
        
        // Method
        public boolean removeProject(String projectId) throws ProjectNotFoundException {
        	Project project = findProjectById(projectId);
        	return projectList.remove(project);        	
        }
        
        public List<Project> getAllProjects() throws ProjectNotFoundException{
        	if(projectList.isEmpty()) {
        		throw new ProjectNotFoundException("Daftar project masih kosong");
        	}
        	return projectList;
        }
        
        
        
        public void displayAllProjectDetails() throws ProjectNotFoundException{
        	if(projectList.isEmpty()) {
        		throw new ProjectNotFoundException("Daftar project masih kosong");
        	}
        	
        	 for (Project project : projectList) {
                 project.displayProjectDetails();
             }
        }
        
        
        
        
        
        
        
}