import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;


public class ColorHand {
    private EV3MediumRegulatedMotor motor;
    private float[] sRGB = new float[3];
    /**
     * 
     */
    public static final int OUT = 0; //hand at starting point (outside the cube)
    public static final int IN = 1; //hand over cube
    private int state = OUT; //current hand state
    private EV3ColorSensor sensor;
    EV3TouchSensor touchSensor;
    private SensorMode sm;
    private float[] sR, sG, sB;
    private static final float COEFF_ST = (float) 2.617;
    private static final int N = 120;
    

    public ColorHand (String motorPort, String sensorPort) {
        motor = new EV3MediumRegulatedMotor(LocalEV3.get().getPort(motorPort));    
        sensor = new EV3ColorSensor(LocalEV3.get().getPort(sensorPort));
        sm = sensor.getRGBMode();
        motor.setSpeed(100);
        motor.backward();
        while (!motor.isStalled()) {
        }
        motor.flt();
        motor.rotate(90);
        state = OUT;
        
        sR = new float[N];
        sG = new float[N];
        sB = new float[N];
    }
    
    public void setSpeed(int speed) {
        motor.setSpeed(speed);
    }
    
    public void moveIN() {
        if (state == OUT) {
            motor.rotate(-90);
            state = IN;
        }
    }
    
    public void moveOUT() {
        if (state == IN) {
            motor.rotate(90);
            state = OUT;
        }
    }
    
    public int getColorID() {
    	return sensor.getColorID();
    }
    
    public float[] getRGB() {
        int sampleSize = sm.sampleSize();
        float samples[] = new float[sampleSize];
        Delay.msDelay(10);
//        moveIN();
        do {
            sm.fetchSample(samples, 0);
            sRGB[0] = samples[0];
            sRGB[1] = samples[1];
            sRGB[2] = samples[2];
        } while (!((sRGB[0] <= 1 && sRGB[0] >=0) 
                && (sRGB[1] <= 1 && sRGB[1] >=0) 
                && (sRGB[2] <= 1 && sRGB[2] >=0)));
//        moveOUT();
        return sRGB;
    }
    
    /**
     * Получить средний свет грани и доверительный интервал. Производится 120
     * измерений, вычисляется среднее значение и доверительный интервал по
     * методу Стьюдента (вероятность 0,99).
     * @return массив float в формате R,dR,G,dG,B,dB
     */
    public float[] getStatRGB() {
    	
    	float[] sRGB = new float[3];
    	float midR = 0, midG = 0, midB = 0;
    	float[] statRGB = new float[6];
    	float dR = 0, dG = 0, dB = 0;
    	
    	for (int i = 0; i < N; i++) {
    		sRGB = getRGB();
    		sR[i] = sRGB[0];
    		midR += sR[i];
    		sG[i] = sRGB[1];
    		midG += sG[i];
    		sB[i] = sRGB[2];
    		midB += sB[i];
    	}
    	midR = midR / N;
    	midG = midG / N;
    	midB = midB / N;
    	for (int i = 0; i < N; i++) {
    		dR += Math.pow((sR[i] - midR), 2); 
    		dG += Math.pow((sG[i] - midG), 2);
    		dB += Math.pow((sB[i] - midB), 2);
    	}
    	statRGB[0] = midR;
    	statRGB[1] = (float) (Math.sqrt(dR / N / (N - 1)) * COEFF_ST);
    	statRGB[2] = midG;
    	statRGB[3] = (float) (Math.sqrt(dG / N / (N - 1)) * COEFF_ST);
    	statRGB[4] = midB;
    	statRGB[5] = (float) (Math.sqrt(dB / N / (N - 1)) * COEFF_ST);
    	
    	return statRGB;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
