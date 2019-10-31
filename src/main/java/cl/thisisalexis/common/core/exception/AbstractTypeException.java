package cl.thisisalexis.common.core.exception;

public enum AbstractTypeException implements TypeException {

    ENTRY_DATA_VALIDATION(Integer.valueOf(400), "Validation", "An error validating entry data"),
    EXTERNAL_RESOURCE_ERROR(Integer.valueOf(501), "External resource problem", "An error produced by an external resource"),
    INTERNAL_ERROR(Integer.valueOf(500), "Internal error", "An unexpected internal error in the app");

    private Integer identifier;
    private String name;
    private String description;

    AbstractTypeException(Integer identifier, String name, String description) {
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
