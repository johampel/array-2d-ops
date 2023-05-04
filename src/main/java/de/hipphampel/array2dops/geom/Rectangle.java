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

import java.util.Optional;

public record Rectangle(int x, int y, int width, int height) {

  public Rectangle {
    if (width < 0) {
      throw new IllegalArgumentException("width=" + width + " must not be negative");
    }
    if (height < 0) {
      throw new IllegalArgumentException("height=" + height + " must not be negative");
    }
  }

  /**
   * Gets the left most x coordinate,
   *
   * @return The left most x coordinate
   */
  public int getLeft() {
    return x;
  }

  /**
   * Gets the right most x coordinate.
   *
   * @return The right most x coordinate
   */
  public int getRight() {
    return x + width - 1;
  }

  /**
   * Gets the top most y coordinate
   *
   * @return The top most y coordinate
   */
  public int getTop() {
    return y;
  }

  /**
   * Gets the bottom most y coordinate.
   *
   * @return The bottom most y coordinate.
   */
  public int getBottom() {
    return y + height - 1;
  }

  /**
   * Checks, whether the point at the specified coordinates is in this rect.
   *
   * @param x The X coordinate of the point
   * @param y The Y coordinate of the point
   * @return {@code true}, if the point is in this rect
   */
  public boolean contains(int x, int y) {
    return getLeft() <= x && x <= getRight() && getTop() <= y && y <= getBottom();
  }

  /**
   * Computes the intersection of this rect and the given line.
   *
   * @param line The line to intersect with
   * @return The intersected line or nothing, if no intersection exists
   */
  public Optional<Line> intersect(Line line) {
    // Trivial case?
    if (contains(line.x0(), line.y0()) && contains(line.x1(), line.y1())) {
      return Optional.of(line);
    }

    Double m = line.getSlope().orElse(null);
    if (m != null) {
      // Not non-vertical line - we have to figure out, whethe the line intersects or not first
      double b = line.y0() - m * line.x0(); // y = m*x+b
      double y_at_0 = m * x + b;
      double y_at_w = m * (x + width) + b;

      if ((y_at_0 < getTop() && y_at_w < getTop()) ||
          (y_at_0 > getBottom() && y_at_w > getBottom())) {
        // line does not cross rect
        return Optional.empty();
      }

      double lower_x = getLeft();
      double upper_x = getRight();
      if (m != 0.0) {
        double l = (y - b) / m;
        double u = (y + height - b) / m;
        lower_x = Math.max(x, Math.min(l, u));
        upper_x = Math.min(x + width, Math.max(l, u));
      }

      double min = Math.max(lower_x, Math.min(line.x0(), line.x1()));
      double max = Math.min(upper_x, Math.max(line.x0(), line.x1()));
      return min <= max ? Optional.of(
          new Line((int) min, (int) (m * min + b), (int) max, (int) (m * max + b)))
          : Optional.empty();
    } else if (contains(line.x0(), getTop())) {
      // The line is vertical somewhere in the same columns of the rect, but might be (partially) outside of the rect
      int min = Math.max(getTop(), Math.min(line.y0(), line.y1()));
      int max = Math.min(getBottom(), Math.max(line.y0(), line.y1()));
      return min <= max ? Optional.of(new Line(line.x0(), min, line.x1(), max)) : Optional.empty();
    } else {
      // Guaranteed outside the rect
      return Optional.empty();
    }
  }

  /**
   * Computes the intersection of this rect and the given one.
   *
   * @param other The rect to intersect with
   * @return The intersection or nothing, if no intersection exists
   */
  public Optional<Rectangle> intersect(Rectangle other) {
    int intersectX = Math.max(getLeft(), other.getLeft());
    int intersectY = Math.max(getTop(), other.getTop());
    int intersectWidth = Math.min(getRight(), other.getRight()) - intersectX;
    int intersectHeight = Math.min(getBottom(), other.getBottom()) - intersectY;

    if (intersectWidth < 0 || intersectHeight < 0) {
      return Optional.empty();
    }

    return Optional.of(
        new Rectangle(intersectX, intersectY, intersectWidth + 1, intersectHeight + 1));
  }
}
