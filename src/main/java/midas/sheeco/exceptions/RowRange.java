package midas.sheeco.exceptions;

import java.io.Serializable;

public class RowRange implements Serializable {
	private static final long serialVersionUID = 1;
	/**
	 * no specific row
	 */
	static RowRange NO_ROW = new RowRange(-1);
	private final int startIndex;
	private final int endIndex;

	RowRange(final int rowIndex) {
		this(rowIndex, rowIndex);
	}

	RowRange(final int startIndex, final int endIndex) {
		if (startIndex > endIndex) {
			throw new IllegalArgumentException("invalid row range: "
					+ startIndex + " ~ " + endIndex);
		}
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	@Override
	public String toString() {
		return startIndex == endIndex ? String.valueOf(startIndex) : startIndex
				+ " ~ " + endIndex;
	}
}
