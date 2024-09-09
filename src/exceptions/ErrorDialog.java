package exceptions;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import http.base.ErrorBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.lang.reflect.Type;


public class ErrorDialog extends Exc {

    public static void showHttpError(Response response) {
        if(response.code() == 401) {
            showErrorMessage("Неоторизиран достъп");
            return;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ErrorBody>(){}.getType();
        try(ResponseBody responseBody = response.errorBody()) {
            if(responseBody != null) {
                ErrorBody error = gson.fromJson(responseBody.charStream(), type);
                showErrorMessage(error.getError());
            } else {
                showErrorMessage("Грешка!");
            }
        }
    }
    public static void main(String[] args) {

    }
}
