package cl.thisisalexis.common.core.workflow;

import cl.thisisalexis.common.core.exception.AbstractAppException;
import cl.thisisalexis.common.core.exception.ConcreteTypeException;
import cl.thisisalexis.common.core.exception.TypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A class which implements the strategy pattern to handle execution of beans (services) that
 * handle API requests. The main idea behind this workflow executor is that all of the public API related services
 * (which implement the Executor interface) be handled by a standard execution process represented by this controller,
 * which warranty an uniform way to handle all exceptions, responses and common actions between services that handles
 * API logic
 *
 * @author Alexis Bravo
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
// TODO Cambiar esto a SINGLETON, en todo caso importar un WorkflowWrapper con la info del Workflow
public class ApiWorkflowExecutor {

    private static Logger logger = LoggerFactory.getLogger(ApiWorkflowExecutor.class); // TODO cambiar esto porque siempre hace log en la misma clase
    @Autowired
    private ApplicationContext applicationContext;
    private Class<? extends Executor> executor;
    private ExecutorRequest executorRequest;
    private List<Validator> validators;
    private Errors errors; //TODO Implement this

    public static ApiWorkflowExecutor getInstance() {
        return new ApiWorkflowExecutor();
    }

    public Class<? extends Executor> getExecutor() {
        return executor;
    }

    public void setExecutor(Class<? extends Executor> executor) {
        this.executor = executor;
    }

    public void setValidators(Validator ...addedValidators) {
        if(null == this.validators) {
            this.validators = new ArrayList();
        }
        this.validators.addAll(Arrays.asList(addedValidators));
    }

    public ExecutorRequest getExecutorRequest() {
        return executorRequest;
    }

    public void setExecutorRequest(ExecutorRequest executorRequest) {
        this.executorRequest = executorRequest;
    }

    public ResponseEntity<ExecutorResponse> execute() {
        try {
            Executor executor = applicationContext.getBean(this.executor);
            ExecutorResponse executorResponse;
            if (null == this.executorRequest) {
                executorResponse = executor.execute();
            } else {
                executorResponse = executor.execute(this.executorRequest);
            }

            log(executorResponse);
            return ResponseEntity.ok(executorResponse);
        } catch (ConstraintViolationException e) {
            return handleConstraintViolationException(e);
        } catch (ValidationException e) {
            return handledValidationException(e);
        } catch (AbstractAppException e) {
            return handledAbstractException(e);
        } catch (Exception e) {
            return handleInternalServerErrorException(e);
        }
    }

    private ResponseEntity<ExecutorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        logWithError(e);
        String message = "";
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            message += constraintViolation.getMessageTemplate() !=
                    null ? constraintViolation.getMessageTemplate() : "Error in " + constraintViolation.getPropertyPath() + ". ";
        }

        ErrorExecutorResponse errorApiResponse = getErrorApiResponse(message, ConcreteTypeException.PERSISTENCE_VALIDATION);
        HttpStatus httpStatus = getErrorResponseHttpStatus(ConcreteTypeException.PERSISTENCE_VALIDATION);

        return ResponseEntity.status(httpStatus).body(errorApiResponse);
    }

    private ResponseEntity<ExecutorResponse> handledValidationException(ValidationException e) {
        logWithError(e);
        ErrorExecutorResponse errorApiResponse = getErrorApiResponse(e.getMessage(), ConcreteTypeException.ENTRY_DATA_VALIDATION);
        HttpStatus httpStatus = getErrorResponseHttpStatus(ConcreteTypeException.ENTRY_DATA_VALIDATION);

        return ResponseEntity.status(httpStatus).body(errorApiResponse);
    }

    private ResponseEntity<ExecutorResponse> handledAbstractException(AbstractAppException e) {
        logWithError(e);

        ErrorExecutorResponse errorApiResponse = getErrorApiResponse(e.getMessage(), e.getTypeException());
        HttpStatus httpStatus = getErrorResponseHttpStatus(e.getTypeException());

        return ResponseEntity.status(httpStatus).body(errorApiResponse);
    }

    private HttpStatus getErrorResponseHttpStatus(TypeException typeException) {
        HttpStatus httpStatus;
        if (ConcreteTypeException.ENTRY_DATA_VALIDATION.equals(typeException)) {
            httpStatus = HttpStatus.OK;
        } else if (ConcreteTypeException.AUTHORIZATION.equals(typeException)) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return httpStatus;
    }

    private ErrorExecutorResponse getErrorApiResponse(String message, TypeException typeException) {
        ErrorExecutorResponse errorApiResponse = new ErrorExecutorResponse();
        errorApiResponse.setMessage(message);
        errorApiResponse.setTypeException(typeException);
        return errorApiResponse;
    }

    private ResponseEntity<ExecutorResponse> handleInternalServerErrorException(Exception e) {
        logWithError(e);
        ErrorExecutorResponse errorApiResponse = new ErrorExecutorResponse();
        errorApiResponse.setMessage("Unexpected error");
        errorApiResponse.setTypeException(ConcreteTypeException.INTERNAL_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorApiResponse);
    }

    public void log(ExecutorResponse executorResponse) {
        logger.info("API Service " + this.executor + " executed successfully");
        logger.info("Service Request", this.executorRequest);
        logger.info("Service Response", executorResponse);
    }

    public void logWithError(Exception e) {
        logger.warn("API Service " + this.executor + " executed with error", e);
        logger.warn("Service Request", this.executorRequest);
    }

    private void validate() {
        for (Validator validator : this.validators) {
            validator.validate(getExecutorRequest(), errors);
        }
    }

}

