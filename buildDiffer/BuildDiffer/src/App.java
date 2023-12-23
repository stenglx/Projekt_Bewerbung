import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import at.aau.diff.common.Change;
import at.aau.diff.common.Differ;
import at.aau.diff.maven.MavenBuildChange;
import at.aau.diff.maven.MavenBuildFileDiffer;

// CMD, Shift, P => Create new Java project without maven; settings.json (add smthg for jar)
public class App {

    public static List<Change> getChangesOfPomFile(File current_pom, File previous_pom){
        System.out.println(current_pom.getName());

        Differ buildDiffer = new MavenBuildFileDiffer();
		
		List<Change> actualChanges = null;
        try {
            actualChanges = buildDiffer.extractChanges(current_pom, previous_pom);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return actualChanges;
        }
    
    // TODO change such that it's done for all changes; not just for One pom file's changes
    public static void writeTrainingDataToCSV(List<Change> changes, int build_result){
        File training_data_file = new File("training_data.csv");
    
        FileWriter file_writer;
        try {
            if (training_data_file.createNewFile()){
                System.out.println("File created :) ");
            } else {
                System.out.println("File already exists!");
            }
            file_writer = new FileWriter("training_data.csv");
            file_writer.write(changes.size()+","+build_result);
            file_writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // training_data.csv file now contains amount of changes and build results seperated through ,
        System.out.println("Wrote results into file called: "+ training_data_file.getName());
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
        /*File old_pom = new File("/Users/Rina/Desktop/StudiAssistent/TestBuildDiffer/src/activemq/pom_alt.xml");
        System.out.println(old_pom.getName());*/
        String PATH_PREFIX = "/Users/Rina/Desktop/StudiAssistent/Projekt_Bewerbung/dataprep/pom_files/";
        //String PATH_PREFIX=new File("").getAbsolutePath()+"/";

        // warum macht das Fehler?
        // TODO: Invoke when receiving error
        //new File("poms/tmp").mkdirs();

        // TODO loop through all poms (0 to 100)
        File current_pom = new File(PATH_PREFIX+"/0/pom_current.xml");
        File previous_pom = new File(PATH_PREFIX+"/0/pom_previous.xml");
        int build_result = 0;
        
        List<Change> actualChanges = getChangesOfPomFile(current_pom, previous_pom);
        System.out.println(actualChanges.size());
         
        // TODO: think of what to do with changes as part of dataprep
        for (Change change : actualChanges) {
			System.out.println(((MavenBuildChange)change).getName());
            // count similar changes to get metrics for model 
		}

        writeTrainingDataToCSV(actualChanges, build_result);
         
    }
}
