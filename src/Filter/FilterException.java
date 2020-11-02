package Filter;

/**
 * Exception when a filter strategy does not exist
 */
public class FilterException extends Exception{
    public FilterException(String violation) {
        super(violation);
    }
}
