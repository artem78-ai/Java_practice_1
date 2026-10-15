public class ComplexMatrix {
    private ComplexNumber[][] cells;

    public ComplexMatrix(int rows, int cols) {
        this.cells = new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new ComplexNumber();
            }
        }
    }

    public int rows() {
        return this.cells.length;
    }

    public int cols() {
        return this.cells[0].length;
    }

    public void set(int row, int col, ComplexNumber value) {
        this.cells[row][col] = value;
    }

    public ComplexNumber get(int row, int col) {
        return this.cells[row][col];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows(); i++) {
            sb.append("[ ");
            for (int j = 0; j < cols(); j++) {
                sb.append(cells[i][j]).append("\t");
            }
            sb.append(" ]\n");
        }
        return sb.toString().stripTrailing();
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rows() != other.rows() || this.cols() != other.cols()) {
            throw new IllegalArgumentException("Разные размеры");
        }

        int rows = this.rows();
        int cols = this.cols();
        ComplexMatrix result = new ComplexMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.cells[i][j] = this.cells[i][j].add(other.cells[i][j]);
            }
        }

        return result;
    }

    public ComplexMatrix subtract(ComplexMatrix other) {
        if (this.rows() != other.rows() || this.cols() != other.cols()) {
            throw new IllegalArgumentException("Разные размеры");
        }

        int rows = this.rows();
        int cols = this.cols();
        ComplexMatrix result = new ComplexMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.cells[i][j] = this.cells[i][j].subtract(other.cells[i][j]);
            }
        }

        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols() != other.rows()) {
            throw new IllegalArgumentException("Столбцы не равны");
        }

        int rows = this.rows();
        int cols = other.cols();
        int inner = this.cols();
        ComplexMatrix result = new ComplexMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ComplexNumber sum = new ComplexNumber();
                for (int k = 0; k < inner; k++) {
                    sum = sum.add(this.cells[i][k].multiply(other.cells[k][j]));
                }
                result.cells[i][j] = sum;
            }
        }

        return result;
    }

    public ComplexMatrix transpose() {
        int rows = this.rows();
        int cols = this.cols();
        ComplexMatrix result = new ComplexMatrix(cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.cells[j][i] = this.cells[i][j];
            }
        }

        return result;
    }

    public ComplexNumber determinant() {
        if (this.rows() != this.cols()) {
            throw new IllegalArgumentException("Определитель существует только для квадратной матрицы");
        }
        return det(this.cells);
    }

    private ComplexNumber det(ComplexNumber[][] m) {
        int n = m.length;

        if (n == 1) {
            return m[0][0];
        }
        if (n == 2) {
            return m[0][0].multiply(m[1][1]).subtract(m[0][1].multiply(m[1][0]));
        }

        ComplexNumber sum = new ComplexNumber();
        for (int j = 0; j < n; j++) {
            ComplexNumber minorDet = det(minor(m, 0, j));
            ComplexNumber term = m[0][j].multiply(minorDet);

            if (j % 2 == 0) {
                sum = sum.add(term);
            } else {
                sum = sum.subtract(term);
            }
        }

        return sum;
    }

    private ComplexNumber[][] minor(ComplexNumber[][] m, int skipRow, int skipCol) {
        int n = m.length;
        ComplexNumber[][] result = new ComplexNumber[n - 1][n - 1];

        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == skipRow) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == skipCol) continue;
                result[r][c] = m[i][j];
                c++;
            }
            r++;
        }

        return result;
    }

    private ComplexNumber cofactor(ComplexNumber[][] m, int row, int col) {
        ComplexNumber minorDet = det(minor(m, row, col));

        if ((row + col) % 2 == 0) {
            return minorDet;
        } else {
            return minorDet.negate();
        }
    }

    public ComplexMatrix inverse() {
        if (this.rows() != this.cols()) {
            throw new IllegalArgumentException("Обратная матрица существует только для квадратной матрицы");
        }

        ComplexNumber d = det(this.cells);

        if (d.getReal() == 0.0 && d.getImag() == 0.0) {
            throw new ArithmeticException("Обратной матрицы не существует, т.к. матрица вырождена");
        }

        int n = this.rows();
        ComplexMatrix adj = new ComplexMatrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adj.cells[i][j] = cofactor(this.cells, j, i).divide(d);
            }
        }

        return adj;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        return this.multiply(other.inverse());
    }
}
