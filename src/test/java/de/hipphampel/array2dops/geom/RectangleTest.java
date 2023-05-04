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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RectangleTest {

  private static final Rectangle RECT = new Rectangle(3, 5, 8, 13);

  @ParameterizedTest
  @CsvSource({
      "  2,  5, false",
      "  3,  4, false",
      "  3,  5, true",
      " 10,  5, true",
      " 11,  5, false",
      " 10,  4, false",
      "  2, 10, false",
      "  3, 10, true",
      "  5, 10, true",
      " 10, 10, true",
      " 14, 10, false",
      " 5,  16, true",
      " 5,  17, true",
      " 5,  18, false",
  })
  public void contains_point(int x, int y, boolean expected) {
    assertThat(RECT.contains(x, y)).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      " 3,  0,  8,  4,  ,   ,   ,   ", // Outside (above)
      " 0,  5,  2, 13,  ,   ,   ,   ", // Outside (Left of)
      " 11, 5,  2, 13,  ,   ,   ,   ", // Outside (Right of)
      " 3, 18,  2, 13,  ,   ,   ,   ", // Outside (Below)
      " 3,  5,  8, 13,  3,  5, 8, 13", // Same
      " 2,  4, 10, 15,  3,  5, 8, 13", // Bigger
      " 0,  0, 10, 15,  3,  5, 7, 10", // Intersection bottom right
      "10, 10, 10, 15, 10, 10, 1,  8"  // Intersection left top
  })
  public void intersect_rectangle(int x, int y, int w, int h, Integer ex, Integer ey, Integer ew,
      Integer eh) {
    assertThat(RECT.intersect(new Rectangle(x, y, w, h)))
        .isEqualTo(Optional.ofNullable(ex).map(ignore -> new Rectangle(ex, ey, ew, eh)));
  }

  @ParameterizedTest
  @CsvSource({
      // Horizontal lines
      " 0,  0, 99,  0,   ,   ,   ,   ", // Outside (above)
      " 0, 99, 99, 99,   ,   ,   ,   ", // Outside (below)
      " 0,  9, 99,  9,  3,  9, 10,  9",
      " 5,  9, 99,  9,  5,  9, 10,  9",
      " 0,  9,  5,  9,  3,  9,  5,  9",
      // Vertical lines
      " 0,  0,  0, 99,   ,   ,   ,   ", // Outside (left of)
      "99,  0, 99, 99,   ,   ,   ,   ", // Outside (right of)
      " 8,  0,  8, 99,  8,  5,  8, 17",
      " 8,  0,  8, 10,  8,  5,  8, 10",
      " 8, 10,  8, 99,  8,  10, 8, 17",
      // Other lines
      " 0,  0, 99, 99,  5,  5, 11, 11",
      " 0, 17, 17,  0,  3, 14, 11,  6",
      " 5,  5, 40, 20,  5,  5, 11,  7",
      " 5,  5, 20, 40,  5,  5, 10, 18",
  })
  public void intersect_line(int x0, int y0, int x1, int y1, Integer ex0, Integer ey0, Integer ex1,
      Integer ey1) {
    // (3,5) - (11/18)
    assertThat(RECT.intersect(new Line(x0, y0, x1, y1)))
        .isEqualTo(Optional.ofNullable(ex0).map(ignore -> new Line(ex0, ey0, ex1, ey1)));
  }
}
