package com.mycompany.agenciabancaria.controller;

import com.mycompany.agenciabancaria.model.Conta;
import com.mycompany.agenciabancaria.model.ContaCorrente;
import com.mycompany.agenciabancaria.model.ContaPoupanca;
import com.mycompany.agenciabancaria.model.ContaSalario;
import com.mycompany.agenciabancaria.model.Titular;
import com.mycompany.agenciabancaria.model.Agencia;

import java.util.ArrayList;
import java.util.List;

public class AgenciaController 
{
    private List<Agencia> agencias = new ArrayList<>();
    private List<Titular> usuarios = new ArrayList();
    private Agencia agenciaSelecionada;
    private Titular usuarioLogado;
    
    
    public boolean cadastrarAgencia(int numero, String nome) {
        if (numero <= 0 || nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Número da agência deve ser positivo e nome não pode ser vazio.");
        }
        for (Agencia agencia : agencias) {
            if (agencia.getNumero() == numero) {
                return false; // Agência já existe
            }
        }
        agencias.add(new Agencia(numero, nome));
        return true;
    }
    
    public boolean selecionarAgencia(int numero) {
        if (numero <= 0) 
        {
            throw new IllegalArgumentException("Número da agência deve ser positivo.");
        }
        for (Agencia agencia : agencias) {
            if (agencia.getNumero() == numero) {
                agenciaSelecionada = agencia;
                return true;
            }
        }
        return false;
    }
    
     public Agencia getAgenciaSelecionada() {
        return agenciaSelecionada;
    }
    
    public boolean cadastrarUsuario (String nome, String cpf, String senha)
    {
        for (Titular titular : usuarios){
            if(titular.getCPF().equals(cpf)){
                return false;//usuário ja cadastrado
                }
        }
        usuarios.add(new Titular(nome, cpf, senha));
        return true; 
    }
    public Titular autenticarUsuario(String cpf, String senha)
    {
        try{
            for (Titular usuario: usuarios)
            {
                if(usuario.getCPF().equals(cpf) && usuario.getSenha().equals(senha))
                {
                    usuarioLogado = usuario;
                    return usuarioLogado;
                }
            }     
            return null; //se nenhum usuário foi autenticado retorna null
        }
        catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        } 
        catch (Exception e) {
            System.out.println("Erro inesperado ao autenticar usuário: " + e.getMessage());
            return null;
        }
    }
    public Titular getUsuarioLogado()
    {
        return usuarioLogado;
    }
    
    public double getSaldo(int numeroConta)
    {      
        Conta conta = null;
        for( Titular usuario : usuarios)
        {
            for(Conta c : usuario.getContas())
            {
                if(c.getNumero() == numeroConta)
                {
                    conta = c;
                    break;
                }
                if(conta != null)
                {
                    break;
                }
            }
        }
            
        if(conta != null){
            return conta.getSaldo();
        }
        else{
            return -1;
        }
           
    }
    public boolean depositar(double valor, int numeroConta)
    {
        for(Titular usuario: usuarios){
            for(Conta conta: usuario.getContas()){
                if(conta.getNumero() == numeroConta){
                    conta.Depositar(valor);
                    return true;
                }
            }
        } 
        return false; //Conta não existe
    }
    public boolean sacar(double valor, int numeroConta)
    {
        for(Titular usuario: usuarios){
            for(Conta conta: usuario.getContas()){
                if(conta.getNumero() == numeroConta && conta.getSaldo() >= valor){
                    conta.Sacar(valor);
                    return true;
                }
            }
        }             
        return false;  // Conta não existe                  
    }
    public boolean transferir(double valor, int contaRemetente, int contaDestinataria)
    {        
    Conta contaOrigem = null;
    Conta contaDestino = null;
         
        for(Conta conta : usuarioLogado.getContas()) //procurando a primeira conta na lista de contas do usuário
        {
            if(conta.getNumero() == contaRemetente )
            {
                contaOrigem = conta;
                break;
            }
        }
        for(Titular usuario : usuarios) // procurando a conta destino na lista de contas dos usuários da aplicação
        {
            for(Conta conta : usuario.getContas())
            {
                if(conta.getNumero() == contaDestinataria )
                {
                    contaDestino = conta;
                    break;
                }
            }
            if(contaDestino != null)
            {
                break;
            }
        }
        if(contaOrigem != null && contaDestino != null && contaOrigem.getSaldo() >= valor)
        {
            contaOrigem.transferir(valor, contaDestino);
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean criarNovaConta(int numero, double saldoInicial, int tipo, Agencia agencia)
    {
        if(agencia == null)
        {
            return false; //erro
        }
        
        else
        {
            Conta novaConta = null;
            boolean exist = false;
            
             for( Conta conta : usuarioLogado.getContas())
            {
                if(conta.getNumero() == numero)
                {
                    exist = true;
                    break;
                }
            }
             if(tipo == 1 && !exist)
            {
                novaConta = new ContaCorrente(numero, saldoInicial, usuarioLogado);
            }
            else if(tipo == 2 && !exist)
            {
                novaConta = new ContaPoupanca(numero, saldoInicial, usuarioLogado); 
            }
            else if(tipo == 3 && !exist)
            {
                novaConta = new ContaSalario(numero, saldoInicial, usuarioLogado); 
            }
            agencia.adicionarConta(novaConta);
            usuarioLogado.AdicionarConta(novaConta);
            return true;
        }       
    }  
}
