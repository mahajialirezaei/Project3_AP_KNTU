package ir.ac.kntu;

public class SupportRequest {
    private RequestCondition requestCondition;
    private String requestText = new String();
    private RequestSection requestSection;
    private String answer = new String();
    private String userPhonenumber = new String();

    public String getUserPhonenumber() {
        return this.userPhonenumber;
    }

    public void setUserPhonenumber(String userPhonenumber) {
        this.userPhonenumber = userPhonenumber;
    }

    SupportRequest() {

    }

    SupportRequest(RequestCondition requestCondition, RequestSection requestSection, String requestText,
            String userPhonenumber) {
        setRequestCondition(requestCondition);
        setRequestSection(requestSection);
        setRequestText(requestText);
        setUserPhonenumber(userPhonenumber);
    }

    public SupportRequest(String line) {
        String[] fields = line.split(",");
        setRequestCondition(RequestCondition.valueOf(fields[0]));
        setRequestText(fields[1]);
        setRequestSection(RequestSection.valueOf(fields[2]));
        setAnswer(fields[3]);
        setUserPhonenumber(fields[4]);
    }

    public String getRequestText() {
        return this.requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    public RequestCondition getRequestCondition() {
        return this.requestCondition;
    }

    public void setRequestCondition(RequestCondition requestCondition) {
        this.requestCondition = requestCondition;
    }

    public RequestSection getRequestSection() {
        return this.requestSection;
    }

    public void setRequestSection(RequestSection requestSection) {
        this.requestSection = requestSection;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        Colors.colorString();
        return "{" +
                " requestCondition='" + getRequestCondition() + "'" +
                ", requestText='" + getRequestText() + "'" +
                ", requestSection='" + getRequestSection() + "'" +
                ", answer='" + getAnswer() + "'" +
                ", userPhonenumber='" + getUserPhonenumber() + "'" +
                "}";
    }

    public String fileToString() {
        return getRequestCondition() + "," + getRequestText() + "," + getRequestSection() + "," + getAnswer() + ","
                + getUserPhonenumber() + "\n";
    }

}
