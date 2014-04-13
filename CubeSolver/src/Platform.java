import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * Управление горизонтальной поворотной платформой (вращение куба вокруг
 * вертикальной оси).
 * 
 * @author Andrei Murzin
 * @version 0.1
 *
 */
public class Platform {

	private EV3LargeRegulatedMotor motor;
	/**
	 * Передаточное число привода поворотной платформы.
	 */
	public static final int GEAR_RATIO = 3;
	private static final int DOVOROT = 20; 
	/**
	 * 
	 * @param port 	указатель на порт управляющего блока EV3, в который включен 
	 * двигатель ("A", "B", "C" или "D")
	 */
	public Platform (String port) {
		motor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort(port));	
		motor.setSpeed(150);
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
	 * Команда поворота платформы на 90 градусов по часовой стрелке (указанное число раз). 
	 * Последний поворот завершается дополнительным доворотом на +15 градусов и
	 * возвратом на -15 градусов (для компенсации люфта куба на платформе). 
	 * 
	 * @param times	число повторов команды
	 * @see rotateCС
	 */
	public void rotate(int times) {
		for (int i = 0; i < times; i++) {
			motor.rotate(90 * GEAR_RATIO);
		}
		motor.rotate(DOVOROT * GEAR_RATIO);
		motor.rotate((-1) * DOVOROT * GEAR_RATIO);
	}
	/**
	 * Команда поворота платформы на 90 градусов против часовой стрелки (указанное число раз). 
	 * Последний поворот завершается дополнительным доворотом на -15 градусов и
	 * возвратом на +15 градусов (для компенсации люфта куба на платформе). 
	 * 
	 * @param times	число повторов команды
	 * @see rotate
	 */
	public void rotateCС(int times) {
		for (int i = 0; i < times; i++) {
			motor.rotate(-90 * GEAR_RATIO);
		}
		motor.rotate((-1) * DOVOROT * GEAR_RATIO);
		motor.rotate(DOVOROT * GEAR_RATIO);
	}
	/**
	 * Освободить ресурсы, занятые классом двигателя.
	 */
	public void close() {
		motor.close();
	}
}
