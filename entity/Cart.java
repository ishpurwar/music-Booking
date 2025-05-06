package entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<ResourceSelection> selections;

    public Cart() {
        this.selections = new ArrayList<>();
    }

    public void addSelection(ResourceSelection selection) {
        selections.add(selection);
    }

    public List<ResourceSelection> getSelections() {
        return selections;
    }

    public void clearCart() {
        selections.clear();
    }
}
