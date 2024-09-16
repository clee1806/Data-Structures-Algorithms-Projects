package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /**
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
        add(null, 0);
    }

    /**
     * Constructs an instance containing all the given elements and their priority values.
     *
     * @param elementsAndPriorities each element and its corresponding priority.
     */
    public OptimizedHeapMinPQ(Map<E, Double> elementsAndPriorities) {
        elements = new ArrayList<>(elementsAndPriorities.size());
        elementsToIndex = new HashMap<>(elementsAndPriorities.size());
        // TODO: Replace with your code
        add(null, 0);
        for (Map.Entry<E, Double> entry : elementsAndPriorities.entrySet()) {
            E element = entry.getKey();
            double priority = entry.getValue();
            if (!elementsToIndex.containsKey(element)) {
                elementsToIndex.put(element, size());
            }
            add(element, priority);
        }
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        // TODO: Replace with your code
        elements.add(new PriorityNode<>(element, priority));
        elementsToIndex.put(element, size());
        swim(size());
    }

    @Override
    public boolean contains(E element) {
        // TODO: Replace with your code
        return elements.contains(new PriorityNode<>(element, 0));
    }

    @Override
    public double getPriority(E element) {
        // TODO: Replace with your code
        return elements.get(elementsToIndex.get(element)).getPriority();
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        return elements.get(1).getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        E element = peekMin();
        swap(1, size());
        elementsToIndex.remove(element);
        elements.remove(size());
        sink(1);
        return element;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        // TODO: Replace with your code
        if (elementsToIndex.containsKey(element)) {
            PriorityNode<E> change = elements.get(elementsToIndex.get(element));
            double oldP = change.getPriority();
            change.setPriority(priority);
            if (oldP > priority) {
                swim(elementsToIndex.get(element));
            } else {
                sink(elementsToIndex.get(element));
            }
        }
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        return elements.size()-1;
    }

    private void swim(int i) {
        while (i > 1 && greater(i/2, i)) {
            swap(i/2, i);
            i = i/2;
        }
    }

    private void sink(int i) {
        while (i*2 <= size()) {
            int j = i*2;
            if (j < size() && greater(j, j+1)) {
                j++;
            }
            if (!greater(i, j)) {
                break;
            }
            swap(i, j);
            i = j;
        }
    }

    private boolean greater(int i, int j) {
        return elements.get(i).getPriority() > elements.get(j).getPriority();
    }

    private void swap(int i, int j) {
        PriorityNode<E> iNode = elements.get(i);
        PriorityNode<E> jNode = elements.get(j);
        elementsToIndex.put(iNode.getElement(), j);
        elementsToIndex.put(jNode.getElement(), i);
        elements.set(i, jNode);
        elements.set(j, iNode);
    }
}
