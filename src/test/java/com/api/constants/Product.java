package com.api.constants;

public enum Product {
	NEXUS_2(1), 
    PIXEL(2);

    private final int code;

    // Private constructor to assign the ID
    private Product(int code) {
        this.code = code;
    }

    // Getter method to retrieve the ID
    public int getCode() {
        return code;
    }
}
