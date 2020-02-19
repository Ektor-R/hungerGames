package Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.Board;
import main.Game;
import main.MinMaxPlayer;
import main.Player;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;

public class GUI extends JFrame{
	
	private RoundPanel roundPanel;
	
	
	private BoardPanel boardPanel;
	
	//playerTypes
	private static String[] playerTypes = {"Random Player", "Heuristic Player", "Min-Max Player"};
			
	
	private PlayerAPanel playerAPanel;
	
	private PlayerBPanel playerBPanel;
	
	private JPanel actionPanel;
	private GenerateBoardButton generateBoardButton;
	private PlayButton playButton;
	private QuitButton quitButton;
	
	//constructor
	public GUI() {
		super("Hunger Games");
		
		setSize(800,600);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		//round
		roundPanel = new RoundPanel();
		add(roundPanel, BorderLayout.PAGE_START);
		
		//board
		boardPanel = new BoardPanel();
		add(boardPanel, BorderLayout.CENTER);
		
		//playerA
		playerAPanel = new PlayerAPanel();
		add(playerAPanel, BorderLayout.LINE_START);
		
		//playerB
		playerBPanel = new PlayerBPanel();
		add(playerBPanel, BorderLayout.LINE_END);
		
		//buttons
		actionPanel = new JPanel();
		actionPanel.setLayout(new GridLayout());
		
		generateBoardButton = new GenerateBoardButton();
		actionPanel.add(generateBoardButton);
		
		playButton = new PlayButton();
		actionPanel.add(playButton);
		
		quitButton = new QuitButton();
		actionPanel.add(quitButton);
		add(actionPanel, BorderLayout.PAGE_END);
		
		setVisible(true);
	}
	
	/*
	 *
	 *Component classes
	 * 
	 */
	
	public static class RoundPanel extends JPanel{
		static JLabel round;
		
		public RoundPanel(){
			round = new JLabel("Round: ");
			add(round);
			
		}
		public static void setRound(int gameRound) {
			round.setText("Round: " + gameRound);
		}
	}
	public static class BoardPanel extends JPanel{
		
		static JPanel[][] tiles;
		static int N, M;
		static int step;
		
