package com.deertt.base.verify;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;


public class VerifyCodeBuilder {
	// 备选字
	String base = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
	// 备选字体
	String[] fontTypes = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
	// 创建随机类的实例
	Random random = new Random();
	// 图片的长宽
	int width = 90;
	int height = 30;
	// 验证码字符数
	int limitLength = 4;
	// 生成的验证码字符串
	String sRand = "";
	
	public String getsRand() {
		return sRand;
	}

	public void setsRand(String sRand) {
		this.sRand = sRand;
	}

	public VerifyCodeBuilder() {
		sRand = getRandText(limitLength);
	}

	public BufferedImage buildVerifyCodeImage(){

		// 创建内存图像
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 设定图像背景色(因为是做背景，所以偏淡)
		g.setColor(getRandColor(random, 200, 250));
		g.fillRect(0, 0, width, height);

		// 在图片背景上增加噪点
		g.setColor(getRandColor(random, 160, 200));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		for (int i = 0; i < 6; i++) {
			g.drawString("***********************************", 0, 5 * (i + 2));
		}

		// 取随机产生的认证码(6个字)
		for (int i = 0; i < limitLength; i++) {

			// 设置字体的颜色
			g.setColor(getRandColor(random, 10, 150));
			// 设置字体
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], Font.BOLD, 18 + random.nextInt(4)));
			// 将此字画到图片上
			g.drawString(sRand.substring(i, i + 1), 20 * i + 5 + random.nextInt(4), 20);
		}
		g.dispose();
		return image;
	}

	// 生成随机颜色
	public Color getRandColor(Random random, int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	// 生成随机的验证码
	public String getRandText(int length) {
		String result = "";
		for (int i = 0; i < length; i++) {
			int start = random.nextInt(base.length());
			String rand = base.substring(start, start + 1);
			if (result.length() < length) {
				result += rand;
			}
		}
		return result;
	}
}
