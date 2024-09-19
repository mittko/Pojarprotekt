package http;

import jxl.write.WriteException;

import java.io.IOException;
import java.util.List;

public abstract class RequestCallback {
    public abstract <T> void callback(List<T> objects);
}
