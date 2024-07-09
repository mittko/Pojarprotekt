package http.base;

public class ErrorBody {
    String status;
    String timestamp;
    String error;
    String path;

    public String getError() {
        return error;
    }


}