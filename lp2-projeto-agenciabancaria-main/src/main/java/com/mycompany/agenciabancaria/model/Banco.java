package com.mycompany.agenciabancaria.model;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<Agencia> agencias;

    public Banco() {
        this.agencias = new ArrayList<>();
    }

    // Adicionar agência ao banco
    public boolean adicionarAgencia(Agencia agencia) {
        for (Agencia a : agencias) {
            if (a.getNumero() == agencia.getNumero()) {
                return false; // Agência já existe
            }
        }
        agencias.add(agencia);
        return true;
    }

    // Buscar agência pelo número
    public Agencia buscarAgencia(int numero) {
        for (Agencia agencia : agencias) {
            if (agencia.getNumero() == numero) {
                return agencia;
            }
        }
        return null;
    }

    // Obter todas as agências
    public List<Agencia> getAgencias() {
        return agencias;
    }
}