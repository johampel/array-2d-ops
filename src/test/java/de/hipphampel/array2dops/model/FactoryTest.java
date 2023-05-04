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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// Note: Since all classes are auto-generated, we pick just one of them for testing...
public class FactoryTest {

    private int[] sampleData;
    private final int sampleWidth = 11;
    private final int sampleHeight = 12;

    @BeforeEach
    public void beforeEach() {
        sampleData = new int[]{
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1,
                2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2,
                3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3,
                4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4,
                5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5,
                6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6,
                7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7,
                8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1,
        };
    }

    @Test
    public void newInstance_noargs() {
        Int2DArray instance = Int2DArray.newInstance();
        assertThat(instance).isNotNull();
        assertThat(instance.getWidth()).isZero();
        assertThat(instance.getHeight()).isZero();
        assertThat(instance.toArray()).hasSize(0);
        assertThat(instance.isArrayOwner()).isTrue();
        assertThat(instance.getData()).hasSize(0)
                .isNotSameAs(instance.toArray());
    }

    @Test
    public void newInstance_dimensions() {
        Int2DArray instance = Int2DArray.newInstance(17, 19);
        assertThat(instance).isNotNull();
        assertThat(instance.getWidth()).isEqualTo(17);
        assertThat(instance.getHeight()).isEqualTo(19);
        assertThat(instance.toArray()).hasSize(17 * 19)
                .isEqualTo(new int[17 * 19]);
        assertThat(instance.isArrayOwner()).isTrue();
        assertThat(instance.getData()).isEqualTo(instance.toArray())
                .isNotSameAs(instance.toArray());
    }

    @Test
    public void newInstance_data_dimensions_ok() {
        Int2DArray instance = Int2DArray.newInstance(sampleData, sampleWidth, sampleHeight);
        assertThat(instance).isNotNull();
        assertThat(instance.getWidth()).isEqualTo(sampleWidth);
        assertThat(instance.getHeight()).isEqualTo(sampleHeight);
        assertThat(instance.toArray()).isEqualTo(sampleData);
        assertThat(instance.isArrayOwner()).isTrue();
        assertThat(instance.getData()).isSameAs(sampleData)
                .isEqualTo(instance.toArray())
                .isNotSameAs(instance.toArray());
    }

    @Test
    public void newInstance_data_dimensions_fail() {
        assertThatThrownBy(() -> Int2DArray.newInstance(sampleData, sampleWidth - 1, sampleHeight))
                .isInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage("data size = 132 differs from logical size 10x12");
    }
}
