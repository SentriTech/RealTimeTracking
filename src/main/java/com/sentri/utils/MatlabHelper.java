package com.sentri.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sanjun.yyj on 11/2/14.
 */
public class MatlabHelper {
    public static double[][] zeros(int n) {
        double[][] matrixA = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = 0;
            }
        }
        return matrixA;
    }

    public static double[][] zeros(int m, int n) {
        double[][] matrixA = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = 0;
            }
        }
        return matrixA;
    }

    public static double[][][] zeros(int m, int n, int l) {
        double[][][] matrixA = new double[m][n][l];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k  = 0; k < l; k++) {
                    matrixA[i][j][k] = 0;
                }
            }
        }
        return matrixA;
    }

    public static double[][][][] zeros(int m, int n, int o, int p) {
        double[][][][] matrixA = new double[m][n][o][p];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k  = 0; k < o; k++) {
                    for (int l = 0; l < p; l++) {
                        matrixA[i][j][k][l] = 0;
                    }
                }
            }
        }
        return matrixA;
    }


    public static double[][] ones(int n) {
        double[][] matrixA = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = 1;
            }
        }
        return matrixA;
    }

    public static double[] onesDim1(int n) {
        double[] matrixA = new double[n];
        for (int i = 0; i < n; i++) {
                matrixA[i] = 1;
        }
        return matrixA;
    }

    public static double[][] ones(int m, int n) {
        double[][] matrixA = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = 1;
            }
        }
        return matrixA;
    }

    public static double[][] subMatrix(double[][] matrixA, int x, int xLength, int y, int yLength) {
        double[][] matrixB = new double[xLength][yLength];
        for (int i = x; i < x + xLength; i++) {
            for (int j = y; j < y + yLength; j++) {
                matrixB[i - x][j - y] = matrixA[i][j];
            }
        }
        return matrixB;
    }

    public static double[][] add(double[][] matrixA, double[][] matrixB) {
        double[][] matrixC = new double[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                matrixC[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        return matrixC;
    }

    public static double[] add(double[] matrixA, int x, int xLength, double constant) {
        double[] matrixB = new double[matrixA.length];
        for (int i = x; i < x +xLength; i++) {
            matrixB[i] = matrixA[i] + constant;
        }
        return matrixB;
    }

    public static double[] add(double[] matrixA, double constant) {
        return add(matrixA, 0, matrixA.length, constant);
    }

    public static double[][] add(double[][] matrixA, int x, int xLength, int y, int yLength, double constant) {
        double[][] matrixB = new double[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if ((i >= x) && (i < x + xLength) && (j >= y) && (j < y + yLength)) {
                    matrixB[i][j] = matrixA[i][j] + constant;
                } else {
                    matrixB[i][j] = matrixA[i][j];
                }
            }
        }
        return matrixB;
    }

    public static double[][] add(double[][] matrixA, double constant) {
        return add(matrixA, 0, matrixA.length, 0, matrixA[0].length, constant);
    }


    public static double[][] minus(double[][] matrixA, int x, int xLength, int y, int yLength, double constant) {
        return add(matrixA, x, xLength, y, yLength, -constant);
    }

    public static double[] minus(double[] matrixA, int x, int xLength, double constant) {
        return add(matrixA, x, xLength, -constant);
    }

    public static double[] minus(double[] matrixA, double constant) {
        return add(matrixA, 0, matrixA.length, -constant);
    }


    public static double[][] minus(double[][] matrixA, double constant) {
        return minus(matrixA, 0, matrixA.length, 0, matrixA[0].length, constant);
    }

    public static double[][] minus(double[][] matrixA, double[][] matrixB) {
        double[][] matrixC = new double[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                matrixC[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }
        return matrixC;
    }

    public static double[][] multiply(double[][] matrixA, int x, int xLength, int y, int yLength, double constant) {
        double[][] matrixB = new double[matrixA.length][matrixA[0].length];
        for (int i = x; i < x + xLength; i++) {
            for (int j = y; j < y + yLength; j++) {
                matrixB[i][j] = matrixA[i][j] * constant;
            }
        }
        return matrixB;
    }

    public static double[][] multiply(double[][] matrixA, double constant) {
        return multiply(matrixA, 0, matrixA.length, 0, matrixA[0].length, constant);
    }

    public static double[][] divide(double[][] matrixA, int x, int xLength, int y, int yLength, double constant) {
        double[][] matrixB = new double[matrixA.length][matrixA[0].length];
        for (int i = x; i < x + xLength; i++) {
            for (int j = y; j < y + yLength; j++) {
                matrixB[i][j] = matrixA[i][j] / constant;
            }
        }
        return matrixB;
    }

    public static double[][] divide(double[][] matrixA, double[] matrixB) {
        double[][] matrixC = new double[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                matrixC[i][j] = matrixA[i][j] / matrixB[i];
            }
        }
        return matrixC;
    }

    public static double[] divide(double[] matrixA, int x, int xLenght, double constant) {
        double[] matrixB = new double[matrixA.length];
        for (int i = x; i < x + xLenght; i++) {
            matrixB[i] = matrixA[i] / constant;
        }
        return matrixB;
    }

    public static double[] divide(double[] matrixA, double constant) {
        return divide(matrixA, 0, matrixA.length, constant);
    }

    public static double[][] divide(double[][] matrixA, double constant) {
        return divide(matrixA, 0, matrixA.length, 0, matrixA[0].length, constant);
    }

    public static double[][] reverse(double[][] matrixA) {
        double[][] matrixB = new double[matrixA[0].length][matrixA.length];
        for (int i = 0; i < matrixA[0].length; i++) {
            for (int j = 0; j < matrixA.length; j++) {
                matrixB[i][j] = matrixA[j][i];
            }
        }
        return matrixB;
    }

    public static double[] getColumn(double[][] matrixA, int n) {
        double[] matrixB = new double[matrixA.length];
        for (int i = 0; i < matrixA.length; i++) {
            matrixB[i] = matrixA[i][n];
        }
        return matrixB;
    }

    public static double[] getRow(double[][] matrixA, int n) {
        double[] matrixB = new double[matrixA[0].length];
        for (int i = 0; i < matrixA[0].length; i++) {
            matrixB[i] = matrixA[n][i];
        }
        return matrixB;
    }

    public static int findFirstIndexOfMore(double[] matrixA, double dataFind) {
        for (int i = 0; i < matrixA.length; i++) {
            if (matrixA[i] > dataFind) {
                return i;
            }
        }
        return matrixA.length;
    }

    public static int findFirstIndexOfMoreOrEqual(double[] matrixA, double dataFind) {
        for (int i = 0; i < matrixA.length; i++) {
            if (matrixA[i] >= dataFind) {
                return i;
            }
        }
        return matrixA.length;
    }

    public static int[] findEqual(double[] matrixA, double dataFind) {
        List<Integer> indexs = new ArrayList<Integer>();
        for (int i = 0; i < matrixA.length; i++) {
            if (matrixA[i] == dataFind) {
                indexs.add(i);
            }
        }
        int[] matrixB = new int[indexs.size()];
        for (int i = 0; i < matrixB.length; i++) {
            matrixB[i] = indexs.get(i);
        }
        return matrixB;
    }

    public static int[] findNotEqual(double[] matrixA, double dataFind) {
        List<Integer> indexs = new ArrayList<Integer>();
        for (int i = 0; i < matrixA.length; i++) {
            if (matrixA[i] != dataFind) {
                indexs.add(i);
            }
        }
        int[] matrixB = new int[indexs.size()];
        for (int i = 0; i < matrixB.length; i++) {
            matrixB[i] = indexs.get(i);
        }
        return matrixB;
    }

    public static double[] getData(double[][] matrixA, int[] rows, int column) {
        List<Double> matrixB = new ArrayList<Double>();
        for (int i : rows) {
            matrixB.add(matrixA[i][column]);
        }
        double[] matrixC = new double[matrixB.size()];
        for (int i = 0; i < matrixB.size(); i++) {
            matrixC[i] = matrixB.get(i);
        }
        return matrixC;
    }

    public static double[][] getData(double[][] matrixA, int[][] matrixB) {
        double[][] matrixC = new double[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                matrixC[i][j] = matrixA[i][j] * matrixB[i][j];
            }
        }
        return matrixC;
    }

    public static int[] convertToInt(Object[] matrixA) {
        int[] matrixB = new int[matrixA.length];
        for (int i = 0; i < matrixA.length; i++) {
            matrixB[i] = Integer.valueOf((Integer)matrixA[i]);
        }
        return matrixB;
    }

    public static double[] convertToDouble(Object[] matrixA) {
        double[] matrixB = new double[matrixA.length];
        for (int i = 0; i < matrixA.length; i++) {
            matrixB[i] = Double.valueOf((Double)matrixA[i]);
        }
        return matrixB;
    }

    public static double sum(double[] matrixA) {
        double sum = 0;
        for (double d : matrixA) {
            sum += d;
        }
        return sum;
    }

    public static List<Double>[][] cell(int m, int n) {
        List<Double>[][] matrixA = new List[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = new ArrayList<Double>();
            }
        }
        return matrixA;
    }

    public static double max(double[][] matrixA) {
        double max = -32767;
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (matrixA[i][j] > max) {
                    max = matrixA[i][j];
                }
            }
        }
        return max;
    }

    public static double max(double[] matrixA) {
        double max = -32767;
        for (int i = 0; i < matrixA.length; i++) {
            if (matrixA[i] > max) {
                max = matrixA[i];
            }
        }
        return max;
    }

    public static double[][] exp(double[][] matrixA) {
        double[][] matrixB = new double[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                matrixB[i][j] = Math.exp(matrixA[i][j]);
            }
        }
        return matrixB;
    }

    public static double[] exp(double[] matrixA) {
        double[] matrixB = new double[matrixA.length];
        for (int i = 0; i < matrixA.length; i++) {
           matrixB[i] = Math.exp(matrixA[i]);
        }
        return matrixB;
    }

    public static double[][] tril(double[][] matrixA, int k) {
        double[][] matrixB = new double[matrixA.length][matrixA[0].length];
        if (k > 0) {
            for (int i = 0; i < min(matrixA.length, matrixA[0].length - k); i++) {
                for (int j = i + k; j < matrixA[0].length; j++) {
                    matrixB[i][j] = matrixA[i][j];
                }
            }
        } else {
            k = Math.abs(k);
            for (int i = k; i < matrixA.length; i++) {
                for (int j = 0; j < min(matrixA.length, i+1); j++) {
                    matrixB[i][j] = matrixA[i][j];
                }
            }
        }
        return matrixB;
    }

    public static double[][] tril(double[][] matrixA) {
        return tril(matrixA, 0);
    }

    public static int min(int a, int b) {
        return (a < b) ? a : b;
    }

    public static int[][] findNotEqual(double[][] matrixA, double data) {
        int[][] matrixB = new int[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (matrixA[i][j] != data) {
                    matrixB[i][j] = 1;
                }
            }
        }
        return matrixB;
    }

    public static int[][] findEqual(double[][] matrixA, double data) {
        int[][] matrixB = new int[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (matrixA[i][j] == data) {
                    matrixB[i][j] = 1;
                }
            }
        }
        return matrixB;
    }

    public static int[][] findMore(double[][] matrixA, double data) {
        int[][] matrixB = new int[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (matrixA[i][j] > data) {
                    matrixB[i][j] = 1;
                }
            }
        }
        return matrixB;
    }

    public static int[][] findLess(double[][] matrixA, double data) {
        int[][] matrixB = new int[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (matrixA[i][j] < data) {
                    matrixB[i][j] = 1;
                }
            }
        }
        return matrixB;
    }

    public static double sum(List<Double> matrixA) {
        double sum = 0;
        for (Double d : matrixA) {
            sum += d;
        }
        return sum;
    }

    public static double variance(List<Double> matrixA) {
        if (matrixA == null || matrixA.size() == 0) {
            return 0;
        }
        double var = 0;
        double avg = sum(matrixA) / matrixA.size();
        for (Double d : matrixA) {
            var += Math.pow(d - avg, 2);
        }
        return Math.sqrt(var / matrixA.size());
    }

    public static double[][] randn(int m, int n) {
        double[][] matrixA = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrixA[i][j] = Math.random();
            }
        }
        return matrixA;
    }

    public static double[][] randn(int n) {
        return randn(n, n);
    }

    public static double[][] abs(double[][] matrixA) {
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                matrixA[i][j] = Math.abs(matrixA[i][j]);
            }
        }
        return matrixA;
    }

    public static double[][] setData(double[][] matrixA, int[][] matrixB, double constants) {
        double[][] matrixC = new double[matrixA.length][matrixA[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (matrixB[i][j] != 0) {
                    matrixA[i][j] = constants;
                }
            }
        }
        return matrixA;
    }

    public static int sum(int[][] matrixA) {
        int sum = 0;
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                sum += matrixA[i][j];
            }
        }
        return sum;
    }

    public static double[][] multiply(double[][] matrixA, double[][] matrixB) {
        double[][] matrixC = new double[matrixA.length][matrixB[0].length];
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return matrixC;
    }

    //直方图统计，每个区间的个数
    public static double[][] histc(double[][] matrixA, double[] matrixB) {
        double[][] matrixC = new double[matrixB.length][matrixA[0].length];
        for (int i = 0 ; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                for (int k = 0; k < matrixB.length - 1; k++) {
                    if (matrixA[i][j] >=matrixB[k] & matrixA[i][j] < matrixB[k+1]) {
                        matrixC[k][j]++;
                    }
                }
                if (matrixA[i][j] >= matrixB[matrixB.length-1]) {
                    matrixC[matrixB.length-1][j]++;
                }
            }
        }
        return matrixC;
    }

    public static double[][][] sumDim(double[][][][] matrixA, int dim) {
        double[][][] matrixB = new double[matrixA.length][matrixA[0].length][matrixA[0][0].length];
        if (dim == 0) {
          matrixB = new double[matrixA[0].length][matrixA[0][0].length][matrixA[0][0][0].length];
        } else if (dim == 1) {
            matrixB = new double[matrixA.length][matrixA[0][0].length][matrixA[0][0][0].length];
        } else if (dim == 2) {
            matrixB = new double[matrixA.length][matrixA[0].length][matrixA[0][0][0].length];
        } else if (dim == 3) {
            matrixB = new double[matrixA.length][matrixA[0].length][matrixA[0][0].length];
        }
        for (int i = 0; i < matrixA.length;i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                for (int k = 0; k < matrixA[0][0].length; k++) {
                    for (int l = 0; l < matrixA[0][0][0].length; l++) {
                        if (dim == 0) {
                            matrixB[j][k][l] += matrixA[i][j][k][l];
                        } else if (dim == 1) {
                            matrixB[i][k][l] += matrixA[i][j][k][l];
                        } else if (dim == 2) {
                            matrixB[i][j][l] += matrixA[i][j][k][l];
                        } else if (dim == 3) {
                            matrixB[i][j][k] += matrixA[i][j][k][l];
                        }
                    }
                }
            }
        }
        return matrixB;
    }

    public static double[][] sumDim(double[][][] matrixA, int dim) {
        double[][] matrixB = new double[matrixA.length][matrixA[0].length];
        if (dim == 0) {
            matrixB = new double[matrixA[0].length][matrixA[0][0].length];
        } else if (dim == 1) {
            matrixB = new double[matrixA.length][matrixA[0][0].length];
        } else if (dim == 2) {
            matrixB = new double[matrixA.length][matrixA[0].length];
        }
        for (int i = 0; i < matrixA.length;i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                for (int k = 0; k < matrixA[0][0].length; k++) {
                    if (dim == 0) {
                        matrixB[j][k] += matrixA[i][j][k];
                    } else if (dim == 1) {
                        matrixB[i][k] += matrixA[i][j][k];
                    } else if (dim == 2) {
                        matrixB[i][j] += matrixA[i][j][k];

                    }
                }
            }
        }
        return matrixB;
    }

    public static double[] sumDim(double[][] matrixA, int dim) {
        double[] matrixB = new double[matrixA.length];
        if (dim == 0) {
            matrixB = new double[matrixA[0].length];
        } else if (dim == 1) {
            matrixB = new double[matrixA.length];
        }
        for (int i = 0; i < matrixA.length;i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                if (dim == 0) {
                    matrixB[j] += matrixA[i][j];
                } else if (dim == 1) {
                    matrixB[i] += matrixA[i][j];
                }
            }
        }
        return matrixB;
    }

    public static double[] cumsum(double[] matrixA) {
        double[] matrixB = new double[matrixA.length + 1];
        matrixB[0] = 0;
        matrixB[matrixA.length] = 1;
        for (int i = 0; i < matrixA.length; i++) {
            matrixB[i+1] = matrixA[i] + matrixB[i];
        }
        return matrixB;
    }

    public static void printMatrix(double[][] matrixA) {
        for (int i = 0; i < matrixA.length;i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                System.out.print(matrixA[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void printMatrix(int[][] matrixA) {
        for (int i = 0; i < matrixA.length;i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                System.out.print(matrixA[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
