import java.util.Scanner;

import controller.Controller;

public class MainApp {
    
    public static void main(String[] args) {
        
        // Creating an instance of the Controller class
        Controller controller = new Controller();

        // Setting up a scanner to read input from the user
        Scanner scanner = new Scanner(System.in);
        
        int scelta;

        // Display a welcome message
        System.out.println("----------------------------------------------------");
        System.out.println("Welcome to the Second Hand Clothes Java Application!");
        System.out.println("----------------------------------------------------");

        do {
            // Display available options to the user
            controller.mostraOpzioni();
            
            // Read user's choice
            scelta = scanner.nextInt();
            
            // Consume the newline left in the buffer
            scanner.nextLine();

            // Execute the operation based on user's choice
            controller.eseguiOperazione(scelta);
            
        } while (scelta != 0); // Loop until the user chooses to exit (choice 0)

        scanner.close();
    }
}