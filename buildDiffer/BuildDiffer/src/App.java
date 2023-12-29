import java.util.*;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import at.aau.diff.common.Change;
import at.aau.diff.common.Differ;
import at.aau.diff.maven.MavenBuildChange;
import at.aau.diff.maven.MavenBuildFileDiffer;

// CMD, Shift, P => Create new Java project without maven; settings.json (add smthg for jar)
public class App {

    static Map<String, Integer> changes_5_categories = new HashMap<>();
    static List<String> features = new ArrayList<String>();

    public static List<Change> getChangesOfPomFile(File current_pom, File previous_pom){
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
    
    public static void writeMetricsToCSV(List<String> changes_and_build_result){
        // every key is a column; every rowm is a new set of changes of another pom file
        String header = "";
        for(String key: changes_5_categories.keySet()){
            header += key+",";
        }
        header+="BuildResult";
        File training_data_file = new File("training_data.csv");
    
        FileWriter file_writer;
        try {
            if (training_data_file.createNewFile()){
                System.out.println("File created :) ");
            } else {
                System.out.println("File already exists!");
            }
            file_writer = new FileWriter("training_data.csv");
            file_writer.write(header+"\n"); // TODO remove header in python file then 
            for(int i = 0; i < changes_and_build_result.size(); i++){
                // 5,1,0,0,0    , errored
                file_writer.write(changes_and_build_result.get(i)+"\n");
            }
            file_writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // training_data.csv file now contains amount of changes and build results seperated through ,
        System.out.println("Wrote results into file called: "+ training_data_file.getName());
    }

    private static void resetDict(){
        // init count with 0 for each build file 
        changes_5_categories.put("GeneralChange", 0);
        changes_5_categories.put("DependencyChange", 0);
        changes_5_categories.put("RepositoryChange", 0);
        changes_5_categories.put("BuildChange", 0);
        changes_5_categories.put("TeamChange", 0);
    }

    private static void incremetDictValue(String category) {
        Integer current_value = changes_5_categories.get(category);
        current_value+=1;
        changes_5_categories.put(category, current_value);
    }

    private static void calculateCategoryValues(List<Change> changes){   
        for (Change change : changes) {
            String name = ((MavenBuildChange)change).getName();
            System.out.println("Change of type: " + name);

            if (name.matches("(GENERAL_|LICENSE_|MODULE_|PARENT_|PROFILE_|PROJECT_|SCM_).*")){
                //System.out.println("FOUND GENERAL CHANGE");
                incremetDictValue("GeneralChange");
                //System.out.println(changes_5_categories);
            }
            else if (name.matches("(PLUGIN_DEPENDENCY_|MANAGED_|DEPENDENCY_).*")){
                //System.out.println("FOUND DEPENDENCY CHANGE");
                incremetDictValue("DependencyChange");
                //System.out.println(changes_5_categories);
            }
            else if (name.matches("(PLUGIN_REPOSITORY_|REPOSITORY_|DIST_).*")){
                //System.out.println("FOUND REPOSITORY CHANGE");
                incremetDictValue("RepositoryChange");
                //System.out.println(changes_5_categories);
            }
            // before we catch specifiy Plugin_xxx, now we catch the rest without the need to specify :D 
            else if (name.matches("(RESOURCE_|PLUGIN_|TEST_|SOURCE_).*")){
                //System.out.println("FOUND BUILD CHANGE");
                incremetDictValue("BuildChange");
                //System.out.println(changes_5_categories);
            }
            else if (name.matches("(CONTRIBUTOR_|DEVELOPER_).*")){
                System.out.println("FOUND TEAM CHANGE");
                incremetDictValue("TeamChange");
                System.out.println(changes_5_categories);
            }
            else{
                System.out.println("ERROR; Category not matched :(");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String PATH_PREFIX_DATA = "/Users/Rina/Desktop/StudiAssistent/Projekt_Bewerbung/data/pom_files/";

        // Invoke when receiving error; same in original build differ repo
        //new File("poms/tmp").mkdirs();     
    
        for(int i = 0; i < 100; i++){
            
            File current_pom = new File(PATH_PREFIX_DATA+"/"+i+"/pom_current.xml");
            if (!current_pom.exists()){
                continue;  // needed as not all pom files could be loaded 
            }
            File previous_pom = new File(PATH_PREFIX_DATA+"/"+i+"/pom_previous.xml");
            String build_result = Files.toString(new File(PATH_PREFIX_DATA+"/"+i+"/build_result.csv"), Charsets.UTF_8);
            
            List<Change> actualChanges = getChangesOfPomFile(current_pom, previous_pom);

            resetDict();
            calculateCategoryValues(actualChanges);

            //String row = String.join(",", changes_5_categories.values()); didn't work on java21
            String row_values = changes_5_categories.values().toString(); 
            String row = (row_values.substring(1, row_values.length()-1)).replaceAll(" ", "");
            System.out.println(row);
            row = row + ","+build_result;
            features.add(row);

            // TODO remove to do for all pom files 
            //if (i > 2){
            //    break;
            //}
        }
    
        writeMetricsToCSV(features);
         
    }
}
