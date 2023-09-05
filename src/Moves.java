import java.util.Arrays;

public class Moves {
	
	public static void flipBoard() {
		String temp;
		for(int i=0; i<32; i++) {
			int r = i/8, c=i%8;
			if(Character.isUpperCase(ChessProject.board[r][c].charAt(0))){
				temp = ChessProject.board[r][c].toLowerCase();
			}
			else {
				temp = ChessProject.board[r][c].toUpperCase();
			}
			if(Character.isUpperCase(ChessProject.board[7-r][7-c].charAt(0))){
				ChessProject.board[r][c] = ChessProject.board[7-r][7-c].toLowerCase();
			}
			else {
				ChessProject.board[r][c] = ChessProject.board[7-r][7-c].toUpperCase();
			}
			ChessProject.board[7-r][7-c] = temp;
		}
		//kings moved
		int tempMoved = ChessProject.whiteKingMoved;
		ChessProject.whiteKingMoved = ChessProject.blackKingMoved;
		ChessProject.blackKingMoved = tempMoved;
		
		//kings position
		int kingTemp = ChessProject.whiteKing;
		ChessProject.whiteKing = 63-ChessProject.blackKing;
		ChessProject.blackKing = 63-kingTemp;
	}
	
	public static void makeMove(String move) {
		if(move.charAt(4)!= 'P' && move.charAt(4)!= 'C') {
			ChessProject.board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] 
			= ChessProject.board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
			ChessProject.board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
			if("K".equals(ChessProject.board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
				ChessProject.whiteKing = 8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
				ChessProject.whiteKingMoved = 1;
			}
		}else if(move.charAt(4)== 'P') {//pawn promotion
			ChessProject.board[1][Character.getNumericValue(move.charAt(0))]=" ";
			ChessProject.board[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
		}else {//make castle
			//System.out.println(move);
			if(move.charAt(1) == '4') {
				if(move.charAt(3)== '6') {//king-side castle
					 ChessProject.board[7][6]  = "K"; 
					 ChessProject.board[7][4]  = " ";
					 ChessProject.board[7][5]  = "R"; 
					 ChessProject.board[7][7]  = " "; 
				 }
				 else{//queen side castle
					 ChessProject.board[7][2]  = "K"; 
					 ChessProject.board[7][4]  = " ";
					 ChessProject.board[7][3]  = "R"; 
					 ChessProject.board[7][0]  = " "; 
				 }
			}
			else{
				//System.out.println("yeah");
				if(move.charAt(3)== '5') {//queen-side castle
					 ChessProject.board[7][5]  = "K"; 
					 ChessProject.board[7][3]  = " ";
					 ChessProject.board[7][4]  = "R"; 
					 ChessProject.board[7][7]  = " "; 
				 }
				 else{//king side castle
					 ChessProject.board[7][1]  = "K"; 
					 ChessProject.board[7][3]  = " ";
					 ChessProject.board[7][2]  = "R"; 
					 ChessProject.board[7][0]  = " "; 
				 }
			}
			 
			ChessProject.whiteKingMoved = 1;
			for(int i=0; i<8; i++) {
				System.out.println(Arrays.toString(ChessProject.board[i]));
			}
			System.out.println("");
				
		}
		
	}
	
	public static void undoMove(String move) {
		if(move.charAt(4)!= 'P' && move.charAt(4)!= 'C'){
			ChessProject.board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]
			= ChessProject.board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
			ChessProject.board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = String.valueOf(move.charAt(4));
			if("K".equals(ChessProject.board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
				ChessProject.whiteKing = 8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
				ChessProject.whiteKingMoved = 0;
			}
		}else if(move.charAt(4)== 'P') {//pawn promotion
			ChessProject.board[1][Character.getNumericValue(move.charAt(0))]="P";
			ChessProject.board[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));	
		}
		else {//undo castle
			if((move.charAt(1) == '4')) {//white side
				if(move.charAt(3)== '6') {//king-side castle
					 ChessProject.board[7][6] = " "; 
					 ChessProject.board[7][4]  = "K";
					 ChessProject.board[7][5]  = " "; 
					 ChessProject.board[7][7]  = "R"; 
				 }
				 else if(move.charAt(3)== '2') {//queen side castle
					 ChessProject.board[7][2]  = " "; 
					 ChessProject.board[7][4]  = "K";
					 ChessProject.board[7][3]  = " "; 
					 ChessProject.board[7][0]  = "R"; 
				 }
			}
			else {
				if(move.charAt(3)== '5') {//king-side castle
					 ChessProject.board[7][5] = " "; 
					 ChessProject.board[7][3]  = "K";
					 ChessProject.board[7][4]  = " "; 
					 ChessProject.board[7][7]  = "R"; 
				 }
				 else {//queen side castle
					 ChessProject.board[7][1]  = " "; 
					 ChessProject.board[7][3]  = "K";
					 ChessProject.board[7][2]  = " "; 
					 ChessProject.board[7][0]  = "R"; 
				 }
			}
			ChessProject.whiteKingMoved = 0;
			for(int i=0; i<8; i++) {
				System.out.println(Arrays.toString(ChessProject.board[i]));
			}
			System.out.println("");
		}
	
	}
	
