import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        default_test();
        test2();
        test_file_in();
        test_file_in_add();
        test_file_in_multiply();
        test_file_out();
    }

    public static void default_test() {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));

        double[] c1 = {6, 5};
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {-2, -9};
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial s = p1.add(p2);

        System.out.println("s(0.1)=" + s.evaluate(0.1));
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }
    }

    public static void test2() {
        double[] c1 = {6, 10, 7, 3};
        int[] e1 = {2, 1, 3, 0};

        double[] c2 = {3, -6, -2, 1};
        int[] e2 = {4, 2, 3, 5};

        Polynomial p1 = new Polynomial(c1, e1);
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial s = p1.add(p2);
        Polynomial s1 = p2.add(p1);

        System.out.println("s(0) = " + s.evaluate(0));
        System.out.println("s1(0) = " + s1.evaluate(0));
    }

    public static void test_file_in() throws FileNotFoundException {
        File f1 = new File("p1.txt");
        File f2 = new File("p2.txt");
        File f3 = new File("p3.txt");

        Polynomial p1 = new Polynomial(f1);
        Polynomial p2 = new Polynomial(f2);
        Polynomial p3 = new Polynomial(f3);

        System.out.println("p1(0.3) = " + p1.evaluate(0));
        System.out.println("p2(0.3) = " + p2.evaluate(0));
        System.out.println("p3(0.3) = " + p3.evaluate(0));
    }

    public static void test_file_in_add() throws FileNotFoundException {
        File f1 = new File("p4.txt");
        File f2 = new File("p5.txt");

        Polynomial p1 = new Polynomial(f1);
        Polynomial p2 = new Polynomial(f2);

        Polynomial sum = p1.add(p2);

        if (sum.hasRoot(2)) {
            System.out.println("2 is a root of sum");
        } else {
            System.out.println("2 is not a root of sum");
        }

        if (sum.hasRoot(3)) {
            System.out.println("3 is a root of sum");
        } else {
            System.out.println("3 is not a root of sum");
        }
    }

    public static void test_file_in_multiply() throws FileNotFoundException {
        File f1 = new File("p1.txt");
        File f2 = new File("p2.txt");

        Polynomial p1 = new Polynomial(f1);
        Polynomial p2 = new Polynomial(f2);

        Polynomial product = p1.multiply(p2);

        System.out.println("product(12) = " + product.evaluate(12));
        System.out.println("product(-2) = " + product.evaluate(-2));
    }

    public static void test_file_out() throws IOException {
        Polynomial p1 = new Polynomial(new File("p1.txt"));
        Polynomial p2 = new Polynomial(new File("P2.txt"));
        Polynomial p3 = new Polynomial(new File("p3.txt")); 

        Polynomial sum = p1.add(p2);
        Polynomial product = sum.multiply(p3);
        
        product.saveToFile("output.txt");
    }
}