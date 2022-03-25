package de.bossascrew.pathlib;

public class Vector {

	private final String name;
	private final float x,y,z;

	public Vector(String name, float x, float y, float z) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float distance(Vector other) {
		return (float) Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2) + Math.pow(other.z - z, 2));
	}

	public String getName() {
		return name;
	}
}
