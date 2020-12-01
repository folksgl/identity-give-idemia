package gov.gsa.give.ipp.idemia.model.response;

public enum ErrorMessage {
    INVALID_ZIP("Invalid/Missing ZIP code");

    public final String value;

    private ErrorMessage(String value) {
        this.value = value;
    }
}