	public static String possibleMoves() {
		String list="";
		for(int i=0; i<64; i++) {
			switch(ChessProject.board[i/8][i%8]) {
			case "P":
				list+=possibleP(i);
				break;
			case "R":
				list+=possibleR(i);
				break; 
			case "N":
				list+=possibleN(i);
				break; 
			case "B":
				list+=possibleB(i);
				break; 
			case "K":
				list+=possibleK(i);
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
					if(i>=16 && Character.isLowerCase(ChessProject.board[r-1][c+j].charAt(0))){
						oldPiece = ChessProject.board[r-1][c+j];
						ChessProject.board[r][c] = " ";
						ChessProject.board[r-1][c+j] = "P";
						if(kingSafe()) {
							list = list+r+c+(r-1)+(c+j)+oldPiece;
						}
						ChessProject.board[r][c]="P";
						ChessProject.board[r-1][c+j]=oldPiece;
					}
				}catch(Exception e) {}
				try{//promotion
					if(i<16 && Character.isLowerCase(ChessProject.board[r-1][c+j].charAt(0))){
						String[] temp = {"Q","N", "R", "B"};
						for(int k=0; k<4;k++) {
							oldPiece = ChessProject.board[r-1][c+j];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r-1][c+j] = temp[k];
							if(kingSafe()) {
								list = list+c+(c+j)+oldPiece+temp[k]+"P";
							}
							ChessProject.board[r][c]="P";
							ChessProject.board[r-1][c+j]=oldPiece;
						}
						
					}
				}catch(Exception e) {}
		}
		try{//move one up
			if(i>=16 && " ".equals(ChessProject.board[r-1][c])){
					ChessProject.board[r][c] = " ";
					ChessProject.board[r-1][c] = "P";
					if(kingSafe()) {
						list = list+r+c+(r-1)+(c)+" ";
					}
					ChessProject.board[r][c]="P";
					ChessProject.board[r-1][c]=" ";
			}
		}catch(Exception e) {}
		try{//promotion and no capture
			if(i<16 && " ".equals(ChessProject.board[r-1][c])){
				String[] temp = {"Q","N", "R", "B"};
				for(int k=0; k<4;k++) {
					ChessProject.board[r][c] = " ";
					ChessProject.board[r-1][c] = temp[k];
					if(kingSafe()) {
						list = list+c+c+" "+temp[k]+"P";
					}
					ChessProject.board[r][c]="P";
					ChessProject.board[r-1][c]=" ";
				}
			}
		}catch(Exception e) {}
		try{//move two up
			if(i>=48 && " ".equals(ChessProject.board[r-1][c]) && " ".equals(ChessProject.board[r-2][c])){
					ChessProject.board[r][c] = " ";
					ChessProject.board[r-2][c] = "P";
					if(kingSafe()) {
						list = list+r+c+(r-2)+(c)+" ";
					}
					ChessProject.board[r][c]="P";
					ChessProject.board[r-2][c]=" ";
			}
		}catch(Exception e) {}
		return list;
	}
	
	public static String possibleK(int i) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		for(int j=0; j<9; j++) {
			if(j!=4) {
				try{
					if(Character.isLowerCase(ChessProject.board[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(ChessProject.board[r-1+j/3][c-1+j%3])) {
					oldPiece = ChessProject.board[r-1+j/3][c-1+j%3];
					ChessProject.board[r][c] = " ";
					ChessProject.board[r-1+j/3][c-1+j%3]="K";
					int kingTemp=ChessProject.whiteKing;
					ChessProject.whiteKing = i+(j/3)*8+j%3-9;
					if(kingSafe()) {
						list = list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
					}
					ChessProject.board[r][c] = "K";
					ChessProject.board[r-1+j/3][c-1+j%3]=oldPiece;
					ChessProject.whiteKing = kingTemp;
				}
				}catch(Exception e) {
					
				}
			}
		}
		
		if(ChessProject.whiteKingMoved==0) {
			if ("K".equals(ChessProject.board[7][4])){
				if("R".equals(ChessProject.board[7][7])) {list += "7476C";}
				if("R".equals(ChessProject.board[7][0])) {list += "7472C";}
				
			}
			if("K".equals(ChessProject.board[7][3])) {

				if("R".equals(ChessProject.board[7][7])) {list += "7375C";}
				if("R".equals(ChessProject.board[7][0])) {list += "7371C";}
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
					while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
						ChessProject.board[r][c] = " ";
						ChessProject.board[r+temp*j][c+temp*k] = "Q";
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
						}
						ChessProject.board[r][c]="Q";
						ChessProject.board[r+temp*j][c+temp*k]=" ";
						temp++;
					}
					if(Character.isLowerCase(ChessProject.board[r+temp*j][c+temp*k].charAt(0))) {
						
							oldPiece = ChessProject.board[r+temp*j][c+temp*k];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "Q";
							if(kingSafe()) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							ChessProject.board[r][c]="Q";
							ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
							temp++;
					}
					
				} catch(Exception e) {}
				temp =1;
			}
		
		}
		return list;
	}
	
	public static String possibleN(int i) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		for(int j=-1; j<=1; j+=2) {		
			for(int k=-1;k<=1;k+=2) {
				try {
					if(Character.isLowerCase(ChessProject.board[r+j][c+2*k].charAt(0)) || " ".equals(ChessProject.board[r+j][c+2*k])) {
						oldPiece = ChessProject.board[r+j][c+2*k];
						ChessProject.board[r][c] = " ";
						ChessProject.board[r+j][c+2*k] = "N";
						if(kingSafe()) {
							list = list+r+c+(r+j)+(c+2*k)+oldPiece;
						}
						ChessProject.board[r][c]="N";
						ChessProject.board[r+j][c+2*k]=oldPiece;
					}
					

				} catch(Exception e) {}
				try {
					if(Character.isLowerCase(ChessProject.board[r+2*j][c+k].charAt(0)) || " ".equals(ChessProject.board[r+2*j][c+k])) {
						oldPiece = ChessProject.board[r+2*j][c+k];
						ChessProject.board[r][c] = " ";
						ChessProject.board[r+2*j][c+k] = "N";
						if(kingSafe()) {
							
							list = list+r+c+(r+2*j)+(c+k)+oldPiece;
						}
						ChessProject.board[r][c]="N";
						ChessProject.board[r+2*j][c+k]=oldPiece;
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
					while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
						ChessProject.board[r][c] = " ";
						ChessProject.board[r+temp*j][c+temp*k] = "R";
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
						}
						ChessProject.board[r][c]="R";
						ChessProject.board[r+temp*j][c+temp*k]=" ";
						temp++;
					}
					if(Character.isLowerCase(ChessProject.board[r+temp*j][c+temp*k].charAt(0))) {
						
							oldPiece = ChessProject.board[r+temp*j][c+temp*k];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "R";
							if(kingSafe()) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							ChessProject.board[r][c]="R";
							ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
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
					while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
						ChessProject.board[r][c] = " ";
						ChessProject.board[r+temp*j][c+temp*k] = "B";
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
						}
						ChessProject.board[r][c]="B";
						ChessProject.board[r+temp*j][c+temp*k]=" ";
						temp++;
					}
					if(Character.isLowerCase(ChessProject.board[r+temp*j][c+temp*k].charAt(0))) {
						
							oldPiece = ChessProject.board[r+temp*j][c+temp*k];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "B";
							if(kingSafe()) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
							}
							ChessProject.board[r][c]="B";
							ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
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
					while(" ".equals(ChessProject.board[ChessProject.whiteKing/8+temp*j][ChessProject.whiteKing%8+temp*k])) {
						temp++;
					}
					if("b".equals(ChessProject.board[ChessProject.whiteKing/8+temp*j][ChessProject.whiteKing%8+temp*k]) || 
						"q".equals(ChessProject.board[ChessProject.whiteKing/8+temp*j][ChessProject.whiteKing%8+temp*k])) {
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
				while(" ".equals(ChessProject.board[ChessProject.whiteKing/8][ChessProject.whiteKing%8+temp*i])) {
					temp++;
				}
				
				if("r".equals(ChessProject.board[ChessProject.whiteKing/8][ChessProject.whiteKing%8+temp*i]) ||
					"q".equals(ChessProject.board[ChessProject.whiteKing/8][ChessProject.whiteKing%8+temp*i])) {
					return false;
				}	
			}catch (Exception e) {}
			try {
				int temp =1;
				while(" ".equals(ChessProject.board[ChessProject.whiteKing/8+temp*i][ChessProject.whiteKing%8])) {
					temp++;
				}
				
				if("r".equals(ChessProject.board[ChessProject.whiteKing/8+temp*i][ChessProject.whiteKing%8]) ||
					"q".equals(ChessProject.board[ChessProject.whiteKing/8+temp*i][ChessProject.whiteKing%8])) {
					return false;
				}	
			}catch (Exception e) {}
		}
		//knights
		
				for(int j=-1; j<=1; j+=2) {
					for(int k=-1;k<=1;k+=2) {
						try {
							if("n".equals(ChessProject.board[ChessProject.whiteKing/8+j][ChessProject.whiteKing%8+2*k])){
								return false;
							}	
						}catch(Exception e) {}
						try {
							if("n".equals(ChessProject.board[ChessProject.whiteKing/8+2*j][ChessProject.whiteKing%8+k])){
								return false;
							}	
						}catch(Exception e) {}
					}
				}
		//pawn
				
				if(ChessProject.whiteKing>=16) {
					try {
						if("p".equals(ChessProject.board[ChessProject.whiteKing/8-1][ChessProject.whiteKing%8-1])) {
							return false;
						}
					}catch(Exception e) {}
					try {
						if("p".equals(ChessProject.board[ChessProject.whiteKing/8-1][ChessProject.whiteKing%8+1])) {
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
							if("k".equals(ChessProject.board[ChessProject.whiteKing/8+j][ChessProject.whiteKing%8+k])){
								return false;
							}	
						}catch(Exception e) {}
					}
				}
		return true;
	}

}
