package cl.thisisalexis.common.core.exception;

/**
 * An interface to represent all possible exception types definitios
 *
 * @author Alexis Bravo
 */
public interface TypeException {

    Integer getIdentifier();
    String getName();
    String getDescription();

}
