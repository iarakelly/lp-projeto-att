package com.mycompany.agenciabancaria.view;

import com.mycompany.agenciabancaria.persistence.BancoPersistencia;


import com.mycompany.agenciabancaria.controller.AgenciaController;
import com.mycompany.agenciabancaria.model.Titular;
import com.mycompany.agenciabancaria.model.Agencia;
import com.mycompany.agenciabancaria.model.Banco;




import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AgenciaBancariaView 
{
    private final Banco banco;
    private final AgenciaController controller;
    private final Scanner scanner;
    private Agencia agenciaSelecionada;
    
    private Agencia agencia1;
    private Agencia agencia2;


    
    public AgenciaBancariaView()
    {
        controller = new AgenciaController();
        scanner = new Scanner(System.in);
        this.agenciaSelecionada = null;
        banco = new Banco();
        
        agencia1 = new Agencia(1, "Agência Centro");
        agencia2 = new Agencia(2, "Agência Norte");
        
        banco.adicionarAgencia(agencia1);
        banco.adicionarAgencia(agencia2);

    }
    
    public void exibirMenu()
    {
        while (true) {
            System.out.println("========= Banco =========");//anterior agencia            
            System.out.println("1 - Cadastrar usuário "); // anterior Cadastro
            System.out.println("2 - Login "); // anterior Cadastro
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) 
            {
                case 1: //ALTERAR PARA SELECIONAR A AGENCIA
                    cadastrarUsuario();                                          
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida, tente novamente!");
                    break;
            }
        }
        
    }
    
    public void cadastrarUsuario() 
    {
        System.out.println("======= Cadastro ========"); 
        System.out.print("Informe seu nome: ");
        String nome = scanner.nextLine();
        System.out.print("Informe seu CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Informe sua senha: ");
        String senha = scanner.nextLine();
        
        if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            System.out.println("Todos os campos devem ser preenchidos.");
            return;
        }
        
        try {
            if (controller.cadastrarUsuario(nome, cpf, senha)) {
                System.out.println("Usuário cadastrado com sucesso!");
                exibirMenu();
            } 
            else {
            System.out.println("Usuário já cadastrado! Tente realizar Login.");
            exibirMenu();
            }
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    public void login()
    {
        System.out.println("========= Login ========="); 
        System.out.print("Informe seu CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Informe sua senha: ");
        String senha = scanner.nextLine();

        
        try
        {
            Titular usuario = controller.autenticarUsuario(cpf, senha);
            if (usuario != null)
            {
            System.out.println("Bem vind@, " + usuario.getNome() + "!");
            System.out.println("Selecione sua agência: ");
            
                if(selecionarAgencia()){
                    exibirMenuOperacoes();
                }
                else{
                    exibirMenu();
                }            
            }
            else
            {
            System.out.println("Credenciais inválidas, verifique o CPF ou senha.");                           
            exibirMenu();
            } 
        }
        catch (IllegalArgumentException e) {
        System.out.println("Erro: " + e.getMessage());} 
        catch (NullPointerException e) {
        System.out.println("Erro inesperado: Verifique se os dados estão corretos ou tente novamente.");} 
        catch (Exception e) {
        System.out.println("Erro inesperado: " + e.getMessage());}             
    }
    
    public boolean selecionarAgencia()
    {
        try{
            System.out.println("======== Agencias ========"); 
            List<Agencia> agencias = banco.getAgencias();
            
            if (agencias == null || agencias.isEmpty()) {
            System.out.println("Nenhuma agência disponível no momento.");
            return false;
            }
            
            for (int i = 0; i < agencias.size(); i++) {
            System.out.println((i + 1) + " - " + agencias.get(i).getNome());        
            }
            System.out.print("Informe o número da agência: ");
             if (!scanner.hasNextInt()) {
                System.out.println("Erro: Entrada inválida. Por favor, informe um número.");
                scanner.nextLine(); // Limpa a entrada inválida
                return false;
             }

            int agencia = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
            
            
            if (agencia < 1 || agencia > agencias.size()) {
            System.out.println("Erro: Número da agência inválido. Selecione um valor inteiro entre 1 e " + agencias.size() + ".");
            return false;
            }

            agenciaSelecionada = banco.buscarAgencia(agencia);
            if (agenciaSelecionada == null){
                System.out.println("Erro: Agência não encontrada.");
            } 
            else{
            System.out.println("Agência selecionada: " + agenciaSelecionada.getNome());
            return true;
            }            
        }
        
        catch (InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. Por favor, insira um número.");
            scanner.nextLine(); // Limpa o buffer
        }
        catch (Exception e) {
        System.out.println("Erro inesperado: " + e.getMessage());
        }
        
    return false;
    }
      
    private void exibirMenuOperacoes()
    {
            System.out.println("=============================="); 
            System.out.println("1 - Verificar Saldo");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Transferir");
            System.out.println("5 - Cadastrar nova conta");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma operação: ");
            int opcao = scanner.nextInt();

            switch(opcao)
            {
                case 1 -> verificarSaldo();
                case 2 -> depositar();
                case 3 -> sacar();
                case 4 -> transferir();
                case 5 -> cadastrarConta();
                case 6 -> { 
                    agenciaSelecionada = null;
                    return;
            }
                default -> {  System.out.println("Opção inválida. Tente novamente.");
            }
            }
    }
    
    public void verificarSaldo()
    {
        try{
            System.out.print("Informe o número da conta: ");
            int numeroConta = scanner.nextInt();
            scanner.nextLine();

            double saldo = controller.getSaldo(numeroConta);
            if (saldo >= 0) {
                System.out.println("O saldo disponível é R$" + saldo);
                voltarMenu();
            } 
            else {
                System.out.println("Conta inexistente.");
                voltarMenu();
            }
        }
         catch (java.util.InputMismatchException e) {
            System.out.println("Erro: O número da conta deve ser um valor inteiro.");
            scanner.nextLine(); 
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao verificar o saldo: " + e.getMessage());
        }
    }
    public void depositar()
    {   
        try{
            System.out.println("======== Depósito =========="); 
            System.out.print("Informe o número da conta em que deseja depositar: ");
            int numeroConta = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Digite o valor para depósito: ");
            double valor = scanner.nextDouble();

            if(valor <= 0) 
            {
                System.out.println("O valor precisa ser maior que 0.");
                voltarMenu();
            }

            else if(controller.depositar(valor, numeroConta) == true)
            {
                System.out.println("Depósito realizado com sucesso!");
                voltarMenu();
            }
            else
            {
                System.out.println("Depósito mal sucedido, tente novamente!");
                voltarMenu();
            }
        }
        catch (java.util.InputMismatchException e) {
            System.out.println("Erro: O número da conta e o valor do depósito devem ser números válidos.");
            scanner.nextLine(); // Limpar o buffer do scanner
            voltarMenu();
        } 
        catch (Exception e) {
            System.out.println("Erro ao realizar o depósito: " + e.getMessage());
            voltarMenu();
        }
    }
    public void sacar()
    {
        try{
            System.out.println("===================================="); 
            System.out.print("Informe o número da conta em que deseja sacar: ");
            int numeroConta = scanner.nextInt();
            System.out.print("Informe o valor que deseja sacar: ");
            double valorSaque = scanner.nextDouble();

            if(valorSaque <= 0)
            {
                System.out.println("O valor precisa ser maior que 0.");
                exibirMenu();
            }        
            else if(controller.sacar(valorSaque, numeroConta))
            {
                System.out.println("Valor de R$" + valorSaque + " sacado com sucesso!");
                exibirMenu();
            }
            else
            {
                System.out.println("Algo deu errado!");
                exibirMenu();
            }
        }
        catch (java.util.InputMismatchException e) {
            System.out.println("Erro: O número da conta e o valor do saque devem ser números válidos.");
            scanner.nextLine(); 
            exibirMenu();
        }
        catch (Exception e) {
        System.out.println("Erro ao realizar o saque: " + e.getMessage());
        exibirMenu();
        }
    }
    public void transferir()
    {  
        try{
            System.out.println("========== Transferência ===========");
            System.out.print("Informe o número da conta remetente da transferência: ");
            int contaRemetente = scanner.nextInt();
            System.out.print("Informe o número da conta destinatária da transferência: ");
            int contaDestinataria = scanner.nextInt();
            System.out.print("Informe o valor que deseja transferir: ");
            double valorTrans = scanner.nextDouble();



            if(controller.transferir(valorTrans, contaRemetente, contaDestinataria))
            {
                System.out.println("Valor de R$" + valorTrans + " tranferido para a conta " + contaDestinataria + " com sucesso!");
            }
            else
            {
                System.out.println("Aldo deu errado!");
            }
        }
        catch (java.util.InputMismatchException e) {
        System.out.println("Erro: As contas e o valor da transferência devem ser números válidos.");
        scanner.nextLine();
        } 
        catch (Exception e) {
        System.out.println("Erro ao realizar a transferência: " + e.getMessage());
        }
    }
    public void cadastrarConta()
    {
        try
        {
        System.out.println("====================================");      
        System.out.print("Informe o número da conta que deseja cadastrar: ");
        int numeroConta = scanner.nextInt();
        System.out.print("Informe o saldo inicial da conta: ");
        double saldoInicial = scanner.nextDouble();
        System.out.println("Informe o tipo:");
        System.out.println("1 - Corrente");
        System.out.println("2 - Poupança:");
        System.out.println("3 - Salario:"); //fazer
        int tipo = scanner.nextInt();
                
            if(controller.criarNovaConta(numeroConta, saldoInicial, tipo, agenciaSelecionada)){
            System.out.println("Conta cadastrada com sucesso!");
            voltarMenu();
            }
            else{
            System.out.println("Algo deu errado!");
            voltarMenu();
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Erro: " + e.getMessage());
        }
        catch (Exception e){
        System.out.println("Erro inesperado: " + e.getMessage());
        scanner.nextLine(); // Limpa o buffer para evitar loops
        }
    }
    public void voltarMenu()
    {
        System.out.println("=============================");      
        System.out.println("1 - Voltar ao menu principal. ");
        System.out.println("2 - Voltar ao menu de operações.");
        System.out.println("3 - Sair.");
        System.out.print("Resposta: ");
        int opc = scanner.nextInt();
        
        switch (opc) {
            case 1 -> exibirMenu();
            case 2 -> exibirMenuOperacoes();
            default -> System.exit(0);            
        }
    }
}
