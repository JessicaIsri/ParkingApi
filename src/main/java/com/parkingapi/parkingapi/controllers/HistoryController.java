package com.parkingapi.parkingapi.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class HistoryController {
    public boolean validatedMask(String placa){
        // Valida a placa com base em regex
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
                return Pattern.matches("^[0-9]*$", split_placa[1]) && Pattern.matches("^[A-Z]*$", split_placa[0]);
            }
        }
    }


    public String actualDate(){
        // utiliza a data atual para poder ser usado como horario de entrada ou saida
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Test
    public long getDateDiff(String entrada, String saida) throws ParseException {
        // Faz o calculo de diferença de tempo baseado em minutos
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date firstDate = sdf.parse(entrada);
            Date secondDate = sdf.parse(saida);
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return diff;
        }
        catch (Exception y){
            return -1;
        }
    }

    public String makeJsonHistoricoReturn(String permanencia, long id, Boolean saida, Boolean pagamento){

        return "{\"id\": "+ id +", \"tempoPermanencia\": "+ permanencia + " , \"saiu\":" + pagamento + ", \"pago\": "+ saida +"}";
    }

}
