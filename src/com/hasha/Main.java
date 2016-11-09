package com.hasha;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {


    private static Scanner entrada;
    private static PrintStream saida;

    private static final int VAGAS_NA_TURMA = 10;

    private static String[] alunos = new String[VAGAS_NA_TURMA];
    private static Double[] av1 = new Double[VAGAS_NA_TURMA];
    private static Double[] av2 = new Double[VAGAS_NA_TURMA];

    public static void main(String[] args) throws Exception {

        entrada = new Scanner(System.in, "850");
        saida = new PrintStream(System.out, true, "850");

        final int ITEM_REGISTRO_DE_ALUNO = 1;
        final int ITEM_CONSULTAR_BOLETIM = 2;
        final int ITEM_CONSULTAR_NOTAS = 3;
        final int ITEM_SAIR = 4;

        int itemEscolhidoMenu = 4;

        loopPrincipal:
        while (true) {

            exibirMenu();

            boolean repetir;
            do {
                try {
                    saida.print("Item menu: ");
                    itemEscolhidoMenu = Integer.parseInt(entrada.nextLine());
                    if (itemEscolhidoMenu > ITEM_SAIR || itemEscolhidoMenu < ITEM_REGISTRO_DE_ALUNO) {
                        repetir = true;
                        saida.println("Entrada inválida.");
                    } else {
                        repetir = false;
                    }
                } catch (NumberFormatException e) {
                    saida.println("Entrada inválida.");
                    repetir = true;
                }
            } while (repetir);

            saida.println("\n-----------------------------------------------------------------\n");

            switch (itemEscolhidoMenu) {
                case ITEM_REGISTRO_DE_ALUNO:
                    registrarAluno();
                    break;
                case ITEM_CONSULTAR_BOLETIM:
                    consultarBoletimAluno();
                    break;
                case ITEM_CONSULTAR_NOTAS:
                    consultarNotasTurma();
                    break;
                case ITEM_SAIR:
                    saida.println("Saindo do programa...");
                    break loopPrincipal;
            }
            saida.println("\n-----------------------------------------------------------------\n");
        }

        saida.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                "░░░░░ ░░░░▀█▄▀▄▀██████░▀█▄▀▄▀████▀\n" +
                "░░░░ ░░░░░░░▀█▄█▄███▀░░░▀██▄█▄█▀");

        saida.close();
        entrada.close();
    }

    private static void exibirMenu() {
        System.out.println("[1] Registrar as notas de um novo aluno.");
        System.out.println("[2] Consultar boletim de um aluno.");
        System.out.println("[3] Consultar notas da turma.");
        System.out.println("[4] Sair.");
    }

    private static void registrarAluno() {
        int indice = 0;
        boolean indiceDisponivel = false;

        for (int x = 0; x < alunos.length; x++) {
            if (alunos[x] == null) {
                indice = x;
                indiceDisponivel = true;
                break;
            }
        }

        if (!indiceDisponivel) {
            saida.println("Não há mais espaço na turma.");
            return;
        }

        boolean repetir;

        String aluno = null;
        do {
            saida.print("Nome do aluno: ");
            try {
                aluno = entrada.nextLine();
                repetir = false;
            } catch (Exception e) {
                saida.println(">>> Entrada inválida. <<<");
                repetir = true;
            }
        } while (repetir);


        Double _av1 = null;
        do {
            try {
                saida.print("Nota da AV1: ");
                _av1 = Double.parseDouble(entrada.nextLine());
                repetir = false;
            } catch (NumberFormatException e) {
                saida.println(">>> Entrada inválida. <<<");
                repetir = true;
            }
        } while (repetir);


        Double _av2 = null;
        do {
            try {
                saida.print("Nota da AV2: ");
                _av2 = Double.parseDouble(entrada.nextLine());
                repetir = false;
            } catch (NumberFormatException e) {
                saida.println(">>> Entrada inválida. <<<");
                repetir = true;
            }
        } while (repetir);

        alunos[indice] = aluno;
        av1[indice] = _av1;
        av2[indice] = _av2;

        saida.println("Aluno cadastrado com código: " + indice);


    }

    /**
     * Este método tem a responsabilidade de solicitar ao usuário o código correspondente a algum aluno cadastrado
     * e retorna os dados do mesmo na tela.
     */
    private static void consultarBoletimAluno() {

        for (String aluno : alunos) {
            if (aluno != null) {
                break;
            }
            System.out.println("Turma vazia!");
            return;
        }

        boolean repetir;
        int codigoAluno = 0;
        do {
            try {
                saida.print("Código: ");
                codigoAluno = Integer.parseInt(entrada.nextLine());
                repetir = false;
            } catch (NumberFormatException e) {
                saida.println(">>> Entrada inválida. <<<");
                repetir = true;
            }
        } while (repetir);

        try {
            if (alunos[codigoAluno] == null) {
                System.out.println("Código do aluno não existe.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Código do aluno não existe.");
            return;
        }

        saida.println("\nNome: " + alunos[codigoAluno]);
        saida.println("AV1: " + av1[codigoAluno]);
        saida.println("AV2: " + av2[codigoAluno]);
        Double media = (av1[codigoAluno] + av2[codigoAluno]) / 2;
        saida.println("Média: " + String.format("%2.2f",media));
        saida.println("Situação: " + situacaoAluno(media));
    }

    private static void consultarNotasTurma() {

        for (String aluno : alunos) {
            if (aluno != null) {
                break;
            }
            System.out.println("Turma vazia!");
            return;
        }

        saida.println("________________________________________________________________________");
        saida.println(String.format("|%-30s|%6s|%6s|%12s|%12s|", "Nome Aluno", "AV1", "AV2", "Média Final", "Situação"));
        saida.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        for (int x = 0; x < alunos.length; x++) {
            if (alunos[x] == null) {
                continue;
            }

            String nomeAluno = alunos[x];
            Double av1Aluno = av1[x];
            Double av2Aluno = av2[x];
            Double mediaFinal = (av1[x] + av2[x]) / 2;

            saida.println(String.format("|%-30s|%6.2f|%6.2f|%12.2f|%12s|", nomeAluno, av1Aluno, av2Aluno, mediaFinal, situacaoAluno(mediaFinal)));
        }
        saida.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

    }

    private static String situacaoAluno(Double _mediaFinal) {

        String situacao = null;

        if (_mediaFinal < 4.0) {
            situacao = "Reprovado";
        } else if (_mediaFinal >= 4 && _mediaFinal < 7) {
            situacao = "Prova final";
        } else if (_mediaFinal >= 7) {
            situacao = "Aprovado";
        }

        return situacao;
    }
}
