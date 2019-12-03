package cl.thisisalexis.common.core.exception;

import javax.validation.constraints.NotNull;

/**
 * This is a root class for all classes used in the application. The main idea behind it is that all customized exceptions
 * in the application extends from this class so they all have a common base; also, AbstractAppException are
 * properly handled by ApiWorkflowExecutor instances
 *
 * @author Alexis Bravo
 */
public abstract class AbstractAppException extends Exception {

    @NotNull
    private TypeException typeException;

    public AbstractAppException() {
        super();
    }

    public AbstractAppException(TypeException typeException) {
        super();
        this.typeException = typeException;
    }

    public TypeException getTypeException() {
        return typeException;
    }

    public void setTypeException(TypeException typeException) {
        this.typeException = typeException;
    }
}
