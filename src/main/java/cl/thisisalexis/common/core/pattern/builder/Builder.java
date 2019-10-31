package cl.thisisalexis.common.core.pattern.builder;

public interface Builder<Builder, Build> {
    Builder getInstance();
    Build build();
}
