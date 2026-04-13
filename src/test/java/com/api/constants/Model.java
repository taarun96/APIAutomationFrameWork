package com.api.constants;

public enum Model {
	NEXUS_2_BLUE(1),GALAXY(2);
	
	 private final int code;

    // Private constructor to assign the ID
    private Model(int code) {
        this.code = code;
    }

    // Getter method to retrieve the ID
    public int getCode() {
        return code;
    }
}
