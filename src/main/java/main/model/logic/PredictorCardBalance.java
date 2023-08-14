package main.model.logic;

import main.model.entities.Transaction;
import main.model.repositories.TransactionRepository;
import org.apache.poi.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PredictorCardBalance {

    private int stepOfFunc = 0;
    private int baseOfFunc = 0;
    private double[] arrY;

    public void initialStepOfFunc(int base,int step){
        baseOfFunc = base;
        stepOfFunc = step;
    }

    public double calculateSumOfPowerX(int power,int step){
        double sumXPower = 0;
        for(int cordX = baseOfFunc;cordX < step;cordX++){
            sumXPower += Math.pow(cordX,power);
        }
        return sumXPower;
    }

    public double calculateSumOfPowerXY(int power,int step){
        double sumXYPower = 0;
        for(int cordX = baseOfFunc;cordX < step;cordX++){
            sumXYPower += arrY[cordX]*Math.pow(cordX,power);
        }
        return sumXYPower;
    }

    public double[][] buildMatrixX(int matrixSize,int step){
        double[][] matrixSumPowerX = new double[matrixSize][matrixSize];
        int power = 0,count = 0;

        for(int i = matrixSize-1;i>=0;i--){
            for(int j = matrixSize-1;j>=0;j--){
                matrixSumPowerX[i][j] = calculateSumOfPowerX(power,step);
                power++;
            }
            count++;
            power = count;
        }
        return  matrixSumPowerX;
    }

    public double[] buildVectorY(int vectorSize,int step){
        double[] vectorSumPowerY = new double[vectorSize];
        int power = 0;

        for(int i = vectorSize-1;i>=0;i--){
            vectorSumPowerY[i] = calculateSumOfPowerXY(power,step);
            power++;
        }
        return vectorSumPowerY;
    }

    public double[][] invert(double a[][]){
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) {
            b[i][i] = 1;
        }
        gaussian(a, index);

        for (int i=0; i<n-1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];
                }
            }
        }

        for (int i=0; i<n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

    public void gaussian(double a[][], int index[]){
        int n = index.length;
        double c[] = new double[n];

        for (int i=0; i<n; ++i) {
            index[i] = i;
        }
        for (int i=0; i<n; ++i){
            double c1 = 0;
            for (int j=0; j<n; ++j){
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        int k = 0;
        for (int j=0; j<n-1; ++j){
            double pi1 = 0;
            for (int i=j; i<n; ++i){
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1){
                    pi1 = pi0;
                    k = i;
                }
            }

            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i){
                double pj = a[index[i]][j]/a[index[j]][j];
                a[index[i]][j] = pj;
                for (int l=j+1; l<n; ++l) {
                    a[index[i]][l] -= pj * a[index[j]][l];
                }
            }
        }
    }

    public double[] calculateSquareCoef(double[][] matrixX,double[] vectorY){
        double[] vectorSquareMV = new double[vectorY.length];

        for (int i = 0; i < matrixX.length;i++) {
            for (int j = 0; j < matrixX.length;j++) {
                vectorSquareMV[i] += matrixX[i][j] * vectorY[j];
            }
        }
        return vectorSquareMV;
    }

    public double[] buildOfPolynomial(int sizeOfPolynomial,int step){
        double[] arrApproximateY = new double[stepOfFunc-baseOfFunc];
        double[] vectorSquareCoef = calculateSquareCoef(invert(buildMatrixX(sizeOfPolynomial,step)),buildVectorY(sizeOfPolynomial,step));
        int count = 0;

        for(int x = baseOfFunc;x < stepOfFunc;x++) {
            int power = sizeOfPolynomial-1;
            for (int c = 0; c < vectorSquareCoef.length; c++) {
                arrApproximateY[count] += Math.pow(x, power) * vectorSquareCoef[c];
                power--;
            }
            count++;
        }
        return arrApproximateY;
    }

    public int selectCorrectPowerOfPoly(int sizeOfFunc,double[] arrInputY,int step){
        int countOfPoly = 2;
        double preDiff = 1000000;

        double midX = Arrays.stream(arrInputY).sum()/arrInputY.length;

        while (true) {
            double postDiff = 0;
            double diffInputY = 0;
            initialStepOfFunc(0, sizeOfFunc);
            double[] arrApproximateY = buildOfPolynomial(countOfPoly, sizeOfFunc);

            for (int i = 0; i < arrInputY.length-step; i++) {
                diffInputY += Math.pow(arrInputY[i]-midX,2);
                postDiff += Math.abs(arrApproximateY[i] - arrInputY[i]);
            }

            if (postDiff < preDiff) {
                if(postDiff < Math.sqrt(diffInputY)){
                    return countOfPoly;
                }
                preDiff = postDiff;
                countOfPoly++;
            }
        }
    }

    public List<Double> predictBalance(List<Double> listBalances,int step){
        int size = listBalances.size();
        arrY = new double[size+step];

        for(int i = 0;i < size;i++){
            arrY[i] = listBalances.get(i);
        }

        int power = selectCorrectPowerOfPoly(size, arrY, step);
        initialStepOfFunc(0, size + step);

        return Arrays.stream(buildOfPolynomial(power, size))
                .boxed().collect(Collectors.toList());
    }
}
