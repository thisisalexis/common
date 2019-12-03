package cl.thisisalexis.common.core.pattern.builder;

@Deprecated
public interface Builder<Builder, Build> {
    Builder getInstance();
    Build build();
}
