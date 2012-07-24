package net.imglib2.algorithm.region.localneighborhood;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.meta.Metadata;
import net.imglib2.outofbounds.OutOfBoundsFactory;
import net.imglib2.outofbounds.OutOfBoundsMirrorFactory;
import net.imglib2.outofbounds.OutOfBoundsMirrorFactory.Boundary;

public class DiscNeighborhood<T, IN extends RandomAccessibleInterval<T> & Metadata>
		extends RealPositionableAbstractNeighborhood<T, IN> {

	/** The radius of the sphere, in calibrated units. */
	protected double radius;

	/*
	 * CONSTRUCTORS
	 */

	public DiscNeighborhood(final IN source, final double radius,
			final OutOfBoundsFactory<T, IN> outOfBounds) {
		super(source, outOfBounds);
		this.radius = radius;
		setRadius(radius);
	}

	public DiscNeighborhood(final IN source, final double radius) {
		this(source, radius, new OutOfBoundsMirrorFactory<T, IN>(
				Boundary.DOUBLE));
	}

	/*
	 * METHODS
	 */

	/**
	 * Overridden not to do anything.
	 * 
	 * @see #setRadius(double)
	 */
	@Override
	public void setSpan(long[] span) {
	}

	/**
	 * Change the radius of this neighborhood.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
		// Compute span
		final long[] span = new long[2];
		for (int d = 0; d < span.length; d++) {
			span[d] = Math.round(radius / calibration[d]);
		}
		super.setSpan(span);
	}

	@Override
	public long size() {
		long pixel_count = 0;
		final int[] local_rxs = new int[(int) (span[1] + 1)];
		int local_rx;

		Utils.getXYEllipseBounds((int) span[0], (int) span[1], local_rxs);
		local_rx = local_rxs[0]; // middle line
		pixel_count += 2 * local_rx + 1;
		for (int i = 1; i <= span[1]; i++) {
			local_rx = local_rxs[i];
			pixel_count += 2 * (2 * local_rx + 1); // Twice because we mirror
		}

		return pixel_count;
	}

	@Override
	public DiscCursor<T, IN> cursor() {
		return new DiscCursor<T, IN>(this);
	}

	@Override
	public DiscCursor<T, IN> localizingCursor() {
		return cursor();
	}

	@Override
	public DiscCursor<T, IN> iterator() {
		return cursor();
	}

}
