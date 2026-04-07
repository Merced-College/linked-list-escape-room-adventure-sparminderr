package game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextArea sceneDescription;
	private static JLabel lblImage;
	private static JButton btnChoice1;
	private static JButton btnChoice2;
	private static Scene currentScene;
	private static AdventureGame game;
	private static JTextField userInventory;
	private JTextArea txtItemsRoom;
	//private static JButton btnPickupItem;
	
	/**
	 * Launch the application.
	 */

	
	public static void main(String[] args) {
        //game.play();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI frame = new GameGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameGUI() {
		
		game = new AdventureGame();
		currentScene = game.getCurrentScene();
		//updateSceneDisplay();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 585);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Choose Your Own Adventure Game");
		lblTitle.setFont(new Font("MS Gothic", Font.BOLD, 16));
		lblTitle.setBounds(187, 26, 286, 72);
		contentPane.add(lblTitle);
		
		lblImage = new JLabel("");
		//lblImage.setIcon(new ImageIcon(GameGUI.class.getResource("/images2/lobby.png")));
		
		
		ImageIcon icon = new ImageIcon(GameGUI.class.getResource("/images2/lobby.png")); 
		Image img = icon.getImage();
		
		Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		
		lblImage.setIcon(scaledIcon);
		lblImage.setBounds(187, 109, 300, 300);
		contentPane.add(lblImage);
		
		sceneDescription = new JTextArea();
		sceneDescription.setBounds(187, 420, 300, 33);
		contentPane.add(sceneDescription);
		
		btnChoice1 = new JButton("New button");
		btnChoice1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScene.getChoices().get(0).getNextSceneId();
	            currentScene = game.getScenes().findSceneById(nextId);
	            
	            updateSceneDisplay(); 
	            
	            if(currentScene.getSceneId() == 5) {
	            	checkWinCondiition();
	            }
				
			}
		});
		btnChoice1.setBackground(new Color(255, 255, 0));
		btnChoice1.setBounds(44, 156, 89, 59);
		contentPane.add(btnChoice1);
		
		btnChoice2 = new JButton("New button");
		btnChoice2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScene.getChoices().get(1).getNextSceneId();
	            currentScene = game.getScenes().findSceneById(nextId);
	            
	            updateSceneDisplay(); 
	            
	            if(currentScene.getSceneId() == 5) {
	            	checkWinCondiition();
	            }
				
			}

		});
		btnChoice2.setBackground(new Color(255, 255, 0));
		btnChoice2.setBounds(527, 156, 89, 59);
		contentPane.add(btnChoice2);
		
		userInventory = new JTextField();
		userInventory.setBounds(187, 464, 300, 33);
		contentPane.add(userInventory);
		userInventory.setColumns(10);
		
		txtItemsRoom = new JTextArea();
		txtItemsRoom.setBounds(10, 420, 167, 33);
		txtItemsRoom.setLineWrap(true);
		txtItemsRoom.setWrapStyleWord(true);
		txtItemsRoom.setEditable(false);
		contentPane.add(txtItemsRoom);
		txtItemsRoom.setColumns(10);
		
		JButton btnPickUpItem = new JButton("Pick up Item");
		btnPickUpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item itemInRoom = currentScene.getItem();
				
				if (itemInRoom != null) {
					game.getPlayer().addItem(itemInRoom);
					currentScene.removeItem();
					updateSceneDisplay();
				}
			}
		});
		btnPickUpItem.setBounds(10, 464, 167, 33);
		contentPane.add(btnPickUpItem);
		
		updateSceneDisplay();
	}
	

	private void checkWinCondiition() {
		boolean hasKeycard = game.getPlayer().hasItem("Keycard");
		boolean hasCodeNote = game.getPlayer().hasItem("Code Note");
		
		if (hasKeycard && hasCodeNote) {
			javax.swing.JOptionPane.showMessageDialog(this,
					"You used the keybard and the Code Note to unlock the exit. \n You escaped.You win!");
		} else {
			javax.swing.JOptionPane.showMessageDialog(this,
					"The exit will not open.\nYou are missing the required items.\nYou need the Keycard and the Code Note.");
		}
		
		btnChoice1.setEnabled(false);
		btnChoice2.setEnabled(false);
		//btnPickUpItem.setEnabled(false);
	}
	
	public void updateSceneDisplay() {
		//Scene scene = game.getCurrentScene();
		sceneDescription.setText(currentScene.getDescription());
		System.out.println(currentScene.getDescription());
		Item itemInRoom = currentScene.getItem();
		if(itemInRoom == null) 
			txtItemsRoom.setText("You find nothing");
		else 
			txtItemsRoom.setText(currentScene.getItem().toString());
		
		userInventory.setText(game.getPlayer().getInventoryText());
		
		ImageIcon icon = new ImageIcon(currentScene.getImagePath());
		Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		lblImage.setIcon(new ImageIcon(img));
		
		btnChoice1.setText("<html>" + currentScene.getChoices().get(0).getText() + "</html>");
		btnChoice2.setText("<html>" + currentScene.getChoices().get(1).getText() + "</html>");
		
		//btnPickup.setVisible(scene.getItem() != null);
		
		
	}
}
