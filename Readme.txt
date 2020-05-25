// giả sử 1 môn chỉ thi ở 1 phòng
biến
Xi : kip thi của  môn i, i = 0,.., N-1, Xi = 1 .. N
Yi : phòng thi của môn i, i = 0, .., N-1,
 Yi = {danh sach các môn có thể chứa}
Z

ràng buộc
1, với mọi (i, j) thuộc conflict: Xi != Xj
2, sức chứa của phòng >= số lượng sinh viên đki
3, cùng kip => != phòng
X[i] = X[j] => Y[i] != Y[j]
4, Z >= X[i]

obj. minimize (Z)
=> Z....


//1 môn có thể thi trong nhiều phòng khác nhau
X(i, j)  = k: phòng i, kip j thi môn k
i [0, ,M-1] ;  j [0, ,N-1]; k [0, ,N]

1, (k1, k2) conflict X[i][j]

//cách khác
Xi : kip thi môn i
Y(i, j) = 1, môn i thi phòng j
Z


1, tổng theo hàng của Y >= 1
2, (i1, i2) conflig => X[i1] != X[i2]
3, cùng kip => khác phòng??
X(i1) = X(i2) suy ra
for j : 0 -> M-1
       Y(i1, j) + Y(i2, j) <= 1
4, sức chứa
for i
     for j = 0, ,M-1,  sum(i) += Y(i, j) * c(j)
     sum(i) >= d(i) 
???
5. Z >= X[i]

OBJ: min Z


hàm random
tham số như sau
N: số môn
M: số phòng
Q: số cặp conflict (môn đánh chỉ số từ 1)
min_student, max_student: số sinh viên 1 môn
min_capacity, max_capacity: sức chứa 1 phòng

tên file: N_M_Q.txt









