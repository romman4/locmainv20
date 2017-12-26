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

    private File selectedFile;
    private String outputPath, outputFilename = "avg_";

    private JLabel fileInName = new JLabel("имя файла");
    private JLabel fileOutPath= new JLabel("путь к готовому файлу");

    JTextArea loggingArea = new JTextArea();

    GUI() {
        super("Cell tower location app");
        this.setBounds(new Rectangle(800, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 2, 10, 60));
        JButton getFileButton = new JButton("Выбрать исходный файл...");
        getFileButton.setPreferredSize(new Dimension(20, 5));
        panel1.add(getFileButton);
        fileInName.setHorizontalAlignment(CENTER);
        panel1.add(fileInName);
        JButton getOutFileButton = new JButton("Выбрать путь для готового файла...");
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


        JButton startHandlingBtn = new JButton("Начать обработку файла");
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
        JPanel panel2 = new JPanel();
        panel2.add(startHandlingBtn);
        loggingArea.setBorder(new BorderUIResource.EtchedBorderUIResource());
        loggingArea.setColumns(60);
        loggingArea.setRows(10);
        JScrollPane pane = new JScrollPane(loggingArea);
        panel2.add(pane);
        this.add(panel2);
    }
}