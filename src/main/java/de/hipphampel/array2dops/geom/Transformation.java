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

/**
 * Represents a transformation that can be applied to a 2D array.
 * <p>
 * Such transformations are rotating it by 90, 180, 270 degree, flipping the content on X or Y axis
 * or transposing it.
 *
 * @see TransformationMatrix
 */
public enum Transformation {

  IDENTITY(false),
  TRANSPOSE(true),
  ROTATE_CLOCKWISE_90(true),
  ROTATE_CLOCKWISE_180(false),
  ROTATE_CLOCKWISE_270(true),
  FLIP_TRANSPOSE(true),
  FLIP_X(false),
  FLIP_Y(false);


  private final boolean swappingWidthAndHeight;
  private static final Transformation[][] CHAIN_MAP = new Transformation[][]{
      {IDENTITY,
          TRANSPOSE, ROTATE_CLOCKWISE_90, ROTATE_CLOCKWISE_180, ROTATE_CLOCKWISE_270,
          FLIP_TRANSPOSE, FLIP_X, FLIP_Y},
      {TRANSPOSE,
          IDENTITY, FLIP_X, FLIP_TRANSPOSE, FLIP_Y,
          ROTATE_CLOCKWISE_180, ROTATE_CLOCKWISE_90, ROTATE_CLOCKWISE_270},
      {ROTATE_CLOCKWISE_90,
          FLIP_Y, ROTATE_CLOCKWISE_180, ROTATE_CLOCKWISE_270, IDENTITY,
          FLIP_X, TRANSPOSE, FLIP_TRANSPOSE},
      {ROTATE_CLOCKWISE_180,
          FLIP_TRANSPOSE, ROTATE_CLOCKWISE_270, IDENTITY, ROTATE_CLOCKWISE_90,
          TRANSPOSE, FLIP_Y, FLIP_X},
      {ROTATE_CLOCKWISE_270,
          FLIP_X, IDENTITY, ROTATE_CLOCKWISE_90, ROTATE_CLOCKWISE_180,
          FLIP_Y, FLIP_TRANSPOSE, TRANSPOSE},
      {FLIP_TRANSPOSE,
          ROTATE_CLOCKWISE_180, FLIP_Y, TRANSPOSE, FLIP_X,
          IDENTITY, ROTATE_CLOCKWISE_270, ROTATE_CLOCKWISE_90},
      {FLIP_X,
          ROTATE_CLOCKWISE_270, FLIP_TRANSPOSE, FLIP_Y, TRANSPOSE,
          ROTATE_CLOCKWISE_90, IDENTITY, ROTATE_CLOCKWISE_180},
      {FLIP_Y,
          ROTATE_CLOCKWISE_90, TRANSPOSE, FLIP_X, FLIP_TRANSPOSE,
          ROTATE_CLOCKWISE_270, ROTATE_CLOCKWISE_180, IDENTITY}
  };

  Transformation(boolean swappingWidthAndHeight) {
    this.swappingWidthAndHeight = swappingWidthAndHeight;
  }

  /**
   * Indicates, whether the role of the array height and width is swapped by this transformation.
   *
   * @return {@code true}, if swapped.
   */
  public boolean isSwappingWidthAndHeight() {
    return swappingWidthAndHeight;
  }

  /**
   * Chains the {@code first} with the {@code further} transformations and builds the resulting
   * one.
   * <p/>
   * For example, chaining a {@link #FLIP_X} with a {@link #TRANSPOSE} is the same as
   * {@link #ROTATE_CLOCKWISE_270}.
   *
   * @param first   First {@code Transformation}
   * @param further Further {@code Transformations}
   * @return Resulting {@code Transformation}
   */
  public static Transformation chain(Transformation first, Transformation... further) {
    Transformation current = first;
    for (Transformation next : further) {
      current = CHAIN_MAP[current.ordinal()][next.ordinal()];
    }
    return current;
  }

  public TransformationMatrix getTransformationMatrix(int width, int height) {
    return switch (this) {

      // a b c    a b c
      // d e f -> d e f
      case IDENTITY -> new TransformationMatrix(0, 0, 1, 0, 0, 1);

      // a b c    a d
      // d e f -> b e
      //          c f
      case TRANSPOSE -> new TransformationMatrix(0, 0, 0, 1, 1, 0);

      // a b c    d a
      // d e f -> e b
      //          f c
      case ROTATE_CLOCKWISE_90 -> new TransformationMatrix(height - 1, 0, 0, -1, 1, 0);

      // a b c    f e d
      // d e f -> c b a
      case ROTATE_CLOCKWISE_180 -> new TransformationMatrix(width - 1, height - 1, -1, 0, 0, -1);

      // a b c    c f
      // d e f -> b e
      //          a d
      case ROTATE_CLOCKWISE_270 -> new TransformationMatrix(0, width - 1, 0, 1, -1, 0);

      // a b c    f c
      // d e f -> e b
      //          d a
      case FLIP_TRANSPOSE -> new TransformationMatrix(height - 1, width - 1, 0, -1, -1, 0);

      // a b c    c b a
      // d e f -> f e d
      case FLIP_X -> new TransformationMatrix(width - 1, 0, -1, 0, 0, 1);

      // a b c    d e f
      // d e f -> a b c
      case FLIP_Y -> new TransformationMatrix(0, height - 1, 1, 0, 0, -1);
    };
  }


}
