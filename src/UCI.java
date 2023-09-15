import java.util.Scanner;
public class UCI {
    static String ENGINENAME="Maxet";
    public static void uciCommunication(String inputString ) {
        Scanner input = new Scanner(System.in);
            
            if ("uci".contains(inputString))
            {
            	greetingUCI();
            }
            else if (inputString.contains("debug on")) {
            	//TODO:
            }
            else if (inputString.contains("debug off")) {
            	//TODO:
            }
            else if (inputString.contains("isready"))
            {
                isReady();
            }
            else if (inputString.contains("setoption"))
            {
                inputSetOption(inputString);
            }
            
            else if (inputString.contains("ucinewgame"))
            {
                inputUCINewGame();
            }
            else if (inputString.contains("position startpos"))
            {
            	inputUCINewGame();
                buildPosition(inputString);
            }
            else if (inputString.contains("go"))
            {
                //inputGo();
            }
            else if (inputString.contains("quit"))
            {
                //inputQuit();
            }
            else if (inputString.contains("print"))
            {
                //inputPrint();
            }
            input.close();
        
    }
    public static void greetingUCI() {
        System.out.println("id name "+ENGINENAME);
        System.out.println("id author Maksim Petrushin");
        //TODO: options
        System.out.println("uciok");
    }
    public static void inputSetOption(String inputString) {
    	//TODO:
    }
    public static void isReady() {
         System.out.println("readyok");
    }
    
 

    public static void inputUCINewGame() {
        //TODO:
    	String freshBoard[][] = {
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
    	for(int i=0; i<64; i++) {
    		ChessProject.board[i/8][i%8]=freshBoard[i/8][i%8];
    	}
    }
    	
    
    public static void buildPosition(String input) {
        if (input.contains("moves")) {
            input=input.substring(input.indexOf("moves")+6);
            int side = 1;
            while (input.length()>0)
            {
                String moves = Moves.possibleMoves(side);
                String maxetMove = algebraToMove(input,moves, side);
                input=input.substring(input.indexOf(' ')+1);
                side = 1 - side;
            }
        }
    }
    public static String algebraToMove(String input,String moves, int side) {
        int start=0,end=0;
        String output = "";
        int from=(input.charAt(0)-'a')+(8*('8'-input.charAt(1)));
        int to=(input.charAt(2)-'a')+(8*('8'-input.charAt(3)));
        if(side == 1) {
        	if("P".equals(ChessProject.board[from/8][from%8])) {//pawn move
        		if( from/8 == 1) {//promotion
        			output = ""+(from%8)+(to%8)+ChessProject.board[to/8][to%8]+String.valueOf(input.charAt(4)).toUpperCase()+"P";
        		}
        		else { //non-promotion
        			output = ""+(from/8)+(from%8)+(to/8)+(to%8)+ChessProject.board[to/8][to%8];
        		}
        		
        	}
        	else if("K".equals(ChessProject.board[from/8][from%8])) {//king moves
        		if(from == 60 && to == 62) {//king-side castle
        			output = "7476C";
        		}
        		else if(from == 60 && to == 58) {//queen-side castle
        			output = "7472C";
        		}
        		else {//normal king move
        			output = ""+(from/8)+(from%8)+(to/8)+(to%8)+ChessProject.board[to/8][to%8];
        		}
        	}
        	else {//regular move with other piece
        		output = ""+(from/8)+(from%8)+(to/8)+(to%8)+ChessProject.board[to/8][to%8];
        	}
        	
    
        
    }
        if(moves.replaceAll(output,"").length() < moves.length()) {
        	return output;
        }
        return "";
        
    }
   
}
    
    