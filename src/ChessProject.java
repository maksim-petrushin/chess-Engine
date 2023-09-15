import javax.swing.*;
import java.util.*;

public class ChessProject {
	
	static String board[][]= {
			
			//castle check 1
			/*
			{" "," "," "," ","k"," "," ","r"},
			{"p"," ","P"," "," "," "," ","p"},
			{"P"," "," "," ","P","P"," ","P"},
			{"P"," "," "," "," "," "," ","P"},
			{"P"," "," "," "," "," "," ","P"},
			{"P"," "," "," "," "," "," ","P"},
			{"P"," "," "," "," "," "," ","P"},
			{"R"," "," "," ","K"," "," ","R"},
			
			//midgame puzzle 1
			{" ","k","r"," "," "," ","n","r"},
			{"p","p","p"," "," "," ","b","p"},
			{" "," ","n"," "," ","q"," "," "},
			{" ","Q"," ","p"," "," "," ","b"},
			{" "," "," "," ","p"," "," ","N"},
			{" "," ","N"," ","P"," ","B","P"},
			{"P","P","P","K"," ","P","P"," "},
			{"R"," "," "," "," ","B"," ","R"},

			//midgame puzzle 2
			{"r"," "," ","k"," "," "," ","r"},
			{"p","p","p"," "," ","p","p","p"},
			{" "," "," "," ","p"," "," "," "},
			{" "," "," ","p"," ","b"," "," "},
			{" "," ","P","P","n","B"," "," "},
			{"q","n","P"," ","P","N"," "," "},
			{"P"," ","R"," "," ","P","P","P"},
			{"K"," "," "," ","Q","B"," ","R"},
			/*
			 // endgame 
			{" "," "," ","b"," "," "," "," "},
			{" "," "," "," "," "," ","q"," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," ","N"},
			{" "," "," "," "," ","k"," "," "},
			{" "," "," "," "," "," "," ","K"},
			{" "," "," "," "," "," "," "," "},
			
			*/
				//starting board	
			{"r","n","b","q","k","b","n","r"},
			{"p","p","p","p","p","p","p","p"},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{"P","P","P","P","P","P","P","P"},
			{"R","N","B","Q","K","B","N","R"},
			
	};
	static int globalDepth = 1;
	static int maxetIsWhite = 1;
	static int isWhitesTurn = 1;
	static int whiteCastlePossible = 1;
	static int blackCastlePossible = 1;
	static int debuggingPositionCount = 0;
	static int whiteKing, blackKing;
	static String moveHistory ="";
	static JFrame frame = new JFrame("Engine Maxet");
	public static void main(String[] args) {
		//System.out.print("Is Engine Maxet White? (1 - yes, 0 - no): ");
		for(int i=0; i<64; i++) {
			switch(ChessProject.board[i/8][i%8]) {
				case "K":
					whiteKing = i;
					break;
				case "k":
					blackKing = i;
					break;
				default:
					break;
			}

		}
		Scanner sc = new Scanner(System.in);
		String move = "1";
		String possibleMoves = "";
		String myMove = "";
		
		//print board
		for(int i = 0; i< 8; i++) {
			System.out.println(Arrays.toString(ChessProject.board[i]));
		}
		
//		//maxetIsWhite = sc.nextInt();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		UserInterface ui = new UserInterface();
//		frame.add(ui);
//		frame.setSize(600, 600);
//		frame.setVisible(true);
//		frame.repaint();
		
		while(move.charAt(0) != '0') {
			System.out.println("Enter your move in UCI");
			move = sc.nextLine();
			possibleMoves = Moves.possibleMoves(1);
			myMove = UCI.algebraToMove(move, possibleMoves, 1);
			
			Moves.makeMove(myMove, 1);
			
			//print board
			for(int i = 0; i< 8; i++) {
				System.out.println(Arrays.toString(ChessProject.board[i]));
			}
			
	
		}
		
		//if(maxetIsWhite == 1) {	
			
		//	callAlphaBeta();

		//	frame.repaint();
			
		//}
		sc.close();

	}

	public static void callAlphaBeta() {
		long startTime = System.currentTimeMillis();
		debuggingPositionCount = 0;
		String move = alphaBeta(globalDepth, 100000000, -100000000, "", 0 );
		Moves.makeMove(move, 0);
		
		//System.out.println(moveHistory);
		long endTime = System.currentTimeMillis();
		
		System.out.println("move time: "+(endTime - startTime));
		System.out.println(debuggingPositionCount);
		System.out.println("");
		
		
	}
	public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
		String list = Moves.possibleMoves(player);
		String listOp = Moves.possibleMoves(1-player);
		if(depth == 0 || list.length()==0) {return move+(Rating.rating(list.length(),listOp.length(), depth, player)*(player*2-1));}
		player = 1-player;
		int i;
		for( i=0; i<list.length();i+=5) {
			
			String currentMove = list.substring(i,i+5);
			
			Moves.makeMove(currentMove, 1-player);
			String returnString = alphaBeta(depth-1, beta, alpha, list.substring(i,i+5), player);
			int value = Integer.valueOf(returnString.substring(5));
			Moves.undoMove(currentMove, 1-player);
			if(player == 0 ) {
				if(value <= beta) {beta = value; if(depth == globalDepth) {move = returnString.substring(0,5);}}
			} else {
				if(value > alpha) {alpha = value; if(depth == globalDepth) {move = returnString.substring(0,5);}}
			}
			if(alpha >=beta) {
				if(player == 0) {return move+beta;} else {return move+alpha;}
			}
		}
		
		if(player == 0) {return move+beta;} else { return move+alpha;}
	}



}
