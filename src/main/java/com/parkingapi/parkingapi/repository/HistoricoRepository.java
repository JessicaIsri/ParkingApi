package com.parkingapi.parkingapi.repository;

import com.parkingapi.parkingapi.models.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    Historico findByPlaca(String placa);
    List<Historico> findAllByPlaca(String placa);
    @Transactional
    @Modifying
    @Query("update Historico u set u.pago = true where u.id = :id")
    int setPago(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Historico u set u.saida = true, u.hr_saida = :saida where u.id = :id")
    int setSaida(@Param("id") Long id, @Param("saida") String saida);


}
