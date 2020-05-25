//mo hinh bang Choco, moi lop thi 1 phong

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Choco {


    int N; //so mon
    int M; // so phong
    int[] d; // d[i] so luong sinh vien dang ki mon i
    int[] c; // c[i] suc chua cua phong i
    int[][] conflict; //conflict[i][j] : mon i va j khong the cung kip

    public static void main(String[] args) {
        Choco app = new Choco();
        app.input("data/input1.txt");
        app.solve();
    }

    private void input(String file) {
        try {
            File f = new File(file);
            Scanner scanner = new Scanner(f);

            N = scanner.nextInt();
            M = scanner.nextInt();

            d = new int[N];
            for (int i = 0; i < N; i++) {
                d[i] = scanner.nextInt();
            }

            c = new int[M];
            for (int i = 0; i < M; i++) {
                c[i] = scanner.nextInt();
            }

            int Q = scanner.nextInt();
            conflict = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    conflict[i][j] = 0;
                }
            }
            for (int i = 0; i < Q; i++) {
                int t1 = scanner.nextInt();
                int t2 = scanner.nextInt();
                conflict[t1][t2] = 1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 
     */
    public void solve() {
        // check xem co mon nao qua nhieu sinh  vien,  khong the thi o bat ki phong nao
        //

        Model model = new Model();
        IntVar[] X = model.intVarArray(N, 1, N);  //X[i]  kip thi cua mon i
        IntVar[] Y = new IntVar[N];  // Y[i] phong thi cua mon i
        for (int i = 0; i < N; i++) {
            // luu cac phong co suc chua c[j] >=  d[i] so sinh vien mon i
            ArrayList<Integer> tlist = new ArrayList<>();
            for (int j = 0; j < M; j++) {
                if (c[j] >= d[i]) {
                    tlist.add(j+1);
                }
            }
            int[] tarr = new int[tlist.size()];
            for (int v = 0; v < tlist.size();  v++) tarr[v] = tlist.get(v);
            Y[i] = model.intVar("y" + i, tarr);
        }

        IntVar Z = model.intVar("z", 1, N);

        //constrain
        //1. not conflict (i, j) conflict => X[i] != X[j]
        for (int i = 0; i < N-1; i++) {
            for (int j = i+1; j < N; j++) {
                if (conflict[i][j] == 1) {
                    model.arithm(X[i], "!=", X[j]).post();
                }
            }
        }

        //2.suc chua >= so sinh vien: da the hien o phan khai bao bien Y[i]

        //3. mon i, j cung kip => khac phong, hoac nguoc lai
        // if X[i] = X[j] => Y[i] != Y[j]
        for (int i = 0; i < N-1; i++) {
            for (int j = i+1; j < N; j++) {
                model.ifThen(
                    model.arithm(X[i], "=", X[j]),
                    model.arithm(Y[i], "!=", Y[j])
                );
            }
        }

        //4. Z >= X[i]
        for (int i = 0; i < N; i++) {
            model.arithm(Z, ">=", X[i]).post();
        }

        model.setObjective(Model.MINIMIZE, Z);
       
        

        Solver solver = model.getSolver();
        

        while (solver.solve()) {
            //stop when find an optimal solution
            System.out.println("---------------------------");
            System.out.println("objective value: " + solver.getBestSolutionValue());
            for (int i = 0; i < N; i++) {
                System.out.println(i +  ". " +  X[i].getValue() + " " + Y[i].getValue());
            }
        }
        

//        Find All Solutions
        
//        List<Solution> solutions = solver.findAllOptimalSolutions(Z, false);
//        System.out.println("number of optimal solutions: " + solutions.size());
//        for (Solution s : solutions) {
//            System.out.println("---------------------------");
//            System.out.println("optimal objective value: " + s.getIntVal(Z));
//            for (int i = 0; i < N; i++) {
//                System.out.println(i +  ". " +  s.getIntVal(X[i]) + " " + s.getIntVal(Y[i]));
//            }
//        }
        
        solver.printStatistics();


    }
}
