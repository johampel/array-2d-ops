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
package de.hipphampel.array2dops.model;

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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.hipphampel.array2dops.geom.Transformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ReadOnlyImplTest {

  @Test
  public void isArrayOwner() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.isArrayOwner()).isFalse();
  }

  @Test
  public void getData() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThatThrownBy(array::getData).isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  public void toArray() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.toArray())
        .isEqualTo(new char[]{'a', 'b', 'c', 'd', 'e', 'f'})
        .isNotSameAs(base.getData());
  }

  @Test
  public void getWidth() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.getWidth()).isEqualTo(3);
  }

  @Test
  public void getHeight() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.getHeight()).isEqualTo(2);
  }

  @ParameterizedTest
  @CsvSource({
      "-1, -1, false",
      " 0,  0, true",
      " 1,  1, true",
      " 2,  1, true",
      " 3,  2, false",
  })
  public void isCoordinateInArray(int x, int y, boolean expected) {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.isCoordinateInArray(x, y)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "-1, -1,  1,  1, false",
      " 0,  0,  3,  2, true",
      " 1,  1, -1, -1, false",
      " 0,  0,  4,  3, false",
  })
  public void isRegionInArray(int x, int y, int width, int height, boolean expected) {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.isRegionInArray(x, y, width, height)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0, a",
      "2, 1, f",
      "1, 0, b"
  })
  public void get_ok(int x, int y, char expected) {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.get(x, y)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "-1,  0",
      " 3,  0",
      " 0, -1",
      " 1,  2",
  })
  public void get_fail(int x, int y) {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThatThrownBy(() -> array.get(x, y)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0, a",
      "2, 1, f",
      "5, 0, f", // Unsafe
  })
  public void getUnsafe_ok(int x, int y, char expected) {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.getUnsafe(x, y)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0",
      "2, 1",
      "1, 0"
  })
  public void set_readonly(int x, int y) {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThatThrownBy(() -> array.set(x, y, 'x').get(x, y)).isInstanceOf(
        UnsupportedOperationException.class);
  }

  @ParameterizedTest
  @CsvSource({
      "-1,  0",
      " 3,  0",
      " 0, -1",
      " 1,  2",
  })
  public void set_fail(int x, int y) {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThatThrownBy(() -> array.set(x, y, 'x')).isInstanceOf(
        ArrayIndexOutOfBoundsException.class);
  }


  @ParameterizedTest
  @CsvSource({
      "0, 0",
      "2, 1",
      "5, 0", // Unsafe
  })
  public void setUnsafe_readonly(int x, int y) {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThatThrownBy(() -> array.setUnsafe(x, y, 'x'));
  }

  @Test
  public void isReadOnly() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.isReadOnly()).isTrue();
  }

  @Test
  public void toReadOnly() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.toReadOnly()).isSameAs(array);
  }

  @Test
  public void copy() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    Char2DArray copy = array.copy();
    assertThat(copy).isInstanceOf(Char2DArrayImpl.class)
        .isNotSameAs(array)
        .isEqualTo(base)
        .isEqualTo(array);
    assertThat(copy.getData()).isNotSameAs(base.getData())
        .isEqualTo(base.getData());
  }

  @Test
  public void shallowRegion_sameSize() {
    Char2DArray base = toChar2DArray("""
        .........
        ..abc....
        ..def....
        .........
        .........
        .........
        """);
    Char2DArray array = base.toReadOnly();

    assertThat(array.shallowRegion(0, 0, array.getWidth(), array.getHeight())).isSameAs(array);
  }

  @Test
  public void shallowRegion_smallerSize() {
    Char2DArray base = toChar2DArray("""
        .........
        ..abc....
        ..def....
        .........
        .........
        .........
        """);
    Char2DArray array = base.toReadOnly();

    Char2DArray region = array.shallowRegion(2, 1, 3, 2);
    assertThat(region).isInstanceOf(SubChar2DArrayImpl.class);
    assertThat(region.printToString("", ":")).isEqualTo("abc:def");

    assertThatThrownBy(() -> region.set(0, 0, 'x')).isInstanceOf(
        UnsupportedOperationException.class);
  }

  @Test
  public void shallowRegion_illegal() {
    Char2DArray base = toChar2DArray("""
        .........
        ..abc....
        ..def....
        .........
        .........
        .........
        """);
    Char2DArray array = base.toReadOnly();

    assertThatThrownBy(() -> array.shallowRegion(-1, -1, 0, 0)).isInstanceOf(
        ArrayIndexOutOfBoundsException.class);
  }

  @Test
  public void copyRegion() {
    Char2DArray base = toChar2DArray("""
        .........
        ..abc....
        ..def....
        .........
        .........
        .........
        """);
    Char2DArray array = base.toReadOnly();

    Char2DArray region = array.copyRegion(2, 1, 3, 2);
    assertThat(region).isInstanceOf(Char2DArrayImpl.class);
    assertThat(region.printToString("", ":")).isEqualTo("abc:def");

    region.set(0, 0, 'x');
    assertThat(array.get(2, 1)).isNotEqualTo('x');

  }

  @Test
  public void copyRegion_illegal() {
    Char2DArray base = toChar2DArray("""
        .........
        ..abc....
        ..def....
        .........
        .........
        .........
        """);
    Char2DArray array = base.toReadOnly();

    assertThatThrownBy(() -> array.copyRegion(-1, -1, 0, 0)).isInstanceOf(
        ArrayIndexOutOfBoundsException.class);
  }

  @Test
  public void shallowTransform_identity() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    Char2DArray transformed = array.shallowTransform(Transformation.IDENTITY);
    assertThat(transformed).isSameAs(array);
  }

  @Test
  public void shallowTransform_valid() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    Char2DArray transformed = array.shallowTransform(Transformation.FLIP_Y);
    assertThat(transformed).isInstanceOf(TransformChar2DArrayImpl.class);
    assertThat(transformed.printToString("", ":")).isEqualTo("def:abc");

    assertThatThrownBy(() -> transformed.set(0, 0, 'x')).isInstanceOf(
        UnsupportedOperationException.class);
  }

  @Test
  public void copyTransform_valid() {
    Char2DArray base = toChar2DArray("""
        abc
        def
        """);
    Char2DArray array = base.toReadOnly();

    Char2DArray transformed = array.copyTransform(Transformation.FLIP_Y);
    assertThat(transformed).isInstanceOf(Char2DArrayImpl.class);
    assertThat(transformed.printToString("", ":")).isEqualTo("def:abc");

    transformed.set(0, 0, 'x');
    assertThat(array.printToString("", ":")).isEqualTo("abc:def");
  }
}
