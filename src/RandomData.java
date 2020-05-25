import java.io.*;
import java.util.Random;
import java.util.Scanner;
public class RandomData {
	public static void main(String[] args) throws IOException {
		int num;
		int[][] conflict;
		int N, M, Q, dem, min_student, max_student, min_capacity, max_capacity;
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		Random rd = new Random();
		System.out.printf("Nhap N: ");
		N = scanner.nextInt();
		conflict = new int[N][N];
		System.out.println();
		System.out.printf("Nhap M: ");
		M = scanner.nextInt();
		System.out.println();
		System.out.printf("Nhap Q: ");
		Q = scanner.nextInt();
		System.out.printf("Nhap min student: ");
		min_student = scanner.nextInt();
		System.out.println();
		System.out.printf("Nhap max student: ");
		max_student = scanner.nextInt();
		System.out.println();
		System.out.printf("Nhap min capacity: ");
		min_capacity = scanner.nextInt();
		System.out.println();
		System.out.printf("Nhap max capacity: ");
		max_capacity = scanner.nextInt();
		System.out.println();
		 FileWriter fw = new FileWriter("..\\data\\"+N +"_"+M+"_"+Q+".dat");
		dem = Q;
		fw.write(N + " " + M);
		fw.write("\r\n");
		//System.out.printf(N + " " + M);
		//System.out.println();
		for(int i = 1; i <= N; i++) {
			num = min_student + rd.nextInt(max_student - min_student + 1);
			fw.write(num + " ");
			//System.out.print(num + " ");
		}
		fw.write("\r\n");
		//System.out.println();
		for(int i = 1; i <= M; i++) {
			num = min_capacity + rd.nextInt(max_capacity - min_capacity + 1);
			fw.write(num + " ");
			//System.out.print(num + " ");
		}
		fw.write("\r\n");
		//System.out.println();
		fw.write(Q + " ");
		//System.out.println(Q);
		fw.write("\r\n");
		while(dem > 0) {
		for (int i = 0; i < N - 1  ; i++) {
			for(int j = i + 1; j < N; j++) {
				if(conflict[i][j] == 1) continue;
				num = rd.nextInt(2);
				if(num == 1) dem --;
				if(dem < 0) break; 
				conflict[i][j] = num;
			}
			if(dem < 0) break; 
		}
		}	
		dem = 0;
		for (int i = 0; i < N -1 ; i++) {
			for(int j = i + 1; j < N; j++) {
				if(conflict[i][j] == 1) {
					dem++;
					fw.write((i + 1) + " " + (j + 1));
					//System.out.print((i + 1) + " " + (j + 1));
					fw.write("\r\n");
					//System.out.println();
				}
				if(dem == Q) break;
				}
			if(dem == Q) break;
			}

        fw.close();
		}

	}

