import lejos.hardware.lcd.LCD;

/**
 * Выполнение заданной цепочки команд для решения пасьянса (предполагается, что
 * начальное состояние куба - собранное).
 * 
 * @author Andrei Murzin
 * @version 0.1
 */
public class MakeSolution {
	/**
	 * 
	 */
	public static final int F = 1; 
	public static final int R = 2;
	public static final int B = 3;
	public static final int L = 4;
	public static final int U = 5;
	public static final int D = 6;
	public static final int FC = 7; 
	public static final int RC = 8;
	public static final int BC = 9;
	public static final int LC = 10;
	public static final int UC = 11;
	public static final int DC = 12;
	public static final int F2 = 13; 
	public static final int R2 = 14;
	public static final int B2 = 15;
	public static final int L2 = 16;
	public static final int U2 = 17;
	public static final int D2 = 18;
	Cube cube;
	int[] sequence;
	/**
	 * 
	 * @param cube экземпляр класса Cube
	 */
	public MakeSolution(Cube cube) {
		this.cube = cube;
	}
	
	/**
	 * Установка последовательности команд пасьянса в виде массива кодов команд
	 * (см. кодирование команд в описании констант).
	 * 
	 * @param sequence	массив целых чисел кодов команд
	 */
	public void setSequence(int[] sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * Инвертировать последовательность команд (обратный порядок выполнения и
	 * замена вращений на противоположные).
	 */
	public void invertSequence() {
		if (sequence == null) return;
		int[] temp = new int[sequence.length];
		for (int i = 0; i < sequence.length; i++) {
			temp[sequence.length - i - 1] = sequence[i];
			if (sequence[i] < 7) {
				temp[sequence.length - i - 1] = temp[sequence.length - i - 1] + 6;
			}
			if (sequence[i] > 6 && sequence[i] < 13) {
				temp[sequence.length - i - 1] = temp[sequence.length - i - 1] - 6;
			}
		}
		sequence = temp;
		temp = null;
	}
	/**
	 * Выполнить решение. В соответствии с массивом команд выполняются вращения куба.
	 */
	public void makeSequence() {
		if (sequence == null) return;
		
		for (int i = 0; i < sequence.length; i++) {
			switch (sequence[i]) {
			case F: cube.rotateFaceClockwise(Cube.F);
				break;
			case R: cube.rotateFaceClockwise(Cube.R);
			break;
			case B: cube.rotateFaceClockwise(Cube.B);
			break;
			case L: cube.rotateFaceClockwise(Cube.L);
			break;
			case U: cube.rotateFaceClockwise(Cube.U);
			break;
			case D: cube.rotateFaceClockwise(Cube.D);
			break;
			case FC: cube.rotateFaceCounterClockwise(Cube.F);
			break;
			case RC: cube.rotateFaceCounterClockwise(Cube.R);
			break;
			case BC: cube.rotateFaceCounterClockwise(Cube.B);
			break;
			case LC: cube.rotateFaceCounterClockwise(Cube.L);
			break;
			case UC: cube.rotateFaceCounterClockwise(Cube.U);
			break;
			case DC: cube.rotateFaceCounterClockwise(Cube.D);
			break;
			case F2: cube.rotateFace180(Cube.F);
			break;
			case R2: cube.rotateFace180(Cube.R);
			break;
			case B2: cube.rotateFace180(Cube.B);
			break;
			case L2: cube.rotateFace180(Cube.L);
			break;
			case U2: cube.rotateFace180(Cube.U);
			break;
			case D2: cube.rotateFace180(Cube.D);
			break;
			default:
				break;
			}
		}
		
	}
	public void printSequence(int x, int y) {
		if (sequence == null) {
			LCD.clear();
			LCD.drawString("No sequence found", x, y);
			return;
		}
		String string = "";
		for (int i = 0; i < sequence.length; i++) {
			string += ":" + sequence[i];
		}
		LCD.drawString(string, x, y);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		Cube cube = new  Cube("B", "C");
//		MakeSolution solution = new MakeSolution(cube);
//		int[] sequence = {MakeSolution.F, 
//				MakeSolution.R, 
//				MakeSolution.B, 
//				MakeSolution.L, 
//				MakeSolution.U, 
//				MakeSolution.D};
//		solution.setSequence(sequence);
//		solution.printSequence();
//		solution.invertSequence();
//		solution.printSequence();
		

	}

}
