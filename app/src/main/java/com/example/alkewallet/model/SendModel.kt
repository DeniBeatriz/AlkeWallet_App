package com.example.alkewallet.model

//class SendModel private constructor(){


object SendModel{
    private var saldoTotal: Double = 352.45


    //Método para restar al saldo
    fun restarSaldo(monto: Double): Boolean {
        if (monto > 0 && monto <= saldoTotal) {
            saldoTotal -= monto
            return true
        } else {
            return false
        }
    }

    fun sumarSaldo(monto: Double): Boolean {
        return if (monto >= 0) {
            saldoTotal += monto
            true
        } else {
            false
        }
    }
        fun getSaldoTotal(): Double {
            return saldoTotal
        }

        }

