public class BootStore {
    private Map<String, Boot> bootMap = new HashMap<>();
	
	//this class is a singleton and should not be instantiated directly!
	private static BootStore instance = new DataStore();
	public static BootStore getInstance(){
		return instance;
	}

	//private constructor so people know to use the getInstance() function instead
	private DataStore(){
		//dummy data

		bootMap.put("red", new Boot("red", 833, 255, "town1", 0));
		//personMap.put("Kevin", new Person("Kevin", "Kevin is the author of HappyCoding.io.", 1986));
		//personMap.put("Stanley", new Person("Stanley", "Stanley is Kevin's cat.", 2007));
	}

	public Boot getBoot(String color) {
		return bootMap.get(color);
	}

	public void putBoot(Boot boot) {
		bootMap.put(boot.getBootColor(), boot);
	}
}

