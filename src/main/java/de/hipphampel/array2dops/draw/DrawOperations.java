package de.hipphampel.array2dops.draw;

public interface DrawOperations<T, I, C> {

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
