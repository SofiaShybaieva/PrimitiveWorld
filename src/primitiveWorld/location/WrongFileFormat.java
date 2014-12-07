package primitiveWorld.location;

@SuppressWarnings("serial")
public class WrongFileFormat extends Exception {
	public String toString() {
		return new String("Expected Primitive World v.1.0 file version");
	}
}
