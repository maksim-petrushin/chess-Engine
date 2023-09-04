import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
	private static int turn = 0;
	private static final long serialVersionUID = 1L;
	static int mouseX, mouseY, newMouseX, newMouseY;
	static int squareSize=32;
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		this.setBackground(Color.gray);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		for(int i=0; i<64; i+=2) {
			g.setColor(new Color(255,200,100));
			g.fillRect((i%8+(i/8)%2)*squareSize ,i/8*squareSize, squareSize, squareSize);
			g.setColor(new Color(150,50,30));
			g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize ,(i+1)/8*squareSize, squareSize, squareSize);
		}
		/*
		g.setColor(Color.BLUE);
		g.fillRect(x-20, y-20, 20, 20);
		g.drawString("Maksim",x,y);
		*/
		Image chessPieceImage;
		chessPieceImage = new ImageIcon("ChessPieces.png").getImage();
		for(int i=0;i<64;i++) {
			int j=-1,k=-1; 
			switch(ChessProject.chessBoard[i/8][i%8]) {
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
			case "K":
				j=1;
				k=1;
				break; 
			case "k":
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
			case "A":
				j=4;
				k=1;
				break;
			case "a":
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
				g.drawImage(chessPieceImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*77, k*77, (j+1)*77, (k+1)*77, this);

			}
		}
		

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		
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
				 if(newMouseY/squareSize ==0 && mouseY/squareSize == 1 && "P".equals(ChessProject.chessBoard[mouseY/squareSize][mouseX/squareSize])) {
					 dragMove = ""+ mouseX/squareSize + newMouseX/squareSize+ChessProject.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";
				 }
				 else {
					 dragMove = ""+mouseY/squareSize + mouseX/squareSize +newMouseY/squareSize + newMouseX/squareSize+ChessProject.chessBoard[newMouseY/squareSize][newMouseX/squareSize];
				 }
				String possibleMovesUser = ChessProject.possibleMoves();
				if(possibleMovesUser.replaceAll(dragMove,"").length() < possibleMovesUser.length()) {
					ChessProject.makeMove(dragMove);
					ChessProject.flipBoard();
					ChessProject.makeMove(ChessProject.alphaBeta(ChessProject.globalDepth, 100000, -100000, "", 0));
					ChessProject.flipBoard();
					repaint();
					turn = 1;

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

