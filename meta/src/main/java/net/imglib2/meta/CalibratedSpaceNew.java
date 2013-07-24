package net.imglib2.meta;

import net.imglib2.img.Img;

/**
 * A Euclidean space whose dimensions have units and calibrations.
 * 
 * @author Curtis Rueden
 * @see CalibratedAxis
 */
public interface CalibratedSpaceNew<A extends CalibratedAxis> extends
	TypedSpace<A>
{
	/** Gets the associated {@link Img}'s calibration at the given dimension. */
	double calibration(int d);

	/** Copies the {@link Img}'s calibration into the given array. */
	void calibration(double[] cal);

	/** Copies the {@link Img}'s calibration into the given array. */
	void calibration(float[] cal);

	/** Sets the image calibration for the given dimension. */
	void setCalibration(double cal, int d);

	/** Sets the image calibration for all dimensions. */
	void setCalibration(double[] cal);

	/** Sets the image calibration for all dimensions. */
	void setCalibration(float[] cal);

	/** Gets the unit for the given dimension. */
	String unit(int d);

	/** Sets the unit for the given dimension. */
	void setUnit(String unit, int d);
}
