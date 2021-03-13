package tictactoe;
import java.util.Scanner;

public class Main {

    private static boolean isFinished = false;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[][] characters = new char[3][3];
        String boardState = "         ";
        int strIndex = 0;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                characters[i][j] = boardState.charAt(strIndex++);
            }
        }

        printTicTacToeBoard(characters);
        String s = null;
        int characterToInsert = 0;
        while(!isFinished){
            if(characterToInsert%2==0){
                insertValueInBoard(scan, characters,boardState,'X');
            }
            else{
                insertValueInBoard(scan, characters,boardState,'O');
            }
            characterToInsert+=1;
            s = decideWinner(characters);
            printTicTacToeBoard(characters);
        }
        System.out.println(s);
    }

    private static void insertValueInBoard(Scanner scan, char[][] characters, String boardState, char charToInsert) {
        boolean enteredValidNumbers = false;
        String coordinateInput;
        int x = 0,y = 0;
        while(!enteredValidNumbers) {
            try {
                System.out.print("Enter the coordinates:");
                coordinateInput = scan.nextLine();
                x = Integer.parseInt(coordinateInput.split(" ")[0]);
                y =  Integer.parseInt(coordinateInput.split(" ")[1]);
                if(x>3 || y>3){
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                if(characters[x-1][y-1]=='X' || characters[x-1][y-1]=='O'){
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                enteredValidNumbers = true;
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
            }
        }

        if(characters[x-1][y-1]!='X' || characters[x-1][y-1]!='O'){
            characters[x - 1][y - 1] = charToInsert;
        }
    }

    private static String decideWinner(char[][] characters) {

        String boardState = createBoardState(characters);
        long xCount = boardState.chars().filter(c -> c == 'X').count();
        long oCount = boardState.chars().filter(c->c=='O').count();
        if(Math.abs(xCount-oCount)>1){
            return "Impossible";
        }
        boolean xWins = count3ConsecutiveMarking(characters, 'X');
        boolean oWins = count3ConsecutiveMarking(characters, 'O');
        if(xWins && oWins){
            return "Impossible";
        }
        else if(xWins){
            isFinished = true;
            return "X wins";
        }
        else if(oWins){
            isFinished = true;
            return "O wins";
        }
        else if(boardState.chars().filter(c->c==' ').count()==0){
            isFinished = true;
            return "Draw";
        }
        else{
            return "Game not finished";
        }
    }

    private static String createBoardState(char[][] characters) {
        StringBuilder boardState = new StringBuilder();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                boardState.append(characters[i][j]);
            }
        }
        return boardState.toString();
    }

    private static boolean count3ConsecutiveMarking(char[][] characters, char chr) {
        return countForX(characters, 0, chr) ||
                countForX(characters, 1, chr) ||
                countForX(characters, 2, chr) ||
                countForY(characters, 0, chr) ||
                countForY(characters, 1, chr) ||
                countForY(characters, 2, chr) ||
                countForDiagonals(characters, chr);
    }

    private static boolean countForDiagonals(char[][] characters, char chr) {
        return (characters[0][0] == chr && characters[1][1] == chr && characters[2][2] == chr) ||
                (characters[0][2] == chr && characters[1][1] == chr && characters[2][0] == chr);
    }

    private static boolean countForY(char[][] characters, int index, char chr) {
        for(int i=0;i<3;i++){
            if(characters[i][index]!=chr){
                return false;
            }
        }
        return true;
    }

    private static boolean countForX(char[][] characters, int index, char chr) {
        for(int i=0;i<3;i++){
            if(characters[index][i]!=chr){
                return false;
            }
        }
        return true;
    }

    private static void printTicTacToeBoard(char[][] characters) {
        System.out.println("---------");
        for(int i=0;i<3;i++){
            System.out.print("| ");
            for(int j=0;j<3;j++){
                System.out.print(characters[i][j]+" ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

}
