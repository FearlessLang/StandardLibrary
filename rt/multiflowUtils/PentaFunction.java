package base.multiflowUtils;

public interface PentaFunction<A, B, C, D, E, R> {
    R apply(A acc, B next, C next1, D next2, E next3);
}
