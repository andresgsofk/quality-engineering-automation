@web @flujo5 @comparacion-productos
Feature: 05 - Comparación de productos financieros

  Background:
    Given que el usuario ingresa al simulador de creditos

  Scenario: Comparación entre crédito de consumo y crédito hipotecario
    When el usuario compara el producto "preciso_comparacion" contra el producto "vivienda_comparacion"
    Then se validan las diferencias financieras y condiciones entre los productos
