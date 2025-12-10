import java.util.Optional;
import java.util.Scanner;

public class SystemCmd {

    Customer customer = new Customer();
    Banker banker = new Banker();
    User user = new User();
    Account sorce_acc = null;
    boolean exit = false;



    private String readAccountNumber(Scanner scanner) {
        String acc;
        while (true) {
            acc = scanner.next();
            if (acc.length() == 10 && acc.matches("\\d+")) {
                return acc;
            }
            System.out.print(" Invalid account number! Must be exactly 10 digits. Re-enter: ");
        }
    }

    private double readAmount(Scanner scanner) {
        while (true) {
            String input = scanner.next();

                double amount = Double.parseDouble(input);
                if (amount > 0 ) {
                    return amount;

            }
                System.out.print(" Amount must be greater than 0. Re-enter: ");
        }
    }

    private String readCardType(Scanner scanner) {
        while (true) {
            String type = scanner.next().trim();
            if (type.equalsIgnoreCase("Mastercard")
                    || type.equalsIgnoreCase("Mastercard Titanium")
                    || type.equalsIgnoreCase("Mastercard Platinum")) {
                return type;
            }
            System.out.print(" Invalid card type! Re-enter (Mastercard Platinum, Mastercard Titanium, Mastercard): ");
        }
    }

    private String readAccTypeWhileCreatingCards(Scanner scanner) {
        while (true) {
            String type = scanner.next().toUpperCase();
            if (type.equals("SAV") || type.equals("CHK")) {
                return type;
            }
            System.out.print(" Invalid account type! Must be SAV or CHK: ");
        }
    }

    private String readSignupAccType(Scanner scanner) {
        while (true) {
            String type = scanner.next().toUpperCase();
            if (type.equals("SAV") || type.equals("CHK") || type.equals("BOTH")) {
                return type;
            }
            System.out.print(" Invalid choice! Choose CHK, SAV or BOTH: ");
        }
    }




    public void RunningSystem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*************************************************");
        System.out.println("Welcome to ACME Bank tha safest bank in the world");
        System.out.println("*************************************************");
        System.out.print("1-Sign up\n2-Sign in\nYour choice: ");

        String account = scanner.next();


