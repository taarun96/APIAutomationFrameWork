package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDemo {

    public static void main(String[] args) throws VaultException {
    	String data=System.getenv("VAULT_SERVER");
    	System.out.println(data);
    	String data1=System.getenv("VAULT_TOKEN");
    	System.out.println(data1);
       /* VaultConfig vaultConfig = new VaultConfig()
                .address("http://3.16.1.150:8200")
                .token("root")
                .build();
        
        
        Vault vault=new Vault(vaultConfig);
        
        LogicalResponse response = vault.logical().read("secret/phoenix/qa/database");

        Map<String, String> dataMap = response.getData();

        System.out.println(dataMap.get("DB_URL"));
        System.out.println(dataMap.get("DB_USERNAME"));
        System.out.println(dataMap.get("DB_PASSWORD"));
        */
    }
}