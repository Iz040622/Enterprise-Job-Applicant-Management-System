/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

/**
 *
 * @author chinj
 */
public class StringValidation {
    public static boolean validateString(String input){
        return input != null && !input.trim().isEmpty();
    }
}
