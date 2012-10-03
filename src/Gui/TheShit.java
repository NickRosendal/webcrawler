package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;

import org.joda.time.LocalDate;

import testArea.SendCommandsToBrowser;
import domain.MusicNumber;

public class TheShit
{
	private domain.Website localWebsite;
	
	public TheShit(domain.Website localWebsite){
		this.localWebsite = localWebsite;
		
		
	}
	

	public static class ButtonCellEditor extends AbstractCellEditor implements TreeCellEditor, ActionListener, MouseListener
	{
		private void setButtonsVisible(boolean condition)
		{
			youtube.setVisible(condition);
			google.setVisible(condition);
			mp3skull.setVisible(condition);
			torrent.setVisible(condition);
		}
		private void setupButtonLayout(){
			
		}

		private static final long serialVersionUID = 1L;
		private JButton youtube;
		private JButton google;
		private JButton mp3skull;
		private JButton torrent;
		private JLabel label;
		private JPanel panel;
		private Object value;
		private JPanel panel1;
		public ButtonCellEditor()
		{
			

			panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			// panel.setBounds(5, 5, 200, 20);
			youtube = new JButton(new ImageIcon(getClass().getResource("icons/YouTube.gif")));
			youtube.setPreferredSize(new Dimension(20, 20));
			youtube.setOpaque(false);
			youtube.setContentAreaFilled(false);
			youtube.setBorderPainted(false);
			youtube.addActionListener(this);
			youtube.setActionCommand("1");
			
			google = new JButton(new ImageIcon(getClass().getResource("icons/Google.gif")));
			google.setPreferredSize(new Dimension(20, 20));
			google.setOpaque(false);
			google.setContentAreaFilled(false);
			google.setBorderPainted(false);
			google.addActionListener(this);
			google.setActionCommand("2");
			
			mp3skull = new JButton(new ImageIcon(getClass().getResource("icons/mp3Skull.gif")));
			mp3skull.setPreferredSize(new Dimension(20, 20));
			mp3skull.setOpaque(false);
			mp3skull.setContentAreaFilled(false);
			mp3skull.setBorderPainted(false);
			mp3skull.addActionListener(this);
			mp3skull.setActionCommand("3");
			
			torrent = new JButton(new ImageIcon(getClass().getResource("icons/torrentscan.gif")));
			torrent.setPreferredSize(new Dimension(20, 20));
			torrent.setOpaque(false);
			torrent.setContentAreaFilled(false);
			torrent.setBorderPainted(false);
			torrent.addActionListener(this);
			torrent.setActionCommand("4");
			
			label = new JLabel();
			label.setPreferredSize(new Dimension(300,20));
			
		//	label.setHorizontalAlignment(label.Right);
			panel.add(label);
			panel.add(youtube);
			panel.add(google);

			panel.add(mp3skull);
			panel.add(torrent);
			panel.setPreferredSize(new Dimension(450,20));
		}

		@Override
		public Object getCellEditorValue()
		{

			String temp = label.getText();
			boolean isAsong = !temp.matches("\\d.*-\\d.*-\\d.*");
			if (isAsong)
			{
				temp.indexOf('-');
				String artist = temp.substring(0, temp.indexOf('-')).trim();
				String musicNumber = temp.substring(temp.indexOf('-') + 2, temp.length()).trim();
				return (new MusicNumber(artist, musicNumber));
			} else
			{
				LocalDate per = new LocalDate(temp);
				return per;
			}
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{

			String val = label.getText(); // <---- DEN TEXT DER ER På JLABLEN
											// ....
			// String val = "LILLE MUS";
			val = val.replaceAll(" ", "+"); // MAN M� IKKE HAVE SPACE I EN URL:/
											// men + er det samme som mellemrum
											// s� den sku v�re fjong...
			
			// String source = e.getSource().toString();

			JButton myButton = (JButton) e.getSource();
			int switchCase = Integer.parseInt(myButton.getActionCommand());

			SendCommandsToBrowser mySendCommandsToBrowser = new SendCommandsToBrowser();

			switch (switchCase)
			{
			case 1: // go to browser youtube /* DER VAR �ndret til google her os
					// :S?! */
				val = val.replaceAll("&", "%26");
				mySendCommandsToBrowser.staticSend("http://www.youtube.com/results?search_query=" + val);
				break;
			case 2: // go to browser google
				val = val.replaceAll("&", "%26");
				mySendCommandsToBrowser.staticSend("http://www.google.com/search?q=" + val + "+320+kbps");
				break;
			case 3: // go to browser mp3skull
				mySendCommandsToBrowser.staticSend("http://mp3skull.com/search.php?q=" + val);
				break;
			case 4: // go to browser torrent
				mySendCommandsToBrowser.staticSend("http://tscan.eu/#!/search/"+val+"/engine/kickasstorrents");
			//	mySendCommandsToBrowser.staticSend("http://tscan.eu/#!/"+val+"/engine/kickasstorrents");
			//	mySendCommandsToBrowser.staticSend("http://tscan.eu/redirect.php?site_id=41&keyword=" + val + "&category_id=0");

				break;

			}

			stopCellEditing();
		}

		@Override
		public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row)
		{
			// this.value = value;
			// label.setText(value.toString());

			Object inObj = ((DefaultMutableTreeNode) value).getUserObject();

			// label.setText(((MusicNumber) inObj).getArtist() + " - " +
			// ((MusicNumber) inObj).getTitle());

			if (inObj instanceof MusicNumber)
			{
				label.setText(((MusicNumber) inObj).getArtist() + " - " + ((MusicNumber) inObj).getTitle());
				setButtonsVisible(true);

			} else if (inObj instanceof LocalDate)
			{
				label.setText("" + value);
				setButtonsVisible(false);

			}
			
			return panel;

		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			stopCellEditing();
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
		}

	}

