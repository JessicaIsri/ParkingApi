package com.parkingapi.parkingapi.controllers;

import java.util.regex.Pattern;

public class HistoryController {
    public boolean validatedMask(String placa){
        int pos  = placa.indexOf('-');
        String[] split_placa = placa.split("-");
        if (pos == -1){
            return false;
        }
        else{
            if (split_placa[0].length() != 3 || split_placa[1].length() != 4){
                return false;
            }
            else{
                if (Pattern.matches("^[0-9]*$", split_placa[0]) || Pattern.matches("^[A-Z]*$", split_placa[1]) ){
                    return false;
                }
            }
        }

        return true;
    }
}
