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
package de.hipphampel.array2dops.geom;

/*-
 * #%L
 * array-2d-ops
 * %%
 * Copyright (C) 2023 Johannes Hampel
 * %%
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
 * #L%
 */

import static de.hipphampel.array2dops.model.TestUtils.toChar2DArray;
import static org.assertj.core.api.Assertions.assertThat;

import de.hipphampel.array2dops.geom.Transformation;
import de.hipphampel.array2dops.geom.TransformationMatrix;
import de.hipphampel.array2dops.model.Char2DArray;
import org.junit.jupiter.api.Test;

public class TransformationTest {

  @Test
  public void identity() {
    Char2DArray in = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    Char2DArray expected = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    TransformationMatrix matrix = Transformation.IDENTITY.getTransformationMatrix(in.getWidth(),
        in.getHeight());
    for (int y = 0; y < in.getHeight(); y++) {
      for (int x = 0; x < in.getWidth(); x++) {
        int xt = matrix.transformX(x, y);
        int yt = matrix.transformY(x, y);

        assertThat(in.getUnsafe(x, y)).isEqualTo(expected.getUnsafe(xt, yt));
      }
    }
  }

  @Test
  public void transpose() {
    Char2DArray in = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    Char2DArray expected = toChar2DArray("""
        aei
        bfj
        cgk
        dhl
        """);
    TransformationMatrix matrix = Transformation.TRANSPOSE.getTransformationMatrix(in.getWidth(),
        in.getHeight());
    for (int y = 0; y < in.getHeight(); y++) {
      for (int x = 0; x < in.getWidth(); x++) {
        int xt = matrix.transformX(x, y);
        int yt = matrix.transformY(x, y);

        assertThat(in.getUnsafe(x, y)).isEqualTo(expected.getUnsafe(xt, yt));
      }
    }
  }

  @Test
  public void rotate_clockwise_90() {
    Char2DArray in = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    Char2DArray expected = toChar2DArray("""
        iea
        jfb
        kgc
        lhd
        """);
    TransformationMatrix matrix = Transformation.ROTATE_CLOCKWISE_90.getTransformationMatrix(
        in.getWidth(),
        in.getHeight());
    for (int y = 0; y < in.getHeight(); y++) {
      for (int x = 0; x < in.getWidth(); x++) {
        int xt = matrix.transformX(x, y);
        int yt = matrix.transformY(x, y);

        assertThat(in.getUnsafe(x, y)).isEqualTo(expected.getUnsafe(xt, yt));
      }
    }
  }

  @Test
  public void rotate_clockwise_180() {
    Char2DArray in = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    Char2DArray expected = toChar2DArray("""
        lkji
        hgfe
        dcba
        """);
    TransformationMatrix matrix = Transformation.ROTATE_CLOCKWISE_180.getTransformationMatrix(
        in.getWidth(),
        in.getHeight());
    for (int y = 0; y < in.getHeight(); y++) {
      for (int x = 0; x < in.getWidth(); x++) {
        int xt = matrix.transformX(x, y);
        int yt = matrix.transformY(x, y);

        assertThat(in.getUnsafe(x, y)).isEqualTo(expected.getUnsafe(xt, yt));
      }
    }
  }

  @Test
  public void rotate_clockwise_270() {
    Char2DArray in = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    Char2DArray expected = toChar2DArray("""
        dhl
        cgk
        bfj
        aei
        """);
    TransformationMatrix matrix = Transformation.ROTATE_CLOCKWISE_270.getTransformationMatrix(
        in.getWidth(),
        in.getHeight());
    for (int y = 0; y < in.getHeight(); y++) {
      for (int x = 0; x < in.getWidth(); x++) {
        int xt = matrix.transformX(x, y);
        int yt = matrix.transformY(x, y);

        assertThat(in.getUnsafe(x, y)).isEqualTo(expected.getUnsafe(xt, yt));
      }
    }
  }

  @Test
  public void flip_x() {
    Char2DArray in = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    Char2DArray expected = toChar2DArray("""
        dcba
        hgfe
        lkji
        """);
    TransformationMatrix matrix = Transformation.FLIP_X.getTransformationMatrix(
        in.getWidth(),
        in.getHeight());
    for (int y = 0; y < in.getHeight(); y++) {
      for (int x = 0; x < in.getWidth(); x++) {
        int xt = matrix.transformX(x, y);
        int yt = matrix.transformY(x, y);

        assertThat(in.getUnsafe(x, y)).isEqualTo(expected.getUnsafe(xt, yt));
      }
    }
  }

  @Test
  public void flip_y() {
    Char2DArray in = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    Char2DArray expected = toChar2DArray("""
        ijkl
        efgh
        abcd
        """);
    TransformationMatrix matrix = Transformation.FLIP_Y.getTransformationMatrix(
        in.getWidth(),
        in.getHeight());
    for (int y = 0; y < in.getHeight(); y++) {
      for (int x = 0; x < in.getWidth(); x++) {
        int xt = matrix.transformX(x, y);
        int yt = matrix.transformY(x, y);

        assertThat(in.getUnsafe(x, y)).isEqualTo(expected.getUnsafe(xt, yt));
      }
    }
  }

  @Test
  public void flip_transpose() {
    Char2DArray in = toChar2DArray("""
        abcd
        efgh
        ijkl
        """);
    Char2DArray expected = toChar2DArray("""
        lhd
        kgc
        jfb
        iea
        """);
    TransformationMatrix matrix = Transformation.FLIP_TRANSPOSE.getTransformationMatrix(
        in.getWidth(),
        in.getHeight());
    for (int y = 0; y < in.getHeight(); y++) {
      for (int x = 0; x < in.getWidth(); x++) {
        int xt = matrix.transformX(x, y);
        int yt = matrix.transformY(x, y);

        assertThat(in.getUnsafe(x, y)).isEqualTo(expected.getUnsafe(xt, yt));
      }
    }
  }
}
