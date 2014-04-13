import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;
import lejos.utility.DebugMessages;

/**
 * Управление роботом.
 * 
 * @author Andrei Murzin
 * @version 0.1
 */
public class Cube {
    /**
     * Код лицевой грани.
     */
    public static final int F = 1; 
    /**
     * Код правой грани.
     */
    public static final int R = 2;
    /**
     * Код задней грани.
     */
    public static final int B = 3;
    /**
     * Код левой грани.
     */
    public static final int L = 4;
    /**
     * Код верхней грани.
     */
    public static final int U = 5;
    /**
     * Код нижней грани.
     */
    public static final int D = 6;
    /**
     * 
     */
    public static final float d = (float) 0.015;
    public static final int WHITE = 6;
    public static final int GREEN = 1;
    Hand hand;
    Platform platform;
    AbstractCube abstractCube;
    ColorHand colorHand;
    
    public Cube(String handPort, String platformPort) {
        hand = new Hand(handPort);
        hand.setSpeed(170);
        platform = new Platform(platformPort);
        platform.setSpeed(170);
        abstractCube = new AbstractCube();
    }
    public Cube(String handPort, String platformPort, int speed) {
        hand = new Hand(handPort);
        hand.setSpeed(speed);
        platform = new Platform(platformPort);
        platform.setSpeed(170);
        abstractCube = new AbstractCube();
    }
    /**
     * 
     * @param handMotorPort	имя порта двигателя манипулятора
     * @param platformMotorPort имя порта двигателя платформы
     * @param colorHandMotorPort имя порта двигателя датчика цвета
     * @param sensorRGB имя порта датчика цвета
     * @param handSpeed начальное значение скорости двигателя манипулятора
     * @param platformSpeed начальное значение скорости двигателя платформы
     * @param colorHandSpeed начальное значение скорости двигателя датчика цвета
     */
    public Cube(String handMotorPort, String platformMotorPort, 
    		String colorHandMotorPort, String sensorRGB, int handSpeed, 
    		int platformSpeed, int colorHandSpeed) {
        hand = new Hand(handMotorPort);
        hand.setSpeed(handSpeed);
        platform = new Platform(platformMotorPort);
        platform.setSpeed(platformSpeed);
//        colorHand = new ColorHand(colorHandMotorPort, sensorRGB);
        abstractCube = new AbstractCube();
//        colorHand.moveOUT();
        hand.moveUp();
    }

    /**
     * Поворот куба вокруг горизонтальной оси (физическое перемещение и отслеживание).
     */
    public void flip() {
//    	colorHand.moveOUT();
        hand.flipCube();
        abstractCube.flip();
    }

