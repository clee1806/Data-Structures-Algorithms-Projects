package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        for (CharSequence term: terms) {
            elements.add(term);
        }
        Collections.sort(elements, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return result;
        }
        int start = Collections.binarySearch(elements, prefix, CharSequence::compare);
        if (start < 0) {
            start = -(start+1);
        }
        List<CharSequence> elements2 = elements.subList(start, elements.size());
            for (int i = 0; i < elements2.size(); i++) {
                if (Autocomplete.isPrefixOf(prefix, elements2.get(i))) {
                    result.add(elements2.get(i));
                } else {
                    return result;
                }
            }
        return result;
    }
}
