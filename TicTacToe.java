import java.util.*;
public class TicTacToe {
static char[] board = new char[9];
static Scanner scanner = new Scanner(System.in);
static final char EMPTY = ' ';
static final char X = 'X';
static final char O = 'O';
public static void main(String[] args) {
Arrays.fill(board, EMPTY);
System.out.println("TIC-TAC-TOE (Console)");
System.out.println("1) Two players (local)");
System.out.println("2) Play vs Computer (unbeatable)");
System.out.print("Choose mode (1 or 2): ");
int mode = readIntInRange(1, 2);
if (mode == 1) twoPlayerGame();
else singlePlayerGame();
scanner.close();
}
static void twoPlayerGame() {
char current = X;
while (true) {
printBoard();
System.out.println("Player " + current + "'s turn.");
playerMove(current);
if (isWin(board, current)) {
printBoard();
System.out.println("Player " + current + " wins!");
break;
}
if (isBoardFull()) {
printBoard();
System.out.println("It's a draw!");
break;
}
current = (current == X) ? O : X;
}
}
static void singlePlayerGame() {
System.out.println("You will play as X. Computer is O (unbeatable).");
System.out.print("Do you want to go first? (y/n): ");
boolean humanFirst = readYesNo();
char human = X;
char ai = O;
char current = humanFirst ? human : ai;
while (true) {
printBoard();
if (current == human) {
System.out.println("Your turn.");
playerMove(human);
if (isWin(board, human)) {
printBoard();
System.out.println("You win! 🎉");
break;
}
} else {
System.out.println("Computer is thinking...");
int move = findBestMove(board, ai, human);
board[move] = ai;
System.out.println("Computer played at position " + (move + 1));
if (isWin(board, ai)) {
printBoard();
System.out.println("Computer wins.");
break;
}
}
if (isBoardFull()) {
printBoard();
System.out.println("It's a draw!");
break;
}
current = (current == human) ? ai : human;
}
}
static void playerMove(char player) {
while (true) {
System.out.print("Enter position (1-9): ");
int pos = readIntInRange(1, 9) - 1;
if (board[pos] == EMPTY) {
board[pos] = player;
break;
} else {
System.out.println("That cell is already taken. Choose another.");
}
}
}
static void printBoard() {
System.out.println();
System.out.println("Board:");
for (int r = 0; r < 3; r++) {
System.out.print(" ");
for (int c = 0; c < 3; c++) {
int i = r * 3 + c;
char ch = (board[i] == EMPTY) ? (char)('1' + i) : board[i];
System.out.print(" " + ch + " ");
if (c < 2) System.out.print("|");
}
System.out.println();
if (r < 2) System.out.println(" ---+---+---");
}
System.out.println();
}
static boolean isBoardFull() {
for (char c : board) if (c == EMPTY) return false;
return true;
}
static boolean isWin(char[] b, char p) {
int[][] wins = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8}, 
            {0,4,8},{2,4,6}          
};
for (int[] w : wins) {
if (b[w[0]] == p && b[w[1]] == p && b[w[2]] == p) return true;
}
return false;
}
static int findBestMove(char[] b, char ai, char human) {
int bestScore = Integer.MIN_VALUE;
int bestMove = -1;
for (int i = 0; i < 9; i++) {
if (b[i] == EMPTY) {
b[i] = ai;
int score = minimax(b, 0, false, ai, human);
b[i] = EMPTY;
if (score > bestScore) {
bestScore = score;
bestMove = i;
}
}
}
if (bestMove == -1) {
for (int i = 0; i < 9; i++) if (b[i] == EMPTY) return i;
}
return bestMove;
}
static int minimax(char[] b, int depth, boolean isMax, char ai, char human) {
if (isWin(b, ai)) return 10 - depth;
if (isWin(b, human)) return depth - 10;
if (isBoardFull()) return 0;
if (isMax) {
int maxEval = Integer.MIN_VALUE;
for (int i = 0; i < 9; i++) {
if (b[i] == EMPTY) {
b[i] = ai;
int eval = minimax(b, depth + 1, false, ai, human);
b[i] = EMPTY;
maxEval = Math.max(maxEval, eval);
}
}
return maxEval;
} else {
int minEval = Integer.MAX_VALUE;
for (int i = 0; i < 9; i++) {
if (b[i] == EMPTY) {
b[i] = human;
int eval = minimax(b, depth + 1, true, ai, human);
b[i] = EMPTY;
minEval = Math.min(minEval, eval);
}
}
return minEval;
}
}
static int readIntInRange(int lo, int hi) {
while (true) {
String s = scanner.nextLine().trim();
try {
int v = Integer.parseInt(s);
if (v >= lo && v <= hi) return v;
} 
catch (NumberFormatException ignored) {}
System.out.print("Please enter a number between " + lo + " and " + hi + ": ");
}
}
static boolean readYesNo() {
while (true) {
String s = scanner.nextLine().trim().toLowerCase();
if (s.equals("y") || s.equals("yes")) return true;
if (s.equals("n") || s.equals("no")) return false;
System.out.print("Please answer y/n: ");
}
}
}