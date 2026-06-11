
import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abdullahatif
 */
public class University {
    //Date formatter
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    //Initialize University Class
    private String universityName;
    private String universityWebsite;
    private String universityID;
    private String universityPassword;
    private ArrayList<Application> applications = new ArrayList<Application>();
    
    //Create University Object
    public University(String universityName, String universityWebsite, String universityID, String universityPassword) {
        this.universityName = universityName;
        this.universityWebsite = universityWebsite;
        this.universityID = universityID;
        this.universityPassword = universityPassword;
    }
    
    //add apllication to object's list
    public void addApplication(Application application) {
        this.applications.add(application);
    }
    
    //Generate application and save it on text file
    public void genApplication(String programName, boolean suppAppRequired, LocalDate suppAppDate, boolean interviewRequired, LocalDate interviewDate, User user) {
        //create application object
        Application newApp = new Application(programName, suppAppRequired, suppAppDate, interviewRequired, interviewDate);
        //add it to list
        applications.add(newApp);
        int locationInsert = 0;
        try {
            //read through array to build array list
            ArrayList<String> output = new ArrayList<String>();
            BufferedReader brt = new BufferedReader(new FileReader("userApplications.txt"));
            String lineRead = brt.readLine();
            while (lineRead != null) {
                output.add(lineRead);
                lineRead = brt.readLine();
            }
            brt.close();
            //Initialize File Reader
            BufferedReader br = new BufferedReader(new FileReader("userApplications.txt"));
            //Initialize line String
            int lines = 4;
            //Skip first 4 lines
            
            for (int i = 0; i < 3; i++) br.readLine();
            
            String line = br.readLine();
            //Loop through file lines
            //Find user and university
            boolean userFound = false;
            boolean uniFound = false;
            int lastUni = 0;
            //loop through
            loop:
            while (line != null) {
                //get num of tabs 0 -> user, 1 -> university, 2 -> program
                int tabsCount = (int) line.chars().filter(ch -> ch == '\t').count();
                System.out.println(tabsCount);
                switch (tabsCount) {
                    case 0 -> {
                        //save user location
                        if (!userFound && (user.getUserID().equalsIgnoreCase(line.strip()))) {
                            userFound = true;
                        //new user starting so break loop
                        } else if (userFound) {
                            break loop;
                        }
                    }
                    case 1 -> {
                        //the user is found and the university is found
                        if (userFound && (this.getUniversityName().equalsIgnoreCase(line.strip().split(",")[0]))) {
                            uniFound = true;
                        //university hasn't been found but user has
                        } else if (userFound && !uniFound) {
                            lastUni = lines;
                        //both have been found
                        } else if (userFound && uniFound) {
                            break loop;
                        }
                    }
                    case 2 -> {
                        if (uniFound) {
                            locationInsert = lines;
                        }
                    }
                }
                //read new line and add to counter
                line = br.readLine();
                ++lines;
	    }
            //close when done
            br.close();
            
            //determine insertion location
            if (!userFound) {
                output.add(user.getUserID());
                output.add(String.format("\t%s,%s,%s,%s", this.universityName, this.universityWebsite, this.universityID, this.universityPassword));
                output.add(newApp.fileFormatOutput());
            } else if (userFound && !uniFound) {
                output.add(lastUni, String.format("\t%s,%s,%s,%s", this.universityName, this.universityWebsite, this.universityID, this.universityPassword));
                output.add(lastUni+1, newApp.fileFormatOutput());
            } else {
                output.add(locationInsert, newApp.fileFormatOutput());
            }
            //rewrite file
            BufferedWriter bw = new BufferedWriter(new FileWriter("userApplications.txt"));
            for (String lineOutput : output) {
                bw.write(lineOutput);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(e+" 1");
            return;
        }
    }
    //getter methods
    public String getUniversityName() {
        return universityName;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }
    //overide to string
    @Override
    public String toString() {
        return String.format("\t%s,%s,%s,%s", universityName, universityWebsite, universityID, universityPassword);
    }
    
    //parse through valid university names
    private static ArrayList<String> validUniversities = new ArrayList<>();
    //quick sort method
    private static void quickSort(ArrayList<String> list, int low, int high) {
        if (low < high) {
            //determine pivot point
            int pivotIndex = partition(list, low, high);
            //recursive loop until array is sorted
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }
    //seperate lists to determine partitions
    private static int partition(ArrayList<String> list, int low, int high) {
        String pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            String jName = list.get(j).split(",")[0].trim();
            String pivotName = pivot.split(",")[0].trim();
            if (jName.compareToIgnoreCase(pivotName) <= 0) {
                i++;
                String temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        String temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        return i + 1;
    }
    //load all uni names to list
    public static void loadUniversities() {
        try {
            //initialize reader
            BufferedReader br = new BufferedReader(new FileReader("globalList.txt"));
            String line;
            //loop through adding all lines
            while ((line = br.readLine()) != null) {
                line = line.trim().toLowerCase().replace("\"", "");
                validUniversities.add(line);
            }
            br.close();
            quickSort(validUniversities, 0, validUniversities.size() - 1);
        } catch (Exception e) {
            System.out.println("globalList.txt not found");
        }
    }
    
    //use a recursive loop to determine valid university
    public static boolean isValidUniversity(String input) {
        return isValidUniversityHelper(input.trim().toLowerCase().replace("\"", ""), 0, validUniversities.size() - 1);
    }

    private static boolean isValidUniversityHelper(String input, int left, int right) {
        //binary search
        if (left > right) {
            return false;
        }
        int mid = left + (right - left) / 2;
        String midName = validUniversities.get(mid).split(",")[0].trim();
        int comparison = midName.compareToIgnoreCase(input.trim());
        if (comparison == 0) return true;
        else if (comparison < 0) return isValidUniversityHelper(input, mid + 1, right);
        else return isValidUniversityHelper(input, left, mid - 1);
    }
    //get website version of valid university
    public static String getUniversityWebsite(String input) {
        String cleanInput = input.trim().toLowerCase().replace("\"", "");
        int left = 0;
        int right = validUniversities.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String[] parts = validUniversities.get(mid).split(",");
            String midName = parts[0].trim();
            int comparison = midName.compareToIgnoreCase(cleanInput);
            if (comparison == 0) {
                return parts[parts.length - 1].trim();
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }


    //Application class (dependant on a uni object)
    public class Application {
        //initialize
        private String programName;
        private boolean suppAppRequired;
        private LocalDate suppAppDate;
        private boolean interviewRequired;
        private LocalDate interviewDate;
        //constructor
        public Application(String programName, boolean suppAppRequired, LocalDate suppAppDate, boolean interviewRequired, LocalDate interviewDate) {
            this.programName = programName;
            this.suppAppRequired = suppAppRequired;
            this.suppAppDate = suppAppDate;
            this.interviewRequired = interviewRequired;
            this.interviewDate = interviewDate;
        }
        //file writer formatting
        private String fileFormatOutput() {
            String output = "\t\t" +programName;
            if (suppAppRequired) {
                output += ",true," + suppAppDate.format(formatter);
            } else {
                output += ",false,"; 
            }
            
            if (interviewRequired) {
                output += ",true," + interviewDate.format(formatter);
            } else {
                output += ",false,"; 
            }
            return output;
        }
        //getter method
        public String getProgramName() {
            return programName;
        }
        //overide for sting output
        @Override
        public String toString() {
            String output =  "\n  University: " + universityName +"\n  Website: " + universityWebsite +"\n  Program: " + programName;
            if (suppAppRequired) {
                output += "\n\tSupplementary App Due: " + suppAppDate.format(formatter);
            }
            if (interviewRequired) {
                output += "\n\tInterview Date: " + interviewDate.format(formatter);
            }
            return output;
        }
        
        
   
        
    }
}
