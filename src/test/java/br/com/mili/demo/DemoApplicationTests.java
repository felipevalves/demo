package br.com.mili.demo;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.ZebraPrintException;
import fr.w3blog.zpl.model.element.ZebraBarCode128;
import fr.w3blog.zpl.model.element.ZebraBarCode39;
import fr.w3blog.zpl.model.element.ZebraText;
import fr.w3blog.zpl.utils.ZebraUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void print() throws ZebraPrintException {
		ZebraLabel zebraLabel = new ZebraLabel();
		zebraLabel.setDefaultZebraFont(ZebraFont.ZEBRA_ZERO);

		zebraLabel.addElement(new ZebraText(75, 50, "SACOS F. MILI BABY MEGA G", 10));
		zebraLabel.addElement(new ZebraText(75, 90, "CODIGO: 7.16.404   LOTE: 148303", 12));

		//Add Code Bar 39
//		zebraLabel.addElement(new ZebraBarCode39(150, 400, "1483031963", 200, 3, 3));
		zebraLabel.addElement(new ZebraBarCode128(200, 400, "1483031963", 200, 3, 3));

		zebraLabel.addElement(new ZebraText(420, 300, "WMS For Android", 6));
		zebraLabel.addElement(new ZebraText(420, 320, "07/03/2023 09:58", 6));

		String zplCode = zebraLabel.getZplCode();
		System.out.println(zplCode);

		//ZebraUtils.printZpl(zebraLabel, "10.2.4.32", 9100);

		printZpl(zplCode, "10.2.4.32", 9100, 1000 * 5);
	}

	public static void printZpl(String zpl, String ip, int port, int timeout) throws ZebraPrintException {
		try {
			Socket clientSocket = new Socket();

			try {
				InetSocketAddress address = new InetSocketAddress(ip, port);
				clientSocket.connect(address, timeout);
				clientSocket.setSoTimeout(timeout);
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				outToServer.writeUTF(zpl);
			} catch (Throwable var8) {
				try {
					clientSocket.close();
				} catch (Throwable var7) {
					var8.addSuppressed(var7);
				}

				throw var8;
			}

			clientSocket.close();
		} catch (SocketTimeoutException var9) {
			throw new ZebraPrintException("Cannot print label on this printer : " + ip + ":" + port + " due to timeout", var9);
		} catch (IOException var10) {
			throw new ZebraPrintException("Cannot print label on this printer : " + ip + ":" + port, var10);
		}
	}
}
