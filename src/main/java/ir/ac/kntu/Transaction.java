package ir.ac.kntu;

import java.time.Instant;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {
    private double amount;
    private String source = new String();
    private String destination = new String();
    private String firstNameDest;
    private String lastNameDest;
    private TransactionType type;
    private Date date = new Date();
    private int iDocumentNumTrans;

    public Transaction(String line) {
        String[] fields = line.split(",");
        setAmount(Double.parseDouble(fields[0]));
        setSource(fields[1]);
        setDestination(fields[2]);
        setFirstNameDest(fields[3]);
        setLastNameDest(fields[4]);
        setType(TransactionType.valueOf(fields[5]));
        setDate(Instant.parse(fields[6]));
        setIDocumentNumTrans(Integer.parseInt(fields[7]));
    }

    public int getIDocumentNumTrans() {
        return this.iDocumentNumTrans;
    }

    public void setIDocumentNumTrans(int iDocumentNumTrans) {
        this.iDocumentNumTrans = iDocumentNumTrans;
    }

    public String getFirstNameDest() {
        return this.firstNameDest;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Instant instant) {
        String date = instant.toString();
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        int hours = Integer.parseInt(date.substring(11, 13));
        int minutes = Integer.parseInt(date.substring(14, 16));
        int second = Integer.parseInt(date.substring(17, 19));
        this.date = new Date(day, year, month, hours, minutes, second, instant);
    }

    public String getFirstNameDes() {
        return this.firstNameDest;
    }

    public void setFirstNameDest(String firstNameDest) {
        this.firstNameDest = firstNameDest;
    }

    public String getLastNameDest() {
        return this.lastNameDest;
    }

    public void setLastNameDest(String lastNameDest) {
        this.lastNameDest = lastNameDest;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public TransactionType getType() {
        return this.type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Transaction() {

    }

    public Transaction(TransactionType type, double amount, String source, String destination, Instant instant,
            String firstNameDest, String lastNameDest) {
        this.type = type;
        this.amount = amount;
        this.source = source;
        this.destination = destination;
        setDate(instant);
        this.firstNameDest = firstNameDest;
        this.lastNameDest = lastNameDest;
        this.iDocumentNumTrans = Math.abs((int) (Math.random() * 100 + 47 * 51 - 31
                + Objects.hash(amount, source, destination, instant)));
    }

    @Override
    public int compareTo(Transaction object) {
        Transaction transaction1 = object;
        return -1 * this.date.compareTo(transaction1.date);
    }

    @Override
    public String toString() {
        Colors.colorString();
        return "{" +
                " amount='" + getAmount() + "'" +
                ", source='" + getSource() + "'" +
                ", destination='" + getDestination() + "'" +
                ", firstNameDest='" + getFirstNameDest() + "'" +
                ", lastNameDest='" + getLastNameDest() + "'" +
                ", type='" + getType() + "'" +
                ", date='" + getDate() + "'" +
                ", IdNumberTrans='" + getIDocumentNumTrans() + "'" +
                "}";
    }

    public String fileToString() {
        return getAmount() + "," +
                getSource() + "," +
                getDestination() + "," +
                getFirstNameDest() + "," +
                getLastNameDest() + "," +
                getType() + "," +
                getDate().fileToString() + "," +
                getIDocumentNumTrans() + "\n";
    }

}
