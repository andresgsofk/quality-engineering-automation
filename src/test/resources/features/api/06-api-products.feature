@api @api-productos
Feature: 06 - Automatización API Fake Store Products

  Como QA Automation SDET
  Quiero validar los endpoints críticos de productos de Fake Store API
  Para asegurar que las operaciones REST principales responden con estructura, datos y comportamiento esperado

  Background:
    Given que la API Fake Store se encuentra disponible

  @api-positive @api-caso1 @positivo @get-producto
  Scenario: Caso 1 - Obtener producto específico exitosamente
    When consulto el producto usando data "producto_valido"
    Then el status code de la API debe coincidir con la data
    And la respuesta debe tener estructura valida del producto consultado

  @api-positive @api-caso2 @positivo @categoria
  Scenario: Caso 2 - Listar productos por categoría
    When consulto los productos por categoria usando data "categoria_electronics"
    Then el status code de la API debe coincidir con la data
    And todos los productos deben pertenecer a la categoria definida en la data

  @api-positive @api-caso3 @positivo @post-producto
  Scenario: Caso 3 - Crear producto exitosamente
    When creo un producto usando data "crear_producto_valido"
    Then el status code de la API debe coincidir con la data
    And la respuesta debe coincidir con el producto definido en la data

  @api-positive @api-caso4 @positivo @put-producto
  Scenario: Caso 4 - Actualizar producto completo exitosamente
    When actualizo un producto usando data "actualizar_producto_valido"
    Then el status code de la API debe coincidir con la data
    And la respuesta debe coincidir con el producto definido en la data

  @api-negative @api-caso5 @negativo @producto-no-encontrado
  Scenario: Caso 5 - Producto no encontrado
    When consulto el producto usando data "producto_inexistente"
    Then el status code de la API debe coincidir con la data
    And la respuesta debe manejar el producto inexistente

  @api-negative @api-caso6 @negativo @categoria-invalida
  Scenario: Caso 6 - Categoría inválida
    When consulto los productos por categoria usando data "categoria_invalida"
    Then el status code de la API debe coincidir con la data
    And la respuesta debe retornar una lista vacia para la categoria invalida

  @api-negative @api-caso7 @negativo @payload-invalido
  Scenario: Caso 7 - Crear producto con payload vacío
    When creo un producto usando data "payload_vacio"
    Then el status code de la API debe coincidir con la data
    And la API debe manejar controladamente el payload invalido

  @api-caso8 @limites @limit-productos
  Scenario: Caso 8 - Validar límite de productos
    When consulto productos con limite usando data "limite_productos"
    Then el status code de la API debe coincidir con la data
    And la respuesta debe retornar maximo el limite definido en la data
