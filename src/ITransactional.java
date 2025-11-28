public interface ITransactional {
    void deposit(double amount);
    void withdraw(double amount) throws Exception;
    void transfer(Account target, double amount) throws Exception;
}
