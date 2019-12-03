package cl.thisisalexis.common.core.workflow;

/**
 * An empty representation of an ExecutorRequest interface.
 * This is meant to be use in Executor implementations when an Executor service does not really need a ExecutorRequest or filter,
 * but it still has to be declared since it is required to implement Executor interface to declare an Executor request param.
 */
public class EmptyExecutorRequest implements ExecutorRequest {
}
