package com.mycompany.agenciabancaria.model;

public class ContaCorrente extends Conta {
    private double taxaManutencao;

    //Taxa de manutenção informada
    public ContaCorrente(int numero, double saldoInicial, Titular dono, double taxaManutencao) {
        super(numero, saldoInicial, dono);
        this.taxaManutencao = taxaManutencao;
    }
    //Com taxa de manutenção padrão
    public ContaCorrente(int numero, double saldoInicial, Titular dono) 
    {
        super(numero, saldoInicial, dono); // classe concreta chama o construtor da classe abstrata
        this.taxaManutencao = 10.00; // Valor padrão

    }

    @Override
    public String getTipo() 
    {
        return "Corrente";
    }
    
    public void cobrarTaxaManutencao() {
        if (getSaldo() >= taxaManutencao) {
            setSaldo(getSaldo() - taxaManutencao);
        } 
        else {
            System.out.println("Saldo insuficiente para cobrar a taxa de manutenção.");
        }
    }
    public double getTaxaManutencao() {
        return taxaManutencao;
    }

    public void setTaxaManutencao(double taxaManutencao) {
        this.taxaManutencao = taxaManutencao;
    }
}
