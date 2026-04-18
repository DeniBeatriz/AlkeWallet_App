package com.example.alkewallet

import com.example.alkewallet.model.SendModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SendModelTest {

    @Before
    fun setup() {
        // Reiniciamos el saldo antes de cada prueba para que sean independientes
        // Como SendModel es un object (Singleton), necesitamos una forma de resetearlo
        // o sumar el valor base necesario para el test.
        val saldoActual = SendModel.getSaldoTotal()
        if (saldoActual > 0) {
            SendModel.restarSaldo(saldoActual)
        }
    }

    @Test
    fun `sumarSaldo aumenta el saldo total correctamente`() {
        // Given: Iniciamos con saldo 0
        val montoASumar = 100.0

        // When: Sumamos 100
        val exito = SendModel.sumarSaldo(montoASumar)

        // Then: El éxito es true y el saldo es 100
        Assert.assertTrue(exito)
        Assert.assertEquals(100.0, SendModel.getSaldoTotal(), 0.01)
    }

    @Test
    fun `restarSaldo descuenta el monto cuando hay saldo suficiente`() {
        // Given: Tenemos 500 de saldo
        SendModel.sumarSaldo(500.0)

        // When: Restamos 200
        val exito = SendModel.restarSaldo(200.0)

        // Then: El éxito es true y el saldo restante es 300
        Assert.assertTrue(exito)
        Assert.assertEquals(300.0, SendModel.getSaldoTotal(), 0.01)
    }

    @Test
    fun `restarSaldo falla cuando el monto es mayor al saldo disponible`() {
        // Given: Tenemos 50 de saldo
        SendModel.sumarSaldo(50.0)

        // When: Intentamos restar 100
        val exito = SendModel.restarSaldo(100.0)

        // Then: El éxito es false y el saldo se mantiene en 50
        Assert.assertFalse(exito)
        Assert.assertEquals(50.0, SendModel.getSaldoTotal(), 0.01)
    }

    @Test
    fun `sumarSaldo rechaza montos negativos`() {
        // Given: Saldo inicial 0
        val montoNegativo = -50.0

        // When: Intentamos sumar un valor negativo
        val exito = SendModel.sumarSaldo(montoNegativo)

        // Then: El éxito es false y el saldo sigue en 0
        Assert.assertFalse(exito)
        Assert.assertEquals(0.0, SendModel.getSaldoTotal(), 0.01)
    }
}