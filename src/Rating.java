
public class Rating {

	public static int rating(int list, int depth) {
		int counter = 0,material = rateMaterial();
		counter+=material;
		counter+=rateAttack();
		counter+=rateMovability(list, depth, material);
		counter+=ratePositional();
		
		Moves.flipBoard();
		material = rateMaterial();
		counter-=material;
		counter-=rateAttack();
		counter-=rateMovability(list, depth, material);
		counter-=ratePositional();
		Moves.flipBoard();
		
		return -(counter+depth*50);
	}
	
	public static int rateMaterial() {
		int counter = 0, bishopCounter =0;
		
		for(int i =0; i<64; i++) {
			switch(ChessProject.board[i/8][i%8]) {
			case "P": counter+=100;
			break;
			case "R": counter+=500;
			break;
			case "Q": counter+=900;
			break;
			case "B": bishopCounter+=1;
			break;
			case "N": counter+=300;
			break;
			}
		}
		if( bishopCounter>=2) {
			counter+=300*bishopCounter;
		}
		else {
			if(bishopCounter==1) {
			counter+=250;	
			}
		}
		
		
		
		return counter;
	}
	
	public static int rateAttack() {
		int counter = 0;
		int tempWhiteKing = ChessProject.whiteKing;
		for(int i =0; i<64; i++) {
			switch(ChessProject.board[i/8][i%8]) {
			case "P": ChessProject.whiteKing = i; if(!Moves.kingSafe()) {counter -=64;}
			break;
			case "R": ChessProject.whiteKing = i; if(!Moves.kingSafe()) {counter -=500;}
			break;
			case "Q": ChessProject.whiteKing = i; if(!Moves.kingSafe()) {counter -=300;}
			break;
			case "B": ChessProject.whiteKing = i; if(!Moves.kingSafe()) {counter -=300;}
			break;
			case "N": ChessProject.whiteKing = i; if(!Moves.kingSafe()) {counter -=900;}
			break;
			}
		}
		ChessProject.whiteKing = tempWhiteKing;
		if(!Moves.kingSafe()) {
			counter =200;
		}
		
		return counter/2;
	}
	
	public static int rateMovability(int listLength, int depth, int material) {
		int counter=0;
		counter +=listLength; // 5 points per valid move
		if(listLength ==0) {//checkmate or stalemate
			if(!Moves.kingSafe()) {//checkmate
				counter+=-200000*depth;
			}
			else {//stalemate
				counter+=-150000*depth;
			}
			
		}
		return counter;
		
	}
	
	public static int ratePositional() {
		return 0;
	}
	

}
