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
package de.hipphampel.array2dops.matrix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.hipphampel.array2dops.model.Long2DArray;
import de.hipphampel.array2dops.model.Object2DArray;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class BigDecimalMatrixTest {

  @Test
  public void newMatrix_noData() {
    Object2DArray<BigDecimal> matrix = BigDecimalMatrix.newMatrix(3, 2);
    assertThat(matrix.printToString()).isEqualTo("""
        0, 0, 0
        0, 0, 0""");
  }

  @Test
  public void newMatrix_withData() {
    Object2DArray<BigDecimal> matrix = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    assertThat(matrix.printToString()).isEqualTo("""
        1, 2, 3
        4, 5, 6""");
  }

  @Test
  public void newUnitMatrix() {
    Object2DArray<BigDecimal> matrix = BigDecimalMatrix.newUnitMatrix(3);
    assertThat(matrix.printToString()).isEqualTo("""
        1, 0, 0
        0, 1, 0
        0, 0, 1""");
  }

  @Test
  public void add_ok() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 3, 2);

    Object2DArray<BigDecimal> result = BigDecimalMatrix.add(left, right);

    assertThat(result.printToString()).isEqualTo("""
        7, 7, 7
        7, 7, 7"""
    );
    assertThat(left.printToString()).isEqualTo("""
        1, 2, 3
        4, 5, 6"""
    );
    assertThat(right.printToString()).isEqualTo("""
        6, 5, 4
        3, 2, 1"""
    );
  }

  @Test
  public void add_fail() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 2, 3);

    assertThatThrownBy(() -> BigDecimalMatrix.add(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void addInplace_ok() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 3, 2);

    Object2DArray<BigDecimal> result = BigDecimalMatrix.addInplace(left, right);

    assertThat(result).isSameAs(left);
    assertThat(left.printToString()).isEqualTo("""
        7, 7, 7
        7, 7, 7"""
    );
    assertThat(right.printToString()).isEqualTo("""
        6, 5, 4
        3, 2, 1"""
    );
  }

  @Test
  public void addInplace_fail() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 2, 3);

    assertThatThrownBy(() -> BigDecimalMatrix.addInplace(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void sub_ok() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 3, 2);

    Object2DArray<BigDecimal> result = BigDecimalMatrix.sub(left, right);

    assertThat(result.printToString()).isEqualTo("""
        -5, -3, -1
        1, 3, 5"""
    );
    assertThat(left.printToString()).isEqualTo("""
        1, 2, 3
        4, 5, 6"""
    );
    assertThat(right.printToString()).isEqualTo("""
        6, 5, 4
        3, 2, 1"""
    );
  }

  @Test
  public void sub_fail() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 2, 3);

    assertThatThrownBy(() -> BigDecimalMatrix.sub(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void subInplace_ok() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 3, 2);

    Object2DArray<BigDecimal> result = BigDecimalMatrix.subInplace(left, right);

    assertThat(result).isSameAs(left);
    assertThat(left.printToString()).isEqualTo("""
        -5, -3, -1
        1, 3, 5"""
    );
    assertThat(right.printToString()).isEqualTo("""
        6, 5, 4
        3, 2, 1"""
    );
  }

  @Test
  public void subInplace_fail() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 2, 3);

    assertThatThrownBy(() -> BigDecimalMatrix.addInplace(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void scalarMul() {
    Object2DArray<BigDecimal> arg = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);

    Object2DArray<BigDecimal>  result = BigDecimalMatrix.scalarMul(BigDecimal.valueOf(2), arg);

    assertThat(arg.printToString()).isEqualTo("""
        1, 2, 3
        4, 5, 6"""
    );
    assertThat(result.printToString()).isEqualTo("""
        2, 4, 6
        8, 10, 12"""
    );
  }

  @Test
  public void scalarMulInplace() {
    Object2DArray<BigDecimal> arg = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);

    Object2DArray<BigDecimal>  result = BigDecimalMatrix.scalarMulInplace(BigDecimal.valueOf(2), arg);

    assertThat(arg).isSameAs(result);
    assertThat(result.printToString()).isEqualTo("""
        2, 4, 6
        8, 10, 12"""
    );
  }

  @Test
  public void mul_ok_1() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 2, 3);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 3, 2);

    Object2DArray<BigDecimal> result = BigDecimalMatrix.mul(left, right);

    assertThat(result.printToString()).isEqualTo("""
        12, 9, 6
        30, 23, 16
        48, 37, 26"""
    );
    assertThat(left.printToString()).isEqualTo("""
        1, 2
        3, 4
        5, 6"""
    );
    assertThat(right.printToString()).isEqualTo("""
        6, 5, 4
        3, 2, 1"""
    );

  }

  @Test
  public void mul_ok_2() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 2, 3);

    Object2DArray<BigDecimal> result = BigDecimalMatrix.mul(left, right);

    assertThat(result.printToString()).isEqualTo("""
        20, 14
        56, 41"""
    );
    assertThat(left.printToString()).isEqualTo("""
        1, 2, 3
        4, 5, 6"""
    );
    assertThat(right.printToString()).isEqualTo("""
        6, 5
        4, 3
        2, 1"""
    );
  }

  @Test
  public void mul_fail() {
    Object2DArray<BigDecimal> left = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);
    Object2DArray<BigDecimal> right = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(6), BigDecimal.valueOf(5), BigDecimal.valueOf(4),
            BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(1)}, 3, 2);

    assertThatThrownBy(() -> BigDecimalMatrix.mul(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void negate() {
    Object2DArray<BigDecimal> arg = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);

    Object2DArray<BigDecimal> result = BigDecimalMatrix.negate(arg);

    assertThat(result.printToString()).isEqualTo("""
        -1, -2, -3
        -4, -5, -6"""
    );
    assertThat(arg.printToString()).isEqualTo("""
        1, 2, 3
        4, 5, 6"""
    );
  }

  @Test
  public void negateInplace() {
    Object2DArray<BigDecimal> arg = BigDecimalMatrix.newMatrix(
        new BigDecimal[]{
            BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
            BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, 3, 2);

    Object2DArray<BigDecimal> result = BigDecimalMatrix.negateInplace(arg);

    assertThat(arg).isSameAs(result);
    assertThat(result.printToString()).isEqualTo("""
        -1, -2, -3
        -4, -5, -6"""
    );
  }

}
