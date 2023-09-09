

public class Moves {
	
	//TODO: Delete vvv
	
	public static void makeMove(String move, int side) {
		if(side == 1) {
			if(move.charAt(4)!= 'P') {
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
			}	
		}
		else {
			if(move.charAt(4)!= 'p') {
				ChessProject.board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] 
				= ChessProject.board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
				ChessProject.board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))] = " ";
				if("k".equals(ChessProject.board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
					ChessProject.blackKing = 8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
					ChessProject.blackKingMoved = 1;
				}
			}
			else if(move.charAt(4)== 'p') {//pawn promotion
				ChessProject.board[6][Character.getNumericValue(move.charAt(0))]=" ";
				ChessProject.board[7][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
			}
			
		}
		
		
	}
	
	public static void undoMove(String move, int side) {
		if(side == 1) {
			if(move.charAt(4)!= 'P'){
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
		}
		else {
			if(move.charAt(4)!= 'p'){
				ChessProject.board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]
				= ChessProject.board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
				ChessProject.board[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))] = String.valueOf(move.charAt(4));
				if("k".equals(ChessProject.board[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
					ChessProject.blackKing = 8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
					ChessProject.blackKingMoved = 0;
				}
			}else if(move.charAt(4)== 'p') {//pawn promotion
				ChessProject.board[6][Character.getNumericValue(move.charAt(0))]="p";
				ChessProject.board[7][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));	
			}	
			
		}
		
	}
	
	public static String possibleMoves(int side) { //side 1 - white, 0 - black
		String list="";
		if(side == 1) {
		
			for(int i=0; i<64; i++) {
				switch(ChessProject.board[i/8][i%8]) {
				case "P":
					list+=possibleP(i, side);
					break;
				case "R":
					list+=possibleR(i, side);
					break; 
				case "N":
					list+=possibleN(i, side);
					break; 
				case "B":
					list+=possibleB(i, side);
					break; 
				case "K":
					list+=possibleK(i, side);
					break;
				case "Q":
					list+=possibleQ(i, side);
					break;
				default:
					break;
				}
				if(!" ".equals(ChessProject.board[i/8][i%8])) {
					System.out.print(ChessProject.board[i/8][i%8]+"  ");
					System.out.println(list);
				}
				
	
			}
		}
		else {
			for(int i=0; i<64; i++) {
				switch(ChessProject.board[i/8][i%8]) {
				
				case "p":
					list+=possibleP(i, side);
					break;
				/*			
				case "r":
					list+=possibleR(i, side);
					break; 
					
				case "n":
					list+=possibleN(i, side);
					break; 
				
				
				
				case "b":
					list+=possibleB(i, side);
					break; 
					
				
				case "k":
					list+=possibleK(i, side);
					break;
				
				
				case "q":
					list+=possibleQ(i, side);
					break;
					
				*/
				default:
					break;
				}
	
			}
			
		}
		
		return list;//x1, y1, x2, y2, captured piece
	}
	
	public static String possibleP(int i, int side) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		if(side == 1) {
			for(int j=-1; j<=1; j+=2) {
				//capture
					try{
						if(i>=16 && Character.isLowerCase(ChessProject.board[r-1][c+j].charAt(0))){
							oldPiece = ChessProject.board[r-1][c+j];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r-1][c+j] = "P";
							if(kingSafe(side)) {
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
								if(kingSafe(side)) {
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
						if(kingSafe(side)) {
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
						if(kingSafe(side)) {
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
						if(kingSafe(side)) {
							list = list+r+c+(r-2)+(c)+" ";
						}
						ChessProject.board[r][c]="P";
						ChessProject.board[r-2][c]=" ";
				}
			}catch(Exception e) {}	
		}
		else {
			for(int j=-1; j<=1; j+=2) {
				//capture
					try{
						if(i<=48 && Character.isUpperCase(ChessProject.board[r-1][c+j].charAt(0))){
							oldPiece = ChessProject.board[r-1][c+j];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+1][c+j] = "p";
							if(kingSafe(side)) {
								list = list+r+c+(r+1)+(c+j)+oldPiece;
							}
							ChessProject.board[r][c]="p";
							ChessProject.board[r+1][c+j]=oldPiece;
						}
					}catch(Exception e) {}
					try{//promotion
						if(i>48 && Character.isUpperCase(ChessProject.board[r-1][c+j].charAt(0))){
							String[] temp = {"q","n", "r", "b"};
							for(int k=0; k<4;k++) {
								oldPiece = ChessProject.board[r-1][c+j];
								ChessProject.board[r][c] = " ";
								ChessProject.board[r+1][c+j] = temp[k];
								if(kingSafe(side)) {
									list = list+c+(c+j)+oldPiece+temp[k]+"p";
								}
								ChessProject.board[r][c]="p";
								ChessProject.board[r+1][c+j]=oldPiece;
							}	
						}
					}catch(Exception e) {}
			}
			try{//move one up
				if(i<=48 && " ".equals(ChessProject.board[r-1][c])){
						ChessProject.board[r][c] = " ";
						ChessProject.board[r+1][c] = "p";
						if(kingSafe(side)) {
							list = list+r+c+(r+1)+(c)+" ";
						}
						ChessProject.board[r][c]="p";
						ChessProject.board[r+1][c]=" ";
				}
			}catch(Exception e) {}
			try{//promotion and no capture
				if(i>48 && " ".equals(ChessProject.board[r-1][c])){
					String[] temp = {"q","n", "r", "b"};
					for(int k=0; k<4;k++) {
						ChessProject.board[r][c] = " ";
						ChessProject.board[r+1][c] = temp[k];
						if(kingSafe(side)) {
							list = list+c+c+" "+temp[k]+"p";
						}
						ChessProject.board[r][c]="p";
						ChessProject.board[r+1][c]=" ";
					}
				}
			}catch(Exception e) {}
			try{//move two up
				if(i<16 && " ".equals(ChessProject.board[r-1][c]) && " ".equals(ChessProject.board[r-2][c])){
						ChessProject.board[r][c] = " ";
						ChessProject.board[r+2][c] = "p";
						if(kingSafe(side)) {
							list = list+r+c+(r+2)+(c)+" ";
						}
						ChessProject.board[r][c]="p";
						ChessProject.board[r+2][c]=" ";
				}
			}catch(Exception e) {}	
		}
		
		return list;
	}
	
	public static String possibleK(int i, int side) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		if(side == 1) {
			for(int j=0; j<9; j++) {
				if(j!=4) {
					try{
						if(Character.isLowerCase(ChessProject.board[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(ChessProject.board[r-1+j/3][c-1+j%3])) {
						oldPiece = ChessProject.board[r-1+j/3][c-1+j%3];
						ChessProject.board[r][c] = " ";
						ChessProject.board[r-1+j/3][c-1+j%3]="K";
						int kingTemp=ChessProject.whiteKing;
						ChessProject.whiteKing = i+(j/3)*8+j%3-9;
						if(kingSafe(side)) {
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
		}
		else {
			for(int j=0; j<9; j++) {
				if(j!=4) {
					try{
						if(Character.isUpperCase(ChessProject.board[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(ChessProject.board[r-1+j/3][c-1+j%3])) {
						oldPiece = ChessProject.board[r-1+j/3][c-1+j%3];
						ChessProject.board[r][c] = " ";
						ChessProject.board[r-1+j/3][c-1+j%3]="k";
						int kingTemp=ChessProject.blackKing;
						ChessProject.blackKing = i+(j/3)*8+j%3-9;
						if(kingSafe(side)) {
							list = list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
						}
						ChessProject.board[r][c] = "k";
						ChessProject.board[r-1+j/3][c-1+j%3]=oldPiece;
						ChessProject.blackKing = kingTemp;
					}
					}catch(Exception e) {
						
					}
				}
			}
			
		}
		
		return list;
	}
	
	public static String possibleQ(int i, int side) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		int temp=1;
		if(side ==1) {
			for(int j=-1; j<=1; j++) {
				
				for(int k=-1;k<=1;k++) {
					try {
						while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "Q";
							if(kingSafe(side)) {
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
								if(kingSafe(side)) {
									list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
								}
								ChessProject.board[r][c]="Q";
								ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
						}
						
					} catch(Exception e) {}
					temp =1;
				}
			
			}	
		}
		else {
			for(int j=-1; j<=1; j++) {
				
				for(int k=-1;k<=1;k++) {
					try {
						while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "q";
							if(kingSafe(side)) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
							}
							ChessProject.board[r][c]="q";
							ChessProject.board[r+temp*j][c+temp*k]=" ";
							temp++;
						}
						if(Character.isUpperCase(ChessProject.board[r+temp*j][c+temp*k].charAt(0))) {
							
								oldPiece = ChessProject.board[r+temp*j][c+temp*k];
								ChessProject.board[r][c] = " ";
								ChessProject.board[r+temp*j][c+temp*k] = "q";
								if(kingSafe(side)) {
									list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
								}
								ChessProject.board[r][c]="q";
								ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
						}
						
					} catch(Exception e) {}
					temp =1;
				}
			
			}	
		}
		
		return list;
	}
	
	public static String possibleN(int i, int side) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		if(side ==1) {
			
			for(int j=-1; j<=1; j+=2) {		
				for(int k=-1;k<=1;k+=2) {
					try {
						if(Character.isLowerCase(ChessProject.board[r+j][c+2*k].charAt(0)) || " ".equals(ChessProject.board[r+j][c+2*k])) {
							oldPiece = ChessProject.board[r+j][c+2*k];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+j][c+2*k] = "N";
							if(kingSafe(side)) {
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
							if(kingSafe(side)) {
								
								list = list+r+c+(r+2*j)+(c+k)+oldPiece;
							}
							ChessProject.board[r][c]="N";
							ChessProject.board[r+2*j][c+k]=oldPiece;
						}
					} catch(Exception e) {}
				}
			
			}
		}
		else {
			for(int j=-1; j<=1; j+=2) {		
				for(int k=-1;k<=1;k+=2) {
					try {
						if(Character.isUpperCase(ChessProject.board[r+j][c+2*k].charAt(0)) || " ".equals(ChessProject.board[r+j][c+2*k])) {
							oldPiece = ChessProject.board[r+j][c+2*k];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+j][c+2*k] = "n";
							if(kingSafe(side)) {
								list = list+r+c+(r+j)+(c+2*k)+oldPiece;
							}
							ChessProject.board[r][c]="n";
							ChessProject.board[r+j][c+2*k]=oldPiece;
						}
						

					} catch(Exception e) {}
					try {
						if(Character.isUpperCase(ChessProject.board[r+2*j][c+k].charAt(0)) || " ".equals(ChessProject.board[r+2*j][c+k])) {
							oldPiece = ChessProject.board[r+2*j][c+k];
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+2*j][c+k] = "n";
							if(kingSafe(side)) {
								
								list = list+r+c+(r+2*j)+(c+k)+oldPiece;
							}
							ChessProject.board[r][c]="n";
							ChessProject.board[r+2*j][c+k]=oldPiece;
						}
					} catch(Exception e) {}
				}
			
			}
		}
		return list;
	}
	
	public static String possibleR(int i, int side) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		int temp=1;
		if(side == 1) {
			for(int j=-1; j<=1; j++) {	
				for(int k=-1; k<=1; k++) {
					if( k!= 0 && j!=0) {
						continue;
					}
					try {
						while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "R";
							if(kingSafe(side)) {
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
								if(kingSafe(side)) {
									list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
								}
								ChessProject.board[r][c]="R";
								ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
						}
						
					} catch(Exception e) {}
					temp =1;
				}
			}
		}
		else {
			for(int j=-1; j<=1; j++) {	
				for(int k=-1; k<=1; k++) {
					if( k!= 0 && j!=0) {
						continue;
					}
					try {
						while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "r";
							if(kingSafe(side)) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
							}
							ChessProject.board[r][c]="r";
							ChessProject.board[r+temp*j][c+temp*k]=" ";
							temp++;
						}
						if(Character.isUpperCase(ChessProject.board[r+temp*j][c+temp*k].charAt(0))) {
							
								oldPiece = ChessProject.board[r+temp*j][c+temp*k];
								ChessProject.board[r][c] = " ";
								ChessProject.board[r+temp*j][c+temp*k] = "r";
								if(kingSafe(side)) {
									list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
								}
								ChessProject.board[r][c]="r";
								ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
						}
						
					} catch(Exception e) {}
					temp =1;
				}
			}
		}
		
		return list;
	}
	
	public static String possibleB(int i, int side) {
		String list="", oldPiece;
		int r=i/8, c=i%8;
		int temp=1;
		if(side == 1) {
			for(int j=-1; j<=1; j=j+2) {
				
				for(int k=-1;k<=1;k=k+2) {
					try {
						while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "B";
							if(kingSafe(side)) {
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
								if(kingSafe(side)) {
									list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
								}
								ChessProject.board[r][c]="B";
								ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
						}
						
					} catch(Exception e) {}
					temp =1;
				}
			
			}	
		}
		else {
			for(int j=-1; j<=1; j=j+2) {
				
				for(int k=-1;k<=1;k=k+2) {
					try {
						while(" ".equals(ChessProject.board[r+temp*j][c+temp*k])) {
							ChessProject.board[r][c] = " ";
							ChessProject.board[r+temp*j][c+temp*k] = "b";
							if(kingSafe(side)) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+" ";
							}
							ChessProject.board[r][c]="b";
							ChessProject.board[r+temp*j][c+temp*k]=" ";
							temp++;
						}
						if(Character.isUpperCase(ChessProject.board[r+temp*j][c+temp*k].charAt(0))) {
							
								oldPiece = ChessProject.board[r+temp*j][c+temp*k];
								ChessProject.board[r][c] = " ";
								ChessProject.board[r+temp*j][c+temp*k] = "b";
								if(kingSafe(side)) {
									list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
								}
								ChessProject.board[r][c]="b";
								ChessProject.board[r+temp*j][c+temp*k]=oldPiece;
						}
						
					} catch(Exception e) {}
					temp =1;
				}
			
			}	
		}			
		
		return list;
	}
	
	public static boolean kingSafe(int side) {
		if(side == 1) {
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
							System.out.println("bishop or queen");
							System.out.println(ChessProject.whiteKing);
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
						System.out.println("rook or queen");
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
						System.out.println("rook or queen");
						return false;
					}	
				}catch (Exception e) {}
			}
			//knights
			
					for(int j=-1; j<=1; j+=2) {
						for(int k=-1;k<=1;k+=2) {
							try {
								if("n".equals(ChessProject.board[ChessProject.whiteKing/8+j][ChessProject.whiteKing%8+2*k])){
									System.out.println("knight");
									return false;
								}	
							}catch(Exception e) {}
							try {
								if("n".equals(ChessProject.board[ChessProject.whiteKing/8+2*j][ChessProject.whiteKing%8+k])){
									System.out.println("knight");
									return false;
								}	
							}catch(Exception e) {}
						}
					}
			//pawn
					
					if(ChessProject.whiteKing>=16) {
						try {
							if("p".equals(ChessProject.board[ChessProject.whiteKing/8-1][ChessProject.whiteKing%8-1])) {
								System.out.println("pawn");
								return false;
							}
						}catch(Exception e) {}
						try {
							if("p".equals(ChessProject.board[ChessProject.whiteKing/8-1][ChessProject.whiteKing%8+1])) {
								System.out.println("pawn");
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
									System.out.println("king");
									System.out.println(ChessProject.whiteKing);
									return false;
								}	
							}catch(Exception e) {}
						}
					}
			return true;			
		}
		else {
			//bishop + queen
			
			for(int j=-1; j<=1; j+=2) {
				for(int k=-1;k<=1;k+=2) {
					int temp =1;
					try {
						while(" ".equals(ChessProject.board[ChessProject.blackKing/8+temp*j][ChessProject.blackKing%8+temp*k])) {
							temp++;
						}
						if("B".equals(ChessProject.board[ChessProject.blackKing/8+temp*j][ChessProject.blackKing%8+temp*k]) || 
							"Q".equals(ChessProject.board[ChessProject.blackKing/8+temp*j][ChessProject.blackKing%8+temp*k])) {
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
					while(" ".equals(ChessProject.board[ChessProject.blackKing/8][ChessProject.blackKing%8+temp*i])) {
						temp++;
					}
					
					if("R".equals(ChessProject.board[ChessProject.blackKing/8][ChessProject.blackKing%8+temp*i]) ||
						"Q".equals(ChessProject.board[ChessProject.blackKing/8][ChessProject.blackKing%8+temp*i])) {
						return false;
					}	
				}catch (Exception e) {}
				try {
					int temp =1;
					while(" ".equals(ChessProject.board[ChessProject.blackKing/8+temp*i][ChessProject.blackKing%8])) {
						temp++;
					}
					
					if("R".equals(ChessProject.board[ChessProject.blackKing/8+temp*i][ChessProject.blackKing%8]) ||
						"Q".equals(ChessProject.board[ChessProject.blackKing/8+temp*i][ChessProject.blackKing%8])) {
						return false;
					}	
				}catch (Exception e) {}
			}
			//knights
			
					for(int j=-1; j<=1; j+=2) {
						for(int k=-1;k<=1;k+=2) {
							try {
								if("N".equals(ChessProject.board[ChessProject.blackKing/8+j][ChessProject.blackKing%8+2*k])){
									return false;
								}	
							}catch(Exception e) {}
							try {
								if("N".equals(ChessProject.board[ChessProject.blackKing/8+2*j][ChessProject.blackKing%8+k])){
									return false;
								}	
							}catch(Exception e) {}
						}
					}
			//pawn
					
						try {
							if("P".equals(ChessProject.board[ChessProject.blackKing/8+1][ChessProject.blackKing%8-1])) {
								return false;
							}
						}catch(Exception e) {}
						try {
							if("P".equals(ChessProject.board[ChessProject.blackKing/8+1][ChessProject.blackKing%8+1])) {
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
								if("K".equals(ChessProject.board[ChessProject.blackKing/8+j][ChessProject.blackKing%8+k])){
									return false;
								}	
							}catch(Exception e) {}
						}
					}
			return true;
		}

	}


