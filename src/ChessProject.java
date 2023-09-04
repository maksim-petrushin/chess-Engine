import javax.swing.*;
import java.util.*;

public class ChessProject {
	
	static String chessBoard[][]= {
			
			/*
			//midgame puzzle 1
			{" ","a","r"," "," "," ","k","r"},
			{"p","p","p"," "," "," ","b","p"},
			{" "," ","k"," "," ","q"," "," "},
			{" ","Q"," ","p"," "," "," ","b"},
			{" "," "," "," ","p"," "," ","K"},
			{" "," ","K"," ","P"," ","B","P"},
			{"P","P","P","A"," ","P","P"," "},
			{"R"," "," "," "," ","B"," ","R"},
			*/
			/*
			//midgame puzzle 2
			{"r"," "," ","a"," "," "," ","r"},
			{"p","p","p"," "," ","p","p","p"},
			{" "," "," "," ","p"," "," "," "},
			{" "," "," ","p"," ","b"," "," "},
			{" "," ","P","P","k","B"," "," "},
			{"q","k","P"," ","P","K"," "," "},
			{"P"," ","R"," "," ","P","P","P"},
			{"A"," "," "," ","Q","B"," ","R"},
			*/
			/* // endgame 
			{" "," "," ","b"," "," "," "," "},
			{" "," "," "," "," "," ","q"," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," ","K"},
			{" "," "," "," "," ","a"," "," "},
			{" "," "," "," "," "," "," ","A"},
			{" "," "," "," "," "," "," "," "},
			*/
			
				//starting board	
			{"r","k","b","q","a","b","k","r"},
			{"p","p","p","p","p","p","p","p"},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{"P","P","P","P","P","P","P","P"},
			{"R","K","B","Q","A","B","K","R"},
	};
	static int globalDepth = 5;
	static int maxetIsWhite = 1; 
	static int kingPositionC=0, kingPositionL=0;
	static JFrame f = new JFrame("Engine Maxet");
	public static void main(String[] args) {
		//System.out.print("Is Engine Maxet White? (1 - yes, 0 - no): ");
		Scanner sc = new Scanner(System.in);
		//maxetIsWhite = sc.nextInt();
		
		kingPositionC = 60;
		
		kingPositionL = 4;

		
		
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui = new UserInterface();
		f.add(ui);
		f.setSize(500, 500);
		f.setVisible(true);
		f.repaint();
		//if(maxetIsWhite == 1) {	
			
		//	callAlphaBeta();
			
		//	f.repaint();
			
		//}


		sc.close();

	}
	public static String sortMoves(String list) {
		int [] score = new int[list.length()/5];
		
		for(int i=0; i<list.length(); i+=5) {
			makeMove(list.substring(i,i+5));
			score[i/5] = -Rating.rating(-1, 0);
			undoMove(list.substring(i,i+5));
		}
		
		String newListA="", newListB=list;
		for(int i=0; i<Math.min(6, list.length()/5); i++) {
			int max = -100000000, maxLocation =0;
			
			for(int j=0; j<list.length()/5; j++) {
				if(score[j]>max) {max = score[j]; maxLocation = j;}
			}
			score[maxLocation]=-100000000;
			newListA += list.substring(maxLocation*5, maxLocation*5+5);
			newListB = newListB.replace(list.substring(maxLocation*5, maxLocation*5+5), "");
			
		}	
		return newListA+newListB;
	}
	public static void callAlphaBeta() {
		flipBoard();
		long startTime = System.currentTimeMillis();
		makeMove(alphaBeta(globalDepth, 100000000, -100000000, "", 0 ));
		long endTime = System.currentTimeMillis();
		flipBoard();
		System.out.println("move time: "+(endTime - startTime));
	}
	public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
		String list = possibleMoves();
		if(depth == 0 || list.length()==0) {return move+(Rating.rating(list.length(), depth)*(player*2-1));}
		list = sortMoves(list);
		
