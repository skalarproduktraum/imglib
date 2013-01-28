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

package net.imglib2.axis;

import net.imglib2.Axis;
import net.imglib2.meta.AxisType;


/**
 * Helper methods for working with {@link Axis} classes.
 * 
 * @author Barry DeZonia
 */
public class AxisUtils {

	/**
	 * Returns a linear scaling axis (offset 0 and step scale of 1). The axis is
	 * labeled as the given {@link AxisType}.
	 * 
	 * @param axisType
	 */
	public static Axis getDefaultAxis(AxisType axisType) {
		Axis axis = new LinearAxis(0, 1);
		axis.setLabel(axisType.getLabel());
		return axis;
	}

	/**
	 * Returns an array of linear scaling axes (offset 0 and step scale of 1).
	 * Each axis is labeled as given.
	 * 
	 * @param axisTypes
	 */
	public static Axis[] getDefaultAxes(AxisType[] axisTypes) {
		Axis[] axes = new Axis[axisTypes.length];
		for (int i = 0; i < axisTypes.length; i++) {
			axes[i] = getDefaultAxis(axisTypes[i]);
		}
		return axes;
	}

	/**
	 * Makes an array of new axes whose data is copied from an existing set of
	 * axes.
	 * 
	 * @param axes The {@link Axis}es to copy
	 */
	public static Axis[] copyAxes(Axis[] axes) {
		Axis[] copy = new Axis[axes.length];
		for (int i = 0; i < axes.length; i++) {
			copy[i] = axes[i].copy();
		}
		return copy;
	}

	/**
	 * Gets an array of {@link AxisType}s from an array of {@Axis}es.
	 * 
	 * @param axes
	 */
	public static AxisType[] getTypes(Axis[] axes) {
		AxisType[] types = new AxisType[axes.length];
		for (int i = 0; i < axes.length; i++) {
			types[i] = axes[i].getType();
		}
		return types;
	}

	/**
	 * Returns the user coordinate offset of the origin point of an axis.
	 * 
	 * @param axis
	 */
	public static double getOffset(Axis axis) {
		return axis.getCalibratedMeasure(0);
	}

	/**
	 * Returns an estimate of the linear scale between two points on an
	 * {@link Axis}. For {@link LinearAxis}es there is no error. For nonlinear
	 * axes there is some error.
	 * 
	 * @param axis
	 * @param rangeStart
	 * @param rangeEnd
	 */
	public static double getScale(Axis axis, double rangeStart, double rangeEnd) {
		double axisRange = rangeEnd - rangeStart;
		double valueRange =
			axis.getCalibratedMeasure(rangeEnd) -
				axis.getCalibratedMeasure(rangeStart);
		return valueRange / axisRange;
	}
}
