package Select;

/**
 * Exception when a select strategy does not exist
 */
public class SelectException extends Exception {
    public SelectException(String violation) {
        super(violation);
    }
}
