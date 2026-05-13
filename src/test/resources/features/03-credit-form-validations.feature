@web @flujo3 @validaciones-formulario
Feature: 03 - Validación de formularios financieros

  Background:
    Given que el usuario ingresa al simulador de creditos

  Scenario Outline: Validación de reglas financieras del simulador
    When el usuario selecciona el credito de validacion por ID "<idValidacion>"
    And ingresa datos invalidos del simulador "<idValidacion>"
    Then el sistema muestra el mensaje de validacion esperado para "<idValidacion>"

    Examples:
      | idValidacion          |
      | preciso_monto_vacio   |
      | preciso_monto_minimo  |
      | preciso_monto_maximo  |
      | vivienda_monto_vacio  |
      | vivienda_monto_minimo |

