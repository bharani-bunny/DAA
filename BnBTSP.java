/*
TRAVELLING SALES PERSON USING BRANCH AND BOUNND
-----------------------------------------------
*/

import java.util.Scanner;
import java.util.Arrays;

public class BnBTSP {
	public static int n;
        static int final_path[];
        static boolean visited[];
        static int final_cost=Integer.MAX_VALUE;
        
        BnBTSP(int n){
                BnBTSP.final_path = new int[n + 1];
                BnBTSP.visited = new boolean[n];
        }
        
        static void copyToFinal(int curr_path[]) {
                for (int i=0; i<n; i++)
                        final_path[i]=curr_path[i];
                final_path[n]=curr_path[0];
        }

        static int firstMin(int adj[][], int i) {
                int min=Integer.MAX_VALUE;
                for (int k=0; k<n; k++)
                        if (adj[i][k]<min && i!=k)
                                min=adj[i][k];
                return min;
        }

        static int secondMin(int adj[][],int i) {
                int first=Integer.MAX_VALUE,second=Integer.MAX_VALUE;
                for (int j=0; j<n; j++) {
                        if (i == j)
                                continue;
                        if (adj[i][j] <= first) {
                                second=first;
                                first=adj[i][j];
                        } else if(adj[i][j]<=second && adj[i][j]!=first) {
                                second=adj[i][j];
                        }
                }
                return second;
        }

        static void TSPRec(int adj[][],int curr_bound,int curr_weight,int level,int curr_path[]) {
                if(level==n) {
                        if(adj[curr_path[level-1]][curr_path[0]]!=0) {
                                int curr_cost=curr_weight+adj[curr_path[level-1]][curr_path[0]];
                                if (curr_cost<final_cost) {
                                        copyToFinal(curr_path);
                                        final_cost=curr_cost;
                                }
                        }
                        return;
                }

                for (int i=0; i<n; i++) {
                        if (adj[curr_path[level-1]][i]!=0 && visited[i]==false) {
                                int temp=curr_bound;
                                curr_weight+=adj[curr_path[level-1]][i];
                                if(level==1) {
                                        curr_bound-=((firstMin(adj, curr_path[level-1])+firstMin(adj,i))/2);
                                }
                                else
                                        curr_bound-=((secondMin(adj, curr_path[level-1])+firstMin(adj,i))/2);
                                if(curr_bound+curr_weight<final_cost) {
                                        curr_path[level]=i;
                                        visited[i]=true;
                                        TSPRec(adj,curr_bound,curr_weight,level+1,curr_path);
                                }
                                curr_weight-=adj[curr_path[level-1]][i];
                                curr_bound=temp;
                                Arrays.fill(visited,false);
                                for (int j=0; j<=level-1; j++)
                                        visited[curr_path[j]] = true;
                        }
                }
        }

        static void TSP(int adj[][]) {
                int curr_path[]=new int[n+1];
                int curr_bound = 0;
                Arrays.fill(curr_path,-1);
                Arrays.fill(visited,false);
                for (int i=0; i<n; i++) {
                        curr_bound+=(firstMin(adj,i)+secondMin(adj,i));
                }
                curr_bound=(curr_bound==1)?curr_bound/2+1:curr_bound/2;
                visited[0] = true;
                curr_path[0] = 0;
                TSPRec(adj,curr_bound,0,1,curr_path);
        }

        public static void main(String[] args)  {
                Scanner sc=new Scanner(System.in);
                int i,j;
                System.out.print("Enter Number of cities: ");
                n=sc.nextInt();
                BnBTSP g = new BnBTSP(n);
                int adj[][]=new int[n+1][n+1];
                System.out.println("Enter the adjacency matrix:");
                for(i=0;i<n;i++)
                        for(j=0;j<n;j++)
                                adj[i][j]=sc.nextInt();
                g.TSP(adj);
                System.out.println("Minimum cost:"+ final_cost);
                System.out.print("The Path is:");
                for (i=0; i<n; i++)  {
                        System.out.print(" "+(final_path[i]+1));
                }
                System.out.println(" 1");
        }
}

/*
OUTPUT
------
Enter Number of cities: 5
Enter the adjacency matrix:
0 20 30 10 11
15 0 16 4 2
3 5 0 2 4
19 6 18 0 3
16 4 7 16 0
Minimum cost:28
The Path is: 1 4 2 5 3 1
*/