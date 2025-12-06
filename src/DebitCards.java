public class DebitCards {
public String card_type;

    public DebitCards(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public double getWithdrawLimitPerDay() {
        return switch (card_type) {
            case "Mastercard Platinum" -> 20000;
            case "Mastercard Titanium" -> 10000;
            case "Mastercard" -> 5000;
            default -> 0;
        };
    }

    public double getTransferLimitPerDay() {
        return switch (card_type) {
            case "Mastercard Platinum" -> 40000;
            case "Mastercard Titanium" -> 20000;
            case "Mastercard" -> 10000;
            default -> 0;
        };
    }

    public double getTransferLimitOwnAccountPerDay() {
        return switch (card_type) {
            case "Mastercard Platinum" -> 80000;
            case "Mastercard Titanium" -> 40000;
            case "Mastercard" -> 20000;
            default -> 0;
        };
    }

    public double getDepositLimitPerDay() {
        return 100000;
    }

    public double getDepositLimitOwnAccountPerDay() {
        return 200000;
    }
}




