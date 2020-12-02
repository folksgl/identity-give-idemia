package gov.gsa.give.ipp.idemia.model.response;

public enum GiveMessage {
    INVALID_STATUS("Invalid/Missing Status"),
    INVALID_UEID("Invalid/Missing UEID"),
    USER_ENROLLED("User Enrolled Successfully"),
    INVALID_ZIP("Invalid/Missing ZIP code"),
    INVALID_UUID("Invalid/Missing UUID"),
    INVALID_FIRST_NAME("Invalid/Missing First Name"),
    INVALID_LAST_NAME("Invalid/Missing Last Name"),
    INVALID_EMAIL("Invalid/Missing Email Address");

    public final String value;

    private GiveMessage(String value) {
        this.value = value;
    }
}
