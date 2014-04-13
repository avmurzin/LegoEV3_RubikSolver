import java.math.BigDecimal;
import java.math.RoundingMode;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;
import lejos.utility.Delay;
import lejos.utility.TextMenu;

/**
 * Основной модуль. Создает меню для выбора пасьянса, решает выбранный пасьянс, 
 * возвращает куб в исходное состояние.
 * 
 * @author Andrei Murzin
 * @version 0.1
 */
public class CubeSolver {
    /**
     * @param args
     */
    static final int F = MakeSolution.F; 
    static final int R = MakeSolution.R;
    static final int B = MakeSolution.B;
    static final int L = MakeSolution.L;
    static final int U = MakeSolution.U;
    static final int D = MakeSolution.D;
    static final int FC = MakeSolution.FC; 
    static final int RC = MakeSolution.RC;
    static final int BC = MakeSolution.BC;
    static final int LC = MakeSolution.LC;
    static final int UC = MakeSolution.UC;
    static final int DC = MakeSolution.DC;
    static final int F2 = MakeSolution.F2; 
    static final int R2 = MakeSolution.R2;
    static final int B2 = MakeSolution.B2;
    static final int L2 = MakeSolution.L2;
    static final int U2 = MakeSolution.U2;
    static final int D2 = MakeSolution.D2;
    
    static final String[] marriages = {
        "Kvark",
        "Six points",
        "Flower",
        "Three corners",
        "Three points",
        "Mezon",
        "KvarkMezon",
        "Links",
        "Diagonals"
    };
    static final int[][] sequences = {
        {UC, L2, U, FC, R2, F, UC, L2, U, FC, R2, F},
        {DC, U, LC, R, BC, F, DC, U},
        {F, U2, DC, LC, UC, D, F2, U, DC, LC, UC, D, UC, FC},
        {F, RC, D, FC, UC, F, DC, FC, U, F, R, FC},
        {U, DC, RC, D2, F, RC, L, D2, R, LC, F, D2, R, UC, D},
        {U2, F2, R2, UC, L2, D, B, RC, B, RC, B, RC, DC, L2, UC},
        {UC, L2, F2, DC, LC, D, U2, R, UC, RC, U2, R2, U, FC, LC, U, RC},
        {U, B2, L, D, BC, F, LC, D, UC, LC, R, FC, D2, RC},
        {F, B, L, R, F, B, L, R, F, B, L, R}
    };
    
    public static void main(String[] args) {
        Cube cube = new Cube("B", "A", "C","S1", 200, 200, 150);
        MakeSolution solution = new MakeSolution(cube);
        TextMenu marriageMenu = new TextMenu(marriages, 1, "Marriages list");

        int marriageNo = marriageMenu.select();
        if (marriageNo < 0) return;
        
        LCD.clear();
        LCD.drawString(marriages[marriageNo], 0, 1);
        LCD.drawString("Number of steps: ", 1, 2);
        LCD.drawString("" + sequences[marriageNo].length, 1, 3);
        LCD.drawString("ENTER to start", 1, 4);
        LCD.drawString("ESCAPE to exit", 1, 5);
        LCD.refresh();

        if (Button.waitForAnyPress() == Button.ID_ESCAPE) {
        	cube.close();
            return;
        }
        LCD.clear();
        LCD.drawString("Working...", 2, 2);
        LCD.refresh();
        solution.setSequence(sequences[marriageNo]);
        solution.makeSequence();
        LCD.clear();
        LCD.drawString("Done!", 2, 2);
        LCD.drawString("Press ENTER to ", 2, 3);
        LCD.drawString("reverse", 2, 4);
        LCD.refresh();
        if (Button.waitForAnyPress() == Button.ID_ESCAPE) {
        	cube.close();
            return;
        }
        
        LCD.clear();
        LCD.drawString("Working...", 2, 2);
        LCD.refresh();
        
        solution.invertSequence();
        solution.makeSequence();
        LCD.clear();
        LCD.drawString("Done!", 2, 2);
        LCD.refresh();
        
        cube.close();

}

}
