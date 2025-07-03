package sk.leziemevpezinku.spring.api.exception;

public class AppParamNotFound extends RuntimeException {

    public AppParamNotFound(String paramName) {
        super("App param with name " + paramName + " not found.");
    }

    public AppParamNotFound(String paramName, Throwable cause) {
        super("App param with name " + paramName + " not found.", cause);
    }
}
