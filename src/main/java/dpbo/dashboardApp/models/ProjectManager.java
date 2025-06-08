package dpbo.dashboardApp.models;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dpbo.dashboardApp.db.ProjectDbController;
import dpbo.dashboardApp.exceptions.ProjectNotFoundException;

public class ProjectManager {

	private ProjectDbController projectDbController;
        public ProjectManager() throws Exception {
			projectDbController = new ProjectDbController();
        }
        
        public void newProject(String title, String description, LocalDateTime deadline, int client) throws Exception {
        	projectDbController.createNewProject(title, description, deadline, client);
        }

		public void removeProject(int projectId) throws ProjectNotFoundException, Exception {
			// Menghapus project berdasarkan ID
			projectDbController.removeProject(projectId);
		}
        
        // Method: Menemukan project berdasarkan ID project
        public Project findProjectById(int projectId) throws ProjectNotFoundException, Exception{

			String type = projectDbController.getType(projectId);

			if (type == null) {
				throw new ProjectNotFoundException("Project dengan ID " + projectId + " tidak ditemukan.");
			}

			if (type.equals("web")) {
				return new WebProject(projectId, "http://example.com");
			} else if (type.equals("mobile")) {
				return new MobileProject(projectId, "android");
			} else if (type.equals("desktop")) {
				return new DesktopProject(projectId, "windows");
			} else {
				throw new ProjectNotFoundException("Tipe project tidak dikenali untuk ID " + projectId);
			}
			
        }
        
        // Method: filter project berdasarkan client
        public List<Project> findProjectsByClient(int clientId) throws Exception {
			ArrayList<Integer> projects = projectDbController.getProjectIdOwnedByUser(clientId);
			List<Project> projectList = new ArrayList<Project>();

			for (int projectId : projects) {
				try {
					Project project = findProjectById(projectId);
					projectList.add(project);
				} catch (ProjectNotFoundException e) {
					// Project tidak ditemukan, lanjutkan ke project berikutnya
				}
			}
			return projectList;
		}

		public void displayAllProjectDetails(int userId) throws Exception {
			ArrayList<Integer> projectIds = projectDbController.getProjectIdOwnedByUser(userId);

			for (int projectId : projectIds) {
				Project project = findProjectById(projectId);
				System.out.println("Project ID: " + project.getId());
				System.out.println("Title: " + project.getTitle());
				System.out.println("Description: " + project.getDescription());
				//System.out.println("Owner ID: " + project.getOwnerId());
				System.out.println("Deadline: " + project.getDeadline());
				System.out.println("-----------------------------");
			}
		}
        
}
