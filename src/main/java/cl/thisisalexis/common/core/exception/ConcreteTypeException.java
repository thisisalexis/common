package cl.thisisalexis.common.core.exception;

/**
 * A concrete implementation of TypeException interface to describe different types of Exceptions in the application
 *
 * @author Alexis Bravo
 */
public enum ConcreteTypeException implements TypeException {

    ENTRY_DATA_VALIDATION(Integer.valueOf(400), "Validation", "An error validating entry data"),
    PERSISTENCE_VALIDATION(Integer.valueOf(400), "Persistence Validation", "Database Constraint Validation"),
    EXTERNAL_RESOURCE_ERROR(Integer.valueOf(501), "External resource problem", "An error produced by an external resource"),
    AUTHORIZATION(Integer.valueOf(403), "Authorization Error", "Unauthorized to access resource"),
    INTERNAL_ERROR(Integer.valueOf(500), "Internal error", "An unexpected internal error in the app");

    private Integer identifier;
    private String name;
    private String description;

    ConcreteTypeException(Integer identifier, String name, String description) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
    }

    @Override
    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
