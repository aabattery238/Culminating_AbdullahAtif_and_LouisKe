
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abdullahatif
 */



public class User {
    //initialize variables
    private String username; 
    private String password;
    private String userID;
    private ArrayList<University> totalUniversitiesApplied = new ArrayList<University>();
    //constructor for sign in
    public User(String userID, String username, String password) {
        this.username = username;
        this.password = password;
        this.userID = userID;
    }
    //constructor for sign up
    private User(String username, String password) {
        this.username = username;
        this.password = password;
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyDDHssSS");
        this.userID = time.format(formatter);
    }
    //add user method
    public static void addUser(User newUser, javax.swing.JFrame gui) {
        //get list of users
        ArrayList<User> users = getUsers();
        users.add(newUser);
        //sort list based on ID
        users.sort((u1, u2) -> u1.getUserID().compareTo(u2.getUserID()));
        //write users to file
        try {
            FileWriter fw = new FileWriter("users.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("userID,username,password");
            for (User user : users) {
                bw.newLine();
                System.out.println(user.getUserID() +"," +user.getUsername() +"," +user.getPassword());
                bw.write(user.getUserID() +"," +user.getUsername() +"," +user.getPassword());
            }
            bw.close();
            
        } catch (Exception e) {
            System.out.println(e +" 2");
            JOptionPane.showMessageDialog(gui, "users.txt Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    //get all users
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            //Initialize File Reader
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            //Initialize line String
            String line = br.readLine(); 
            line = br.readLine();//Skip first line
            //Loop through file lines
            while (line != null) {
                String[] userProp = line.split(",", 3);
                users.add(new User(userProp[0], userProp[1], userProp[2]));
                line = br.readLine();
	    }
            br.close();
            //return list of users
            return users;
        } catch (Exception e) {
            System.out.println(e+" 1");
            return null;
        }
    }
    
    //check if user exists for sign in
    public static boolean checkUserExists(String userInput, javax.swing.JFrame gui) {
        try {
            //Initialize File Reader
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            //Initialize line String
            String line = br.readLine();
            line = br.readLine(); //Skip first line
            //Loop through file lines
            while (line != null) {
                if (line.split(",")[1].equals(userInput)) {
                    return true;
                }
                line = br.readLine();
	    }
            br.close();
            return false;
        } catch (Exception e) {
            System.out.println(e+" 3");
            JOptionPane.showMessageDialog(gui, "users.txt Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    //find user for sign in
    private static String findUser(String userInput, javax.swing.JFrame gui) {
        try {
            //Initialize File Reader
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            //Initialize line String
            String line = br.readLine(); // skip header
            line = br.readLine();
            while (line != null) {
                //loop through and get values if found
                if (line.split(",")[1].equals(userInput)) {
                    br.close();
                    return line;
                }
                line = br.readLine();
            }
            br.close();
            return null;
        } catch (Exception e) {
            System.out.println(e+" 4");
            JOptionPane.showMessageDialog(gui, "users.txt Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    //login method to ensure encapsulation and privacy
    public static User login(String usernameInput, String passwordInput, javax.swing.JFrame gui) {
        String found = findUser(usernameInput, gui);
        if (found == null) {
            JOptionPane.showMessageDialog(gui, "User not found", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String[] userLine = found.split(",", 3);
        
        if (userLine[2].equals(passwordInput)) {
           return new User(userLine[0], userLine[1], userLine[2]); 
        } else {
            JOptionPane.showMessageDialog(gui, "Incorrect Password", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }  
    }
    //sign up method to create and generate user, including writing into file
    public static User signUp(String usernameInput, String passwordInput, javax.swing.JFrame gui) {
        if (checkUserExists(usernameInput, gui)) {
            JOptionPane.showMessageDialog(gui, "User with this name already exists", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            User newUser = new User(usernameInput, passwordInput);
            User.addUser(newUser, gui);
            return newUser;
        }
        
    }
    //getter methods
    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    private String getPassword() {
        return password;
    }  
    //search user's university
    private University binarySearchUniversity(String searchUniversity) {
        //initialize leftmost and right most indexes
        int left = 0;
        int right = totalUniversitiesApplied.size() -1;
        //repeat loop until one is selected
        
        while (left <= right) {
            //get middle value
            int mid = left + (right - left) / 2;
            System.out.println(mid);
            //if middle value is the item return
            if (totalUniversitiesApplied.get(mid).getUniversityName().equals(searchUniversity)) {
                 return totalUniversitiesApplied.get(mid);
            }
            //compare title if less than 0 choose right half
            if (totalUniversitiesApplied.get(mid).getUniversityName().compareTo(searchUniversity) < 0) {
                left = mid + 1;
            } else { //choose left half
                right = mid - 1;
            }
            
        }
         //display error if not found
        return null;
            
    }

    // returns university if found, null if not found
    public University getsearchUniversity(String searchUniversity) {
        University universityFound = binarySearchUniversity(searchUniversity);
        if (universityFound != null) {
            return universityFound;
        } else {
            return null;
        }
    }
    //add to list
    public void addTotalUniversitiesApplied(University newUni) {
        this.totalUniversitiesApplied.add(newUni);
    }
    //generate application for method
    public void generateApplication(University selectedUniversity, String programName, boolean suppAppRequired, LocalDate suppAppDate, boolean interviewRequired, LocalDate interviewDate) {
        University universityFound = binarySearchUniversity(selectedUniversity.getUniversityName());
        if (universityFound != null) {
           universityFound.genApplication(programName, suppAppRequired, suppAppDate, interviewRequired, interviewDate, this);
        }
    }
    
    
    //get all user applications
    public ArrayList<University.Application> getApplications() {
        ArrayList<University.Application> userApplications = new ArrayList<University.Application>();
        for (University uniApplied : totalUniversitiesApplied) {
            for(University.Application uniApplications : uniApplied.getApplications()) {
                userApplications.add(uniApplications);
            }
        }
        return userApplications;
    }
    //get total universities
    public ArrayList<University> getTotalUniversitiesApplied() {
        return totalUniversitiesApplied;
    }
    //read user universities to ensure exisiting user's lists are already saved and resored
    public void readUserUniversities() {
        try {
            ArrayList<University> userUniOutput = new ArrayList<University>();
            BufferedReader br = new BufferedReader(new FileReader("userApplications.txt"));

            for (int i = 0; i < 3; i++) br.readLine();

            String line = br.readLine();
            boolean userFound = false;
            boolean uniOpen = false;
            University currentUniLine = null;
            loop:
            while (line != null) {
                int tabsCount = (int) line.chars().filter(ch -> ch == '\t').count();
                switch (tabsCount) {
                    case 0 -> {
                        if (!userFound && (userID.equalsIgnoreCase(line.strip()))) {
                            userFound = true;
                        } else if (userFound) {
                            break loop;
                        }
                    }
                    case 1 -> {
                        try {
                            if (userFound) {
                                if (uniOpen && currentUniLine != null) {
                                    userUniOutput.add(currentUniLine);
                                }
                                String[] formattedUniLine = line.strip().split(",");
                                currentUniLine = new University(
                                formattedUniLine[0], formattedUniLine[1],
                                formattedUniLine[2], formattedUniLine[3]
                            );
                            uniOpen = true;
                            }   
                        } catch (Exception e) {
                            System.out.println(e + " 4");
                        }
                    }
                    case 2 -> {
                        if (uniOpen) {
                            String[] formattedAppLine = line.strip().split(",");
                            String programName = formattedAppLine[0];
                            boolean suppAppRequired = Boolean.parseBoolean(formattedAppLine[1]);
                            LocalDate suppAppDate = null;
                            LocalDate interviewDate = null;
                            try {
                                if (suppAppRequired) {
                                    String[] date = formattedAppLine[2].split("/");
                                    int day = Integer.parseInt(date[0]);
                                    int month = Integer.parseInt(date[1]);
                                    int year = Integer.parseInt(date[2]);
                                    suppAppDate = LocalDate.of(year, month, day); 
                                }
                            } catch (Exception e) {
                                System.out.println(e + " 6");
                            }
                            boolean interviewRequired = Boolean.parseBoolean(formattedAppLine[3]);
                            try {
                                if (interviewRequired) {
                                String[] date = formattedAppLine[4].split("/");
                                int day = Integer.parseInt(date[0]);
                                int month = Integer.parseInt(date[1]);
                                int year = Integer.parseInt(date[2]);
                                interviewDate = LocalDate.of(year, month, day); 
                            }
                            } catch (Exception e) {
                                System.out.println(e + " 7");
                            }

                            University.Application currentLineApplication = currentUniLine.new Application(
                                programName, suppAppRequired, suppAppDate,
                                interviewRequired, interviewDate
                            );
                            currentUniLine.addApplication(currentLineApplication);
                        }
                    }
                }
                line = br.readLine();
            }
            if (uniOpen && currentUniLine != null) {
                userUniOutput.add(currentUniLine);
            }

            br.close();
            totalUniversitiesApplied = userUniOutput;
        } catch (Exception e) {
            System.out.println(e + " 8");
        }
    }   
}
    
    
    
