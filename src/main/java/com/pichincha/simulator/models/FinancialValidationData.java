package com.pichincha.simulator.models;

public class FinancialValidationData extends CreditSimulationData {

    private String mensajeEsperado;
    private String campoValidacion;

    public FinancialValidationData() {
    }

    // =========================
    // VALIDATION FIELDS
    // =========================
    public String getMensajeEsperado() {
        return mensajeEsperado;
    }

    public void setMensajeEsperado(String mensajeEsperado) {
        this.mensajeEsperado = mensajeEsperado;
    }

    public String getCampoValidacion() {
        return campoValidacion;
    }

    public void setCampoValidacion(String campoValidacion) {
        this.campoValidacion = campoValidacion;
    }

    // =========================
    // DEBUG (OPCIONAL PERO ÚTIL)
    // =========================
    @Override
    public String toString() {
        return "FinancialValidationData{" +
                "tipoCredito='" + getTipoCredito() + '\'' +
                ", idCredito='" + getIdCredito() + '\'' +
                ", montoPrestamo='" + getMontoPrestamo() + '\'' +
                ", montoVivienda='" + getMontoVivienda() + '\'' +
                ", term='" + getTerm() + '\'' +
                ", mensajeEsperado='" + mensajeEsperado + '\'' +
                ", campoValidacion='" + campoValidacion + '\'' +
                '}';
    }
}