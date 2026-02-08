package ReportingComponent.entity;

import java.util.List;

public class ListResult<T> {
    private final List<T> items;

    public ListResult(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public int getTotal() {
        return items == null ? 0 : items.size();
    }
}