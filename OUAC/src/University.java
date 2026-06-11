
import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abdullahatif
 */
public class University {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String universityName;
    private String universityWebsite;
    private String universityID;
    private String universityPassword;
    private ArrayList<Application> applications = new ArrayList<Application>();

    public University(String universityName, String universityWebsite, String universityID, String universityPassword) {
        this.universityName = universityName;
        this.universityWebsite = universityWebsite;
        this.universityID = universityID;
        this.universityPassword = universityPassword;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }
    
    public void genApplication(String programName, boolean suppAppRequired, LocalDate suppAppDate, boolean interviewRequired, LocalDate interviewDate, User user) {
        Application newApp = new Application(programName, suppAppRequired, suppAppDate, interviewRequired, interviewDate);
        applications.add(newApp);
        int WebsiteInsert = 0;
        try {
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
            boolean userFound = false;
            boolean uniFound = false;
            int lastUni = 0;
            loop:
            while (line != null) {
                int tabsCount = (int) line.chars().filter(ch -> ch == '\t').count();
                System.out.println(tabsCount);
                switch (tabsCount) {
                    case 0 -> {
                        if (!userFound && (user.getUserID().equalsIgnoreCase(line.strip()))) {
                            userFound = true;
                        } else if (userFound) {
                            break loop;
                        }
                    }
                    case 1 -> {
                        System.out.println(line.strip().split(",")[0]);
                        if (userFound && (this.getUniversityName().equalsIgnoreCase(line.strip().split(",")[0]))) {
                            uniFound = true;
                        } else if (userFound && !uniFound) {
                            lastUni = lines;
                        } else if (userFound && uniFound) {
                            break loop;
                        }
                    }
                    case 2 -> {
                        if (uniFound) {
                            WebsiteInsert = lines;
                        }
                    }
                }
                line = br.readLine();
                ++lines;
	    }
            br.close();
            if (!userFound) {
                output.add(user.getUserID());
                output.add(String.format("\t%s,%s,%s,%s", this.universityName, this.universityWebsite, this.universityID, this.universityPassword));
                output.add(newApp.fileFormatOutput());
            } else if (userFound && !uniFound) {
                output.add(lastUni, String.format("\t%s,%s,%s,%s", this.universityName, this.universityWebsite, this.universityID, this.universityPassword));
                output.add(lastUni+1, newApp.fileFormatOutput());
            } else {
                output.add(WebsiteInsert, newApp.fileFormatOutput());
            }
            
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

    public String getUniversityName() {
        return universityName;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    @Override
    public String toString() {
        return String.format("\t%s,%s,%s,%s", universityName, universityWebsite, universityID, universityPassword);
    }
    
    private static ArrayList<String> validUniversities = new ArrayList<>();

    public static void loadUniversities()  {
        try {
        BufferedReader br = new BufferedReader(new FileReader("globalList.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String name = line.split(",")[0].trim().toLowerCase();
            validUniversities.add(name);
        }
        br.close();
        } catch (Exception e) {
            System.out.println("globalList.txt not found");
        }
    }
    
    public static boolean isValidUniversity(String input) {
        int left = 0;
        int right = validUniversities.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparison = validUniversities.get(mid).compareToIgnoreCase(input.trim());

            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
    
    public static String getUniversityWebsite(String input) {
        int left = 0;
        int right = validUniversities.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparison = validUniversities.get(mid).split(",")[0].compareToIgnoreCase(input.trim());

            if (comparison == 0) {
                return validUniversities.get(mid).split(",")[1];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null; // not found
}
    


    public class Application {
        private String programName;
        private boolean suppAppRequired;
        private LocalDate suppAppDate;
        private boolean interviewRequired;
        private LocalDate interviewDate;

        public Application(String programName, boolean suppAppRequired, LocalDate suppAppDate, boolean interviewRequired, LocalDate interviewDate) {
            this.programName = programName;
            this.suppAppRequired = suppAppRequired;
            this.suppAppDate = suppAppDate;
            this.interviewRequired = interviewRequired;
            this.interviewDate = interviewDate;
        }
        
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

        public String getProgramName() {
            return programName;
        }
        
        
   
        
    }
}
