/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Keshandra A/L Jayaselan

package boundary;

import java.util.Scanner;

/**
 *
 * @author chinj
 */
public class SearchServiceUI {
    static Scanner scanner = new Scanner(System.in);
    
    //Method to get keyword
    public static String getKeyword(boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        
        if (!valid)
            System.out.println("\033[31m" + "\t\t\tCannot enter empty input! Please try again!" + "\033[0m");
        System.out.print("\t\t\tEnter search keyword: ");
        
        String keyword = scanner.nextLine();
        return keyword;
    }
}
