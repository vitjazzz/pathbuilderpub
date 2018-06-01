package com.vitja.study.degree.service.bp.impl;

import com.vitja.study.degree.service.bp.TravellingSalesmanAlgorithm;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class TravellingSalesmanAlgorithmImpl implements TravellingSalesmanAlgorithm {
    private int N, maxNodesNumber;
    private double[][] distance;
    private List<TemporaryPath> possibleRoutes = new ArrayList<>();

    @Override
    public List<Integer> getBestPath(double[][] distanceMatrix, int maxNodes) {
        possibleRoutes.clear();
        distance = distanceMatrix;
        N = distance.length;
        maxNodesNumber = N < maxNodes ? N : maxNodes;

        if (maxNodesNumber <= 0) throw new IllegalStateException("N cannot be <= 0");
        if (N != distance[0].length) throw new IllegalStateException("Matrix must be square (n x n)");

        if(N == 1 || maxNodesNumber == 1){
            return Collections.singletonList(0);
        }

        if(N == 2 && maxNodesNumber == 2){
            return Arrays.asList(0, 1);
        }

        for(int i = 0; i < N; i++){
            TemporaryPath bestPathForStartNode = getBestPathForStartNode(i);
            possibleRoutes.add(bestPathForStartNode);
        }
        Optional<TemporaryPath> result = possibleRoutes.stream()
                .min(Comparator.comparing(TemporaryPath::getDistance));
        return result.isPresent() ? result.get().getPath() : new ArrayList<>();
    }

    private TemporaryPath getBestPathForStartNode(int start){
        List<Integer> tour = new ArrayList<>();
        double minTourCost = Double.POSITIVE_INFINITY;

        List<Integer> possibleEndStates = combinations(maxNodesNumber, N);
        int tempEndState = possibleEndStates.get(0);
        Double[][] memo = new Double[N][1 << N];

        // Add all outgoing edges from the starting node to memo table.
        for (int end = 0; end < N; end++) {
            if (end == start) continue;
            memo[end][(1 << start) | (1 << end)] = distance[start][end];
        }

        for (int r = 3; r <= maxNodesNumber; r++) {
            for (int subset : combinations(r, N)) {
                if (notIn(start, subset)) continue;
                for (int next = 0; next < N; next++) {
                    if (next == start || notIn(next, subset)) continue;
                    int subsetWithoutNext = subset ^ (1 << next);
                    double minDist = Double.POSITIVE_INFINITY;
                    for (int end = 0; end < N; end++) {
                        if (end == start || end == next || notIn(end, subset)) continue;
                        double newDistance = memo[end][subsetWithoutNext] + distance[end][next];
                        if (newDistance < minDist) {
                            minDist = newDistance;
                        }
                    }
                    memo[next][subset] = minDist;
                }
            }
        }

        // Connect tour back to starting node and minimize cost.
        for (int i = 0; i < N; i++) {
            if (i == start) continue;
            for (int possibleEndState : possibleEndStates)
                try {
                    double tourCost = memo[i][possibleEndState];
                    if (tourCost < minTourCost) {
                        minTourCost = tourCost;
                        tempEndState = possibleEndState;
                    }
                } catch (Exception ignored) {
                }

        }

        int lastIndex = start;
        int state = tempEndState;

        // Reconstruct TSP path from memo table.
        for (int i = 1; i < maxNodesNumber; i++) {

            int index = -1;
            for (int j = 0; j < N; j++) {
                if (j == start || notIn(j, state)) continue;
                if (index == -1) index = j;
                double prevDist = lastIndex == start ? memo[index][state] : memo[index][state] + distance[index][lastIndex];
                double newDist  = lastIndex == start ? memo[j][state] : memo[j][state] + distance[j][lastIndex];
                if (newDist < prevDist) {
                    index = j;
                }
            }

            tour.add(index);
            state = state ^ (1 << index);
            lastIndex = index;
        }
        tour.add(start);
        Collections.reverse(tour);
        return new TemporaryPath(minTourCost, tour);
    }

    private static boolean notIn(int elem, int subset) {
        return ((1 << elem) & subset) == 0;
    }

    // This method generates all bit sets of size n where r bits
    // are set to one. The result is returned as a list of integer masks.
    private static List<Integer> combinations(int r, int n) {
        List<Integer> subsets = new ArrayList<>();
        combinations(0, 0, r, n, subsets);
        return subsets;
    }

    // To find all the combinations of size r we need to recurse until we have
    // selected r elements (aka r = 0), otherwise if r != 0 then we still need to select
    // an element which is found after the position of our last selected element
    private static void combinations(int set, int at, int r, int n, List<Integer> subsets) {

        // Return early if there are more elements left to select than what is available.
        int elementsLeftToPick = n - at;
        if (elementsLeftToPick < r) return;

        // We selected 'r' elements so we found a valid subset!
        if (r == 0) {
            subsets.add(set);
        } else {
            for (int i = at; i < n; i++) {
                // Try including this element
                set |= 1 << i;

                combinations(set, i + 1, r - 1, n, subsets);

                // Backtrack and try the instance where we did not include this element
                set &= ~(1 << i);
            }
        }
    }
}
