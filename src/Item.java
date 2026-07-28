import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Item extends JComponent {

    String name;
    List<Item[]> recipe = new ArrayList<>();

    public Item(String _name) {
        this.name = _name;
    }

    public Item(String _name,List<Item[]> recipe) {
        this.name = _name;
        this.recipe = recipe;
    }

}
