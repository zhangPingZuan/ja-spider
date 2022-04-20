package io.zpz.tool.engine.core;


import java.lang.reflect.Type;

public class ResolvableType {

    /**
     * The underlying Java type being managed.
     */
    private final Type type;

    private Class<?> resolved;

    public static ResolvableType forInstance(Object instance) {
        assert instance != null;
//        if (instance instanceof ResolvableTypeProvider) {
//            ResolvableType type = ((ResolvableTypeProvider) instance).getResolvableType();
//            if (type != null) {
//                return type;
//            }
//        }
        return ResolvableType.forClass(instance.getClass());
    }


    public static ResolvableType forClass(Class<?> clazz) {
        return new ResolvableType(clazz);
    }

    /**
     * Private constructor used to create a new {@link org.springframework.core.ResolvableType} on a {@link Class} basis.
     * Avoids all {@code instanceof} checks in order to create a straight {@link Class} wrapper.
     *
     * @since 4.2
     */
    private ResolvableType(Class<?> clazz) {
        this.resolved = (clazz != null ? clazz : Object.class);
        this.type = this.resolved;

    }

    public boolean isAssignableFrom(ResolvableType resolvableType) {
        return this.type.getClass().isAssignableFrom(resolvableType.getClass());
    }

//    public static void main(String[] args) {
//        ResolvableType resolvableType = ResolvableType.forInstance(new MainSpider());
//        System.out.println(resolvableType.type.getTypeName());
//    }


}


