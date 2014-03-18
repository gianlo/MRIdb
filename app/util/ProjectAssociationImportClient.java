package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;
import models.Project;
import models.ProjectAssociation;
import models.Study;


public class ProjectAssociationImportClient {
	private String filename;
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public ProjectAssociationImportClient(String filename) {
		super();
		setFilename(filename);
	}
	public void run() throws IOException{
		importAssociationsFromCSV(filename);
	}
	public static void importAssociationsFromCSV(String filename) throws IOException{
		CSVReader reader;
		try {
			 reader = new CSVReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//read headers
		reader.readNext();
		//parse file
		
		String[] line = null;
		String participationid;
		Long project_id;
		Long study_pk;
		Study current_study;
		Project project;
		while ((line = reader.readNext()) != null) {
//			participationid,project_id,study_pk
//			parse columns
			participationid = line[0];
			project_id = Long.parseLong(line[1], 10);
			study_pk = Long.parseLong(line[2], 10);
//			retrieve study and project from db
			project =  Project.findById(project_id);
			current_study = Study.findById(study_pk);
			if (project != null & current_study != null){
				associate(current_study, project, participationid);
			}
		}
		reader.close();
	}

	static void associate(Study study, Project project, String participationID) {
		ProjectAssociation association = ProjectAssociation.find("byStudy", study).first();
		if (project == null) {
			if (association != null) {
				association.delete();
			}
		} else {
			if (association != null) {
				if (association.project.id == project.id) {
					if (participationID.equals(association.participationID)) {
						return;
					}
				} else {
					association.project = project;
				}
			} else {
				association = new ProjectAssociation(project, study);
			}
			association.participationID = participationID;
			association.save();
		}
	}

	 public static void main (String[] args){
		 if (args.length > 1){
			 System.out.println("Importing " + args[1]);
//			 ProjectAssociationImportClient importClient;
//			 importClient = new ProjectAssociationImportClient(args[1]);
			 try {
				importAssociationsFromCSV(args[1]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }
}
