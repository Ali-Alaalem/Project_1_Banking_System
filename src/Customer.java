import java.io.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Customer extends User{



    @Override
    public boolean Create_account(String userName, String password, String Email ,String first_name ,String last_name,String accType) {
        try {
            setUser_type("C");

            File usersFile = new File("users.txt");
            Scanner scanner = new Scanner(usersFile);
            int lastUserId = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] user = line.split(",");
                lastUserId = Math.max(lastUserId, Integer.parseInt(user[0]));

                String fileUserName = user[4];
                String fileEmail = user[3];

                if (userName.equals(fileUserName)) {
                    System.out.println("This User name already exists");
                    return false;
                }
                if (Email.equals(fileEmail)) {
                    System.out.println("This Email already exists");
                    return false;
                }
            }
            scanner.close();

            File requestsFile = new File("requests.txt");
            if (!requestsFile.exists()) requestsFile.createNewFile();

            Scanner reqScanner = new Scanner(requestsFile);
            int lastRequestId = 0;

            while (reqScanner.hasNextLine()) {
                String line = reqScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] user = line.split(",");
                lastRequestId = Math.max(lastRequestId, Integer.parseInt(user[0]));

                String fileUserName = user[4];
                String fileEmail = user[3];

                if (userName.equals(fileUserName)) {
                    System.out.println("This User name already exists in requests");
                    return false;
                }
                if (Email.equals(fileEmail)) {
                    System.out.println("This Email already exists in requests");
                    return false;
                }
            }
            reqScanner.close();

            int newId = Math.max(lastUserId, lastRequestId) + 1;
            setId(newId);


            BufferedWriter write = new BufferedWriter(new FileWriter(requestsFile, true));
           String  chk_acc_number= Account.generateAccountNumber();
           String  sav_acc_number=Account.generateAccountNumber();

            if(accType.equalsIgnoreCase("CHK")){
                String info = getId() + "," + first_name + "," + last_name + "," + Email + "," + userName + "," + password.hashCode()+"," +"CHK"+","+chk_acc_number+","+"No Card"+","+"0"+"," + getUser_type();
                write.write(info);
                write.newLine();

            }else if (accType.equalsIgnoreCase("SAV")){
                String info = getId() + "," + first_name + "," + last_name + "," + Email + "," + userName + "," + password.hashCode()+"," +"SAV"+","+sav_acc_number+","+"No Card"+","+"0"+"," + getUser_type();
                write.write(info);
                write.newLine();

            }else if(accType.equalsIgnoreCase("BOTH")){
                String info = getId() + "," + first_name + "," + last_name + "," + Email + "," + userName + "," + password.hashCode()+"," +"CHK"+","+chk_acc_number+","+"No Card"+","+"0"+"," + getUser_type();
                String info2 = getId() + "," + first_name + "," + last_name + "," + Email + "," + userName + "," + password.hashCode()+"," +"SAV"+","+sav_acc_number+","+"No Card"+","+"0"+"," + getUser_type();
                write.write(info);
                write.newLine();
                write.write(info2);
                write.newLine();

            }
            write.close();

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
