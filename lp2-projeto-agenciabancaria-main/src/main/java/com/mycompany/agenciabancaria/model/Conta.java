package com.mycompany.agenciabancaria.model;

public abstract class Conta {
    protected int numero;
    protected double saldo;
    protected Titular dono;
    
    public Conta (int numero, double saldoInicial, Titular dono)
    {
        this.numero = numero;
        this.saldo = saldoInicial;
        this.dono = dono;
    }
    
    public int getNumero()
    {
        return numero;
    }
    public double getSaldo()
    {
        return saldo;
    }
    public Titular getDono()
    {
        return dono;
    }
    protected  void setSaldo(double saldo){
        this.saldo = saldo;
    }
    public void Depositar(double valor)
    {
        saldo = saldo + valor;
    }
    public boolean Sacar(double valor)
    {
        if (saldo >= valor)
        {
            saldo = saldo - valor;
            return true;
        }
        return false;
    }
    public boolean transferir(double valor, Conta ContaDestino)
    {
        if (saldo >= valor) 
        {
            saldo = saldo - valor;
            ContaDestino.Depositar(valor);
            return true;
        }
        return false;
    }
    public abstract String getTipo();
}
