package cl.thisisalexis.common.core.api;

import cl.thisisalexis.common.core.workflow.ApiWorkflowExecutor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * An abstract representation of an ApiController class, that is a class that contains or implements methods that acts as
 * an API layer
 *
 * @author Alexis Bravo
 */
public abstract class AbstractApiController implements ApiControllerInt {

    @Autowired
    protected ApiWorkflowExecutor apiWorkflowExecutor;

}
