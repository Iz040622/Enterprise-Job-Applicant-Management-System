/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.util.Scanner;

/**
 *
 * @author chinj
 */
public class EmptyADTHandling {
    static Scanner scanner = new Scanner(System.in);
    
    public static void displayEmptyMessage(String word){
        System.out.print("\033[12;0H\033[0J");
        System.out.print("\t\t\t" + word + " is empty! Press enter to continue...");
        scanner.nextLine();
    }
}
