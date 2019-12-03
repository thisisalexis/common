package cl.thisisalexis.common.core.workflow;

import cl.thisisalexis.common.core.exception.TypeException;

/**
 * A concrete implementation of ExecutorResponse to describe a common API response for handled errors
 *
 * @author Alexis Bravo
 */
public class ErrorExecutorResponse implements ExecutorResponse {

    private String message;
    private TypeException typeException;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TypeException getTypeException() {
        return typeException;
    }

    public void setTypeException(TypeException typeException) {
        this.typeException = typeException;
    }
}