		player = 1-player;
		int i;
		for( i=0; i<list.length();i+=5) {
			makeMove(list.substring(i,i+5));
			flipBoard();
			String returnString = alphaBeta(depth-1, beta, alpha, list.substring(i,i+5), player);
			int value = Integer.valueOf(returnString.substring(5));
			flipBoard();
			undoMove(list.substring(i,i+5));
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

	public static void flipBoard() {
		String temp;
		for(int i=0; i<32; i++) {
			int r = i/8, c=i%8;
			if(Character.isUpperCase(chessBoard[r][c].charAt(0))){
				temp = chessBoard[r][c].toLowerCase();
			}
			else {
				temp = chessBoard[r][c].toUpperCase();
			}
			if(Character.isUpperCase(chessBoard[7-r][7-c].charAt(0))){
				chessBoard[r][c] = chessBoard[7-r][7-c].toLowerCase();
			}
			else {
				chessBoard[r][c] = chessBoard[7-r][7-c].toUpperCase();
			}
			chessBoard[7-r][7-c] = temp;
		}
		int kingTemp = kingPositionC;
		kingPositionC = 63-kingPositionL;
		kingPositionL = 63-kingTemp;
	}
	
	public static void makeMove(String move) {
		if(move.charAt(4)!= 'P') {
			chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] 
			= chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
			chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
			if("A".equals(chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
				kingPositionC = 8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
			}
		}else {//pawn promotion
			chessBoard[1][Character.getNumericValue(move.charAt(0))]=" ";
			chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
		}
	}
	public static void undoMove(String move) {
		if(move.charAt(4)!= 'P') {
			chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]
			= chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
			chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = String.valueOf(move.charAt(4));
			if("A".equals(chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
				kingPositionC = 8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
			}
		}else {//pawn promotion
			chessBoard[1][Character.getNumericValue(move.charAt(0))]="P";
			chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));
		
			
		}
	
	}
	
	public static String possibleMoves() {
		String list="";
		for(int i=0; i<64; i++) {
			switch(chessBoard[i/8][i%8]) {
			//case "P":
			//	list+=possibleP(i);
			//	break; 
			case "P":
				list+=possibleP(i);
				break;
			case "R":
				list+=possibleR(i);
				break; 
			case "K":
				list+=possibleK(i);
				break; 
			case "B":
				list+=possibleB(i);
				break; 
			case "A":
				list+=possibleA(i);
				break;
			case "Q":
				list+=possibleQ(i);
				break; 
			default:
				break;
			}
		}
		return list;//x1, y1, x2, y2, captured piece
	}
	public static String possibleP(int i) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		
		for(int j=-1; j<=1; j+=2) {
			//capture
				try{
					if(i>=16 && Character.isLowerCase(chessBoard[r-1][c+j].charAt(0))){
						oldPiece = chessBoard[r-1][c+j];
						chessBoard[r][c] = " ";
						chessBoard[r-1][c+j] = "P";
						if(kingSafe()) {
							list = list+r+c+(r-1)+(c+j)+oldPiece;
						}
						chessBoard[r][c]="P";
						chessBoard[r-1][c+j]=oldPiece;
					}
				}catch(Exception e) {}
				try{//promotion
					if(i<16 && Character.isLowerCase(chessBoard[r-1][c+j].charAt(0))){
						String[] temp = {"Q","K", "R", "B"};
						for(int k=0; k<4;k++) {
							oldPiece = chessBoard[r-1][c+j];
							chessBoard[r][c] = " ";
							chessBoard[r-1][c+j] = temp[k];
							if(kingSafe()) {
								list = list+c+(c+j)+oldPiece+temp[k]+"P";
							}
							chessBoard[r][c]="P";
							chessBoard[r-1][c+j]=oldPiece;
						}
						
					}
				}catch(Exception e) {}
		}
		try{//move one up
			if(i>=16 && " ".equals(chessBoard[r-1][c])){
					chessBoard[r][c] = " ";
					chessBoard[r-1][c] = "P";
					if(kingSafe()) {
						list = list+r+c+(r-1)+(c)+" ";
					}
					chessBoard[r][c]="P";
					chessBoard[r-1][c]=" ";
			}
		}catch(Exception e) {}
		try{//promotion and no capture
			if(i<16 && " ".equals(chessBoard[r-1][c])){
				String[] temp = {"Q","K", "R", "B"};
				for(int k=0; k<4;k++) {
					chessBoard[r][c] = " ";
					chessBoard[r-1][c] = temp[k];
					if(kingSafe()) {
						list = list+c+c+" "+temp[k]+"P";
					}
					chessBoard[r][c]="P";
					chessBoard[r-1][c]=" ";
				}
			}
		}catch(Exception e) {}
		try{//move two up
			if(i>=48 && " ".equals(chessBoard[r-1][c]) && " ".equals(chessBoard[r-2][c])){
					chessBoard[r][c] = " ";
					chessBoard[r-2][c] = "P";
					if(kingSafe()) {
						list = list+r+c+(r-2)+(c)+" ";
					}
					chessBoard[r][c]="P";
					chessBoard[r-2][c]=" ";
			}
		}catch(Exception e) {}
		return list;
	}
	public static String possibleA(int i) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		for(int j=0; j<9; j++) {
			if(j!=4) {
				try{
					if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])) {
					oldPiece = chessBoard[r-1+j/3][c-1+j%3];
					chessBoard[r][c] = " ";
					chessBoard[r-1+j/3][c-1+j%3]="A";
					int kingTemp=kingPositionC;
					kingPositionC = i+(j/3)*8+j%3-9;
					if(kingSafe()) {
						list = list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
					}
					chessBoard[r][c] = "A";
					chessBoard[r-1+j/3][c-1+j%3]=oldPiece;
					kingPositionC = kingTemp;
				}
				}catch(Exception e) {
					
				}
			}
		}
		return list;
	}
	public static String possibleQ(int i) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		int temp=1;
		for(int j=-1; j<=1; j++) {
			
			for(int k=-1;k<=1;k++) {
				try {
					while(" ".equals(chessBoard[r+temp*j][c+temp*k])) {
						chessBoard[r][c] = " ";
						chessBoard[r+temp*j][c+temp*k] = "Q";
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
						}
						chessBoard[r][c]="Q";
						chessBoard[r+temp*j][c+temp*k]=" ";
						temp++;
					}
					if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
						
							oldPiece = chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c] = " ";
							chessBoard[r+temp*j][c+temp*k] = "Q";
							if(kingSafe()) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							chessBoard[r][c]="Q";
							chessBoard[r+temp*j][c+temp*k]=oldPiece;
							temp++;
					}
					
				} catch(Exception e) {}
				temp =1;
			}
		
		}
		return list;
	}
	public static String possibleK(int i) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		for(int j=-1; j<=1; j+=2) {		
			for(int k=-1;k<=1;k+=2) {
				try {
					if(Character.isLowerCase(chessBoard[r+j][c+2*k].charAt(0)) || " ".equals(chessBoard[r+j][c+2*k])) {
						oldPiece = chessBoard[r+j][c+2*k];
						chessBoard[r][c] = " ";
						chessBoard[r+j][c+2*k] = "K";
						if(kingSafe()) {
							list = list+r+c+(r+j)+(c+2*k)+oldPiece;
						}
						chessBoard[r][c]="K";
						chessBoard[r+j][c+2*k]=oldPiece;
					}
					

				} catch(Exception e) {}
				try {
					if(Character.isLowerCase(chessBoard[r+2*j][c+k].charAt(0)) || " ".equals(chessBoard[r+2*j][c+k])) {
						oldPiece = chessBoard[r+2*j][c+k];
						chessBoard[r][c] = " ";
						chessBoard[r+2*j][c+k] = "K";
						if(kingSafe()) {
							
							list = list+r+c+(r+2*j)+(c+k)+oldPiece;
						}
						chessBoard[r][c]="K";
						chessBoard[r+2*j][c+k]=oldPiece;
					}
				} catch(Exception e) {}
			}
		
		}
		return list;
	}
	public static String possibleR(int i) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		int temp=1;
		for(int j=-1; j<=1; j++) {
			
			for(int k=-1; k<=1; k++) {
				if( k!= 0 && j!=0) {
					continue;
				}
				try {
					while(" ".equals(chessBoard[r+temp*j][c+temp*k])) {
						chessBoard[r][c] = " ";
						chessBoard[r+temp*j][c+temp*k] = "R";
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
						}
						chessBoard[r][c]="R";
						chessBoard[r+temp*j][c+temp*k]=" ";
						temp++;
					}
					if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
						
							oldPiece = chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c] = " ";
							chessBoard[r+temp*j][c+temp*k] = "R";
							if(kingSafe()) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							chessBoard[r][c]="R";
							chessBoard[r+temp*j][c+temp*k]=oldPiece;
							temp++;
					}
					
				} catch(Exception e) {}
				temp =1;
			}
		}
		return list;
	}
	public static String possibleB(int i) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		int temp=1;
		for(int j=-1; j<=1; j=j+2) {
			
			for(int k=-1;k<=1;k=k+2) {
				try {
					while(" ".equals(chessBoard[r+temp*j][c+temp*k])) {
						chessBoard[r][c] = " ";
						chessBoard[r+temp*j][c+temp*k] = "B";
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
						}
						chessBoard[r][c]="B";
						chessBoard[r+temp*j][c+temp*k]=" ";
						temp++;
					}
					if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
						
							oldPiece = chessBoard[r+temp*j][c+temp*k];
							chessBoard[r][c] = " ";
							chessBoard[r+temp*j][c+temp*k] = "B";
							if(kingSafe()) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							chessBoard[r][c]="B";
							chessBoard[r+temp*j][c+temp*k]=oldPiece;
							temp++;
					}
					
				} catch(Exception e) {}
				temp =1;
			}
		
		}
		return list;
	}
	public static boolean kingSafe() {
		//bishop + queen
		
		for(int j=-1; j<=1; j+=2) {
			for(int k=-1;k<=1;k+=2) {
				int temp =1;
				try {
					while(" ".equals(chessBoard[kingPositionC/8+temp*j][kingPositionC%8+temp*k])) {
						temp++;
					}
					if("b".equals(chessBoard[kingPositionC/8+temp*j][kingPositionC%8+temp*k]) || 
						"q".equals(chessBoard[kingPositionC/8+temp*j][kingPositionC%8+temp*k])) {
						return false;
					}	
				}catch(Exception e) {}
				temp = 1;
			}
		}
		//rook + queen
		for(int i=-1; i<=1;i+=2) {
			try {
				int temp =1;
				while(" ".equals(chessBoard[kingPositionC/8][kingPositionC%8+temp*i])) {
					temp++;
				}
				
				if("r".equals(chessBoard[kingPositionC/8][kingPositionC%8+temp*i]) ||
					"q".equals(chessBoard[kingPositionC/8][kingPositionC%8+temp*i])) {
					return false;
				}	
			}catch (Exception e) {}
			try {
				int temp =1;
				while(" ".equals(chessBoard[kingPositionC/8+temp*i][kingPositionC%8])) {
					temp++;
				}
				
				if("r".equals(chessBoard[kingPositionC/8+temp*i][kingPositionC%8]) ||
					"q".equals(chessBoard[kingPositionC/8+temp*i][kingPositionC%8])) {
					return false;
				}	
			}catch (Exception e) {}
		}
		//knights
		
				for(int j=-1; j<=1; j+=2) {
					for(int k=-1;k<=1;k+=2) {
						try {
							if("k".equals(chessBoard[kingPositionC/8+j][kingPositionC%8+2*k])){
								return false;
							}	
						}catch(Exception e) {}
						try {
							if("k".equals(chessBoard[kingPositionC/8+2*j][kingPositionC%8+k])){
								return false;
							}	
						}catch(Exception e) {}
					}
				}
		//pawn
				
				if(kingPositionC>=16) {
					try {
						if("p".equals(chessBoard[kingPositionC/8-1][kingPositionC%8-1])) {
							return false;
						}
					}catch(Exception e) {}
					try {
						if("p".equals(chessBoard[kingPositionC/8-1][kingPositionC%8+1])) {
							return false;
						}
					}catch(Exception e) {}
				}
		//king
				for(int j=-1; j<=1; j++) {
					for(int k=-1;k<=1;k++) {
						if(k  ==0 && j==0) {
							continue;
						}
						try {
							if("a".equals(chessBoard[kingPositionC/8+j][kingPositionC%8+k])){
								return false;
							}	
						}catch(Exception e) {}
					}
				}
		return true;
	}

}
