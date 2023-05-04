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
/**
 * Model classes for 2D arrays.
 * <p>
 * This package contains classes allowing to interpret a one dimensional array as a two dimensional.
 * For each primitive type there is an according implementation, e.g. for {@code int} we have a
 * {@link de.hipphampel.array2dops.model.Int2DArray}, for {@code boolean} a
 * {@link de.hipphampel.array2dops.model.Boolean2DArray} and so on. For reference types, there is a
 * generic {@link de.hipphampel.array2dops.model.Object2DArray}.
 * <p>
 * The (more or less trivial) base functionality of all these classes is that they allow to get and
 * set an individual value within the array usng the {@code get} and {@code set} methods, e.g.:
 * <pre><tt>
 *   var anArray = Char2DArray.newInstance(10, 5); // Creates an array with 10 columns and 5 rows
 *   anArray.set(1, 2, 'f'); // sets a cell
 *   vat value = anArray.get(9, 4); // gets an value
 * </tt></pre>
 * <p>
 * The array classes provide additional functionality in terms of building sub arrays or
 * transformations, whereas both operation can be done in a way that a sub-array or transformation
 * shares the memory with the original array or has its own memory space.
 * <p>
 * For example, the following code creates a main array and a sub array for a specific range that
 * shares the memory with the main array:
 * <pre><tt>
 *   var anArray = Char2DArray.newInstance(10, 5); // Creates an array with 10 columns and 5 rows
 *   var aRegion = anArray.shallowRegion(0, 1, 2, 3);
 *   var shouldBeTrue =  anArray.get(2, 2) =  aRegion(2, 3);
 * </tt></pre>
 */
package de.hipphampel.array2dops.model;