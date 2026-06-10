
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
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
    private String username; 
    private String password;
    private String userID;
    private ArrayList<University> totalUniversitiesApplied = new ArrayList<University>();

    public User(String userID, String username, String password) {
        this.username = username;
        this.password = password;
        this.userID = userID;
    }
    private User(String username, String password) {
        this.username = username;
        this.password = password;
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyDDHssSS");
        this.userID = time.format(formatter);
    }
    
    public static void addUser(User newUser, javax.swing.JFrame gui) {
        ArrayList<User> users = getUsers();
        users.add(newUser);
        users.sort((u1, u2) -> u1.getUserID().compareTo(u2.getUserID()));
        System.out.println(users);
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
            return users;
        } catch (Exception e) {
            System.out.println(e+" 1");
            return null;
        }
    }
    
    
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
    
    private static String findUser(String userInput, javax.swing.JFrame gui) {
        try {
            //Initialize File Reader
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            //Initialize line String
            String line = br.readLine();
            //Loop through file lines
            while (line != null) {
                line = br.readLine();
                if (line.split(",")[1].equals(userInput)) {
                    return line;
                }
	    }
            br.close();
            return null;
        } catch (Exception e) {
            System.out.println(e+" 4");
            JOptionPane.showMessageDialog(gui, "users.txt Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public static User login(String usernameInput, String passwordInput, javax.swing.JFrame gui) {
        String[] userLine = findUser(usernameInput, gui).split(",", 3);
        
        if (userLine[2].equals(passwordInput)) {
           return new User(userLine[0], userLine[1], userLine[2]); 
        } else {
            JOptionPane.showMessageDialog(gui, "Incorrect Password", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }  
    }
    
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

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    private String getPassword() {
        return password;
    }  
    
    private University booleanSearchUniversity(String searchUniversity) {
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
        University universityFound = booleanSearchUniversity(searchUniversity);
        if (universityFound != null) {
            return universityFound;
        } else {
            return null;
        }
    }
    
    
    
    public void generateApplication(University selectedUniversity, String programName, boolean suppAppRequired, LocalDateTime suppAppDate, boolean interviewRequired, LocalDateTime interviewDate) {
        University universityFound = booleanSearchUniversity(selectedUniversity.getUniversityName());
        if (universityFound != null) {
           universityFound.genApplication(programName, suppAppRequired, suppAppDate, interviewRequired, interviewDate, this);
        }
    }
    
    
    
    public ArrayList<University.Application> getApplications() {
        ArrayList<University.Application> userApplications = new ArrayList<University.Application>();
        for (University uniApplied : totalUniversitiesApplied) {
            for(University.Application uniApplications : uniApplied.getApplications()) {
                userApplications.add(uniApplications);
            }
        }
        return userApplications;
    }
    
    
}