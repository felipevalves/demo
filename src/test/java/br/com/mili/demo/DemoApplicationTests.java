package br.com.mili.demo;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.ZebraPrintException;
import fr.w3blog.zpl.model.ZebraUtils;
import fr.w3blog.zpl.model.element.ZebraBarCode39;
import fr.w3blog.zpl.model.element.ZebraText;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
		zebraLabel.addElement(new ZebraText(75, 100, "CODIGO: 7.16.404   LOTE: 148303", 12));

		//Add Code Bar 39
		zebraLabel.addElement(new ZebraBarCode39(200, 220, "1483031963", 200, 3, 3));

		/*zebraLabel.addElement(new ZebraText(395, 85, "Camera", 14));

		zebraLabel.addElement(new ZebraText(10, 161, "CA201212AA", 14));



		zebraLabel.addElement(new ZebraText(10, 365, "Qté:", 11));
		zebraLabel.addElement(new ZebraText(180, 365, "3", 11));
		zebraLabel.addElement(new ZebraText(317, 365, "QA", 11));

		zebraLabel.addElement(new ZebraText(10, 520, "Ref log:", 11));
		zebraLabel.addElement(new ZebraText(180, 520, "0035", 11));
		zebraLabel.addElement(new ZebraText(10, 596, "Ref client:", 11));
		zebraLabel.addElement(new ZebraText(180, 599, "1234", 11));*/

		String zplCode = zebraLabel.getZplCode();
		System.out.println(zplCode);

//		ZebraUtils.printZpl(zebraLabel, "10.2.4.32", 9100);
	}
}
