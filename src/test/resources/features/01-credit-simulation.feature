@web @flujo1 @flujo2 @simulacion
Feature: 01 - Simulación de créditos

  Background:
    Given que el usuario ingresa al simulador de creditos

  Scenario Outline: Simulación de crédito con validación de resultados financieros
    When el usuario selecciona el credito por ID "<idCredito>"
    And ingresa la informacion financiera del simulador "<idCredito>"
    Then se genera la simulacion del credito correctamente
    And se valida la cuota mensual, tasa de interes y tabla de amortizacion

    Examples:
      | idCredito       |
      | preciso_base    |
      | vivienda_base   |