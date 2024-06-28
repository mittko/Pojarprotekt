package http;

import java.util.List;

public abstract class RequestCallback {
    public abstract <T> void callback(List<T> objects);
}
