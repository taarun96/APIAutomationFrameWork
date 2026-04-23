package com.api.utils;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;
import java.util.Map;

public class VaultDBConfig {

    private static Vault vault;

    static {
        try {
            VaultConfig vaultConfig = new VaultConfig()
            		   .address("http://3.16.1.150:8220"
            			   )
                    .token("root")
                    .build();
            vault = new Vault(vaultConfig);
        } catch (VaultException e) {
            e.printStackTrace();
        }
    }

    private VaultDBConfig() {
        // Private constructor to prevent instantiation
    }

    public static String getSecret(String key) {
    	LogicalResponse response=null;
        try {
             response = vault.logical().read("secret/phoenix/qa/database");
       
        } catch (VaultException e) {
            e.printStackTrace();
            return null;
        }
        
        Map<String, String> dataMap = response.getData();

        String secretValue = dataMap.get(key);
        return secretValue;
    }
}