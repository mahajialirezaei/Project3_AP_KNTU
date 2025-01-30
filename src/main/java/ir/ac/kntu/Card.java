package ir.ac.kntu;

public class Card {
    private String iDocument = "";
    private String password = "";

    Card() {

    }

    public Card(String iDocument, String password) {
        setIDocument(iDocument);
        setPassword(password);
    }

    public String getIDocument() {
        return this.iDocument;
    }

    public void setIDocument(String iDocument) {
        this.iDocument = iDocument;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
