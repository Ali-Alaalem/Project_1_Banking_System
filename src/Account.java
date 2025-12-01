import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public abstract class Account implements ITransactional {
    protected String accountNumber;
    protected double balance;
public int overdrafting_count=0;
public User user;


    public Account(String accountNumber, double balance , User user) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.user=user;

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


            if(overdrafting_count ==2 && balance>0){
                overdrafting_count=0;
            }

        Writehistory(amount ,"deposit");

    }

    @Override
    public void withdraw(double amount )  {

        if(overdrafting_count ==2 ) {
            System.out.println("you cant do anything your account is locked");
            return;
        }
        if(balance < 0) {
            overdrafting_count++;
            balance -=35;
            if(amount >= 100){
                return;
            }
        }
        balance -= amount;
        FindAccAndEditBalance(accountNumber, -amount);
        System.out.println("you balance now is :" + balance);
        Writehistory(amount ,"withdraw");
    }

    @Override
    public void transfer(String targetAcc, double amount)  {
        if(overdrafting_count ==2 ) {
            System.out.println("you cant do anything your account is locked");
        }else {
            withdraw(amount);
            FindAccAndEditBalance(targetAcc, amount);
Writehistory(amount ,"transfer");
        }
    }


    public void Writehistory(double amount , String tran_type){
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter("history.txt", true));
            write.write(user.getId()+","+"Account Type:"+getType()+","+" - Account number:"+accountNumber+","+" - Transaction Type:"+tran_type+","+" - Amount:"+amount+","+" - Current balance:"+balance);
            write.newLine();
            write.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
