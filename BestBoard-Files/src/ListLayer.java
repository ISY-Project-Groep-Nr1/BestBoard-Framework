import java.util.ArrayList;

public class ListLayer<T> extends Layer<T> {
    ArrayList<T> list;



    public ListLayer(boolean is_active, String name) {
        super(is_active, name);
    }
}
