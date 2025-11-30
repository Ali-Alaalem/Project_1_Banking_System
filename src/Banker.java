import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class Banker extends User{
    File file = new File("requests.txt");


    @Override
    public boolean Create_account(String userName, String password, String Email, String first_name, String last_name,String accType) {
        return false;
    }

    public void Requests_num() {
        if (!file.exists()) {
            System.out.println("There are 0 requests to be solved");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            int lines = 0;
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                lines++;
            }
            System.out.println("There are " + lines + " requests to be solved");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void Display_Requests() {
        if (!file.exists()) {
            System.out.println("No requests to display");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            System.out.println("Current account requests:");
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




    public boolean CheckRequests() {
        try {
            Scanner scanner = new Scanner(file);
            Scanner scanner1 = new Scanner(System.in);

            System.out.print("Enter the IDs to approve (separate with ,): ");
            String[] Ids = scanner1.next().split(",");
            while (scanner.hasNextLine()) {
                String[] user = scanner.nextLine().split(",");

                for (String id : Ids) {
                    if (user[0].equals(id)) {

                        BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
                        writer.write(String.join(",", user));
                        writer.newLine();
                        writer.close();
                        System.out.println("Account " + id + " added to users.txt");
                    }
                }
            }

            scanner.close();
            if (file.delete()) {
                System.out.println("requests.txt has been deleted");
            } else {
                System.out.println("Failed to delete requests.txt (maybe path is wrong or file is in use)");
            }

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
