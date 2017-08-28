package utiltest;

import generalutils.MutualInformation;

import java.io.PrintStream;

public class ChiSquare
{
  public static double calculateChiSquare(double[][] matrix, boolean failProof)
  {
    if (failProof) {
      matrix = add1ToAll(matrix);
    }
    double total = 0.0D;
    double[] rows = new double[matrix.length];
    double[] columns = new double[matrix[0].length];
    for (int i = 0; i < matrix[0].length; i++) {
      columns[i] = 0.0D;
    }
    for (int i = 0; i < matrix.length; i++) {
      rows[i] = MutualInformation.addArray(matrix[i]);
      for (int j = 0; j < matrix[0].length; j++) {
        columns[j] += matrix[i][j];
        total += matrix[i][j];
      }
    }
    double chi = 0.0D;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        chi += chiWordCategory(matrix, rows, columns, i, j, total);
      }
    }

    return chi;
  }

  private static double chiWordCategory(double[][] matrix, double[] rows, double[] columns, int category, int word, double total) {
    double est = rows[category] * columns[word] / total;
    double act = matrix[category][word];
    double dif = act - est;
    System.out.println(columns[word]);
    return dif * dif / est;
  }

  private static double[][] add1ToAll(double[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        matrix[i][j] += 1.0D;
      }
    }
    return matrix;
  }

  public static void main(String[] args) {
    System.out.println("CHI: " + calculateChiSquare(new double[][] { { 150.0D, 120.0D },  { 120.0D, 110.0D }, { 110.0D, 105.0D } }, false));
  }
}