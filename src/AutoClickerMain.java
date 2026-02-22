import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.util.concurrent.ThreadLocalRandom;


public class AutoClickerMain {
    boolean enabled = false;
    int cps = 10;

    public static void main(String[] args) {
        new AutoClickerMain().initialize();

    }

    private void initialize() {
        JFrame frame = new JFrame("JaviClicker");
        frame.setBounds(200, 200, 500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        Image icon = new ImageIcon(getClass().getResource("/resources/favicon.png")).getImage();
        frame.setIconImage(icon);

        JPanel panel = new JPanel() {
            final Image background = new ImageIcon(
                    getClass().getResource("/resources/background.jpg")
            ).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        };
        panel.setLayout(new BorderLayout());
        frame.setContentPane(panel);

        JLabel label = new JLabel(
                "<html><div style='text-align: center; padding: 20px 20px'>"
                        + "¡JaviClicker!<br>"
                        + "<span style='font-size:20px;'>U can start pressing the START button or 'CTRL + K'</span>"
                        + "</div></html>"
        );
        label.setFont(new Font("Arial", Font.BOLD, 50));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        panel.add(centerPanel, BorderLayout.CENTER);


        JButton button = new JButton("START");
        Border bordeTitulo = BorderFactory.createLineBorder(Color.BLACK, 5);
        button.setBorder(bordeTitulo);
        button.setPreferredSize(new Dimension(200, 80));
        button.setFont(new Font("Arial", Font.BOLD, 40));
        button.setBackground(Color.RED);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setBorderPainted(true);

        JTextField cpsInput = new JTextField("Introduce Clicks Per Second:");
        cpsInput.setForeground(Color.GRAY);

        cpsInput.addFocusListener(new java.awt.event.FocusAdapter() {

            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (cpsInput.getText().equals("Introduce Clicks Per Second:")) {
                    cpsInput.setText("");
                    cpsInput.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (cpsInput.getText().isEmpty()) {
                    cpsInput.setText("Introduce Clicks Per Second:");
                    cpsInput.setForeground(Color.RED);
                }
            }
        });

        panel.add(cpsInput, BorderLayout.SOUTH);

        button.addActionListener(e -> {

            if (button.getText().equals("START")) {

                try {
                    cps = Integer.parseInt(cpsInput.getText());

                    if (cps <= 0) {
                        JOptionPane.showMessageDialog(frame, "El número debe ser mayor que 0");
                        return;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Introduce un número válido.");
                    return;
                }

                button.setText("STOP");
                enabled = true;
                startClicking();

            } else {

                button.setText("START");
                enabled = false;
            }

        });

        centerPanel.add(button);

        KeyStroke keyStroke = KeyStroke.getKeyStroke("control K");
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "toggleClicker");
        panel.getActionMap().put("toggleClicker", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });

        frame.setVisible(true);
    }

    private void startClicking() {
        Thread t = new Thread(() -> {
            try {
                Robot robot = new Robot();
                Thread.sleep(3000);

                int clickCount = 0;
                while (enabled) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                    int baseDelay = 1000 / cps;

                    double variation = 0.30;
                    double randomFactor = 1 + ThreadLocalRandom.current().nextDouble(-variation, variation);

                    int delay = (int) (baseDelay * randomFactor);

                    delay = Math.max(delay, 1);

                    Thread.sleep(delay);
                    clickCount++;
                    if (clickCount % 40 == 0) {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(80, 200));
                    }
                    System.out.println("CLICKS :: " + clickCount);
                    System.out.println("DELAY :: " + delay + "ms");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }
}