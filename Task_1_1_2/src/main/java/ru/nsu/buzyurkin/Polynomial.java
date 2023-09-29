package ru.nsu.buzyurkin;

public class Polynomial {
    private int[] coefs;

    /**
     * Constructs a Polynomial object with the given coefficients.
     *
     * @param givenCoefs An array of integers representing the coefficients
     *                   of the polynomial from the highest degree to the constant term.
     */
    Polynomial(int[] givenCoefs) {
        coefs = new int[givenCoefs.length];
        System.arraycopy(givenCoefs, 0, this.coefs, 0, givenCoefs.length);
    }

    /**
     * Adds the given polynomial to this polynomial and returns the result.
     *
     * @param givenPoly The polynomial to be added.
     * @return A new Polynomial representing the result of the addition.
     */
    public Polynomial plus(Polynomial givenPoly) {
        int maxCoefsLen = Math.max(givenPoly.coefs.length, this.coefs.length);

        Polynomial res = new Polynomial(new int[maxCoefsLen]);

        for (int i = 0; i < maxCoefsLen; i++) {
            if (i < givenPoly.coefs.length) {
                res.coefs[i] += givenPoly.coefs[i];
            }

            if (i < coefs.length) {
                res.coefs[i] += this.coefs[i];
            }
        }

        return res;
    }

    /**
     * Subtracts the given polynomial from this polynomial and returns the result.
     *
     * @param givenPoly The polynomial to be subtracted.
     * @return A new Polynomial representing the result of the subtraction.
     */
    public Polynomial minus(Polynomial givenPoly) {
        int maxCoefsLen = Math.max(givenPoly.coefs.length, this.coefs.length);

        Polynomial res = new Polynomial(new int[maxCoefsLen]);

        for (int i = 0; i < maxCoefsLen; i++) {
            if (i < this.coefs.length) {
                res.coefs[i] += this.coefs[i];
            }

            if (i < givenPoly.coefs.length) {
                res.coefs[i] -= givenPoly.coefs[i];
            }
        }

        return res;
    }

    /**
     * Multiplies this polynomial by the given polynomial and returns the result.
     *
     * @param givenPoly The polynomial to be multiplied by.
     * @return A new Polynomial representing the result of the multiplication.
     */
    public Polynomial times(Polynomial givenPoly) {
        Polynomial res = new Polynomial(new int[this.coefs.length + givenPoly.coefs.length]);

        for (int i = 0; i < this.coefs.length; i++) {
            for (int j = 0; j < givenPoly.coefs.length; j++) {
                res.coefs[i + j] += this.coefs[i] * givenPoly.coefs[j];
            }
        }

        return res;
    }

    /**
     * Evaluates the polynomial at a given value of x.
     *
     * @param x The value at which the polynomial should be evaluated.
     * @return The result of evaluating the polynomial at x.
     */
    public double evaluate(double x) {
        double res = 0;
        for (int i = 0; i < coefs.length; i++) {
            res += this.coefs[i] * Math.pow(x, i);
        }

        return res;
    }

    /**
     * Computes the t-th derivative of this polynomial.
     *
     * @param t The order of differentiation.
     * @return A new Polynomial representing the t-th derivative.
     */
    public Polynomial differentiate(int t) {
        if (t == 0) {
            return this;
        }

        return differentiateRecursive(this, t);
    }

    private Polynomial differentiateRecursive(Polynomial p, int t) {
        if (t == 0) {
            return p;
        }

        if (p.coefs.length == 1) {
            p.coefs[0] = 0;
            return p;
        }

        Polynomial res = new Polynomial(new int[p.coefs.length - 1]);
        for (int i = 1; i < p.coefs.length; i++) {
            res.coefs[i - 1] = p.coefs[i] * i;
        }

        return differentiateRecursive(res, t - 1);
    }

    /**
     * Checks if this polynomial is equal to the given polynomial.
     *
     * @param obj The polynomial to be compared with.
     * @return true if the two polynomials are equal, false otherwise.
     */

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Polynomial givenPoly = (Polynomial) obj;
        int minLen = Math.min(this.coefs.length, givenPoly.coefs.length);

        for (int i = 0; i < minLen; i++) {
            if (this.coefs[i] != givenPoly.coefs[i]) {
                return false;
            }
        }

        if (this.coefs.length > givenPoly.coefs.length) {
            for (int i = this.coefs.length - 1; i >= minLen; i--) {
                if (this.coefs[i] != 0) {
                    return false;
                }
            }
        }

        if (this.coefs.length < givenPoly.coefs.length) {
            for (int i = givenPoly.coefs.length - 1; i >= minLen; i--) {
                if (givenPoly.coefs[i] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns a string representation of the polynomial.
     *
     * @return A string representing the polynomial in a human-friendly form.
     */

    @Override
    public String toString() {
        int degree = this.coefs.length - 1;
        String res = "";
        for (int i = degree; i >= 0; i--) {
            if (this.coefs[i] != 0) {
                if (this.coefs[i] < 0) {
                    res += "-";
                }
                res += atomToString(this.coefs[i], i);
                degree = i;
                break;
            }
        }

        if (res.isEmpty()) {
            return "0";
        }

        for (int i = degree - 1; i >= 0; i--) {
            if (this.coefs[i] == 0) {
                continue;
            }

            if (this.coefs[i] > 0) {
                res += " + " + atomToString(this.coefs[i], i);
            }

            if (this.coefs[i] < 0) {
                res += " - " + atomToString(this.coefs[i], i);
            }
        }

        return res;
    }

    private String atomToString(int coef, int degree) {
        String str = "";
        if (coef != 1) {
            str += "" + Math.abs(coef);
        }

        if (degree != 0) {
            str += "x";

            if (degree != 1) {
                str += "^" + degree;
            }
        }

        return str;
    }
}