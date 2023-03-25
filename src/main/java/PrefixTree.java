import java.util.*;

public class PrefixTree <T> {
    private final List<PrefixTree<T>> children;
    private char value;
    private T info;

    public PrefixTree () {
        children = new LinkedList<>();
    }

    public void addString(String string, T info) {
        if (string == null) {
            throw new NullPointerException();
        }
        if (string.equals("")) {
            this.info = info;
            return;
        }

        PrefixTree<T> next = getChild(string.charAt(0));
        if (next == null) {
            next = new PrefixTree<>();
            next.value = string.charAt(0);
            children.add(next);
        }
        next.addString(string.substring(1), info);
    }

    public SortedMap<String, T> getInfoByPrefix(String prefix) {
        SortedMap<String, T> result = new TreeMap<>();
        getAllInfo(prefix, "", result);
        return result;
    }

    private void getAllInfo(String prefix, String string, SortedMap<String, T> data) {
        if (Objects.equals(prefix, "")) {
            for (PrefixTree<T> child : children) {
                if (child.info != null) {
                    data.put(string + child.value, child.info);
                }
                child.getAllInfo(prefix, string + child.value, data);
            }
        } else {
            PrefixTree<T> child = getChild(prefix.charAt(0));
            if (child != null) {
                if (prefix.length() == 1 && child.info != null) {
                    data.put(string + child.value, child.info);
                }
                child.getAllInfo(prefix.substring(1), string + child.value, data);
            }
        }
    }

    private PrefixTree<T> getChild(Character value) {
        PrefixTree<T> next = null;
        for (PrefixTree<T> child : children) {
            if (child.value == value) {
                next = child;
                break;
            }
        }
        return next;
    }
}
