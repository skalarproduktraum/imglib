package net.imglib2.ops.iterable;

import java.util.Iterator;

import net.imglib2.ops.UnaryOperation;
import net.imglib2.type.numeric.RealType;

public class Min< T extends RealType< T >, V extends RealType< V >> implements UnaryOperation< Iterator< T >, V >
{

	@Override
	public V compute( Iterator< T > input, V output )
	{
		T min = null;
		while ( input.hasNext() )
		{
			T in = input.next();

			if ( min == null || in.compareTo( min ) < 0 )
				min = in;
		}

		output.setReal( min.getRealDouble() );

		return output;
	}

	@Override
	public UnaryOperation< Iterator< T >, V > copy()
	{
		return new Min< T, V >();
	}

}
