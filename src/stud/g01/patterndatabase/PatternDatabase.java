package stud.g01.patterndatabase;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class PatternDatabase {
    public int[] target = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
    public HashMap<String, int[]>[] puzzle_15 = (HashMap<String, int[]>[]) new HashMap<?,?>[4];
    public HashMap<String, int[]> getHashMap(int flag, ArrayList<Key> keys) {
        HashMap<String, int[]> map = new HashMap<>();
        for(int i : target) {
            int[] a = new int[4];
            a[0] = i;
            for( int j : target) {
                if(i == j)continue;
                a[1] = j;
                for(int m : target) {
                    if(m == i || m == j)continue;
                    a[2] = m;
                    for(int n:target) {
                        if(n==i||n==j||n==m)continue;
                        a[3]=n;
                        Key key = new Key(a);
                        keys.add(key);
                        int[] distance = new int[4];
                        distance[0]=getDataHamming(flag, a);//获取第flag个模式的汉明距离，a为该模式的状态
                        distance[1]=getDataManhattan(flag, a);
                        distance[2]=getDataZero(flag, a);
                        distance[3]=getDataDijkstra(flag, a);
                        map.put(keys.get(keys.size()-1).key, distance);
                    }
                }
            }
        }
        return map;
    }
    public int getDataHamming(int flag, int[] a) {
        int count=0;
        switch(flag) {
            case 0:
                for(int i=0; i<4; i++) {
                    if(a[i]!=target[i])count++;
                }
                break;
            case 1:
                for(int i=0; i<4; i++) {
                    if(a[i]!=target[i+4])count++;
                }
                break;
            case 2:
                for(int i=0; i<4; i++) {
                    if(a[i]!=target[i+8])count++;
                }
                break;
            default:
                for(int i=0; i<4; i++) {
                    if(a[i]!=target[i+12])count++;
                }
                break;
        }
        return count;
    }
    public int getDataManhattan(int flag, int[] a) {
        int count=0;
        switch(flag) {
            case 0:
                for(int i=0; i<4; i++){
                    for(int j=0;j<16;j++){
                        if(a[i]==target[j]&&a[i]!=0)count = count + (j/4)+Math.abs(j%4-i);
                    }
                }
                break;
            case 1:
                for(int i=0; i<4; i++){
                    for(int j=0;j<16;j++){
                        if(a[i]==target[j]&&a[i]!=0)count = count + Math.abs(j/4-1)+Math.abs(j%4-i);
                    }
                }
                break;
            case 2:
                for(int i=0; i<4; i++) {
                    for(int j=0;j<16;j++){
                        if(a[i]==target[j]&&a[i]!=0)count = count + Math.abs(j/4-2)+Math.abs(j%4-i);
                    }
                }
                break;
            default:
                for(int i=0; i<4; i++) {
                    for(int j=0;j<16;j++){
                        if(a[i]==target[j]&&a[i]!=0)count = count + Math.abs(j/4-3)+Math.abs(j%4-i);
                    }
                }
                break;
        }
        return count;
    }
    public int getDataZero(int flag, int[] a) {
        int count=0;
        switch(flag) {
            case 0:
                for(int i=0; i<4; i++) {
                    if(a[i]==0) {count=6-i;break;}
                }
                break;
            case 1:
                for(int i=0; i<4; i++) {
                    if(a[i]==0) {count=5-i;break;}
                }
                break;
            case 2:
                for(int i=0; i<4; i++) {
                    if(a[i]==0) {count=4-i;break;}
                }
                break;
            default:
                for(int i=0; i<4; i++) {
                    if(a[i]==0) {count=3-i;break;}
                }
                break;
        }
        return count;
    }
    public int getDataDijkstra(int flag, int[] a) {
        int count = 0;
        switch(flag) {
            case 0:
                count = dijkstraDistance(a, Arrays.copyOfRange(target, 0, 4));
                break;
            case 1:
                count = dijkstraDistance(a, Arrays.copyOfRange(target, 4, 8));
                break;
            case 2:
                count = dijkstraDistance(a, Arrays.copyOfRange(target, 8, 12));
                break;
            default:
                count = dijkstraDistance(a, Arrays.copyOfRange(target, 12, 16));
                break;
        }
        return count;
    }

    private int dijkstraDistance(int[] a, int[] targetSubset) {
        int[] distance = new int[targetSubset.length];
        Arrays.fill(distance, Integer.MAX_VALUE);

        boolean[] visited = new boolean[targetSubset.length];
        int startIndex = getIndex(a, targetSubset);

        if (startIndex >= 0 && startIndex < targetSubset.length) {
            distance[startIndex] = 0;

            for (int i = 0; i < targetSubset.length; i++) {
                int u = getMinDistanceVertex(distance, visited);
                visited[u] = true;

                for (int v = 0; v < targetSubset.length; v++) {
                    if (!visited[v] && targetSubset[v] != 0 &&
                            distance[u] != Integer.MAX_VALUE && distance[u] + 1 < distance[v]) {
                        distance[v] = distance[u] + 1;
                    }
                }
            }

            int totalDistance = 0;
            for (int i = 0; i < targetSubset.length; i++) {
                if (a[i] != 0) {
                    totalDistance += distance[i];
                }
            }

            return totalDistance;
        } else {
            // 处理索引越界的情况，返回一个合适的错误值或者抛出异常
            return 0;
        }
    }


    private int getIndex(int[] state, int[] subset) {
        int index = 0;
        for (int i = 0; i < state.length; i++) {
            if (Arrays.binarySearch(subset, state[i]) >= 0) {
                index = index * 4 + state[i];
            }
        }
        return index;
    }

    private int getMinDistanceVertex(int[] distance, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = 0;

        for (int v = 0; v < distance.length; v++) {
            if (!visited[v] && distance[v] < min) {
                min = distance[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    public PatternDatabase(ArrayList<Key> keys) {
        for (int i = 0; i < 4; i++) {
            puzzle_15[i] = getHashMap(i, keys);
        }
    }
}