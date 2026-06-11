
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abdullahatif
 */

//Error checking class
public class ErrorCheck {
    //Format String to Double Safetly
    public static double doubleParse(String userInput, double min, double max, String textFieldName, javax.swing.JFrame gui) {
        //ensure string is not blank
        if (userInput.isBlank()) {
            JOptionPane.showMessageDialog(gui, "Please Enter a " +textFieldName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return -1; //input is Blank
        }
        double attemptedDouble;
        //attempt to convert string to double
        try {
            attemptedDouble = Double.parseDouble(userInput);
            //ensure within reasonable range
            if ((attemptedDouble < min) || (attemptedDouble > max)) {
                JOptionPane.showMessageDialog(gui, textFieldName +" outside of Range \n" +Double.toString(min) +" and " +Double.toString(max), "ERROR", JOptionPane.ERROR_MESSAGE);
                return -2; //input out of range
            } else {
                return attemptedDouble;
            }
        } catch(NumberFormatException e) {
            //ensure number was inputted
            JOptionPane.showMessageDialog(gui, "Please Enter a Valid " +textFieldName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return -3; //Invalid input
        } catch(Exception e) {
            JOptionPane.showMessageDialog(gui, "An Error Occured", "ERROR", JOptionPane.ERROR_MESSAGE);
            return -4;
        }
    }
    //Format String to Integer Safetly with Max
    public static int intParse(String userInput, int min, int max, String textFieldName, javax.swing.JFrame gui) {
        //ensure string is not blank
        if (userInput.isBlank()) {
            JOptionPane.showMessageDialog(gui, "Please Enter a " +textFieldName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return -1; //input is Blank
        }
        int attemptedDouble;
        //attempt to convert string to int
        try {
            attemptedDouble = Integer.parseInt(userInput);
            //ensure within reasonable range
            if ((attemptedDouble < min) || (attemptedDouble > max)) {
                JOptionPane.showMessageDialog(gui, textFieldName +" outside of Range \n" +Integer.toString(min) +" and " +Integer.toString(max), "ERROR", JOptionPane.ERROR_MESSAGE);
                return -2; //input out of range
            } else {
                return attemptedDouble;
            }
        } catch(NumberFormatException e) {
            //ensure number was inputted
            JOptionPane.showMessageDialog(gui, "Please Enter a Valid " +textFieldName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return -3; //Invalid input
        } catch(Exception e) {
            JOptionPane.showMessageDialog(gui, "An Error Occured", "ERROR", JOptionPane.ERROR_MESSAGE);
            return -4;
        }
    }
    //Format String to Integer Safetly without Max
    public static int intParse(String userInput, int min, String textFieldName, javax.swing.JFrame gui) {
        //ensure string is not blank
        if (userInput.isBlank()) {
            JOptionPane.showMessageDialog(gui, "Please Enter a " +textFieldName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return -1; //input is Blank
        }
        int attemptedDouble;
        //attempt to convert string to int
        try {
            attemptedDouble = Integer.parseInt(userInput);
            //ensure within reasonable range
            if ((attemptedDouble < min)) {
                JOptionPane.showMessageDialog(gui, textFieldName +" Cannot Be Less Than \n" +Integer.toString(min), "ERROR", JOptionPane.ERROR_MESSAGE);
                return -2; //input out of range
            } else {
                return attemptedDouble;
            }
        } catch(NumberFormatException e) {
            //ensure number was inputted
            JOptionPane.showMessageDialog(gui, "Please Enter a Valid " +textFieldName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return -3; //Invalid input
        } catch(Exception e) {
            JOptionPane.showMessageDialog(gui, "An Error Occured", "ERROR", JOptionPane.ERROR_MESSAGE);
            return -4;
        }
    }
    
    //Ensure String is valid (Special Characters and Numbers Allowed)
    public static String stringParse(String userInput, int maxLength, String textFieldName, javax.swing.JFrame gui) {
        //Check if blank
        if (userInput.isBlank()) {
            JOptionPane.showMessageDialog(gui, "Please Enter a " + textFieldName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } else if (userInput.length() <= maxLength) {
            //ensure under max length
            //return trimmed
            return userInput.trim();
        } else {
            return null;
        }
    }
    
    //Ensure String is valid
    public static String trueStringParse(String userInput, int maxLength, String textFieldName, javax.swing.JFrame gui) {
        //Check if blank
        if (userInput.isBlank()) {
            JOptionPane.showMessageDialog(gui, "Please Enter a " + textFieldName, "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        } else if (userInput.length() <= maxLength) {
            //ensure under max length
            String[] specialChars = {"!", "@", "#", "$", "%", "^", "&", "*", "_", "=", "+", "[", "]", "{", "}", "|", ";", ":", ",", "<", ">", "/", "?", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            for (String special : specialChars) {
                if (userInput.contains(special)) {
                    JOptionPane.showMessageDialog(gui, textFieldName +"Cannot Contain Special Characters", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            //return trimmed
            return userInput.trim();
        } else {
            JOptionPane.showMessageDialog(gui, "An Error Occured\nPlease Try Again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
