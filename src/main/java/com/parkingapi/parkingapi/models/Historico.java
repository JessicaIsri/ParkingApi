package com.parkingapi.parkingapi.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="TB_HISTORICO")
public class Historico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    private String placa;
    private String hr_entrada;
    private String hr_saida;
    private boolean pago;
    private boolean saida;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getHr_entrada() {
        return hr_entrada;
    }

    public void setHr_entrada(String hr_entrada) {
        this.hr_entrada = hr_entrada;
    }

    public String getHr_saida() {
        return hr_saida;
    }

    public void setHr_saida(String hr_saida) {
        this.hr_saida = hr_saida;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public boolean isSaida() {
        return saida;
    }

    public void setSaida(boolean saida) {
        this.saida = saida;
    }


}
