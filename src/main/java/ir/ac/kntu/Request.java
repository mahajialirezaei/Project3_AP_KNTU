package ir.ac.kntu;

import java.util.Map;

public class Request extends SupportRequest {
    private AuthenticationType authentication;

    Request() {
        super();
    }

    public Request(AuthenticationType authentication, String answer, RequestSection section, RequestCondition condition, String phoneNumber) {
        super(condition, section, "please Authentication", phoneNumber);
        setAnswer(answer);
        setAuthentication(authentication);
    }

    public static Request getrequest(User user1, Map<SupportRequest, User> requests) {
        for (SupportRequest request : requests.keySet()) {
            if (requests.get(request).equals(user1) && "ir.ac.kntu.Request".equals(request.getClass().getName())) {
                return (Request) request;
            }
        }
        return new Request(AuthenticationType.INPROGRESS, "no answer", RequestSection.AUTHENTICATION, RequestCondition.INPROGRESS, user1.getPhoneNumber());
    }

    @Override
    public String getRequestText() {
        return super.getRequestText() + getUserPhonenumber();
    }

    public AuthenticationType getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(AuthenticationType authentication) {
        this.authentication = authentication;
    }

    @Override
    public String toString() {
        return super.toString() + "Authentication request {" + " authentication='" + getAuthentication() + "'" + ", answer='" + getAnswer() + "'" + "}";
    }

}
