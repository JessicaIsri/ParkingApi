package com.parkingapi.parkingapi.repository;

import com.parkingapi.parkingapi.models.Historico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    Historico findByPlaca(String placa);

}
