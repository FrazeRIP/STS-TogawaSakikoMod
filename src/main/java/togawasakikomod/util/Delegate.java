package togawasakikomod.util;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Delegate<T> {
    private final List<Consumer<T>> consumers = new ArrayList<>();

    // 添加方法
    public void add(Consumer<T> consumer) {
        consumers.add(consumer);
    }

    // 移除方法
    public void remove(Consumer<T> consumer) {
        consumers.remove(consumer);
    }

    // 调用所有方法
    public void invoke(T arg) {
        consumers.forEach(consumer -> consumer.accept(arg));
    }
}