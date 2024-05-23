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
package smile.llm.llama;

import smile.deep.activation.SiLU;
import smile.deep.layer.FullyConnectedLayer;
import smile.deep.tensor.Tensor;

/**
 * Feedforward layer in Transformer. It has two linear transformations and
 * an intermediate SiLU activation function.
 *
 * @author Haifeng Li
 */
public class FeedForward {
    final FullyConnectedLayer w1, w2, w3;
    final SiLU silu;

    /**
     * Constructor.
     * @param dim the dimension of input tensor.
     * @param hiddenDim the dimension of hidden layer. First, hiddenDim is set
     *                 to two-thirds of the provided hiddenDim value. If ffnDimMultiplier
     *                 is provided, hiddenDim is further multiplied by this value.
     *                  The hiddenDim is then adjusted to ensure it is a multiple of multipleOf.
     * @param multipleOf make SwiGLU hidden layer size multiple of large power of 2.
     * @param ffnDimMultiplier the multiplier for the hidden dimension of the feedforward layers.
     */
    public FeedForward(int dim, int hiddenDim, int multipleOf, Double ffnDimMultiplier) {
        hiddenDim = (int) (2 * hiddenDim / 3.0);
        // custom dim factor multiplier
        if (ffnDimMultiplier != null){
            hiddenDim = (int) (ffnDimMultiplier * hiddenDim);
        }
        hiddenDim = multipleOf * ((hiddenDim + multipleOf - 1) / multipleOf);
        this.w1 = new FullyConnectedLayer(dim, hiddenDim, false);
        this.w2 = new FullyConnectedLayer(hiddenDim, dim, false);
        this.w3 = new FullyConnectedLayer(dim, hiddenDim, false);
        this.silu = new SiLU(true);
    }

    /**
     * Feed forward.
     * @param x the input tensor.
     * @return the output tensor.
     */
    public Tensor forward(Tensor x) {
        return w2.forward(silu.forward(w1.forward(x)).mul(w3.forward(x)));
    }
}
