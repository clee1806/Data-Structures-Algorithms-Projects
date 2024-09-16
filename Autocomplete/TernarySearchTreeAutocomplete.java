package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        for (CharSequence term: terms) {
            overallRoot = addAll(overallRoot, term, 0);
        }
    }
    private Node addAll(Node overallRoot, CharSequence term, int index) {
        char c = term.charAt(index);
        if (overallRoot == null) {
            overallRoot = new Node(c);
        }
        if (c < overallRoot.data) {
            overallRoot.left = addAll(overallRoot.left, term, index);
        } else if (c > overallRoot.data) {
            overallRoot.right = addAll(overallRoot.right, term, index);
        } else {
            if (index < term.length() - 1) {
                overallRoot.mid = addAll(overallRoot.mid, term, index+1);
            } else {
                overallRoot.isTerm = true;
            }
        }
        return overallRoot;
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return result;
        }
        Node curr = get(overallRoot, prefix, 0);
        if (curr == null) {
            return result;
        }
        if (curr.isTerm) {
            result.add(prefix);
        }
        collect(curr.mid, new StringBuilder(prefix), result);
        return result;
    }

    private Node get(Node overallRoot, CharSequence prefix, int index) {
        if (prefix == null || prefix.length() == 0) {
            return null;
        }
        char c = prefix.charAt(index);
        if (c < overallRoot.data) {
            return get(overallRoot.left, prefix, index);
        } else if (c > overallRoot.data) {
            return get(overallRoot.right, prefix, index);
        } else if (index + 1 < prefix.length()) {
            return get(overallRoot.mid, prefix, index+1);
        } else {
            return overallRoot;
        }
    }
    private void collect(Node overallRoot, StringBuilder prefix, List<CharSequence> result) {
        if (overallRoot == null) {
            return;
        }
        collect(overallRoot.left, prefix, result);
        if (overallRoot.isTerm) {
            result.add(prefix.toString() + overallRoot.data);
        }
        collect(overallRoot.mid, prefix.append(overallRoot.data), result);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(overallRoot.right, prefix, result);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
