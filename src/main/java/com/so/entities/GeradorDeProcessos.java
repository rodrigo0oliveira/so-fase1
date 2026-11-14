package com.so.entities;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeradorDeProcessos {

    private static final Random random = new Random();
    private static final int MIN_INSTRUCOES = 10;
    private static final int MAX_INSTRUCOES = 50;
    private static final int MIN_PRIORIDADE = 1;
    private static final int MAX_PRIORIDADE = 5;

    public static List<Processo> gerarProcessos(int quantidade) {
        return Stream.generate(GeradorDeProcessos::gerarProcesso)
                .limit(quantidade)
                .collect(Collectors.toList());
    }

    public static Processo gerarProcesso() {
        return new Processo(random.nextInt(MIN_INSTRUCOES, MAX_INSTRUCOES), random.nextInt(MIN_PRIORIDADE, MAX_PRIORIDADE));
    }

    public static List<Processo> getProcessosFinalizados(List<Processo> processos) {
        List<Processo> finalizados = processos.stream().filter(processo -> processo.getQuantidadeInstrucoes() == 0).toList();
        System.out.println("Processos finalizados: " + finalizados.toString());
        return finalizados;
    }

    public static List<Processo> getProcessosAguardando(List<Processo> processos, Processo atual) {
        List<Processo> aguardando = processos.stream().filter(processo -> processo.getQuantidadeInstrucoes() > 0 && processo.getId() != atual.getId()).toList();
        System.out.println("Processos aguardando: " + aguardando.toString());
        return aguardando;
    }

    public static List<Processo> copiar(List<Processo> originais) {
        return originais.stream()
                .map(p -> new Processo(p.getId(), p.getQuantidadeInstrucoes(), p.getPrioridade()))
                .collect(Collectors.toList());
    }

}
