import java.util.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.ArrayList;


public class EightPuzzle {

    static class node {

        public List<node> children = new ArrayList<>();
        public int []puzzle = new int [9];
        public int x = 0;

        public node (int []p)
        {
            SetPuzzle(p);
        }

        public void SetPuzzle (int[]p)
        {
            for (int i = 0;i<9;i++)
            {
                puzzle[i] = p[i];
            }
        }

        public void expandNodeDFS()
        {
            for (int i = 0; i < 9; i++ )
            {
                if (puzzle[i] == 0)
                    x = i;
            }

            MoveToRight(puzzle,x);
            MoveToDown(puzzle,x);
            MoveToLeft(puzzle,x);
            MoveToUp(puzzle,x);
        }

        public void expandNodeBFS()
        {
            for (int i = 0; i < 9; i++ )
            {
                if (puzzle[i] == 0)
                    x = i;
            }

            MoveToUp(puzzle,x);
            MoveToLeft(puzzle,x);
            MoveToDown(puzzle,x);
            MoveToRight(puzzle,x);
        }


        public void MoveToUp(int []p,int i)
        {
            if (i - 3 >=0)
            {
                int[] tempP = new int[9];
                CopyPuzzle(tempP,p);

                int temp = tempP[i-3];
                tempP[i-3] = tempP[i];
                tempP[i] = temp;

                node child = new node(tempP);
                children.add(child);
            }

        }

        public void MoveToDown(int []p,int i)
        {
            if (i + 3 < 9)
            {
                int[] tempP = new int[9];
                CopyPuzzle(tempP,p);

                int temp = tempP[i+3];
                tempP[i+3] = tempP[i];
                tempP[i] = temp;

                node child = new node(tempP);
                children.add(child);
            }


        }

        public void MoveToLeft(int []p,int i)
        {
            if ((i % 3) > 0)
            {
                int[] tempP = new int[9];
                CopyPuzzle(tempP,p);

                int temp = tempP[i-1];
                tempP[i-1] = tempP[i];
                tempP[i] = temp;

                node child = new node(tempP);
                children.add(child);
            }

        }

        public void MoveToRight(int []p,int i)
        {
            if ((i % 3) < (3 - 1))
            {
                int[] tempP = new int[9];
                CopyPuzzle(tempP,p);

                int temp = tempP[i+1];
                tempP[i+1] = tempP[i];
                tempP[i] = temp;

                node child = new node(tempP);
                children.add(child);
            }
        }

        public void CopyPuzzle(int[]a,int[]b)
        {
            for (int i = 0;i<9;i++)
            {
                a[i] = b[i];
            }
        }


        public void printPuzzle ()
        {
            System.out.println();
            int m = 0;
            for (int i = 0; i < 3; i++ )
            {
                for (int j = 0; j < 3; j++ )
                {
                    System.out.print(puzzle[m]+" ");
                    m++;
                }
                System.out.println();
            }
        }

        public boolean isSamePuzzle(int[]p)
        {
            boolean samePuzzle = true;
            for (int i = 0; i < 9; i++ )
            {
                if (puzzle[i] != p[i])
                    samePuzzle = false;
            }
            return samePuzzle;
        }

        public boolean GoalTest()
        {
            boolean isGoal = true;
            int m = puzzle[0];

            for (int i = 1;i<9;i++)
            {
                if(m>puzzle[i])
                    isGoal = false;
                m = puzzle[i];
            }
            return isGoal;
        }
    }

    static class Search {

        public static void BFS (node root)
        {
            Queue<node> frontier = new LinkedList<>();
            Queue<node> explored = new LinkedList<>();

            frontier.add(root);

            while (!frontier.isEmpty())
            {
                node current = frontier.poll();
                explored.add(current);
                current.printPuzzle();

                if (current.GoalTest())
                    return;

                current.expandNodeBFS();

                for (int i = 0; i < current.children.size(); i++)
                {
                    node currentChild = current.children.get(i);

                    if(!containsQueue(frontier,currentChild) & !containsQueue(explored,currentChild))
                        frontier.add(currentChild);
                }
            }
        }

        public static void DFS (node root)
        {
            Stack<node> frontier = new Stack<>();
            Queue<node> explored = new LinkedList<>();

            frontier.push(root);

            while (!frontier.isEmpty())
            {
                node current = frontier.pop();
                explored.add(current);
                current.printPuzzle();

                if (current.GoalTest())
                    return;

                current.expandNodeDFS();

                for (int i = 0; i < current.children.size(); i++)
                {
                    node currentChild = current.children.get(i);

                    if(!containsStack(frontier,currentChild) & !containsQueue(explored,currentChild))
                        frontier.push(currentChild);
                }
            }
        }


        public static boolean containsStack (Stack<node> front,node c)
        {
            boolean contain = false;

            for (int i = 0 ; i < front.size() ; i++)
            {
                if (front.get(i).isSamePuzzle(c.puzzle))
                    contain = true;
            }
            return contain;
        }



        public static boolean containsQueue (Queue<node> front,node c)
        {
            boolean contain = false;
            List<node> temp = (List) front;

            for (int i = 0 ; i < front.size() ; i++)
            {
                if (temp.get(i).isSamePuzzle(c.puzzle))
                    contain = true;
            }
            return contain;
        }




        public static void main (String[]args) {

            int [] puzzle = {1,2,5,3,4,0,6,7,8};

            node initialNode = new node(puzzle);

            DFS(initialNode);

        }
    }
}
