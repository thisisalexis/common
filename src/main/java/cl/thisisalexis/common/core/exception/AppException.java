package cl.thisisalexis.common.core.exception;

/**
 * A concrete implementation of AbstractAppException for common use
 *
 * @author Alexis Bravo
 */
public class AppException extends AbstractAppException {

    public AppException(Throwable cause) {
        super(cause);
        setTypeException(ConcreteTypeException.INTERNAL_ERROR);
    }

}
