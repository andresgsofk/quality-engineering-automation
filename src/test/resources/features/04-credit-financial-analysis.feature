@web @flujo4 @calculos-financieros
Feature: 04 - Validación de cálculos financieros del simulador

  Background:
    Given que el usuario ingresa al simulador de creditos

  Scenario Outline: Validación matemática de la tabla de amortización
    When el usuario selecciona el credito por ID "<idCredito>"
    And ingresa la informacion financiera del simulador "<idCredito>"
    Then se validan los calculos financieros de la simulacion "<idCredito>"

    Examples:
      | idCredito             |
      | preciso_base          |
      | preciso_monto_30000   |
      | preciso_monto_45000   |
