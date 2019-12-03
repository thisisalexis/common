package cl.thisisalexis.common.core.workflow;

import cl.thisisalexis.common.core.exception.AbstractAppException;

/**
 * An executor is an interface that represents any service that executes an in/out operation in the application.
 * It helps to have a common and unique behavior to all services with one and only one function
 *
 * @author Alexis Bravo
 */
public interface Executor<rq extends ExecutorRequest, rs extends ExecutorResponse> {

    rs execute(rq executorParam) throws AbstractAppException;

    rs execute() throws AbstractAppException;

}