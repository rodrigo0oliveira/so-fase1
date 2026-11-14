package com.so;

import com.so.entities.GeradorDeProcessos;
import com.so.entities.Processo;
import com.so.entities.Resultado;
import com.so.escalonamento.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimuladorCPU {
    public static final int SLEEPMS = 10;
    private static final int[] QUANTUNS = {2, 5, 10};

    private final List<Processo> originais = GeradorDeProcessos.gerarProcessos(3);
    private final List<Resultado> resultados = new ArrayList<>();

    public void executarTodos() throws InterruptedException {
        processarNaoPreemptivos();
        processarPreemptivo();
        exibirOriginais();
        exibirRelatorio();
    }

    public void processarNaoPreemptivos() throws InterruptedException {
        rodar("FCFS", new EscalonadorFCFS(GeradorDeProcessos.copiar(originais)), Integer.MAX_VALUE);
        rodar("SJF", new EscalonadorSJF(GeradorDeProcessos.copiar(originais)), Integer.MAX_VALUE);
        rodar("PRIORIDADE", new EscalonadorPrioridade(GeradorDeProcessos.copiar(originais)), Integer.MAX_VALUE);
    }

    public void processarPreemptivo() throws InterruptedException {
        for (int quantum : QUANTUNS) {
            rodar("RR (q=" + quantum + ")",
                    new EscalonadorRoundRobin(GeradorDeProcessos.copiar(originais)), quantum);
        }
    }

    private void rodar(String nome, Escalonador escalonador, int quantum) throws InterruptedException {
        System.out.println();
        System.out.println("==================== " + nome + " ====================");

        List<Processo> todos = new ArrayList<>();
        List<Integer> ordem = new ArrayList<>();
        long relogio = 0;
        long somaEspera = 0;
        int finalizados = 0;
        int trocas = 0;
        int ultimoId = -1;

        while (true) {
            Processo processo = escalonador.next();
            if (processo == null) {
                break;
            }
            if (!todos.contains(processo)) {
                todos.add(processo);
            }

            if (ultimoId != -1 && ultimoId != processo.getId()) {
                trocas++;
            }
            ultimoId = processo.getId();
            ordem.add(processo.getId());

            int antes = processo.getEtapaAtual();
            System.out.println("[EXECUTANDO] Processo " + processo.getId() + " | tick " + relogio);
            processo.executar(quantum);
            relogio += processo.getEtapaAtual() - antes;

            if (processo.terminou()) {
                long espera = relogio - processo.getEtapaAtual();
                processo.setTempoEspera(espera);
                somaEspera += espera;
                finalizados++;
                System.out.println("[FINALIZADO] Processo " + processo.getId()
                        + " | tick " + relogio + " | espera: " + espera + " ticks");
            } else {
                escalonador.put(processo);
            }

            GeradorDeProcessos.getProcessosAguardando(todos, processo);
            GeradorDeProcessos.getProcessosFinalizados(todos);
        }

        double media = finalizados == 0 ? 0 : (double) somaEspera / finalizados;
        String ordemTexto = ordem.stream().map(id -> "P" + id).collect(Collectors.joining(" > "));
        System.out.printf("[%s] Espera média: %.2f ticks | Trocas: %d%n", nome, media, trocas);
        resultados.add(new Resultado(nome, ordemTexto, media, trocas));
    }

    private void exibirOriginais() {
        System.out.println("=========== PROCESSOS ===========");
        originais.forEach(p -> System.out.printf("Processo %d | instruções: %d | prioridade: %d%n",
                p.getId(), p.getQuantidadeInstrucoes(), p.getPrioridade()));
    }

    private void exibirRelatorio() {
        System.out.println();
        System.out.println("=========================== RELATÓRIO COMPARATIVO ===========================");
        System.out.printf("%-14s | %-11s | %-7s | %s%n", "Algoritmo", "Espera méd.", "Trocas", "Ordem de execução");
        for (Resultado r : resultados) {
            System.out.printf("%-14s | %-11.2f | %-7d | %s%n",
                    r.getAlgoritmo(), r.getEsperaMedia(), r.getTrocas(), r.getOrdem());
        }
    }
}