	public static class ButtonCellRenderer extends JPanel implements TreeCellRenderer
	{

		JLabel label;
		JButton youtube;
		JButton google;
		JButton mp3skull;
		JButton torrent;

		ButtonCellRenderer()
		{
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			label = new JLabel();
			label.setPreferredSize(new Dimension(300,20));
			add(label);

			youtube = new JButton(new ImageIcon(getClass().getResource("icons/YouTube.gif")));
			youtube.setPreferredSize(new Dimension(20, 20));
			youtube.setOpaque(false);
			youtube.setContentAreaFilled(false);
			youtube.setBorderPainted(false);
			google = new JButton(new ImageIcon(getClass().getResource("icons/Google.gif")));
			google.setPreferredSize(new Dimension(20, 20));
			google.setOpaque(false);
			google.setContentAreaFilled(false);
			google.setBorderPainted(false);

			mp3skull = new JButton(new ImageIcon(getClass().getResource("icons/mp3Skull.gif")));
			mp3skull.setPreferredSize(new Dimension(20, 20));
			mp3skull.setOpaque(false);
			mp3skull.setContentAreaFilled(false);
			mp3skull.setBorderPainted(false);

			torrent = new JButton(new ImageIcon(getClass().getResource("icons/torrentscan.gif")));
			torrent.setPreferredSize(new Dimension(20, 20));
			torrent.setOpaque(false);
			torrent.setContentAreaFilled(false);
			torrent.setBorderPainted(false);
			
			this.add(youtube);
			this.add(google);
			this.add(mp3skull);
			this.add(torrent);
			this.setPreferredSize(new Dimension(450,20));

		}

		private void setButtonsVisible(boolean condition)
		{
			youtube.setVisible(condition);
			google.setVisible(condition);
			mp3skull.setVisible(condition);
			torrent.setVisible(condition);
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
		{

			Object inObj = ((DefaultMutableTreeNode) value).getUserObject();
			label.setText(value.toString());
			if (inObj instanceof LocalDate)
			{
				setButtonsVisible(false);
			} else if (inObj instanceof MusicNumber)
			{
				label.setText(((MusicNumber) inObj).getArtist().trim() + " - " + ((MusicNumber) inObj).getTitle().trim());
				setButtonsVisible(true);

			}

			return this;
		}

	}
	public void expand7Days(JTree tree, int counter){	
		
		if(counter == 0)
		     return;
		else
		       {
		       tree.expandRow(counter);
		       expand7Days(tree, --counter);
		       return;
		       }
		}
	
	public JPanel drawGui()
	{

		DefaultMutableTreeNode root = null;

		//navigablemap da vi så kan bruge descendingmap, til at vende det om
		NavigableMap<LocalDate,Set<MusicNumber>> myTreeMap = localWebsite.getDays();
		
		root = new DefaultMutableTreeNode("hidden");// ADD HIDDEN TOP ROOT
		

		myTreeMap = myTreeMap.descendingMap();
		for (Map.Entry<LocalDate, Set<MusicNumber>> entry : myTreeMap.entrySet())
			
		{// ADD DAYS
			LocalDate key = entry.getKey();
			Set<MusicNumber> value = entry.getValue();

			Set<MusicNumber> mySet = entry.getValue();
			Iterator itr = mySet.iterator();

			DefaultMutableTreeNode Day = new DefaultMutableTreeNode(key);
			root.add(Day);
			while (itr.hasNext())
			{ // ADD SONGS
				MusicNumber myMusicNumer = (MusicNumber) itr.next();

				DefaultMutableTreeNode MusicNumber = new DefaultMutableTreeNode(myMusicNumer);

				Day.add(MusicNumber);

			}
		}

		JTree tree = new JTree(root);
		expand7Days(tree, 7);
   
        tree.setOpaque(false);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setEditable(true);
		ButtonCellRenderer myButtonCellRenderer = new ButtonCellRenderer();
		tree.setCellRenderer(myButtonCellRenderer);

		tree.setCellEditor(new ButtonCellEditor());
		
		JScrollPane myPane = new JScrollPane(tree);
		

		JPanel treePane = new JPanel();
		treePane.setBorder(new EmptyBorder(0, 0, 0, 0));
		treePane.setLayout(new BorderLayout(0, 0));
		myPane.setOpaque(false);
//		//---------------banner------------- 		
//		try {
//	        BufferedImage image = ImageIO.read(new File("src/Gui/icons/musicbackground.jpg"));
//	        ImageResize resizedImage = new ImageResize(image);
//	        
//	        myPane.add(resizedImage, BorderLayout.CENTER);
//	        resizedImage.setBackground(Color.BLACK);
//	        resizedImage.setLayout(new BorderLayout());
//	        resizedImage.setOpaque(false);
//	        
//			
//		} catch (IOException e) {
//		}		
////-------------------slut banner------------
		treePane.add(myPane);
		tree.setOpaque(false);	
		

		


		TreeMap<LocalDate, Set<MusicNumber>> dayArtistNumberMap = localWebsite.getDayArtistNumberMap();

		for (LocalDate folderName : dayArtistNumberMap.keySet())
		{
			// Create a node for the folder

			DefaultMutableTreeNode folder = new DefaultMutableTreeNode(folderName, true);

			root.add(folder);
		}
		return treePane;
	}
}
