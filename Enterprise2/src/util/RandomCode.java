package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


public class RandomCode {
	private static final long serialVersionUID = 1L;

	private static int WIDTH = 70;

	private static int HEIGHT = 40;

	public ValidateCode getValdateCode() {
		ValidateCode vc = new ValidateCode();
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		char[] rands = generateCheckCode();
		drawBackground(g);
		drawRands(g, rands);
		g.dispose();
		
		vc.setCode(new String(rands));
		vc.setImage(image);
		return vc;
	}

	private void drawBackground(Graphics g) {
		g.setColor(new Color(0xffffff));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for (int i = 0; i < 500; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
	}

	private void drawRands(Graphics g, char[] rands) {
		// g.setColor(Color.BLUE);
		Random random = new Random();
		int red = random.nextInt(110);
		int green = random.nextInt(50);
		int blue = random.nextInt(50);
		g.setColor(new Color(red, green, blue));
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 22));
		g.drawString("" + rands[0], 1, 27);
		g.drawString("" + rands[1], 16, 25);
		g.drawString("" + rands[2], 31, 28);
		g.drawString("" + rands[3], 46, 26);
	}

	private char[] generateCheckCode() {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * 36);
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}
}
