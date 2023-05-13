package de.hipphampel.array2dops.draw;

import de.hipphampel.array2dops.geom.Line;
import de.hipphampel.array2dops.geom.Point;
import de.hipphampel.array2dops.geom.Rectangle;

public interface DrawOperations<T, I, C extends DrawOperations<T, I, C>> {

  /**
   * Sets the origin of the context.
   * <p>
   * All operations are done relative to this origin.
   *
   * @param x The x coordinate of the origin
   * @param y The y coordinate of the origin
   * @return This instance.
   */
  C origin(int x, int y);

  /**
   * Sets the origin of the context.
   * <p>
   * All operations are done relative to this origin.
   *
   * @param origin The origin
   * @return This instance.
   */
  default C origin(Point origin) {
    return origin(origin.x(), origin.y());
  }

  /**
   * Sets the color.
   *
   * @param color The color to use
   * @return This instance.
   */
  C color(T color);

  /**
   * Fills the cell at the specified coordinate with the current color.
   * <p>
   * If the coordinate is not in array, nothing happens.
   *
   * @param x The X coordinate
   * @param y The Y coordinate
   * @return This instance.
   */
  C set(int x, int y);

  /**
   * Fills the cell at the specified coordinate with the current color.
   * <p>
   * If the coordinate is not in array, nothing happens.
   *
   * @param point The {@code Point} describing the coordinate
   * @return This instance.
   */
  default C set(Point point) {
    return set(point.x(), point.y());
  }

  /**
   * Draws a line from the given start to the end coordinate with the current color.
   * <p>
   * If parts of the line are outside the array, these portions are ignored.
   *
   * @param x0 X coordinate of the start point
   * @param y0 Y coordinate of the start point
   * @param x1 X coordinate of the end point
   * @param y1 Y coordinate of the end point
   * @return This instance.
   */
  C line(int x0, int y0, int x1, int y1);

  /**
   * Draws a line between the given points with the current color.
   * <p>
   * If parts of the line are outside the array, these portions are ignored.
   *
   * @param point1 Start point
   * @param point2 End point
   * @return This instance.
   */
  default C line(Point point1, Point point2) {
    return line(point1.x(), point1.y(), point2.x(), point2.y());
  }

  /**
   * Draws a line with the current color.
   * <p>
   * If parts of the line are outside the array, these portions are ignored.
   *
   * @param line The line
   * @return This instance.
   */
  default C line(Line line) {
    return line(line.x0(), line.y0(), line.x1(), line.y1());
  }

  /**
   * Draws a rect with the current color.
   * <p>
   * If parts of the rect are outside the array, these portions are ignored. In order to draw a
   * filled rect, use {@code fillRect}. The rect is identified by the coordinates of one of the rect
   * diagonals.
   *
   * @param x0 X coordinate of the diagonal start point
   * @param y0 Y coordinate of the diagonal start point
   * @param x1 X coordinate of the diagonal end point
   * @param y1 Y coordinate of the diagonal end point
   * @return This instance.
   */
  C rect(int x0, int y0, int x1, int y1);

  /**
   * Draws a rect with the current color.
   * <p>
   * If parts of the rect are outside the array, these portions are ignored. In order to draw a
   * filled rect, use {@code fillRect}. The rect is identified by the coordinates of one of the rect
   * diagonals.
   *
   * @param rect The rectangle to draw
   * @return This instance.
   */
  default C rect(Rectangle rect) {
    return rect(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom());
  }

  /**
   * Fills a rect with the current color.
   * <p>
   * If parts of the rect are outside the array, these portions are ignored. In order to draw the
   * outline of a rect, use {@code rect}. The rect is identified by the coordinates of one of the
   * rect diagonals.
   *
   * @param x0 X coordinate of the diagonal start point
   * @param y0 Y coordinate of the diagonal start point
   * @param x1 X coordinate of the diagonal end point
   * @param y1 Y coordinate of the diagonal end point
   * @return This instance.
   */
  C fillRect(int x0, int y0, int x1, int y1);

  /**
   * Fills a rect with the current color.
   * <p>
   * If parts of the rect are outside the array, these portions are ignored. In order to draw the
   * outline of a rect, use {@code rect}. The rect is identified by the coordinates of one of the
   * rect diagonals.
   *
   * @param rect The rectangle to draw
   * @return This instance.
   */
  default C fillRect(Rectangle rect) {
    return fillRect(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom());
  }

  /**
   * Draws a circle with the current color.
   * <p>
   * If parts of the circle are outside the array, these portions are ignored. In order to fill a
   * circle, use {@code fillCircle}.
   *
   * @param x0 X coordinate of circle center
   * @param y0 Y coordinate of  circle center
   * @param r  The radius of the instance
   * @return This instance.
   */
  C circle(int x0, int y0, int r);

  /**
   * Draws a circle with the current color.
   * <p>
   * If parts of the circle are outside the array, these portions are ignored. In order to fill a
   * circle, use {@code fillCircle}.
   *
   * @param center The center of the circle
   * @param r      The radius of the instance
   * @return This instance.
   */
  default C circle(Point center, int r) {
    return circle(center.x(), center.y(), r);
  }

  /**
   * Fills a circle with the current color.
   * <p>
   * If parts of the circle are outside the array, these portions are ignored. In order to draw the
   * outline of a circle, use {@code circle}.
   *
   * @param x0 X coordinate of circle center
   * @param y0 Y coordinate of  circle center
   * @param r  The radius of the instance
   * @return This instance.
   */
  C fillCircle(int x0, int y0, int r);

  /**
   * Fills a circle with the current color.
   * <p>
   * If parts of the circle are outside the array, these portions are ignored. In order to draw the
   * outline of a circle, use {@code circle}.
   *
   * @param center The center of the circle
   * @param r      The radius of the instance
   * @return This instance.
   */
  default C fillCircle(Point center, int r) {
    return fillCircle(center.x(), center.y(), r);
  }

  /**
   * Draws {@code image} at the given position.
   * <p>
   * If parts of the image are outside the array, these portions are ignored.
   *
   * @param x0    X coordinate where to place the left side of the image
   * @param y0    Y coordinate where to place the top side of the image
   * @param image The image
   * @return This instance.
   */
  C image(int x0, int y0, I image);

  /**
   * Performs a flood fill using the current color.
   *
   * @param x0 X coordinate of the point to start with
   * @param y0 Y coordinate of the point to start with
   * @return This instance.
   */
  C fill(int x0, int y0);
}
