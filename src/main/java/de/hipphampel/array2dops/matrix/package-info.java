/*
 * The MIT License
 * Copyright © 2023 Johannes Hampel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * Provides basic matrix operations on arrays.
 * <p>
 * There are implementations for each primitive type as well as for {@link java.math.BigDecimal}.
 * For example, for matrices based on {@code doubles}, you can use the class
 * {@link de.hipphampel.array2dops.matrix.DoubleMatrix}, which provides a set of static methods.
 * <p>
 * Some examples:
 * <pre><tt>
 *   Double2DArray matrix1 = DoubleMatrix.newMatrix(new double[]{1, 2, 3, 4, 5, 6}, 3, 2); // Creates a new 3x2 matrix
 *   Double2DArray matrix2 = DoubleMatrix.newMatrix(new double[]{7, 8, 9, 0, 1, 2}, 3, 2); // Creates a new 3x2 matrix
 *
 *   Double2DArray result1 = DoubleMatrix.add(matrix1, matrix2); // add
 *   Double2DArray result2 = DoubleMatrix.mul(matrix1, DoubleMatrix.transposeInplace(matrix2)); // mul
 *   Double2DArray result3 = DoubleMatrix.scalarMul(matrix1, matrix2); // scalar multiplication
 * </tt></pre>
 * <p>
 * For a complete list of the available methods, please refer to the class documentation.
 */
package de.hipphampel.array2dops.matrix;