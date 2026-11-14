package com.so.escalonamento;

import com.so.entities.Processo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EscalonadorSJF implements Escalonador {

    private Queue<Processo> processos;

    public EscalonadorSJF(List<Processo> processosList) {
        this.processos = ordenarProcessos(processosList);
    }

    @Override
    public Queue<Processo> ordenarProcessos(List<Processo> processos) {
        var order = processos.stream().sorted(Comparator.comparing(Processo::getQuantidadeInstrucoes)).toList();
        return new LinkedList<>(order);
    }

    @Override
    public Processo next() {
        return this.processos.poll();
    }

    @Override
    public void put(Processo processo) {

    }
}
