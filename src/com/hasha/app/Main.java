package com.hasha.app;

import com.hasha.dao.AlunoDAO;
import com.hasha.entity.Aluno;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Main {

    /**
     * Atributo que representa a entrada de dados/informações do teclado no console.
     */
    private static Scanner entrada;

    /**
     * Atributo que representa a saída de dados/informações do sistema para o console.
     */
    private static PrintStream saida;

    /**
     * Atributo que define a quantidade fixa de vagas para alunos na turma.
     */
    private static final int VAGAS_NA_TURMA = 10;

    /**
     * O método principal chamado pela JVM.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Inicialização da variável 'entrada', com instanciação da classe Scanner. (Criando o primeiro objeto do sistema).
        // UTF-8 (codificação de caracteres).
        entrada = new Scanner(System.in, "850");

        saida = new PrintStream(System.out, true, "850");

        // Itens do menu, sendo constantes
        final int ITEM_REGISTRO_DE_ALUNO = 1;
        final int ITEM_CONSULTAR_BOLETIM = 2;
        final int ITEM_CONSULTAR_NOTAS = 3;
        final int ITEM_SAIR = 4;

        int itemEscolhidoMenu = 0;

        boolean continuar = true;

        do {

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
                    continuar = false;
                    break;
            }

            saida.println("\n-----------------------------------------------------------------\n");

        } while (continuar);

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

    private static void registrarAluno() throws InterruptedException {

        boolean repetir;

        String nome = null;
        do {
            saida.print("Nome do aluno: ");
            try {
                nome = entrada.nextLine();
                repetir = false;
            } catch (Exception e) {
                saida.println(">>> Entrada inválida. <<<");
                repetir = true;
            }
            Thread.sleep(2000L);
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

        Aluno aluno = new Aluno();

        aluno.setNome(nome);
        aluno.setNota1(_av1);
        aluno.setNota2(_av2);

        Integer codigo = null;

        try {
            codigo = new AlunoDAO().adicionar(aluno);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        saida.println("Aluno cadastrado com código: " + codigo);

    }

    /**
     * Este método tem a responsabilidade de solicitar ao usuário o código correspondente a algum aluno cadastrado
     * e retorna os dados do mesmo na tela.
     */
    private static void consultarBoletimAluno() {

        boolean repetir;
        int codigoAluno = 0;
        Aluno aluno = null;

        do {

            try {
                saida.print("Código: ");
                codigoAluno = Integer.parseInt(entrada.nextLine());
                aluno = new AlunoDAO().findAluno(codigoAluno);
                repetir = false;
            } catch (NumberFormatException e) {
                saida.println(">>> Entrada inválida. <<<");
                repetir = true;
            } catch (Exception e) {
                saida.println(e.getMessage());
                repetir = true;
            }

        } while (repetir);

        saida.println("\nNome: " + aluno.getNome());
        saida.println("AV1: " + aluno.getNota1());
        saida.println("AV2: " + aluno.getNota2());
        Double media = (aluno.getNota1() + aluno.getNota2()) / 2;
        saida.println("Média: " + String.format("%2.2f", media));
        saida.println("Situação: " + situacaoAluno(media));
    }

    private static void consultarNotasTurma() {

        ArrayList<Aluno> alunos;
        try {
            alunos = new AlunoDAO().listaAlunos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        saida.println("________________________________________________________________________");
        saida.println(String.format("|%-30s|%6s|%6s|%12s|%12s|", "Nome Aluno", "AV1", "AV2", "Média Final", "Situação"));
        saida.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
        for (Aluno aluno : alunos) {

            String nomeAluno = aluno.getNome();
            Double av1Aluno = aluno.getNota1();
            Double av2Aluno = aluno.getNota2();
            Double mediaFinal = (aluno.getNota1() + aluno.getNota2()) / 2;

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
