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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TransformImplTest {

  private Char2DArray root;

  @BeforeEach
  public void beforeEach() {
    root = toChar2DArray("""
        abcdefghijk
        lmnopqrstuv
        wxyzabcdefg
        hijklmnopqr
        stuvwxyzabc
        """);
  }

  @Test
  public void isArrayOwner() {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_Y);

    assertThat(array.isArrayOwner()).isFalse();
  }

  @Test
  public void getData() {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_Y);

    assertThatThrownBy(array::getData).isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  public void toArray() {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_Y);

    assertThat(array.toArray())
        .isEqualTo(new char[]{
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
        });
  }

  @Test
  public void getWidth() {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_Y);

    assertThat(array.getWidth()).isEqualTo(11);
  }

  @Test
  public void getHeight() {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_Y);

    assertThat(array.getHeight()).isEqualTo(5);
  }

  @ParameterizedTest
  @CsvSource({
      "-1, -1, false",
      " 0,  0, true",
      "10,  4, true",
      " 8,  2, true",
      "11,  5, false",
  })
  public void isCoordinateInArray(int x, int y, boolean expected) {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_Y);

    assertThat(array.isCoordinateInArray(x, y)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "-1, -1,  1,  1, false",
      " 0,  0,  3,  2, true",
      " 1,  1, -1, -1, false",
      " 0,  0, 12,  6, false",
  })
  public void isRegionInArray(int x, int y, int width, int height, boolean expected) {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_Y);

    assertThat(array.isRegionInArray(x, y, width, height)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0, s",
      "0, 1, t",
      "1, 0, h",
      "1, 1, i",
      "2, 1, x",
  })
  public void get_ok(int x, int y, char expected) {
    // shwla
    // tixmb
    // ujync
    // ...
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    assertThat(array.get(x, y)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "-1,  0",
      "11,  0",
      " 0, -1",
      " 1,  5",
  })
  public void get_fail(int x, int y) {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_X);

    assertThatThrownBy(() -> array.get(x, y)).isInstanceOf(ArrayIndexOutOfBoundsException.class);
  }

  @ParameterizedTest
  @CsvSource({
      " 0, 0, k",
      " 2, 1, t",
      "11, 5, c", // Unsafe
  })
  public void getUnsafe_ok(int x, int y, char expected) {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_X);

    assertThat(array.getUnsafe(x, y)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "0, 0",
      "2, 1",
      "1, 0"
  })
  public void set_ok(int x, int y) {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_X);

    assertThat(array.set(x, y, 'x').get(x, y)).isEqualTo('x');
  }

  @ParameterizedTest
  @CsvSource({
      "-1,  0",
      "11,  0",
      " 0, -1",
      " 1,  5",
  })
  public void set_fail(int x, int y) {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_X);

    assertThatThrownBy(() -> array.set(x, y, 'x')).isInstanceOf(
        ArrayIndexOutOfBoundsException.class);
  }


  @ParameterizedTest
  @CsvSource({
      " 0,  0",
      " 2,  1",
      "-1, -1", // Unsafe
  })
  public void setUnsafe_ok(int x, int y) {
    Char2DArray array = root.shallowRegion(1, 1, 9, 3)
        .shallowTransform(Transformation.TRANSPOSE);

    array.setUnsafe(x, y, 'x');
    assertThat(array.getUnsafe(x, y)).isEqualTo('x');
  }

  @Test
  public void isReadOnly() {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_TRANSPOSE);

    assertThat(array.isReadOnly()).isFalse();
  }

  @Test
  public void toReadOnly() {
    Char2DArray array = root.shallowTransform(Transformation.FLIP_TRANSPOSE);

    assertThat(array.toReadOnly()).isInstanceOf(ReadOnlyChar2DArrayImpl.class);
  }

  @Test
  public void copy() {
    Char2DArray array = root.shallowTransform(Transformation.IDENTITY);

    Char2DArray copy = array.copy();
    assertThat(copy).isInstanceOf(Char2DArrayImpl.class)
        .isNotSameAs(array)
        .isEqualTo(array);
    assertThat(copy.getData()).isNotSameAs(array.getData())
        .isEqualTo(array.getData());
  }

  @Test
  public void shallowRegion_sameSize() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    assertThat(array.shallowRegion(0, 0, array.getWidth(), array.getHeight())).isSameAs(array);
  }

  @Test
  public void shallowRegion_smallerSize() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    Char2DArray region = array.shallowRegion(2, 1, 3, 2);
    assertThat(region).isInstanceOf(SubChar2DArrayImpl.class);
    assertThat(region.printToString("", ":")).isEqualTo("xmb:ync");

    region.set(0, 0, 'x');
    assertThat(array.get(2, 1)).isEqualTo('x');
  }

  @Test
  public void shallowRegion_illegal() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    assertThatThrownBy(() -> array.shallowRegion(-1, -1, 0, 0)).isInstanceOf(
        ArrayIndexOutOfBoundsException.class);
  }

  @Test
  public void copyRegion() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    Char2DArray region = array.copyRegion(2, 1, 3, 2);
    assertThat(region).isInstanceOf(Char2DArrayImpl.class);
    assertThat(region.printToString("", ":")).isEqualTo("xmb:ync");

    region.set(0, 0, '!');
    assertThat(array.printToString("", ":")).doesNotContain("!");
  }

  @Test
  public void copyRegion_illegal() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    assertThatThrownBy(() -> array.copyRegion(-1, -1, 0, 0)).isInstanceOf(
        ArrayIndexOutOfBoundsException.class);
  }

  @Test
  public void shallowTransform_identity() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    Char2DArray transformed = array.shallowTransform(Transformation.IDENTITY);
    assertThat(transformed).isSameAs(array);
  }

  @Test
  public void shallowTransform_inverse() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    Char2DArray transformed = array.shallowTransform(Transformation.ROTATE_CLOCKWISE_270);
    assertThat(transformed).isSameAs(root);
  }

  @Test
  public void shallowTransform_valid() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    Char2DArray transformed = array.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);
    assertThat(transformed).isInstanceOf(TransformChar2DArrayImpl.class);
    assertThat(transformed.printToString("", ":")).isEqualTo("cbazyxwvuts:rqponmlkjih:gfedcbazyxw:vutsrqponml:kjihgfedcba");

    transformed.set(0, 0, 'x');
    assertThat(array.printToString("", ":")).isEqualTo("shwla:tixmb:ujync:vkzod:wlape:xmbqf:yncrg:zodsh:apeti:bqfuj:xrgvk");
  }

  @Test
  public void copyTransform_valid() {
    Char2DArray array = root.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);

    Char2DArray transformed = array.copyTransform(Transformation.FLIP_Y);
    assertThat(transformed).isInstanceOf(Char2DArrayImpl.class);
    assertThat(transformed.printToString("", ":")).isEqualTo("crgvk:bqfuj:apeti:zodsh:yncrg:xmbqf:wlape:vkzod:ujync:tixmb:shwla");

    transformed.set(0, 0, '!');
    assertThat(array.printToString("", ":")).doesNotContain("!");
  }


}
