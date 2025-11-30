public interface ITransactional {
    void deposit(double amount);
    void withdraw(double amount) throws Exception;
    void transfer(String targetAcc, double amount) throws Exception;
}
