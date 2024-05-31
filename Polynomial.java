import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
           this.coefficients = new double[1];
           this.exponents = new int[1];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[exponents.length];

        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
        }
    }

    public Polynomial(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        String polynomial = input.nextLine();

        int index = 0;
        while (index < polynomial.length()) {
            if (polynomial.charAt(index) == '-') {
                polynomial = polynomial.substring(0, index) + "+" + polynomial.substring(index);
                index+=2;
                continue;
            }

            index++;
        }

        String[] elements = polynomial.split("\\+");
        int length = elements.length;

        if (elements[0].equals("")) {
            length--;
        }

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals("") || !elements[i].contains("x")) {
                continue;
            }

            String[] element = elements[i].split("x");

            if (element.length == 1) {
                elements[i] = elements[i] + "1";
            }
            
            if (element[0].equals("")) {
                elements[i] = "1" + elements[i];
            } 
            
            if (element[0].equals("-")) {
                elements[i] = "-1" + elements[i].substring(1);
            }
        }

        this.exponents = new int[length];
        this.coefficients = new double[length]; 
        index = 0;
        int i = 0;

        while (i < elements.length) {
            if (elements[i].equals("")) {
                i++;
                continue;
            } else if (!elements[i].contains("x")) {
                this.coefficients[index] = Double.parseDouble(elements[i]);
                this.exponents[index] = 0;
                index++;
                i++;
                continue;
            }

            String[] element = elements[i].split("x");

            this.coefficients[index] = Double.parseDouble(element[0]);
            this.exponents[index] = Integer.parseInt(element[1]);

            i++;
            index++;
        }
    }

    public Polynomial add(Polynomial toAdd) {
        int[] temp_exp = new int[this.exponents.length + toAdd.exponents.length];
        double[] temp_coeff = new double[this.exponents.length + toAdd.exponents.length];
        int length = 0;
        int ind = 0;

        for (int i = 0; i < this.exponents.length; i++) {
            temp_exp[ind] = this.exponents[i];
            length++;
            ind++;
        }

        for (int i = 0; i < toAdd.exponents.length; i++) {
            boolean in_arr = false;

            for (int j = 0; j < length; j++) {
                if (temp_exp[j] == toAdd.exponents[i]) {
                    in_arr = true;
                    break;
                }
            }

            if (!in_arr) {
                temp_exp[ind] = toAdd.exponents[i];
                length++;
                ind++;
            }   
        }

        int final_length = length;

        for (int i = 0; i < length; i++) {
            double p1_coeff = 0;
            double p2_coeff = 0;

            for (int j = 0; j < this.exponents.length; j++) {
                if (this.exponents[j] == temp_exp[i]) {
                    p1_coeff = this.coefficients[j];
                    break;
                }
            }

            for (int j = 0; j < toAdd.exponents.length; j++) {
                if (toAdd.exponents[j] == temp_exp[i]) {
                    p2_coeff = toAdd.coefficients[j];
                    break;
                }
            }

            if (p1_coeff + p2_coeff == 0) {
                final_length--;
            } else {
                temp_coeff[i] = p1_coeff + p2_coeff;
            }
        }

        int[] result_exp = new int[final_length];
        double[] result_coeff = new double[final_length];
        int i = 0;
        int index = 0;

        while (i < final_length) {
            if (temp_coeff[i] == 0) {
                i++;
                final_length++;
                continue;
            }

            result_exp[index] = temp_exp[i];
            result_coeff[index] = temp_coeff[i];
            i++;
            index++;
        }

        return new Polynomial(result_coeff, result_exp);
    }

    public double evaluate(double value) {
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(value, this.exponents[i]);
        }

        return result;
    }

    public boolean hasRoot(double value) {
        return evaluate(value) == 0;
    }

    public Polynomial multiply(Polynomial toMultiply) {
        Polynomial result = new Polynomial();

        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.coefficients[i] == 0) {
                continue;
            }

            int[] temp_exp = new int[toMultiply.exponents.length];
            double[] temp_coeff = new double[toMultiply.coefficients.length];

            for (int j = 0; j < toMultiply.coefficients.length; j++) {
                temp_exp[j] = this.exponents[i] + toMultiply.exponents[j];
                temp_coeff[j] = this.coefficients[i] * toMultiply.coefficients[j];
            }

            result = result.add(new Polynomial(temp_coeff, temp_exp));
        }

        return result;
    }

    public void saveToFile(String fileName) throws IOException {
        FileWriter output = new FileWriter(fileName);
        output.write(toString());
        output.close();
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.coefficients[i] == 0) {
                continue;
            }

            if (this.coefficients[i] > 0 && i != 0) {
                result += "+";
            }

            result += Double.toString(this.coefficients[i]);

            if (this.exponents[i] != 0) {
                result += "x";

                if (this.exponents[i] > 1) {
                    result += Integer.toString(this.exponents[i]);
                }
            }
        }

        return result;
    }

}