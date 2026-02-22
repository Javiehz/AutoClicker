# JaviClicker

JaviClicker is a Java auto-clicker that performs left mouse clicks at a user-defined Clicks Per Second (CPS), simulating human-like behavior.

Features

Simulates left mouse clicks using java.awt.Robot.

UI made using Swing (Old JAVA UI) -> FOR NEXT VERSIONS IT WILL BE IMPROVED TO JavaFX (Modern JAVA UI)

Adjustable Clicks Per Second (CPS).

Randomized delay (±30%) between clicks to mimic human behavior.

Small random pause every 40 clicks.

Keyboard shortcut (CTRL + K) to start/stop clicking.

Simple Swing GUI with background image and custom icon.

Requirements

Java 8 or higher.

GUI support (Swing and AWT).

Usage

Run the program.

Enter the desired CPS in the input field.

Press START or use CTRL + K to toggle clicking.

The program waits 3 seconds and starts clicking automatically.

Press STOP or CTRL + K again to stop.

How It Works

The delay between clicks is calculated as:

baseDelay = 1000 / cps

A ±30% random variation is applied to simulate human behavior.

Every 40 clicks, a random micro-pause between 80–200 ms is added.

Clicking is executed in a separate thread using java.awt.Robot.
Notes

Use responsibly; some applications or games may prohibit automation tools.

Intended for educational purposes and learning Java GUI and threading.
