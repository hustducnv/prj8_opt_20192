import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;


public class Choco2 {
    int N; //so mon
    int M; // so phong
    int[] d; // d[i] so luong sinh vien dang ki mon i
    int[] c; // c[i] suc chua cua phong i
    int[][] conflict; //conflict[i][j] : mon i va j khong the cung kip
    int[] oneM;

    public static void main(String[] args) {
        Choco2 app = new Choco2();
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

    public void solve() {
        // check xem co mon nao qua nhieu sinh  vien,
        oneM = new int[M];
        for (int i = 0; i < M ; i++) {
            oneM[i] = 1;
        }

        Model model = new Model();
        IntVar[] X = model.intVarArray(N, 1, N);
        IntVar[][] Y = model.intVarMatrix(N, M, 0, 1);
        IntVar Z = model.intVar(1, N);

        //constraints
        //1.
        for (int i = 0; i < N; i++) {
            model.scalar(Y[i], oneM, ">=", 1).post();
        }

        //2.
        for (int i1 = 0; i1 < N-1; i1++) {
            for (int i2 = i1+1; i2 < N; i2++) {
                if (conflict[i1][i2] == 1) {
                    model.arithm(X[i1], "!=", X[i2]).post();
                }
            }
        }

        //3
        for (int i1 = 0; i1 < N-1; i1++) {
            for (int i2 = i1+1; i2 < N; i2++) {
                for (int j = 0; j < M; j++) {
                    model.ifThen(
                    		model.arithm(X[i1], "=", X[i2]),
                            model.arithm(Y[i1][j], "+", Y[i2][j], "<=", 1)
                    );
                }

            }
        }

        //4
        for (int i = 0; i < N; i++) {
            model.scalar(Y[i], c, ">=", d[i]).post();
        }

        //5
        for (int i = 0; i < N; i++) {
            model.arithm(Z , ">=", X[i]).post();
        }

        model.setObjective(false, Z);

        Solver solver = model.getSolver();

        while (solver.solve()) {
            //stop when find an optimal solution
            System.out.println("\n---------------------------");
            System.out.println("objective value: " + solver.getBestSolutionValue());
            for (int i = 0; i < N; i++) {
                System.out.println();
                System.out.print("Mon " + i  + " kip " + X[i].getValue() + ", phong: ");
                for (int j = 0; j < M; j++) {
                    if (Y[i][j].getValue() == 1) {
                        System.out.print(j + ", ");
                    }
                }
            }
        }

        //Find All Solutions
//        List<Solution> solutions = solver.findAllOptimalSolutions(Z, false);
//        System.out.println("number of optimal solutions: " + solutions.size());
//        for (Solution s : solutions) {
//            System.out.println("---------------------------");
//            System.out.println("optimal objective value: " + s.getIntVal(Z));
//
//            for (int i = 0; i < N; i++) {
//                System.out.println();
//                System.out.print("Mon " + i  + " kip " + s.getIntVal(X[i]) + ", phong: ");
//                for (int j = 0; j < M; j++) {
//                    if (s.getIntVal(Y[i][j])== 1) {
//                        System.out.print(j + ", ");
//                    }
//                }
//            }
//
//        }

        solver.printStatistics();





    }
}
