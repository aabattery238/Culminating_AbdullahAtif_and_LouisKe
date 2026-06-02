
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

    public User(String userID, String username, String password) {
        this.username = username;
        this.password = password;
        this.userID = userID;
    }
    private User(String username, String password) {
        this.username = username;
        this.password = password;
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyMMddHssSSS");
        this.userID = time.format(formatter);
        
    }
    
    public static void addUser(User newUser, javax.swing.JFrame gui) {
        ArrayList<User> users = new ArrayList<User>();
        try {
            //Initialize File Reader
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            //Initialize line String
            String line = br.readLine(); //Skip first line
            //Loop through file lines
            while (line != null) {
                line = br.readLine();
                String[] userProp = line.split(",", 3);
                users.add(new User(userProp[0], userProp[1], userProp[2]));
	    }
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(gui, "users.txt Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        users.add(newUser);
        users.sort((u1, u2) -> u1.getUserID().compareTo(u2.getUserID()));
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("userID,username,password");
            for (User user : users) {
                bw.newLine();
                bw.write(user.getUserID() +"," +user.getUsername() +"," +user.getPassword());
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(gui, "users.txt Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
    }
    
    
    public static boolean checkUserExists(String userInput, javax.swing.JFrame gui) {
        try {
            //Initialize File Reader
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            //Initialize line String
            String line = br.readLine(); //Skip first line
            //Loop through file lines
            while (line != null) {
                line = br.readLine();
                if (line.split(",")[1].equals(userInput)) {
                    return true;
                }
	    }
            br.close();
            return false;
        } catch (Exception e) {
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

    private String getUserID() {
        return userID;
    }

    private String getPassword() {
        return password;
    }
    
    
    
    

    
    
    
}