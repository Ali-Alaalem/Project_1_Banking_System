import java.util.Optional;
import java.util.Scanner;

public class SystemCmd {
    Customer customer = new Customer();
    Banker banker =new Banker();
    User user  =new User();
    Account sorce_acc = null;
    boolean exit =false;

    public void RunningSystem(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to ACME Bank tha safest bank in the world");
        System.out.println("*************************************************");
        System.out.println();
        System.out.print("Do you have an account or you want to create one?  (y/n) :");
        String account = scanner.next();

        if (account.equals("y")) {

            System.out.print("User name / Email :");
            String user_name_or_email = scanner.next();
            System.out.print("Password :");
            String password = scanner.next();

            user.Login(
                    Optional.of(user_name_or_email),
                    String.valueOf(password.hashCode()),
                    Optional.of(user_name_or_email)
            );

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
                    System.out.println(" 6-exit");
                } else {
                    System.out.println(" 1-Transfer");
                    System.out.println(" 2-Deposit");
                    System.out.println(" 3-Withdraw");
                    System.out.println(" 4-Display History");
                    System.out.println(" 5-exit");
                }

                System.out.print("What do you want to do : ");
                String option = scanner.next();

                if (user.getUser_type().equals("B")) {

                    if (option.equals("1")) {
                        System.out.println();
                        banker.Requests_num();
                        System.out.println();
                        System.out.print("Do you want to proceed  (y/n) :");
                        String option2 = scanner.next();

                        if (option2.equals("y")) {
                            System.out.println("*************************************************");
                            banker.Display_Requests();
                            System.out.println("*************************************************");
                            banker.CheckRequests();
                        } else {
                            System.exit(0);
                        }
                    }

                    if (option.equals("2")) {
                        System.out.print("Account number : ");
                        String from_acc = scanner.next();
                        System.out.print("Account number : ");
                        String to_acc_number = scanner.next();
                        System.out.print("Amount : ");
                        String amount = scanner.next();

                        for (Account acc : user.getAccounts()) {
                            if (acc.accountNumber.equals(from_acc)) {
                                sorce_acc = acc;
                            }
                        }

                        try {
                            sorce_acc.transfer(to_acc_number, Double.parseDouble(amount));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (option.equals("3")) {
                        System.out.print("Account number : ");
                        String in_acc = scanner.next();
                        System.out.print("Amount : ");
                        String amount = scanner.next();

                        for (Account acc : user.getAccounts()) {
                            if (acc.accountNumber.equals(in_acc)) {
                                sorce_acc = acc;
                            }
                        }

                        try {
                            sorce_acc.deposit(Double.parseDouble(amount));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (option.equals("4")) {
                        System.out.print("Account number : ");
                        String from_acc = scanner.next();
                        System.out.print("Amount : ");
                        String amount = scanner.next();

                        for (Account acc : user.getAccounts()) {
                            if (acc.accountNumber.equals(from_acc)) {
                                sorce_acc = acc;
                            }
                        }

                        try {
                            sorce_acc.withdraw(Double.parseDouble(amount));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (option.equals("5")) {
                        user.DisplayHistory();
                    }

                    if (option.equals("6")) {
                        exit = true;
                    }
                }

                else if (user.getUser_type().equals("C")) {

                    if (option.equals("1")) {
                        System.out.print("Account number : ");
                        String from_acc = scanner.next();
                        System.out.print("Account number : ");
                        String to_acc_number = scanner.next();
                        System.out.print("Amount : ");
                        String amount = scanner.next();

                        for (Account acc : user.getAccounts()) {
                            if (acc.accountNumber.equals(from_acc)) {
                                sorce_acc = acc;
                            }
                        }

                        try {
                            sorce_acc.transfer(to_acc_number, Double.parseDouble(amount));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    else if (option.equals("2")) {
                        System.out.print("Account number : ");
                        String in_acc = scanner.next();
                        System.out.print("Amount : ");
                        String amount = scanner.next();

                        for (Account acc : user.getAccounts()) {
                            if (acc.accountNumber.equals(in_acc)) {
                                sorce_acc = acc;
                            }
                        }

                        try {
                            sorce_acc.deposit(Double.parseDouble(amount));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }


                    else if (option.equals("3")) {
                        System.out.print("Account number : ");
                        String from_acc = scanner.next();
                        System.out.print("Amount : ");
                        String amount = scanner.next();

                        for (Account acc : user.getAccounts()) {
                            if (acc.accountNumber.equals(from_acc)) {
                                sorce_acc = acc;
                            }
                        }

                        try {
                            sorce_acc.withdraw(Double.parseDouble(amount));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    else if (option.equals("4")) {
                        user.DisplayHistory();
                    }

                    else if (option.equals("5")) {
                        exit = true;
                    }
                }

                System.out.println();
                System.out.print("Press ENTER to return to main menu...");
                try { System.in.read(); } catch (Exception e) { }
            }

        } else if (account.equals("n")) {

            System.out.print("Do you want to create one?  (y/n) :");
            String create_acc = scanner.next();

            if (create_acc.equals("n")) {
                System.exit(0);
            }

            System.out.print("First name :");
            String f_name = scanner.next();
            System.out.print("Last name :");
            String l_name = scanner.next();
            System.out.print("Email :");
            String Email = scanner.next();
            System.out.print("User name :");
            String user_name = scanner.next();
            System.out.print("Password :");
            String password = scanner.next();
            System.out.print("Account type checking account or savings account or both (CHK , SAV , BOTH :");
            String acc_type = scanner.next();

            customer.Create_account(user_name, password, Email, f_name, l_name, acc_type);
            System.out.println("Your account will be reviewed and created within 24 hours");
        }



    }
}
