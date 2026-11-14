package com.so.escalonamento;

import com.so.entities.Processo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EscalonadorPrioridade implements Escalonador {

    private final Queue<Processo> processos;

    public EscalonadorPrioridade(List<Processo> processosList) {
        this.processos = ordenarProcessos(processosList);
    }

    @Override
    public Queue<Processo> ordenarProcessos(List<Processo> processos) {
        var order = processos.stream()
                .sorted(Comparator.comparingInt(Processo::getPrioridade))
                .toList();
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