package com.pichincha.simulator.models;

public class CreditSimulationData {

    private String tipoCredito;
    private String idCredito;
    private String montoPrestamo;
    private String montoVivienda;
    private String term;

    public CreditSimulationData() {
    }

    // =========================
    // GETTERS
    // =========================
    public String getTipoCredito() {
        return tipoCredito;
    }

    public String getIdCredito() {
        return idCredito;
    }

    public String getMontoPrestamo() {
        return montoPrestamo;
    }

    public String getMontoVivienda() {
        return montoVivienda;
    }

    public String getTerm() {
        return term;
    }

    // =========================
    // SETTERS (IMPORTANTE PARA JACKSON)
    // =========================
    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public void setIdCredito(String idCredito) {
        this.idCredito = idCredito;
    }

    public void setMontoPrestamo(String montoPrestamo) {
        this.montoPrestamo = montoPrestamo;
    }

    public void setMontoVivienda(String montoVivienda) {
        this.montoVivienda = montoVivienda;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}