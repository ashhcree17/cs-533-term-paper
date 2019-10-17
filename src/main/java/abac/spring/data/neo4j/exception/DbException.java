package abac.spring.data.neo4j.exception;

public class DbException extends RuntimeException {
    public DbException(String message, Exception e) {
        super(message, e);
    }
}