		public BoardPanel() {
			
			N=20;
			M=20;
			
			setLayout(new GridLayout(N,M));
			
			tiles = new JPanel[N][M];
			for(int i=0; i<20; i++) {
				for(int j=0; j<20; j++) {
					tiles[i][j] = new JPanel();
					tiles[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
					tiles[i][j].setBackground(Color.white);
					add(tiles[i][j]);
				}
			}
		}
		public static void setTiles(String[][] rep) {
			
			for(int i=0; i<20; i++) {
				for(int j=0; j<20; j++) {
					tiles[i][j].setBackground(Color.black);
				}
			}
			
			step = (20 - rep.length)/2;
			N=rep.length;
			M=rep[0].length;
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<M; j++) {
					if(rep[i][j] == "___") {
						tiles[i + step][j + step].setBackground(Color.white);
					}else if (rep[i][j] == "MMP") {
						tiles[i + step][j + step].setBackground(Color.black);
					}else if (rep[i][j] == "RP ") {
						tiles[i + step][j + step].setBackground(Color.gray);
					}else if (rep[i][j].charAt(0) == 'W') {
						tiles[i + step][j + step].setBackground(Color.blue);
					}else if (rep[i][j].charAt(0) == 'F') {
						tiles[i + step][j + step].setBackground(Color.green);
					}else if (rep[i][j].charAt(0) == 'T') {
						tiles[i + step][j + step].setBackground(Color.red);
					}
				}
			}
		}
		
	}
	public static class PlayerAPanel extends JPanel{
		JTextField playerAName;
		static JLabel playerAMoveScore;
		static JLabel playerATotalScore;
		JComboBox playerAType;
		
		public PlayerAPanel() {
			setLayout(new GridLayout(4,1));
			playerAName = new JTextField("Player A Name", 10);
			add(playerAName);
			playerAMoveScore = new JLabel("Move Score: ");
			add(playerAMoveScore);
			playerATotalScore = new JLabel("Total Score: ");
			add(playerATotalScore);
			playerAType = new JComboBox(playerTypes);
			add(playerAType);
		}
		
		public static void setPlayerAMoveScore(int score) {
			playerAMoveScore.setText("Move Score: " + score);
		}
		public static void setPlayerATotalScore(int score) {
			playerATotalScore.setText("Total Score: " + score);
		}
	}
	public static class PlayerBPanel extends JPanel{
		JTextField playerBName;
		static JLabel playerBMoveScore;
		static JLabel playerBTotalScore;
		JComboBox playerBType;
		
		public PlayerBPanel() {
			setLayout(new GridLayout(4,1));
			playerBName = new JTextField("Player B Name", 10);
			add(playerBName);
			playerBMoveScore = new JLabel("Move Score: ");
			add(playerBMoveScore);
			playerBTotalScore = new JLabel("Total Score: ");
			add(playerBTotalScore);
			playerBType = new JComboBox(playerTypes);
			add(playerBType);
		}
		
		public static void setPlayerBMoveScore(int score) {
			playerBMoveScore.setText("Move Score: " + score);
		}
		public static void setPlayerBTotalScore(int score) {
			playerBTotalScore.setText("Total Score: " + score);
		}
	}
	
	//button actionListeners
	public class PlayButton extends JButton implements ActionListener{
		public PlayButton() {
			setText("Play");
			addActionListener(this);
		}
		public void actionPerformed(ActionEvent e) {
			Game.gameplay(generateBoardButton.board, generateBoardButton.minMaxPlayer, generateBoardButton.randomPlayer);
		}
	}
	public class GenerateBoardButton extends JButton implements ActionListener{
		
		private Board board;
		
		int[][] weaponAreaLimits = {{-2, -2}, {2, -2}, {2, 2}, {-2, 2}};
		int[][] foodAreaLimits = {{-3, -3}, {3, -3}, {3, 3}, {-3, 3}};
		int[][] trapAreaLimits = {{-4, -4}, {4, -4}, {4, 4}, {-4 ,4}};
		
		MinMaxPlayer minMaxPlayer;
		Player randomPlayer;
		
		public GenerateBoardButton() {
			setText("Generate Board");
			addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {
			board=new Board(20, 20, 6, 10, 8);
			board.setWeaponAreaLimits(weaponAreaLimits);
			board.setFoodAreaLimits(foodAreaLimits);
			board.setTrapAreaLimits(trapAreaLimits);
			board.createBoard();
			
			//defining Random Player 1
			minMaxPlayer=new MinMaxPlayer();
			minMaxPlayer.setId(1);
			minMaxPlayer.setName("MinMax Player");
			minMaxPlayer.setBoard(board);
			minMaxPlayer.setScore(15);
			minMaxPlayer.setX(-board.getM()/2);
			minMaxPlayer.setY(-board.getN()/2);
			
			//defining Random Player 2
			randomPlayer=new Player();
			randomPlayer.setId(2);
			randomPlayer.setName("Random Player 2");
			randomPlayer.setBoard(board);
			randomPlayer.setScore(15);
			randomPlayer.setX(board.getM()/2);
			randomPlayer.setY(board.getN()/2);
			
			BoardPanel.setTiles(board.getStringRepresentation(minMaxPlayer, randomPlayer));
			RoundPanel.setRound(0);
			PlayerAPanel.setPlayerAMoveScore(0);
			PlayerAPanel.setPlayerATotalScore(15);
			PlayerBPanel.setPlayerBMoveScore(0);
			PlayerBPanel.setPlayerBTotalScore(15);
			
		}
	}
	public class QuitButton extends JButton implements ActionListener{
		public QuitButton() {
			setText("Quit");
			addActionListener(this);
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	//get created Board for gameplay
	public Board getBoard() {
		return generateBoardButton.board;
	}

}
