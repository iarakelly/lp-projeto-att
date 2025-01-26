package com.mycompany.agenciabancaria.model;

public class ContaPoupanca extends Conta    
{
    private double taxaRendimento;
    
    //Construtor indicando taxaRendimento
    public ContaPoupanca (int numero, double saldoInicial, Titular dono, double taxa)
    {
        super(numero, saldoInicial, dono);
        this.taxaRendimento = taxa;
    }
    //Construtor com taxa padrão
    public ContaPoupanca (int numero, double saldoInicial, Titular dono)
    {
        super(numero, saldoInicial, dono);
        this.taxaRendimento = 0.005; //Taxa padrão de 0,5% ao mês
    }
    
    @Override
    public String getTipo()
    {
        return "Poupança";
    }
    
    public void aplicarRendimento() {
        double rendimento = getSaldo() * taxaRendimento;
        setSaldo(getSaldo() + rendimento);
    }

    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }
}
