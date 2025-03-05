import java.util.Arrays;
import java.util.Scanner;

public class b0 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int m = input.nextInt();
        int[][] rows = new int[m][2];
        int[] vertexClass = new int[n];// class1: 0 class2: 1 unclassifed: -1 unseened: -2
        Arrays.fill(vertexClass, -2);
        for (int i = 0; i < m; i++) {
            rows[i][0] = input.nextInt();
            rows[i][1] = input.nextInt();
            if (rows[i][1] < rows[i][0]) {
                int temp = rows[i][1];
                rows[i][1] = rows[i][0];
                rows[i][0] = temp;
            }
        }
        
        boolean confliction = false;
        for (int i = 0; i < rows.length; i++) {

            boolean addedCore = false;
            for (int j = 0; j < rows.length; j++) {
                /*
                 * if graph is ok evrey connected vertex will have diffrent class for eg.
                 * we have 0 1 // 2 3 // 1 2 if we color like this 0:b 1:r 2:b 3:r then 1 and 2
                 * are in same class
                 * b stands for blue 0// r stands for red 1
                 * to prevent conflict in each round we only color one pair of undefineds
                 */
                int vertex1 = rows[j][0];// -1 for to index
                int vertex2 = rows[j][1];
                vertexClass[vertex1] = (vertexClass[vertex1] == -2) ? -1 : vertexClass[vertex1];
                vertexClass[vertex2] = (vertexClass[vertex2] == -2) ? -1 : vertexClass[vertex2];// if unseened now is
                                                                                                // seened

                if (vertexClass[vertex1] == -1 && vertexClass[vertex2] == -1) {
                    if (!addedCore) {
                        vertexClass[vertex1] = 0;
                        vertexClass[vertex2] = 1;

                        addedCore = true;
                    }
                } else if (vertexClass[vertex1] == -1) {
                    vertexClass[vertex1] = (vertexClass[vertex2] == 0) ? 1 : 0;// v1 will be opposite class with v2
                } else if (vertexClass[vertex2] == -1) {
                    vertexClass[vertex2] = (vertexClass[vertex1] == 0) ? 1 : 0;
                } else if (vertexClass[vertex1] == vertexClass[vertex2]) {
                    //System.out.printf("%d and %d has clas");
                    confliction = true;
                    break;
                }
            }

            if (!addedCore || confliction) {
                break;
            }
        }

        if(confliction)
            System.out.println("No");
        else
            System.out.println("Yes");
        input.close();
    }
}
