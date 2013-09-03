package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Website;

import observer.interfaces.Observer;

import startup.Start;
import javax.swing.JLabel;

public class MainGui extends JFrame implements Observer {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel VoicePanel;
	private JLabel responselabel;

	public MainGui() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/Gui/icons/16x16_agt_mp3.png")));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(95, 0, 500, 900);
		contentPane = new JPanel();
		// contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.lightGray);
		setContentPane(contentPane);
		this.setVisible(true);

		JPanel panelbutton = new JPanel();
		panelbutton.setOpaque(false);
		contentPane.add(panelbutton, BorderLayout.NORTH);
		panelbutton.setLayout(new BorderLayout(0, 0));

		JButton btnUpdate = new JButton("");
		btnUpdate.setOpaque(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setFocusPainted(false);
		btnUpdate.setToolTipText("Update list of songs");
		panelbutton.add(btnUpdate, BorderLayout.WEST);
		btnUpdate.setHorizontalTextPosition(SwingConstants.LEADING);
		btnUpdate.setIcon(new ImageIcon(MainGui.class
				.getResource("/Gui/icons/update.png")));
		btnUpdate.setPreferredSize(new Dimension(37, 37));

		responselabel = new JLabel("");
		panelbutton.add(responselabel, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setOpaque(false);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		// TheVoice
		for (Website myWebsite : Start.websitelist) {
			JPanel myPanel = new TheShit(myWebsite).drawGui();
		//	String inconpath = myWebsite.getMyInformation().getIconPath();
			// tabbedPane.addTab(myWebsite.getWebsiteTitle()).getIcon(getClass().getResource(inconpath)),
			// myPanel, null);
			//tabbedPane.addTab(myWebsite.getWebsiteTitle(), new ImageIcon(
			//		getClass().getResource(inconpath)), myPanel, null);
			tabbedPane.addTab(myWebsite.getWebsiteTitle(), new ImageIcon(myWebsite.getMyInformation().getIconPath()), myPanel, null);

		}
		// VoicePanel = new TheShit(Start.theVoice).drawGui();
		// tabbedPane.addTab("The Voice", new
		// ImageIcon(getClass().getResource("icons/theVoice.gif")), VoicePanel,
		// null);
		// // NRG
		//
		// JPanel NrjPanel = new TheShit(Start.Nrj).drawGui();
		// tabbedPane.addTab("NRJGermany", new
		// ImageIcon(getClass().getResource("icons/nrj.gif")), NrjPanel, null);

		// btnUpdate actionListener..
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				System.out.println("i can listen?!");
				int index = tabbedPane.getSelectedIndex();
				switch (index) {
				case 0: // voice
					System.out.println("THE VOICE");
					Start.theVoice.update();
					System.out.println("that dude just started a new thread!");
					break;
				case 1: // NRJ
					System.out.println("NRJGermany");
					Start.Nrj.update();
					break;
				case 2: // DR P3
					System.out.println("DR P3");
					Start.dRP3Website.update();
				}
			}
		});
	}

	@Override
	public void update(String event) {
		boolean itIsAWebSite = false;
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			if (tabbedPane.getTitleAt(i).equals(event)) {
				itIsAWebSite = true;
				JPanel myPan = (JPanel) tabbedPane.getComponentAt(i);
				myPan.removeAll();

				for (Website myWebsite : Start.websitelist) {
					if (myWebsite.getWebsiteTitle().equals(event)) {
						myPan.add(new TheShit(myWebsite).drawGui());
					}
				}
				tabbedPane.updateUI();
			}

		}
		if (!itIsAWebSite) {
			responselabel.setText(event);
		}
	};
}
