package com.parkingapi.parkingapi.resources;


import com.parkingapi.parkingapi.controllers.HistoryController;
import com.parkingapi.parkingapi.models.Historico;
import com.parkingapi.parkingapi.repository.HistoricoRepository;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/estacionamentos")
public class HistoricoResources {

    private final HistoryController controller = new HistoryController();

    @Autowired
    HistoricoRepository historicoRepository;
    private Object HistoryController;

    @GetMapping("/historicos")
    public List<Historico> listaHistoricos(){
        return historicoRepository.findAll();
    }

    @RequestMapping(value = "/{placa}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ArrayList<JSONObject> listByPlaca(@PathVariable(value = "placa") String placa, HttpServletResponse response) throws ParseException, JSONException, net.minidev.json.parser.ParseException {

        List<Historico> historico = historicoRepository.findAllByPlaca(placa);
        ArrayList<JSONObject> retorno = new ArrayList<>();
        int count = 0;
        for (Historico index : historico){
            String time_permanencia = "";
            String hr_entrada = index.getHr_entrada();
            String hr_saida = index.getHr_saida();


            boolean pago = index.isPago();
            boolean saida = index.isSaida();
            long id = index.getId();
            long permanencia = controller.getDateDiff(hr_entrada, hr_saida);

            // Verifica se o mesmo ainda se encontra permanete ou não
            if (permanencia == -1){
                time_permanencia = "\" Ainda Permanente \"";
            }
            else{
                time_permanencia = "\"" + permanencia + " minutos \"";
            }

            // Monta o retorno em json
            String json = this.controller.makeJsonHistoricoReturn(time_permanencia, id, pago, saida);

            // Realiza a conversão do string para json
            JSONParser parser = new JSONParser();
            JSONObject js_parsed = (JSONObject) parser.parse(json);

            //Adiciona o retorno
            retorno.add(js_parsed);

        }

        return retorno;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String entrada(@RequestBody Historico historico){
        //Valida a placa informada
        String placa = historico.getPlaca();
        boolean valid_placa = controller.validatedMask(placa);

        // Data atual
        String entrada = controller.actualDate();

        if (valid_placa){

            int count = (int) historicoRepository.count();
            if (count > 0){
                try{
                    // Caso exista registro sem ter seido efetuada a saida
                    Historico his = historicoRepository.findByPlaca(placa);

                    if (!his.isSaida()){
                        return "{\"Reserva já registrada:\"" + his.getId() + " }";
                    }
                }
                catch (Exception y){
                    //Caso normal
                    historico.setHr_entrada(entrada);
                    Historico register = historicoRepository.save(historico);
                    return "{\"Reserva registrada:\"" + register.getId() + " }";
                }

            }
            else{
                //Primeiro registro do historico
                historico.setHr_entrada(entrada);
                Historico register = historicoRepository.save(historico);
                return "{\"Reserva registrada:\"" + register.getId() + " }";
            }

        }
        else {
            // Caso a placa esteja invalida
            return "{\"Formato de placa invalido\"}";
        }
        //Saida Padrão
        historico.setHr_entrada(entrada);
        Historico register = historicoRepository.save(historico);
        return "{\"Reserva registrada:\"" + register.getId() + " }";

    }

    @RequestMapping(value = "/{id}/saida", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateSaida(@PathVariable(value = "id") long id){
        Optional<Historico> historico = historicoRepository.findById(id);

        // Não permite a saida sem pagamento
        if (historico.get().isPago()){
            historicoRepository.setSaida(id, controller.actualDate());
            return "{ \"Saida Realizada \"}";
        }
        else{
            return "{\"Nescessita do pagamento\"}";
        }
    }

    @RequestMapping(value = "/{id}/pagamento", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updatePagamento(@PathVariable(value = "id") long id){
        Optional<Historico> historico = historicoRepository.findById(id);

        // Caso já exista um pagamento realizado
        if (historico.get().isPago()){
            return "{ \"Pagamento já realizado\" }";
        }
        else{
            historicoRepository.setPago(id);
            return "{ \"Pagamento realizado\" }";
        }

    }

}
