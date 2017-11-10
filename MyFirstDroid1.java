/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package sampleteam;


import robocode.Droid;
import robocode.MessageEvent;
import robocode.TeamRobot;
import static robocode.util.Utils.normalRelativeAngleDegrees;


/**
 * SimpleDroid - a sample robot by Mathew Nelson.
 * <p/>
 * Follows orders of team leader.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class MyFirstDroid1 extends TeamRobot implements Droid {

	/**
	 * run:  Droid's default behavior
	 */
	public void run() {
		out.println("MyFirstDroid ready.");
	}

	/**
	 * onMessageReceived:  What to do when our leader sends a message
	 */
			double[] x = new double[3];
			double[] y = new double[3];
			double[] time = new double[3];
			int i;
/*	void MyFirstDroid1(){
			x = new double[3];
			y = new double[3];
			time = new double[3];
	}*/
	public void onMessageReceived(MessageEvent e) {
		// Fire at a point
		double power = 3;
		if (e.getMessage() instanceof Point) {
			Point p = (Point) e.getMessage();
			
			for	(i=0 ; i<2 ; i++){
				x[i] = x[i+1];
				y[i] = y[i+1];
				time[i] = time[i+1];
			}
			out.print(x[0]);
			x[2] = p.getX();
			y[2] = p.getY();
			time[2] = System.nanoTime()*0.000000001;
			
			double vx,vy;
			double ax,ay;
			vx = (x[2]-x[1])/(time[2]-time[1]);
			vy = (y[2]-y[1])/(time[2]-time[1]);
			ax = ((x[2]-x[1])/(time[2]-time[1])-(x[1]-x[0])/(time[1]-time[0]))/(time[2]-time[0]);
			ay = ((y[2]-y[1])/(time[2]-time[1])-(y[1]-y[0])/(time[1]-time[0]))/(time[2]-time[0]);

			out.println(vx);
			out.println(vy);
			out.println(ax);
			out.println(ay);
			out.println();
			
			double MyX = this.getX();
			double MyY = this.getY();
			double EnemyX = p.getX();
			double EnemyY = p.getY();
			double PredictEnemyX,PredictEnemyY,T;
			double A = Math.pow(vx,2)+Math.pow(vy,2) - Math.pow(20-3*power,2);
			double B = vx*(MyX-EnemyX)+vy*(MyY-EnemyY);
			double C = Math.pow(MyX,2)+Math.pow(EnemyX,2)-2*MyX*EnemyX+Math.pow(MyY,2)+Math.pow(EnemyY,2)-2*MyY*EnemyY;
			
			T = (-B + Math.sqrt(Math.pow(B,2)+A*C)) / A;
			out.print("T:");
			out.println(T);
			// Calculate x and y to target
			double dx = p.getX()+vx*T - this.getX();
			double dy = p.getY()+vy*T - this.getY();
			// Calculate angle to target
			double theta = Math.toDegrees(Math.atan2(dx, dy));

			// Turn gun to target
			turnGunRight(normalRelativeAngleDegrees(theta - getGunHeading()));
			// Fire hard!
			fire(3);
		} // Set our colors
		else if (e.getMessage() instanceof RobotColors) {
			RobotColors c = (RobotColors) e.getMessage();

			setBodyColor(c.bodyColor);
			setGunColor(c.gunColor);
			setRadarColor(c.radarColor);
			setScanColor(c.scanColor);
			setBulletColor(c.bulletColor);
		}
	}
}
