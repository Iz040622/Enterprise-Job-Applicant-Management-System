/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

/**
 *
 * @author chinj
 */
public class SuccessMessageUI {
    public static void successMessage(String action){
        System.out.print("\n\n\t\t\t" + action + " Successful");
        
        //Delay few seconds before clearing page
        try {
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(500);
            System.out.print(".");
            Thread.sleep(200);
        }
        catch (InterruptedException e) {
            System.out.println("Delay Error!");
        }
    }
}