    /**
     * Поворот куба целиком на 90 градусов (физическое перемещение и отслеживание).
     */
    public void rotate() {
//    	colorHand.moveOUT();
        hand.moveUp();
        platform.rotate(1);
        abstractCube.rotate();
    }
    /**
     * Поворот куба целиком на 90 градусов указанное число раз
     * (физическое перемещение и отслеживание).
     * 
     * @param times	число повортов на 90 градусов
     */
    public void rotateFace(int times) {
//    	colorHand.moveOUT();
        hand.moveDown();
        platform.rotate(times);
        hand.moveUp();
    }
    public void rotateFaceCC(int times) {
//    	colorHand.moveOUT();
        hand.moveDown();
        platform.rotateCС(times);
        hand.moveUp();
    }
    /**
     * Поворот грани куба по часовой стрелке (куб фиксируется маниплятором и
     * грань поворачивается платформой). До вызова метода грань может быть в любой
     * позиции. Перемещение в позицию 6 производится автоматически.
     * 
     * @param face имя поворачиваемой грани
     */
    public void rotateFaceClockwise(int face) {
        //if face is on left or right rotate cube 
        if (abstractCube.getFacePosition(face) == Cube.L || abstractCube.getFacePosition(face) == Cube.R) {
            rotate();
        }
        //flip cube wile face position not D
        while (abstractCube.getFacePosition(face) != Cube.D) {
            flip();
        }
        
//        rotateFace(3);
        rotateFaceCC(1);
        
    }
    /**
     * Поворот грани куба против часовой стрелки (куб фиксируется маниплятором и
     * грань поворачивается платформой). До вызова метода грань может быть в любой
     * позиции. Перемещение в позицию 6 производится автоматически.
     * 
     * @param face имя поворачиваемой грани
     */
    public void rotateFaceCounterClockwise(int face) {
        //if face is on left or right rotate cube 
        if (abstractCube.getFacePosition(face) == Cube.L || abstractCube.getFacePosition(face) == Cube.R) {
            rotate();
        }
        //flip cube wile face position not D
        while (abstractCube.getFacePosition(face) != Cube.D) {
            flip();
        }
        
        rotateFace(1);
    } 
    /**
     * Поворот грани куба на 180 градусов (куб фиксируется маниплятором и
     * грань поворачивается платформой). До вызова метода грань может быть в любой
     * позиции. Перемещение в позицию 6 производится автоматически.
     * 
     * @param face имя поворачиваемой грани
     */
    public void rotateFace180(int face) {
        //if face is on left or right rotate cube 
        if (abstractCube.getFacePosition(face) == Cube.L || abstractCube.getFacePosition(face) == Cube.R) {
            rotate();
        }
        //flip cube wile face position not D
        while (abstractCube.getFacePosition(face) != Cube.D) {
            flip();
        }
    
        rotateFace(2);
    }
    
//    /**
//     * Сканировать начальную ориентацию физического куба. Читается цвет
//     * центральных элементов каждой грани и если это белый или зеленый, то
//     * сохраняется в абстрактном кубе.
//     */
//    public void storeCubeOrientation() {
//    	int colorID = 0;
//    	abstractCube.storeOrientation();
//    	for (int i = 0; i < 4; i++) {
//    		colorHand.moveIN();
//
//    		colorID = colorHand.getColorID();
//    		LCD.clear();
//    		LCD.drawString("ColorID = " + colorID, 1, 1);
//    		LCD.refresh();
//    		if ((colorID == WHITE) || (colorID == GREEN)) {
//    			abstractCube.setFaceColorID(colorID, B);
//
//    		}
//    		colorHand.moveOUT();
//    		rotate();
//    	}
//    	flip();
//    	colorHand.moveIN();
//
//    	colorID = colorHand.getColorID();
//		LCD.clear();
//		LCD.drawString("ColorID = " + colorID, 1, 1);
//		LCD.refresh();
//		if ((colorID == WHITE) || (colorID == GREEN)) {
//			abstractCube.setFaceColorID(colorID, B);
//		}
//    	flip();
//    	flip();
//    	colorHand.moveIN();
//    	colorID = colorHand.getColorID();
//		LCD.clear();
//		LCD.drawString("ColorID = " + colorID, 1, 1);
//		LCD.refresh();
//
//		if ((colorID == WHITE) || (colorID == GREEN)) {
//			abstractCube.setFaceColorID(colorID, B);
//
//		}
//		flip();
//		//после этого куб вернулся в исходную позицию
//		//и двум граням виртуального куба присвоены цвета WHITE & GREEN
//    }
//    
//    public boolean restoreCubeOrientation() {
//    	boolean exit = false;
//    	int whiteFace = 0, greenFace = 0;
//    	int colorID = 0;
//    	for (int j = 0; j < 4; j++) {
//    		colorHand.moveIN();
//    		colorID = colorHand.getColorID();
//    		if ((colorID == WHITE) || (colorID == GREEN)) {
//    			for (int i = 1; i <=6; i++) {
//    				if (abstractCube.getFaceColorID(i) == colorID) {
//    					abstractCube.setFacePosition(i);
//    					if (colorID == WHITE) {
//    						whiteFace = i;
//    					} else {
//    						greenFace = i;
//    					}
//    				}
//    			}
//    		}
//    		rotate();
//    	}
//    	flip();
//		colorHand.moveIN();
//		colorID = colorHand.getColorID();
//		if ((colorID == WHITE) || (colorID == GREEN)) {
//			for (int i = 1; i <=6; i++) {
//				if (abstractCube.getFaceColorID(i) == colorID) {
//					abstractCube.setFacePosition(i);
//					if (colorID == WHITE) {
//						whiteFace = i;
//					} else {
//						greenFace = i;
//					}
//				}
//			}
//		}
//		flip();
//		flip();
//		colorHand.moveIN();
//		colorID = colorHand.getColorID();
//		if ((colorID == WHITE) || (colorID == GREEN)) {
//			for (int i = 1; i <=6; i++) {
//				if (abstractCube.getFaceColorID(i) == colorID) {
//					abstractCube.setFacePosition(i);
//					if (colorID == WHITE) {
//						whiteFace = i;
//					} else {
//						greenFace = i;
//					}
//				}
//			}
//		}
//		
//		//теперь вращаем физический куб через все позиции (32), пока 
//		//местоположение граней whiteFace и greenFace не совпадет с сохраненным
//		do {
//			rotate();
//			flip();
//			flip();
//			for (int i = 0; i < 4; i++) { 
//				for (int j = 0; j < 4; j++) {
//					if (abstractCube.getFacePosition(whiteFace) == abstractCube.getStoredFacePosition(whiteFace)
//							&& abstractCube.getFacePosition(greenFace) == abstractCube.getStoredFacePosition(greenFace)) {
//						exit = true;
//						break;
//					}
//					rotate();
//				}
//				if (exit) break;
//				flip();
//			}
//		} while (!exit);
//		
//		abstractCube.restoreOrientation();
//    	return true;
//    }
//    
// 
//    public void debugCubeOrientation() {
//    	DebugMessages log = new DebugMessages();
//    	for (int i = 0; i < 6; i++) {
//    		log.clear();
//    		float[] sRGB;
//    		sRGB = abstractCube.getFaceColor(i + 1);
//    		LCD.clear();
//    		LCD.drawString("" + sRGB[0], 0, 0);
//    		LCD.drawString("" + sRGB[1], 0, 1);
//    		LCD.drawString("" + sRGB[2], 0, 2);
//    		LCD.drawString("" + sRGB[3], 0, 3);
//    		LCD.drawString("" + sRGB[4], 0, 4);
//    		LCD.drawString("" + sRGB[5], 0, 5);
//    		LCD.refresh();
//
//            if (Button.waitForAnyPress() == Button.ID_ESCAPE) {
//                return;
//            }
//    	}
//    }
    public void close() {
    	hand.close();
    	platform.close();
    }
}
