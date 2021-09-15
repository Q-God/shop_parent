package com.gmall.fuction;

import java.util.Objects;
import java.util.function.Function;

/**
 * @version v1.0
 * @ClassName TriFunction<T, U, V, R>
 * @Description 赋3个值
 * @Author Q
 */
public interface TriFunction<T, U, V, R> {

    /**
     * @param t
     * @param u
     * @param v
     * @return
     */
    R apply(T t, U u, V v);

    /**
     * @param after
     * @param <W>
     * @return
     */
    default <W> TriFunction<T, U, V, W> andThen(Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (T t, U u, V v) -> after.apply(apply(t, u, v));
    }
}
