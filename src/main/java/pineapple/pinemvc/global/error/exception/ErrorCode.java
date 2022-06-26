package pineapple.pinemvc.global.error.exception;

public enum ErrorCode {
    NOT_ALLOWED_METHOD(816000, "Only GET, POST, PATCH AND DELETE methods are allowed");
    private final int code;
    private final String message;

    ErrorCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode valueOfOrNull(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }
}