        if (account.equals("2")) {

            while (user.getF_name() == null) {
                System.out.print("User name / Email : ");
                String user_name_or_email = scanner.next();
                System.out.print("Password : ");
                String password = scanner.next();

                user.Login(
                        Optional.of(user_name_or_email),
                        String.valueOf(password.hashCode()),
                        Optional.of(user_name_or_email)
                );
            }

            while (!exit) {
                System.out.println("*************************************************");
                for (Account acc : user.getAccounts()) {
                    System.out.println(acc.getType() + ": " + acc.accountNumber + " (" + acc.balance + ")");
                }
                System.out.println("*************************************************");

                if (user.getUser_type().equals("B")) {
                    System.out.println(" 1-Check requests");
                    System.out.println(" 2-Transfer");
                    System.out.println(" 3-Deposit");
                    System.out.println(" 4-Withdraw");
                    System.out.println(" 5-Display History");
                    System.out.println(" 6-Account Statement");
                    System.out.println(" 7-Filter Transaction");
                    System.out.println(" 8-exit");
                } else {
                    System.out.println(" 1-Transfer");
                    System.out.println(" 2-Deposit");
                    System.out.println(" 3-Withdraw");
                    System.out.println(" 4-Display History");
                    System.out.println(" 5-Account Statement");
                    System.out.println(" 6-Filter Transaction");
                    System.out.println(" 7-exit");
                }

                System.out.print("What do you want to do : ");
                String option = scanner.next();



                if (user.getUser_type().equals("B")) {

                    if (option.equals("1")) {
                        System.out.println();
                        System.out.println("*************************************************");
                        banker.Display_Requests();
                        System.out.println("*************************************************");
                        banker.CheckRequests();
                    }
                    else if (option.equals("2")) {
                        System.out.print("From Account: ");
                        String from_acc = readAccountNumber(scanner);

                        System.out.print("To Account : ");
                        String to_acc_number = readAccountNumber(scanner);

                        System.out.print("Amount : ");
                        double amount = readAmount(scanner);

                        sorce_acc = user.getAccounts().stream()
                                .filter(a -> a.accountNumber.equals(from_acc))
                                .findFirst().orElse(null);

                        if (sorce_acc != null) {
                            try {
                                sorce_acc.transfer(to_acc_number, amount);
                            } catch (Exception e) {
                                throw new RuntimeException();

                            }
                        }else{
                            System.out.println("This is not your account");
                        }
                    } else if (option.equals("3")) {
                        System.out.print("Account number : ");
                        String in_acc = readAccountNumber(scanner);

                        System.out.print("Amount : ");
                        double amount = readAmount(scanner);

                        sorce_acc = user.getAccounts().stream()
                                .filter(a -> a.accountNumber.equals(in_acc))
                                .findFirst().orElse(null);

                        if (sorce_acc != null) {
                            try {
                                sorce_acc.deposit(amount);
                            } catch (Exception e) {
                                throw new RuntimeException();
                            }
                        }else{
                            System.out.println("This is not your account");
                        }
                    } else if(option.equals("4")) {
                        System.out.print("Account number : ");
                        String from_acc = readAccountNumber(scanner);

                        System.out.print("Amount : ");
                        double amount = readAmount(scanner);

                        sorce_acc = user.getAccounts().stream()
                                .filter(a -> a.accountNumber.equals(from_acc))
                                .findFirst().orElse(null);

                        if (sorce_acc != null) {
                            try {
                                sorce_acc.withdraw(amount);
                            } catch (Exception e) {
                                throw new RuntimeException();
                            }
                        }else{
                            System.out.println("This is not your account");
                        }
                    }

                    else if (option.equals("5")){
                        System.out.print("For which account (SAV,CHK) :");
                        user.DisplayHistory(scanner.next());
                    }
                    else if(option.equals("6"))
                    {
                        System.out.print("For which account (SAV,CHK) :");
                        user.DetailedAccountStatment(scanner.next());
                    }

                    else if (option.equals("7")) {
                        System.out.println(" 1-Today");
                        System.out.println(" 2-Yesterday");
                        System.out.println(" 3-Last 7 days");
                        System.out.println(" 4-Last 30 days");
                        System.out.print("Pick the filter you want : ");
                        user.FilterTransaction(scanner.next());
                    }
                    else if (option.equals("8")) {
                        System.exit(0);
                    }

                }


                else {

                    if (option.equals("1")) {
                        System.out.print("From Account: ");
                        String from_acc = readAccountNumber(scanner);

                        System.out.print("To Account : ");
                        String to_acc_number = readAccountNumber(scanner);

                        System.out.print("Amount : ");
                        double amount = readAmount(scanner);

                        sorce_acc = user.getAccounts().stream()
                                .filter(a -> a.accountNumber.equals(from_acc))
                                .findFirst().orElse(null);

                        if (sorce_acc != null) {
                            try {
                                sorce_acc.transfer(to_acc_number, amount);
                            } catch (Exception e) {
                                throw new RuntimeException();
                            }
                        }else{
                            System.out.println("This is not your account");
                        }
                    }

                    else if (option.equals("2")) {
                        System.out.print("Account number : ");
                        String in_acc = readAccountNumber(scanner);

                        System.out.print("Amount : ");
                        double amount = readAmount(scanner);

                        sorce_acc = user.getAccounts().stream()
                                .filter(a -> a.accountNumber.equals(in_acc))
                                .findFirst().orElse(null);

                        if (sorce_acc != null) {
                            try {
                                sorce_acc.deposit(amount);
                            } catch (Exception e) {
                                throw new RuntimeException();
                            }
                        }else{
                            System.out.println("This is not your account");
                        }
                    }

                    else if (option.equals("3")) {
                        System.out.print("Account number : ");
                        String from_acc = readAccountNumber(scanner);

                        System.out.print("Amount : ");
                        double amount = readAmount(scanner);

                        sorce_acc = user.getAccounts().stream()
                                .filter(a -> a.accountNumber.equals(from_acc))
                                .findFirst().orElse(null);

                        if (sorce_acc != null) {
                            try {
                                sorce_acc.withdraw(amount);
                            } catch (Exception e) {
                                throw new RuntimeException();
                            }
                        }else{
                            System.out.println("This is not your account");
                        }
                    }

                    else if (option.equals("4")){
                        System.out.print("For which account (SAV,CHK) :");
                        user.DisplayHistory(scanner.next());
                    }
                    else if(option.equals("5"))
                    {
                        System.out.print("For which account (SAV,CHK) :");
                        user.DetailedAccountStatment(scanner.next());
                    }

                    else if (option.equals("6")) {
                        System.out.println(" 1-Today");
                        System.out.println(" 2-Yesterday");
                        System.out.println(" 3-Last 7 days");
                        System.out.println(" 4-Last 30 days");
                        System.out.print("Pick the filter you want : ");
                        user.FilterTransaction(scanner.next());
                    }
                    else if (option.equals("7")) {
                        System.exit(0);
                    }


                }

                System.out.println();
                System.out.print("Press ENTER to return...");
                try {
                    System.in.read();
                } catch (Exception ignored) {
                }
            }

        }


        else if (account.equals("1")) {

            System.out.print("First name : ");
            String f_name = scanner.next();

            System.out.print("Last name : ");
            String l_name = scanner.next();

            System.out.print("Email : ");
            String Email = scanner.next();

            System.out.print("User name : ");
            String user_name = scanner.next();

            System.out.print("Password : ");
            String password = scanner.next();

            System.out.print("Account type (CHK , SAV , BOTH) : ");
            String acc_type = readSignupAccType(scanner);

            String CHK_card_type=null;
            String SAV_card_type=null;

            if(acc_type.equals("BOTH")){
                System.out.print("Card Type for CHK account (Mastercard Platinum,Mastercard Titanium,Mastercard) : ");
                 CHK_card_type = readCardType(scanner);
                System.out.print("Card Type for SAV account (Mastercard Platinum,Mastercard Titanium,Mastercard) : ");
                 SAV_card_type = readCardType(scanner);
            }else if(acc_type.equals("CHK")){
                System.out.print("Card Type for CHK account (Mastercard Platinum,Mastercard Titanium,Mastercard) : ");
                 CHK_card_type = readCardType(scanner);
            }else if(acc_type.equals("SAV")){
                System.out.print("Card Type for SAV account (Mastercard Platinum,Mastercard Titanium,Mastercard) : ");
                 SAV_card_type = readCardType(scanner);
            }

            customer.Create_account(user_name, password, Email, f_name, l_name, acc_type , Optional.ofNullable(CHK_card_type) ,Optional.ofNullable(SAV_card_type));
            System.out.println("Your account will be reviewed and created within 24 hours");
        }
        else{
            System.out.println("Please enter a number from the list");
        }
    }
}
