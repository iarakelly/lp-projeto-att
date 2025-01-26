package com.mycompany.agenciabancaria.persistence;

import com.mycompany.agenciabancaria.controller.AgenciaController;
import com.mycompany.agenciabancaria.model.Titular;
import com.mycompany.agenciabancaria.model.Agencia;
import com.mycompany.agenciabancaria.model.Banco;
import com.mycompany.agenciabancaria.model.Conta;
import com.mycompany.agenciabancaria.model.ContaCorrente;
import com.mycompany.agenciabancaria.model.ContaPoupanca;
import com.mycompany.agenciabancaria.model.ContaSalario;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BancoPersistencia {
    private static final String ARQUIVO_USUARIOS = "usuarios.csv";
    private static final String ARQUIVO_CONTAS = "contas.csv";

    public static void salvarUsuarios(List<Titular> usuarios) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            writer.write("nome,cpf\n");
            for (Titular usuario : usuarios) {
                writer.write(usuario.getNome() + "," + usuario.getCPF() + "\n");
            }
        }
    }

    public static List<Titular> carregarUsuarios() throws IOException {
        List<Titular> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            reader.readLine(); // Ignora o cabeçalho
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String nome = partes[0];
                String cpf = partes[1];
                String senha = partes[2];
                usuarios.add(new Titular(nome, cpf, senha));
            }
        }
        return usuarios;
    }

    public static void salvarContas(List<Titular> usuarios) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTAS))) {
            writer.write("numero,saldo,cpfTitular\n");
            for (Titular usuario : usuarios) {
                for (Conta conta : usuario.getContas()) {
                    writer.write(conta.getNumero() + "," + conta.getSaldo() + "," + usuario.getCPF() + "\n");
                }
            }
        }
    }

   public static void carregarContas(List<Titular> usuarios) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTAS))) {
            reader.readLine(); // Ignora o cabeçalho
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                int numero = Integer.parseInt(partes[0]);
                double saldo = Double.parseDouble(partes[1]);
                String cpfTitular = partes[2];
                String tipoConta = partes[3]; // Suponha que o tipo da conta seja a quarta coluna

                // Encontra o titular pelo CPF
                for (Titular usuario : usuarios) {
                    if (usuario.getCPF().equals(cpfTitular)) {
                        // Cria a conta de acordo com o tipo
                        Conta conta = null;
                        switch (tipoConta) {
                            case "contaCorrente":
                                conta = new ContaCorrente(numero, saldo, usuario);
                                break;
                            case "contaPoupanca":
                                conta = new ContaPoupanca(numero, saldo, usuario);
                                break;
                            case "contaSalario":
                                conta = new ContaSalario(numero, saldo, usuario);
                                break;
                        }

                        if (conta != null) {
                            usuario.AdicionarConta(conta);  // Adiciona a conta ao titular
                        }
                        break;
                    }
                }
            }
        }
    }
   public static void salvarContasT(List<Titular> usuarios) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTAS))) {
        // Escreve o cabeçalho
        writer.write("numero,saldo,cpfTitular,tipoConta");
        writer.newLine();
        
        // Percorre todos os usuários e suas contas para gravar no arquivo
        for (Titular usuario : usuarios) {
            for (Conta conta : usuario.getContas()) {
                // Escreve os dados de cada conta no formato CSV
                writer.write(conta.getNumero() + "," +
                             conta.getSaldo() + "," +
                             conta.getDono().getCPF() + "," +
                             conta.getTipo());
                writer.newLine();
            }
        }
    }
}
}