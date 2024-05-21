/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieReservationGUI extends JFrame {

    private static final int TOTAL_SEATS = 50;
    private Seat[] seats = new Seat[TOTAL_SEATS];

    private SeatPanel seatPanel;

    public MovieReservationGUI() {
        setTitle("Movie Seat Reservation System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeSeats();

        seatPanel = new SeatPanel(seats);
        add(seatPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());

        JButton showSeatsButton = new JButton("Show Available Seats");
        showSeatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAvailableSeats();
            }
        });

        JButton reserveSeatButton = new JButton("Reserve a Seat");
        reserveSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveSeat();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openbooking1();
            }
        });

        controlPanel.add(showSeatsButton);
        controlPanel.add(reserveSeatButton);
        controlPanel.add(exitButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void initializeSeats() {
        for (int i = 0; i < TOTAL_SEATS; i++) {
            if (i < TOTAL_SEATS / 2) {
                seats[i] = new PremiumSeat(i + 1);
            } else {
                seats[i] = new RegularSeat(i + 1);
            }
        }
    }

    private void showAvailableSeats() {
        seatPanel.repaint();
    }

    private void reserveSeat() {
        String input = JOptionPane.showInputDialog("Enter the seat number you want to reserve:");
        try {
            int seatNumber = Integer.parseInt(input);

            if (seatNumber < 1 || seatNumber > TOTAL_SEATS) {
                JOptionPane.showMessageDialog(this, "Invalid seat number. Please enter a valid seat number.");
                return;
            }

            Seat selectedSeat = seats[seatNumber - 1];

            if (selectedSeat.isReserved()) {
                JOptionPane.showMessageDialog(this, "Seat already reserved. Please choose another seat.");
            } else {
                selectedSeat.setReserved(true);
                JOptionPane.showMessageDialog(this, "Seat " + seatNumber + " reserved successfully.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid seat number.");
        }
    }

    private void openbooking1() {
        // Dispose of the current frame
        dispose();

        // Open the new Booking1Page
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new booking1().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MovieReservationGUI().setVisible(true);
            }
        });
    }

    private abstract class Seat {
        private int seatNumber;
        private boolean reserved;

        public Seat(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public boolean isReserved() {
            return reserved;
        }

        public void setReserved(boolean reserved) {
            this.reserved = reserved;
        }
    }

    private class PremiumSeat extends Seat {
        public PremiumSeat(int seatNumber) {
            super(seatNumber);
        }
    }

    private class RegularSeat extends Seat {
        public RegularSeat(int seatNumber) {
            super(seatNumber);
        }
    }

    private class SeatPanel extends JPanel {
        private Seat[] seats;

        public SeatPanel(Seat[] seats) {
            this.seats = seats;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int rows = 5;
            int cols = TOTAL_SEATS / rows;
            int seatSize = 80;
            int gap = 20;

            for (int i = 0; i < TOTAL_SEATS; i++) {
                int row = i / cols;
                int col = i % cols;

                int x = col * (seatSize + gap);
                int y = row * (seatSize + gap);

                Seat seat = seats[i];

                if (seat.isReserved()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }

                g.fillRect(x, y, seatSize, seatSize);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, seatSize, seatSize);
                g.drawString(String.valueOf(seat.getSeatNumber()), x + 10, y + 20);
            }
        }
    }
}
