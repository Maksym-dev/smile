/*
 * Copyright (c) 2010-2024 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 */
package smile.manifold;

import java.util.Arrays;

import smile.math.MathEx;
import smile.test.data.MNIST;
import smile.test.data.SwissRoll;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Karl Li
 */
public class UMAPTest {

    public UMAPTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testMnist() throws Exception {
        System.out.println("UMAP MNIST");

        MathEx.setSeed(19650218); // to get repeatable results.
        long start = System.currentTimeMillis();
        double[][] coordinates = UMAP.of(MNIST.x, 15);
        long end = System.currentTimeMillis();
        System.out.format("UMAP takes %.2f seconds\n", (end - start) / 1000.0);
        assertEquals(MNIST.x.length, coordinates.length);
    }

    @Test
    public void testSwissRoll() throws Exception {
        System.out.println("UMAP SwissRoll");

        MathEx.setSeed(19650218); // to get repeatable results.
        double[][] data = Arrays.copyOf(SwissRoll.data, 1000);
        long start = System.currentTimeMillis();
        double[][] coordinates = UMAP.of(data, 15);
        long end = System.currentTimeMillis();
        System.out.format("UMAP takes %.2f seconds\n", (end - start) / 1000.0);
        assertEquals(data.length, coordinates.length);
    }
}
