import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public abstract class Account implements ITransactional {
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getType() {
        return this instanceof SavingsAccount ? "SAV" : "CHK";
    }

    public void FindAccAndEditBalance(String targetAcc,double amount){
        try {

            List<String> lines = Files.readAllLines(Path.of("users.txt"));

            for (int i = 0; i <lines.size() ; i++) {
                String line=lines.get(i);
                String[] user=line.split(",");
                if(user[7].equals(targetAcc)){
                    user[8]=String.valueOf(Double.parseDouble(user[8])+amount);
                    lines.set(i,String.join(",",user));
                }
            }
            Files.write(Path.of("users.txt"),lines);

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    public static String generateAccountNumber() {
        long number = (long)(Math.random() * 1_000_000_0000L);
        return String.format("%010d", number);
    }

    @Override
    public void deposit(double amount) {
        balance+=amount;
        FindAccAndEditBalance(accountNumber,amount);
        System.out.println("you balance now is :" + balance);

    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (balance ==0){
            System.out.println("Insufficient funds");
        }else {
            balance-=amount;
            FindAccAndEditBalance(accountNumber,-amount);
            System.out.println("you balance now is :" + balance);
        }
    }

    @Override
    public void transfer(String targetAcc, double amount) throws Exception {
        withdraw(amount);
        FindAccAndEditBalance(targetAcc,amount);

    }


}
