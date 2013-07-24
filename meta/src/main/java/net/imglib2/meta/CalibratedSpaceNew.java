package net.imglib2.meta;


/**
 * A Euclidean space whose dimensions have units and calibrations.
 * 
 * @author Curtis Rueden
 * @see CalibratedAxis
 */
public interface CalibratedSpaceNew<A extends CalibratedAxis> extends
	TypedSpace<A>
{
	// NB: Marker interface.
	// TODO - BDZ: did we forget to add something here?
}
