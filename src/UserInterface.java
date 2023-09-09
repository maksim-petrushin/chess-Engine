import java.awt.*;
import java.util.Arrays;
import java.awt.event.*;

import javax.swing.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	static int mouseX, mouseY, newMouseX, newMouseY;
	static int squareSize=64;
	Timer stopwatch;
	int count=0;
	int delay =200;
	int turn =0;
	@Override
	public void paintComponent(Graphics graphics) {
		
		super.paintComponent(graphics);
		this.setBackground(Color.gray);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for(int i=0; i<64; i+=2) {
			graphics.setColor(new Color(255,200,100));
			graphics.fillRect((i%8+(i/8)%2)*squareSize ,i/8*squareSize, squareSize, squareSize);
			graphics.setColor(new Color(150,50,30));
			graphics.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize ,(i+1)/8*squareSize, squareSize, squareSize);
		}
		/*
		graphics.setColor(Color.BLUE);
		graphics.fillRect(x-20, y-20, 20, 20);
		graphics.drawString("Maksim",x,y);
		*/
		Image chessPieceImage;
		chessPieceImage = new ImageIcon("ChessPieces.png").getImage();
		for(int i=0;i<64;i++) {
			int j=-1,k=-1; 
			switch(ChessProject.board[i/8][i%8]) {
			//case "P":
			//	list+=possibleP(i);
			//	break; 
			case "P":
				j=5;
				k=1;
				break;
			case "p":
				j=5;
				k=0;
				break;
			case "R":
				j=0;
				k=1;
				break; 
			case "r":
				j=0;
				k=0;
				break; 
			case "N":
				j=1;
				k=1;
				break; 
			case "n":
				j=1;
				k=0;
				break; 
			case "B":
				j=2;
				k=1;
				break; 
			case "b":
				j=2;
				k=0;
				break; 
			case "K":
				j=4;
				k=1;
				break;
			case "k":
				j=4;
				k=0;
				break;
			case "Q":
				j=3;
				k=1;
				break;
			case "q":
				j=3;
				k=0;
				break;
			default:
				break;
			}
			if(j!=-1 && k!=-1) {
				graphics.drawImage(chessPieceImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*77, k*77, (j+1)*77, (k+1)*77, this);

			}

		}
		//System.out.println("repainted");
		if(turn==1) {
			turn =2;
		}
		//for(int i = 0; i< 8; i++) {
		//	System.out.println(Arrays.toString(ChessProject.board[i]));
		//}
		

		
		
		

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(turn ==2) {
			ChessProject.callAlphaBeta();
			turn=0;
			repaint();
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()== MouseEvent.BUTTON3) {
			for(int i = 0; i< 8; i++) {
				System.out.println(Arrays.toString(ChessProject.board[i]));
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getX()<8*squareSize && e.getY()<8*squareSize) {
			 mouseX = e.getX();
			 mouseY = e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getX()<8*squareSize && e.getY()<8*squareSize) {
			 newMouseX = e.getX();
			 newMouseY = e.getY();

			 if(e.getButton()== MouseEvent.BUTTON1) {
				 String dragMove;
				 if(newMouseY/squareSize ==0 && mouseY/squareSize == 1 && "P".equals(ChessProject.board[mouseY/squareSize][mouseX/squareSize])) {
					 dragMove = ""+ mouseX/squareSize + newMouseX/squareSize+ChessProject.board[newMouseY/squareSize][newMouseX/squareSize]+"QP";
				 }
				 else {
					 dragMove = ""+mouseY/squareSize + mouseX/squareSize +newMouseY/squareSize + newMouseX/squareSize+ChessProject.board[newMouseY/squareSize][newMouseX/squareSize];
				 }
				String possibleMovesUser = Moves.possibleMoves(1);
				System.out.println(possibleMovesUser);
				if(possibleMovesUser.replaceAll(dragMove,"").length() < possibleMovesUser.length()) {
					if(ChessProject.board[Character.getNumericValue(dragMove.charAt(0))][Character.getNumericValue(dragMove.charAt(1))]=="K") {
						ChessProject.whiteKingMoved = 1;
					}
					Moves.makeMove(dragMove, 1);
					System.out.println(dragMove);
					turn =1;

					repaint();

				}
				 
			 }
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

