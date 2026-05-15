package stuctures;

public class Position {
	private int x;
	private int y;

	public Position() {}

	public Position(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public void setPosition(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	@Override public boolean equals(final Object obj) {
		if (!(obj instanceof Position other)) return false;
		return x == other.x && y == other.y;
	}
}
