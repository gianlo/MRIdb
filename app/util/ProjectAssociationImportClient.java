package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;
import models.Project;
import models.ProjectAssociation;
import models.Study;


public class ProjectAssociationImportClient {

	public static void importAssociationsFromCSV(String filename) throws IOException, FileNotFoundException{
		CSVReader reader;
		
		reader = new CSVReader(new FileReader(filename));
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
		 if (args.length > 0){
			 System.out.println("Importing " + args[0]);
			 try {
				importAssociationsFromCSV(args[0]);
			} catch (FileNotFoundException e) {
				System.err.println("File not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Error whilst reading file");
				e.printStackTrace();
			}
		 }
	 }
}
