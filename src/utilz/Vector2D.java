package utilz;

public class Vector2D {
	
	private double x;
	private double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(double x1, double y1, double x2, double y2) {
		this.x = x2-x1;
		this.y = y2-y1;
	}
	
	public static Vector2D findOrthogonalPoint(Vector2D M, Vector2D A, Vector2D B, double distance, boolean invertSide) {
		Vector2D AB = B.subtract(A).normalize();

		Vector2D orthogonalDirection = new Vector2D(-AB.getY(), AB.getX());
		
		if(!invertSide) {
			orthogonalDirection.inverse();
		}
		

		// Scale the orthogonal direction vector by the specified distance
		Vector2D offset = orthogonalDirection.multiply(distance);

		// Calculate the final point O by adding the offset to the midpoint M
		Vector2D O = M.add(offset);

		// Return the orthogonal point O
		return O;
	}
	
	public static Vector2D findMidpoint(Vector2D A, Vector2D B) {
        double midX = (A.getX() + B.getX()) / 2.0;
        double midY = (A.getY() + B.getY()) / 2.0;
        Vector2D midpoint = new Vector2D(midX, midY);
        return midpoint;
    }
	
	public void inverse() {
		this.x *= -1;
		this.y *= -1;
	}
	
	public Vector2D multiply(double p) {
        return new Vector2D(this.x * p, this.y * p);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }
	
	public double dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }
	
	public static double dotProduct(Vector2D a, Vector2D b) {
		return a.x * b.x + a.y * b.y;
	}
	
	public Vector2D normalize() {
        double magnitude = getMagnitude();
        if (magnitude != 0) {
            return new Vector2D(x / magnitude, y / magnitude);
        } else {
            return new Vector2D(0,0);
        }
    }
	
	public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
}
