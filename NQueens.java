/*
NQUEENS
-------
*/
import java.util.*;
class NQueens {
    static List < List < Integer >> result = new ArrayList < List < Integer >> ();
    static boolean[] cols, leftDiagonal, rightDiagonal;
    static List < List < Integer >> nQueen(int n) {
        cols = new boolean[n];
        leftDiagonal = new boolean[2 * n];
        rightDiagonal = new boolean[2 * n];
        ArrayList < Integer > temp = new ArrayList < > ();
        for (int i = 0; i < n; i++)
            temp.add(0);
        solveNQUtil(result, n, 0, temp);
        return result;
    }
    private static void solveNQUtil(List < List < Integer >> result, int n, int row, List < Integer > comb) {
        if (row == n) {
            result.add(new ArrayList < > (comb));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (cols[col] || leftDiagonal[row + col] || rightDiagonal[row - col + n])
                continue;
            cols[col] = leftDiagonal[row + col] = rightDiagonal[row - col + n] = true;
            comb.set(col, row + 1);
            solveNQUtil(result, n, row + 1, comb);
            cols[col] = leftDiagonal[row + col] = rightDiagonal[row - col + n] = false;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n : ");
        int n = sc.nextInt();
        List < List < Integer >> res = nQueen(n);
        System.out.println("Number of solutions : " + res.size());
        for (List i: res)
            System.out.println(i);
    }
}

/*
OUTPUT
------
Enter n : 4
Number of solutions : 2
[3, 1, 4, 2]
[2, 4, 1, 3]
*/
