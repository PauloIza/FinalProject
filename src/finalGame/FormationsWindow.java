package finalGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

public class FormationsWindow extends JDialog{
	private String s;
	private BufferedImage formation442, formation532;
	public JButton formation442Button, formation532Button;
	public Game game;

	public FormationsWindow() {
		Path currentRelativePath = Paths.get("");
		s = currentRelativePath.toAbsolutePath().toString();

		setTitle("Formation Chooser");
		setSize(600, 450);
		setLayout(new GridLayout(0, 1));
		iconButtons();
		
		add(formation442Button);
		add(formation532Button);
		
	}
	
	public void iconButtons() {
		try {
			formation442 = ImageIO.read(new File(s+"//442Formation.JPG"));
			formation442Button = new JButton(new ImageIcon(formation442));
			formation442Button.setBorder(BorderFactory.createEmptyBorder());
			formation442Button.setContentAreaFilled(false);
			formation442Button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					game.setFormationFile(s+"//442Formation.csv");
					setVisible(false);
				}
			});
			
			formation532 = ImageIO.read(new File(s+"//532Formation.JPG"));
			formation532Button = new JButton(new ImageIcon(formation532));
			formation532Button.setBorder(BorderFactory.createEmptyBorder());
			formation532Button.setContentAreaFilled(false);
			formation532Button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					game.setFormationFile(s+"//532Formation.csv");
					setVisible(false);
				}
			});
			
		} catch (IOException e) {
			System.out.println("One of the formation file images was incorrect.");
		}
		
	}
}
