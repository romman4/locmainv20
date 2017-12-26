import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingConstants.CENTER;

public class GUI extends JFrame {

    File selectedFile;
    String outputPath, outputFilename = "avg_";

    JButton getFileButton = new JButton("Выбрать исходный файл...");
    JLabel fileInName = new JLabel("имя файла");

    JButton getOutFileButton = new JButton("Выбрать путь для готового файла...");
    JLabel fileOutPath= new JLabel("путь к готовому файлу");

    JButton startHandlingBtn = new JButton("Начать обработку файла");

    JTextArea loggingArea = new JTextArea();

    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    GUI() {
        super("Cell tower location app");
        this.setBounds(new Rectangle(800, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1));

        panel1.setLayout(new GridLayout(2, 2, 10, 60));
        getFileButton.setPreferredSize(new Dimension(20, 5));
        panel1.add(getFileButton);
        fileInName.setHorizontalAlignment(CENTER);
        panel1.add(fileInName);
        getOutFileButton.setPreferredSize(new Dimension(20, 5));
        panel1.add(getOutFileButton);
        fileOutPath.setHorizontalAlignment(CENTER);
        panel1.add(fileOutPath);
        this.setLocationRelativeTo(null);

        getFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    fileInName.setText(selectedFile.getName());
                    outputFilename += fileInName.getText();
                }
            }
        });

        getOutFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    outputPath = chooser.getSelectedFile().getPath();
                    fileOutPath.setText(outputPath + "\\" + outputFilename);
                }
            }
        });
        this.add(panel1);


        startHandlingBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (selectedFile == null) {
                        throw new IOException("Выберите файл для обработки");
                    } else if (outputPath == null) {
                        throw new IOException("Выберите путь для вывода");
                    }
                    FileHandler fileHandler = new FileHandler(selectedFile, fileOutPath.getText());
                    fileHandler.startHandling(GUI.this);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                } catch (UnirestException e2) {
                    JOptionPane.showMessageDialog(null, e2.getMessage());
                } catch (NullPointerException e3) {
                    JOptionPane.showMessageDialog(null, "Выберите корректный файл и путь");
                }
            }
        });
        startHandlingBtn.setPreferredSize(new Dimension(200, 50));
        panel2.add(startHandlingBtn);
        loggingArea.setBorder(new BorderUIResource.EtchedBorderUIResource());
        loggingArea.setColumns(60);
        loggingArea.setRows(10);
        JScrollPane pane = new JScrollPane(loggingArea);
        panel2.add(pane);
        this.add(panel2);
    }
}