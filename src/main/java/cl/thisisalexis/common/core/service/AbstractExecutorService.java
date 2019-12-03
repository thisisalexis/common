package cl.thisisalexis.common.core.service;

import cl.thisisalexis.common.core.exception.AbstractAppException;
import cl.thisisalexis.common.core.workflow.Executor;
import cl.thisisalexis.common.core.workflow.ExecutorRequest;
import cl.thisisalexis.common.core.workflow.ExecutorResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class AbstractExecutorService<rq extends ExecutorRequest, rs extends ExecutorResponse>
        extends AbstractService implements Executor<rq, rs> {

    @Override
    public rs execute(rq executorParam) throws AbstractAppException {
        throw new NotImplementedException();
    }

    @Override
    public rs execute() throws AbstractAppException {
        throw new NotImplementedException();
    }
}
