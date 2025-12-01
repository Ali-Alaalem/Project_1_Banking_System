import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      Customer customer = new Customer();
        Banker banker =new Banker();
        User user  =new User();
        Account sorce_acc = null;
boolean exit =false;



        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to ACME Bank tha safest bank in the world");
        System.out.println("*************************************************");
        System.out.println();
        System.out.print("Do you have an account or you want to create one?  (y/n) :");
        String account=scanner.next();
        if(account.equals("y")){
            System.out.print("User name / Email :");
            String user_name_or_email=scanner.next();
            System.out.print("Password :");
            String password=scanner.next();

            user.Login(
                    Optional.of(user_name_or_email),
                    String.valueOf(password.hashCode()),
                    Optional.of(user_name_or_email)
            );

            while (!exit) {
                if (user.getUser_type().equals("B")) {
                    System.out.println("*************************************************");
                    for (Account acc : user.getAccounts()) {
                        System.out.println(acc.getType() + ": " + acc.accountNumber + " (" + acc.balance + ")");
                    }
                    System.out.println("*************************************************");
                    System.out.println(" 1-Check requests \n 2-Transfer \n 3-Deposit \n 4-Withdraw \n 5-exit");
                    System.out.println("What do you want to do : ");
                    String option = scanner.next();
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
                            System.out.println();
                            banker.CheckRequests();
                        } else if (option2.equals("n")) {
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

                    } else if (option.equals("3")) {
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
                    } else if (option.equals("4")) {
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
                    else if (option.equals("5")) {
                        exit=true;
                    }
                } else if (user.getUser_type().equals("C")) {
                    System.out.println("*************************************************");
                    for (Account acc : user.getAccounts()) {
                        System.out.println(acc.getType() + ": " + acc.accountNumber + " (" + acc.balance + ")");
                    }
                    System.out.println("*************************************************");
                    System.out.println(" 1-Transfer \n 2-Deposit \n 3-Withdraw \n 4-exit");
                    System.out.println("What do you want to do : ");
                    String option = scanner.next();
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

                    } else if (option.equals("2")) {
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
                    } else if (option.equals("3")) {
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
                    }else if (option.equals("4")) {
                        exit=true;
                    }
                }
            }
        }else if(account.equals("n")){
            System.out.print("Do you want to create one?  (y/n) :");
            String create_acc=scanner.next();
            if(create_acc.equals("n")){
                System.exit(0);
            }else{
                System.out.print("First name :");
                String f_name=scanner.next();
                System.out.print("Last name :");
                String l_name=scanner.next();
                System.out.print("Email :");
                String Email=scanner.next();
                System.out.print("User name :");
                String user_name=scanner.next();
                System.out.print("Password :");
                String password=scanner.next();
                System.out.print("Account type checking account or savings account or both (CHK , SAV , BOTH :");
                String acc_type=scanner.next();
                customer.Create_account(user_name,password,Email,f_name,l_name,acc_type);
                System.out.println("Your account will be reviewed and created within 24 hours");
            }
        }



    }
}
