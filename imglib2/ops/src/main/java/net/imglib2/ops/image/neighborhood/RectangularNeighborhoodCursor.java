package net.imglib2.ops.image.neighborhood;

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

import net.imglib2.AbstractCursor;
import net.imglib2.RandomAccess;
import net.imglib2.type.Type;

/**
 * Iterates all pixels in a 3 by 3 by .... by 3 neighborhood of a certain
 * location but skipping the central pixel
 * 
 * @param <T>
 * 
 * @author Stephan Preibisch
 * @author Stephan Saalfeld
 * @author Benjamin Schmid
 * @author Tobias Pietzsch <tobias.pietzsch@gmail.com>
 * @author Christian Dietz
 */
public class RectangularNeighborhoodCursor< T extends Type< T >> extends AbstractCursor< T > implements NeighborhoodCursor< T >
{
	final RandomAccess< T > source;

	private long[] center;

	private final long[] min;

	private final long[] max;

	private final long[] span;

	private final long[] bck;

	private final long maxCount;

	private long count;

	public RectangularNeighborhoodCursor( final RandomAccess< T > source, final long[] center, final long[] span )
	{
		super( source.numDimensions() );
		this.source = source;
		this.center = center;
		this.span = span;

		max = new long[ n ];
		min = new long[ n ];
		bck = new long[ n ];

		int tmp = 1;

		for ( int d = 0; d < span.length; d++ )
		{
			tmp *= ( span[ d ] * 2 ) + 1;
			bck[ d ] = ( -2 * span[ d ] ) - 1;
		}

		maxCount = tmp;

		reset();
	}

	protected RectangularNeighborhoodCursor( final RectangularNeighborhoodCursor< T > c )
	{
		super( c.numDimensions() );
		this.source = c.source.copyRandomAccess();
		this.center = c.center;
		max = c.max.clone();
		min = c.min.clone();
		span = c.span;
		maxCount = c.maxCount;
		bck = c.bck;
	}

	@Override
	public T get()
	{
		return source.get();
	}

	@Override
	public void fwd()
	{

		for ( int d = 0; d < n; ++d )
		{
			source.fwd( d );
			if ( source.getLongPosition( d ) > max[ d ] )
				source.move( bck[ d ], d );
			else
				break;
		}

		++count;
	}

	@Override
	public void reset()
	{
		for ( int d = 0; d < n; ++d )
		{
			min[ d ] = center[ d ] - span[ d ];
			max[ d ] = center[ d ] + span[ d ];
		}
		source.setPosition( min );
		source.bck( 0 );
		count = 0;
	}

	@Override
	public boolean hasNext()
	{
		return count < maxCount;
	}

	@Override
	public float getFloatPosition( final int d )
	{
		return source.getFloatPosition( d );
	}

	@Override
	public double getDoublePosition( final int d )
	{
		return source.getDoublePosition( d );
	}

	@Override
	public int getIntPosition( final int d )
	{
		return source.getIntPosition( d );
	}

	@Override
	public long getLongPosition( final int d )
	{
		return source.getLongPosition( d );
	}

	@Override
	public void localize( final long[] position )
	{
		source.localize( position );
	}

	@Override
	public void localize( final float[] position )
	{
		source.localize( position );
	}

	@Override
	public void localize( final double[] position )
	{
		source.localize( position );
	}

	@Override
	public void localize( final int[] position )
	{
		source.localize( position );
	}

	@Override
	public RectangularNeighborhoodCursor< T > copy()
	{
		return new RectangularNeighborhoodCursor< T >( this );
	}

	@Override
	public RectangularNeighborhoodCursor< T > copyCursor()
	{
		return copy();
	}

}
