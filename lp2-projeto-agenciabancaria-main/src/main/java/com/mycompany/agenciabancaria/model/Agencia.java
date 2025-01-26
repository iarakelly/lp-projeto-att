package com.mycompany.agenciabancaria.model;

/**
 *
 * @author iarak
 */

import java.util.ArrayList;
import java.util.List;

public class Agencia {
    private int numero;
    private String nome;
    private List<Conta> contas;
    private List<Titular> usuarios;


    public Agencia(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
        this.contas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public List<Conta> getContas() {
        return contas;
    }
    
    public List<Titular> getUsuarios() {
        return usuarios;
    }

    public boolean adicionarConta(Conta conta) {
        for (Conta c : contas) {
            if (c.getNumero() == conta.getNumero()) {
                return false; // Conta já existe
            }
        }
        contas.add(conta);
        return true;
    }

    public Conta buscarConta(int numero) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }
    
    public boolean adicionarUsuario(Titular titular) {
        for (Titular t : usuarios) {
            if (t.getCPF().equals(titular.getCPF())) {
                return false; // Usuário já cadastrado
            }
        }
        usuarios.add(titular);
        return true;
    }

    public Titular buscarUsuario(String cpf) {
        for (Titular titular : usuarios) {
            if (titular.getCPF().equals(cpf)) {
                return titular;
            }
        }
        return null;
    }
}