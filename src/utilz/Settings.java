package utilz;

//Class for storing settings regarding the program
public enum Settings {
	//SETTINGS
	PRINT_PERFORMANCE_UPDATES(true),
	ENABLE_DOUBLE_BUFFERING(true);
	
	
	
	
	
	
	
	private final Boolean value;
	
	Settings(Boolean value) {
        this.value = value;
    }
	
	public Boolean getValue() {
        return value;
    }
}
