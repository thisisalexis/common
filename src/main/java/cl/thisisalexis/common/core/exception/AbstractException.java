package cl.thisisalexis.common.core.exception;

public class AbstractException extends Exception {
    private TypeException typeException;

    public AbstractException(TypeException typeException) {
        super();
        this.typeException = typeException;
    }

}
