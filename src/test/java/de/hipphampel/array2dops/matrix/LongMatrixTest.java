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
import org.junit.jupiter.api.Test;

public class LongMatrixTest {

  @Test
  public void newMatrix_noData() {
    Long2DArray matrix = LongMatrix.newMatrix(3, 2);
    assertThat(matrix.printToString()).isEqualTo("""
        0, 0, 0
        0, 0, 0""");
  }

  @Test
  public void newMatrix_withData() {
    Long2DArray matrix = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    assertThat(matrix.printToString()).isEqualTo("""
        1, 2, 3
        4, 5, 6""");
  }

  @Test
  public void newUnitMatrix() {
    Long2DArray matrix = LongMatrix.newUnitMatrix(3);
    assertThat(matrix.printToString()).isEqualTo("""
        1, 0, 0
        0, 1, 0
        0, 0, 1""");
  }

  @Test
  public void add_ok() {
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 3, 2);

    Long2DArray result = LongMatrix.add(left, right);

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
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 2, 3);

    assertThatThrownBy(() -> LongMatrix.add(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void addInplace_ok() {
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 3, 2);

    Long2DArray result = LongMatrix.addInplace(left, right);

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
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 2, 3);

    assertThatThrownBy(() -> LongMatrix.addInplace(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void sub_ok() {
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 3, 2);

    Long2DArray result = LongMatrix.sub(left, right);

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
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 2, 3);

    assertThatThrownBy(() -> LongMatrix.sub(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void subInplace_ok() {
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 3, 2);

    Long2DArray result = LongMatrix.subInplace(left, right);

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
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 2, 3);

    assertThatThrownBy(() -> LongMatrix.addInplace(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void scalarMul() {
    Long2DArray arg = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);

    Long2DArray result = LongMatrix.scalarMul(2, arg);

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
    Long2DArray arg = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);

    Long2DArray result = LongMatrix.scalarMulInplace(2, arg);

    assertThat(arg).isSameAs(result);
    assertThat(result.printToString()).isEqualTo("""
        2, 4, 6
        8, 10, 12"""
    );
  }

  @Test
  public void mul_ok_1() {
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 2, 3);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 3, 2);

    Long2DArray result = LongMatrix.mul(left, right);

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
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 2, 3);

    Long2DArray result = LongMatrix.mul(left, right);

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
    Long2DArray left = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);
    Long2DArray right = LongMatrix.newMatrix(new long[]{6, 5, 4, 3, 2, 1}, 3, 2);

    assertThatThrownBy(() -> LongMatrix.mul(left, right)).isInstanceOf(
        IllegalArgumentException.class);
  }

  @Test
  public void negate() {
    Long2DArray arg = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);

    Long2DArray result = LongMatrix.negate(arg);

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
    Long2DArray arg = LongMatrix.newMatrix(new long[]{1, 2, 3, 4, 5, 6}, 3, 2);

    Long2DArray result = LongMatrix.negateInplace(arg);

    assertThat(arg).isSameAs(result);
    assertThat(result.printToString()).isEqualTo("""
        -1, -2, -3
        -4, -5, -6"""
    );
  }

}
