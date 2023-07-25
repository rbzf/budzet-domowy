package ps.as;

public class Transaction {
    private Integer id;
    private Type type;
    private String description;
    private double amount;
    private String date;

    public Transaction(Integer id, Type type, String description, double amount, String date) {
        this(type, description, amount, date);
        this.id = id;
    }

    public Transaction(Type type, String description, double amount, String date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                '}';
    }
}

