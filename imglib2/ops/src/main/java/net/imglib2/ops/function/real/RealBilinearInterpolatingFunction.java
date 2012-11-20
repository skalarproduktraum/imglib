/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2012 Stephan Preibisch, Stephan Saalfeld, Tobias
 * Pietzsch, Albert Cardona, Barry DeZonia, Curtis Rueden, Lee Kamentsky, Larry
 * Lindsey, Johannes Schindelin, Christian Dietz, Grant Harris, Jean-Yves
 * Tinevez, Steffen Jaensch, Mark Longair, Nick Perry, and Jan Funke.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */


package net.imglib2.ops.function.real;

import net.imglib2.ops.function.Function;
import net.imglib2.type.numeric.RealType;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 */
public class RealBilinearInterpolatingFunction<T extends RealType<T>> 
	implements Function<double[],T>
{
	// -- instance variables --
	
	private Function<long[],T> discreteFunc;
	private long[] index;
	private T ul, ur, ll, lr;
	
	// -- constructor --
	
	public RealBilinearInterpolatingFunction(Function<long[],T> discreteFunc) {
		this.discreteFunc = discreteFunc;
		this.index = new long[2];
		this.ul = createOutput();
		this.ur = createOutput();
		this.ll = createOutput();
		this.lr = createOutput();
	}

	// -- Function methods --
	
	@Override
	public void compute(double[] point, T output) {
		long x = (long) Math.floor(point[0]);
		long y = (long) Math.floor(point[1]);
		double ix = point[0] - x;
		double iy = point[1] - y;
		getValue((x+0),(y+0),ul);
		getValue((x+1),(y+0),ur);
		getValue((x+0),(y+1),ll);
		getValue((x+1),(y+1),lr);
		double value = interpolate(ix, iy, ul.getRealDouble(), 
				ur.getRealDouble(), ll.getRealDouble(), lr.getRealDouble());
		output.setReal(value);
	}
	
	@Override
	public RealBilinearInterpolatingFunction<T> copy() {
		return new RealBilinearInterpolatingFunction<T>(discreteFunc.copy());
	}
	
	@Override
	public T createOutput() {
		return discreteFunc.createOutput();
	}

	// -- private helpers --
	
	private double interpolate(double ix, double iy, double ul, double ur, double ll, double lr) {
		double value = 0;
		value += (1-ix)*(1-iy)*ul;
		value += (1-ix)*(iy)*ll;
		value += (ix)*(1-iy)*ur;
		value += (ix)*(iy)*lr;
		return value;
	}

	private void getValue(long x, long y, T output) {
		index[0] = x;
		index[1] = y;
		discreteFunc.compute(index, output);
	}
	
}