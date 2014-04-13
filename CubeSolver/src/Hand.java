import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;

/**
 * Управление манипулятором (вращение куба вокруг
 * горизонтальной оси и фиксация куба для поворота грани).
 * 
 * @author Andrei Murzin
 * @version 0.1
 */
public class Hand {
	private EV3LargeRegulatedMotor motor;
	/**
	 * Признак - манипулятор поднят.
	 */
	public static final int UP = 0; //hand is starting point
	/**
	 * Признак - манипулятор опущен (куб фиксирован).
	 */
	public static final int DOWN = 1; //hand on cube
	private int state = UP; //current hand state
	/**
	 * 
	 * @param port указатель на порт управляющего блока EV3, в который включен 
	 * двигатель ("A", "B", "C" или "D")
	 */
	public Hand (String port) {
		motor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(port));	
		motor.setSpeed(150);
        motor.backward();
        while (!motor.isStalled()) {
        }
        motor.stop();
		state = UP;
	}
	/**
	 * Изменение скорости вращения двигателя (градусы/сек). 
	 *  
	 * @param speed	скорость градусы/сек. Максимальное значение 
	 * 100 x (напряжение батареи под нагрузкой)
	 */
	public void setSpeed(int speed) {
		motor.setSpeed(speed);
	}
	/**
	 * Поднять манипулятор.
	 */
	public void moveUp() {
		if (state == DOWN) {
			motor.rotate(-100);
			state = UP;
		}
	}
	/**
	 * Опустить манипулятор (зафиксировать куб для вращения грани)
	 */
	public void moveDown() {
		if (state == UP) {
			motor.rotate(100);
			state = DOWN;
		}
	}
	/**
	 * Перевернуть куб вокруг горизотальной оси.
	 */
	public void flipCube() {
		if (state == UP) {
			motor.rotate(210);
			motor.rotate(-210);
		}
		if (state == DOWN) {
			motor.rotate(110);
			motor.rotate(-110);
		}
	}
	/**
	 * Освободить ресурсы, занятые классом двигателя.
	 */
	public void close() {
		motor.close();
	}

}
