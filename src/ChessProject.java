import javax.swing.*;
import java.util.*;

public class ChessProject {
	
	static String board[][]= {
			
			//castle check 1
			/*
			{"r"," "," "," ","k"," "," ","r"},
			{"p"," "," "," "," "," ","Q","p"},
			{"P"," "," ","P","P"," "," ","P"},
			{"P"," "," "," "," "," "," ","P"},
			{"P"," "," "," "," "," "," ","P"},
			{"P"," "," "," "," "," "," ","P"},
			{"P"," "," "," "," ","Q"," ","P"},
			{"R"," "," "," ","K"," "," ","R"},*/
			
			/*
			//midgame puzzle 1
			{" ","k","r"," "," "," ","n","r"},
			{"p","p","p"," "," "," ","b","p"},
			{" "," ","n"," "," ","q"," "," "},
			{" ","Q"," ","p"," "," "," ","b"},
			{" "," "," "," ","p"," "," ","N"},
			{" "," ","N"," ","P"," ","B","P"},
			{"P","P","P","K"," ","P","P"," "},
			{"R"," "," "," "," ","B"," ","R"},
			*/
			/*
			//midgame puzzle 2
			{"r"," "," ","k"," "," "," ","r"},
			{"p","p","p"," "," ","p","p","p"},
			{" "," "," "," ","p"," "," "," "},
			{" "," "," ","p"," ","b"," "," "},
			{" "," ","P","P","n","B"," "," "},
			{"q","n","P"," ","P","N"," "," "},
			{"P"," ","R"," "," ","P","P","P"},
			{"K"," "," "," ","Q","B"," ","R"},
			*/
			/* // endgame 
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
	static int globalDepth = 4;
	static int maxetIsWhite = 1;
	static int isWhitesTurn = 1;
	static int whiteCastlePossible = 1;
	static int blackCastlePossible = 1;
	static int whiteKing=60, blackKing=4;
	static String moveHistory ="";
	static JFrame frame = new JFrame("Engine Maxet");
	public static void main(String[] args) {
		//System.out.print("Is Engine Maxet White? (1 - yes, 0 - no): ");
		Scanner sc = new Scanner(System.in);
		//maxetIsWhite = sc.nextInt();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui = new UserInterface();
		frame.add(ui);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.repaint();
		
		//if(maxetIsWhite == 1) {	
			
		//	callAlphaBeta();
		for(int i = 0; i< 8; i++) {
			System.out.println(Arrays.toString(ChessProject.board[i]));
		}
		//	frame.repaint();
			
		//}
		sc.close();

	}

	public static void callAlphaBeta() {
		long startTime = System.currentTimeMillis();
		String move = alphaBeta(globalDepth, 100000000, -100000000, "", 0 );
		Moves.makeMove(move, 0);
		
		System.out.println(moveHistory);
		long endTime = System.currentTimeMillis();
		System.out.println("move time: "+(endTime - startTime));
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
