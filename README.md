# Library to deal with 2d arrays

## Motivation

Often there are use cases where you have a one dimensional array, but actually want to interpret it
as 2D. Typical examples are images or matrices.

So the library provides several ways to deal with 2D arrays in general:
  
  - For each primitive type there is a specialized class, e.g. for `byte` we have a `Byte2DArray`,
    but for `int` an `Int2DArray`. Also, for reference types we have a generic `Object2DArray` in
    place.
  - Basic operations:
    - Indeed, getting and setting an individual value with in the array
    - Transformations: you may rotate, transpose or flip (by x/y axis) an 2D array.
    - Filling the array or regions of it with values from other arrays or just fixed values
    - Extract sub regions.
    - All transformation/extracting operations can be done in a deep-copy way so that the resulting
      array has its own memory or in a shallow way, where - for example the rotated - result array
      shares the memory with the original (see examples below).
  - Matrix operations:
    - For all primitive types and the `BigDecimal` we have basic matrix operations, so you may 
      do matrix multiplication, addition, substraction, or scalar multiplication with these arrays
    - Again this utilizes the ability to deal with shallow or deep copies of array, so that it is
      possible to implement performant matrix - related applications on it.
  - Draw operations:
    - I know, a little bit esoteric: but you may draw on these arrays (and honestly, this was my
      original motivation, because i needed that for a  - yes - esoteric application).
    - Drawing primitive like points, lines, rects, and circles are supported.
    - Again, for each primtive type we have a special adapted class, e.g. `ByteArray2DDrawContext`
      for `Byte2DArrays` which use `byte` as element type.


## Usage

#### Maven
```xml
    <dependency>
      <groupId>de.hipphampel</groupId>
      <artifactId>array-2d-ops</artifactId>
      <version>VERSION/version>
    </dependency>
```
#### Gradle
```groovy
    implementation 'de.hipphampel:array-2d-ops:VERSION'
```


## Basic examples

### The 2D array model classes

As an appetizer, let's try it with `jshell`; so after building this project, type in your shell
`jshell --class-path target/classes` in order to start a `jshell` that knows the classes from this 
project (indeed, you have to a `mvn install` before it to compile to source):

Creating a first array:

```jshell
jshell> import de.hipphampel.array2dops.model.*;

jshell> var values = IntStream.range(0,100).toArray(); // Define a 1D array
values ==> int[100] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,  ... , 94, 95, 96, 97, 98, 99 }

jshell> var a = Int2DArray.newInstance(values, 10, 10); // Wrap the array in a 2D wrapper
a ==> de.hipphampel.array2dops.model.Int2DArrayImpl@401553d5

jshell> a.print();
0, 1, 2, 3, 4, 5, 6, 7, 8, 9
10, 11, 12, 13, 14, 15, 16, 17, 18, 19
20, 21, 22, 23, 24, 25, 26, 27, 28, 29
30, 31, 32, 33, 34, 35, 36, 37, 38, 39
40, 41, 42, 43, 44, 45, 46, 47, 48, 49
50, 51, 52, 53, 54, 55, 56, 57, 58, 59
60, 61, 62, 63, 64, 65, 66, 67, 68, 69
70, 71, 72, 73, 74, 75, 76, 77, 78, 79
80, 81, 82, 83, 84, 85, 86, 87, 88, 89
90, 91, 92, 93, 94, 95, 96, 97, 98, 99
```

Note that we deal with `int` here, the library provides for each primitive type a special class,
also
an implementation for object array in general.

Indeed we could get and set values in the array as very basic operations, here just the `get`:

```jshell
jshell> a.get(1, 2);
$5 ==> 21
```

This is more or less trivial and honestly not worth for a library, so let's go on:

```
jshell> var b = a.shallowRegion(1, 2, 3, 4);
b ==> de.hipphampel.array2dops.model.SubInt2DArrayImpl@9310eb1e

jshell> b.print();
21, 22, 23
31, 32, 33
41, 42, 43
51, 52, 53
```

A little bit more interesting than before, maybe: The created `b` as a sub-array of
`a`, so that `b` is more or less a view on `a` for the region in range (1/2) - (3/5).
Since we used `shallowRegion` instead of `copyRegion`, `b` shares the memory with `a`, so
setting a value in `b` affects `a` as well; in case of `copyRegion` `b` would be a deep copy
of that region.

