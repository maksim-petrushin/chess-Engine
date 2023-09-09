
public class Rating {

	public static int rating(int list,int listOp, int depth, int side) {
		int counter = 0,material;
		
		//rater's side
		material = rateMaterial(side);
		counter+=material;
		counter+=rateAttack(side);
		counter+=rateMovability(list, depth, material,side);
		counter+=ratePositional(side);
		
		//opponent's side
		material = rateMaterial(1-side);
		counter-=material;
		counter-=rateAttack(1-side);
		counter-=rateMovability(listOp, depth, material,1-side);
		counter-=ratePositional(1-side);
		
		return -(counter+depth*50);
	}
	
	public static int rateMaterial(int side) {
		int counter = 0, bishopCounter =0;
			
		if(side == 1) {
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
		}
		else {
			for(int i =0; i<64; i++) {
				switch(ChessProject.board[i/8][i%8]) {
				case "p": counter+=100;
				break;
				case "r": counter+=500;
				break;
				case "q": counter+=900;
				break;
				case "b": bishopCounter+=1;
				break;
				case "n": counter+=300;
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
		}
		
		
		
		
		return counter;
	}
	
	public static int rateAttack(int side) {
		int counter = 0;
		
		if(side == 1) {
			int tempWhiteKing = ChessProject.whiteKing;
			for(int i =0; i<64; i++) {
				switch(ChessProject.board[i/8][i%8]) {
				case "P": ChessProject.whiteKing = i; if(!Moves.kingSafe(side)) {counter -=64;}
				break;
				case "R": ChessProject.whiteKing = i; if(!Moves.kingSafe(side)) {counter -=500;}
				break;
				case "Q": ChessProject.whiteKing = i; if(!Moves.kingSafe(side)) {counter -=300;}
				break;
				case "B": ChessProject.whiteKing = i; if(!Moves.kingSafe(side)) {counter -=300;}
				break;
				case "N": ChessProject.whiteKing = i; if(!Moves.kingSafe(side)) {counter -=900;}
				break;
				}
			}
			ChessProject.whiteKing = tempWhiteKing;
			if(!Moves.kingSafe(side)) {
				counter =200;
			}
			
		}
		else {
			int tempBlackKing = ChessProject.blackKing;
			for(int i =0; i<64; i++) {
				switch(ChessProject.board[i/8][i%8]) {
				case "p": ChessProject.blackKing = i; if(!Moves.kingSafe(side)) {counter -=64;}
				break;
				case "r": ChessProject.blackKing = i; if(!Moves.kingSafe(side)) {counter -=500;}
				break;
				case "q": ChessProject.blackKing = i; if(!Moves.kingSafe(side)) {counter -=300;}
				break;
				case "b": ChessProject.blackKing = i; if(!Moves.kingSafe(side)) {counter -=300;}
				break;
				case "n": ChessProject.blackKing = i; if(!Moves.kingSafe(side)) {counter -=900;}
				break;
				}
			}
			ChessProject.blackKing = tempBlackKing;
			if(!Moves.kingSafe(side)) {
				counter =200;
			}
			
		}
		
		return counter/2;
	}
	
	/*
	 * REMOVE THE CURRENT Castling implementation and instead do the move history implementation
	 */
	
	public static int rateMovability(int listLength, int depth, int material, int side) {
		int counter=0;
		counter +=listLength; // 5 points per valid move
		if(listLength ==0) {//checkmate or stalemate
			if(!Moves.kingSafe(side)){//checkmate
				counter+=-200000*depth;
			}
			else {//stalemate
				counter+=-150000*depth;
			}
			
		}
		return counter;
		
	}
	
	public static int ratePositional(int side) {
		return 0;
	}
	

}
