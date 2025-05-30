import Model.Board;
import Model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
            final var positions = Stream.of(args)
                    .collect(Collectors.toMap(
                            k -> k.split(";")[0],
                            v -> v.split(";")[1]
                    ));

            var option = -1;
            while (true){
                System.out.println("Selecione uma das opções a seguir");
                System.out.println("1 - Iniciar um novo Jogo");
                System.out.println("2 - Colocar um novo número");
                System.out.println("3 - Remover um número");
                System.out.println("4 - Visualizar jogo atual");
                System.out.println("5 - Verificar status do jogo");
                System.out.println("6 - limpar jogo");
                System.out.println("7 - Finalizar jogo");
                System.out.println("8 - Sair");

                option = scanner.nextInt();

                switch (option){
                    case 1 -> startGame(positions);
                    case 2 -> inputNumber();
                    case 3 -> removeNumber();
                    case 4 -> showCurrentGame();
                    case 5 -> showGameStatus();
                    case 6 -> clearGame();
                    case 7 -> finishGame();
                    case 8 -> System.exit(0);
                    default -> System.out.println("Opcao invalida!");
                }
            }
    }

    private static void startGame(Map<String, String> positions) {
        if(nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }
        List<List<Space>> spaces = new ArrayList<>();
            for(int i = 0; i < BOARD_LIMIT; i++){
                spaces.add(new ArrayList<>());
                for(int j = 0; j < BOARD_LIMIT; j++){
                    var positionConfig = positions.get("%s, %s".formatted(1, j));
                    var expected = Integer.parseInt(positionConfig.split(",")[0]);
                    var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                    var currentSpace = new Space(expected, fixed);
                    spaces.get(i).add(currentSpace);
                }
            }
            board = new Board(spaces);
            System.out.println("O jogo está pronto para começar!");

    }

    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("O jogo não foi inciado");
            return;
        }
        System.out.println("Informe a coluna que deseja inserir o numero!");
        var col = runUntilGetValidNumber(0, 0);
        System.out.println("Informe a linha que deseja inserir o numero!");
        var row = runUntilGetValidNumber(0, 0);
        System.out.println("Informe o numero a inserir:");
        var value = runUntilGetValidNumber(1, 9);

        if (!board.changeValue(col, row, value)){
            System.out.println("A posição selecionada possui um valor fixo");
        }
    }

    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("O jogo não foi inciado");
            return;
        }
        System.out.println("Informe a coluna que deseja remover o numero!");
        var col = runUntilGetValidNumber(0, 0);
        System.out.println("Informe a linha que deseja remover o numero!");
        var row = runUntilGetValidNumber(0, 0);
        System.out.println("Informe o numero a remover:");
        var value = runUntilGetValidNumber(1, 9);

        if (!board.clearValue(col, row)){
            System.out.println("A posição selecionada possui um valor fixo");
        }
    }

    private static void showCurrentGame() {
    }

    private static void showGameStatus() {
    }

    private static void clearGame() {
    }

    private static void finishGame() {
    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = scanner.nextInt();
        while (current < min || current > max){
            System.out.println("Informe um numero entre %i e %i\n");
            current = scanner.nextInt();
        }
        return current;
    }
}