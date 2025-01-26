package com.mycompany.agenciabancaria.model;

public class ContaSalario extends Conta 
{
    private int limiteSaques;
    private int saquesRealizados;
    
    public ContaSalario(int numero, double saldoInicial, Titular dono, int limiteSaques)
    {
        super(numero, saldoInicial, dono);
        this.limiteSaques = limiteSaques;
        this.saquesRealizados = 0;
    }
    
    public ContaSalario(int numero, double saldoInicial, Titular dono)
    {
        super(numero, saldoInicial, dono);
        this.limiteSaques = 10;
        this.saquesRealizados = 0;
    }
    
    @Override
    public String getTipo()
    {
        return "Salário";
    }
    
    @Override
    public boolean Sacar(double valor) 
    {
        if (saquesRealizados < limiteSaques) {
            if (getSaldo() >= valor) {
                setSaldo(getSaldo() - valor);
                saquesRealizados++;
                return true;
            } else {
                return false;
            }
        } 
        else {
            return false;
        }
    }
    
   public boolean depositarEmpregador(double valor) 
   {
        if (valor > 0) {
            setSaldo(getSaldo() + valor);
            return true;
        } 
        else {
            return false;
        }
    }

    public int getLimiteSaques() {
        return limiteSaques;
    }

    public void setLimiteSaques(int limiteSaques) {
        this.limiteSaques = limiteSaques;
    }

    public int getSaquesRealizados() {
        return saquesRealizados;
    }       
}
