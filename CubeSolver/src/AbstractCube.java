import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

/**
 * Отслеживание перемещений граней при поворотах куба (являются содержимым массива и маркируются цифрами
 * по принципу: 1-F, 2-R, 3-B, 4-L, 5-U, 6-D). 
 * Куб вращается внутри неподвижного куба, связанного с роботом 
 * (номера граней явлются индексами массива, увеличенными на единицу, и 
 * маркируются по тому же принципцу, грань 6-D соотв. платформе). 
 * Первоначально кубы совпадают. По мере вращения соответствие изменяется. Методы
 * класса не приводят к физическому перемещению элементов робота и должны использоваться
 * совместно с методами классов Hand, Platform.
 * @see Hand, Platform
 * 
 * @author Andrei Murzin
 * @version 0.1
 */
public class AbstractCube {

    private int[] orientation = {1, 2, 3, 4, 5, 6}; //mean F R B L U D
    private int[] storedOrientation = {1, 2, 3, 4, 5, 6};
    private float[][] faceColors = new float[6][6];
    private int[] faceColorsID = new int[6];
    
    public AbstractCube() {

    }
    /**
     * Свободное вращение куба вокруг вертикальной оси.
     */
    public void rotate() {
        int temp;
        temp = orientation[0];
        orientation[0] = orientation[1];
        orientation[1] = orientation[2];
        orientation[2] = orientation[3];
        orientation[3] = temp;
    }
    /**
     * Свободное вращение куба вокруг горизонтальной оси.
     */
    public void flip() {
        int temp;
        temp = orientation[0];
        orientation[0] = orientation[4];
        orientation[4] = orientation[2];
        orientation[2] = orientation[5];
        orientation[5] = temp;
    }
    /**
     * Получить номер грани виртуального куба, лежащей в текущий момент
     * на платформе (т.е. той, что находится в позиции 6 фиксированного куба).
     *  
     * @return
     */
    public int getMovableFace() {
        return orientation[5];
    }
    
    /**
     * Получить текущую позиции указнной грани.
     * @param face	номер искомой грани (1-F, 2-R, 3-B, 4-L, 5-U, 6-D)
     * @return номер текущй позиции (т.е. номе грани неподвижного куба, в которой
     * находится искомая грань подвижного куба). Ноль, если не найдено.
     */
    public int getFacePosition(int face) {
        int result = -1;
        for (int i = 0; i < 6; i++) {
            if (orientation[i] == face) {
                result = i;
            }
        }
        return result + 1;
    }
    /**
     * Получить номер сохраненной физической позиции указанной грани 
     * виртуального куба. Т.е. узнать где находилась в момент сохранения 
     * указанная грань. 
     * @param face номер грани виртуального куба.
     * @return номер грани физического куба.
     */
    public int getStoredFacePosition(int face) {
        int result = -1;
        for (int i = 0; i < 6; i++) {
            if (storedOrientation[i] == face) {
                result = i;
            }
        }
        return result + 1;
    }
    /**
     * Установить цвет для грани виртуального куба, 
     * которая находится на текущий момент в позиции напротив датчика.
     * @param statRGB - массив значений цвета и доверительных интервалов
     * в формате R,dR,G,dG,B,dB
     * @param realFace - номер грани физического куба (зависит от расположения 
     * манипулятора с датчиком, не соответствует грани виртуального куба)
     */
    public void setFaceColor(float[] statRGB, int realFace) {
    			faceColors[orientation[realFace - 1] - 1] = statRGB;
    }
    
    /**
     * Установить цвет для грани виртуального куба, 
     * которая находится на текущий момент в позиции напротив датчика.
     * @param colorID - номер цвета из класса Color
     * @param realFace - номер грани физического куба (зависит от расположения 
     * манипулятора с датчиком, не соответствует грани виртуального куба)
     */
    public void setFaceColorID(int colorID, int realFace) {
    	faceColorsID[orientation[realFace - 1] - 1] = colorID;
    }
    
    /**
     * Получить цвет указанной грани виртуального куба.
     * @param face номер грани виртуального куба
     * @return массив float в формате R,dR,G,dG,B,dB
     */
    public float[] getFaceColor(int face) {
    	return faceColors[face - 1];
    }
    
    /**
     * Получить цвет указанной грани виртуального куба.
     * @param face номер грани виртуального куба
     * @return номер цвета из класса Color
     */
    public int getFaceColorID(int face) {
    	return faceColorsID[face - 1];
    }
    
    /**
     * Сохранить текущую ориентацию виртуального куба для последующего
     * использования.
     */
    public void storeOrientation() {
    	for (int i = 0; i < 6; i++) {
    		storedOrientation[i] = orientation[i];
    	}
    }
    
    public void restoreOrientation() {
    	for (int i = 0; i < 6; i++) {
    		orientation[i] = storedOrientation[i];
    	}
    }
    /**
     * Установка номера грани виртуального куба, которая находится на текущий
     * момент в позиции В (напротив датчика цвета) физического куба номера 
     * противоположной грани (1-3, 2-4, 5-6)
     * @param face номер грани виртуального куба
     */
    public void setFacePosition(int face) {
    	orientation[2] = face;
    	switch (face) {
		case 1: orientation[0] = 3;
		break;
		case 2: orientation[0] = 4;
		break;
		case 5: orientation[0] = 6;
		break;
		case 3: orientation[0] = 1;
		break;
		case 4: orientation[0] = 2;
		break;
		case 6: orientation[0] = 5;
		break;
		default:
			break;
		}
     }
    
    public boolean checkOrientation() {
        LCD.clear();
        for (int i = 0; i < 6; i++) {
        	LCD.drawString(":" + orientation[i], 1, i);
        }
    	return false;
    }

}
