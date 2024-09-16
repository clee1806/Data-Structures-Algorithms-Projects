package seamfinding;

import graphs.Edge;
import seamfinding.energy.EnergyFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {
    private double[][] graph;

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        // TODO: Replace with your code
        List<Integer> result = new ArrayList<>();
        graph = new double[picture.width()][picture.height()];
        for (int y = 0; y < picture.height(); y++) {
            graph[0][y] = f.apply(picture, 0, y);
        }
        double min = Double.MAX_VALUE;
        for (int c = 1; c < picture.width(); c++){
            for (int r = 0; r < picture.height(); r++) {
                for (int z = r-1; z <= r+1; z++) {
                    if (z >= 0 && z < picture.height()) {
                        if (graph[c-1][z] <= min) {
                            min = graph[c-1][z];
                        }
                    }
                }
                graph[c][r] = f.apply(picture, c, r) + min;
                min = Double.MAX_VALUE;
            }
        }
        int yVal = 0;
        for (int y = 0; y < picture.height(); y++) {
            if (graph[picture.width()-1][y] < min) {
                yVal = y;
                min = graph[picture.width()-1][y];
            }
        }
        result.add(yVal);
        min = Double.MAX_VALUE;
        int temp = 0;
        for (int x = picture.width()-2; x >= 0; x--) {
            for (int z = yVal-1; z <= yVal+1; z++) {
                if (z >= 0 && z < picture.height()) {
                    if (graph[x][z] <= min) {
                        temp = z;
                        min =graph[x][z];
                    }
                }
            }
            yVal = temp;
            min = Double.MAX_VALUE;
            result.add(yVal);
        }
        Collections.reverse(result);
        return result;
    }
}