So for now, setting some value in `b` is visible in `a` as well:

```
jshell> b.set(2, 3, -1);
$11 ==> de.hipphampel.array2dops.model.SubInt2DArrayImpl@9310eae8

jshell> b.print();
21, 22, 23
31, 32, 33
41, 42, 43
51, 52, -1
jshell> a.print();
0, 1, 2, 3, 4, 5, 6, 7, 8, 9
10, 11, 12, 13, 14, 15, 16, 17, 18, 19
20, 21, 22, 23, 24, 25, 26, 27, 28, 29
30, 31, 32, 33, 34, 35, 36, 37, 38, 39
40, 41, 42, 43, 44, 45, 46, 47, 48, 49
50, 51, 52, -1, 54, 55, 56, 57, 58, 59
60, 61, 62, 63, 64, 65, 66, 67, 68, 69
70, 71, 72, 73, 74, 75, 76, 77, 78, 79
80, 81, 82, 83, 84, 85, 86, 87, 88, 89
90, 91, 92, 93, 94, 95, 96, 97, 98, 99
```

In a similar way, transformation work, lets have a look:

```
jshell> var c = b.shallowTransform(Transformation.ROTATE_CLOCKWISE_90);
c ==> de.hipphampel.array2dops.model.TransformInt2DArrayImpl@14e81862

jshell> c.print();
51, 41, 31, 21
52, 42, 32, 22
-1, 43, 33, 23
jshell> c.set(2, 1, -2);
$16 ==> de.hipphampel.array2dops.model.TransformInt2DArrayImpl@1308f940

jshell> c.print();
51, 41, 31, 21
52, 42, -2, 22
-1, 43, 33, 23
jshell> b.print();
21, 22, 23
31, -2, 33
41, 42, 43
51, 52, -1
jshell> a.print();
0, 1, 2, 3, 4, 5, 6, 7, 8, 9
10, 11, 12, 13, 14, 15, 16, 17, 18, 19
20, 21, 22, 23, 24, 25, 26, 27, 28, 29
30, 31, -2, 33, 34, 35, 36, 37, 38, 39
40, 41, 42, 43, 44, 45, 46, 47, 48, 49
50, 51, 52, -1, 54, 55, 56, 57, 58, 59
60, 61, 62, 63, 64, 65, 66, 67, 68, 69
70, 71, 72, 73, 74, 75, 76, 77, 78, 79
80, 81, 82, 83, 84, 85, 86, 87, 88, 89
90, 91, 92, 93, 94, 95, 96, 97, 98, 99
```

As you can see, such regions and transformations can nest.

There are nine different interfaces for the 2D array classes, one for each primitive type and a
further for arbitrary objects. The interfaces and all the implementations of it are autogenerated
based on some Mustache template. In general, the implementations try to reduce unnecessary overhead:
for example, if you do a `someArray.shallowTransform(Transformation.TRANSPOSE)
.shallowTransform(Transformation.TRANSPOSE)}`, the result array is `someArray` and not 
two additional array transformations that invert each other.


### The Matrix Operations

The library also offers some basic matrix related operations:
```
jshell> import de.hipphampel.array2dops.matrix.*;

jshell> var values = IntStream.range(1, 7).toArray();
values ==> int[6] { 1, 2, 3, 4, 5, 6 }

jshell> var m1 = IntMatrix.newMatrix(values, 3, 2);
m1 ==> de.hipphampel.array2dops.model.Int2DArrayImpl@6e4c4249

jshell> m1.print();
1, 2, 3
4, 5, 6

jshell> var m2 = IntMatrix.newMatrix(values, 2, 3);
m2 ==> de.hipphampel.array2dops.model.Int2DArrayImpl@3b448b23

jshell> m2.print();
1, 2
3, 4
5, 6

jshell> IntMatrix.mul(m1, m2).print();
22, 28
49, 64

jshell> IntMatrix.mul(m2, m1).print();
9, 12, 15
19, 26, 33
29, 40, 51

jshell> IntMatrix.add(m1, IntMatrix.transpose(m2)).print();
2, 5, 8
6, 9, 12
```
Similar to the plain arrays, there exists for each primitive type an according matrix implementation,
so we have - for example - `DoubleMatrix` for `doubles`. Since there is no meaningful implementation
for `Object` based matrices, we only have one for `BigDecimal ` - the `BigDecimalMatrix` - in place.