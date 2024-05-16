public class Polynomial {
    double[] coefficients;

    public Polynomial() {
           this.coefficients = new double[1];
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = new double[coefficients.length];

        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
        }
    }

    public Polynomial add(Polynomial toAdd) {
        double[] resultArray = new double[Math.max(toAdd.coefficients.length, this.coefficients.length)];

        for (int i = 0; i < resultArray.length; i++) {
            if (i < toAdd.coefficients.length) {
                resultArray[i] += toAdd.coefficients[i];
            }

            if (i < this.coefficients.length) {
                resultArray[i] += this.coefficients[i];
            }
        }

        return new Polynomial(resultArray);
    }

    public double evaluate(double value) {
        double result = 0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(value, i);
        }

        return result;
    }

    public boolean hasRoot(double value) {
        return evaluate(value) == 0;
    }
}