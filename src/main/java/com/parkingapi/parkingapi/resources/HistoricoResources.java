package com.parkingapi.parkingapi.resources;


import com.parkingapi.parkingapi.models.Historico;
import com.parkingapi.parkingapi.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/estacionamentos")
public class HistoricoResources {

    @Autowired
    HistoricoRepository historicoRepository;
    private Object HistoryController;

    @GetMapping("/historicos")
    public List<Historico> listaHistoricos(){
        return historicoRepository.findAll();
    }

    @GetMapping("/{placa}")
    public Historico listByPlaca(@PathVariable(value = "placa") String placa){
        return historicoRepository.findByPlaca(placa);
    }

    @PostMapping("")
    public Historico entrada(@RequestBody Historico historico){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        historico.setHr_entrada(dtf.format(now));
        return historicoRepository.save(historico);
    }

    @RequestMapping(value = "/{id}/saida", method = RequestMethod.PUT)
    public String updateSaida(@PathVariable(value = "id") long id){
        Optional<Historico> historico = historicoRepository.findById(id);
        if (historico.get().isPago() == true){
            historico.get().setSaida(true);
            return "Saida Realizada";
        }
        else{
            return "Nescessita do pagamento";
        }
    }

    @RequestMapping(value = "/{id}/pagamento", method = RequestMethod.PUT)
    public String updatePagamento(@PathVariable(value = "id") long id){
        Optional<Historico> historico = historicoRepository.findById(id);
        historico.get().setPago(true);
        return "Pagamento Realizado";
    }

}
