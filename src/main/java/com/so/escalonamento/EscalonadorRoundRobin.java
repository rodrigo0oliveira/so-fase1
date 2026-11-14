package com.so.escalonamento;

import com.so.entities.Processo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EscalonadorRoundRobin implements Escalonador {

    private Queue<Processo> processos;

    public EscalonadorRoundRobin(List<Processo> processos) {
        this.processos = ordenarProcessos(processos);
    }

    @Override
    public Queue<Processo> ordenarProcessos(List<Processo> processos) {
        return new LinkedList<>(processos);
    }

    @Override
    public Processo next() {
        if (processos.isEmpty()) {
            System.out.println("Nenhum processo restante na fila");
            return null;
        }

        Processo processo = processos.element();

        if (processo.terminou()) {
            processos.remove();
            return next();
        } else {
            return processos.poll();
        }

    }

    @Override
    public void put(Processo processo) {
        if (processo == null) return;

        if (processo.getQuantidadeInstrucoes() == 0) return;

        processos.add(processo);
    }
}
