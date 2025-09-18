import java.util.HashMap;

public class HashMapLayer<T> extends Layer<T> {
    HashMap<String, T> hashmap;



    public HashMapLayer(boolean is_active, String name) {
        super(is_active, name);
    }
}
