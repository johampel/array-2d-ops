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
package de.hipphampel.array2dops.draw;

import static org.assertj.core.api.Assertions.assertThat;

import de.hipphampel.array2dops.model.Byte2DArray;
import de.hipphampel.array2dops.model.Char2DArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Char2DArrayDrawContextTest {

  private Char2DArray canvas;
  private Char2DArrayDrawContext context;

  @BeforeEach
  public void beforeEach() {
    canvas = Char2DArray.newInstance(20, 20);
    canvas.fill('.');
    context = new Char2DArrayDrawContext(canvas);
    context.color('X');
  }

  @Test
  public void set() {
    context.set(0, 0)
        .set(100, 100) // Out of bounds
        .set(10, 5)
        .set(19, 19);

    assertCanvas("""
        X...................
        ....................
        ....................
        ....................
        ....................
        ..........X.........
        ....................
        ....................
        ....................
        ....................
        ....................
        ....................
        ....................
        ....................
        ....................
        ....................
        ....................
        ....................
        ....................
        ...................X""");
  }

  @Test
  public void line() {
    context
        .color('A')
        .line(10, 0, 10, 20)
        .color('B')
        .line(0, 10, 20, 10)
        .color('C')
        .line(5, 0, 15, 15)
        .color('D')
        .line(-50, -100, 15, 5);

    assertCanvas("""
        .....C....AD........
        ......C...A.D.......
        ......C...A..D......
        .......C..A..D......
        ........C.A...D.....
        ........C.A....D....
        .........CA.........
        ..........C.........
        ..........C.........
        ..........AC........
        BBBBBBBBBBBBCBBBBBBB
        ..........A.C.......
        ..........A..C......
        ..........A...C.....
        ..........A...C.....
        ..........A....C....
        ..........A.........
        ..........A.........
        ..........A.........
        ..........A.........""");
  }

  @Test
  public void rect() {
    context
        .color('A')
        .rect(1, 1, 18, 18)
        .color('B')
        .rect(3, 3, 20, 20)
        .color('C')
        .rect(-3, -3, 16, 16)
        .color('D')
        .rect(5, 5, 14, 14);

    assertCanvas("""
        ................C...
        .AAAAAAAAAAAAAAACAA.
        .A..............C.A.
        .A.BBBBBBBBBBBBBCBBB
        .A.B............C.A.
        .A.B.DDDDDDDDDD.C.A.
        .A.B.D........D.C.A.
        .A.B.D........D.C.A.
        .A.B.D........D.C.A.
        .A.B.D........D.C.A.
        .A.B.D........D.C.A.
        .A.B.D........D.C.A.
        .A.B.D........D.C.A.
        .A.B.D........D.C.A.
        .A.B.DDDDDDDDDD.C.A.
        .A.B............C.A.
        CCCCCCCCCCCCCCCCC.A.
        .A.B..............A.
        .AABAAAAAAAAAAAAAAA.
        ...B................""");
  }

  @Test
  public void fillRect() {
    context
        .color('A')
        .fillRect(1, 1, 18, 18)
        .color('B')
        .fillRect(3, 3, 20, 20)
        .color('C')
        .fillRect(-3, -3, 16, 16)
        .color('D')
        .fillRect(5, 5, 14, 14);

    assertCanvas("""
        CCCCCCCCCCCCCCCCC...
        CCCCCCCCCCCCCCCCCAA.
        CCCCCCCCCCCCCCCCCAA.
        CCCCCCCCCCCCCCCCCBBB
        CCCCCCCCCCCCCCCCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCDDDDDDDDDDCCBBB
        CCCCCCCCCCCCCCCCCBBB
        CCCCCCCCCCCCCCCCCBBB
        .AABBBBBBBBBBBBBBBBB
        .AABBBBBBBBBBBBBBBBB
        ...BBBBBBBBBBBBBBBBB""");
  }

  @Test
  public void circle() {
    context
        .color('A')
        .circle(9, 9, 12)
        .color('B')
        .circle(9, 9, 10)
        .color('C')
        .circle(9, 9, 6)
        .color('D')
        .circle(4, 9, 4);

    assertCanvas("""
        .A..BB.......BB..A..
        A..B...........B..A.
        ..B.............B..A
        .B.....CCCCC.....B.A
        B....CC.....CC....B.
        B.DDDDD.......C...B.
        .D..C..D......C....B
        D..C....D......C...B
        D..C....D......C...B
        D..C....D......C...B
        D..C....D......C...B
        D..C....D......C...B
        .D..C..D......C....B
        B.DDDDD.......C...B.
        B....CC.....CC....B.
        .B.....CCCCC.....B.A
        ..B.............B..A
        A..B...........B..A.
        .A..BB.......BB..A..
        ..AA..BBBBBBB..AA...""");
  }

  @Test
  public void fillCircle() {
    context
        .color('A')
        .fillCircle(9, 9, 12)
        .color('B')
        .fillCircle(9, 9, 10)
        .color('C')
        .fillCircle(9, 9, 6)
        .color('D')
        .fillCircle(4, 9, 4);

    assertCanvas("""
        .AAABBBBBBBBBBBAAA..
        AAABBBBBBBBBBBBBAAA.
        AABBBBBBBBBBBBBBBAAA
        ABBBBBBCCCCCBBBBBBAA
        BBBBBCCCCCCCCCBBBBBA
        BBDDDDDCCCCCCCCBBBBA
        BDDDDDDDCCCCCCCBBBBB
        DDDDDDDDDCCCCCCCBBBB
        DDDDDDDDDCCCCCCCBBBB
        DDDDDDDDDCCCCCCCBBBB
        DDDDDDDDDCCCCCCCBBBB
        DDDDDDDDDCCCCCCCBBBB
        BDDDDDDDCCCCCCCBBBBB
        BBDDDDDCCCCCCCCBBBBA
        BBBBBCCCCCCCCCBBBBBA
        ABBBBBBCCCCCBBBBBBAA
        AABBBBBBBBBBBBBBBAAA
        AAABBBBBBBBBBBBBAAA.
        .AAABBBBBBBBBBBAAA..
        ..AAAABBBBBBBAAAA...""");
  }

  @Test
  public void image() {
    Char2DArray image = Char2DArray.newInstance("ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray(), 5, 5);

    context
        .image(-2, -2, image)
        .image(-2, 17, image)
        .image(8, 8, image)
        .image(17, 17, image)
        .image(16, -2, image);

    assertCanvas("""
        MNO.............KLMN
        RST.............PQRS
        WXY.............UVWX
        ....................
        ....................
        ....................
        ....................
        ....................
        ........ABCDE.......
        ........FGHIJ.......
        ........KLMNO.......
        ........PQRST.......
        ........UVWXY.......
        ....................
        ....................
        ....................
        ....................
        CDE..............ABC
        HIJ..............FGH
        MNO..............KLM""");
  }

  @Test
  public void image_origin() {
    Char2DArray image = Char2DArray.newInstance("ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray(), 5, 5);

    context
        .origin(-2, -2)
        .image(0, 0, image)
        .origin(-2, 17)
        .image(0, 0, image)
        .origin(8, 8)
        .image(0, 0, image)
        .origin(17, 17)
        .image(0, 0, image)
        .origin(16, -2)
        .image(0, 0, image);

    assertCanvas("""
        MNO.............KLMN
        RST.............PQRS
        WXY.............UVWX
        ....................
        ....................
        ....................
        ....................
        ....................
        ........ABCDE.......
        ........FGHIJ.......
        ........KLMNO.......
        ........PQRST.......
        ........UVWXY.......
        ....................
        ....................
        ....................
        ....................
        CDE..............ABC
        HIJ..............FGH
        MNO..............KLM""");
  }

  @Test
  public void image_bigger() {
    Char2DArray image = Char2DArray.newInstance("ABCDEFGHIJKLMNOPQRSTUVWXY".toCharArray(), 5, 5);

    canvas = Char2DArray.newInstance(3, 3);
    canvas.fill('.');
    context = new Char2DArrayDrawContext(canvas);
    context
        .image(0, 0, image);

    assertCanvas("""
        ABC
        FGH
        KLM""");
  }

  @Test
  public void fill() {

    // Arrange
    context
        .color('A')
        .line(3, 0, 3, 3)
        .line(6, 0, 6, 3)
        .line(9, 0, 9, 3)
        .line(12, 0, 12, 3)
        .line(15, 0, 15, 3)
        .line(0, 4, 3, 4)
        .line(7, 4, 8, 4)
        .line(12, 4, 15, 4)
        .line(0, 8, 10, 8)
        .line(12, 8, 20, 8)
        .rect(12, 9, 20, 19)
        .rect(-1, 14, 6, 17);
    assertCanvas("""
        ...A..A..A..A..A....
        ...A..A..A..A..A....
        ...A..A..A..A..A....
        ...A..A..A..A..A....
        AAAA...AA...AAAA....
        ....................
        ....................
        ....................
        AAAAAAAAAAA.AAAAAAAA
        ............AAAAAAAA
        ............A.......
        ............A.......
        ............A.......
        ............A.......
        AAAAAAA.....A.......
        ......A.....A.......
        ......A.....A.......
        AAAAAAA.....A.......
        ............A.......
        ............AAAAAAAA""");

    // ACT
    context.color('X').fill(10, 10);

    assertCanvas("""
        ...AXXA..AXXA..AXXXX
        ...AXXA..AXXA..AXXXX
        ...AXXA..AXXA..AXXXX
        ...AXXA..AXXA..AXXXX
        AAAAXXXAAXXXAAAAXXXX
        XXXXXXXXXXXXXXXXXXXX
        XXXXXXXXXXXXXXXXXXXX
        XXXXXXXXXXXXXXXXXXXX
        AAAAAAAAAAAXAAAAAAAA
        XXXXXXXXXXXXAAAAAAAA
        XXXXXXXXXXXXA.......
        XXXXXXXXXXXXA.......
        XXXXXXXXXXXXA.......
        XXXXXXXXXXXXA.......
        AAAAAAAXXXXXA.......
        ......AXXXXXA.......
        ......AXXXXXA.......
        AAAAAAAXXXXXA.......
        XXXXXXXXXXXXA.......
        XXXXXXXXXXXXAAAAAAAA""");

  }

  private void assertCanvas(String expected) {
    assertThat(canvas.printToString("", "\n")).isEqualTo(expected);
  }
}
